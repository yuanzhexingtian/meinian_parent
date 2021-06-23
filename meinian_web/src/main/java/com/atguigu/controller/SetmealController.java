package com.atguigu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.constant.MessageConstant;
import com.atguigu.constant.RedisConstant;
import com.atguigu.pojo.Setmeal;
import com.atguigu.service.SetmealService;
import com.atguigu.utils.QiniuUtils;
import com.atguigu.vo.PageResult;
import com.atguigu.vo.QueryPageBean;
import com.atguigu.vo.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.JedisPool;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Reference
    private SetmealService setmealService;

    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/upload")
    public Result upload(@RequestBody MultipartFile imgFile) {
        String baseUrl="http://qv1urdveu.hn-bkt.clouddn.com/";
        String newFileName =null;
        try {
            byte[] bytes = imgFile.getBytes();
            String originalFilename = imgFile.getOriginalFilename();
            newFileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf('.'));

            QiniuUtils.upload2Qiniu(bytes,newFileName);
        } catch (Exception e) {
            return new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        //七牛云文件--》redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_RESOURCES,baseUrl+newFileName);
        return new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS,baseUrl+newFileName);
    }

    @RequestMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] travelgroupIds) {
        try {
            setmealService.add(setmeal, travelgroupIds);
        } catch (Exception e) {
            return new Result(false, MessageConstant.ADD_SETMEAL_FAIL);
        }
        //数据库文件--》redis
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PIC_DB_RESOURCES, setmeal.getImg());

        return new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
    }

    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        return setmealService.findPage(queryPageBean.getCurrentPage(),
                queryPageBean.getPageSize(), queryPageBean.getQueryString());
    }

}
