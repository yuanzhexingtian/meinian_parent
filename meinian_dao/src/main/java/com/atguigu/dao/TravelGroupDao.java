package com.atguigu.dao;

import com.atguigu.pojo.TravelGroup;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface TravelGroupDao {
    void setTravelGroupAndTravelItem(Map<String, Integer> map);

    void add(TravelGroup travelGroup);

    Page<TravelGroup> findPage(String queryString);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void deleteTravelItemIdByTravelgroupId(Integer id);

    void edit(TravelGroup travelGroup);

    List<TravelGroup> findAllGroup();
}
