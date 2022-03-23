/**
 * Author:   Herewe
 * Date:     2022/3/6 10:11
 * Description:
 */
package com.example.testtool.common;

import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;

@Data
public class BaseResponse {
    private Integer code;
    private String message;
    private Object data;

    public static BaseResponse success(Object data) {
        BaseResponse result = new BaseResponse();
        result.setCode(200);
        result.setMessage("success");
        result.setData(data);
        return result;
    }
}
