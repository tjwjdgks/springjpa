package com.study.springjpa.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

// 순수 jpa
@Getter
@MappedSuperclass
public class JpaBaseEntity {

    @Column(updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;

    @PrePersist // persist하기 전에 이벤트 발생
    public void prePersist(){
        LocalDateTime now = LocalDateTime.now();
        createDate = now;
        updatedDate = now;
    }
    @PreUpdate
    public void preUpdate(){
        updatedDate = LocalDateTime.now();
    }

}
