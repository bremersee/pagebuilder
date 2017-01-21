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

import org.bremersee.comparator.ObjectComparatorFactory;
import org.bremersee.pagebuilder.model.PageRequest;
import org.bremersee.pagebuilder.model.PageRequestDto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * Default page builder implementation.
 * </p>
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"unused", "WeakerAccess"})
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
    public void setObjectComparatorFactory(final ObjectComparatorFactory objectComparatorFactory) {
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
    public void setPageBuilderFilter(final PageBuilderFilter pageBuilderFilter) {
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

    @Override
    public <E> PageResult<E> buildPage(final Iterable<? extends E> pageEntries, final PageRequest pageRequest,
                                       final long totalSize) {
        return buildPage(pageEntries, pageRequest, totalSize, null);
    }

    @Override
    public <T, E> PageResult<T> buildPage(final Iterable<? extends E> pageEntries, final PageRequest pageRequest,
                                          final long totalSize, final PageEntryTransformer<T, E> transformer) {
        return PageBuilderUtils.createPage(pageEntries, pageRequest, totalSize, transformer);
    }

    @Override
    public <E> PageResult<E> buildFilteredPage(final Collection<? extends E> allAvailableEntries,
                                               final PageRequest pageRequest, final Object filterCriteria) {
        return buildFilteredPage(allAvailableEntries, pageRequest, filterCriteria, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, E> PageResult<T> buildFilteredPage(final Collection<? extends E> allAvailableEntries, // NOSONAR
                                                  final PageRequest pageRequest, final Object filterCriteria,
                                                  final PageEntryTransformer<T, E> transformer) {

        final Collection<? extends E> allAvailEntries;
        if (allAvailableEntries == null) {
            allAvailEntries = new ArrayList<>();
        } else {
            allAvailEntries = allAvailableEntries;
        }
        final PageRequest request = pageRequest == null ? new PageRequestDto() : pageRequest;
        List<E> allEntries = new ArrayList<>(allAvailEntries);
        if (request.getComparatorItem() != null) {
            Collections.sort(allEntries, objectComparatorFactory.newObjectComparator(request.getComparatorItem()));
        }
        List<E> filteredEntries;
        if (getPageBuilderFilter() == null) {
            filteredEntries = allEntries;
        } else {
            filteredEntries = new ArrayList<>(allAvailEntries.size());
            for (E entry : allEntries) {
                if (getPageBuilderFilter().accept(entry, filterCriteria)) {
                    filteredEntries.add(entry);
                }
            }
        }
        List<T> pageEntries = new ArrayList<>(filteredEntries.size());
        if (request.getFirstResult() < filteredEntries.size()) {
            int lastResult = request.getFirstResult() + request.getPageSize();
            for (int i = request.getFirstResult(); i < lastResult; i++) {
                if (i < filteredEntries.size()) {
                    if (transformer == null) { // NOSONAR
                        pageEntries.add((T) filteredEntries.get(i));
                    } else {
                        pageEntries.add(transformer.transform(filteredEntries.get(i)));
                    }
                }
            }
        }

        return new PageResult<>(pageEntries, request, filteredEntries.size());
    }

}
