package com.study.springjpa.repository;

public class UsernameOnlyDto {

    private final String username;

    // 파라미터 명을 entity와 매칭 // 결과가 proxy 클래스 아닌 실제 클래스로 생성된다
   public UsernameOnlyDto(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
