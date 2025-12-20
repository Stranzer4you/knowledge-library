package com.knowledge.library.util;

import lombok.Data;

@Data
public class BaseResponseUtility {

    public static BaseResponse getBaseResponse(Object data) {
        BaseResponse response = new BaseResponse();
        response.setData(data);
        return response;
    }

    public static BaseResponse getBaseResponse() {
        return new BaseResponse();
    }
}

