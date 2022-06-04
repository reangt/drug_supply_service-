package com.xz.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.DrugType;

import java.util.List;
import java.util.Map;

public interface DrugTypeService extends IService<DrugType> {
    List<Map<Integer,String>> getproducttype();

    List<Map<Integer, String>> getpacktype();

    List<Map<Integer, String>> getusetype();

    List<Map<Integer, String>> getpackagingunit();

    String getTypeById(int i,String type);
}
