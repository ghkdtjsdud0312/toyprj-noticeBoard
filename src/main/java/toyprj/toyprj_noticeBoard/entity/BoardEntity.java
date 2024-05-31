package toyprj.toyprj_noticeBoard.entity;

import lombok.Getter;
import lombok.Setter;
import toyprj.toyprj_noticeBoard.dto.BoardDTO;

import javax.persistence.*;

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

    // static 형태의 메서드로 구현
    public static BoardEntity toSaveEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits());

        return boardEntity;
    }

}
