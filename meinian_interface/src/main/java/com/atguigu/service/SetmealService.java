package com.atguigu.service;

import com.atguigu.pojo.Setmeal;
import com.atguigu.vo.PageResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface SetmealService {

    void add(Setmeal setmeal, Integer[] travelgroupIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);


}
