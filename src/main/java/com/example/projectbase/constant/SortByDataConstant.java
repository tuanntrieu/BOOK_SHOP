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

    PRODUCT {
        @Override
        public String getSortBy(String sortBy) {
            if ("name".equals(sortBy)) {
                return "name";
            } else if ("createdDate".equals(sortBy))
                return "createdDate";
            else if ("selled".equals(sortBy))
                return "selled";
            else if ("discount".equals(sortBy))
                return "discount";
            else if ("quantity".equals(sortBy))
                return "quantity";
            return "price";
        }
    },
    CATEGORY {
        @Override
        public String getSortBy(String sortBy) {

            return "price";
        }
    },
    CUSTOMER {
        @Override
        public String getSortBy(String sortBy) {
            if (sortBy.equals("address"))
                return "address";
            return "name";
        }
    },
    BILL {
        @Override
        public String getSortBy(String sortBy) {
            return "lastModifiedDate";
        }
    },

    BANNER{
        @Override
        public String getSortBy(String sortBy) {
            return "last_modified_date";
        }
    }


}
