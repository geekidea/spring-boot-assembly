package io.geekidea.springboot.assembly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     系统用户实体类
 * </p>
 *
 * @author liujixiang
 * @since 2018/12/28
 */
@Data
public class SysUser implements Serializable {
    private static final long serialVersionUID = -6310626094486022728L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 账号
     */
    private String account;
    /**
     * pwd
     */
    private String pwd;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}
