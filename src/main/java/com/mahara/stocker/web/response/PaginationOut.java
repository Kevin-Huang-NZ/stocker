package com.mahara.stocker.web.response;

import java.util.List;

public class PaginationOut<T> {
  private int number;
  private int size;
  private int totalElements = 0;
  private int totalPages = 0;
  private List<T> content = null;

  public PaginationOut() {
    this(1, 10);
  }

  public PaginationOut(int number, int size) {
    this.number = number;
    this.size = size;
  }

  public PaginationOut(int pageNo, int pageSize, List<T> content) {
    this.number = number;
    this.size = size;
    this.content = content;
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

  public int getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(int totalElements) {
    this.totalElements = totalElements;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }
}
