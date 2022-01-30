/*
 * Copyright 2022 the original author or authors.
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

package org.bremersee.pagebuilder.model;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;
import java.util.ServiceLoader;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrders;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.Cat;
import org.bremersee.pagebuilder.testmodel.Dog;
import org.bremersee.pagebuilder.testmodel.ObjectFactory;
import org.bremersee.pagebuilder.testmodel.Person;
import org.bremersee.xml.JaxbContextBuilder;
import org.bremersee.xml.JaxbContextData;
import org.bremersee.xml.JaxbContextDataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * The model test.
 *
 * @author Christian Bremer
 */
class ModelTest {

  private static JaxbContextBuilder jaxbContextBuilder;

  private static ObjectMapper objectMapper;

  /**
   * Setups xml and json mapper.
   */
  @BeforeAll
  static void setup() {
    jaxbContextBuilder = JaxbContextBuilder.builder()
        .processAll(ServiceLoader.load(JaxbContextDataProvider.class))
        .add(new JaxbContextData(ObjectFactory.class.getPackage()))
        .initJaxbContext();

    objectMapper = new ObjectMapper();
    objectMapper.activateDefaultTypingAsProperty(
        BasicPolymorphicTypeValidator.builder()
            .allowIfBaseType(Object.class)
            .build(),
        DefaultTyping.JAVA_LANG_OBJECT,
        "_type");
  }

  /**
   * Xml page.
   *
   * @throws Exception the exception
   */
  @Test
  void xmlPage() throws Exception {
    CommonPageDto expected = examplePage();
    StringWriter sw = new StringWriter();
    jaxbContextBuilder.buildMarshaller().marshal(expected, sw);
    String xml = sw.toString();
    // System.out.println(xml);
    CommonPageDto actual = (CommonPageDto) jaxbContextBuilder.buildUnmarshaller()
        .unmarshal(new StringReader(xml));
    assertThat(actual)
        .isEqualTo(expected);
  }

  /**
   * Json page.
   *
   * @throws Exception the exception
   */
  @Test
  void jsonPage() throws Exception {
    CommonPageDto expected = examplePage();
    String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(expected);
    CommonPageDto actual = objectMapper.readValue(json, CommonPageDto.class);
    assertThat(actual)
        .isEqualTo(expected);
  }

  private CommonPageDto examplePage() {
    SortOrder sortOrder = new SortOrder(null, true, true, false);
    Address address = new Address("Somewhere");
    Person person = new Person("Anna Livia", "Plurabelle", address);
    Dog dog = new Dog(address);
    dog.setName("Struppi");
    Cat cat = new Cat(person);
    cat.setName("Garfield");
    return new CommonPageDto(
        List.of(address, person, dog, cat),
        0,
        10,
        20,
        new SortOrders(List.of(sortOrder)));
  }

}
