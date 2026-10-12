package com.example.firstproject.service;

import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Coffee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CoffeeServiceTest {
    // 스프링부트에서 객체를 생성하여 주입
    @Autowired
    CoffeeService coffeeService;

    // 해당 코드가 테스트 코드임을 선언
    @Test
    void index() {
        // 전체 조회 테스트
        // data.sql의 모든 데이터가 조회되는지 확인
        Coffee a = new Coffee(1L, "아메리카노", "4500");
        Coffee b = new Coffee(2L, "라떼", "5000");
        Coffee c = new Coffee(3L, "카페 모카", "5500");

        List<Coffee> expected = new ArrayList<Coffee>(Arrays.asList(a, b, c));

        List<Coffee> coffees = coffeeService.index();

        // assertEquals("예상 데이터", "실제 데이터")
        // 두 데이터가 같은지 비교
        assertEquals(expected.toString(), coffees.toString());

    }
}