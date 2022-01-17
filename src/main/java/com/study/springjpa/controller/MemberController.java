package com.study.springjpa.controller;

import com.study.springjpa.dto.MemberDto;
import com.study.springjpa.entity.Member;
import com.study.springjpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    // 도메인 클래스 컨버터, pk로 조회해 회원 엔티티 객체를 반환한다
    // 트랜잭션이 없는 범위에서 엔티티를 조회했으므로, 엔티티를 변경해도 db에 반영되지 않는다
    @GetMapping("/members/{id}")
    public String findMember(@PathVariable(name = "id") Member member){
        return member.getUsername();
    }

    // page 기능
    // spring boot가 자동으로 세팅을 해준다 ex)  page=0&size=3&sort=id,desc&sort=username,desc // page = page 번호, size = page element 수,
    // PageableDefault 는 global setting 과 별개로 적용하고 싶을 때
    @GetMapping("/members")
    public Page<Member> list(@PageableDefault(size = 5, sort = "username") Pageable pageable){
        // 마지막 파리미터에 pageable 넣어주면 된다
        Page<Member> page = memberRepository.findAll(pageable);
        page.map(member -> new MemberDto(member.getId(),member.getUsername(),null));
        return page;
    }
    /*
    @PostConstruct
    public void init(){
        for(int i=0; i<100; i++){
            memberRepository.save(new Member("user"+i,i));
        }
    }
     */
}
