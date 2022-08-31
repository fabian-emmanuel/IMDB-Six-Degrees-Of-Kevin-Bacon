package com.lunatech.imdb.model.actedwith;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class ActedWithIds implements Serializable {
    private String actor_id;
    private String other_actor_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActedWithIds that)) return false;

        if (!actor_id.equals(that.actor_id)) return false;
        return other_actor_id.equals(that.other_actor_id);
    }

    @Override
    public int hashCode() {
        int result = actor_id.hashCode();
        result = 31 * result + other_actor_id.hashCode();
        return result;
    }
}
