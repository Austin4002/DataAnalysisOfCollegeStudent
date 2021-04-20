package com.ngx.boot;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.service.BookInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : 朱坤
 * @date :
 */
@SpringBootTest
@Slf4j
public class AlgorithmTest {

    @Autowired
    private BookInfoService bookInfoService;



    @Test
    @Transactional
    public void testUpdateWrapper(){
        UpdateWrapper<BookInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("book_no",5844);
        wrapper.set("recommend",1);
        boolean update = bookInfoService.update(wrapper);
        System.out.println(update);
    }

}
