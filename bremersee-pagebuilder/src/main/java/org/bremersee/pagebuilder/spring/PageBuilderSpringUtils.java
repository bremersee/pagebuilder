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
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * @author Christian Bremer
 */
public abstract class PageBuilderSpringUtils {

    private PageBuilderSpringUtils() {
    }

    public static SpringPageRequest toSpringPageRequest(PageRequestDto pageRequest) {
        if (pageRequest == null) {
            return null;
        }
        return new SpringPageRequestImpl(pageRequest.getPageNumber(), pageRequest.getPageSize(),
                ComparatorSpringUtils.toSort(pageRequest.getComparatorItem()), pageRequest.getQuery(),
                pageRequest.getExtension());
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
            pageRequest.setExtension(spr.getExtension());
            pageRequest.setQuery(spr.getQuery());
        }
        return pageRequest;
    }

    public static PageImpl<Object> toSpringPage(PageDto page) {
        if (page == null) {
            return null;
        }
        //@formatter:off
        return new SpringPageImpl<Object>(
                page.getEntries(), 
                toSpringPageRequest(page.getPageRequest()), 
                page.getTotalSize());
        //@formatter:on
    }

    public static PageDto fromSpringPage(org.springframework.data.domain.Page<?> springPage) {
        if (springPage == null) {
            return null;
        }
        PageRequestDto pageRequest = getPageRequest(springPage);
        return new PageDto(springPage.getContent(), pageRequest, springPage.getTotalElements());
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
