package com.emil.projects.usersectorapi.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = { "parent", "children", "users" })
@Entity
@Table(name = "sectors")
public class SectorEntity {

    @Id
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private SectorEntity parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<SectorEntity> children = new HashSet<>();

    @ManyToMany(mappedBy = "sectors", fetch = FetchType.LAZY)
    private Set<UserEntity> users = new HashSet<>();

    public SectorEntity(String name) {
        this.name = name;
    }

    public void addChild(SectorEntity child) {
        if (child != null && this.children.add(child)) {
            child.setParent(this);
        }
    }

    public void removeChild(SectorEntity child) {
        if (child != null && this.children.remove(child)) {
            child.setParent(null);
        }
    }
}