package com.study.springjpa.repository;

import org.springframework.beans.factory.annotation.Value;

public interface UsernameOnly {

    //@Value("#{target.username + ' ' + traget.age}") // open projection // 멤버 엔티티를 다가져와서 원하는 결과를 조합한다
    // close projection // 정확히 matching 되는 데이터 가져온다 // query도 정확한 값만 가져온다(최적화)
    String getUsername();
}
