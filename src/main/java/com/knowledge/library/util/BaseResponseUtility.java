package com.knowledge.library.util;

import lombok.Data;

@Data
public class BaseResponseUtility {

    public static BaseResponse getBaseResponse(Object data) {
        BaseResponse response = new BaseResponse();
        response.setData(data);
        response.setStatus(200);
        return response;
    }

    public static BaseResponse getBaseResponse() {
        BaseResponse response = new BaseResponse();
        response.setStatus(200);
        return response;

    }
}

