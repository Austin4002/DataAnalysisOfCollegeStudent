package com.ngx.boot.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ngx.boot.bean.Admin;
import com.ngx.boot.service.AdminService;
import com.ngx.boot.utils.TokenUtil;
import com.ngx.boot.vo.LoginUser;
import com.ngx.boot.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
//@RequestMapping("/api")
@Slf4j
public class LoginController {

    @Autowired
    private AdminService adminService;

    @PostMapping("/login/account")
    public Result login(@RequestBody LoginUser user){
        Result rs = new Result<>(500, "error");

        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("name",user.getUsername());
        wrapper.eq("password",user.getPassword());
        Admin admin = adminService.getOne(wrapper);
        if (admin!= null){
            String token = TokenUtil.sign(user.getUsername(), user.getPassword());
            rs.setData(token);
            rs.setMsg("ok");
            rs.setCode(200);
        }

        return rs;

    }


    @GetMapping("/currentUser")
    public Result getCurrentUser(HttpServletRequest request){
        Result rs = new Result<>(500, "error");
        String token = request.getHeader("Authorization");
        if (token!=null){
            String username = TokenUtil.decode(token);
            QueryWrapper<Admin> wrapper = new QueryWrapper<>();
            wrapper.eq("name",username);
            Admin admin = adminService.getOne(wrapper);
            rs.setCode(200);
            rs.setMsg("ok");
            rs.setData(admin);
        }

        return rs;

    }

    @GetMapping("/login/outLogin")
    public Result logout(){
        return new Result<>(200, "ok");
    }



}
