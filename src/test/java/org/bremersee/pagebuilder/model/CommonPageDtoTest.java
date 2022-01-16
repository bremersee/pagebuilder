package org.bremersee.pagebuilder.model;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.model.SortOrders;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;

@ExtendWith(SoftAssertionsExtension.class)
class CommonPageDtoTest {

  @Test
  void getContent() {
    List<Integer> expected = List.of(2, 4, 6);
    CommonPageDto actual = new CommonPageDto(expected, 0, 4, 10L, null);
    Assertions.assertThat(actual)
        .extracting(CommonPageDto::getContent, InstanceOfAssertFactories.list(Integer.class))
        .containsExactlyElementsOf(expected);
  }

  @Test
  void from(SoftAssertions softly) {
    List<Person> entries = List.of(
        new Person("", "", new Address("London")),
        new Person("", "", new Address("Berlin")),
        new Person("", "", new Address("New York")));
    Page<Person> page = new PageImpl<>(entries, PageRequest.of(0, 10), 100L);
    CommonPageDto expected = new CommonPageDto(entries, 0, 10, 100L, null);
    CommonPageDto actual = CommonPageDto.from(page);
    softly.assertThat(actual)
        .isEqualTo(expected);

    page = new PageImpl<>(
        entries,
        PageRequest.of(0, 10, Sort
            .by(Sort.Order
                .by("city")
                .with(Direction.ASC)
                .ignoreCase()
                .with(NullHandling.NULLS_LAST))),
        100L);
    List<Address> convertedEntries = List.of(
        new Address("London"),
        new Address("Berlin"),
        new Address("New York"));
    expected = new CommonPageDto(
        convertedEntries,
        0,
        10,
        100L,
        new SortOrders(List.of(new SortOrder("city", true, true, false))));
    actual = CommonPageDto.from(page, Person::getAddress);
    softly.assertThat(actual)
        .isEqualTo(expected);
  }

}