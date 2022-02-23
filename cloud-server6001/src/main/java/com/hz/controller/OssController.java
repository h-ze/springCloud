package com.hz.controller;


import com.common.entity.ResponseResult;
import com.common.exception.RRException;
import com.hz.oss.cloud.OSSFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
@Api(tags = "云存储器接口")
public class OssController {

    /**
     * 上传文件
     */
    //@RequiresPermissions("sys:oss:all")
    @ApiOperation(value = "上传文件",notes = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docName",value = "文档名",paramType = "query",dataType = "String",required = false),
            @ApiImplicitParam(name = "encryptConfig",value = "加密策略",paramType = "query",dataType ="String",required = false) ,
            @ApiImplicitParam(name = "docId",value = "文档id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name ="file",value = "文件",paramType = "form",dataType = "__file",required = true)
    })
    @PostMapping("uploadFile")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) throws Exception {

		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}


		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = OSSFactory.build().uploadSuffix(file.getBytes(), suffix);
        //OSSFactory.build().uploadObject2OSS(file,"","test/");

		//保存文件信息
		/*SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);*/

		//存储的信息需要入库

        return ResponseResult.successResult(100000,url);

    }


    @ApiOperation(value = "删除文件",notes = "删除文件")
    @DeleteMapping("deleteFile")
    public ResponseResult deleteFile(@RequestParam("fileName")String fileName) throws Exception {
        //存储的信息需要入库
        return ResponseResult.successResult(100000,"删除成功");
    }

    @ApiOperation(value = "查询文件",notes = "查询文件")
    @GetMapping("fileList")
    public ResponseResult selectFile(){
        return ResponseResult.successResult(100000,"查询成功");
    }



}
