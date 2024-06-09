package toyprj.toyprj_noticeBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyprj.toyprj_noticeBoard.dto.CommentDTO;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;
import toyprj.toyprj_noticeBoard.entity.CommentEntity;
import toyprj.toyprj_noticeBoard.repository.BoardRepository;
import toyprj.toyprj_noticeBoard.repository.CommentRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    // repository 주입 받기
    private final CommentRepository commentRepository;
    // 부모 엔티티 받기
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDTO) {
        // 부모 엔티티(BoardEntity) 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDTO.getBoardId());
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            // Entity -> DTO 변환
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO,boardEntity);
            return commentRepository.save(commentEntity).getId();
        } else {
            return null;
        }
    }
}
