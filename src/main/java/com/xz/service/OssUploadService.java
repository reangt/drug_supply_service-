package com.xz.service;

import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.ObjectMetadata;
import com.xz.controller.utils.UpType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class OssUploadService {

    public String uploadfile(MultipartFile multipartFile,String dir){

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = "oss-cn-beijing.aliyuncs.com";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "**";//此处按自己账号信息填写
        String accessKeySecret = "**";//此处按自己账号信息填写
        // 填写Bucket名称，例如examplebucket。
        String bucketName = "drugimage";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            if (!ossClient.doesBucketExist(bucketName)){
                ossClient.createBucket(bucketName);
                ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            }
            //获取文件流
            InputStream inputStream = multipartFile.getInputStream();
            //生成日期目录
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            String datePath = simpleDateFormat.format(new Date());
            //获取文件名
            String originalFilename = multipartFile.getOriginalFilename();
            String imgSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + imgSuffix;
            String fileurl=dir+"/"+datePath+"/"+newFilename;
            //
            // 填写字符串。
            String content = "Hello OSS";

            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
//             ObjectMetadata metadata = new ObjectMetadata();
//             metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            // metadata.setObjectAcl(CannedAccessControlList.Private);
            // putObjectRequest.setMetadata(metadata);
            //设置设置 HTTP 头 里边的 Content-Type
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType(UpType.getcontentType(fileurl.substring(fileurl.lastIndexOf("."))));

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileurl,inputStream);
            // 上传字符串。
            ossClient.putObject(putObjectRequest);

            return "https://"+bucketName+"."+endpoint+"/"+fileurl;
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message:" + oe.getErrorMessage());
            System.out.println("Error Code:" + oe.getErrorCode());
            System.out.println("Request ID:" + oe.getRequestId());
            System.out.println("Host ID:" + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message:" + ce.getMessage());
        } catch (Exception ce) {
            ce.printStackTrace();
            return "fail";
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return "endpoint";
    }
}
