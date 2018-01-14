package ru.voting.api.restaurants.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.EnumSet;
import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "user.delete", query = "DELETE FROM User u WHERE u.id=:id"),
        @NamedQuery(name = "user.getAll", query = "SELECT u FROM User u LEFT JOIN FETCH u.roles"),
        @NamedQuery(name = "user.byEmail", query = "SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.roles WHERE u.email=:email")
})
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name mast not be empty")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "email must be a valid e-mail address (example@mail.com)")
    @NotBlank(message = "email mast not be empty")
    private String email;

    @Column(name = "password", nullable = false)
    @Size(min = 5, max = 32, message = "password mast have a length between 5 and 32 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    public User() {
    }

    public User(String name, String email, String password, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(String name, String email, String password, Role role, Role... roles) {
        this(name, email, password, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String email, String password, Set<Role> roles) {
        this(name, email, password, roles);
        this.id = id;
    }

    public User(Integer id, String name, String email, String password, Role role, Role... roles) {
        this(id, name, email, password, EnumSet.of(role, roles));
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRoles());
        this.enabled = user.isEnabled();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
