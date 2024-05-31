package toyprj.toyprj_noticeBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;

// repository는 기본적으로 엔티티만 받아 준다.
// 상속 받은 것이라서 추가할 내용이 없다.
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

}
