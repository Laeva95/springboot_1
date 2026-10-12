package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ArticleServiceTest {
    // 스프링부트에서 객체를 생성하여 주입
    @Autowired
    ArticleService articleService;

    // 해당 코드가 테스트 코드임을 선언
    @Test
    void index() {
        // 전체 조회 테스트
        // data.sql의 모든 데이터가 조회되는지 확인
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");

        List<Article> expected = new ArrayList<Article>(Arrays.asList(a, b, c));

        List<Article> articles = articleService.index();

        // assertEquals("예상 데이터", "실제 데이터")
        // 두 데이터가 같은지 비교
        assertEquals(expected.toString(), articles.toString());

    }


    @Test
    void show_성공_존재하는_id_입력() {
        // 특정 행 조회 테스트
        // 예상 데이터
        Long id = 1L;   // 조회 할 게시글 id
        Article expected = new Article(id, "가가가가", "1111"); // 조회 할 게시글 결과 예상 데이터

        // 실제 DB 데이터
        Article article = articleService.show(id);                          // 실제로 조회 한 게시글 데이터

        // 예상과 실제 데이터 비교 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    void show_실패_존재하지_않는_id_입력() {
        // 특정 행 조회 테스트
        // 예상 데이터
        Long id = -1L;   // 조회 할 게시글 id
        Article expected = null; // 조회 할 게시글 결과 예상 데이터

        // 실제 DB 데이터
        Article article = articleService.show(id);                          // 실제로 조회 한 게시글 데이터

        // 예상과 실제 데이터 비교 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void create_성공_title와_content만_있는_dto_입력() {
        // 게시글 추가에 성공하는지 확인
        // 예상 데이터
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);
        Article expected = new Article(4L, title, content);

        // 실제 데이터
        Article article = articleService.create(dto);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void create_실패_id가_포함된_dto_입력() {
        // 예상 데이터
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 실제 데이터
        Article article = articleService.create(dto);

        // 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        // 예상 데이터
        Long id = 1L;
        String title = "가가가가";
        String content = "1111";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, content);

        // 실제 데이터
        Article article = articleService.update(id, dto);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력(){
        // 예상 데이터
        Long id = 1L;
        String title = "AAAA";
        String content = null;
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = new Article(id, title, "1111");

        // 실제 데이터
        Article article = articleService.update(id, dto);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());

    }

    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력(){
        // 예상 데이터
        Long id = -1L;
        String title = "가나다라";
        String content = "1234";
        ArticleForm dto = new ArticleForm(id, title, content);
        Article expected = null;

        // 실제 데이터
        Article article = articleService.update(id, dto);

        // 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        // 예상 데이터
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");

        // 실제 데이터
        Article article = articleService.delete(id);

        // 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }

    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력(){
        // 예상 데이터
        Long id = -1L;
        Article expected = null;

        // 실제 데이터
        Article article = articleService.delete(id);

        // 비교 및 검증
        assertEquals(expected, article);
    }

    @Test
    @Transactional
    void createArticle() {


    }
}