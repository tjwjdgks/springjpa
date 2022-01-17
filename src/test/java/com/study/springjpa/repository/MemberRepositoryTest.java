package com.study.springjpa.repository;

import com.study.springjpa.dto.MemberDto;
import com.study.springjpa.entity.Member;
import com.study.springjpa.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Test
    public void testMember(){
        Member member = new Member("memberA");
        Member save = memberRepository.save(member);

        Member findMember = memberRepository.findById(save.getId()).get();

        assertEquals(findMember.getId(),save.getId());
        assertEquals(findMember.getUsername(), save.getUsername());
        assertEquals(findMember,save);
    }
    @Test
    public void findByUsernameAndAgeGreaterThan(){
        Member m1= new Member("AAA",10);
        Member m2= new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> find = memberRepository.findByUsernameAndAgeGreaterThan("AAA",15);

        assertEquals(find.size(),1);
        assertEquals(find.get(0).getUsername(),"AAA");
        assertEquals(find.get(0).getAge(),20);
    }
    @Test
    public void findUser(){
        Member m1= new Member("AAA",10);
        Member m2= new Member("AAA",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> find = memberRepository.findUser("AAA",10);

        assertEquals(find.size(),1);
        assertEquals(find.get(0).getUsername(),"AAA");
        assertEquals(find.get(0).getAge(),10);
    }
    @Test
    public void findUserAndTeam(){
        Team t1 = new Team("BBB");
        teamRepository.save(t1);

        Member m1= new Member("AAA",10);
        memberRepository.save(m1);
        m1.setTeam(t1);

        List<MemberDto> find = memberRepository.findAllMemberDto();

        assertEquals(find.size(),1);
        assertEquals(find.get(0).getUsername(),"AAA");
        assertEquals(find.get(0).getTeamName(),"BBB");
    }

    @Test
    public void findByNames(){
        Member m1= new Member("AAA",10);
        Member m2= new Member("BBB",20);
        memberRepository.save(m1);
        memberRepository.save(m2);

        List<Member> find = memberRepository.findByNames(List.of("AAA","BBB"));

        assertEquals(find.size(),2);
    }

    @Test
    public void page(){
        // given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));
        memberRepository.save(new Member("member6",10));
        memberRepository.save(new Member("member7",10));

        int age = 10;
        int offset =0;
        int limit = 3;
        // spring data jpa page 0 부터 시작
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Page<Member> page = memberRepository.findPageByAge(age,pageRequest);
        // page<Entity> 쉽게 Page<Dto>로 변경하는 방법
        Page<MemberDto> map = page.map(member -> new MemberDto(member.getId(), member.getUsername(), null));

        // then
        List<Member> content = page.getContent();
        long totalElements = page.getTotalElements();

        assertEquals(content.size(),3);
        assertEquals(totalElements,7);
        // page index 이다
        assertEquals(page.getNumber(),0);
        assertEquals(page.getTotalPages(),3);
        assertEquals(page.isFirst(),true);
        assertEquals(page.hasNext(),true);


    }
    @Test
    public void slice(){
        // given
        memberRepository.save(new Member("member1",10));
        memberRepository.save(new Member("member2",10));
        memberRepository.save(new Member("member3",10));
        memberRepository.save(new Member("member4",10));
        memberRepository.save(new Member("member5",10));
        memberRepository.save(new Member("member6",10));
        memberRepository.save(new Member("member7",10));

        int age = 10;
        int offset =0;
        int limit = 3;
        // spring data jpa page 0 부터 시작
        PageRequest pageRequest = PageRequest.of(offset, limit, Sort.by(Sort.Direction.DESC, "username"));

        // when
        Slice<Member> page = memberRepository.findSliceByAge(age,pageRequest);
        // then
        List<Member> content = page.getContent();
        long totalElements = page.getNumberOfElements();

        assertEquals(content.size(),3);
        assertEquals(totalElements,3);
        // page index 이다
        assertEquals(page.getNumber(),0);
        assertEquals(page.getSize(),3);
        assertEquals(page.isFirst(),true);
        assertEquals(page.hasNext(),true);


    }

}
