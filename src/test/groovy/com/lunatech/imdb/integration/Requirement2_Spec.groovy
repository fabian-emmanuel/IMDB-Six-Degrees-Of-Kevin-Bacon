package com.lunatech.imdb.integration

import com.google.common.base.Charsets
import com.google.common.io.Resources
import org.skyscreamer.jsonassert.JSONAssert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Requirement2_Spec extends Specification {
    @LocalServerPort
    private Integer apiPort;

    @Autowired
    private RestTemplate restTemplate;

    /*
        Requirement Description:

        Top rated movies: Given a query by the user, you must provide what are the top rated movies
        for a genre (If the user searches horror, then it should show a list of top rated horror movies).

     */

    @Unroll("fullFetch = #fullFetch, expectedOutcome = #expectedOutcome")
    def 'requirement 2 works as expected'() {

        given:
        URL resource = Resources.getResource(expectedOutcome);
        String expected = Resources.toString(resource, Charsets.UTF_8);

        String url = "http://localhost:$apiPort/api/v1/search?page=0&size=1"


        String completeUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("genre", "Horror")
                .queryParam("full-fetch", fullFetch)
                .toUriString()

        when:
        String results = restTemplate.exchange(
                completeUrl,
                HttpMethod.GET,
                null,
                String).body

        then:
        results
        JSONAssert.assertEquals(expected, results, true)

        where:
        fullFetch | expectedOutcome
        "false"   | "requirement2_no_full_fetch_response.json"
        "true"    | "requirement2_full_fetch_response.json"

    }


}