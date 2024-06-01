package toyprj.toyprj_noticeBoard.controller;

import lombok.RequiredArgsConstructor;
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
}
