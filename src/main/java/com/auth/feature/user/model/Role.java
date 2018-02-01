package com.auth.feature.user.model;

import com.auth.feature.user.model.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum type;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles") // Ignore the fields in UserInfo entity.
    private List<UserInfo> users = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "roles_privileges",
            // The owning entity.
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            ),
            // The non owning entity.
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"
            )
    )
    @JsonIgnoreProperties({"roles", "id"}) // This ignore the fields in the Privilege entity.
    private List<Privilege> privileges = new ArrayList<>();

    public Role() {
    }

    public Role(RoleEnum type) {
        this.type = type;
    }

    public Role(RoleEnum type, List<UserInfo> users, List<Privilege> privileges) {
        this.type = type;
        this.users = users;
        this.privileges = privileges;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleEnum getType() {
        return type;
    }

    public void setType(RoleEnum type) {
        this.type = type;
    }

    public List<UserInfo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInfo> users) {
        this.users = users;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        for (Privilege privilege : privileges) {
            setPrivilege(privilege);
        }
    }

    public void removePrivileges(List<Privilege> privileges) {
        for (Privilege privilege : privileges) {
            removePrivilege(privilege);
        }
    }

    /**
     * Add role and also to child entity privilege.
     *
     * @param privilege
     */
    public void setPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
        privilege.getRoles().add(this);
    }

    /**
     * Remove role and also to child entity privilege.
     *
     * @param privilege
     */
    public void removePrivilege(Privilege privilege) {
        this.privileges.remove(privilege);
        privilege.getRoles().remove(this);
    }
}
