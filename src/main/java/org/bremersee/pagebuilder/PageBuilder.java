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

package org.bremersee.pagebuilder;

import static java.util.Objects.requireNonNullElse;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.bremersee.comparator.ComparatorBuilder;
import org.bremersee.comparator.ValueComparator;
import org.bremersee.comparator.model.SortOrder;
import org.bremersee.comparator.spring.mapper.SortMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

/**
 * @author Christian Bremer
 */
public class PageBuilder<S, T> {

  private Stream<? extends S> sourceEntries;

  private Predicate<S> sourceFilter;

  private Function<SortOrder, Comparator<?>> sourceSortFn;

  private Integer pageNumber;

  private Integer pageSize;

  private List<SortOrder> sort;

  private SortTarget sortTarget;

  private Function<S, T> converter;

  private Predicate<T> targetFilter;

  private Function<SortOrder, Comparator<?>> targetSortFn;

  public PageBuilder() {
    sourceFilter = sourceEntry -> true;
    //noinspection unchecked
    sourceSortFn = cf -> (Comparator<S>) new ValueComparator(cf);
    //noinspection unchecked
    converter = e -> (T) e;
    targetFilter = targetEntry -> true;
    //noinspection unchecked
    targetSortFn = cf -> (Comparator<T>) new ValueComparator(cf);
  }

  public PageBuilder<S, T> sourceEntries(Stream<? extends S> entries) {
    if (!Objects.isNull(entries)) {
      this.sourceEntries = entries;
    }
    return this;
  }

  public PageBuilder<S, T> sourceEntries(Iterable<? extends S> entries) {
    if (!Objects.isNull(entries)) {
      this.sourceEntries = StreamSupport.stream(entries.spliterator(), false);
    }
    return this;
  }

  public PageBuilder<S, T> sourceEntries(Iterator<? extends S> entries) {
    if (!Objects.isNull(entries)) {
      this.sourceEntries = StreamSupport.stream(Spliterators
          .spliteratorUnknownSize(entries, Spliterator.ORDERED), false);
    }
    return this;
  }

  public PageBuilder<S, T> sourceFilter(Predicate<S> sourceFilter) {
    if (!Objects.isNull(sourceFilter)) {
      this.sourceFilter = sourceFilter;
    }
    return this;
  }

  public PageBuilder<S, T> sourceSortFn(
      Function<SortOrder, Comparator<?>> sourceSortFn) {
    if (!Objects.isNull(sourceSortFn)) {
      this.sourceSortFn = sourceSortFn;
    }
    return this;
  }

  public PageBuilder<S, T> pageable(Pageable pageable) {
    return pageable(pageable, SortTarget.TARGET_ENTRIES);
  }

  public PageBuilder<S, T> pageable(Pageable pageable, SortTarget sortTarget) {
    if (!Objects.isNull(pageable)) {
      return pageable(
          pageable.getPageNumber(),
          pageable.getPageSize(),
          sortTarget,
          SortMapper.fromSort(pageable.getSort()));
    }
    return this;
  }

  public PageBuilder<S, T> pageable(
      Integer pageNumber,
      Integer pageSize,
      SortOrder... sort) {
    return pageable(
        pageNumber,
        pageSize,
        SortTarget.TARGET_ENTRIES,
        Objects.isNull(sort) ? null : Arrays.asList(sort));
  }

  public PageBuilder<S, T> pageable(
      Integer pageNumber,
      Integer pageSize,
      List<SortOrder> sort) {
    return pageable(pageNumber, pageSize, SortTarget.TARGET_ENTRIES, sort);
  }

  public PageBuilder<S, T> pageable(
      Integer pageNumber,
      Integer pageSize,
      SortTarget sortTarget,
      SortOrder... sort) {
    return pageable(
        pageNumber,
        pageSize,
        sortTarget,
        Objects.isNull(sort) ? null : Arrays.asList(sort));
  }

  public PageBuilder<S, T> pageable(
      Integer pageNumber,
      Integer pageSize,
      SortTarget sortTarget,
      List<SortOrder> sort) {

    Pageable pageable = PageRequest.of(pageNumber, pageSize);
    this.pageNumber = pageable.getPageNumber();
    this.pageSize = pageable.getPageSize();
    this.sort = sort;
    if (ObjectUtils.isEmpty(this.sort)) {
      this.sortTarget = null;
    } else {
      this.sortTarget = Objects.requireNonNullElse(sortTarget, SortTarget.TARGET_ENTRIES);
    }
    return this;
  }

  public PageBuilder<S, T> converter(Function<S, T> converter) {
    if (!Objects.isNull(converter)) {
      this.converter = converter;
    }
    return this;
  }

  public PageBuilder<S, T> targetFilter(Predicate<T> targetFilter) {
    if (!Objects.isNull(targetFilter)) {
      this.targetFilter = targetFilter;
    }
    return this;
  }

  public PageBuilder<S, T> targetSortFn(
      Function<SortOrder, Comparator<?>> targetSortFn) {
    if (!Objects.isNull(targetSortFn)) {
      this.targetSortFn = targetSortFn;
    }
    return this;
  }

  public Page<T> build() {
    //noinspection unchecked
    final List<S> source = ((Stream<S>) requireNonNullElse(this.sourceEntries, Stream.empty()))
        .filter(sourceFilter)
        .collect(Collectors.toList());
    if (SortTarget.SOURCE_ENTRIES.equals(sortTarget) && !ObjectUtils.isEmpty(sort)) {
      source.sort(ComparatorBuilder.builder()
          .addAll(sort, sourceSortFn)
          .build());
    }
    final List<T> target = source.stream()
        .map(converter)
        .filter(targetFilter)
        .collect(Collectors.toList());
    final Sort pageSort;
    if (SortTarget.TARGET_ENTRIES.equals(sortTarget) && !ObjectUtils.isEmpty(sort)) {
      target.sort(ComparatorBuilder.builder()
          .addAll(sort, targetSortFn)
          .build());
      pageSort = SortMapper.toSort(sort);
    } else {
      pageSort = Sort.unsorted();
    }

    int number = Objects.requireNonNullElse(pageNumber, 0);
    int size = Objects.requireNonNullElse(pageSize, Integer.MAX_VALUE);
    final Pageable pageable = PageRequest.of(number, size, pageSort);
    final List<T> content = target.stream()
        .skip(pageable.getOffset())
        .limit(pageable.getPageSize())
        .collect(Collectors.toList());
    return new PageImpl<>(content, pageable, target.size());
  }

  enum SortTarget {
    SOURCE_ENTRIES,
    TARGET_ENTRIES
  }
}
