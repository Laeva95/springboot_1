package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 이 클래스를 REST API 개발 방식으로 개발한 컨트롤러로 스프링 컨테이너 공간에 등록
// 톰캣 서버를 시작할 때 ArticleApiController 클래스의 객체가 1개 자동 생성되어 스프링 컨테이너 객체 공간에 보관해 등록됨
// 아래의 ArticleApiController 클래스 내부에 작성한 모든 메소드의 반환값은 HTTP 응답 메세지의 본문 영역에 표시됨
@RestController
@Slf4j  // 변수 값을 확인하기 위한 라이브러리 어노테이션
public class ArticleApiController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleRepository articleRepository;

    // GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        // 모든 article 객체를 배열 형태로 반환
        return articleService.index();
    }

    // GET
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){ // @PathVariable: url의 변수를 가져와서 매개변수로 전달
        // 특정 id 값인 article 객체를 찾아서 반환(없으면 null 반환)
        return articleService.show(id);
    }

    // POST
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleForm dto){
        // create(): DB에 추가할 글 제목, 글 내용이 저장된 ArticleForm을 Article 엔티티로 변환해서 반환
        Article created = articleService.create(dto);

        // DB에 Article 엔티티를 삽입 성공하면 성공 코드 반환
        // DB에 Article 엔티티를 삽입 실패하면 실패 코드 반환
        return (created != null) ? ResponseEntity.status(HttpStatus.OK).body(created)
                                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        // Service 클래스에 글 수정 요청하여 수정된 Article 엔티티를 반환 받음
        Article updated = articleService.update(id, dto);

        // DB에 Article 엔티티를 수정 성공하면 성공 코드 반환
        // DB에 Article 엔티티를 수정 실패하면 실패 코드 반환
        return (updated != null) ? ResponseEntity.status(200).body(updated)
                                : ResponseEntity.status(400).build();
    }
    // DELETE
    // 삭제할 글의 id를 전달 받아서 조회하여 삭제
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        // Service 클래스에 글 삭제 요청하여 삭제된 Article 엔티티를 반환 받음
        Article deleted = articleService.delete(id);

        return (deleted != null) ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // 트랜잭션
    // 작업을 수행할 때 모든 작업을 성공하지 못하고 중간에 실패한다면, 수행했던 모든 작업을 롤백함
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleForm> dtos){
        List<Article> createdList = articleService.createArticle(dtos);

        return (createdList != null) ? ResponseEntity.status(HttpStatus.OK).body(createdList)
                                    : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
