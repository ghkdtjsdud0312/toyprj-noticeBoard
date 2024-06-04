package toyprj.toyprj_noticeBoard.dto;

// DTO(Data Transfer Object) : 데이터를 전송할 때 사용하는 객체(하나의 객체를 담아서 주고 받는 것)
// VO, Bean으로도 많이 사용함
// Entity 라는 것도 있지만 목적은 다르다.(다른 목적을 가짐)

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;
import toyprj.toyprj_noticeBoard.entity.BoardFileEntity;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter // getter 만들어 줌
@Setter // setter 만들어 줌
@ToString // 필드 값 확인 시 사용
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 필드를 매개 변수로 하는 생성자
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits; // 조회수
    private LocalDateTime boardCreatedTime; // 작성 시간
    private LocalDateTime boardUpdatedTime; // 수정 시간

    // 파일 관리 DTO
    // DTO에서 받아주는 부분은 boardFile만 동작을 한다.
    private MultipartFile boardFile; // save.html -> Controller 파일 담는 용도
    // service 클래스에서 사용
    private String originalFileName; // 원본 파일 이름 조회 시
    private String storedFileName; // 서버 저장용 파일 이름
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)

    // mappedBy = "boardEntity" : boardFileEntity에 boardEntity과 같게 해야 한다.
    // 부모 엔티티가 삭제되면 자식 엔티티도 삭제 된다.(부모가 자식의 삭제 생명 주기 관리)
    // cascade = CascadeType.REMOVE : 부모 엔티티와 자식 엔티티 사이의 연관관계를 제거해도, 자식 엔티티는 삭제되지 않고 그대로 DB에 남아 있다.
    // orphanRemoval = true : 부모 엔티티와 자식 엔티티 사이의 연관관계를 제거하면, 자식 엔티티는 고아 객체로 취급되어 DB에서 삭제됩니다.
    // fetch = FetchType.LAZY :지연 로딩, 필요한 것만 가져올 수 있게
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>(); // 게시글 하나에 여러 개가 올 수 있다. / boardFileEntity 참조관계 형성

    // command + N -> genarate
    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        return boardDTO;
    }
}
