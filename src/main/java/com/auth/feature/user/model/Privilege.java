package com.auth.feature.user.model;

import com.auth.feature.user.model.enums.PrivilegeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PrivilegeEnum type;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnoreProperties("privileges") // Ignore the fields in Role entity.
    private List<Role> roles = new ArrayList<>();

    public Privilege() {
    }

    public Privilege(PrivilegeEnum type) {
        this.type = type;
    }

    public Privilege(PrivilegeEnum type, List<Role> roles) {
        this.type = type;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrivilegeEnum getType() {
        return type;
    }

    public void setType(PrivilegeEnum type) {
        this.type = type;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}
