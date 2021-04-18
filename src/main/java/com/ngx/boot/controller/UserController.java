package com.ngx.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.bean.StuCheck;
import com.ngx.boot.bean.StuInfo;
import com.ngx.boot.bean.StuScore;
import com.ngx.boot.service.*;
import com.ngx.boot.vo.Major;
import com.ngx.boot.vo.Result;
import com.ngx.boot.vo.portrait.TreeMap;
import com.ngx.boot.vo.portrait.*;
import com.ngx.boot.vo.survey.Gender;
import com.ngx.boot.vo.survey.Grade;
import com.ngx.boot.vo.survey.Survey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/user")
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


    @GetMapping("/portrait")
    public Result getPortrait(@RequestParam(value = "stuId", required = true) String stuId) throws InvocationTargetException, IllegalAccessException {
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
        String stuTagsStr = learnTags + "-" + consumeTags + "-" + behaviorTags;

        String[] stuTags = stuTagsStr.split("-");
        //将所有标签放入portrait
        portrait.setStuTags(stuTags);

        //查询学生GPA
        List<StuGPA> stuGPAList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("stu_no", stuId);
        List<StuScore> scores = stuScoreService.listByMap(map);
        scores.forEach(item -> {
            double score = item.getStuGpa();
            String term = item.getStuTerm();
            String year = item.getStuYear();
            StuGPA stuGPA = new StuGPA();
            stuGPA.setValue(score);
            stuGPA.setYear(year + "." + term);
            stuGPAList.add(stuGPA);
        });
        portrait.setStuGPA(stuGPAList);

        // 创建词云
        List<StuWord> word = new ArrayList<>();

        StuWord stuWord1 = new StuWord(new Random().nextInt(10 - 1) + 1, stuInfo.getStuMajor());
        StuWord stuWord2 = new StuWord(new Random().nextInt(10 - 1) + 1, stuInfo.getStuGrade());
        for (int i = 0; i < stuTags.length; i++) {
            word.add(new StuWord(new Random().nextInt(10 - 1) + 1, stuTags[i]));
        }
        word.add(stuWord1);
        word.add(stuWord2);
        portrait.setWord(word);

        //封装消费数据
        List<ConsumeData> consumeDataList = new ArrayList<>();
        //平均消费
        ConsumeData avgConsumeData = new ConsumeData();
        avgConsumeData.setName("平均消费");
        double avgConsume = stuConsumeService.getAvgConsumeMoneyByNo(stuId);
        avgConsumeData.setStar(avgConsume);
        //消费峰值
        ConsumeData topConsumeData = new ConsumeData();
        topConsumeData.setName("消费峰值");
        double topConsume = stuConsumeService.getMaxConsumeMoneyByNo(stuId);
        topConsumeData.setStar(topConsume);
        //消费频次
        ConsumeData frequencyConsumeData = new ConsumeData();
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
        StuBehavior stuBehavior = new StuBehavior(consume, learn, behavior);
        portrait.setStuBehavior(stuBehavior);


        List<TreeMap> treeMapList = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("DISTINCT book_type");

        List<BookInfo> list = bookInfoService.list(wrapper);
        list.forEach(item -> {
            String bookType = item.getBookType();
            QueryWrapper queryWrapper = new QueryWrapper();
            Map<String, String> rvMap = new HashMap<>();
            rvMap.put("stu_no", "201919201");
            rvMap.put("book_type", bookType);
            queryWrapper.allEq(rvMap);

            int count = stuBorrowService.count(queryWrapper);
            TreeMap treeMap = new TreeMap(bookType, count);
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
//        });
        int count1 = (int) maxTime.stream().filter(item -> item <= topTime).count();
        inLibraryTime.setOver(count1 / maxTime.size() * 100);

        //最高入馆次数
        InLibraryFrequency inLibraryFrequency = new InLibraryFrequency();
        inLibraryFrequency.setValue(topTimeCount);
        List<Integer> maxTimeCount = stuCheckService.getAllMaxTimeCount();
        int count2 = (int) maxTimeCount.stream().filter(item -> item <= topTime).count();
        inLibraryFrequency.setOver(count2 / maxTimeCount.size() * 100);


        QueryWrapper frequencyWrapper = new QueryWrapper();
        frequencyWrapper.eq("stu_no", stuId);
        List<StuCheck> stuCheckList = stuCheckService.list(frequencyWrapper);
        int arraySize = stuCheckList.size();
        Integer[] monthFre = new Integer[arraySize];
        for (int i = 0; i < stuCheckList.size(); i++) {
            monthFre[i] = stuCheckList.get(i).getStuFrequent();
        }

        portrait.setStuBook(new StuBook(treeMapList, inLibraryTime, inLibraryFrequency, monthFre));

        rs.setCode(200);
        rs.setMsg("ok");
        rs.setData(portrait);
        return rs;
    }


    @GetMapping("/survey")
    public Result getSurvey() throws Exception {
        Result rs = new Result<>(500, "error");
        Survey survey = new Survey();
        //将各年级占比封装进去
        List<Grade> grade = new ArrayList<>();
        List<String> gradelist = stuInfoService.getGradeNumber();
        AtomicInteger count1 = new AtomicInteger();
        AtomicInteger count2= new AtomicInteger();
        AtomicInteger count3= new AtomicInteger();
        AtomicInteger count4= new AtomicInteger();
        gradelist.forEach(item->{
            if (item.equals("大一")){ count1.getAndIncrement(); }
            else if(item.equals("大二")){ count2.getAndIncrement(); }
            else if(item.equals("大三")){ count3.getAndIncrement(); }
            else if(item.equals("大四")){ count4.getAndIncrement(); }
        });
        Grade grade1 = new Grade();
        Grade grade2 = new Grade();
        Grade grade3 = new Grade();
        Grade grade4 = new Grade();
        grade1.setType("大一");
        grade1.setValue(count1.intValue());
        grade2.setType("大二");
        grade2.setValue(count2.intValue());
        grade3.setType("大三");
        grade3.setValue(count3.intValue());
        grade4.setType("大四");
        grade4.setValue(count4.intValue());
        grade.add(grade1);
        grade.add(grade2);
        grade.add(grade3);
        grade.add(grade4);
        survey.setGrade(grade);

        //将男女占比封装进去
        List<Gender> gender = new ArrayList<>();
        List<String> genderlist = stuInfoService.getGenderNumber();
        AtomicInteger gendercount1 = new AtomicInteger();
        AtomicInteger gendercount2= new AtomicInteger();
        genderlist.forEach(v->{
            if(v.equals("男")){gendercount1.getAndIncrement();}
            else if(v.equals("女")){ gendercount2.getAndIncrement(); }
        });
        Gender gender1 = new Gender();
        Gender gender2 = new Gender();
        gender1.setType("男");gender1.setValue(gendercount1.intValue());
        gender2.setType("女");gender2.setValue(gendercount2.intValue());
        gender.add(gender1);
        gender.add(gender2);
        survey.setGender(gender);

        //将各专业占比封装进去
        List<Major> major = new ArrayList<>();
        //List<String> genderlist = stuInfoService.getGenderNumber();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("DISTINCT stu_major");
        List<StuInfo> stuInfos = stuInfoService.list(wrapper);
        List<String> res=stuInfos.stream().map(v->v.getStuMajor()).collect(Collectors.toList());
        for (int i = 0; i < res.size(); i++) {
            QueryWrapper majorWrapper = new QueryWrapper();
            majorWrapper.eq("stu_major",res.get(i));
            int count = stuInfoService.count(majorWrapper);
            
        }










        //将餐厅月营业额封装进去








        //将学生月阅读量封装进去












        rs.setData(survey);
        rs.setCode(200);
        rs.setMsg("ok");

        return rs;


    }



    @GetMapping("/major")
    public Result getMajor() throws Exception {
        Result rs = new Result<>(500, "error");
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("DISTINCT stu_major");
        List<StuInfo> majors = stuInfoService.list(queryWrapper);

        List<String> res=majors.stream().map(v->v.getStuMajor()).collect(Collectors.toList());

        rs.setData(res);

        rs.setCode(200);
        rs.setMsg("ok");

        return rs;
    }











    public Result getScore(@RequestParam String stuId){

        Result rs=new Result();


        return rs;

    }



}
