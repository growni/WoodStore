package com.WoodStore.messages.errors;
public enum ProductErrors {
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
}
