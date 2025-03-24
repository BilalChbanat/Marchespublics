package com.project.marchespublics.config.response;

import com.project.marchespublics.model.User;
import lombok.Getter;
import lombok.Setter;
import java.util.Collections;
import java.util.List;

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
        List<String> roles = Collections.singletonList(String.valueOf(user.getRole()));

        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles
        );
    }

    @Getter
    @Setter
    public static class UserDTO {
        private Long id;
        private String username;
        private String email;
        private List<String> roles;

        public UserDTO(Long id, String username, String email) {
            this.id = id;
            this.username = username;
            this.email = email;
        }

        public UserDTO(Long id, String username, String email, List<String> roles) {
            this.id = id;
            this.username = username;
            this.email = email;
            this.roles = roles;
        }
    }
}