package com.tramed.backend.core.base.pagination;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class PageRequest implements Serializable {
  @Serial private static final long serialVersionUID = -4541509938956089562L;

  private int pageNumber;
  private int pageSize;
  private int offset;
  private List<SortItem> sortItems;

  public PageRequest(int pageNumber, int pageSize, List<SortItem> sortItems) {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.offset = (pageNumber - 1) * pageSize;
    this.sortItems = sortItems;
  }
}
