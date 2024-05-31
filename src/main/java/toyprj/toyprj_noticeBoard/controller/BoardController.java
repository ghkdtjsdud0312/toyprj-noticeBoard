package toyprj.toyprj_noticeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import toyprj.toyprj_noticeBoard.dto.BoardDTO;
import toyprj.toyprj_noticeBoard.service.BoardService;

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
        return null;
    }
}
