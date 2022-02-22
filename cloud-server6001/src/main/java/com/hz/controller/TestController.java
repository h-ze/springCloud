package com.hz.controller;

import com.common.entity.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("test")
public class TestController {

    private Logger logger = LoggerFactory.getLogger(TestController.class);

    @ApiOperation("测试")
    //@RequiresPermissions("upms:log:read")
    @RequiresRoles("admin")
    @GetMapping("test")
    public ResponseResult test(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order){
            logger.debug("测试");
            logger.error("测试");
            logger.info("测试");
        SecurityManager securityManager = SecurityUtils.getSecurityManager();
        Session session = SecurityUtils.getSubject().getSession();
        logger.info("session: {}", session.getId());
        return ResponseResult.successResult(100000,"测试成功");
    }
}
