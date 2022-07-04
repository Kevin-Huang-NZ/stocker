package com.mahara.stocker.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.mahara.stocker.core.model.User;
import com.mahara.stocker.util.MockDataFactory;
import com.mahara.stocker.web.request.PaginationIn;
import com.mahara.stocker.web.vo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.text.MessageFormat;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {
  private static User newUser = null;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private MockDataFactory mdf;

  @BeforeAll
  public static void initData() {
    log.debug("initData");

    newUser = new User();
    newUser.setUserName("王重阳");
    newUser.setGender("1");
    newUser.setPhone("13000000000");
    newUser.setPassword("1234Qwer");
    newUser.setStatus("1");
  }

  @Test
  public void searchWithoutAuthentication() throws Exception {
    log.debug("searchWithoutAuthentication");

    this.mockMvc.perform(get("/api/user/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JSONObject.toJSONString(new PaginationIn()))
    )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("error")))
        .andExpect(jsonPath("$.data.errorCode", is(900403)))
        .andExpect(jsonPath("$.data.errorMessage", notNullValue()));
  }

  @Order(1)
  @Test
  public void create() throws Exception {
    log.debug("create");

    var result = this.mockMvc.perform(post("/api/user/create")
            .header("Authorization", mdf.jwt())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(newUser))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("success")))
        .andExpect(jsonPath("$.data.id", notNullValue()))
        .andReturn();
    Long newId = JSONPath.read(result.getResponse().getContentAsString(), "$.data.id", Long.class);
    newUser.setId(newId);
  }

  @Order(2)
  @Test
  public void searchByKeyword() throws Exception {
    log.debug("searchByKeyword");

    this.mockMvc.perform(get("/api/user/")
            .header("Authorization", mdf.jwt())
            .contentType(MediaType.APPLICATION_JSON)
            .param("keyword", newUser.getPhone())
            .content(JSONObject.toJSONString(new PaginationIn()))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("success")))
        .andExpect(jsonPath("$.data.content.length()", equalTo(1)));
  }

  @Order(3)
  @Test
  public void search() throws Exception {
    log.debug("search");

    this.mockMvc.perform(get("/api/user/")
            .header("Authorization", mdf.jwt())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(new PaginationIn()))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("success")))
        .andExpect(jsonPath("$.data.content.length()", greaterThan(0)));
  }

  @Order(4)
  @Test
  public void update() throws Exception {
    log.debug("update");
    newUser.setBirthday("1981-06-02");

    this.mockMvc.perform(post("/api/user/update")
            .header("Authorization", mdf.jwt())
            .contentType(MediaType.APPLICATION_JSON)
            .content(JSONObject.toJSONString(newUser))
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("success")));
  }

  @Order(5)
  @Test
  public void findOne() throws Exception {
    log.debug("findOne");

    String url = MessageFormat.format("/api/user/{0}", newUser.getId());
    this.mockMvc.perform(get(url)
            .header("Authorization", mdf.jwt())
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("success")))
        .andExpect(jsonPath("$.data.birthday", is("1981-06-02")));
  }

  @Order(9)
  @Test
  public void delete() throws Exception {
    log.debug("delete");

    String url = MessageFormat.format("/api/user/{0}/del", newUser.getId());
    this.mockMvc.perform(post(url)
            .header("Authorization", mdf.jwt())
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.status", is("success")));
  }
}
