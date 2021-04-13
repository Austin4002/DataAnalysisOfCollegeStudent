package com.ngx.boot.controller;

import com.ngx.boot.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/portrait")
    public Result getPortrait(@RequestParam("userId") String userId, @RequestParam("term") String term){

        Result rs = new Result<>(500, "error");



        return rs;
    }



}
