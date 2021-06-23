package com.atguigu.service.Impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.dao.TravelGroupDao;
import com.atguigu.pojo.TravelGroup;
import com.atguigu.service.TravelGroupService;
import com.atguigu.vo.PageResult;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = TravelGroupService.class)
@Transactional
public class TravelGroupServiceImpl implements TravelGroupService{

    @Autowired
    private TravelGroupDao travelGroupDao;

    @Override
    public void add(TravelGroup travelGroup, Integer[] travelItemIds) {
        //新增跟团游表
        travelGroupDao.add(travelGroup);
        //新增关联表
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    private void setTravelGroupAndTravelItem(Integer id, Integer[] travelItemIds) {

        // 新增几条数据，由travelItemIds的长度决定
        if (travelItemIds!=null && travelItemIds.length>0){
            for (Integer travelItemId : travelItemIds) {
                // 如果有多个参数使用map
                Map<String, Integer> map = new HashMap<>();
                map.put("travelGroup",id);
                map.put("travelItem",travelItemId);
                travelGroupDao.setTravelGroupAndTravelItem(map);
            }
        }
    }

    @Override
    public PageResult findPage(Integer currentPage, Integer pageSize, String queryString) {
        //开启分页
        PageHelper.startPage(currentPage, pageSize);
        //进行查询
        Page<TravelGroup> travelGroups=travelGroupDao.findPage(queryString);
        return new PageResult(travelGroups.getTotal(), travelGroups.getResult());
    }


    @Override
    public TravelGroup findById(Integer id) {
        return travelGroupDao.findById(id);
    }

    @Override
    public List<Integer> findTravelItemIdByTravelgroupId(Integer id) {
        return travelGroupDao.findTravelItemIdByTravelgroupId(id);
    }

    @Override
    public void edit(TravelGroup travelGroup, Integer[] travelItemIds) {
        //编辑跟团游表
        travelGroupDao.edit(travelGroup);
        //删除关联表
        travelGroupDao.deleteTravelItemIdByTravelgroupId(travelGroup.getId());

        //新增关联表
        setTravelGroupAndTravelItem(travelGroup.getId(),travelItemIds);
    }

    @Override
    public List<TravelGroup> findAllGroup() {
        return travelGroupDao.findAllGroup();
    }
}
