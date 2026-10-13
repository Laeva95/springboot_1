package com.example.firstproject.service;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service    // Service 객체를 자동 생성해서 스프링 컨테이너에 등록
@Slf4j
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    // 댓글 조회
    public List<CommentDto> comments(Long articleId) {
        // 결과 반환
        // db에서 List<Comment> 를 반환 받아서 stream -> List<CommentDto>으로 변환하여 반환
        return commentRepository.findByArticleId(articleId).stream().map(CommentDto::createCommentDto).toList();
    }

    // 댓글 생성
    // 댓글 정보가 담긴 CommentDto 반환
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // DB에서 부모 게시글을 조회해서 가져오고 없으면 예외 발생
        Article article = articleRepository.findById(articleId)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패! 대상 부모 게시글이 없습니다."));

        // 부모 게시글의 새 댓글 엔티티 생성
        Comment comment = Comment.createComment(dto, article);

        // 생성된 엔티티를 DB에 저장
        Comment created = commentRepository.save(comment);

        // DB에 저장한 엔티티 정보를 dto 객체로 변환해서 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 수정할 댓글이 DB에 존재하는지 확인
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패! 수정할 댓글을 찾을 수 없습니다."));

        // 수정시킬 댓글 정보가 있다면 수정
        target.patch(dto);

        // DB에 수정 정보 반영
        Comment updated = commentRepository.save(target);

        // 수정한 CommentDto 객체 반환
        return CommentDto.createCommentDto(updated);
    }

    public CommentDto delete(Long id) {
        // 삭제할 대상이 존재하는지 db에서 확인
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패! 삭제할 댓글이 없습니다."));

        // 댓글 삭제
        commentRepository.delete(target);

        // 삭제에 성공한 댓글 정보 반환
        return CommentDto.createCommentDto(target);
    }
}
