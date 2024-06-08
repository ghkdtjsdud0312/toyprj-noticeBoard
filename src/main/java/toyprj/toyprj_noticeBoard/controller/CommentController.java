package toyprj.toyprj_noticeBoard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import toyprj.toyprj_noticeBoard.dto.CommentDTO;

@Controller
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    @PostMapping("/save")
    public @ResponseBody String save(@ModelAttribute CommentDTO commentDTO) {
        System.out.println("commentDTO = " + commentDTO);
        return "요청 성공";
    }
}