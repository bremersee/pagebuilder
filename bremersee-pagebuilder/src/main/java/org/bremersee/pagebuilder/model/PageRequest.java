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

import java.io.Serializable;

import org.bremersee.comparator.model.ComparatorItem;

/**
 * @author Christian Bremer
 */
public interface PageRequest extends Serializable {

    /**
     * Returns the first result (may be {@code null}).
     * 
     * @return the first result
     */
    Integer getFirstResult();

    /**
     * Returns the number of maximum results (may be {@code null}).
     * 
     * @return the number of maximum results
     */
    Integer getMaxResults();

    /**
     * Returns the comparator item (may be {@code null}).
     * 
     * @return the comparator item
     */
    ComparatorItem getComparatorItem();

    /**
     * Returns the search query (may be {@code null}).
     * 
     * @return the search query
     */
    String getQuery();

    /**
     * Returns a custom extension (may be {@code null}).
     * 
     * @return a custom extension
     */
    Object getExtension();

}
