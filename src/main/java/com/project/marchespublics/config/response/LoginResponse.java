package com.project.marchespublics.config.response;

import com.project.marchespublics.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private long expiresIn;
    private UserDTO user;

    public LoginResponse(String token, long expiresIn) {
        this.token = token;
        this.expiresIn = expiresIn;
    }

    public LoginResponse(String jwtToken, long expirationTime, User authenticatedUser) {
        this.token = jwtToken;
        this.expiresIn = expirationTime;
        this.user = convertToUserDTO(authenticatedUser);
    }

    private UserDTO convertToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

    @Getter
    @Setter
    public static class UserDTO {
        private Long id;
        private String username;
        private String email;

        public UserDTO(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }
    }
}