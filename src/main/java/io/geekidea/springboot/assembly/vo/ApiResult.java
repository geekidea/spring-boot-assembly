package io.geekidea.springboot.assembly.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 *     Api响应对象
 * </p>
 *
 * @author liujixiang
 * @since 2018/12/28
 */
@Data
@Builder
public class ApiResult  implements Serializable {

    private static final String SUCCESS = "成功";
    private static final String FAILED = "失败";
    private static final String OPERATION_SUCCESS = "操作成功";
    private static final String OPERATION_FAILED = "操作失败";

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String msg;

    /**
     * 响应数据
     */
    private Object data;

    /**
     * 响应时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss:S")
    private Date time;

    /**
     * 根据条件判断，返回操作结果
     * @param flag
     * @param msgPrefix
     * @return
     */
    public static ApiResult result(Boolean flag,String msgPrefix){
        if (flag){
            return ok(msgPrefix + SUCCESS);
        }
        return failed(msgPrefix + FAILED);
    }

    /**
     * 操作成功
     * @return
     */
    public static ApiResult ok(){
        return ok(null);
    }

    /**
     * 操作成功
     * @param msg
     * @return
     */
    public static ApiResult ok(String msg){
        return ok(msg,null);
    }

    /**
     * 操作成功
     * @param data
     * @return
     */
    public static ApiResult ok(Object data){
        return ok(null,data);
    }

    /**
     * 操作成功
     * @param msg
     * @param data
     * @return
     */
    public static ApiResult ok(String msg,Object data){
        if (msg == null){
            msg = OPERATION_SUCCESS;
        }
        return ApiResult.builder().code(200).msg(msg).data(data).time(new Date()).build();
    }

    /**
     * 操作失败
     * @return
     */
    public static ApiResult failed(){
        return failed(null);
    }

    /**
     * 操作失败
     * @param msg
     * @return
     */
    public static ApiResult failed(String msg){
        return failed(msg,null);
    }

    /**
     * 操作失败
     * @param data
     * @return
     */
    public static ApiResult failed(Object data){
        return failed(null,data);
    }


    /**
     * 操作失败
     * @param msg
     * @param data
     * @return
     */
    public static ApiResult failed(String msg,Object data){
        if (msg == null){
            msg = OPERATION_FAILED;
        }
        return ApiResult.builder().code(500).msg(msg).data(data).time(new Date()).build();
    }

}
