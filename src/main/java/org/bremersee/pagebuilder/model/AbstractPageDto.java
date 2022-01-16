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
 * @author Christian Bremer
 */
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
public abstract class AbstractPageDto {

  @XmlElement(name = "number", required = true)
  private final int number;

  @XmlElement(name = "size", required = true)
  private final int size;

  @XmlElement(name = "totalElements", required = true)
  private final long totalElements;

  @XmlElementRef
  private final SortOrders sort;

  protected AbstractPageDto() {
    this(0, 0, 0, null);
  }

  public AbstractPageDto(int number, int size, long totalElements, SortOrders sort) {
    this.number = number;
    this.size = size;
    this.totalElements = totalElements;
    this.sort = sort;
  }
}
