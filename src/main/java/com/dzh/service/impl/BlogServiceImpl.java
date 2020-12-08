package com.dzh.service.impl;

import com.dzh.entity.Blog;
import com.dzh.mapper.BlogMapper;
import com.dzh.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author miziha
 * @since 2020-11-27
 */
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

}
