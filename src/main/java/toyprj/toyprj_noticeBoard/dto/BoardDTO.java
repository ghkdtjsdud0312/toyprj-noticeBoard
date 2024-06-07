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
    private List<MultipartFile> boardFile; // save.html -> Controller 파일 담는 용도
    // service 클래스에서 사용
    private List<String> originalFileName; // 원본 파일 이름 조회 시
    private List<String> storedFileName; // 서버 저장용 파일 이름
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
        if (boardEntity.getFileAttached() == 0) {
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 0
        } else {
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDTO.setFileAttached(boardEntity.getFileAttached()); // 1
            // 파일 이름을 가져가야 함
            // originalFileName, storedFileName : board_file_table(BoardFileEntity)
            // join 문법
            // select * from board_table b, board_file_table bf where b.id=bf.board_id and where b.id=?
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
                 }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
            // 단일 파일일 경우
//            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
//            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
        }
        return boardDTO;
    }
}
