package org.gxu.design.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 控制层返回结果封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T>{
    private T data; // 响应数据
    private String errorMsg; // 操作失败信息

    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        Result<T> res = new Result<>();
        res.setData(data);
        return res;
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public static  Result<String> error(String msg){
        return new Result<>(null,msg);
    }
}
