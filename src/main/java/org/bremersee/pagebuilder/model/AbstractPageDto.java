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
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.bremersee.comparator.model.SortOrders;

/**
 * The abstract page dto.
 *
 * @author Christian Bremer
 */
@SuppressWarnings("SameNameButDifferent")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "abstractPageType", propOrder = {
    "number",
    "size",
    "totalElements",
    "sort"
})
@Getter
@EqualsAndHashCode
@ToString
@Schema(description = "The base information of a page.")
public abstract class AbstractPageDto {

  @Schema(description = "The page number starting with 0.")
  @XmlElement(name = "number", required = true)
  private final int number;

  @Schema(description = "The size of the page (not the size of the content).")
  @XmlElement(name = "size", required = true)
  private final int size;

  @Schema(description = "The size of available elements.")
  @XmlElement(name = "totalElements", required = true)
  private final long totalElements;

  @XmlElementRef
  private final SortOrders sort;

  /**
   * Instantiates a new abstract page dto.
   */
  protected AbstractPageDto() {
    this(0, 0, 0, null);
  }

  /**
   * Instantiates a new abstract page dto.
   *
   * @param number the number
   * @param size the size
   * @param totalElements the total elements
   * @param sort the sort
   */
  public AbstractPageDto(int number, int size, long totalElements, SortOrders sort) {
    this.number = number;
    this.size = size;
    this.totalElements = totalElements;
    this.sort = sort;
  }
}
