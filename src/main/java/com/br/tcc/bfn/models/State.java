package com.br.tcc.bfn.models;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_states")
public class State implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stateId;
    private String stateName;
    private String uf;
    private Date createdStateAt;
    private Date deletedStateAt;

    public State() {
    }

    public Long getStateId() {
        return stateId;
    }

    public void setStateId(Long stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Date getCreatedStateAt() {
        return createdStateAt;
    }

    public void setCreatedStateAt(Date createdStateAt) {
        this.createdStateAt = createdStateAt;
    }

    public Date getDeletedStateAt() {
        return deletedStateAt;
    }

    public void setDeletedStateAt(Date deletedStateAt) {
        this.deletedStateAt = deletedStateAt;
    }
}
