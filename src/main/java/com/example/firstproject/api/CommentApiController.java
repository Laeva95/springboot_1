package com.example.firstproject.api;

import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // RestController으로 스프링 컨테이너에 등록. 메소드의 반환값을 JSON으로 브라우저에 응답
@Slf4j
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    // 댓글 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId){
        // 서비스에 위임
        // 부모 게시글의 id를 통해 댓글 목록을 조회
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답

        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto){
        // 서비스에 위임
        // 부모 게시글의 id와 생성 데이터 dto를 전달하여 새로운 dto 객체 생성
        CommentDto createdDto = commentService.create(articleId, dto);

        // 결과를 브라우저로 응답
        return ResponseEntity.status(200).body(createdDto);
    }

    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto){
        // 서비스 객체에 수정 요청
        CommentDto updated = commentService.update(id, dto);

        // 결과를 브라우저에 응답
        return ResponseEntity.status(200).body(updated);
    }

    // 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id  ){
        // 서비스 객체에 삭제 요청
        CommentDto deleted = commentService.delete(id);

        // 결과를 브라우저에 응답
        return ResponseEntity.status(200).body(deleted);
    }
}
