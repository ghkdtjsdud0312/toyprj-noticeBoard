package toyprj.toyprj_noticeBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyprj.toyprj_noticeBoard.dto.CommentDTO;
import toyprj.toyprj_noticeBoard.entity.CommentEntity;
import toyprj.toyprj_noticeBoard.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    // repository 주입 받기
    private final CommentRepository commentRepository;

    public void save(CommentDTO commentDTO) {
        // Entity -> DTO 변환
        CommentEntity.toSaveEntity(commentDTO);
    }
}
