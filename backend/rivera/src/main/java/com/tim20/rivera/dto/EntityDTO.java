package com.tim20.rivera.dto;

import com.tim20.rivera.model.EntityKind;
import com.tim20.rivera.model.Rentable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntityDTO {
    private Integer id;
    private String name;
    private String profilePicture;
    private EntityKind kind;

    public EntityDTO(Rentable rentable, EntityKind kind) {
        this.id = rentable.getId();
        this.name = rentable.getName();
        this.profilePicture = rentable.getProfilePicture();
        this.kind = kind;
    }
}
