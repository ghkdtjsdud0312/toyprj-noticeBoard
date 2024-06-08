package toyprj.toyprj_noticeBoard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id; // 댓글 번호
    private String commentWriter; // 작성자
    private String commentContents; // 작성 내용
    private Long boardId; // 게시물 번호(fk)
    private LocalDateTime commentCreatedTime; // 댓글 작성 시간
}
