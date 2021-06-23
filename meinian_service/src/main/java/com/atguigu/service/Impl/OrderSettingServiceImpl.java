package com.atguigu.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.OrderSettingDao;
import com.atguigu.pojo.OrderSetting;
import com.atguigu.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettings) {
        //1.遍历List
        for (OrderSetting orderSetting : orderSettings) {
            //判断当前日期之前是否已经被设置为当前预约日期使用当前时间作为查询条件
            long count=orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
            //如果设置过预约日期，更新number数量
            if (count > 0) {
                orderSettingDao.editNumberByOrderDate(orderSetting);
            }else{
                //如果没有设置过预约日期，执行保存
                orderSettingDao.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map<String,String>> getOrderSettingByMonth(String date) {
        List<OrderSetting> orderSettings=orderSettingDao.getOrderSettingByMonth(date);
        List<Map<String,String>> list=null;
        for (OrderSetting orderSetting : orderSettings) {
            Map<String,String> map= new HashMap<>();
            String str = orderSetting.getOrderDate().toString();
            String substring = str.substring(str.lastIndexOf('-') + 1);
            map.put("date",substring);
            map.put("number", orderSetting.getNumber()+"");
            map.put("reservations", orderSetting.getReservations() + "");
            list.add(map);
        }
        return list;
    }

    @Override
    public void editNumberByDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if (count > 0) {
            //当前日期已经进行了预购
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            orderSettingDao.add(orderSetting);
        }

    }
}
