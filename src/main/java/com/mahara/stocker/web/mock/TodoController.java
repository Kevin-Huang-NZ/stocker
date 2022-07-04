package com.mahara.stocker.web.mock;

import com.mahara.stocker.web.response.Root;
import com.mahara.stocker.web.vo.TodoItem;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@Tag(name = "TODO List模拟数据接口")
@RestController
@RequestMapping("/api")
public class TodoController {
  @Operation(summary = "TODO List模拟数据")
  @GetMapping("/todolist")
  public Root todolist() {
    var todoList = new ArrayList<TodoItem>();
    todoList.add(new TodoItem("Racing car sprays burning fuel into crowd."));
    todoList.add(new TodoItem("Japanese princess to wed commoner."));
    todoList.add(new TodoItem("Australian walks 100km after outback crash."));
    todoList.add(new TodoItem("Man charged over missing wedding girl."));
    todoList.add(new TodoItem("Los Angeles battles huge wildfires."));
    return Root.create(todoList);
  }

  @Operation(summary = "热门搜索模拟数据")
  @GetMapping("/popular")
  public Root popular() {
    var popularSearch = new String[]{
        "AA Insurance", "ReactJs", "房产投资", "FML", "俄乌战争",
        "王晓霞", "动态清零", "躺平", "covid19", "疫苗副作用",
        "互联网大厂", "高薪整治", "央行降息", "三体电影", "第二职业",
        "天花板", "一去不回", "上海疫情", "张国荣", "刘德华",
        "周润发"
    };
    return Root.create(popularSearch);
  }

  @Operation(summary = "主题模拟数据")
  @GetMapping("/topic")
  public Root topic() {
    var topics = new Topic[]{
        new Topic(2l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "大众文学"),
        new Topic(3l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "探索"),
        new Topic(4l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "手工"),
        new Topic(6l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "足球"),
        new Topic(7l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "制品"),
        new Topic(8l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "直播"),
        new Topic(9l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "街头健身"),
        new Topic(10l, "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg", "科学上网")
    };
    return Root.create(topics);
  }

  @Operation(summary = "文章模拟数据")
  @GetMapping("/posts")
  public Root posts() {
    var posts =
        new Post[] {
          new Post(
              1l,
              "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg",
              "窝囊二三事", "昨天老公值班，我又一夜没睡。自从父亲去世，我就一直不敢一个人睡。一闭上眼睛，父亲离开时的情形，他看我的眼神，就浮现出来。我不敢关灯，关上灯就会更...", ""),

          new Post(
              2l,
              "",
              "窝囊二三事2", "昨天老公值班，我又一夜没睡。自从父亲去世，我就一直不敢一个人睡。一闭上眼睛，父亲离开时的情形，他看我的眼神，就浮现出来。我不敢关灯，关上灯就会更...", ""),

          new Post(
              3l,
              "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg",
              "窝囊二三事3", "昨天老公值班，我又一夜没睡。自从父亲去世，我就一直不敢一个人睡。一闭上眼睛，父亲离开时的情形，他看我的眼神，就浮现出来。我不敢关灯，关上灯就会更...", ""),

          new Post(
              4l,
              "",
              "窝囊二三事4", "昨天老公值班，我又一夜没睡。自从父亲去世，我就一直不敢一个人睡。一闭上眼睛，父亲离开时的情形，他看我的眼神，就浮现出来。我不敢关灯，关上灯就会更...", ""),

          new Post(
              5l,
              "https://lupic.cdn.bcebos.com/20210629/2001373470_14.jpg",
              "窝囊二三事5", "昨天老公值班，我又一夜没睡。自从父亲去世，我就一直不敢一个人睡。一闭上眼睛，父亲离开时的情形，他看我的眼神，就浮现出来。我不敢关灯，关上灯就会更...", "")

        };
    return Root.create(posts);
  }
}
