package com.mahara.stocker.util;

import com.alibaba.fastjson.JSON;
import com.mahara.stocker.GlobalConst;
import com.mahara.stocker.web.response.Root;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ResponseUtil {
  public static void out(HttpServletRequest request, HttpServletResponse response, Root data) {
    List<String> headers =
        Arrays.asList(
            GlobalConst.TOKEN_HEADER,
            "Accept",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers",
            "Accept-Language",
            "Authorization",
            "Content-Type",
            "Request-Name",
            "Request-Surname",
            "Origin");
    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", String.join(",", headers));
    response.setContentType("application/json;charset=UTF-8");

    try {
      response.getWriter().write(JSON.toJSONString(data));
    } catch (IOException e) {
      log.error("响应流写入失败。", e);
    }
  }
}
