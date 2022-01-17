package com.study.springjpa.repository;

import com.study.springjpa.dto.MemberDto;
import com.study.springjpa.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

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

    // @Modifying, executeUpdate 실행, 이 어노테이션이 없으면 getResultList나 getSingleResult를 실행한다
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query("update Member m set m.age = m.age+1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);


    // fetch 조인
    @EntityGraph(attributePaths = {"team"})
    @Query("select m from Member m")
    List<Member> findFetchAll();

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly",value = "true"))
    Member findReadOnlyByUsername(String username);


    // 락을 걸 수 있다 // select for update
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUsername(String username);

    // projection
    List<UsernameOnly> findProjectionByUsername(String username);

    // native query
    @Query(value = "select * from member where username = ?",nativeQuery = true)
    Member findByNativeQuery(String name);

    // native query + projection
    @Query(value = "select m.member_id as id, m.username, t.name as teamName from member m left join team t ",
            countQuery = "select count(*) from member",nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
