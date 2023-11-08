package com.example.projectbase.constant;

public enum SortByDataConstant implements SortByInterface {

  USER {
    @Override
    public String getSortBy(String sortBy) {
      switch (sortBy) {
        case "name":
          return "name";
        case "lastModifiedDate":
          return "last_modified_date";
        default:
          return "created_date";
      }
    }
  },

}
