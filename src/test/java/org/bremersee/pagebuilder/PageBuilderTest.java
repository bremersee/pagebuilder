package org.bremersee.pagebuilder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.bremersee.comparator.ValueComparator;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.pagebuilder.PageBuilder.SortTarget;
import org.bremersee.pagebuilder.testmodel.Address;
import org.bremersee.pagebuilder.testmodel.Animal;
import org.bremersee.pagebuilder.testmodel.Cat;
import org.bremersee.pagebuilder.testmodel.Dog;
import org.bremersee.pagebuilder.testmodel.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

@ExtendWith(SoftAssertionsExtension.class)
class PageBuilderTest {

  @Test
  void sourceFilter(SoftAssertions softly) {
    List<Integer> entries = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
    List<Integer> expected = List.of(2, 4, 6, 8, 10);
    Page<Integer> actual = new PageBuilder<Integer, Integer>()
        .sourceEntries(entries.stream())
        .sourceFilter(i -> i % 2 == 0)
        .pageable(null, SortTarget.SOURCE_ENTRIES)
        .build();
    softly.assertThat(actual)
        .isNotNull()
        .containsExactlyElementsOf(expected);
    softly.assertThat(actual.getTotalElements())
        .isEqualTo(expected.size()); // expected size, because filtered entries are not counted
    softly.assertThat(actual.getNumber())
        .isEqualTo(0);
    softly.assertThat(actual.getSize())
        .isGreaterThanOrEqualTo(expected.size());
    softly.assertThat(actual.getPageable().getSort())
        .isEqualTo(Sort.unsorted());
  }

  @Test
  void sourceSortFn(SoftAssertions softly) {
    List<Integer> entries = List.of(2, 4, 6, 8, 10, 9, 7, 5, 3, 1);
    List<Integer> expected = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9);
    Page<Integer> actual = new PageBuilder<Integer, Integer>()
        .sourceEntries(entries)
        .pageable(0, 9,
            SortTarget.SOURCE_ENTRIES,
            List.of(new SortOrder(null, true, false, false)))
        .sourceSortFn(ValueComparator::new)
        .targetSortFn(null)
        .build();
    softly.assertThat(actual)
        .isNotNull()
        .containsExactlyElementsOf(expected);
    softly.assertThat(actual.getTotalElements())
        .isEqualTo(entries.size());
    softly.assertThat(actual.getNumber())
        .isEqualTo(0);
    softly.assertThat(actual.getSize())
        .isEqualTo(9);
    softly.assertThat(actual.getTotalPages())
        .isEqualTo(2);
    softly.assertThat(actual.getPageable().getSort())
        .isEqualTo(Sort.unsorted()); // sorting of source entries is not put into the page
  }

  @Test
  void pageable(SoftAssertions softly) {
    List<Address> entries = List.of(
        new Address("Paris"),
        new Address("London"),
        new Address("Berlin"),
        new Address("New York"));
    List<Address> expected = List.of(new Address("Berlin"), new Address("London"));
    Page<Address> actual = new PageBuilder<Address, Address>()
        .sourceEntries(entries.iterator())
        .pageable(0, 2,
            List.of(new SortOrder("city", true, true, false)))
        .build();
    softly.assertThat(actual)
        .isNotNull()
        .containsExactlyElementsOf(expected);
    softly.assertThat(actual.getTotalElements())
        .isEqualTo(entries.size());
    softly.assertThat(actual.getNumber())
        .isEqualTo(0);
    softly.assertThat(actual.getSize())
        .isEqualTo(2);
    softly.assertThat(actual.getTotalPages())
        .isEqualTo(2);
    softly.assertThat(actual.getPageable().getSort())
        .isEqualTo(Sort.by(Order.by("city")
            .with(Direction.ASC)
            .ignoreCase()
            .with(NullHandling.NULLS_LAST)));

    actual = new PageBuilder<Address, Address>()
        .sourceEntries(entries.iterator())
        .pageable(2, 2)
        .build();
    softly.assertThat(actual)
        .isEmpty();
    softly.assertThat(actual.getNumber())
        .isEqualTo(2);
    softly.assertThat(actual.getSize())
        .isEqualTo(2);
  }

  @Test
  void pageableWithPageRequest(SoftAssertions softly) {
    List<Address> entries = List.of(
        new Address("Paris"),
        new Address("London"),
        new Address("Berlin"),
        new Address("New York"));
    List<Address> expected = List.of(new Address("New York"), new Address("Paris"));
    Page<Address> actual = new PageBuilder<Address, Address>()
        .sourceEntries(entries.iterator())
        .pageable(PageRequest.of(1, 2, Sort.by(Direction.ASC, "city")))
        .build();
    softly.assertThat(actual)
        .isNotNull()
        .containsExactlyElementsOf(expected);
    softly.assertThat(actual.getTotalElements())
        .isEqualTo(entries.size());
    softly.assertThat(actual.getNumber())
        .isEqualTo(1);
    softly.assertThat(actual.getSize())
        .isEqualTo(2);
    softly.assertThat(actual.getTotalPages())
        .isEqualTo(2);
    softly.assertThat(actual.getPageable().getSort())
        .isEqualTo(Sort.by(Order.by("city").with(Direction.ASC).with(NullHandling.NULLS_LAST)));

    actual = new PageBuilder<Address, Address>()
        .sourceEntries(entries.iterator())
        .pageable(PageRequest.of(2, 2), SortTarget.SOURCE_ENTRIES)
        .build();
    softly.assertThat(actual)
        .isEmpty();
    softly.assertThat(actual.getNumber())
        .isEqualTo(2);
    softly.assertThat(actual.getSize())
        .isEqualTo(2);
  }

  @Test
  void converter() {
    List<Address> expected = List.of(
        new Address("London"),
        new Address("Berlin"),
        new Address("New York"));
    List<Person> entries = List.of(
        new Person("", "", expected.get(0)),
        new Person("", "", expected.get(1)),
        new Person("", "", expected.get(2))
    );
    Page<Address> actual = new PageBuilder<Person, Address>()
        .sourceEntries(entries)
        .converter(Person::getAddress)
        .build();
    assertThat(actual)
        .containsExactlyElementsOf(expected);
  }

  @Test
  void targetFilter(SoftAssertions softly) {
    List<Person> entries = List.of(
        new Person("", "", new Address("London")),
        new Person("", "", new Address("Berlin")),
        new Person("", "", new Address("New York")));
    List<Address> expected = List.of(
        new Address("Berlin"),
        new Address("London"));
    Page<Address> actual = new PageBuilder<Person, Address>()
        .sourceEntries(entries)
        .converter(Person::getAddress)
        .targetFilter(address -> !address.getCity().contains(" "))
        .pageable(0, Integer.MAX_VALUE,
            SortTarget.TARGET_ENTRIES,
            new SortOrder("city", true, true, false))
        .build();
    softly.assertThat(actual)
        .containsExactlyElementsOf(expected);
    softly.assertThat(actual.getPageable().getSort())
        .isEqualTo(Sort.by(Sort.Order.by("city")
            .with(Direction.ASC)
            .ignoreCase()
            .with(NullHandling.NULLS_LAST)));
  }

  @Test
  void targetSortFn(SoftAssertions softly) {
    List<Animal> entries = List.of(
        new Cat("Garfield"),
        new Dog("Snoopy"),
        new Cat("Tom"),
        new Dog("Struppi"));
    List<Animal> expected = List.of(
        new Dog("Struppi"),
        new Dog("Snoopy"),
        new Cat("Tom"),
        new Cat("Garfield"));
    Page<Animal> actual = new PageBuilder<Animal, Animal>()
        .sourceEntries(entries)
        .pageable(0, Integer.MAX_VALUE,
            new SortOrder("_type", true, false, false),
            new SortOrder("name", false, true, false))
        .targetSortFn(sortOrder -> {
          if ("_type".equals(sortOrder.getField())) {
            return (o1, o2) -> o1 instanceof Dog && o2 instanceof Dog
                || o1 instanceof Cat && o2 instanceof Cat
                ? 0
                : o1 instanceof Dog ? -1 : 1;
          }
          return new ValueComparator(sortOrder);
        })
        .build();
    softly.assertThat(actual)
        .containsExactlyElementsOf(expected);
    softly.assertThat(actual.getPageable().getSort())
        .isEqualTo(Sort.by(
            Sort.Order.by("_type")
                .with(Direction.ASC)
                .with(NullHandling.NULLS_LAST),
            Sort.Order.by("name")
                .with(Direction.DESC)
                .ignoreCase()
                .with(NullHandling.NULLS_LAST)));

    expected = List.of(
        new Cat("Garfield"),
        new Dog("Snoopy"),
        new Dog("Struppi"),
        new Cat("Tom"));
    actual = new PageBuilder<Animal, Animal>()
        .sourceEntries(entries)
        .pageable(0, Integer.MAX_VALUE,
            new SortOrder("name", true, true, false))
        .targetSortFn(null)
        .build();
    softly.assertThat(actual)
        .containsExactlyElementsOf(expected);
  }
}