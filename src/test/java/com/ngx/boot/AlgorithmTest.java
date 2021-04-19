package com.ngx.boot;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.bean.StuBorrow;
import com.ngx.boot.service.BookInfoService;
import com.ngx.boot.service.StuBorrowService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@SpringBootTest
@Slf4j
public class AlgorithmTest {

    @Autowired
    private StuBorrowService stuBorrowService;

    @Autowired
    private BookInfoService bookInfoService;

    @Test
    public void generateFile() throws IOException {

        List<StuBorrow> list = stuBorrowService.list();
        BufferedWriter bw = new BufferedWriter(new FileWriter("src/main/resources/bookComment.txt"));
        list.forEach(item->{
            String str = item.getStuNo()+"\t"+item.getBookNo()+"\t"+(int)item.getBorTime();
            try {
                bw.write(str);
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
        bw.close();
        System.out.println("ok");

    }

    @Test
//    @Transactional
    public void testUpdateWrapper(){
        UpdateWrapper<BookInfo> wrapper = new UpdateWrapper<>();
        wrapper.eq("book_no",5844);
        wrapper.set("book_comment",1);
        boolean update = bookInfoService.update(wrapper);
        System.out.println(update);
    }
}
