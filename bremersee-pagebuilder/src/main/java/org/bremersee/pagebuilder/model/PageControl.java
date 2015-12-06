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

/**
 * <p>
 * A {@link PageControl} can be used to display a {@link Page} on a web site.
 * </p>
 * 
 * @author Christian Bremer
 */
public interface PageControl extends Serializable {

    /**
     * Returns the page.
     */
    Page getPage();

    /**
     * Returns the pagination.
     */
    Pagination getPagination();

    /**
     * Returns the parameter name of the page number.
     */
    String getPageNumberParamName();

    /**
     * Returns the parameter name of the maximum results.
     */
    String getMaxResultsParamName();

    /**
     * Returns a list with maximum result options.
     */
    List<MaxResultsSelectorOption> getMaxResultsSelectorOptions();

    /**
     * Returns the parameter name of the comparator item.
     */
    String getComparatorParamName();

    /**
     * Returns the serialized value of the comparator item.
     */
    String getComparatorParamValue();

    /**
     * Returns {@code true} if a query field should be displayed, otherwise
     * {@code false}.
     */
    boolean isQuerySupported();

    /**
     * Returns the parameter name of the query.
     */
    String getQueryParamName();

    /**
     * Returns the query value.
     */
    String getQuery();

}
