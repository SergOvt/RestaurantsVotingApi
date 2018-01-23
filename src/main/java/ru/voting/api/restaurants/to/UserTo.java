package ru.voting.api.restaurants.to;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import ru.voting.api.restaurants.model.User;

import javax.validation.constraints.Size;

public class UserTo {

    @NotBlank(message = "name mast not be empty")
    private String name;

    @Email(message = "email must be a valid e-mail address (example@mail.com)")
    @NotBlank(message = "email mast not be empty")
    private String email;

    @Size(min = 5, max = 32, message = "password mast be between 5 and 32 characters")
    private String password;

    public UserTo() {
    }

    public UserTo(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public UserTo (User user) {
        this(user.getName(), user.getEmail(), user.getPassword());
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
