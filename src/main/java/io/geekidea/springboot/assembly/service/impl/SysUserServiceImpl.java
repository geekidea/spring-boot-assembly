package io.geekidea.springboot.assembly.service.impl;

import io.geekidea.springboot.assembly.entity.SysUser;
import io.geekidea.springboot.assembly.mapper.SysUserMapper;
import io.geekidea.springboot.assembly.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *     系统用户服务实现
 * </p>
 *
 * @author liujixiang
 * @since 2018/12/28
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public Boolean addSysUser(SysUser sysUser) {
        return sysUserMapper.addSysUser(sysUser) == 1;
    }

    @Override
    public Boolean updateSysUser(SysUser sysUser) {
        return sysUserMapper.updateSysUser(sysUser) == 1;
    }

    @Override
    public Boolean deleteSysUser(Long id) {
        return sysUserMapper.deleteSysUser(id) == 1;
    }

    @Override
    public SysUser getSysUser(Long id) {
        return sysUserMapper.getSysUser(id);
    }

    @Override
    public List<SysUser> getSysUserList() {
        return sysUserMapper.getSysUserList();
    }

}
