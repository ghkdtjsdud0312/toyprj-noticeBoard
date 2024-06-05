package toyprj.toyprj_noticeBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import toyprj.toyprj_noticeBoard.dto.BoardDTO;
import toyprj.toyprj_noticeBoard.entity.BoardEntity;
import toyprj.toyprj_noticeBoard.entity.BoardFileEntity;
import toyprj.toyprj_noticeBoard.repository.BoardRepository;

import java.io.File;
import java.io.IOException;
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

    public void save(BoardDTO boardDTO) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDTO.getBoardFile().isEmpty()) {
            // 첨부 파일 없음
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
            boardRepository.save(boardEntity);
        } else  {
            // 참부 파일 있음
            // 1. DTO에 담긴 파일을 꺼냄
            MultipartFile boardFile = boardDTO.getBoardFile();
            // 2. 파일의 이름 가져옴
            String originalFileName = boardFile.getOriginalFilename();
            // 3. 서버 저장용 이름을 만듦(내사진.jpeg -> 840549092_내사진.jpeg => 파일끼리 겹치지 않게 하기 위해서)
            String storedFileName = System.currentTimeMillis() + " " + originalFileName; // 난수 숫자 필요
            // 4. 저장 경로 설정
            String savePath = "/Users/hwangseon-yeong/springboot.img/" + storedFileName; // 맥인 경우 : /Users/사용자 이름/springboot.img/890493583_내사진.jpeg
            // 5. 해당 경로에 파일 저장
            boardFile.transferTo(new File(savePath)); // 지정된 경로로 파일을 넘긴다.
            // 6. board_table에 해당 데이터 save 처리
            BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO); // id 값이 없다.
            // 7. board_file_table에 해당 데이터 save 처리
            Long saveId = boardRepository.save(boardEntity).getId();
            BoardEntity board = boardRepository.findById(saveId).get(); // 부모 엔티티로부터 다시 받아온다, 다시 db에서 엔티티르 가져옴

            BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFileName, storedFileName);

        }
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

    // 페이징 요청 처리(entity 객체 -> dto 객체로 변환 =. page 객체)
    public Page<BoardDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() -1; // page 위치에 있는 값은 0부터 시작하기 때문이다.
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // Sort.by(Sort.Direction.DESC, "id") => sort에 id(Entity 기준)가 DESC 내림차순
        Page<BoardEntity> boardEntities = // boardEntities : 페이징 처리된 데이터를 가져올 때
                boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // 페이지 목록에 나오는 내용: id, writer, title, hits, createdTime
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }
}
