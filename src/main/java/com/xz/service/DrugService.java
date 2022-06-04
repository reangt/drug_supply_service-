package com.xz.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xz.domain.Drug;

public interface DrugService extends IService<Drug> {
    Drug getByid(Integer id);

    IPage getDrugAllBymerchant(Page<Drug> page, int merchantid,Integer id,String name);

    Drug getByname(String name, Long merchantid);

    IPage getDrugAll(Page<Drug> page, Integer id, String name);
}
