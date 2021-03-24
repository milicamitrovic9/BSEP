package com.ftn.bsep.model;

import javax.persistence.*;

@Entity
public class InvalidateAlias {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "Alias", nullable = false)
    private String alias;

    public InvalidateAlias() {
    }

    public InvalidateAlias(Long id, String alias) {
        this.id = id;
        this.alias = alias;
    }

    public InvalidateAlias(String alias) {
        this.alias = alias;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}

