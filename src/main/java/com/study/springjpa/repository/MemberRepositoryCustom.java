package com.study.springjpa.repository;

import com.study.springjpa.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {

    List<Member> findMemberCustom();
}
