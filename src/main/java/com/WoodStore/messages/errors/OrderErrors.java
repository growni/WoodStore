package com.WoodStore.messages.errors;

public enum OrderErrors {
    ;
    public static final String ORDER_NOT_FOUND_ERROR = "Order with id %d not found.";
    public static final String INVALID_IMAGE_URL = "The image url is not valid.";
    public static final String BASKET_NOT_FOUND = "Basket with id %d not found";
    public static final String INVALID_ORDER_RECIPIENT = "Recipient information is required to place the order.";
    public static final String INVALID_ORDER_RECIPIENT_EMAIL = "The recipient's email is empty.";
    public static final String INVALID_ORDER_RECIPIENT_FIRST_NAME = "Invalid order recipient first name.";
    public static final String INVALID_ORDER_RECIPIENT_LAST_NAME = "Invalid order recipient last name.";
    public static final String INVALID_ORDER_RECIPIENT_PHONE = "Invalid order recipient phone number.";
    public static final String INVALID_ORDER_CARRIER= "Carrier information is required to place the order.";
    public static final String INVALID_ORDER_CARRIER_CITY = "Invalid order carrier city.";
    public static final String INVALID_ORDER_CARRIER_REGION = "Invalid order carrier region.";
    public static final String INVALID_ORDER_CARRIER_ADDRESS = "Invalid order carrier address.";
    public static final String INVALID_ORDER_BASKET = "Basket is required to place the order.";
}
