package com.lunatech.imdb.payload.dos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DegreeOfSeparationRequest {
    private String source;
    private String target = "Kevin Bacon";
}
