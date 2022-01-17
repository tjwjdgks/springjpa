package com.study.springjpa;

import org.hibernate.annotations.BatchSize;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;
import java.util.UUID;

@EnableJpaAuditing // spring data jpa auditing 기능
@SpringBootApplication
public class SpringjpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringjpaApplication.class, args);
    }

    // 실제로는 authentication 정보를 꺼내서 user 정보를 넣어야 한다 // 생성자, 수정자
    // 등록, 수정마다  auditorProvider 호출해서 값을 채워 넣어준다
    @Bean
    public AuditorAware<String> auditorProvider(){
        return ()-> Optional.of(UUID.randomUUID().toString());
    }
}
