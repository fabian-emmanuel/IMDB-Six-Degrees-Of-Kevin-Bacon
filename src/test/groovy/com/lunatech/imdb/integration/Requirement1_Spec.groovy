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
class Requirement1_Spec extends Specification {
    @LocalServerPort
    private Integer apiPort;

    @Autowired
    private RestTemplate restTemplate;

    /*
        Requirement Description:

        IMDb copycat: Present the user with endpoint for allowing them to search
        by movie’s primary title or original title.
        The outcome should be related information to that title, including cast and crew.

     */

    @Unroll("fullFetch = #fullFetch, expectedOutcome = #expectedOutcome")
    def 'requirement 1 works as expected'() {

        given:
        URL resource = Resources.getResource(expectedOutcome);
        String expected = Resources.toString(resource, Charsets.UTF_8);

        String url = "http://localhost:$apiPort/api/v1/search?page=0&size=1"


        String completeUrl = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("title", "Hamlet")
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
        "false"   | "requirement1_no_full_fetch_response.json"
        "true"    | "requirement1_full_fetch_response.json"
    }
}
