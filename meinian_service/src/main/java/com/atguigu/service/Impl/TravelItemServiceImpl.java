package com.atguigu.service.Impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.constant.MessageConstant;
import com.atguigu.dao.TravelItemDao;
import com.atguigu.pojo.TravelItem;
import com.atguigu.service.TravelItemService;
import com.atguigu.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = TravelItemService.class)
@Transactional
public class TravelItemServiceImpl implements TravelItemService{

    @Autowired
    private TravelItemDao travelItemDao;

    @Override
    public void add(TravelItem travelItem) {
        travelItemDao.add(travelItem);
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {

        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        Page<TravelItem> page = travelItemDao.findPage(queryString);
        //
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        //查询是否有关联条件
        if(travelItemDao.findRelationship(id)>0) {
            throw new RuntimeException(MessageConstant.GET_RALETIONSHIP_ERROR);
        }
        travelItemDao.delete(id);
    }

    @Override
    public TravelItem findById(Integer id) {
        return travelItemDao.findById(id);
    }

    @Override
    public void edit(TravelItem travelItem) {
        travelItemDao.edit(travelItem);
    }

    @Override
    public List<TravelItem> findAll() {
        return travelItemDao.findAll();
    }
}
