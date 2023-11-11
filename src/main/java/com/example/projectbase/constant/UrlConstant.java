package com.example.projectbase.constant;

import com.example.projectbase.domain.entity.Category;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";
        public static final String REGISTER = PRE_FIX + "/register";
        public static final String LOGOUT = PRE_FIX + "/logout";
        public static final String FORGET_PASSWORD = PRE_FIX + "/forget-password";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password/{username}";

        private Auth() {
        }
    }

    public static class User {
        private static final String PRE_FIX = "/user";

        public static final String GET_USERS = PRE_FIX;
        public static final String GET_USER = PRE_FIX + "/{userId}";
        public static final String GET_CURRENT_USER = PRE_FIX + "/current";

        private User() {
        }
    }

    public static class Customer {
        private static final String PRE_FIX = "/customer";

        public static final String GET_CUSTOMERS = PRE_FIX;
        public static final String GET_CUSTOMER = PRE_FIX + "/{customerId}";
        public static final String UPDATE_CUSTOMER = PRE_FIX + "/{customerId}";
        public static final String DELETE_CUSTOMER = PRE_FIX + "/{customerId}";

        public static final String PLACE_ORDER = PRE_FIX + "/{customerId}" + "/bill";
        public static final String BUY = PRE_FIX + "/{customerId}" + "/bill" + "/{billId}";

        private Customer() {
        }
    }

    public static class Product {
        private static final String PRE_FIX = "/product";
        public static final String GET_PRODUCTS = PRE_FIX + "/get-products";
        public static final String GET_PRODUCT_DETAIL = PRE_FIX + "/get-product-detail/{productId}";

        private Product() {

        }
    }
    public static class Category{
        private static final String PRE_FIX = "/category";
        public static final String CREATE_CATEGORY=PRE_FIX+"/create-category";
        public static final String UPDATE_CATEGORY=PRE_FIX+"update-category/{categoryId}";
        public static final String DELETE_CATEGORY=PRE_FIX+"/delete-category/{categoryId}";
        public static final String GET_CATEGORIES=PRE_FIX+"/get-categories";

        private Category(){

        }
    }

}
