package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity                 // 해당 클래스가 엔티티 클래스임을 선언. 클래스 변수를 바탕으로 DB에 테이블 생성
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class Comment {
    @Id                                                 // id 열을 기본키로 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB에 행을 추가할 때 자동으로 값 추가
    private Long id;            // 댓글 db 아이디

    @ManyToOne                      // 댓글 엔티티와 게시글 엔티티를 다대일 관계로 설정
    @JoinColumn(name="article_id")  // 외래키 열 이름 지정: DB에는 Article 객체 대신 그 기본키 값이 article_id 열에 저장됨
    private Article article;    // 부모 글 정보

    @Column                         // 테이블에 해당 변수에 해당하는 열 생성
    private String nickname;    // 댓글 작성자
    @Column                         // 테이블에 해당 변수에 해당하는 열 생성
    private String body;        // 댓글 내용

    // dto와 부모 게시글을 전달 받아서 새 댓글 엔티티를 생성해서 반환하는 메소드
    public static Comment createComment(CommentDto dto, Article article) {
        // 예외처리
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글 아이디가 입력되었습니다.");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 부모 게시글의 id가 일치하지 않습니다.");

        // 엔티티 생성 및 반환
        return new Comment(null, article, dto.getNickname(), dto.getBody());
    }
}
