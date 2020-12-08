package com.dzh.controller;


import com.dzh.common.lang.Result;
import com.dzh.entity.User;
import com.dzh.service.UserService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miziha
 * @since 2020-11-27
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService  userService;

    @GetMapping("/index")  //@GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)
    public Result index(){
        User user = userService.getById(1L);
        return Result.success(user);
    }

    @RequiresAuthentication
    @GetMapping("/test")  //@GetMapping是一个组合注解，是@RequestMapping(method = RequestMethod.GET)
    public Result test(){
        User user = userService.getById(1L);
        return Result.success(user);
    }

    @PostMapping("/save")
    public Result save(@Validated @RequestBody User user){
        return Result.success(user);
    }
}
