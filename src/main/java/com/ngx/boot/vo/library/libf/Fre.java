package com.ngx.boot.vo.library.libf;


import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Data
public class Fre {

    //各年级入馆频次
    private List<Freque> frequency;
    //各年级入馆时长
    private List<Timee> time;
    //每月入馆频次
    private List<Total> total;





}
