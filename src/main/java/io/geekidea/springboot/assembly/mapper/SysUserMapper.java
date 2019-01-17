package io.geekidea.springboot.assembly.mapper;

import io.geekidea.springboot.assembly.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *     系统用户Mapper
 * </p>
 *
 * @author liujixiang
 * @since 2018/12/28
 */
@Repository
public interface SysUserMapper {

    /**
     * 添加
     * @param sysUser
     * @return
     */
    Integer addSysUser(SysUser sysUser);

    /**
     * 修改
     * @param sysUser
     * @return
     */
    Integer updateSysUser(SysUser sysUser);

    /**
     * 删除
     * @param id
     * @return
     */
    Integer deleteSysUser(Long id);

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
