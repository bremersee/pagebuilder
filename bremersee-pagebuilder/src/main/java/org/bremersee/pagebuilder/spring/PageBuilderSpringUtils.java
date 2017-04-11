/*
 * Copyright 2015 the original author or authors.
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

package org.bremersee.pagebuilder.spring;

import org.bremersee.comparator.spring.ComparatorSpringUtils;
import org.bremersee.pagebuilder.PageBuilderUtils;
import org.bremersee.pagebuilder.PageEntryTransformer;
import org.bremersee.pagebuilder.PageResult;
import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageRequest;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Christian Bremer
 */
public abstract class PageBuilderSpringUtils { // NOSONAR

    private PageBuilderSpringUtils() {
    }

    public static SpringPageRequest toSpringPageRequest(final PageRequest pageRequest) {
        if (pageRequest == null) {
            return null;
        }
        // make sure that first result (= offset) is not bigger than
        // Integer.MAX_VALUE
        int pageNumber = pageRequest.getPageNumber();
        int pageSize = pageRequest.getPageSize();
        long firstResult = (long) pageNumber * (long) pageSize;
        if (firstResult > (long) Integer.MAX_VALUE) {
            pageNumber = 0;
        }
        return new SpringPageRequestImpl(pageNumber, pageSize,
                ComparatorSpringUtils.toSort(pageRequest.getComparatorItem()), pageRequest.getQuery(),
                pageRequest.getExtensions());
    }

    @SuppressWarnings("unused")
    public static PageRequestDto fromSpringPageRequest(final Pageable pageable) {
        if (pageable != null && pageable instanceof SpringPageRequestImpl) {
            SpringPageRequestImpl spr = (SpringPageRequestImpl) pageable;
            return fromSpringPageRequest(pageable, spr.getQuery(), spr.getExtensions());
        } else {
            return fromSpringPageRequest(pageable, null, null);
        }
    }

    @SuppressWarnings("unused")
    public static PageRequestDto fromSpringPageRequest(final Pageable pageable, final String query) {
        if (pageable != null && pageable instanceof SpringPageRequestImpl) {
            return fromSpringPageRequest(pageable, query, ((SpringPageRequestImpl) pageable).getExtensions());
        } else {
            return fromSpringPageRequest(pageable, query, null);
        }
    }

    @SuppressWarnings("WeakerAccess")
    public static PageRequestDto fromSpringPageRequest(final Pageable pageable, final String query,
                                                       final Map<String, Object> extensions) {
        if (pageable == null) {
            return null;
        }
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setComparatorItem(ComparatorSpringUtils.fromSort(pageable.getSort()));
        pageRequest.setPageNumber(pageable.getPageNumber());
        pageRequest.setPageSize(pageable.getPageSize());
        pageRequest.setQuery(query);
        if (extensions != null) {
            if (pageRequest.getExtensions() != null) {
                pageRequest.getExtensions().putAll(extensions);
            } else {
                pageRequest.setExtensions(extensions);
            }
        }
        return pageRequest;
    }

    @SuppressWarnings("WeakerAccess")
    public static <E> PageImpl<E> toSpringPage(final Page<E> page) {
        if (page == null) {
            return null;
        }
        //@formatter:off
        return new SpringPageImpl<>(
                page.getEntries(),
                toSpringPageRequest(page.getPageRequest()),
                page.getTotalSize());
        //@formatter:on
    }

    @SuppressWarnings("unused")
    public static <E, T> PageImpl<T> toSpringPage(final Page<E> page, final PageEntryTransformer<T, E> transformer) {
        if (transformer == null) {
            //noinspection unchecked
            return (PageImpl<T>) toSpringPage(page);
        }
        if (page == null) {
            return null;
        }
        List<T> transformedEntries = new ArrayList<>(page.getEntries().size());
        for (E entry : page.getEntries()) {
            transformedEntries.add(transformer.transform(entry));
        }
        //@formatter:off
        return new SpringPageImpl<>(
                transformedEntries,
                toSpringPageRequest(page.getPageRequest()),
                page.getTotalSize());
        //@formatter:on
    }

    public static <E> PageResult<E> fromSpringPage(final org.springframework.data.domain.Page<E> springPage) {
        return fromSpringPage(springPage, null, (Map<String, Object>) null);
    }

    @SuppressWarnings("unused")
    public static <E> PageResult<E> fromSpringPage(final org.springframework.data.domain.Page<E> springPage,
                                                   final String query) {
        return fromSpringPage(springPage, query, (Map<String, Object>) null);
    }

    @SuppressWarnings("WeakerAccess")
    public static <E> PageResult<E> fromSpringPage(final org.springframework.data.domain.Page<E> springPage,
                                                   final String query, final Map<String, Object> extensions) {
        if (springPage == null) {
            return null;
        }
        PageRequestDto pageRequest = getPageRequest(springPage, query, extensions);
        return new PageResult<>(springPage.getContent(), pageRequest, springPage.getTotalElements());
    }

    @SuppressWarnings("unused")
    public static <E, T> PageResult<T> fromSpringPage(final org.springframework.data.domain.Page<E> springPage,
                                                      final PageEntryTransformer<T, E> transformer) {
        return fromSpringPage(springPage, null, null, transformer);
    }

    @SuppressWarnings("unused")
    public static <E, T> PageResult<T> fromSpringPage(final org.springframework.data.domain.Page<E> springPage,
                                                      final String query,
                                                      final PageEntryTransformer<T, E> transformer) {
        return fromSpringPage(springPage, query, null, transformer);
    }

    @SuppressWarnings({"WeakerAccess", "SameParameterValue"})
    public static <E, T> PageResult<T> fromSpringPage(final org.springframework.data.domain.Page<E> springPage,
                                                      final String query, final Map<String, Object> extensions,
                                                      final PageEntryTransformer<T, E> transformer) {
        if (transformer == null) {
            //noinspection unchecked
            return (PageResult<T>) fromSpringPage(springPage, query, extensions);
        }
        if (springPage == null) {
            return null;
        }
        PageRequestDto pageRequest = getPageRequest(springPage, query, extensions);
        return PageBuilderUtils.createPage(springPage.getContent(), pageRequest, springPage.getTotalElements(), transformer);
    }

    private static PageRequestDto getPageRequest(final org.springframework.data.domain.Page<?> springPage,
                                                 final String query, final Map<String, Object> extensions) {
        if (springPage == null) {
            return null;
        }
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setComparatorItem(ComparatorSpringUtils.fromSort(springPage.getSort()));
        pageRequest.setPageNumber(springPage.getNumber());
        pageRequest.setPageSize(springPage.getSize());
        pageRequest.setQuery(query);
        if (extensions != null) {
            if (pageRequest.getExtensions() != null) {
                pageRequest.getExtensions().putAll(extensions);
            } else {
                pageRequest.setExtensions(extensions);
            }
        }
        return pageRequest;
    }

}
