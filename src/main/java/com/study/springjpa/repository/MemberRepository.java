package com.study.springjpa.repository;

import com.study.springjpa.dto.MemberDto;
import com.study.springjpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

    @Query("select m from Member m where m.username = :username and m.age = :age")
    List<Member> findUser(@Param("username") String username, @Param("age") int age);

    @Query("select m.username from Member m")
    List<String> findAllUserNameList();

    @Query("select new com.study.springjpa.dto.MemberDto(m.id, m.username,t.name) from Member m join m.team t")
    List<MemberDto> findAllMemberDto();

    // collection 도 바인딩 된다
    @Query("select m from Member m where m.username in :names")
    List<Member> findByNames(@Param("names") List<String> names);

    // 반환타입 컬렉션, 단건, Optional 가능하다 // 컬렉션은 값이 없으면 빈 컬렉션을 반환한다 // 단건인 경우에는 없으면 null 이다
    // 단건 조회에서 단건을 생각했는데 2개이상의 데이터가 조회된 경우에는 NonUniqueResultException (jpa) -> IncorrectResultSizeDataAccessException (spring) 이 터진다
    Member findByUsername(String username);

    // 페이징과 정렬

    Page<Member> findPageByAge(int age, Pageable pageable);
    // SLICE
    Slice<Member> findSliceByAge(int age, Pageable pageable);

    // count 쿼리 분리
    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m.username) from Member m")
    Page<Member> findPageWithCountByAge(int age, Pageable pageable);

}
