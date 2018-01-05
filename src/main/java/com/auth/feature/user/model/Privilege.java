package com.auth.feature.user.model;

import com.auth.feature.user.model.enums.PrivilegeType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PrivilegeType type;

    @ManyToMany(mappedBy = "privileges", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("privileges")
    private Collection<Role> roles;

    public Privilege() {
    }

    public Privilege(PrivilegeType type, Collection<Role> roles) {
        this.type = type;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PrivilegeType getType() {
        return type;
    }

    public void setType(PrivilegeType type) {
        this.type = type;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}
