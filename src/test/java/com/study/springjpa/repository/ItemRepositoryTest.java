package com.study.springjpa.repository;

import com.study.springjpa.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ItemRepositoryTest {
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void save(){
        Item item= new Item("testA");
        itemRepository.save(item);
    }
}