package toyprj.toyprj_noticeBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyprj.toyprj_noticeBoard.entity.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

}
