package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Service: 해당 클래스를 서비스 계층 클래스로 스프링 환경에 등록
//          톰캣 서버가 시작할 때 ArticleService 클래스에 대한 객체가 하나 생성됨
//          ArticleApiController 내부 @Autowired 어노테이션을 통해 자동으로 등록됨
@Service
@Slf4j
public class ArticleService {
    // Spring Data JPA가 만든 ArticleRepository 인터페이스 구현 자식 객체를 자동 생성
    @Autowired
    private ArticleRepository articleRepository;


    // DB에서 모든 게시글을 조회하여 반환
    public List<Article> index() {
        return articleRepository.findAll();
    }

    // DB에서 특정 ID 게시글을 조회하여 반환
    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    // 입력받은 데이터를 통해 DB에 행 추가
    public Article create(ArticleForm dto) {
        // 전달 받은 객체를 통해 Article 엔티티 생성
        Article article = dto.toEntity();

        // 새 글 요청에 id가 있다면 save() 메소드가 같은 id의 기존 글을 덮어 쓰는 update 기능을 수행함
        // 그러므로 null을 반환해서 새로운 글 생성을 거부
        if(article.getId() != null){
            return null;
        }

        // DB에 Article 엔티티 삽입
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        // 수정할 내용이 담긴 Article 엔티티 객체 생성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());

        // 수정 요청 받은 id를 통해 수정할 Article 엔티티 객체 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 목표 객체가 없거나 요청 받은 id와 엔티티 객체의 id가 다를 경우
        if(target == null || id != article.getId()){
            // 잘못된 요청이므로 처리 실패
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }

        // 전달 받은 article 객체를 db에 저장
        // target 변수에 저장된 글 수정시 입력한 일부 정보를 patch 메소드를 호출 할 때 전달해서
        // 수정할 정보를 완성
        target.patch(article);
        Article updated = articleRepository.save(target);


        // 객체를 저장했다면 처리 성공
        // 객체 정보를 json 타입으로 변경하여 반환
        return updated;
    }

    public Article delete(Long id) {
        // 삭제할 대상 글 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 요청 URL을 잘못 입력했을 경우 처리
        if(target == null){
            // 삭제하지 않고 null 반환
            return null;
        }

        // db에서 삭제 대상 행 삭제
        articleRepository.delete(target);

        // 요청 처리 성공 응답
        // DB에서 삭제한 Article 엔티티를 반환
        return target;
    }

    @Transactional
    public List<Article> createArticle(List<ArticleForm> dtos) {
        // ArticleForm 배열을 Stream으로 변환하여 각 요소를 엔티티로 변환하여 리스트에 저장
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).toList();

        // 엔티티 배열을 순회하여 각 요소를 DB에 추가
        articleList.stream().forEach(article -> articleRepository.save(article));

        // 강제 예외 발생 시키기
        // 트랜잭셔널 어노테이션에 의해 예외가 발생하면 이전 실행 내용이 모두 롤백됨
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결제 실패"));

        return articleList;
    }
}
