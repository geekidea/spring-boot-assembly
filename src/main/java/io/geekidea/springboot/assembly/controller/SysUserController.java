package io.geekidea.springboot.assembly.controller;

import io.geekidea.springboot.assembly.entity.SysUser;
import io.geekidea.springboot.assembly.service.SysUserService;
import io.geekidea.springboot.assembly.vo.ApiResult;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统用户控制器
 * @author geekidea
 * @date 2018/12/28
 */
@Log
@RestController
@RequestMapping(value = "/sysUser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    @RequestMapping("/add")
    public ApiResult addSysUser(SysUser sysUser){
        Boolean flag = sysUserService.addSysUser(sysUser);
        return ApiResult.result(flag,"保存用户");
    }

    /**
     * 修改用户
     * @param sysUser
     * @return
     */
    @RequestMapping("/update")
    public ApiResult updateSysUser(SysUser sysUser){
        Boolean flag = sysUserService.updateSysUser(sysUser);
        return ApiResult.result(flag,"修改用户");
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    @PostMapping("/delete")
    public ApiResult updateSysUser(Long id){
        Boolean flag = sysUserService.deleteSysUser(id);
        return ApiResult.result(flag,"删除用户");
    }

    /**
     * 根据ID查询用户
     * @param id
     * @return
     */
    @GetMapping("/get/{id}")
    public ApiResult getSysUser(@PathVariable("id") Long id){
        SysUser sysUser = sysUserService.getSysUser(id);
        log.info("sysUser:" + sysUser);
        return ApiResult.ok(sysUser);
    }

    /**
     * 获取用户列表
     * @return
     */
    @RequestMapping("/list")
    public ApiResult getSysUserList(){
        List<SysUser> sysUsers = sysUserService.getSysUserList();
        log.info("sysUser:" + sysUsers);
        return ApiResult.ok(sysUsers);
    }

}
