package com.mahara.stocker.web.mock;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
  private long id;
  private String image;
  private String topic;
}
