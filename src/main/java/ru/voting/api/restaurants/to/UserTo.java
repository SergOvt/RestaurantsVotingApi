package ru.voting.api.restaurants.to;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class UserTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @Size(min = 5, max = 32)
    private String password;

    public UserTo() {
    }

    public UserTo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
