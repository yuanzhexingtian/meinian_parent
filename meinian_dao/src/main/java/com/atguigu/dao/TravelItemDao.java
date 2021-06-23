package com.atguigu.dao;

import com.atguigu.pojo.TravelItem;
import com.github.pagehelper.Page;

import java.util.List;

public interface TravelItemDao {
    void add(TravelItem travelItem);


    Page<TravelItem> findPage(String queryString);

    void delete(Integer id);

    Integer findRelationship(Integer id);

    TravelItem findById(Integer id);

    void edit(TravelItem travelItem);

    List<TravelItem> findAll();
}
