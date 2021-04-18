package com.ngx.boot;

import com.ngx.boot.bean.StuScore;
import com.ngx.boot.service.StuScoreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author : 朱坤
 * @date :
 */
@SpringBootTest
public class CreatGPA {

    //随机
    private  int getIntRandom(int start,int end){
        return (int)(Math.random()*(end-start))+start;
    }

    private  String[] gpa= new String[]{"1.1","1.2","1.3","1.4","1.5","2.1","2.2","2.3","2.4","2.5","2.6","2.0",
            "1.6","1.7","1.8","1.9","2.7","2.8","2.9","3.0","3.1","3.2","3.3","3.4","3.5",
            "3.6","3.7","3.8","3.9","4.0","4.1","4.2","4.3","4.4"};
    @Autowired
    private StuScoreService stuScoreService;

    // 慎用！！！
    @Test
   // @Transactional
    public void updateGPA(){

        List<StuScore> list = stuScoreService.list();
        list.forEach(item->{
            StuScore record = new StuScore();
            record.setId(item.getId());
            record.setStuGpa(this.getgpa());
            stuScoreService.updateById(record);
        });


    }

    //生成gpa
    public double getgpa(){
        return Double.parseDouble(gpa[getIntRandom(0,gpa.length)]);
//        return gpa[getIntRandom(0,gpa.length)];
    }
}
