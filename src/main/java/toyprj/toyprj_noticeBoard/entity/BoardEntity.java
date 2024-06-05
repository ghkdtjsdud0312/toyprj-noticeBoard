package toyprj.toyprj_noticeBoard.entity;

import lombok.Getter;
import lombok.Setter;
import toyprj.toyprj_noticeBoard.dto.BoardDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// DB의 테이블 역할을 하는 클래스
@Entity
@Getter
@Setter
@Table(name = "board_table") // 테이블 이름
public class BoardEntity extends BaseEntity{
    @Id // pk 컬럼 지점, 필수로 았어야 함
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    // nullable = false는 null 일 수 없다 라는 뜻(not null)
    // length : 컬럼 크기 값 지정(크기 20)
    @Column(length = 20, nullable = false) // 일반 컬럼에 주는 어노테이션
    private String boardWriter;

    @Column // 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    // mappedBy = "boardEntity" : boardFileEntity에 boardEntity과 같게 해야 한다.
    // 부모 엔티티가 삭제되면 자식 엔티티도 삭제 된다.(부모가 자식의 삭제 생명 주기 관리)
    // cascade = CascadeType.REMOVE : 부모 엔티티와 자식 엔티티 사이의 연관관계를 제거해도, 자식 엔티티는 삭제되지 않고 그대로 DB에 남아 있다.
    // orphanRemoval = true : 부모 엔티티와 자식 엔티티 사이의 연관관계를 제거하면, 자식 엔티티는 고아 객체로 취급되어 DB에서 삭제됩니다.
    // fetch = FetchType.LAZY :지연 로딩, 필요한 것만 가져올 수 있게
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>(); // 게시글 하나에 여러 개가 올 수 있다. / boardFileEntity 참조관계 형성

    // static 형태의 메서드로 구현
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0); // 파일 없음

        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId()); // id가 있어야만 데이터 쿼리가 전환할 수 있음
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());

        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); // 파일 있음
        return boardEntity;
    }
}
