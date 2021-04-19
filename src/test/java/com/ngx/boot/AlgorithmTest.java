package com.ngx.boot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ngx.boot.bean.StuBorrow;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.service.StuBorrowService;
import com.ngx.boot.service.StuInfoService;
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
    private StuInfoService stuInfoService;

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
    public void testPage(){
        Page<StuInfo> page = new Page<>(1, 5);
        QueryWrapper<StuInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("stu_grade","大三");
        Page<StuInfo> page1 = stuInfoService.page(page,wrapper);
        System.out.println("总条数："+page1.getTotal());
        System.out.println("总页数："+page1.getPages());
        page.getRecords().forEach(System.out::println);

    }
}
