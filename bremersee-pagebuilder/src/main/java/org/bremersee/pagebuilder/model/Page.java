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
 * A page is a sublist of a list of elements.
 * </p>
 *
 * @author Christian Bremer
 */
public interface Page<E> extends Serializable {

    /**
     * Returns the elements of the page.
     *
     * @return the elements of the page
     */
    List<E> getEntries();

    /**
     * Returns the page request.
     */
    PageRequest getPageRequest();

    /**
     * Returns the size of all available elements.
     */
    long getTotalSize();

    /**
     * Returns the number of all pages.
     */
    int getTotalPages();

}
