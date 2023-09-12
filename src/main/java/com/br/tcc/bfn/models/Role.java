package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_roles")
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;
    private String roleName;
    private Date createdRoleAt;
    private Date updatedRoleAt;
    private Date deletedRoleAt;

    public Role() {
    }

    public Role(Long roleId, String roleName) {
        super();
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreatedRoleAt() {
        return createdRoleAt;
    }

    public void setCreatedRoleAt(Date createdRoleAt) {
        this.createdRoleAt = createdRoleAt;
    }

    public Date getUpdatedRoleAt() {
        return updatedRoleAt;
    }

    public void setUpdatedRoleAt(Date updatedRoleAt) {
        this.updatedRoleAt = updatedRoleAt;
    }

    public Date getDeletedRoleAt() {
        return deletedRoleAt;
    }

    public void setDeletedRoleAt(Date deletedRoleAt) {
        this.deletedRoleAt = deletedRoleAt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((roleName == null) ? 0 : roleName.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Role other = (Role) obj;
        if (roleName == null) {
            if (other.roleName != null)
                return false;
        } else if (!roleName.equals(other.roleName))
            return false;
        return true;
    }
}