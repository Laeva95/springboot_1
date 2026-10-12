//1. 이 클래스가 속한 패키지를 선언합니다.
package com.example.firstproject.controller;

//2. @Controller 어노테이션을 사용하기 위해 임포트합니다.
import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.repository.CommentRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
//3. @GetMapping 어노테이션을 사용하기 위해 임포트합니다.
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

//4. 이 클래스가 컨트롤러 역할을 함을 선언하는 어노테이션입니다.
//   이 어노테이션이 붙으면 스프링이 자동으로 이 클래스의 객체(빈)를 생성해서 컨테이너에 등록합니다.
@Controller
@Slf4j
//5. 클래스 선언부입니다. 클래스명 ArticleController.
//   이름에서 알 수 있듯이, 게시글(Article) 관련 요청을 처리하는 컨트롤러입니다.
public class ArticleController {
    // @Autowired: 자동으로 스프링부트 내부의 익명 객체를 생성해서 전달
    // ArticleRepository 인터페이스를 implement 하여 구현한 자식 객체를 생성해서 변수에 저장
    // 의존성 주입 - 필드(변수) 주입
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;
    //6. 반환형이 String인 newArticleForm() 메소드를 선언합니다.

    //7. 클라이언트가 요청할 URL 주소 : http://localhost:8070/articles/new
    //   이 주소로 요청하면 "글쓰기 입력 화면"을 보여주는 것이 이 메소드의 목적입니다.
    //8. @GetMapping("/articles/new") : "/articles/new" 경로로 GET 요청이 들어오면
    //   아래 메소드를 실행하라는 매핑 설정입니다.
    @GetMapping("/articles/new")
    public String newArticleForm(){

        //9. 문자열 "articles/new"를 반환합니다.
        //   이 값은 templates 디렉터리 기준으로 "articles" 하위 폴더 안에 있는
        //   "new.mustache" 파일(경로: templates/articles/new.mustache)을 가리키는 논리적인 뷰 이름입니다.
        //   슬래시(/)는 실제 폴더 구조의 경로 구분자와 그대로 대응됩니다.
        return "articles/new";
        //요약: 이 메소드는 Model에 데이터를 담지 않고, 단순히 입력 폼(form) 화면만 보여주는
        //      역할만 수행합니다. 사용자가 폼에 입력한 데이터를 서버로 보내는 처리(등록, 저장)는
        //      이 메소드가 아니라 별도의 POST 요청을 처리하는 메소드에서 담당해야 합니다.
    }
    // 글쓰기 form 데이터 전송 처리
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
//        // 전달 받은 dto 내용을 콘솔에 출력
//        System.out.println(form.toString());
        log.info(form.toString());

        // dto를 entity로 변환
        // 매개 변수 form의 toEntity() 메소드를 호출하여 Article 객체 반환
        Article article = form.toEntity();
//        System.out.println(article.toString());
        log.info(article.toString());

        // repository 인터페이스를 구현한 자식 객체를 사용해서 entity의 정보를 db에 저장
        // save(): insert into article(id, title, content) values(?, ?, ?);
        Article saved = articleRepository.save(article);
//        System.out.println(saved.toString());
        log.info(saved.toString());
        return "redirect:/articles/" + saved.getId();
    }

    // @PathVariable: @GetMapping의 {id} 변수에 들어오는 값을 매개변수로 연결
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model){
        // 클라이언트가 조회 요청한 글의 id
        log.info("id = " + id);

        // 글 id를 통해 데이터 하나 조회
        // findById(id): 매개변수 id를 통해 글 하나의 정보 조회
        // orElse(null): 조회한 글이 없으면 null 값 반환
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 부모글 id를 통해 댓글 목록 가져오기
        List<CommentDto> commentsDtos = commentService.comments(id);

        // 조회한 글 하나의 정보를 view 페이지에 보여주기 위해 model 객체에 전달
        model.addAttribute("article", articleEntity);

        // 부모글 id로 조회한 댓글 목록을 model 객체에 전달
        model.addAttribute("commentsDtos", commentsDtos);

        // view 페이지가 위치한 경로를 설정
        return "articles/show";
    }
    @GetMapping("/articles")
    public String index(Model model){
        // 모든 글 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();

        // Model 객체에 데이터 전달
        model.addAttribute("articleList", articleEntityList);

        // view 페이지가 위치한 경로를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        // 수정할 데이터 db에서 조회하여 가져오기 가져오기
        // id가 일치하는 행을 찾아서 조회하여 반환
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델 객체에 수정을 위해 조회한 글 정보 등록
        model.addAttribute("article", articleEntity);

        // 수정을 위해 조회한 글 정보를 보여주면서 수정을 위해 입력하는 화면 경로를 설정
        return "articles/edit";
    }

    @PostMapping(   "/articles/update")    // edit에서 입력한 글 제목, 내용을 request 객체에서 꺼내어 ArticleForm 객체에 저장
    public String update(ArticleForm form){
        // DTO를 엔티티로 변환
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());

        // 엔티티를 DB에 저장
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 해당 id를 가진 행에서 성공적으로 엔티티를 가져왔는지 확인
        if(target != null){
            articleRepository.save(articleEntity);
        }

        // 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    // RedirectAttribute: 리다이렉트시 전달될 객체 매개변수
    // 스프링부트 프레임워크에서 리다이렉트 시점에만 사용 할 수 있는 key:value 형태의 데이터 저장 가능
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        // 전달 받은 id 값을 통해 id가 일치하는 행을 엔티티로 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());

        // 찾은 행이 null이 아니라면
        if(target != null) {
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다.");
        }

        return "redirect:/articles";
    }
}
