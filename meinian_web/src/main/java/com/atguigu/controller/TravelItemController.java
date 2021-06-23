package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.atguigu.vo.PageResult;
import com.atguigu.vo.QueryPageBean;
import com.atguigu.vo.Result;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


//自由行
@RestController
@RequestMapping("/tavelItem")
public class TravelItemController {

    @Reference
    private TravelItemService travelItemService;

    @RequestMapping("/add")
    public Result add(@RequestBody TravelItem travelItem){
        try {
            travelItemService.add(travelItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_TRAVELITEM_SUCCESS);
    }

    @RequestMapping ("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        PageResult pageResult=travelItemService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString());
        return pageResult;
    }

    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            travelItemService.delete(id);
        } catch (RuntimeException e){
            return new Result(false, MessageConstant.GET_RALETIONSHIP_ERROR);
        } catch (Exception e) {
            return new Result(false, MessageConstant.DELETE_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.DELETE_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findById")
    public Result findById(Integer id) {
        TravelItem travelItem =null;
        try {
            travelItem = travelItemService.findById(id);
        } catch (Exception e) {
            return new Result(false, MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
        System.out.println("travelItem = " + travelItem);
        return new Result(true, MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItem);
    }

    @RequestMapping("/edit")
    public Result edit(@RequestBody TravelItem travelItem) {
        try {
            travelItemService.edit(travelItem);
        } catch (Exception e) {
            return new Result(false, MessageConstant.EDIT_TRAVELITEM_FAIL);
        }
        return new Result(true, MessageConstant.EDIT_TRAVELITEM_SUCCESS);
    }

    @RequestMapping("/findAll")
    public Result findAll() {
        List<TravelItem> travelItemList= null;
        try {
            travelItemList= travelItemService.findAll();
        } catch (Exception e) {
            return new Result(false,MessageConstant.QUERY_TRAVELITEM_FAIL);
        }
        return new Result(true,MessageConstant.QUERY_TRAVELITEM_SUCCESS,travelItemList);
    }

}
