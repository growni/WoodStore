package com.WoodStore.constants;

public enum Constants {
    ;
    public static final int PRODUCT_NAME_MAX_LENGTH = 255;
    public static final int PRODUCT_NAME_MIN_LENGTH = 1;
    public static final int PRODUCT_DESCRIPTION_MAX_LENGTH = 1000;
    public static final double PRODUCT_PRICE_MIN = 100;
    public static final double PRODUCT_PRICE_MAX = 10000;
    public static final int PRODUCT_QUANTITY_MAX = 200;
    public static final int PRODUCT_QUANTITY_MIN = 0;
    public static final int PRODUCT_WIDTH_MIN = 1;
    public static final int PRODUCT_WIDTH_MAX = 500;
    public static final int PRODUCT_HEIGHT_MIN = 1;
    public static final int PRODUCT_HEIGHT_MAX = 300;
    public static final int PRODUCT_WEIGHT_MIN = 1;
    public static final int PRODUCT_WEIGHT_MAX = 250;
    public static final int USERNAME_LENGTH_MIN = 3;
    public static final int USERNAME_LENGTH_MAX = 20;
    public static final String USERNAME_VALID_REGEX = "^[a-zA-Z0-9]+$";
    public static final int USER_PASSWORD_MIN_LENGTH = 8;
    public static final int USER_PASSWORD_MAX_LENGTH = 50;
    public static final String USER_PASSWORD_MISSING_UPPERCASE_REGEX = ".*[A-Z].*";
    public static final String USER_PASSWORD_MISSING_LOWERCASE_REGEX = ".*[a-z].*";
    public static final String USER_PASSWORD_MISSING_NUMBER_REGEX = ".*\\d.*";
    public static final String USER_PASSWORD_MISSING_SPECIAL_REGEX = ".*[!@#$%^&*(),.?\":{}|<>].*";
    public static final int JWT_TOKEN_EXPIRE_AFTER = 3600000; // 1 hour
}
