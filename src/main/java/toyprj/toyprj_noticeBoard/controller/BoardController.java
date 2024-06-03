package toyprj.toyprj_noticeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import toyprj.toyprj_noticeBoard.dto.BoardDTO;
import toyprj.toyprj_noticeBoard.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService; // 생성자 주입 방식으로 의존성을 주입 받게 된다.

    // 저장한 것 조회
    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    // 입력한 내용 저장
    @PostMapping("/save")
    // ModelAttribute 로테이션에 의해 BoardDTO의 boardDTO 객체를 찾아서 html의 name과 dto의 이름이 동일하다면 스프링이 해당하는 필드에 대한 setter를 호출하면서 dto에 담겨진 값을 setter method로 각각 담아준다.
    public String save(@ModelAttribute BoardDTO boardDTO) { // @RequestParam은 html의 값을 불러오기 위해서 사용하는 것인데 DTO를 세팅해주면 일일이 RequestParam 받아 올 필요 없이 DTO 하나로 받으면 된다.
        boardService.save(boardDTO);
        return "index";
    }

    // 게시판 목록
    @GetMapping("/")
    public String findAll(Model model) {
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    // 게시판 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) {
        // 해당 게시글의 조회수를 하나 올리고
        // 게시글 데이터를 가져와서 detail.html에 출력
        // 두 번의 호출이 일어난다.(조회수, id로 데이터 가져오기)
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
    }

    // 게시판 수정 조회
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    // 게시판 수정하기
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO); // 수정이 끝나고 수정이 반영된 객체를 가져와서 그걸 가지고 DTO로 가져가는 것
        model.addAttribute("board", board);
        return "detail";
//        return "redirect:/board/" + boardDTO.getId();
    }

    // 게시판 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        return "redirect:/board/";
    }

    // 페이징 처리(몇 페이지에 대한 요청을 받았는지 봄)
    @GetMapping("/paging") // /board/paging?page=1 -> 페이지 번호 바뀜(pageable -> 파라미터 값을 받아줌)
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) { // model은 페이징 된 데이터를 가지고 화면으로 넘어 가야해서 model 객체를 사용, page=1은 기본 값으로 설정
//        pageable.getPageNumber(); // 몇 페이지가 요청 되었는지의 숫자를 나타냄
        Page<BoardDTO> boardList = boardService.paging(pageable);

        int blockLimit = 3; // 페이지 번호 갯수
        // startpage 페이지 설명 : pageable.getPageNumber() -> 현재 사용자가 요청한 페이지를 blockLimit 으로 나눠서 Math.ceil 소숫점을 올리는 작업에서 -1을 하고 blockLimit(선택한 페이지)에 곱하기를 하고 +1 해준다.
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 계산으로 나오는 값 : 1 4 7 10 ~~
        // endPage 페이지 설명 : 삼항 연산자 사용 / ((startPage + blockLimit - 1) < boardList.getTotalPages()) -> 조건식 / 만족하면 endpage을 startPage + blockLimit - 1 / 만족 못하면 전체 페이지 값을 endpage -> boardList.getTotalPages()
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3 (blockLimit)
        // 현재 사용자가 7페이지
        // 7 8 9 (blockLimit)
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging"; // paging.html로 이동
    }
}
