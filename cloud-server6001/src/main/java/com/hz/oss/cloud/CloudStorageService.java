/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package com.hz.oss.cloud;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * 云存储(支持七牛、阿里云、腾讯云、又拍云)
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class CloudStorageService {
    /** 云存储配置信息 */
    CloudStorageConfig config;

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {
        //生成uuid
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //文件路径
        String path = DateUtils.format(new Date(), "yyyyMMdd") + "/" + uuid;

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        return path + suffix;
    }

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return         返回http地址
     */
    public abstract String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return             返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix);


    /**
     * 创建存储空间
     * @param ossClient      OSS连接
     * @param bucketName 存储空间
     * @return
     */
    public abstract String createBucketName(OSSClient ossClient, String bucketName);

    /**
     * 删除存储空间buckName
     * @param ossClient  oss对象
     * @param bucketName  存储空间
     */
    public abstract void deleteBucket(OSSClient ossClient, String bucketName);

    /**
     * 创建模拟文件夹
     * @param ossClient oss连接
     * @param bucketName 存储空间
     * @param folder   模拟文件夹名如"qj_nanjing/"
     * @return  文件夹名
     */
    public abstract String createFolder(OSSClient ossClient,String bucketName,String folder);

    /**
     * 根据key删除OSS服务器上的文件
     * @param ossClient  oss连接
     * @param bucketName  存储空间
     * @param folder  模拟文件夹名 如"qj_nanjing/"
     * @param key Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
     */
    public abstract void deleteFile(OSSClient ossClient, String bucketName, String folder, String key);

    /**
     * 上传图片至OSS
     * @param file 上传文件（文件全路径如：D:\\image\\cake.jpg）
     * @param bucketName  存储空间
     * @param folder 模拟文件夹名 如"qj_nanjing/"
     * @return String 返回的唯一MD5数字签名
     * */
    public abstract String uploadObject2OSS(File file, String bucketName, String folder);

    /**
     * 通过文件名判断并获取OSS服务文件上传时文件的contentType
     * @param fileName 文件名
     * @return 文件的contentType
     */
    public abstract String getContentType(String fileName);


}
