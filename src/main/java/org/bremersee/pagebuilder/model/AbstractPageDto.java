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

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrders;
import org.bremersee.comparator.spring.mapper.SortMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

/**
 * The abstract page transfer object.
 *
 * @param <T> the type of the content
 * @author Christian Bremer
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractPageType")
@Getter
@EqualsAndHashCode
@ToString
@Schema(description = "The base page.")
public abstract class AbstractPageDto<T> {

  /**
   * The Content.
   */
  @Schema(description = "The content of the page.")
  @XmlTransient
  protected List<T> content = new ArrayList<>();

  @Schema(description = "The page number starting with 0.")
  @XmlElement(name = "number", required = true)
  private final int number;

  @Schema(description = "The size of the page (not the size of the content).")
  @XmlElement(name = "size", required = true)
  private final int size;

  @Schema(description = "The size of available elements.")
  @XmlElement(name = "totalElements", required = true)
  private final long totalElements;

  @XmlElementRef(type = SortOrders.class)
  private final SortOrders sort;

  /**
   * Instantiates a new abstract page transfer object.
   */
  protected AbstractPageDto() {
    this(null, 0, 0, 0, (SortOrders) null);
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   */
  public AbstractPageDto(
      List<? extends T> content,
      int number,
      int size,
      long totalElements) {
    this(content, number, size, totalElements, Sort.unsorted());
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param content the content of the page
   * @param number the number of the page starting with 0
   * @param size the size of the page (not the size of the content)
   * @param totalElements the total elements (the size of available elements)
   * @param sort the sort oder
   */
  public AbstractPageDto(
      List<? extends T> content,
      int number,
      int size,
      long totalElements,
      SortOrders sort) {
    if (!Objects.isNull(content)) {
      this.content.addAll(content);
    }
    this.number = number;
    this.size = size;
    this.totalElements = totalElements;
    this.sort = Objects.isNull(sort) ? new SortOrders(List.of()) : sort;
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param content the content
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public AbstractPageDto(
      List<? extends T> content,
      int number,
      int size,
      long totalElements,
      Sort sort) {
    this(
        content,
        number,
        size,
        totalElements,
        new SortOrders(SortMapper.fromSort(sort)));
  }

  /**
   * Instantiates a new abstract page transfer object.
   *
   * @param page the page
   */
  public AbstractPageDto(Page<? extends T> page) {
    this(
        page.getContent(),
        page.getNumber(),
        page.getSize(),
        page.getTotalElements(),
        page.getSort());
  }

  /**
   * Gets content.
   *
   * @return the content
   */
  public abstract List<T> getContent();

  /**
   * Gets sort.
   *
   * @return the sort
   */
  public final SortOrders getSort() {
    if (Objects.isNull(sort)) {
      return new SortOrders(List.of());
    }
    return sort;
  }
}
