package com.dzh.controller;


import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dzh.common.lang.Result;
import com.dzh.entity.Blog;
import com.dzh.service.BlogService;
import com.dzh.util.ShiroUtil;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author miziha
 * @since 2020-11-27
 */
@RestController
public class BlogController {

    @Autowired
    BlogService blogService;
    @RequestMapping("/blogs")
    public Result list(@RequestParam(defaultValue = "1") Integer currentPage){
        Page page = new Page(currentPage,5);
        IPage pageData = blogService.page(page, new QueryWrapper<Blog>().orderByDesc("created"));
        return Result.success(pageData);
    }
    @RequestMapping("/blog/{id}")
    public Result detail(@PathVariable(name = "id") Long id){
        Blog blog = blogService.getById(id);
        Assert.notNull(blog,"博客已被删除");
        return Result.success(blog);
    }
    @RequiresAuthentication
    @PostMapping("/blog/edit")
    public Result edit( @Validated @RequestBody Blog blog){
        //userId是否为空，为空则添加当前登录id，不为空，则校验是否与当前登录id相同，不同则不能修改
        Blog temp = new Blog();
        if(blog.getId() != null){
            temp = blogService.getById(blog.getId());
            Assert.isTrue(temp.getUserId().equals(ShiroUtil.getprofile().getId()),"没有权限编辑!!!");
            //Assert.isTrue(temp.getUserId().longValue() == ShiroUtil.getprofile().getId().longValue(), "没有权限编辑");
        }else{
            temp.setUserId(ShiroUtil.getprofile().getId());
            temp.setCreated(LocalDateTime.now());
            temp.setStatus(0);
        }
        BeanUtils.copyProperties(blog,temp,"id", "userId", "created", "status");
        blogService.saveOrUpdate(temp);
        return Result.success(null);
    }
    @RequiresAuthentication
    @PostMapping("/blog/delete")
    public Result delete(@Validated @RequestBody Blog blog){
        Assert.notNull(blog,"博客已被删除");
        blogService.removeById(blog.getId());
        return Result.success(null);
    }

}
