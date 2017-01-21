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

import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageRequest;

import java.util.Collection;

/**
 * <p>
 * A page builder creates a {@link Page} form a list of elements.
 * </p>
 * <p>
 * There are two ways to create a page:
 * <ul>
 * <li>The list of elements that build the page is pre-filtered.<br/>
 * The list of elements is the result of a SQL query for example.<br/>
 * Than the page must build with
 * {@link PageBuilder#buildPage(Iterable, PageRequest, long)} .</li>
 * <li>The list of items that build the page is not filtered and contains all
 * available elements.<br/>
 * Than the page must build with
 * {@link PageBuilder#buildFilteredPage(Collection, PageRequest, Object)} .</li>
 * </ul>
 * </p>
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"SameParameterValue", "unused"})
public interface PageBuilder {

    <E> Page<E> buildPage(Iterable<? extends E> pageElements, PageRequest pageRequest, long totalSize);

    <T, E> Page<T> buildPage(Iterable<? extends E> pageElements, PageRequest pageRequest, long totalSize,
                             PageEntryTransformer<T, E> transformer);

    <E> Page<E> buildFilteredPage(Collection<? extends E> allAvailableElements, PageRequest pageRequest,
                                  Object filterCriteria);

    <T, E> Page<T> buildFilteredPage(Collection<? extends E> allAvailableEntries, PageRequest pageRequest,
                                     Object filterCriteria, PageEntryTransformer<T, E> transformer);

}
