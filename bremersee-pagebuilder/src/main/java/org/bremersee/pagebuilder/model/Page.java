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
import java.util.List;

import org.bremersee.comparator.model.ComparatorItem;

/**
 * <p>
 * A page consist at least a list of items. Furthermore it may has information
 * about the size of all available items, the number of the first item of this
 * page, the maximum number of items and how they are sorted.
 * </p>
 * 
 * @author Christian Bremer
 */
public interface Page extends Serializable {

    /**
     * Return the size of all available entries.
     * 
     * @return the size of all available entries (may be {@code null})
     */
    Integer getTotalSize();

    /**
     * Return the first result number.
     * 
     * @return the first result number
     */
    Integer getFirstResult();

    /**
     * Return the maximum number of results.
     * 
     * @return the maximum number of results
     */
    Integer getMaxResults();

    /**
     * Return the comparator item that is used for sorting.
     * 
     * @return the comparator item
     */
    ComparatorItem getComparatorItem();

    /**
     * Return the entries of this page.
     * 
     * @return the entries of this page
     */
    List<Object> getEntries();

    /**
     * Calculates the previous first result number. It requires that
     * {@link Page#getTotalSize()}, {@link Page#getMaxResults()} and
     * {@link Page#getFirstResult()} are set (not {@code null}). Otherwise
     * {@code null} will be returned.<br/>
     * Be aware: If the previous first number is smaller than {@code 0},
     * {@code null} will be returned, too.
     * 
     * @return the previous first result number or {@code null}
     * @see ModelUtils#getPreviousFirstResult(Page)
     */
    Integer getPreviousFirstResult();

    /**
     * Calculates the next first result number. It requires that
     * {@link Page#getTotalSize()}, {@link Page#getMaxResults()} and
     * {@link Page#getFirstResult()} are set (not {@code null}). Otherwise
     * {@code null} will be returned.<br/>
     * Be aware: If the next first number is bigger than
     * {@link Integer#MAX_VALUE} or {@code totalSize}, {@code null} will be
     * returned, too.
     * 
     * @return the next first result number or {@code null}
     * @see ModelUtils#getNextFirstResult(Page)
     */
    Integer getNextFirstResult();

    /**
     * Calculates the number of available pages. It requires that
     * {@link Page#getTotalSize()} and {@link Page#getMaxResults()} are set (not
     * {@code null}). Otherwise {@code null} will be returned.
     * 
     * @return the number of available pages or {@code null}
     * @see ModelUtils#getPageSize(Page)
     */
    Integer getPageSize();

    /**
     * Calculates the current page number. It requires that
     * {@link Page#getFirstResult()} and {@link Page#getMaxResults()} are set
     * (not {@code null}). Otherwise {@code null} will be returned.
     * 
     * @return the current page number or {@code null}
     * @see ModelUtils#getCurrentPage(Page)
     */
    Integer getCurrentPage();

    /**
     * Calculates the pagination size depending on the number of fields (
     * {@code fieldSize}). It requires that {@link Page#getPageSize()} is not
     * {@code null}.
     * 
     * <pre>
     *    ---    ---    ---    ---    ---      ---    ---    ---    ---
     *   | 1 |  | 2 |  | 3 |  | 4 |  | 5 |    | 6 |  | 7 |  | 8 |  | 9 | 
     *    ---    ---    ---    ---    ---      ---    ---    ---    ---
     *   Page1  Page2  Page3  Page4  Page5    Page6  Page7  Page8  Page9
     *   |                               |    |                               |
     *    -------------------------------      -------------------------------
     *              field size                           field size
     *   |                                                                    |
     *    --------------------------------------------------------------------
     *                            pagination size = 2
     * </pre>
     * 
     * @param fieldSize
     *            the field size
     * @return the pagination size or {@code null}
     * @see ModelUtils#getPaginationSize(Page, int)
     */
    Integer getPaginationSize(int fieldSize);

}
