package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

// Article 클래스가 Entity 역할임을 선언하기 위한 어노테이션
// @Entity: JPA 프레임워크에서 제공하는 어노테이션
// 이 어노테이션이 붙은 클래스를 기반으로 DB에 테이블이 자동으로 생성됨
@Entity
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter             // Getter: getter 역할을 하는 메소드 자동 생성
public class Article {
    // @Id: 해당 Entity의 대표값(기본키 - Primary Key)
    // @GeneratedValue: 숫자가 자동으로 생성되서 추가되는 기능. 기본값은 1씩 증가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // title 열 생성
    @Column
    private String title;

    // content 열 생성
    @Column
    private String content;

    // article 객체의 값을 전체가 아니라 일부분만 수정하기 위한 값이 전달될 경우 이를 처리하기 위한 메소드
    public void patch(Article article) {
        if(article.title != null)
            this.title = article.title;
        if(article.content != null)
            this.content = article.content;
    }
}
