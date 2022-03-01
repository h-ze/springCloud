package com.hz.controller;


import com.common.entity.Document;
import com.common.entity.PageRequest;
import com.common.entity.PageResult;
import com.common.entity.ResponseResult;
import com.common.exception.RRException;
import com.hz.entity.DocumentMongo;
import com.hz.oss.cloud.OSSFactory;
import com.hz.service.DocService;
import com.hz.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@Api(tags = "文件列表接口")
@RequestMapping("DocList")
@Slf4j
public class DocController {

    private static final Logger logger = LoggerFactory.getLogger(DocController.class);

    @Autowired
    private DocService docService;

    @Autowired
    private JWTUtil jwtUtil;

    @ApiOperation(value = "更新文档信息",notes = "更新文档信息")
    @PutMapping("/updateDocument")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docId",value = "文档id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name = "encryptConfig",value = "加密策略",paramType = "query",dataType = "String"),
            @ApiImplicitParam(name = "docName",value = "文档名称",paramType = "query",dataType = "String")
    })
    public ResponseResult updateDocument(@RequestParam("docId") String docId,
                                         @RequestParam("encryptConfig")String encryptConfig,
                                         @RequestParam("docName")String docName){
        Document docServiceDocument = docService.getDocument(docId);
        docServiceDocument.setEncryptConfig(encryptConfig);
        docServiceDocument.setDocName(docName);

        int i = docService.updateDocument(docServiceDocument);
        if (i>0){
            return ResponseResult.successResult(100000,docServiceDocument);

        }else {
            return ResponseResult.errorResult(999999,"更新失败");
        }
    }

    /**
     * 获取文档列表
     * @return ConvertResult对象
     */
    @ApiOperation(value ="获取文档列表",notes="获取文档列表")
    @GetMapping("/document")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page",value = "页数",paramType = "query",dataType = "Integer",required = true),
            @ApiImplicitParam(name = "per_page",value = "每页数量",paramType = "query",dataType = "Integer",required = true)
    })
    public ResponseResult getDocList(@RequestParam("per_page")Integer per_page,
                                     @RequestParam("page")Integer page){
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        Claims claims = jwtUtil.parseJWT(principal);
        String userId = (String)claims.get("userId");
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPageNum(page);
        pageRequest.setPageSize(per_page);

        PageResult docsPage = docService.getDocsPage(pageRequest,userId);
        return ResponseResult.successResult(100000,docsPage);
    }

    @GetMapping("/getDocumentById")
    @ApiOperation(value = "获取文档信息",notes = "获取文档信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docId",value = "文档id",paramType = "query",dataType = "String",required = true)
    })
    public ResponseResult getDocumentById(@RequestParam("docId")String docId){
        Document document = docService.getDocument(docId);
        return ResponseResult.successResult(100000,document);
    }

    @ApiOperation(value = "删除文档信息",notes = "删除文档信息")
    @DeleteMapping("/document/{docId}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docId",value = "文档id",paramType = "path",dataType = "String",required = true)
    })
    public ResponseResult deleteDocuments(@PathVariable("docId") String docId){

        int i = docService.deleteDocument(docId);
        if (i>0){
            return ResponseResult.successResult(100000,"删除文档成功");
        }
        return ResponseResult.errorResult(999999,"删除文档失败");
    }

    @ApiOperation(value = "新增文档信息",notes = "新增文档信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docName",value = "文档名",paramType = "query",dataType = "String",required = false),
            @ApiImplicitParam(name = "encryptConfig",value = "加密策略",paramType = "query",dataType ="String",required = false) ,
            @ApiImplicitParam(name = "docId",value = "文档id",paramType = "query",dataType = "String",required = true)
    })
    @PostMapping("/addDocument")
    public ResponseResult addDocument(@RequestParam("docName") String docName,
                                      @RequestParam("encryptConfig") String encryptConfig,
                                      @RequestParam("docId") String docId){
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        Claims claims = jwtUtil.parseJWT(principal);
        String userId = (String)claims.get("userId");
        Document document = new Document();
        document.setUserId(userId);
        document.setCAppId("111010");
        document.setCmisId("213213");
        document.setCreateDate(new Date());
        document.setDocName(docName);
        document.setEncryptConfig(encryptConfig);
        document.setDocId(docId);
        int create = docService.createDocument(document);
        if (create>0){
            return ResponseResult.successResult(100000,document);
        }
        return ResponseResult.errorResult(999999,new Document());
    }

    /**
     * 上传文件
     */
    //@RequiresPermissions("sys:oss:all")
    @ApiOperation(value = "上传文件",notes = "上传文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "docName",value = "文档名",paramType = "query",dataType = "String",required = false),
            //@ApiImplicitParam(name = "encryptConfig",value = "加密策略",paramType = "query",dataType ="String",required = false) ,
            //@ApiImplicitParam(name = "docId",value = "文档id",paramType = "query",dataType = "String",required = true),
            @ApiImplicitParam(name ="file",value = "文件",paramType = "form",dataType = "__file",required = true)
    })
    @PostMapping("uploadFile")
    public ResponseResult upload(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            throw new RRException("上传文件不能为空");
        }
        //上传文件
        //String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        Claims claims = jwtUtil.parseJWT(principal);
        String userId = (String)claims.get("userId");
        String suffix =userId+"/"+file.getOriginalFilename();

        String docId = UUID.randomUUID().toString().replace("-", "");

        DocumentMongo documentMongo = new DocumentMongo();

        documentMongo.setDocName(file.getOriginalFilename());
        documentMongo.setClickNumber(0L);
        documentMongo.setCreateDate(new Date());
        documentMongo.setUpdateDate(new Date());
        documentMongo.setDocId(docId);
        documentMongo.setDocOwner(claims.getSubject());
        documentMongo.setId(System.currentTimeMillis());

        Document document = new Document();
        document.setUserId(userId);
        document.setCAppId("111010");
        document.setCmisId("213213");
        document.setCreateDate(new Date());
        document.setDocName(file.getOriginalFilename());
        document.setEncryptConfig("1");
        document.setDocId(docId);

        String uploadDoc = docService.uploadDoc(document,documentMongo, file.getBytes(), file.getSize(), suffix);


        //String url = OSSFactory.build().uploadSuffix(file.getBytes(),file.getSize(), suffix);
        //OSSFactory.build().uploadObject2OSS(file,"","test/");

        //保存文件信息
		/*SysOssEntity ossEntity = new SysOssEntity();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);*/

        //存储的信息需要入库
        //if (i>0){
            return ResponseResult.successResult(100000,uploadDoc);
        //}
        //return ResponseResult.errorResult(999999,new Document());
        //return ResponseResult.successResult(100000,url);

    }

}
