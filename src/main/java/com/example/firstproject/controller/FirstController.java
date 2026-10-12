//1. 이 클래스가 속한 패키지를 선언합니다. controller 패키지는 클라이언트의 HTTP 요청을 받아
//   처리를 담당하는 클래스들을 모아두는 폴더입니다.
package com.example.firstproject.controller;

//2. @Controller 어노테이션을 사용하기 위해 임포트합니다.
import org.springframework.stereotype.Controller;
//3. Model 클래스를 사용하기 위해 임포트합니다. Model은 컨트롤러에서 뷰(화면) 파일로
//   데이터를 전달할 때 사용하는 객체입니다.
import org.springframework.ui.Model;
//4. @GetMapping 어노테이션을 사용하기 위해 임포트합니다. GET 방식 HTTP 요청과
//   자바 메소드를 연결(매핑)할 때 사용합니다.
import org.springframework.web.bind.annotation.GetMapping;

/*
참고. @Controller 어노테이션의 역할
-----------------------------------
1) 이 어노테이션이 붙은 클래스는 "컨트롤러 계층"에 속한다는 것을 스프링에게 알립니다.
2) 스프링 부트가 실행될 때 @ComponentScan 기능이 이 클래스를 자동으로 감지해서
   객체(빈, Bean)로 생성한 뒤 스프링 컨테이너라는 메모리 공간에 등록해 둡니다.
3) 이후 클라이언트(웹 브라우저)의 요청 URL이 들어오면, 톰캣(내장 서버)이
   이 클래스 안에서 URL과 일치하는 메소드를 찾아 실행시킵니다.
*/
//5. 이 클래스가 컨트롤러 역할을 한다는 것을 선언하는 어노테이션입니다.
@Controller
//6. 클래스 선언부입니다. 클래스명 FirstController.
public class FirstController {

    //7. 반환형이 String(문자열)인 niceToMeetYou() 메소드를 선언합니다.
    //   이 메소드가 반환하는 문자열은 "다음에 보여줄 뷰(화면) 파일의 이름"으로 해석됩니다.

    //8. 클라이언트가 요청할 URL 주소 : http://localhost:8070/hi
    //9. @GetMapping("/hi") : "/hi" 라는 경로로 GET 방식 요청이 들어오면
    //   바로 아래에 있는 niceToMeetYou() 메소드를 실행하라는 매핑(연결) 설정입니다.
    @GetMapping("/hi")
    //10. 매개변수 Model model : 톰캣(내장 서버)이 요청을 처리할 때 자동으로 생성해서 넘겨주는 객체입니다.
    //    이 객체 안에 담아둔 값은 뷰 파일(mustache)에서 꺼내 쓸 수 있습니다.
    public String niceToMeetYou(Model model){

        //11. model.addAttribute(변수명, 변수값) 메소드 호출
        //    "username"이라는 이름(키, key)에 "hongpark"이라는 문자열 값을 저장(바인딩)합니다.
        //    이렇게 저장한 값은 뷰 파일에서 {{username}} 형식으로 꺼내 쓸 수 있습니다.
        model.addAttribute("username","hongpark");

        //12. return "greetings"; 문장의 의미
        //    문자열 "greetings"를 반환하면, 톰캣서버는 templates 디렉터리에서
        //    "greetings.mustache" 라는 이름의 파일을 찾아 그 내용을 클라이언트(브라우저)에게 전송합니다.
        //    즉, 이 반환값은 실제 파일명이 아니라 확장자(.mustache)를 뺀 "논리적인 뷰 이름"입니다.
        return "greetings";
    }
    /*
    참고. /hi 요청의 전체 실행 흐름
    --------------------------------
    [브라우저] http://localhost:8070/hi 주소 입력 후 Enter (HTTP GET 요청 전송)
         |
         v
    [톰캣서버] "/hi" 경로와 매핑된 niceToMeetYou() 메소드를 검색해서 호출
         |
         v
    [niceToMeetYou 메소드] Model 객체에 username="hongpark" 값을 바인딩
         |
         v
    [return "greetings"]  templates/greetings.mustache 파일을 찾도록 톰캣서버에 지시
         |
         v
    [greetings.mustache]  {{username}} 위치에 "hongpark" 값을 대입해서 최종 HTML 생성
         |
         v
    [브라우저] 완성된 HTML을 응답받아 화면에 렌더링(출력)
    */

    //13. 반환형이 String인 seeYouNext() 메소드를 선언합니다.
    //14. 클라이언트가 요청할 URL 주소 : http://localhost:8070/bye
    //15. @GetMapping("/bye") : "/bye" 경로로 GET 요청이 들어오면 아래 메소드를 실행하라는 매핑입니다.
    @GetMapping("/bye")
    public String seeYouNext(Model model){

        //16. Model 객체에 "nickname"이라는 키로 "홍길동"이라는 문자열 값을 바인딩합니다.
        //    goodbye.mustache 파일에서 {{nickname}} 형식으로 이 값을 꺼내 씁니다.
        model.addAttribute("nickname", "홍길동");

        //17. 문자열 "goodbye"를 반환합니다.
        //    이 값은 templates 디렉터리 안의 "goodbye.mustache" 파일을 가리키는 논리적인 뷰 이름입니다.
        return "goodbye";
        //요약: niceToMeetYou()와 seeYouNext() 두 메소드 모두 "Model에 값 저장 → 뷰 이름 반환"이라는
        //      동일한 패턴을 따릅니다. 이 패턴이 스프링 MVC(Model-View-Controller)의 기본 구조입니다.
    }

}
