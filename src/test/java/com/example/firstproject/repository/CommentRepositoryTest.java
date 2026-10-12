package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest    // Jpa, DB 관련 구성(엔티티, 레포지토리, H2 DB, data.sql)만 구동해서 테스트를 도와주는 어노테이션
                // 각 테스트가 끝나면 DB 변경 사항을 자동으로 롤백함
class CommentRepositoryTest {
    @Autowired  // 자동으로 자식 구현 객체를 생성해서 주입해주는 어노테이션
    CommentRepository commentRepository;

    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회")    // 테스트 결과 창에 테스트 이름 설정
    void findByArticleId() {
        {
            // 테스트 유형 1. id가 4번인 부모 게시글의 모든 댓글 조회
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            Article article = new Article(4L, "당신의 인생 영화는?", "댓글 고");
            Comment a = new Comment(1L, article, "Park", "굿 윌 헌팅");
            Comment b = new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c = new Comment(3L, article, "Choi", "쇼생크 탈출");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글 출력!");
        }
        {
            // 테스트 유형 2. id가 1번인 부모 게시글의 모든 댓글 조회
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();

            // 비교 및 검증
            assertEquals(expected, comments, "1번 글의 모든 댓글 출력!");
        }
        {
            // 테스트 유형 3. 9번 게시글의 모든 댓글 조회
            // 입력 데이터 준비
            Long articleId = 9L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            List<Comment> expected = Arrays.asList();

            // 비교 및 검증
            assertEquals(expected, comments, "9번 글의 모든 댓글 출력!");
        }
        {
            // 테스트 유형 4. 999번 게시글의 모든 댓글 조회
            // 입력 데이터 준비
            Long articleId = 999L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            List<Comment> expected = Arrays.asList();

            // 비교 및 검증
            assertEquals(expected, comments, "999번 글의 모든 댓글 출력!");
        }
        {
            // 테스트 유형 5. -1번 게시글의 모든 댓글 조회
            // 입력 데이터 준비
            Long articleId = -1L;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상 데이터
            List<Comment> expected = Arrays.asList();

            // 비교 및 검증
            assertEquals(expected, comments, "-1번 글의 모든 댓글 출력!");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        {
            // 테스트 유형 1. "Park"의 모든 댓글 조회
            // 입력 데이터
            String nickname = "Park";

            // 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상 데이터
            Comment a = new Comment(1L, new Article(4L, "당신의 인생 영화는?", "댓글 고"), nickname, "굿 윌 헌팅");
            Comment b = new Comment(4L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickname, "치킨");
            Comment c = new Comment(7L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "조깅");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글 출력");
        }
        {
            // 테스트 유형 2. "Kim"의 모든 댓글 조회
            // 입력 데이터
            String nickname = "Kim";

            // 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상 데이터
            Comment a = new Comment(2L, new Article(4L, "당신의 인생 영화는?", "댓글 고"), nickname, "아이 엠 샘");
            Comment b = new Comment(5L, new Article(5L, "당신의 소울 푸드는?", "댓글 고고"), nickname, "샤브샤브");
            Comment c = new Comment(8L, new Article(6L, "당신의 취미는?", "댓글 고고고"), nickname, "유튜브 시청");
            List<Comment> expected = Arrays.asList(a, b, c);

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "Kim의 모든 댓글 출력");
        }
        {
            // 테스트 유형 3. null의 모든 댓글 조회
            // 입력 데이터
            String nickname = null;

            // 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상 데이터
            List<Comment> expected = Arrays.asList();

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "null의 모든 댓글 출력");
        }
        {
            // 테스트 유형 4. ""의 모든 댓글 조회
            // 입력 데이터
            String nickname = "";

            // 실제 데이터
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상 데이터
            List<Comment> expected = Arrays.asList();

            // 비교 및 검증
            assertEquals(expected.toString(), comments.toString(), "\"\"의 모든 댓글 출력");
        }
    }
}