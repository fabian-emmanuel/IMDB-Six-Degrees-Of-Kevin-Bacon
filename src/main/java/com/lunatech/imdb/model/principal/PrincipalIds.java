package com.lunatech.imdb.model.principal;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
public class PrincipalIds implements Serializable {
    private String tconst;
    private Long ordering;
    private String nconst;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PrincipalIds that)) return false;
        if (!Objects.equals(tconst, that.tconst)) return false;
        if (!Objects.equals(ordering, that.ordering)) return false;
        return Objects.equals(nconst, that.nconst);
    }

    @Override
    public int hashCode() {
        int result = tconst != null ? tconst.hashCode() : 0;
        result = 31 * result + (ordering != null ? ordering.hashCode() : 0);
        result = 31 * result + (nconst != null ? nconst.hashCode() : 0);
        return result;
    }
}
