package com.lunatech.imdb.integration

import com.google.common.base.Charsets
import com.google.common.io.Resources
import com.lunatech.imdb.payload.dos.DegreeOfSeparationRequest
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Requirement3_Spec extends Specification {

    @LocalServerPort
    private Integer apiPort;

    @Autowired
    private RestTemplate restTemplate;

    /*
        Requirement Description:

        Six degrees of Kevin Bacon: Given a query by the user, you must provide what’s the degree of
        separation between the person (e.g. actor or actress) the user has entered and Kevin Bacon.


     */

    @Unroll("request = #request, expectedOutcome = #expectedOutcome")
    def 'requirement 3 works as expected'() {

        given:
        URL resource = Resources.getResource(expectedOutcome);
        String expected = Resources.toString(resource, Charsets.UTF_8);

        String url = "http://localhost:$apiPort/api/v1/degree-of-separation"

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json")
        HttpEntity<DegreeOfSeparationRequest> httpEntity = new HttpEntity<>(request, headers);


        when:
        String results = restTemplate.exchange(
                url,
                HttpMethod.POST,
                httpEntity,
                String).body

        then:
        results
        JSONAssert.assertEquals(expected, results, true)

        where:
        request                                                            | expectedOutcome
        new DegreeOfSeparationRequest("Bruce Payne", "")                   | "requirement3_BrucePayne_KevinBacon.json"
        new DegreeOfSeparationRequest("Bruce Payne","Kevin Bacon")         | "requirement3_BrucePayne_KevinBacon.json"
        new DegreeOfSeparationRequest("Melinda Hill","Kevin Bacon")        | "requirement3_MelindaHill_KevinBacon.json"
        new DegreeOfSeparationRequest("Kevin Bacon","Kevin Bacon")         | "requirement3_KevinBacon_KevinBacon.json"

    }

}