package com.study.springjpa.repository;

import com.study.springjpa.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

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
}
