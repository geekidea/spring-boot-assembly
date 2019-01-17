package io.geekidea.springboot.assembly.service;

import io.geekidea.springboot.assembly.entity.SysUser;

import java.util.List;

/**
 * <p>
 *     系统用户服务接口
 * </p>
 *
 * @author liujixiang
 * @since 2018/12/28
 */
public interface SysUserService {

    /**
     * 添加
     * @param sysUser
     * @return
     */
    Boolean addSysUser(SysUser sysUser);

    /**
     * 修改
     * @param sysUser
     * @return
     */
    Boolean updateSysUser(SysUser sysUser);

    /**
     * 删除
     * @param id
     * @return
     */
    Boolean deleteSysUser(Long id);

    /**
     * 根据ID获取用户
     * @param id
     * @return
     */
    SysUser getSysUser(Long id);

    /**
     * 获取所有用户
     * @return
     */
    List<SysUser> getSysUserList();

}
