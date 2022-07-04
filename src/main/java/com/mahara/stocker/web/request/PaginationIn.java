package com.mahara.stocker.web.request;

import javax.validation.constraints.Min;

public class PaginationIn {
  @Min(value = 0l, message = "分页条件的页码不正确，应为0或正整数。")
  private int number;

  @Min(value = 1l, message = "分页条件的页大小不正确，应为正整数。")
  private int size;

  public PaginationIn() {
    this(0, 10);
  }

  public PaginationIn(int number, int size) {
    this.number = number;
    this.size = size;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public int getSize() {
    return size;
  }

  public void setSize(int size) {
    this.size = size;
  }
}
