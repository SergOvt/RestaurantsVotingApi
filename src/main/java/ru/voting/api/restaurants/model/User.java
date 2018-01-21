package ru.voting.api.restaurants.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.EnumSet;
import java.util.Set;

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
    @Size(min = 5, max = 100, message = "password mast be between 5 and 100 characters")
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    @ElementCollection(fetch = FetchType.EAGER)
    @BatchSize(size = 200)
    private Set<Role> roles;

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @Formula("(SELECT v.id FROM votes v WHERE v.date = CURDATE() AND v.user_id = id)")
    @JsonIgnore
    private Integer voteId;

    public User() {
    }

    public User(String name, String email, String password, Integer voteId, Set<Role> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.voteId = voteId;
        this.roles = roles;
    }

    public User(Integer id, String name, String email, String password, Integer voteId, Set<Role> roles) {
        this(name, email, password, voteId, roles);
        this.id = id;
    }

    public User(Integer id, String name, String email, String password, Integer voteId, Role role, Role... roles) {
        this(id, name, email, password, voteId, EnumSet.of(role, roles));
    }

    public User(User user) {
        this(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getVoteId(), user.getRoles());
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

    public Integer getVoteId() {
        return voteId;
    }

    public void setVoteId(Integer voteId) {
        this.voteId = voteId;
    }

}
