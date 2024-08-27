package com.WoodStore.messages.errors;

public enum UserErrors {
    ;
    public static final String USER_NOT_FOUND_ERROR = "User does not exist.";
    public static final String USERNAME_EMPTY_ERROR = "Username can't be empty.";
    public static final String USERNAME_LENGTH_ERROR = "Username must be between 3 and 20 characters long.";
    public static final String USERNAME_INVALID_CHARACTERS_ERROR = "Username can only contain alphanumeric characters.";
    public static final String USER_PASSWORD_EMPTY_ERROR = "Password cannot be null or empty.";
    public static final String USER_PASSWORD_TOO_SHORT_ERROR = "Password must be at least 8 characters long.";
    public static final String USER_PASSWORD_TOO_LONG = "Password must be at most 50 characters long.";
    public static final String USER_PASSWORD_MISSING_UPPERCASE_ERROR = "Password must contain at least one uppercase letter.";
    public static final String USER_PASSWORD_MISSING_LOWERCASE_ERROR = "Password must contain at least one lowercase letter.";
    public static final String USER_PASSWORD_MISSING_NUMBER_ERROR = "Password must contain at least one number.";
    public static final String USER_PASSWORD_MISSING_SPECIAL_ERROR = "Password must contain at least one special character.";
    public static final String USERNAME_NOT_FOUND_ERROR = "User with username %s does not exist.";

    public static final String ROLE_NOT_FOUND_ERROR = "Role with name %s not found.";
    public static final String INVALID_ROLE_PROMOTION_HIERARCHY = "Can't manipulate the role of hierarchy level %s.";
}
