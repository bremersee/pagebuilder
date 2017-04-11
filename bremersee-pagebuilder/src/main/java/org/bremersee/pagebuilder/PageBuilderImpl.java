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
import java.util.List;
import java.util.stream.Collectors;

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
     * Returns the object comparator factory.
     *
     * @return the object comparator factory
     */
    protected ObjectComparatorFactory getObjectComparatorFactory() {
        return objectComparatorFactory;
    }

    /**
     * Sets the object comparator factory. A default one is present.
     *
     * @param objectComparatorFactory the object comparator factory
     */
    public void setObjectComparatorFactory(final ObjectComparatorFactory objectComparatorFactory) {
        if (objectComparatorFactory != null) {
            this.objectComparatorFactory = objectComparatorFactory;
        }
    }

    @Override
    public String toString() {
        return "PageBuilderImpl [objectComparatorFactory=" + objectComparatorFactory + "]";
    }

    @Override
    public <E> PageResult<E> buildPage(final Iterable<? extends E> pageEntries, final PageRequest pageRequest,
                                       final long totalSize) {
        return buildPage(pageEntries, pageRequest, totalSize, null);
    }

    @Override
    public <T, E> PageResult<T> buildPage(final Iterable<? extends E> pageEntries,
                                          final PageRequest pageRequest,
                                          final long totalSize,
                                          final PageEntryTransformer<T, E> transformer) {
        return PageBuilderUtils.createPage(
                pageEntries,
                pageRequest,
                totalSize,
                transformer);
    }

    @Override
    public <E> PageResult<E> buildFilteredPage(final Collection<? extends E> allAvailableEntries,
                                               final PageRequest pageRequest,
                                               final PageBuilderFilter filter) {
        return buildFilteredPage(allAvailableEntries,
                pageRequest,
                true,
                filter,
                true,
                null);
    }

    @Override
    public <T, E> PageResult<T> buildFilteredPage(final Collection<? extends E> allAvailableElements,
                                                  final PageRequest pageRequest,
                                                  final boolean sortEntriesBeforeTransforming,
                                                  final PageEntryTransformer<T, E> transformer) {
        return buildFilteredPage(
                allAvailableElements,
                pageRequest,
                sortEntriesBeforeTransforming,
                null,
                true,
                transformer);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, E> PageResult<T> buildFilteredPage(final Collection<? extends E> allAvailableEntries, // NOSONAR
                                                  final PageRequest pageRequest,
                                                  final boolean sortEntriesBeforeTransforming,
                                                  final PageBuilderFilter filter,
                                                  final boolean executeFilterBeforeTransforming,
                                                  final PageEntryTransformer<T, E> transformer) {

        final Collection<? extends E> allAvailEntries;
        if (allAvailableEntries == null) {
            allAvailEntries = new ArrayList<>();
        } else {
            allAvailEntries = allAvailableEntries;
        }
        final PageRequest request = pageRequest == null ? new PageRequestDto() : pageRequest;

        final List<Object> entries;
        if (executeFilterBeforeTransforming && filter != null) {
            entries = allAvailEntries.stream().filter(filter::accept).collect(Collectors.toList());
        } else {
            entries = toList(allAvailEntries);
        }

        final boolean doSortBeforeTransforming = transformer == null || sortEntriesBeforeTransforming;
        if (request.getComparatorItem() != null && doSortBeforeTransforming) {
            entries.sort(objectComparatorFactory.newObjectComparator(request.getComparatorItem()));
        }

        if ((executeFilterBeforeTransforming || filter == null)
                && (sortEntriesBeforeTransforming || transformer == null)) {

            // faster way
            final List<Object> pageEntries = new ArrayList<>(entries.size());
            if (request.getFirstResult() < entries.size()) {
                final int lastResult = request.getFirstResult() + request.getPageSize();
                for (int i = request.getFirstResult(); i < lastResult; i++) {
                    if (i < entries.size()) { // NOSONAR
                        if (transformer == null) {
                            pageEntries.add(entries.get(i));
                        } else {
                            pageEntries.add(transformer.transform((E) entries.get(i)));
                        }
                    }
                }
            }
            return (PageResult<T>) new PageResult<>(pageEntries, request, entries.size());
        }

        List<Object> transformedEntries;
        if (transformer == null) {
            transformedEntries = entries;
        } else {
            transformedEntries = new ArrayList<>();
            for (Object source : entries) {
                transformedEntries.add(transformer.transform((E) source));
            }
        }

        List<Object> transformedFilteredEntries;
        if (!executeFilterBeforeTransforming && filter != null) {
            transformedFilteredEntries = transformedEntries.stream().filter(filter::accept).collect(Collectors.toList());
        } else {
            transformedFilteredEntries = transformedEntries;
        }

        if (!doSortBeforeTransforming && request.getComparatorItem() != null) {
            transformedFilteredEntries.sort(objectComparatorFactory.newObjectComparator(request.getComparatorItem()));
        }

        final List<Object> pageEntries = new ArrayList<>(transformedFilteredEntries.size());
        if (request.getFirstResult() < transformedFilteredEntries.size()) {
            final int lastResult = request.getFirstResult() + request.getPageSize();
            for (int i = request.getFirstResult(); i < lastResult; i++) {
                if (i < transformedFilteredEntries.size()) {
                    pageEntries.add(transformedFilteredEntries.get(i));
                }
            }
        }

        return (PageResult<T>) new PageResult<>(pageEntries, request, transformedFilteredEntries.size());
    }

    private List<Object> toList(Collection<?> col) {
        if (col instanceof List) {
            //noinspection unchecked
            return (List<Object>) col;
        } else {
            return new ArrayList<>(col);
        }
    }

}
