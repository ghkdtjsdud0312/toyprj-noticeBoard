package toyprj.toyprj_noticeBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyprj.toyprj_noticeBoard.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

}
