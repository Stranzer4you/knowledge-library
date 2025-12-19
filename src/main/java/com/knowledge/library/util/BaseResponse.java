package com.knowledge.library.util;

import lombok.Data;

@Data
public class BaseResponse {
    private Integer status;
    private Object data;
}
