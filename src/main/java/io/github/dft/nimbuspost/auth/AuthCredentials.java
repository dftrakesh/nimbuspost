package io.github.dft.nimbuspost.auth;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;

    public AuthCredentials(String email, String password) {
        this.email = email;
        this.password = password;
    }
}