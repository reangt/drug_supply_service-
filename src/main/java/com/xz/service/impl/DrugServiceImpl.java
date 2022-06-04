package com.xz.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.DrugDao;
import com.xz.domain.Drug;
import com.xz.service.DrugService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DrugServiceImpl extends ServiceImpl<DrugDao, Drug> implements DrugService {

    @Autowired
    private DrugDao drugDao;

    @Override
    public Drug getByid(Integer id) {
        return drugDao.getByid(id);
    }

    @Override
    public IPage getDrugAllBymerchant(Page<Drug> pageparam,int merchantid,Integer id,String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if (id!=null){
            queryWrapper.eq("id",id);
        }
        if (name!=""&&id==null){
            queryWrapper.eq("name",name);
        }
        queryWrapper.eq("merchantid",merchantid);
        queryWrapper.orderByDesc("id");
        Page page = baseMapper.selectPage(pageparam, queryWrapper);
        return page;
    }

    @Override
    public Drug getByname(String name, Long merchantid) {
        QueryWrapper<Drug> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("name",name);
        queryWrapper.eq("merchantid",merchantid);
        Drug drug = baseMapper.selectOne(queryWrapper);
        return drug;
    }

    @Override
    public IPage getDrugAll(Page<Drug> pageparam, Integer id, String name) {
        QueryWrapper queryWrapper=new QueryWrapper();
        if (id!=null){
            queryWrapper.eq("id",id);
        }
        if (name!=""&&id==null){
            queryWrapper.eq("name",name);
        }
        queryWrapper.orderByDesc("id");
        Page page = baseMapper.selectPage(pageparam, queryWrapper);
        return page;
    }
}
