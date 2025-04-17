package com.tramed.backend.core.base.pagination;

import java.io.Serializable;

public class SortItem implements Serializable {
  private static final long serialVersionUID = 1L;
  private String column;
  private boolean asc = true;
}
