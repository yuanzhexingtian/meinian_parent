package com.atguigu.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.RedisConstant;
import com.atguigu.dao.SetmealDao;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.QiniuUtils;
import com.atguigu.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;


    @Override
    public void add(Setmeal setmeal, Integer[] travelgroupIds) {
        //新增套餐
        setmealDao.add(setmeal);
        //新增关联表
        addTravelGroupIdBySetmealId(setmeal.getId(), travelgroupIds);
    }

    private void addTravelGroupIdBySetmealId(Integer id, Integer[] travelgroupIds) {
        for (Integer travelgroupId : travelgroupIds) {
            Map<String,Integer> map= new HashMap<>();
            map.put("setmeal_id", id);
            map.put("travelgroup_id", travelgroupId);
            setmealDao.addTravelGroupIdBySetmealId(map);
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> setmeals = setmealDao.findPage(queryString);
        return new PageResult(setmeals.getTotal(),setmeals.getResult());
    }
}
