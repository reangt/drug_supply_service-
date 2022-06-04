package com.xz.controller;

import com.xz.controller.utils.Result;
import com.xz.domain.DrugType;
import com.xz.service.DrugTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/drugtypes")
public class DrugTypeController {
    @Autowired
    private DrugTypeService drugTypeService;

    @PostMapping("/byid")
    public Result gettype(@RequestBody DrugType drugType){
        System.out.println(drugType);
        int packagingunit = Integer.parseInt(drugType.getPackagingunit());
        int packtype = Integer.parseInt(drugType.getPacktype());
        int producttype = Integer.parseInt(drugType.getProducttype());
        int usetype = Integer.parseInt(drugType.getUsetype());
        DrugType drugType1 = new DrugType();
        drugType1.setPackagingunit(drugTypeService.getTypeById(packagingunit,"packagingunit"));
        drugType1.setPacktype(drugTypeService.getTypeById(packtype,"packtype"));
        drugType1.setProducttype(drugTypeService.getTypeById(producttype,"producttype"));
        drugType1.setUsetype(drugTypeService.getTypeById(usetype,"usetype"));
        return Result.ok(drugType1);
    }
}
