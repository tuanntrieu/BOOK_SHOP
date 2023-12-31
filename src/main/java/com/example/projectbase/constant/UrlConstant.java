package com.example.projectbase.constant;

public class UrlConstant {

    public static class Auth {
        private static final String PRE_FIX = "/auth";

        public static final String LOGIN = PRE_FIX + "/login";
        public static final String REGISTER = PRE_FIX + "/register";
        public static final String LOGOUT = PRE_FIX + "/logout";
        public static final String FORGET_PASSWORD = PRE_FIX + "/forget-password";
        public static final String CHANGE_PASSWORD = PRE_FIX + "/change-password/{username}";
        public static final String ADMIN_REGISTER = PRE_FIX + "/admin-register";

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
        public static final String GET_CUSTOMER = PRE_FIX + "/get-customer/{customerId}";
        public static final String GET_CUSTOMER_BY_USER = PRE_FIX + "/get-by-user/{userId}";
        public static final String UPDATE_CUSTOMER = PRE_FIX + "/{customerId}";
        public static final String DELETE_CUSTOMER = PRE_FIX + "/{customerId}";

        public static final String PLACE_ORDER = PRE_FIX + "/{customerId}" + "/bill";
        public static final String BUY = PRE_FIX + "/{customerId}" + "/bill" + "/{billId}";

        public static final String GET_FAVORITE_PRODUCTS = PRE_FIX + "/{customerId}/favorite-products";
        public static final String CHECK_FAVORITE_PRODUCT = PRE_FIX + "/{customerId}/favorite-products/{productId}";
        public static final String ADD_FAVORITE_PRODUCT = PRE_FIX + "/{customerId}/favorite-products/{productId}";
        public static final String REMOVE_FAVORITE_PRODUCT = PRE_FIX + "/{customerId}/favorite-products/{productId}";
        public static final String GET_COUNT_CUSTOMER = PRE_FIX + "/get-count-customer";
        public static final String UPLOAD_IMAGE = PRE_FIX + "/upload-image";

        private Customer() {
        }
    }

    public static class Product {
        private static final String PRE_FIX = "/product";
        public static final String GET_PRODUCTS = PRE_FIX + "/get-products";
        public static final String GET_PRODUCTS_ADMIN = PRE_FIX + "/get-products-admin";

        public static final String GET_PRODUCT_DETAIL = PRE_FIX + "/get-product-detail/{productId}";
        public static final String GET_PRODUCTS_BY_CATEGORY_ID = PRE_FIX + "/get-products-by-categoryId/{categoryId}";
        public static final String GET_PRODUCTS_SORT_BY_TOTAL = PRE_FIX + "/get-products-sort-by-total";
        public static final String GET_PRODUCTS_SAME_AUTHOR = PRE_FIX + "/get-products-same-author/{productId}";

        public static final String FIND_PRODUCT = PRE_FIX + "/find-product";
        public static final String CREATE_PRODUCT = PRE_FIX + "/create-product";
        public static final String UPDATE_PRODUCT = PRE_FIX + "/update-product/{productId}";
        public static final String DELETE_PRODUCT = PRE_FIX + "/delete-product/{productId}";
        public static final String ADD_IMAGE_FOR_PRODUCT = PRE_FIX + "/add-image/{productId}";
        public static final String DELETE_PRODUCT_IMAGE = PRE_FIX + "/delete-image/{productId}/{imageId}";
        public static final String UPLOAD_FEATURED_IMAGE = PRE_FIX + "/upload-featured-image/{productId}";
        public static final String GET_QUANTITY_PRODUCTS = PRE_FIX + "/get-quantity";
        public static final String FIND_PRODUCTS_ADMIN = PRE_FIX + "/find-products-admin";


        private Product() {

        }
    }

    public static class Category {
        private static final String PRE_FIX = "/category";
        public static final String CREATE_CATEGORY = PRE_FIX + "/create-category/{categoryId}";
        public static final String UPDATE_CATEGORY = PRE_FIX + "/update-category/{categoryId}";
        public static final String DELETE_CATEGORY = PRE_FIX + "/delete-category/{categoryId}";
        public static final String GET_CATEGORY = PRE_FIX + "/get-category/{categoryId}";
        public static final String GET_CATEGORIES = PRE_FIX + "/get-categories";

        private Category() {

        }
    }

    public static class Cart {
        private static final String PRE_FIX = "/cart";
        public static final String GET_CART_INFOR = PRE_FIX + "/get-cart-infor/{customerId}";
        public static final String UPDATE_CART_INFOR = PRE_FIX + "/update-cart-infor/{customerId}";
        public static final String DELETE_PRODUCT_FROM_CART = PRE_FIX + "/delete-product-from-cart/{customerId}/{productId}";
        public static final String GET_CART_TOTAL = PRE_FIX + "/total-product/{customerId}";

        private Cart() {
        }

    }

    public static class CartDetail {
        private static final String PRE_FIX = "/cart-detail";
        public static final String ADD_PRODUCT_TO_CART = PRE_FIX + "/add-product-to-cart/{customerId}";

        private CartDetail() {
        }
    }

    public static class Banner {
        private static final String PRE_FIX = "/banner";
        public static final String GET_BANNERS = PRE_FIX + "/get-banners";
        public static final String GET_BANNER = PRE_FIX + "/get-banner/{bannerId}";
        public static final String UPDATE_BANNER = PRE_FIX + "/update-banner/{bannerId}";
        public static final String DELETE_BANNER = PRE_FIX + "/delete-banner/{bannerId}";
        public static final String CREATE_BANNER = PRE_FIX + "/create-banner";

        private Banner() {
        }

    }

    public static class Bill {
        private static final String PRE_FIX = "/bill";
        public static final String ORDER_FROM_CART = PRE_FIX + "/order-from-cart/{customerId}";
        public static final String BUY_NOW = PRE_FIX + "/buy-now/{customerId}";

        public static final String CANCEL_ORDER = PRE_FIX + "/cancel-order/{customerId}/{billId}";
        public static final String CONFIRM_ORDER = PRE_FIX + "/confirm-order/{billId}";
        public static final String BUY_AGAIN = PRE_FIX + "/buy-again/{customerId}/{billId}";

        public static final String GET_BILLS = PRE_FIX + "/get-bills/{customerId}";
        public static final String GET_BILL_INFOR = PRE_FIX + "/get-bill-infor/{billId}";

        public static final String GET_COUNT_BILL = PRE_FIX + "/get-count-bill";
        public static final String GET_REVENUE = PRE_FIX + "/get-revenue";
        public static final String GET_COUNT_BILL_BY_STATUS = PRE_FIX + "/get-count-bill-by-status";
        public static final String GET_BILLS_TO_PAY = PRE_FIX + "/get-bills-to-pay";

        public static final String GET_BILLS_BY_STATUS = PRE_FIX + "/get-bills-by-status";

        public static final String GET_ALL_BILLS = PRE_FIX + "/get-all-bills";
        public static final String GET_STATISTC = PRE_FIX + "/get-statistic";

        public static final String UPDATE_STATUS = PRE_FIX + "/update-status/{billId}";


    }

}
