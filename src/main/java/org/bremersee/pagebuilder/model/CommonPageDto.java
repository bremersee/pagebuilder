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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrders;
import org.bremersee.comparator.spring.mapper.SortMapper;
import org.springframework.data.domain.Page;

/**
 * The common page dto.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("SameNameButDifferent")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "page")
@XmlType(name = "pageType", propOrder = {
    "content"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Schema(description = "A page.")
public class CommonPageDto extends AbstractPageDto {

  @XmlElementWrapper(name = "content")
  @XmlAnyElement(lax = true)
  @Schema(description = "The content of the page.")
  private final List<Object> content = new ArrayList<>();

  /**
   * Instantiates a new common page dto.
   */
  protected CommonPageDto() {
    super();
  }

  /**
   * Instantiates a new Common page dto.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  @JsonCreator
  public CommonPageDto(
      @JsonProperty("content") List<?> content,
      @JsonProperty(value = "number", required = true) int number,
      @JsonProperty(value = "size", required = true) int size,
      @JsonProperty(value = "totalElements", required = true) long totalElements,
      @JsonProperty("sort") SortOrders sort) {
    super(number, size, totalElements, sort);
    if (!Objects.isNull(content)) {
      this.content.addAll(content);
    }
  }

  /**
   * Gets content.
   *
   * @return the content
   */
  public List<Object> getContent() {
    return Collections.unmodifiableList(content);
  }

  /**
   * Creates a common page dto from Spring's page.
   *
   * @param page the page
   * @return the common page dto
   */
  public static CommonPageDto from(Page<?> page) {
    return from(page, null);
  }

  /**
   * Creates a common page dto from Spring's page.
   *
   * @param <T> the type of the source page
   * @param page the page
   * @param converter the converter
   * @return the common page dto
   */
  public static <T> CommonPageDto from(Page<T> page, Function<T, ?> converter) {
    return Optional.ofNullable(page)
        .map(p -> new CommonPageDto(
            Objects.isNull(converter) ? p.getContent() : p.map(converter).getContent(),
            p.getNumber(),
            p.getSize(),
            p.getTotalElements(),
            p.getSort().isSorted()
                ? new SortOrders(SortMapper.fromSort(p.getSort()))
                : null))
        .orElseGet(CommonPageDto::new);
  }

}
