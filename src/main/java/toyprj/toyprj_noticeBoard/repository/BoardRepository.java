package toyprj.toyprj_noticeBoard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;

// repository는 기본적으로 엔티티만 받아 준다.
// 상속 받은 것이라서 추가할 내용이 없다.
public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    // update board_table set board_hits = board_hits+1 where id=?
    // 엔티티를 기준으로 쿼리문 작성
    // 쿼리문 끝에 nativeQuery = true 하면 DB에 쓰이는 쿼리문과 같게 만들어줌
    // 원래는 쿼리에 테이블이름이 오는데 엔티티가 오면 엔티티를 써주고 그 뒤 쿼리문 내용은 약어(b)를 쓰는게 필수
    // 바뀌는 부분은 :id로(콜론 붙여서) 사용 = param의 id와 같은 걸로 생각하기
    @Modifying // 업데이트나 삭제를 할 때 추가적으로 modifying 어노테이션을 필수로 해줘야 한다.
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
        void updateHits(@Param("id") Long id);
}
