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

package org.bremersee.pagebuilder.model;

import org.bremersee.comparator.model.ComparatorItem;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Christian Bremer
 */
public interface PageRequest extends Serializable {

    /**
     * Returns the requested page number (starting with 0).
     *
     * @return the requested page number (starting with 0)
     */
    int getPageNumber();

    /**
     * Returns the maximum number of elements on the page (is always greater than 0).
     *
     * @return the maximum number of elements on the page (is always greater than 0)
     */
    int getPageSize();

    /**
     * Returns the first result (offset).
     *
     * @return the first result (offset)
     */
    int getFirstResult();

    /**
     * Returns the comparator item (may be {@code null}).
     *
     * @return the comparator item (may be {@code null})
     */
    ComparatorItem getComparatorItem();

    /**
     * Returns the search query (may be {@code null}).
     *
     * @return the search query (may be {@code null})
     */
    String getQuery();

    /**
     * Returns custom extensions (may be {@code null}).
     *
     * @return custom extensions (may be {@code null})
     */
    Map<String, Object> getExtensions();

}
