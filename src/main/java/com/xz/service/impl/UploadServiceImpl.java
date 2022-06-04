package com.xz.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadServiceImpl {

    public String uploadImg(MultipartFile multipartFile,String dir){
        //获取文件和存放目录

        try {
            //生成新文件名
            String originalFilename = multipartFile.getOriginalFilename();
            String imgSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + imgSuffix;
            //生成日期目录
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = simpleDateFormat.format(new Date());
            //指定文件上传目录
            File targetPath=new File("F://tmp/"+dir,datePath);
            if (!targetPath.exists()){
                targetPath.mkdirs();
            }
            //上传处理后的文件
            multipartFile.transferTo(new File(targetPath,newFilename));
            String finalPath=dir+"/"+datePath+"/"+newFilename;
            return finalPath;
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
    }
}
