package com.ngx.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ngx.boot.bean.*;
import com.ngx.boot.service.*;
import com.ngx.boot.vo.Records.Record;
import com.ngx.boot.vo.Result;
import com.ngx.boot.vo.portrait.TreeMap;
import com.ngx.boot.vo.portrait.*;
import com.ngx.boot.vo.score.GPAList;
import com.ngx.boot.vo.score.GoodGrade;
import com.ngx.boot.vo.score.Score;
import com.ngx.boot.vo.survey.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.stringtemplate.v4.ST;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/user")
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

    @Autowired
    private RecordService recordService;


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
        String stuTagsStr = "";
        if (learnTags != null) {
            stuTagsStr += learnTags + "-";
        }
        if (consumeTags != null) {
            stuTagsStr += consumeTags + "-";
        }
        if (behaviorTags != null) {
            stuTagsStr += behaviorTags;
        }

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
            rvMap.put("stu_no", stuId);
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
        double inTime = (double) count1 / maxTime.size() * 100;
        log.error("count1--->{},maxTimesize--->{},inTime--->{}", count1, maxTime.size(), inTime);
        inLibraryTime.setOver(inTime);

        //最高入馆次数
        InLibraryFrequency inLibraryFrequency = new InLibraryFrequency();
        inLibraryFrequency.setValue(topTimeCount);
        List<Integer> maxTimeCount = stuCheckService.getAllMaxTimeCount();
        int count2 = (int) maxTimeCount.stream().filter(item -> item <= topTimeCount).count();

        double inFrequency = (double) count2 / maxTimeCount.size() * 100;
        log.error("count2--->{},maxTimeCountsize--->{},inFrequency--->{}", count2, maxTimeCount.size(), inFrequency);
        inLibraryFrequency.setOver(inFrequency);


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
        AtomicInteger count2 = new AtomicInteger();
        AtomicInteger count3 = new AtomicInteger();
        AtomicInteger count4 = new AtomicInteger();
        gradelist.forEach(item -> {
            if (item.equals("大一")) {
                count1.getAndIncrement();
            } else if (item.equals("大二")) {
                count2.getAndIncrement();
            } else if (item.equals("大三")) {
                count3.getAndIncrement();
            } else if (item.equals("大四")) {
                count4.getAndIncrement();
            }
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
        AtomicInteger gendercount2 = new AtomicInteger();
        genderlist.forEach(v -> {
            if (v.equals("男")) {
                gendercount1.getAndIncrement();
            } else if (v.equals("女")) {
                gendercount2.getAndIncrement();
            }
        });
        Gender gender1 = new Gender();
        Gender gender2 = new Gender();
        gender1.setType("男");
        gender1.setValue(gendercount1.intValue());
        gender2.setType("女");
        gender2.setValue(gendercount2.intValue());
        gender.add(gender1);
        gender.add(gender2);
        survey.setGender(gender);

        //将各专业占比封装进去
        List<Majorss> major = new ArrayList<>();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.select("DISTINCT stu_major");
        List<StuInfo> stuInfos = stuInfoService.list(wrapper);
        List<String> res = stuInfos.stream().map(v -> v.getStuMajor()).collect(Collectors.toList());
        for (int i = 0; i < res.size(); i++) {
            QueryWrapper majorWrapper = new QueryWrapper();
            majorWrapper.eq("stu_major", res.get(i));
            int count = stuInfoService.count(majorWrapper);
            Majorss majo = new Majorss();
            majo.setType(res.get(i));
            majo.setValue(count);
            major.add(majo);
        }
        survey.setMajor(major);


        //将餐厅月营业额封装进去
        List<sConsume> consumes = new ArrayList<>();
//        QueryWrapper queryWrapper = new QueryWrapper();
//        Map<String, String> rvMap = new HashMap<>();
//        rvMap.put("stu_no", "201919201");
//        rvMap.put("book_type", bookType);
//        queryWrapper.allEq(rvMap);
//        int count = stuBorrowService.count(queryWrapper);

        for (int i = 2017; i < 2020; i++) {

            for (int j = 1; j < 11; j++) {

                for (int k = 1; k < 5; k++) {

                    QueryWrapper Wrapper1 = new QueryWrapper();
                    Map<Object, Object> resMap = new HashMap<>();
                    //String resno = ""+k;
                    resMap.put("stu_year", i);
                    resMap.put("con_month", j);
                    resMap.put("con_restaurant", k);
                    Wrapper1.allEq(resMap);
                    List<StuConsume> stuConsumes = stuConsumeService.list(Wrapper1);
                    double counts = stuConsumes.stream().mapToInt(item -> (int) item.getConMoney()).sum();
                    sConsume cons = new sConsume();
                    String times = i + "." + j;
                    String resno = null;
                    if (k == 1) {
                        resno = "1号餐厅";
                    } else if (k == 2) {
                        resno = "2号餐厅";
                    } else if (k == 3) {
                        resno = "3号餐厅";
                    } else if (k == 4) {
                        resno = "4号餐厅";
                    }

                    cons.setDate(times);
                    cons.setType(resno);
                    cons.setValue(counts);
                    consumes.add(cons);

                }

            }

        }

        survey.setConsume(consumes);

        //将学生月阅读量封装进去

        List<Reading> readings = new ArrayList<>();

        for (int i = 2017; i < 2020; i++) {
            for (int j = 1; j < 3; j++) {
                for (int k = 2016; k < 2020; k++) {

                    QueryWrapper Wrapper2 = new QueryWrapper();
                    Wrapper2.eq("stu_year", i);
                    Wrapper2.eq("stu_term", j);
                    Wrapper2.likeRight("stu_no", k);
                    List<StuBorrow> stuBor = stuBorrowService.list(Wrapper2);
                    //double countss = StuBorrow.stream().mapToInt(item -> (int) item.getb.sum();
                    double countss = stuBor.stream().mapToDouble(StuBorrow::getBorTime).sum();
                    String grad = null;
                    if (k == 2016) {
                        grad = "大四";
                    }
                    if (k == 2017) {
                        grad = "大三";
                    }
                    if (k == 2018) {
                        grad = "大二";
                    }
                    if (k == 2019) {
                        grad = "大一";
                    }
                    String times = i + "/" + j;
                    Reading read = new Reading();
                    read.setTerm(times);
                    read.setGrade(grad);
                    read.setReading(countss);
                    readings.add(read);
                }

            }

        }
        survey.setReading(readings);


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

        List<String> res = majors.stream().map(v -> v.getStuMajor()).collect(Collectors.toList());

        rs.setData(res);

        rs.setCode(200);
        rs.setMsg("ok");

        return rs;
    }


    @GetMapping("/score")
    public Result getScore(@RequestParam(required = true, value = "stuId") String stuId) {
        Result rs = new Result<>(500, "error");

        Score score = new Score();

        StuInfo stuInfo = stuInfoService.getById(stuId);
        BeanUtils.copyProperties(stuInfo, score);
        score.setStuId(stuInfo.getStuNo());
        //计算该同学的总成绩
        QueryWrapper<StuScore> totalScoreWrapper = new QueryWrapper<>();
        totalScoreWrapper.eq("stu_no", stuId);
        //该同学的成绩记录
        List<StuScore> list = stuScoreService.list(totalScoreWrapper);
        //该同学的平均gpa
        double aveGPAScore = list.stream()
                .mapToDouble(StuScore::getStuGpa)
                .sum() / list.size();
        //封装平均gpa
        score.setAveGpaScore(aveGPAScore);

        //该同学的总成绩
        double totalScore = list.stream()
                .map(v -> v.getOneScore() + v.getTwoScore() + v.getThreeScore())
                .mapToDouble(v -> v)
                .sum();
        score.setTotalScore(totalScore);
        //封装平均综合成绩
        double aveScore = totalScore / list.size() / 3;
        score.setAveScore(aveScore);

        /**
         * 计算该同学的总成绩超过了多少人
         */
        List<StuScore> stuNoDistinct = stuScoreService.getStuNoDistinct();
        //所有同学的总成绩
        List<Double> totalScoreList = new ArrayList<>();
        //所有同学的平均成绩
        List<Double> totalAveScoreList = new ArrayList<>();

        List<Double> aveGPAList = new ArrayList<>();

        stuNoDistinct.forEach(item -> {
            QueryWrapper<StuScore> totalPercentWrapper = new QueryWrapper<>();
            totalPercentWrapper.eq("stu_no", item.getStuNo());
            List<StuScore> scoreList = stuScoreService.list(totalPercentWrapper);
            //一个人的总成绩
            double oneTotalScore = scoreList.stream()
                    .map(v -> v.getOneScore() + v.getTwoScore() + v.getThreeScore())
                    .mapToDouble(v -> v)
                    .sum();
            //一个人的平均成绩
            double oneAveScore = oneTotalScore / scoreList.size() / 3;

            //一个人的平均gpa
            double oneAveGPA = list.stream()
                    .mapToDouble(StuScore::getStuGpa)
                    .sum() / scoreList.size();

            aveGPAList.add(oneAveGPA);
            totalScoreList.add(oneTotalScore);
            totalAveScoreList.add(oneAveScore);

        });
        double totalPercentCount = 0;
        double avePercentCount = 0;
        double aveGPAPercentCount = 0;
        for (int i = 0; i < totalScoreList.size(); i++) {
            if (totalScore > totalScoreList.get(i)) {
                totalPercentCount++;
            }
            if (aveScore > totalAveScoreList.get(i)) {
                avePercentCount++;
            }
            if (aveGPAScore > aveGPAList.get(i)) {
                aveGPAPercentCount++;
            }

        }
        log.error("totalPercentCount--->{},totalScoreList---->{}", totalPercentCount, totalScoreList.size());
        //总成绩超过了多少人(百分比)
        double totalPercent = totalPercentCount / totalScoreList.size() * 100;
        score.setTotalPercent(totalPercent);

        log.error("avePercentCount--->{},totalAveScoreList--->{}", avePercentCount, totalAveScoreList.size());
        //平均成绩超过了多少人(百分比)
        double avePercent = avePercentCount / totalAveScoreList.size() * 100;
        score.setAvePercent(avePercent);
        //平均GPA超过了多少人(百分比)
        double aveGPAPercent = aveGPAPercentCount / aveGPAList.size() * 100;
        log.error("aveGPAPercentCount--->{},aveGPAList--->{}", aveGPAPercentCount, aveGPAList.size());
        score.setAveGpaPercent(aveGPAPercent);

        /**
         * 封装成绩最好的三门课
         */
        ArrayList<GoodGrade> gradeList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            gradeList.add(new GoodGrade(list.get(i).getOneCourse(), list.get(i).getOneScore()));
            gradeList.add(new GoodGrade(list.get(i).getTwoCourse(), list.get(i).getTwoScore()));
            gradeList.add(new GoodGrade(list.get(i).getThreeCourse(), list.get(i).getThreeScore()));
        }

        //根据成绩降序排序--牛的一！！
        List<GoodGrade> goodGrade = gradeList
                .stream()
                .sorted(Comparator.comparing(GoodGrade::getScore).reversed())
                .limit(3)
                .collect(Collectors.toList());

        score.setGoodGrade(goodGrade);

        ArrayList<GPAList> gpaList = new ArrayList<>();

        for (StuScore stuScore : list) {
            double stuGpa = stuScore.getStuGpa();
            String year = stuScore.getStuYear();
            if (stuScore.getStuTerm().equals("1")) {
                year = year + ".3";
            } else if (stuScore.getStuTerm().equals("2")) {
                year = year + ".9";
            }

            gpaList.add(new GPAList(year, stuGpa));
        }

        score.setGpaList(gpaList);

        rs.setMsg("ok");
        rs.setCode(200);
        rs.setData(score);

        return rs;

    }


    //学生概况
    @PostMapping("/info")
    public Result getCondition(@RequestParam("current") Integer current,
                               @RequestParam(value = "major" , required = false) String major,
                               @RequestParam("pageSize") Integer pageSize,
                               @RequestParam(value = "stuId", required = false) String stuId) {

        Result rs = new Result<>(500, "error");

        List<Record> records = new ArrayList<>();
        QueryWrapper<StuInfo> queryWrapper = new QueryWrapper();
        if (stuId != null && !stuId.equals("") && major != null && !major.equals("")) {
            queryWrapper.eq("stu_major", major);
            queryWrapper.eq("stu_no", stuId);
        }
        if (stuId != null && !stuId.equals("")) {
            queryWrapper.eq("stu_no", stuId);
        }
        if (major != null && !major.equals("")) {
            queryWrapper.eq("stu_major", major);
        }

        //分页
        Page<StuInfo> page = new Page<StuInfo>(current, pageSize);
        Page<StuInfo> page1 = stuInfoService.page(page,queryWrapper);



        rs.setData(page1);
        rs.setMsg("ok");
        rs.setCode(200);

        return rs;

    }


    @PostMapping("/list")
    public Result getList(@RequestParam("current") String current,@RequestParam("pagesize") String pagesize,
                          @RequestParam(value = "major",required = false) String major,
                          @RequestParam(value = "stuId",required = false) String stuId){
        Result rs = new Result<>(500, "error");

//        rs.setData(current);
        return rs;

    }

}
