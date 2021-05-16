package com.ngx.boot.excelTest;

import com.ngx.boot.bean.StuConsume;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.bean.StuScore;
import com.ngx.boot.excelOperation.CreateExcel;
import com.ngx.boot.excelOperation.GeneratePerSonInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class ExcelTest {

    @Test
    public void test() {

        List<StuInfo> stuInfoList = new ArrayList<>();
        List<StuScore> stuScoreList = new ArrayList<>();

        for (int grade = 2017; grade <= 2020; grade++) {
            for (int i = 1; i <= 99; i++) {
                //生成学生记录
                StuInfo stuInfo = new StuInfo();
                String noSuffix;
                if (i <= 9) {
                    noSuffix = "0" + i;
                } else {
                    noSuffix = i + "";
                }
                stuInfo.setStuNo(grade + "19" + noSuffix);
                stuInfo.setStuName(GeneratePerSonInfo.getChineseName());
                stuInfo.setStuGrade(grade+"");
                stuInfo.setStuSex(GeneratePerSonInfo.getSex());
                stuInfo.setStuMajor(GeneratePerSonInfo.getMajor());
                stuInfoList.add(stuInfo);

                //生成学生成绩
                if (grade<2020){
                    //如果年级书小于当前年份，就从入学以来的成绩都生成一次
                    for (int time = grade;time<=2020;time++){
                        for (int term =1;term<=2;term++){
                            StuScore stuScore = new StuScore();
                            BeanUtils.copyProperties(stuInfo,stuScore);
                            stuScore.setOneCourse(GeneratePerSonInfo.getCourse());
                            stuScore.setOneScore(GeneratePerSonInfo.getScore());
                            stuScore.setTwoCourse(GeneratePerSonInfo.getCourse());
                            stuScore.setTwoScore(GeneratePerSonInfo.getScore());
                            stuScore.setThreeCourse(GeneratePerSonInfo.getCourse());
                            stuScore.setThreeScore(GeneratePerSonInfo.getScore());
                            stuScore.setStuTerm(term+"");
                            stuScore.setStuYear(time+"");
                            stuScoreList.add(stuScore);
                        }
                    }
                }else {
                    StuScore stuScore = new StuScore();
                    BeanUtils.copyProperties(stuInfo,stuScore);
                    stuScore.setOneCourse(GeneratePerSonInfo.getCourse());
                    stuScore.setOneScore(GeneratePerSonInfo.getScore());
                    stuScore.setTwoCourse(GeneratePerSonInfo.getCourse());
                    stuScore.setTwoScore(GeneratePerSonInfo.getScore());
                    stuScore.setThreeCourse(GeneratePerSonInfo.getCourse());
                    stuScore.setThreeScore(GeneratePerSonInfo.getScore());
                    stuScore.setStuYear(grade+"");
                    stuScore.setStuTerm("1");
                    stuScoreList.add(stuScore);
                }

                //生成消费数据
                //生成每一年的消费数据,一年有12个月，其中9-12月和3-6在学校，只需要生成这些月份的数据，按一个月30天来算,1天要吃3顿饭
                for (int month = 3; month <=6 ; month++) {
                    for (int day =1;day<=30;day++){
                        for (int time = 7;time<=10;time=time+5){
                            StuConsume consume = new StuConsume();
                            BeanUtils.copyProperties(stuInfo,consume);
                            consume.setConMoney(GeneratePerSonInfo.getMoney());
                        }



                    }
                }




                //生成门禁数据

                //生成借阅数据

                //生成图书数据
                
            }
        }

        CreateExcel.writeStuInfo(stuInfoList,"E:\\testFile\\stuInfo.xlsx");
        CreateExcel.writeStuScore(stuScoreList,"E:\\testFile\\stuScore.xlsx");
    }



}
