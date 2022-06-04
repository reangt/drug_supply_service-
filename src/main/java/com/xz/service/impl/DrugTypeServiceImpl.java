package com.xz.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xz.dao.DrugTypeDao;
import com.xz.domain.DrugType;
import com.xz.service.DrugTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("DrugTypeServiceImpl")
@Transactional
public class DrugTypeServiceImpl extends ServiceImpl<DrugTypeDao, DrugType> implements DrugTypeService {
    @Autowired
    private DrugTypeDao drugTypeDao;

    @Override
    public List<Map<Integer,String>> getproducttype() {
        return drugTypeDao.getproducttype();
    }

    @Override
    public List<Map<Integer, String>> getpacktype() {
        return drugTypeDao.getpacktype();
    }

    @Override
    public List<Map<Integer, String>> getusetype() {
        return drugTypeDao.getusetype();
    }

    @Override
    public List<Map<Integer, String>> getpackagingunit() {
        return drugTypeDao.getpackagingunit();
    }

    @Override
    public String getTypeById(int i,String type) {
//        if (type.equals("packagingunit")) {
//            return drugTypeDao.getTypeByIdpai(i,type);
//        }
//        if (type.equals("usetype")) {
//            return drugTypeDao.getTypeByIdpai(i,type);
//        }
//        if (type.equals("packagingunit")) {
//            return drugTypeDao.getTypeByIdpai(i,type);
//        }
//        if (type.equals("packagingunit")) {
//            return drugTypeDao.getTypeByIdpai(i,type);
//        }

        switch (type){
            case "packagingunit":
                return drugTypeDao.getTypeByIdpai(i,type);
            case "usetype":
                return drugTypeDao.getTypeByIdut(i,type);
            case "packtype":
                return drugTypeDao.getTypeByIdpt(i,type);
            case "producttype":
                return drugTypeDao.getTypeByIdpr(i,type);
        }
        return "错误";
    }
}
