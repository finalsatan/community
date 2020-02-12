package com.murphy.community.provider;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * QcloudProvider
 *
 * @author zhangyh2360@dimpt.com
 * @date 2020/2/12 11:46 下午
 */

@Component
public class QcloudProvider {
    @Value("${qcloud.ufile.secret-id}")
    private String secretId;

    @Value("${qcloud.ufile.secret-key}")
    private String secretKey;

    @Value("${qcloud.ufile.region}")
    private String region;

    @Value("${qcloud.ufile.bucket-name}")
    private String bucketName;

    @Value("${qcloud.ufile.path}")
    private String path;

    public String upload(MultipartFile file) {
        try {
            // 1 初始化用户身份信息（secretId, secretKey）。
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
            Region qregion = new Region(region);
            ClientConfig clientConfig = new ClientConfig(qregion);
            // 3 生成 cos 客户端。
            COSClient cosClient = new COSClient(cred, clientConfig);

            // 指定要上传到的存储桶
            String qbucketName = bucketName;
            // 指定要上传到 COS 上对象键
            String fileName = file.getOriginalFilename();
            String key = "/" + UUID.randomUUID() + fileName;

            File localFile = null;
            localFile = File.createTempFile("temp", null);
            file.transferTo(localFile);

            PutObjectRequest putObjectRequest = new PutObjectRequest(qbucketName, key, localFile);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
            return this.path + putObjectRequest.getKey();
        } catch (IOException e) {
            return "";
        }
    }
}
