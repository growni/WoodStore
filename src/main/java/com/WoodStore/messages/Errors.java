package com.WoodStore.messages;

public enum Errors {
    ;
    public static final String PRODUCT_NOT_FOUND_ERROR = "Product with id %d not found.";
    public static final String CHEAPER_PRODUCTS_NOT_FOUND_ERROR = "There are no products with price less than %.2f.";
    public static final String EXPENSIVE_PRODUCTS_NOT_FOUND_ERROR = "There are no products with price greater than %.2f.";
    public static final String AVAILABLE_PRODUCTS_NOT_FOUND_ERROR = "There are no available products at this time.";
    public static final String PRODUCT_NAME_LENGTH_ERROR = "Product name must be between 1 and 255 symbols long.";
    public static final String PRODUCT_NAME_ALREADY_EXISTS = "Product with that name already exists.";
    public static final String PRODUCT_DESCRIPTION_LENGTH_ERROR = "Product description can't be more than 1000 symbols long.";
    public static final String PRODUCT_PRICE_ERROR = "Product price must be between 100.00 and 10000.";
    public static final String PRODUCT_QUANTITY_ERROR = "Product quantity must be between 0 and 200.";
    public static final String PRODUCT_OUT_OF_STOCK_ERROR = "Product is out of stock.";
    public static final String PRODUCT_WIDTH_ERROR = "Product width must be between 1 and 500.";
    public static final String PRODUCT_HEIGHT_ERROR = "Product height must be between 1 and 300.";
    public static final String PRODUCT_WEIGHT_ERROR = "Product weight must be between 1 and 250.";
    public static final String PRODUCT_DUPLICATE_ERROR = "A product with the same name and properties already exists in the database.";
    public static final String INVALID_EMAIL_ERROR = "The email is not valid.";
    public static final String EMAIL_NOT_SUBSCRIBED = "The email is not subscribed for this product.";
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
