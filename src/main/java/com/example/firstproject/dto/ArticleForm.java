package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor     // 생성자 자동 생성: 모든 전역변수에 대한 매개변수를 전달 받는 생성자 생성
@ToString               // toString() 자동 생성: 변수=값 형태의 문자열 반환
public class ArticleForm {
    // 글 번호, 제목, 내용을 저장할 변수
    private Long id;
    private String title;
    private String content;

    // 새로운 Article 객체를 생성해서 반환하는 메소드
    public Article toEntity() {
        return new Article(id, title, content);
    }
}
