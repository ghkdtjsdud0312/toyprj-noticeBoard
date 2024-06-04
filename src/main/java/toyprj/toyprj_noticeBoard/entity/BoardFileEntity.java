package toyprj.toyprj_noticeBoard.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_file_table")
public class BoardFileEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String storedFileName;

    // 파일의 기준에서는 게시글과 N:1 관계가 된다.
    // 연관된 엔티티를 프록시로 조회
    // 프록시를 실제 사용할 때 초기화하면서 데이터베이스를 조회한다.
    @ManyToOne(fetch = FetchType.LAZY) // board를 조회할 때 지연 로딩을 사용하겠다는 의미 / 즉시 로딩 적용 시 , 예상하지 못한 쿼리가 발생할 가능성이 높다.
    @JoinColumn(name = "board_id") // 부모를 조회할 때 자식을 같이 그냥 다 가져온다. 이때 lazy를 사용하면 필요한 상황에 내가 원한 것을 호출해서 쓸 수 있다.
    // 반드시 부모 엔티티 타입으로 작성해야 한다. 실제 DB에 들어갈 때는 id값만 들어감
    // jpa로 부모 자식 관계를 맺어 놓으면 그 부모 엔티티 객체 조회를 했을 때 자식 엔티티 객체를 같이 조회를 할 수 있다.
    private BoardEntity boardEntity; // 부모 엔티티 타입으로 적어야 한다.


}
