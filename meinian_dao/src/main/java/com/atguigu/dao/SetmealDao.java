package com.atguigu.dao;

import com.atguigu.pojo.Setmeal;
import com.github.pagehelper.Page;

import java.util.Map;

public interface SetmealDao {

    void addTravelGroupIdBySetmealId(Map<String, Integer> map);

    void add(Setmeal setmeal);

    Page<Setmeal> findPage(String queryString);

}
