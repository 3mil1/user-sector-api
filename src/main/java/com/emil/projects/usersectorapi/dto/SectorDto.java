package com.emil.projects.usersectorapi.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public record SectorDto(
        Long id,
        String name,
        List<SectorDto> children) {

    public SectorDto {
        if (children == null) {
            children = new ArrayList<>();
        } else {
            children = Collections.unmodifiableList(new ArrayList<>(children));
        }
    }

    public SectorDto(Long id, String name) {
        this(id, name, Collections.emptyList());
    }

    public SectorDto() {
        this(null, null, Collections.emptyList());
    }

    public SectorDto withAddedChild(SectorDto child) {
        if (child == null) {
            return this;
        }

        List<SectorDto> newChildren = new ArrayList<>(this.children);
        newChildren.add(child);
        return new SectorDto(this.id, this.name, newChildren);
    }

    public SectorDto withAddedChildren(List<SectorDto> newChildren) {
        if (newChildren == null || newChildren.isEmpty()) {
            return this;
        }

        List<SectorDto> allChildren = new ArrayList<>(this.children);
        allChildren.addAll(newChildren);
        return new SectorDto(this.id, this.name, allChildren);
    }
}