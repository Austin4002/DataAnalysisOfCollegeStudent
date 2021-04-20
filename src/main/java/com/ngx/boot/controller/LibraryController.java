package com.ngx.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ngx.boot.bean.BookInfo;
import com.ngx.boot.bean.StuBorrow;
import com.ngx.boot.bean.StuCheck;
import com.ngx.boot.service.BookInfoService;
import com.ngx.boot.service.StuBorrowService;
import com.ngx.boot.service.StuCheckService;
import com.ngx.boot.vo.Result;
import com.ngx.boot.vo.library.lib.Frequents;
import com.ngx.boot.vo.library.lib.Libcheck;
import com.ngx.boot.vo.library.lib.Times;
import com.ngx.boot.vo.library.lib.Treemaps;
import com.ngx.boot.vo.library.libf.Fre;
import com.ngx.boot.vo.library.libf.Freque;
import com.ngx.boot.vo.library.libf.Timee;
import com.ngx.boot.vo.library.libf.Total;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : 朱坤
 * @date :
 */


@RestController
@Slf4j
@RequestMapping("/lib")
public class LibraryController {

    @Autowired
    private StuCheckService stuCheckService;

    @Autowired
    private BookInfoService bookInfoService;

    @Autowired
    private StuBorrowService stuBorrowService;


    @GetMapping("/info")
    public Result getInfo(@RequestParam String year, @RequestParam String month) {

        Result rs = new Result<>(500, "error");
        Libcheck libcheck = new Libcheck();

        //将入馆频次封装进去
        if (year != null) {

            for (int i = 2017; i < 2020; i++) {
                if (Integer.parseInt(year) == i) {
                    QueryWrapper Wrapper1 = new QueryWrapper();
                    Map<Object, Object> resMap = new HashMap<>();
                    //String resno = ""+k;
                    resMap.put("stu_year", i);
                    Wrapper1.allEq(resMap);
                    List<StuBorrow> borlist = stuBorrowService.list(Wrapper1);
                    String fre = borlist.size() + "";
                    Frequents fres = new Frequents();
                    fres.setFrequency(fre);
                    libcheck.setFrequency(fres);
                    break;

                } else {
                    continue;
                }

            }

        }
        if (year != null && month != null && !month.equals("")) {

            for (int j = 2017; j < 2020; j++) {
                if (Integer.parseInt(year) == j) {
                    int k = 0;
                    if (Integer.parseInt(month) <= 6) {
                        k = 1;
                        QueryWrapper Wrap = new QueryWrapper();
                        Map<Object, Object> Ma = new HashMap<>();
                        //String resno = ""+k;
                        Ma.put("stu_year", j);
                        Ma.put("stu_term", k);
                        Wrap.allEq(Ma);
                        List<StuBorrow> borlists = stuBorrowService.list(Wrap);
                        String fres = borlists.size() + "";
                        Frequents fress = new Frequents();
                        fress.setFrequency(fres);
                        libcheck.setFrequency(fress);

                    } else if (Integer.parseInt(month) <= 6) {
                        k = 2;
                        QueryWrapper Wrap2 = new QueryWrapper();
                        Map<Object, Object> Ma2 = new HashMap<>();
                        //String resno = ""+k;
                        Ma2.put("stu_year", j);
                        Ma2.put("stu_term", k);
                        Wrap2.allEq(Ma2);
                        List<StuBorrow> borlists2 = stuBorrowService.list(Wrap2);
                        String fres2 = borlists2.size() + "";
                        Frequents fress2 = new Frequents();
                        fress2.setFrequency(fres2);
                        libcheck.setFrequency(fress2);

                    }
                    break;

                } else {
                    continue;
                }

            }
        }


        //将入馆时长封装进去
        if (year != null) {

            for (int i = 2017; i < 2020; i++) {

                if (Integer.parseInt(year) == i) {
                    QueryWrapper Wraptime1 = new QueryWrapper();
                    Map<Object, Object> Maptime1 = new HashMap<>();
                    Maptime1.put("stu_year", i);
                    Wraptime1.allEq(Maptime1);
                    List<StuBorrow> timebor1 = stuBorrowService.list(Wraptime1);
                    double timeborr1 = timebor1.stream().mapToInt(item -> (int) item.getBorTime()).sum();
                    String time1 = "" + timeborr1;
                    Times t1 = new Times();
                    t1.setTime(time1);
                    libcheck.setTime(t1);

                    break;
                } else {
                    continue;
                }
            }


        }
        if (year != null && month != null && !month.equals("")) {

            for (int j = 2017; j < 2020; j++) {
                if (Integer.parseInt(year) == j) {
                    int k = 0;
                    if (Integer.parseInt(month) <= 6) {
                        k = 1;
                        QueryWrapper Wraptime2 = new QueryWrapper();
                        Map<Object, Object> Maptime2 = new HashMap<>();
                        Maptime2.put("stu_year", j);
                        Maptime2.put("stu_term", k);
                        Wraptime2.allEq(Maptime2);
                        List<StuBorrow> timebor2 = stuBorrowService.list(Wraptime2);
                        double timeborr2 = timebor2.stream().mapToInt(item -> (int) item.getBorTime()).sum();
                        String time2 = "" + timeborr2;
                        Times t2 = new Times();
                        t2.setTime(time2);
                        libcheck.setTime(t2);

                    } else if (Integer.parseInt(month) <= 6) {
                        k = 2;
                        QueryWrapper Wraptime3 = new QueryWrapper();
                        Map<Object, Object> Maptime3 = new HashMap<>();
                        Maptime3.put("stu_year", j);
                        Maptime3.put("stu_term", k);
                        Wraptime3.allEq(Maptime3);
                        List<StuBorrow> timebor3 = stuBorrowService.list(Wraptime3);
                        double timeborr3 = timebor3.stream().mapToInt(item -> (int) item.getBorTime()).sum();
                        String time3 = "" + timeborr3;
                        Times t3 = new Times();
                        t3.setTime(time3);
                        libcheck.setTime(t3);
                    }
                    break;

                } else {
                    continue;
                }

            }

        }

        //书籍类比占比及数量
        List<Treemaps> tr = new ArrayList<>();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.select("DISTINCT book_type");
        List<BookInfo> book = bookInfoService.list(queryWrapper);
        List<String> booktype = book.stream().map(v -> v.getBookType()).collect(Collectors.toList());

        for (int i = 0; i < 10; i++) {

            QueryWrapper queryWrapper2 = new QueryWrapper();
//            Map<Object, Object> Maps = new HashMap<>();
//            Maps.put("book_type",booktype.get(i));
            queryWrapper2.eq("book_type", booktype.get(i));

//            queryWrapper2.allEq(Maps);
            List<BookInfo> book2 = bookInfoService.list(queryWrapper2);
            int typecount = book2.size();
            Treemaps tres = new Treemaps();
            tres.setName(booktype.get(i));
            tres.setValue(typecount);
            tr.add(tres);

        }

        libcheck.setTreeMap(tr);

        rs.setCode(200);
        rs.setMsg("ok");
        rs.setData(libcheck);
        return rs;


    }


    @GetMapping("/frequency")
    public Result getFrequency(@RequestParam String year) {


        Result rs = new Result<>(500, "error");
        Fre fre = new Fre();

        //各年级入馆频次封装进去
        List<Freque> list1 = new ArrayList<>();
        //将年级类型封装进list
        for (int i = 2017; i < 2020; i++) {
            if (Integer.parseInt(year) == i) {

                for (int t = 2016; t < 2020; t++) {
                    QueryWrapper Wrapper2 = new QueryWrapper();
                    Wrapper2.eq("stu_year", i);
                    Wrapper2.likeRight("stu_no", t);
                    List<StuCheck> stuChecks = stuCheckService.list(Wrapper2);
                    int count1 = stuChecks.stream().mapToInt(item -> (int) item.getStuFrequent()).sum();
                    Freque freque = new Freque();
                    String grade = null;
                    if (t == 2016) {
                        grade = "大四";
                    }
                    if (t == 2017) {
                        grade = "大三";
                    }
                    if (t == 2018) {
                        grade = "大二";
                    }
                    if (t == 2019) {
                        grade = "大一";
                    }
                    freque.setType(grade);
                    freque.setValue(count1);
                    list1.add(freque);
                }
            } else {
            }
        }
        fre.setFrequency(list1);


        //各年级入馆时长封装进去

        List<Timee> list2 = new ArrayList<>();

        for (int j = 2017; j < 2020; j++) {
            if (Integer.parseInt(year) == j) {
                for (int m = 1; m < 3; m++) {

                    for (int n = 2016; n < 2020; n++) {

                        QueryWrapper Wrapper3 = new QueryWrapper();
                        Wrapper3.eq("stu_year", j);
                        Wrapper3.likeRight("stu_no", n);
                        Wrapper3.eq("stu_term", m);
                        List<StuCheck> stuChecks = stuCheckService.list(Wrapper3);
                        int count2 = stuChecks.stream().mapToInt(item -> (int) item.getSumTime()).sum();
                        //int count1 = stuChecks.stream().mapToInt(item -> (int) item.getStuFrequent()).sum();
                        //Freque freque = new Freque();
                        Timee timee = new Timee();
                        String grade2 = null;
                        if (n == 2016) {
                            grade2 = "大四";
                        }
                        if (n == 2017) {
                            grade2 = "大三";
                        }
                        if (n == 2018) {
                            grade2 = "大二";
                        }
                        if (n == 2019) {
                            grade2 = "大一";
                        }
                        String terms = null;
                        if (m == 1) {
                            terms = "第一学期";
                        }
                        if (m == 2) {
                            terms = "第二学期";
                        }

                        timee.setGrade(grade2);
                        timee.setTerm(terms);
                        timee.setValue(count2);
                        list2.add(timee);
                    }

                }
            } else {
            }
        }
        fre.setTime(list2);


        //每月入馆频次封装进去
        List<Total> list3 = new ArrayList<>();

        for (int a = 2017; a < 2020; a++) {
            if (Integer.parseInt(year) == a) {
                for (int b = 1; b < 13; b++) {
                    QueryWrapper Wrapper3 = new QueryWrapper();
                    Wrapper3.eq("stu_year", a);
                    Wrapper3.eq("stu_month", b);
                    List<StuCheck> stuChecks = stuCheckService.list(Wrapper3);
                    int count3 = stuChecks.stream().mapToInt(item -> (int) item.getStuFrequent()).sum();
                    Total total = new Total();
                    String tt = b + "月";
                    total.setType(tt);
                    total.setValue(count3);
                    list3.add(total);
                }
            } else {
            }
        }
        fre.setTotal(list3);

        rs.setCode(200);
        rs.setMsg("ok");
        rs.setData(fre);
        return rs;


    }


    @GetMapping("/book")
    public Result getBookCommentByPage(
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "month", required = false) String month,
            //当前页
            @RequestParam("current") Integer current,
            //一页有多少条数据
            @RequestParam("pageSize") Integer pageSize) {
        Result rs = new Result<>(500, "error");

        Page<BookInfo> page = new Page<>(current, pageSize);
        QueryWrapper<BookInfo> wrapper =new QueryWrapper<>();
        wrapper.orderByDesc("recommend");
        Page<BookInfo> bookInfoPage = bookInfoService.page(page,wrapper);

        List<BookInfo> list = bookInfoPage.getRecords();
        List<BookInfo> records = new ArrayList<>();

        for (BookInfo item : list) {
            BookInfo record = new BookInfo();
            BeanUtils.copyProperties(item, record);
            if (item.getRecommend() < 100) {
                record.setRecommend(1.0);
            } else if (item.getRecommend() >= 100 && item.getRecommend() < 150) {
                record.setRecommend(2.0);
            } else if (item.getRecommend() >= 150 && item.getRecommend() < 200) {
                record.setRecommend(3.0);
            } else if (item.getRecommend() >= 200 && item.getRecommend() < 250) {
                record.setRecommend(4.0);
            } else if (item.getRecommend() >= 250) {
                record.setRecommend(5.0);
            }
            records.add(record);
        }
        bookInfoPage.setRecords(records);


        if (bookInfoPage.getSize() >= 0) {
            rs.setData(bookInfoPage);
            rs.setCode(200);
            rs.setMsg("ok");
        }

        return rs;


    }


}
