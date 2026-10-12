//1. 이 테스트 클래스가 속한 패키지를 선언합니다.
//   테스트 코드는 실제 실행 코드(src/main)와는 별도로 src/test 디렉터리에 분리되어 관리됩니다.
package com.example.firstproject;

//2. JUnit 라이브러리의 @Test 어노테이션을 임포트합니다.
//   JUnit은 자바 코드를 자동으로 검증(테스트)할 때 가장 널리 사용하는 테스트 프레임워크입니다.
import org.junit.jupiter.api.Test;
//3. 스프링 부트 테스트 환경을 구성하기 위한 @SpringBootTest 어노테이션을 임포트합니다.
import org.springframework.boot.test.context.SpringBootTest;

//4. @SpringBootTest 어노테이션 : 이 클래스를 실행할 때 실제 스프링 컨테이너(애플리케이션 컨텍스트)를
//   통째로 띄워서 테스트하겠다는 설정입니다. 즉, 애플리케이션을 실행했을 때와 거의 동일한 환경에서
//   테스트가 진행됩니다.
@SpringBootTest
//5. 클래스 선언부입니다. 클래스명 FirstprojectApplicationTests.
//   접근제어자가 생략되어 있으면 default(패키지 내부에서만 접근 가능) 접근 범위를 가집니다.
class FirstprojectApplicationTests {

	//6. @Test 어노테이션 : 이 메소드가 "테스트 케이스"임을 JUnit에게 알려주는 표시입니다.
	//   테스트를 실행하면 JUnit이 이 어노테이션이 붙은 메소드들을 찾아 자동으로 실행합니다.
	@Test
	//7. 반환형이 void(반환값 없음)인 contextLoads() 메소드를 선언합니다.
	void contextLoads() {
		//8. 메소드 몸체가 비어 있습니다.
		//   이 테스트의 목적은 "스프링 컨테이너(애플리케이션 컨텍스트)가 오류 없이 정상적으로
		//   로딩(구동)되는지"만 확인하는 것입니다. 별도의 검증 코드(assert문)가 없어도,
		//   컨텍스트를 띄우는 과정에서 예외(에러)가 발생하지 않으면 테스트는 성공(pass)으로 처리됩니다.
	}
	//요약: 이 클래스는 실제 기능을 검증하는 것이 아니라, "프로젝트 설정에 문제가 없어서
	//      애플리케이션이 정상적으로 뜰 수 있는가"를 확인하는 기본 테스트입니다.

}
