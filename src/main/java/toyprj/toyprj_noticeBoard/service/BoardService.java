package toyprj.toyprj_noticeBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyprj.toyprj_noticeBoard.dto.BoardDTO;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;
import toyprj.toyprj_noticeBoard.repository.BoardRepository;

// DTO -> Entity 변환 (entity 클래스에서 시용)
// Entity -> DTO 변환 (DTO 클래스에서 시용)
// Entity(DB와 연관이 많이 되어 있어 노출이 되면 좋지 않다, 되도록이면 service 까지 오게 한다.)

// 컨트롤러로부터 호출을 받을 떄는 디티오로 옮겨 받는다.
// 리파지토리로 넘겨줄 때는 엔티티로 넘겨 받는다.
// 조회 같은 디비는 리파지토리로 부터 엔티티를 받아 온다. 근데 이거를 컨드롤러로 받을 때는 디티오로 바꿔서 넘겨준다.

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {

        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);
    }

}
