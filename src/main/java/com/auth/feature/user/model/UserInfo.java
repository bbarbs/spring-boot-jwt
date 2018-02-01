package com.auth.feature.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_info")
@JsonIgnoreProperties({"password"}) // Ignore password field.
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email(message = "Email must be valid")
    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private boolean enabled;

    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            // The owning entity.
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            // Non owning entity.
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    @JsonIgnoreProperties({"users", "id"}) // This ignore the fields in Role entity.
    private List<Role> roles = new ArrayList<>();

    public UserInfo() {
    }

    public UserInfo(String email, String password, String firstName, String lastName, boolean enabled) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public UserInfo(String email, String password, String firstName, String lastName, boolean enabled, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        for (Role role : roles) {
            setRole(role);
        }
    }

    public void removeRoles(List<Role> roles) {
        for (Role role : roles) {
            removeRole(role);
        }
    }

    /**
     * Add user and also to child entity role.
     *
     * @param role
     */
    public void setRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    /**
     * Remove user and also to child entity role.
     *
     * @param role
     */
    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(role);
    }
}
