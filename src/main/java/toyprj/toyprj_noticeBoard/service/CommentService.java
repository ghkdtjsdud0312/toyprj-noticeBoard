package toyprj.toyprj_noticeBoard.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyprj.toyprj_noticeBoard.repository.CommentRepository;

@Service
@RequiredArgsConstructor
public class CommentService {
    // repository 주입 받기
    private final CommentRepository commentRepository;
}
