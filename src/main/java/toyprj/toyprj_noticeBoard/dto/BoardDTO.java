package toyprj.toyprj_noticeBoard.dto;

// DTO(Data Transfer Object) : 데이터를 전송할 때 사용하는 객체(하나의 객체를 담아서 주고 받는 것)
// VO, Bean으로도 많이 사용함
// Entity 라는 것도 있지만 목적은 다르다.(다른 목적을 가짐)

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;

import java.time.LocalDateTime;

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
