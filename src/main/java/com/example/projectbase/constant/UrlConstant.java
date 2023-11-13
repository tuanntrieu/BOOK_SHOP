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
        public static final String GET_PRODUCTS_BY_CATEGORY_ID = PRE_FIX + "/get-products-by-categoryId/{categoryId}";
        public static final String FIND_PRODUCT = PRE_FIX + "/find-product";
        public static final String CREATE_PRODUCT=PRE_FIX+"/create-product";
        public static final String UPDATE_PRODUCT=PRE_FIX+"/update-product/{productId}";
        public static final String DELETE_PRODUCT=PRE_FIX+"/delete-product/{productId}";


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

    public static class Cart{
        private static final String PRE_FIX = "/cart";
        public static final String GET_CART_INFOR=PRE_FIX+"/get-cart-infor/{userId}";
        public static final String UPDATE_CART_INFOR=PRE_FIX+"/update-cart-infor/{userId}";
        public static final String DELETE_PRODUCT_FROM_CART=PRE_FIX+"/delete-product-from-cart/{userId}/{productId}";

    }

    public static class CartDetail{
        private static final String PRE_FIX = "/cart-detail";
        public static final String ADD_PRODUCT_TO_CART=PRE_FIX+"/add-product-to-cart/{userId}";

    }

}
