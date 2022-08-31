package com.lunatech.imdb.service.dos;

import com.lunatech.imdb.payload.dos.DegreeOfSeparationRequest;
import com.lunatech.imdb.payload.dos.DegreeOfSeparationResponse;

public interface DegreeOfSeparationService {
    DegreeOfSeparationResponse processDegreeOfSeparation(DegreeOfSeparationRequest degreeOfSeparationRequest);
}
