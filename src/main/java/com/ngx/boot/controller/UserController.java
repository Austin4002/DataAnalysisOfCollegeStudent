package com.ngx.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.bean.StuCheck;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.bean.StuScore;
import com.ngx.boot.service.*;
import com.ngx.boot.vo.Result;
import com.ngx.boot.vo.portrait.TreeMap;
import com.ngx.boot.vo.portrait.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@RestController
@Slf4j
//@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private StuInfoService stuInfoService;

    @Autowired
    private StuScoreService stuScoreService;

    @Autowired
    private StuConsumeService stuConsumeService;

    @Autowired
    private StuCheckService stuCheckService;

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private StuBorrowService stuBorrowService;


    @GetMapping("/user/portrait")
    public Result getPortrait(@RequestParam(value = "stuId" ,required = true) String stuId) throws InvocationTargetException, IllegalAccessException {
        Result rs = new Result<>(500, "error");

        Portrait portrait = new Portrait();
        StuInfo stuInfo = stuInfoService.getById(stuId);
        portrait.setStuId(stuId);
        portrait.setStuName(stuInfo.getStuName());
        portrait.setStuGrade(stuInfo.getStuGrade());
        portrait.setStuMajor(stuInfo.getStuMajor());
        portrait.setStuGender(stuInfo.getStuSex());

        //查询学习行为标签
        String learnTags = stuInfo.getLearn();
        //查询消费行为标签
        String consumeTags = stuInfo.getConsume();
        //查询行为标签
        String behaviorTags = stuInfo.getBehavior();
        String stuTagsStr ="";
        if (learnTags != null){
            stuTagsStr += learnTags + "-";
        }
        if (consumeTags != null){
            stuTagsStr += consumeTags + "-";
        }
        if (behaviorTags != null){
            stuTagsStr += behaviorTags;
        }

        String[] stuTags = stuTagsStr.split("-");
        //将所有标签放入portrait
        portrait.setStuTags(stuTags);

        //查询学生GPA
        List<StuGPA> stuGPAList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("stu_no",stuId);
        List<StuScore> scores = stuScoreService.listByMap(map);
        scores.forEach(item->{
            double score = item.getStuGpa();
            String term = item.getStuTerm();
            String year = item.getStuYear();
            StuGPA stuGPA = new StuGPA();
            stuGPA.setValue(score);
            stuGPA.setYear(year+"."+term);
            stuGPAList.add(stuGPA);
        });
        portrait.setStuGPA(stuGPAList);

        // 创建词云
        List<StuWord> word =new ArrayList<>();

        StuWord stuWord1 = new StuWord(new Random().nextInt(10 - 1) + 1, stuInfo.getStuMajor());
        StuWord stuWord2 = new StuWord(new Random().nextInt(10 - 1) + 1, stuInfo.getStuGrade());
        for (int i = 0; i < stuTags.length; i++) {
            word.add(new StuWord(new Random().nextInt(10 - 1) + 1,stuTags[i]));
        }
        word.add(stuWord1);
        word.add(stuWord2);
        portrait.setWord(word);

        //封装消费数据
        List<ConsumeData> consumeDataList = new ArrayList<>();
        //平均消费
        ConsumeData avgConsumeData =new ConsumeData();
        avgConsumeData.setName("平均消费");
        double avgConsume =stuConsumeService.getAvgConsumeMoneyByNo(stuId);
        avgConsumeData.setStar(avgConsume);
        //消费峰值
        ConsumeData topConsumeData = new ConsumeData();
        topConsumeData.setName("消费峰值");
        double topConsume = stuConsumeService.getMaxConsumeMoneyByNo(stuId);
        topConsumeData.setStar(topConsume);
        //消费频次
        ConsumeData frequencyConsumeData =new ConsumeData();
        frequencyConsumeData.setName("消费频次");
        Integer frequencyConsume = stuConsumeService.getFrequencyConsumeData(stuId);
        frequencyConsumeData.setStar(frequencyConsume);

        consumeDataList.add(avgConsumeData);
        consumeDataList.add(topConsumeData);
        consumeDataList.add(frequencyConsumeData);

        //封装学习数据
        List<LearnData> learnDataList = new ArrayList<>();
        //平均gpa
        LearnData avgGPAData = new LearnData();
        avgGPAData.setName("平均gpa");
        double avgGPA = stuScoreService.getAvgGPA(stuId);
        avgGPAData.setStar(avgGPA);
        //最高gpa
        LearnData topGPAData = new LearnData();
        topGPAData.setName("最高gpa");
        double topGPA = stuScoreService.getTopGPA(stuId);
        topGPAData.setStar(topGPA);
        //平均成绩
        LearnData avgScoreData = new LearnData();
        avgScoreData.setName("平均成绩");
        double avgScore = stuScoreService.getAvgScore(stuId);
        avgScoreData.setStar(avgScore);
        //添加到list中，还需要放入Learn对象中，再放入stuBehavior
        learnDataList.add(avgGPAData);
        learnDataList.add(topGPAData);
        learnDataList.add(avgScoreData);

        //封装行为书籍
        List<BehaviorData> behaviorDataList = new ArrayList<>();
        //平均入馆次数
        BehaviorData avgTimeCountData = new BehaviorData();
        avgTimeCountData.setName("平均入馆次数");
        double avgTimeCount = stuCheckService.getAvgTimeCount(stuId);
        avgTimeCountData.setStar(avgTimeCount);
        //平均入馆时长
        BehaviorData avgTimeData = new BehaviorData();
        avgTimeData.setName("平均入馆时长");
        double avgTime = stuCheckService.getAvgTime(stuId);
        avgTimeData.setStar(avgTime);
        //最高入馆次数
        BehaviorData topTimeCountData = new BehaviorData();
        topTimeCountData.setName("最高入馆次数");
        double topTimeCount = stuCheckService.getTopTimeCount(stuId);
        topTimeCountData.setStar(topTimeCount);
        //最高入馆时长
        BehaviorData topTimeData = new BehaviorData();
        topTimeData.setName("最高入馆时长");
        double topTime = stuCheckService.getTopTime(stuId);
        topTimeData.setStar(topTime);

        behaviorDataList.add(avgTimeCountData);
        behaviorDataList.add(avgTimeData);
        behaviorDataList.add(topTimeCountData);
        behaviorDataList.add(topTimeData);

        //将数据封装到StuBehavior
        Consume consume = new Consume(consumeDataList);
        Learn learn = new Learn(learnDataList);
        Behavior behavior = new Behavior(behaviorDataList);
        StuBehavior stuBehavior = new StuBehavior(consume,learn,behavior);
        portrait.setStuBehavior(stuBehavior);




        List<TreeMap> treeMapList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("DISTINCT book_type");

        List<BookInfo> list = bookInfoService.list(wrapper);
        list.forEach(item->{
            String bookType = item.getBookType();
            QueryWrapper queryWrapper = new QueryWrapper();
            Map<String,String> rvMap = new HashMap<>();
            rvMap.put("stu_no","201919201");
            rvMap.put("book_type",bookType);
            queryWrapper.allEq(rvMap);

            int count = stuBorrowService.count(queryWrapper);
            TreeMap treeMap = new TreeMap(bookType,count);
            treeMapList.add(treeMap);
        });



        InLibraryTime inLibraryTime = new InLibraryTime();
        //最高入馆时长
        inLibraryTime.setValue(topTime);
        List<Integer> maxTime = stuCheckService.getAllMaxTime();
//        int count = 0;
//        maxTime.forEach(item->{
//            if (item <= topTime){
//                count++;
//            }
//        });0
        int count1 = (int) maxTime.stream().filter(item -> item <= topTime).count();
        double inTime = (double) count1/maxTime.size() * 100;
        log.error("count1--->{},maxTimesize--->{},inTime--->{}",count1,maxTime.size(),inTime);
        inLibraryTime.setOver(inTime);

        //最高入馆次数
        InLibraryFrequency inLibraryFrequency = new InLibraryFrequency();
        inLibraryFrequency.setValue(topTimeCount);
        List<Integer> maxTimeCount = stuCheckService.getAllMaxTimeCount();
        int count2 = (int) maxTimeCount.stream().filter(item -> item <= topTime).count();
        double inFrequency = (double) count2/maxTimeCount.size() * 100;
        log.error("count2--->{},maxTimeCountsize--->{},inFrequency--->{}",count2,maxTimeCount.size(),inFrequency);
        inLibraryFrequency.setOver(inFrequency);



        QueryWrapper frequencyWrapper = new QueryWrapper();
        frequencyWrapper.eq("stu_no",stuId);
        List<StuCheck> stuCheckList = stuCheckService.list(frequencyWrapper);
        int arraySize = stuCheckList.size();
        Integer[] monthFre = new Integer[arraySize];
        for (int i = 0; i < stuCheckList.size(); i++) {
            monthFre[i] = stuCheckList.get(i).getStuFrequent();
        }

        portrait.setStuBook(new StuBook(treeMapList,inLibraryTime,inLibraryFrequency,monthFre));

        rs.setCode(200);
        rs.setMsg("ok");
        rs.setData(portrait);
        return rs;
    }

    public Result getScore(@RequestParam String stuId){

        Result rs=new Result();


        return rs;

    }



}
