package toyprj.toyprj_noticeBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toyprj.toyprj_noticeBoard.dto.BoardDTO;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;
import toyprj.toyprj_noticeBoard.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    // 전체 조회(목록 글)
    // entity -> DTO로 넘어오는 객체 만들기
    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for (BoardEntity boardEntity : boardEntityList) {
            boardDTOList.add(BoardDTO.toBoardDTO(boardEntity));
        }
        return boardDTOList;
    }

    // 조회수 증가
    @Transactional // 수동적인 쿼리를 관리해야 하는 경우 사용
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    // 게시글 데이터 조회
    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if (optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            BoardDTO boardDTO = BoardDTO.toBoardDTO(boardEntity);
            return boardDTO;
        } else {
            return null;
        }
    }

    // 게시글 수정
    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity);
        return findById(boardDTO.getId());
    }

    // 게시글 삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // 페이징 요청 처리
    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() -1; // page 위치에 있는 값은 0부터 시작하기 때문이다.
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // Sort.by(Sort.Direction.DESC, "id") => sort에 id(Entity 기준)가 DESC 내림차순
        Page<BoardEntity> boardEntities = // boardEntities : 페이징 처리된 데이터를 가져올 때
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
    }
}
