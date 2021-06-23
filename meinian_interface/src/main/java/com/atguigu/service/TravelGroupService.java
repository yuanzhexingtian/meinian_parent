package com.atguigu.service;

import com.atguigu.pojo.TravelGroup;
import com.atguigu.vo.PageResult;

import java.util.List;

public interface TravelGroupService {

    void add(TravelGroup travelGroup, Integer[] travelItemIds);

    PageResult findPage(Integer currentPage, Integer pageSize, String queryString);

    TravelGroup findById(Integer id);

    List<Integer> findTravelItemIdByTravelgroupId(Integer id);

    void edit(TravelGroup travelGroup, Integer[] travelItemIds);

    List<TravelGroup> findAllGroup();

}
