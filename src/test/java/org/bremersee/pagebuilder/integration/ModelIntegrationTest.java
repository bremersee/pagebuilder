/*
 * Copyright 2020-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.bremersee.pagebuilder.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.bremersee.pagebuilder.model.CommonPageDto;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.AddressPage;
import org.bremersee.pagebuilder.testmodel.AnimalPage;
import org.bremersee.pagebuilder.testmodel.Cat;
import org.bremersee.pagebuilder.testmodel.Dog;
import org.bremersee.pagebuilder.testmodel.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * The model integration test.
 *
 * @author Christian Bremer
 */
@SpringBootTest(
    classes = {ModelIntegrationTestConfiguration.class},
    webEnvironment = WebEnvironment.RANDOM_PORT
)
class ModelIntegrationTest {

  /**
   * The rest template builder.
   */
  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  RestTemplateBuilder restTemplateBuilder;

  /**
   * The local port.
   */
  @LocalServerPort
  int port;

  /**
   * Gets common page as xml.
   */
  @Test
  void getCommonPageAsXml() {
    RestTemplate restTemplate = restTemplateBuilder
        .build();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_XML));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<CommonPageDto> response = restTemplate.exchange(
        "http://localhost:" + port + "/common-page",
        HttpMethod.GET,
        httpEntity,
        CommonPageDto.class);
    assertThat(response.getBody())
        .isEqualTo(new CommonPageDto(
            List.of(
                new Person("Anna Livia", "Plurabelle", new Address("Dublin")),
                new Dog("Lassie", new Address("Yorkshire")),
                new Cat("Tom", new Person("Joseph", "Barbera", null))
            ),
            2,
            3,
            10L));
  }

  /**
   * Gets common page as json.
   */
  @Test
  void getCommonPageAsJson() {
    RestTemplate restTemplate = restTemplateBuilder
        .build();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<CommonPageDto> response = restTemplate.exchange(
        "http://localhost:" + port + "/common-page",
        HttpMethod.GET,
        httpEntity,
        CommonPageDto.class);

    // without global 'objectMapper.activateDefaultTypingAsProperty'
//    assertThat(response.getBody())
//        .extracting(CommonPageDto::getContent, InstanceOfAssertFactories.list(Object.class))
//        .hasSize(3)
//        .allMatch(entry -> entry instanceof Map);

    // with global 'objectMapper.activateDefaultTypingAsProperty'
    assertThat(response.getBody())
        .isEqualTo(new CommonPageDto(
            List.of(
                new Person("Anna Livia", "Plurabelle", new Address("Dublin")),
                new Dog("Lassie", new Address("Yorkshire")),
                new Cat("Tom", new Person("Joseph", "Barbera", null))
            ),
            2,
            3,
            10L));

    ResponseEntity<String> stringResponse = restTemplate.exchange(
        "http://localhost:" + port + "/common-page",
        HttpMethod.GET,
        httpEntity,
        String.class);
    System.out.println(stringResponse.getBody());
  }

  /**
   * Gets address page as json.
   */
  @Test
  void getAddressPageAsJson() {
    RestTemplate restTemplate = restTemplateBuilder
        .build();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<AddressPage> response = restTemplate.exchange(
        "http://localhost:" + port + "/address-page",
        HttpMethod.GET,
        httpEntity,
        AddressPage.class);
    assertThat(response.getBody())
        .isEqualTo(new AddressPage(
            List.of(
                new Address("Aachen"),
                new Address("Berlin"),
                new Address("Chemnitz"),
                new Address("Dortmund")
            ),
            0,
            4,
            11L,
            Sort.by("city")
        ));
  }

  /**
   * Gets animal page as json.
   */
  @Test
  void getAnimalPageAsJson() {
    RestTemplate restTemplate = restTemplateBuilder
        .build();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<AnimalPage> response = restTemplate.exchange(
        "http://localhost:" + port + "/animal-page",
        HttpMethod.GET,
        httpEntity,
        AnimalPage.class);
    assertThat(response.getBody())
        .isEqualTo(new AnimalPage(
            List.of(
                new Dog("Lassie", new Address("Yorkshire")),
                new Cat("Tom", new Person("Joseph", "Barbera", null))
            ),
            3,
            2,
            11L,
            Sort.by("city")
        ));
  }

  /**
   * Gets animal page as xml.
   */
  @Test
  void getAnimalPageAsXml() {
    RestTemplate restTemplate = restTemplateBuilder
        .build();

    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setAccept(List.of(MediaType.APPLICATION_XML));
    HttpEntity<?> httpEntity = new HttpEntity<>(httpHeaders);
    ResponseEntity<AnimalPage> response = restTemplate.exchange(
        "http://localhost:" + port + "/animal-page",
        HttpMethod.GET,
        httpEntity,
        AnimalPage.class);
    assertThat(response.getBody())
        .isEqualTo(new AnimalPage(
            List.of(
                new Dog("Lassie", new Address("Yorkshire")),
                new Cat("Tom", new Person("Joseph", "Barbera", null))
            ),
            3,
            2,
            11L,
            Sort.by("city")
        ));
  }

  /**
   * Open api.
   */
  @Test
  void openApi() {
    RestTemplate restTemplate = restTemplateBuilder.build();
    ResponseEntity<String> response = restTemplate.getForEntity(
        "http://localhost:" + port + "/v3/api-docs.yaml",
        String.class);
    assertThat(response.getBody()).isNotNull();
    System.out.println(response.getBody());
  }

}
