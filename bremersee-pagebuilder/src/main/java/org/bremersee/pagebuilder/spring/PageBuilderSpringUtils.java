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

import java.util.ArrayList;
import java.util.List;

import org.bremersee.comparator.spring.ComparatorSpringUtils;
import org.bremersee.pagebuilder.PageBuilderUtils;
import org.bremersee.pagebuilder.PageEntryTransformer;
import org.bremersee.pagebuilder.PageResult;
import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageRequest;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * @author Christian Bremer
 */
public abstract class PageBuilderSpringUtils {

    private PageBuilderSpringUtils() {
    }

    public static SpringPageRequest toSpringPageRequest(PageRequest pageRequest) {
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

    public static PageRequestDto fromSpringPageRequest(Pageable pageable) {
        if (pageable == null) {
            return null;
        }
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setComparatorItem(ComparatorSpringUtils.fromSort(pageable.getSort()));
        pageRequest.setPageNumber(pageable.getPageNumber());
        pageRequest.setPageSize(pageable.getPageSize());
        if (pageable instanceof SpringPageRequestImpl) {
            SpringPageRequestImpl spr = (SpringPageRequestImpl) pageable;
            pageRequest.setExtensions(spr.getExtensions());
            pageRequest.setQuery(spr.getQuery());
        }
        return pageRequest;
    }

    public static <E> PageImpl<E> toSpringPage(Page<E> page) {
        if (page == null) {
            return null;
        }
        //@formatter:off
        return new SpringPageImpl<E>(
                page.getEntries(), 
                toSpringPageRequest(page.getPageRequest()), 
                page.getTotalSize());
        //@formatter:on
    }

    public static <E, T> PageImpl<T> toSpringPage(Page<E> page, PageEntryTransformer<T, E> transformer) {
        if (page == null) {
            return null;
        }
        if (transformer == null) {
            transformer = new PageEntryTransformer<T, E>() {

                @SuppressWarnings("unchecked")
                @Override
                public T transform(E source) {
                    return (T)source;
                }
            };
        }
        List<T> transformedEntries = new ArrayList<>(page.getEntries().size());
        for (E entry : page.getEntries()) {
            transformedEntries.add(transformer.transform(entry));
        }
        //@formatter:off
        return new SpringPageImpl<T>(
                transformedEntries, 
                toSpringPageRequest(page.getPageRequest()), 
                page.getTotalSize());
        //@formatter:on
    }

    public static <E> PageResult<E> fromSpringPage(org.springframework.data.domain.Page<E> springPage) {
        if (springPage == null) {
            return null;
        }
        PageRequestDto pageRequest = getPageRequest(springPage);
        return new PageResult<E>(springPage.getContent(), pageRequest, springPage.getTotalElements());
    }

    public static <E, T> PageResult<T> fromSpringPage(org.springframework.data.domain.Page<E> springPage, PageEntryTransformer<T, E> transformer) {
        if (springPage == null) {
            return null;
        }
        if (transformer == null) {
            transformer = new PageEntryTransformer<T, E>() {

                @SuppressWarnings("unchecked")
                @Override
                public T transform(E source) {
                    return (T)source;
                }
            };
        }
        PageRequestDto pageRequest = getPageRequest(springPage);
        return PageBuilderUtils.createPage(springPage.getContent(), pageRequest, springPage.getTotalElements(), transformer);
    }

    private static PageRequestDto getPageRequest(org.springframework.data.domain.Page<?> springPage) {
        if (springPage == null) {
            return null;
        }
        if (springPage instanceof SpringPageImpl && ((SpringPageImpl<?>) springPage).getPageable() != null) {
            return fromSpringPageRequest(((SpringPageImpl<?>) springPage).getPageable());
        }
        PageRequestDto pageRequest = new PageRequestDto();
        pageRequest.setComparatorItem(ComparatorSpringUtils.fromSort(springPage.getSort()));
        pageRequest.setPageNumber(springPage.getNumber());
        pageRequest.setPageSize(springPage.getSize());
        return pageRequest;
    }

}
