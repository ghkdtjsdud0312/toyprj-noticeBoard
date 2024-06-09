package toyprj.toyprj_noticeBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;
import toyprj.toyprj_noticeBoard.entity.CommentEntity;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    // select * from comment_table where board_id=? => findAllByBoardEntityOrderByIdDesc
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
