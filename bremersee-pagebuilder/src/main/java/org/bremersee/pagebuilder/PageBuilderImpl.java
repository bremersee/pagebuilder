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

    private boolean transformEntriesBeforeBuilding = false;

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
     * @return Whether the transforming of the page entries should be done before the page is build or not
     * (default is {@code false}).
     */
    public boolean isTransformEntriesBeforeBuilding() {
        return transformEntriesBeforeBuilding;
    }

    /**
     * Specifies whether the transforming of the page entries should be done before the page is build or not.
     * The default value is {@code false}. So the page is build first and than only the entries of the page are
     * transformed. This is normally the faster way. But sometimes is can be necessary to transform the entries
     * before the page is build (for example if the source entries cannot be sorted by the comparator).
     *
     * @param transformEntriesBeforeBuilding should the entries be transormed before the page is build?
     */
    public void setTransformEntriesBeforeBuilding(boolean transformEntriesBeforeBuilding) {
        this.transformEntriesBeforeBuilding = transformEntriesBeforeBuilding;
    }

    @Override
    public String toString() {
        return "PageBuilderImpl [objectComparatorFactory=" + objectComparatorFactory
                + ", transformEntriesBeforeBuilding=" + transformEntriesBeforeBuilding + "]";
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
                                               final PageRequest pageRequest, final PageBuilderFilter filter) {
        return buildFilteredPage(allAvailableEntries, pageRequest, filter, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T, E> PageResult<T> buildFilteredPage(final Collection<? extends E> allAvailableEntries, // NOSONAR
                                                  final PageRequest pageRequest, final PageBuilderFilter filter,
                                                  final PageEntryTransformer<T, E> transformer) {

        final Collection<? extends E> allAvailEntries;
        if (allAvailableEntries == null) {
            allAvailEntries = new ArrayList<>();
        } else {
            allAvailEntries = allAvailableEntries;
        }
        final PageRequest request = pageRequest == null ? new PageRequestDto() : pageRequest;

        if (transformEntriesBeforeBuilding && transformer != null) {
            List<Object> targets = new ArrayList<>(allAvailEntries.size());
            for (E e : allAvailEntries) {
                targets.add(transformer.transform(e));
            }
            return buildInternalFilteredPage(targets, request, filter, null);
        } else {
            List<Object> allEntries = new ArrayList<Object>(allAvailEntries); // NOSONAR
            return buildInternalFilteredPage(allEntries, request, filter, transformer);
        }
    }

    @SuppressWarnings("unchecked")
    public PageResult buildInternalFilteredPage(final List<Object> allEntries, final PageRequest request, // NOSONAR
                                                final PageBuilderFilter filter,
                                                final PageEntryTransformer transformer) {

        if (request.getComparatorItem() != null) {
            Collections.sort(allEntries, objectComparatorFactory.newObjectComparator(request.getComparatorItem()));
        }

        final List<Object> filteredEntries;
        if (filter == null) {
            filteredEntries = allEntries;
        } else {
            filteredEntries = new ArrayList<>(allEntries.size());
            for (Object entry : allEntries) {
                if (filter.accept(entry)) {
                    filteredEntries.add(entry);
                }
            }
        }

        final List<Object> pageEntries = new ArrayList<>(filteredEntries.size());

        if (request.getFirstResult() < filteredEntries.size()) {
            final int lastResult = request.getFirstResult() + request.getPageSize();
            for (int i = request.getFirstResult(); i < lastResult; i++) {
                if (i < filteredEntries.size()) {
                    if (transformer == null) { // NOSONAR
                        pageEntries.add(filteredEntries.get(i));
                    } else {
                        pageEntries.add(transformer.transform(filteredEntries.get(i)));
                    }
                }
            }
        }

        return new PageResult<>(pageEntries, request, filteredEntries.size());
    }

}
