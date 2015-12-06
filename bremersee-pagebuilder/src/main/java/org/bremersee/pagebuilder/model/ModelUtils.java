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

import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang3.Validate;

/**
 * <p>
 * Utility methods.
 * </p>
 * 
 * @author Christian Bremer
 */
@XmlTransient
public abstract class ModelUtils {

    private ModelUtils() {
    }

    /**
     * Calculates the previous first result number. It requires that
     * {@link Page#getTotalSize()}, {@link Page#getMaxResults()} and
     * {@link Page#getFirstResult()} are set (not {@code null}). Otherwise
     * {@code null} will be returned.<br/>
     * Be aware: If the previous first number is smaller than {@code 0},
     * {@code null} will be returned, too.
     * 
     * @param page
     *            the page
     * @return the previous first result number or {@code null}
     * @throws IllegalArgumentException
     *             if page is {@code null}
     */
    public static Integer getPreviousFirstResult(Page page) {
        Validate.notNull(page, "page must not be null");
        if (page.getTotalSize() != null && page.getFirstResult() != null && page.getMaxResults() != null) {
            long previous = page.getFirstResult() - page.getMaxResults();
            if (previous < Integer.MIN_VALUE) {
                return null;
            }
            return (int) previous < 0 ? null : (int) previous;
        }
        return null;
    }

    /**
     * Calculates the next first result number. It requires that
     * {@link Page#getTotalSize()}, {@link Page#getMaxResults()} and
     * {@link Page#getFirstResult()} are set (not {@code null}). Otherwise
     * {@code null} will be returned.<br/>
     * Be aware: If the next first number is bigger than
     * {@link Integer#MAX_VALUE} or {@code totalSize}, {@code null} will be
     * returned, too.
     * 
     * @param page
     *            the page
     * @return the next first result number or {@code null}
     * @throws IllegalArgumentException
     *             if page is {@code null}
     */
    public static Integer getNextFirstResult(Page page) {
        Validate.notNull(page, "page must not be null");
        if (page.getTotalSize() != null && page.getFirstResult() != null && page.getMaxResults() != null) {
            long next = page.getFirstResult() + page.getMaxResults();
            if (next > Integer.MAX_VALUE) {
                return null;
            }
            return (int) next >= page.getTotalSize() ? null : (int) next;
        }
        return null;
    }

    /**
     * Calculates the number of available pages. It requires that
     * {@link Page#getTotalSize()} and {@link Page#getMaxResults()} are set (not
     * {@code null}). Otherwise {@code null} will be returned.
     *
     * @param page
     *            the page
     * @return the number of available pages or {@code null}
     * @throws IllegalArgumentException
     *             if page is {@code null}
     */
    public static Integer getPageSize(Page page) {
        Validate.notNull(page, "page must not be null");
        if (page.getTotalSize() != null && page.getMaxResults() != null && page.getMaxResults() > 0) {
            return Double.valueOf(Math.ceil((double) page.getTotalSize() / (double) page.getMaxResults())).intValue();
        }
        return null;
    }

    /**
     * Calculates the current page number. It requires that
     * {@link Page#getFirstResult()} and {@link Page#getMaxResults()} are set
     * (not {@code null}). Otherwise {@code null} will be returned.
     * 
     * @param page
     *            the page
     * @return the current page number or {@code null}
     * @throws IllegalArgumentException
     *             if page is {@code null}
     */
    public static Integer getCurrentPage(Page page) {
        Validate.notNull(page, "page must not be null");
        if (page.getFirstResult() != null && page.getMaxResults() != null && page.getMaxResults() > 0) {
            return Double.valueOf(Math.floor((double) page.getFirstResult() / (double) page.getMaxResults()))
                    .intValue();
        }
        return null;
    }

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
     * @param page
     *            the page
     * @param fieldSize
     *            the field size
     * @return the pagination size or {@code null}
     * @throws IllegalArgumentException
     *             if page is {@code null}
     */
    public static Integer getPaginationSize(Page page, int fieldSize) {
        Validate.notNull(page, "page must not be null");
        Integer pageSize = page.getPageSize();
        if (pageSize != null) {
            if (pageSize == 0) {
                return 0;
            }
            if (fieldSize < pageSize) {
                fieldSize = pageSize;
            }
            return Double.valueOf(Math.ceil((double) pageSize / (double) fieldSize)).intValue();
        }
        return null;
    }

}
