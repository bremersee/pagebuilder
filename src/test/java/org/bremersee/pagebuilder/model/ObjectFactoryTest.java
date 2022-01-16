package org.bremersee.pagebuilder.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ObjectFactoryTest {

  @Test
  void createCommonPageDto() {
    ObjectFactory target = new ObjectFactory();
    assertThat(target.createCommonPageDto())
        .isEqualTo(new CommonPageDto());
  }
}