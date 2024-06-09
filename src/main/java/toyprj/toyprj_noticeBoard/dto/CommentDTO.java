package toyprj.toyprj_noticeBoard.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import toyprj.toyprj_noticeBoard.entity.CommentEntity;

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

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
     // commentDTO.setBoardId(commentEntity.getBoardEntity().getId()); // service 메서드 @Transactional 해줘야 사용 가능
        commentDTO.setBoardId(boardId);
        return commentDTO;
    }
}
