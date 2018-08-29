package cn.likegirl.rt.controller;

import cn.likegirl.rt.constant.ResultCode;
import cn.likegirl.rt.framework.response.PlatformResult;
import cn.likegirl.rt.framework.response.Result;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class RestNotFoundFilter implements ErrorController {

    private static final String NOT_FOUND = "404";
    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Result handleError() {
        return PlatformResult.failure(ResultCode.INTERFACE_ADDRESS_INVALID);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

}