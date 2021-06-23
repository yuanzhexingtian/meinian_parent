package com.atguigu.service;

import com.atguigu.pojo.TravelItem;
import com.atguigu.vo.PageResult;

import java.util.List;

public interface TravelItemService {
    void add(TravelItem travelItem);


    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    void delete(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
