//1. 이 클래스가 속한 패키지(디렉터리 경로)를 선언하는 구문입니다.
//   패키지명은 실제 폴더 구조(com/example/firstproject)와 일치해야 컴파일이 정상적으로 됩니다.
package com.example.firstproject;

//2. SpringApplication 클래스를 임포트합니다.
//   SpringApplication은 스프링 부트 애플리케이션을 실행(구동)시키는 역할을 하는 클래스입니다.
import org.springframework.boot.SpringApplication;

//3. @SpringBootApplication 어노테이션을 사용하기 위해 임포트합니다.
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
참고. @SpringBootApplication 어노테이션의 역할
-----------------------------------------------
이 어노테이션 하나에는 사실 3개의 어노테이션이 합쳐져 있습니다.

1) @Configuration   : 이 클래스가 설정(환경설정) 정보를 담고 있는 클래스임을 표시
2) @EnableAutoConfiguration : 클래스패스에 등록된 라이브러리 목록을 스캔해서
                              필요한 설정(예: 내장 톰캣, JSON 변환기 등)을 자동으로 구성
3) @ComponentScan  : 현재 패키지(com.example.firstproject)와 하위 패키지를 모두 검색해서
                     @Controller, @Service, @Repository 같은 어노테이션이 붙은
                     클래스들을 찾아 자동으로 객체(빈, Bean)로 등록

즉, 이 어노테이션이 붙은 클래스가 스프링 부트 프로젝트의 "출발점"이 됩니다.
*/
//4. 이 클래스를 스프링 부트 애플리케이션의 메인 설정 클래스로 지정하는 어노테이션입니다.
@SpringBootApplication
//5. 클래스 선언부입니다. 클래스명은 FirstprojectApplication이며, 프로젝트명(firstproject)에서 자동 생성된 이름입니다.
public class FirstprojectApplication {

	//6. main() 메소드 : 자바 프로그램이 실행될 때 JVM(자바 가상 머신)이 가장 먼저 호출하는 진입점(entry point) 메소드입니다.
	//   접근제어자 public, 반환형 없음(void), 정적 메소드(static)로 선언되어 있어야
	//   객체 생성 없이 바로 실행할 수 있습니다.
	//   매개변수 String[] args는 프로그램 실행 시 외부에서 전달되는 명령행 인자(argument) 배열입니다.
	public static void main(String[] args) {

		//7. SpringApplication.run() 메소드를 호출해서 내장 톰캣 서버를 기동시키고
		//   스프링 컨테이너(모든 빈 객체를 관리하는 공간)를 초기화합니다.
		//   첫 번째 매개변수 FirstprojectApplication.class는 "어느 클래스를 기준으로 설정을 읽을지" 지정하는 것이고,
		//   두 번째 매개변수 args는 main() 메소드가 전달받은 명령행 인자를 그대로 넘겨주는 것입니다.
		SpringApplication.run(FirstprojectApplication.class, args);
		//요약: 이 한 줄이 실행되면 내장 톰캣이 켜지고, application.properties에 설정된 포트 번호로
		//      HTTP 요청을 받을 준비가 완료됩니다.
	}

}
