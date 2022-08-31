package com.lunatech.imdb.payload.dos;

import lombok.*;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DegreeOfSeparationResponse {
    private String actor;
    private String connected_to;
    private Integer bacon_no;
}
