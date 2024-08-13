package com.WoodStore.entities.dtos;

import com.WoodStore.exceptions.UserError;
import lombok.Getter;
import lombok.Setter;

import static com.WoodStore.constants.Constants.*;
import static com.WoodStore.messages.Errors.*;
import static com.WoodStore.messages.Errors.USERNAME_INVALID_CHARACTERS_ERROR;

@Getter
@Setter
public class RegisterDto {
        private String username;
        private String password;
        private String newUsername;
        private String newPassword;

    public void validatePassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new UserError(USER_PASSWORD_EMPTY_ERROR);
        }

        if (password.length() < USER_PASSWORD_MIN_LENGTH) {
            throw new UserError(USER_PASSWORD_TOO_SHORT_ERROR);
        }

        if (password.length() > USER_PASSWORD_MAX_LENGTH) {
            throw new UserError(USER_PASSWORD_TOO_LONG);
        }

        if (!password.matches(USER_PASSWORD_MISSING_UPPERCASE_REGEX)) {
            throw new UserError(USER_PASSWORD_MISSING_UPPERCASE_ERROR);
        }

        if (!password.matches(USER_PASSWORD_MISSING_LOWERCASE_REGEX)) {
            throw new UserError(USER_PASSWORD_MISSING_LOWERCASE_ERROR);
        }

        if (!password.matches(USER_PASSWORD_MISSING_NUMBER_REGEX)) {
            throw new UserError(USER_PASSWORD_MISSING_NUMBER_ERROR);
        }

        if (!password.matches(USER_PASSWORD_MISSING_SPECIAL_REGEX)) {
            throw new UserError(USER_PASSWORD_MISSING_SPECIAL_ERROR);
        }
    }

    public void validateUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new UserError(USERNAME_EMPTY_ERROR);
        }

        if (username.length() < USERNAME_LENGTH_MIN || username.length() > USERNAME_LENGTH_MAX) {
            throw new UserError(USERNAME_LENGTH_ERROR);
        }

        if (!username.matches(USERNAME_VALID_REGEX)) {
            throw new UserError(USERNAME_INVALID_CHARACTERS_ERROR);
        }
    }

}
