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

import java.util.List;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrder.CaseHandling;
import org.bremersee.comparator.model.SortOrders;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.AddressPage;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * The json page dto test.
 *
 * @author Christian Bremer
 */
class JsonPageDtoTest {

  @Test
  void getContentWithNoSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressPage actual = new AddressPage(content, 0, 4, 10L);
    assertThat(actual)
        .extracting(AddressPage::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  @Test
  void getContentWithSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressPage actual = new AddressPage(content, 0, 4, 10L, Sort.by("city"));
    assertThat(actual)
        .extracting(AddressPage::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  @Test
  void getContentWithSortOrders() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressPage actual = new AddressPage(content, 0, 4, 10L,
        new SortOrders(List.of(SortOrder.by("city"))));
    assertThat(actual)
        .extracting(AddressPage::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  @Test
  void getContentWithPage() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    Page<Address> page = new PageImpl<>(content, PageRequest.of(0, 4, Sort.by("city")), 10L);
    AddressPage actual = new AddressPage(page);
    assertThat(actual)
        .extracting(AddressPage::getContent, InstanceOfAssertFactories.list(Address.class))
        .containsExactly(
            new Address("Berlin"),
            new Address("London"),
            new Address("New York"));
  }

  @Test
  void getSortWitSort() {
    List<Address> content = List.of(
        new Address("Berlin"),
        new Address("London"),
        new Address("New York"));
    AddressPage actual = new AddressPage(content, 0, 4, 10L,
        SortOrders.by(SortOrder.by("city").with(CaseHandling.SENSITIVE)));
    assertThat(actual)
        .extracting(AddressPage::getSort)
        .isNotNull();
    assertThat(actual)
        .extracting(AddressPage::getSort)
        .isEqualTo(SortOrders.by(SortOrder.by("city").with(CaseHandling.SENSITIVE)));
  }

}