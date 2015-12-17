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

package org.bremersee.pagebuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bremersee.comparator.ObjectComparatorFactory;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequestDto;

/**
 * <p>
 * Default page builder implementation.
 * </p>
 * 
 * @author Christian Bremer
 */
public class PageBuilderImpl implements PageBuilder {

    private ObjectComparatorFactory objectComparatorFactory = ObjectComparatorFactory.newInstance();

    /**
     * An optional filter.
     */
    private PageBuilderFilter pageBuilderFilter;

    /**
     * Returns the object comparator factory.
     */
    protected ObjectComparatorFactory getObjectComparatorFactory() {
        return objectComparatorFactory;
    }

    /**
     * Sets the object comparator factory. A default one is present.
     */
    public void setObjectComparatorFactory(ObjectComparatorFactory objectComparatorFactory) {
        if (objectComparatorFactory != null) {
            this.objectComparatorFactory = objectComparatorFactory;
        }
    }

    /**
     * Return the filter for this page builder or {@code null} if no filter is
     * present.
     */
    protected PageBuilderFilter getPageBuilderFilter() {
        return pageBuilderFilter;
    }

    /**
     * Set the filter for this page builder.
     */
    public void setPageBuilderFilter(PageBuilderFilter pageBuilderFilter) {
        this.pageBuilderFilter = pageBuilderFilter;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageBuilderImpl [pageBuilderFilter=" + pageBuilderFilter + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bremersee.pagebuilder.PageBuilder#buildPage(java.util.Collection,
     * org.bremersee.pagebuilder.model.PageRequestDto, long)
     */
    @Override
    public PageDto buildPage(Collection<? extends Object> pageEntries, PageRequestDto pageRequest, long totalSize) {
        return new PageDto(pageEntries, pageRequest, totalSize);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.PageBuilder#buildFilteredPage(java.util.
     * Collection, org.bremersee.pagebuilder.model.PageRequestDto,
     * java.lang.Object)
     */
    @Override
    public PageDto buildFilteredPage(Collection<? extends Object> allAvailableEntries, PageRequestDto pageRequest,
            Object filterCriteria) {
        
        if (allAvailableEntries == null) {
            allAvailableEntries = new ArrayList<>();
        }
        if (pageRequest == null) {
            pageRequest = new PageRequestDto();
        }
        List<Object> allEntries = new ArrayList<>(allAvailableEntries);
        if (pageRequest.getComparatorItem() != null) {
            Collections.sort(allEntries, objectComparatorFactory.newObjectComparator(pageRequest.getComparatorItem()));
        }
        List<Object> filteredEntries;
        if (getPageBuilderFilter() == null) {
            filteredEntries = allEntries;
        } else {
            filteredEntries = new ArrayList<Object>(allAvailableEntries.size());
            for (Object entry : allEntries) {
                if (getPageBuilderFilter().accept(entry, filterCriteria)) {
                    filteredEntries.add(entry);
                }
            }
        }
        List<Object> pageEntries = new ArrayList<>(filteredEntries.size());
        if (pageRequest.getFirstResult() < filteredEntries.size()) {
            int lastResult = pageRequest.getFirstResult() + pageRequest.getPageSize();
            for (int i = pageRequest.getFirstResult(); i < lastResult; i++) {
                if (i < filteredEntries.size()) {
                    pageEntries.add(filteredEntries.get(i));
                }
            }
        }

        return new PageDto(pageEntries, pageRequest, filteredEntries.size());
    }

}
