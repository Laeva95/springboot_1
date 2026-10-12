package com.example.firstproject.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController // 이 클래스를 Rest 컨트롤러 기능의 객체로 생성해서 스프링 컨테이너 공간에 등록
public class FirstApiController {
    @GetMapping("/api/hello")
    public String hello(){
        return "hello world";
    }
// @RestController: @ResponseBody + @Controller 두 기능을 합친 어노테이션
// @ResponseBody: 해당 어노테이션이 붙은 메소드의 return 값만 브라우저로 보내서 응답 할 수 있음

}
