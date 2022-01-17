package com.study.springjpa.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDateTime;

// generate value 못쓸 경우
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item implements Persistable<String>{

    @Id
    private String id;

    @CreatedDate
    LocalDateTime createDate;

    public Item(String id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return createDate==null;
    }
}
