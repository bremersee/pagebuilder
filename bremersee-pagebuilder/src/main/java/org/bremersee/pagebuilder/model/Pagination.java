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
 * A pagination can be used to display pagination buttons on a web site.
 * </p>
 * 
 * @author Christian Bremer
 */
public interface Pagination extends Serializable {

    /**
     * Returns all buttons.
     */
    List<PaginationButton> getAllButtons();

    /**
     * Returns the maximum numbers of pagination buttons.
     */
    int getMaxPaginationButtons();

    /**
     * Returns the first pagination button.
     */
    PaginationButton getFirstPageButton();

    /**
     * Returns the previous pagination buttons.
     */
    PaginationButton getPreviousPageButton();

    /**
     * Returns the pagination buttons.
     */
    List<PaginationButton> getButtons();

    /**
     * Returns the next pagination button.
     */
    PaginationButton getNextPageButton();

    /**
     * Returns the last pagination button.
     */
    PaginationButton getLastPageButton();

}
