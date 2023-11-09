package com.example.projectbase.constant;

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

}
