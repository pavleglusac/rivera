package com.tim20.rivera.dto;

import com.tim20.rivera.model.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EntityDTO {
    private Integer id;
    private String name;
    private String profilePicture;
    private EntityKind kind;

    public EntityDTO(Rentable rentable) {
        this.id = rentable.getId();
        this.name = rentable.getName();
        this.profilePicture = rentable.getPictures().get(0);
        this.kind = getEntityKind(rentable);
    }

    private EntityKind getEntityKind(Rentable entity) {
        if (entity instanceof Adventure)
            return EntityKind.ADVENTURE;
        else if (entity instanceof Cottage)
            return EntityKind.COTTAGE;
        return EntityKind.BOAT;
    }
}
