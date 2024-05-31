package toyprj.toyprj_noticeBoard.dto;

// DTO(Data Transfer Object) : 데이터를 전송할 때 사용하는 객체(하나의 객체를 담아서 주고 받는 것)
// VO, Bean으로도 많이 사용함
// Entity 라는 것도 있지만 목적은 다르다.(다른 목적을 가짐)

import lombok.*;

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
}
