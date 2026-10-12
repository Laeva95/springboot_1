package com.example.firstproject.controller;

import com.example.firstproject.dto.MemberForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Member;
import com.example.firstproject.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class MemberController {
    @Autowired
    MemberRepository memberRepository;
    @GetMapping("/members/signup")
    public String signUpPage(){
        return "members/new";
    }

    @PostMapping("/members/join")
    public String join(MemberForm form){
        System.out.println(form.toString());

        Member member = form.toEntity();
        System.out.println(member.toString());

        Member saved = memberRepository.save(member);
        System.out.println(saved.toString());
        return "redirect:/members/" + saved.getId();
    }
    // @PathVariable: @GetMapping의 {id} 변수에 들어오는 값을 매개변수로 연결
    @GetMapping("/members/{id}")
    public String show(@PathVariable Long id, Model model){
        // 클라이언트가 조회 요청한 글의 id
        log.info("id = " + id);

        // 글 id를 통해 데이터 하나 조회
        // findById(id): 매개변수 id를 통해 글 하나의 정보 조회
        // orElse(null): 조회한 글이 없으면 null 값 반환
        Member memberEntity = memberRepository.findById(id).orElse(null);

        // 조회한 글 하나의 정보를 view 페이지에 보여주기 위해 model 객체에 전달
        model.addAttribute("member", memberEntity);

        // view 페이지가 위치한 경로를 설정
        return "members/show";
    }
    @GetMapping("/members")
    public String index(Model model){
        // 모든 글 데이터 가져오기
        List<Member> memberEntityList = memberRepository.findAll();

        // Model 객체에 데이터 전달
        model.addAttribute("memberList", memberEntityList);

        // view 페이지가 위치한 경로를 설정
        return "members/index";
    }
}
