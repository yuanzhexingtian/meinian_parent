package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.atguigu.vo.PageResult;
import com.atguigu.vo.QueryPageBean;
import com.atguigu.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/travelGroup")
public class TravelGroupController {

    @Reference
    private TravelGroupService travelGroupService;

    @RequestMapping("/add")
    public Result add(@RequestBody TravelGroup travelGroup,Integer[] travelItemIds) {
        try {
            travelGroupService.add(travelGroup, travelItemIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult=travelGroupService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(),queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        TravelGroup travelGroup=null;
        try {
            travelGroup=travelGroupService.findById(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroup);

    }

    @RequestMapping("/findTravelItemIdByTravelgroupId")
    public Result findTravelItemIdByTravelgroupId(Integer id) {
        List<Integer> integers=null;
        try {
            integers=travelGroupService.findTravelItemIdByTravelgroupId(id);
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_TRAVELITEMIDS_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_TRAVELITEMIDS_SUCCESS,integers);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelGroup travelGroup,Integer[] travelItemIds) {
        try {
            travelGroupService.edit(travelGroup, travelItemIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELGROUP_SUCCESS);
    }

    @RequestMapping("/findAllGroup")
    public Result findAllGroup() {
        List<TravelGroup> travelGroups =null;
        try {
            travelGroups = travelGroupService.findAllGroup();
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_TRAVELGROUP_FAIL);
        }
        return new Result(true, MessageConstant.QUERY_TRAVELGROUP_SUCCESS,travelGroups);

    }
}
