package com.mahara.stocker.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.mahara.stocker.util.MockDataFactory;
import com.mahara.stocker.web.vo.LoginUser;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private MockDataFactory mdf;

  @Test
  public void loginSuccess() throws Exception {
//    UrlEncodedFormEntity formData = new UrlEncodedFormEntity(Arrays.asList(
//        new BasicNameValuePair("userName", "13811650908"),
//        new BasicNameValuePair("password", "1234Qwer")
//        ), "utf-8");
//    this.mockMvc.perform(post("/api/login")
//        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
//        .content(EntityUtils.toString(formData))
//    ).andExpect(status().isOk()).andReturn();

    this.mockMvc.perform(post("/api/login")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(mdf.loginUser()))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("success")))
        .andExpect(jsonPath("$.data", notNullValue()));
  }

  @Test
  public void loginFailedWrongPassword() throws Exception {
    LoginUser loginUser = new LoginUser();
    loginUser.setUserName("13811650908");
    loginUser.setPassword("wrongpassword");
    this.mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(loginUser))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.data", notNullValue()))
        .andExpect(jsonPath("$.data.errorCode", is(900401)))
        .andExpect(jsonPath("$.data.errorMessage", notNullValue()));
  }

  @Test
  public void loginFailedInvalidPhone() throws Exception {
    LoginUser loginUser = new LoginUser();
    loginUser.setUserName("not exists");
    loginUser.setPassword("wrongpassword");
    this.mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(loginUser))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.data", notNullValue()))
        .andExpect(jsonPath("$.data.errorCode", is(900400)))
        .andExpect(jsonPath("$.data.errorMessage", notNullValue()));
  }

  @Test
  public void loginFailedNotExists() throws Exception {
    LoginUser loginUser = new LoginUser();
    loginUser.setUserName("13999999999");
    loginUser.setPassword("wrongpassword");
    this.mockMvc.perform(post("/api/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(loginUser))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.data", notNullValue()))
        .andExpect(jsonPath("$.data.errorCode", is(900401)))
        .andExpect(jsonPath("$.data.errorMessage", notNullValue()));
  }
}
