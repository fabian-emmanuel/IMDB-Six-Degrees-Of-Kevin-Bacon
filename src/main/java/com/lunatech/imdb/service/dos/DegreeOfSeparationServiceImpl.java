package com.lunatech.imdb.service.dos;


import com.lunatech.imdb.exceptions.InvalidRequestException;
import com.lunatech.imdb.payload.dos.DegreeOfSeparationRequest;
import com.lunatech.imdb.payload.dos.DegreeOfSeparationResponse;
import com.lunatech.imdb.payload.dos.IDegreeOfSeparation;
import com.lunatech.imdb.repository.actedwith.ActedWithRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DegreeOfSeparationServiceImpl implements DegreeOfSeparationService {
    private final ActedWithRepository baseRepository;
    private static final String TARGET_NAME = "Kevin Bacon";

    @Override
    public DegreeOfSeparationResponse processDegreeOfSeparation(DegreeOfSeparationRequest degreeOfSeparationRequest) {
        if (degreeOfSeparationRequest.getSource().isEmpty()) {
            throw new InvalidRequestException("Source name is empty");
        }

        if (degreeOfSeparationRequest.getTarget().isEmpty()) {
            degreeOfSeparationRequest.setTarget(TARGET_NAME);
        }

        if(!nameExists(degreeOfSeparationRequest.getSource())) {
            throw new InvalidRequestException("Source name does not exist");
        }

        IDegreeOfSeparation res=  this.baseRepository.findDegreeOfSeparation(degreeOfSeparationRequest.getSource(), degreeOfSeparationRequest.getTarget());

        return DegreeOfSeparationResponse.builder()
                .actor(res.getActor())
                .connected_to(res.getConnectedto())
                .bacon_no(res.getBacon())
                .build();
    }

    private boolean nameExists(String name) {
        return this.baseRepository.existsByActorName(name);
    }
}
