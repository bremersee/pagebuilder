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

import java.io.Serializable;

/**
 * <p>
 * A filter for a {@link PageBuilder}.
 * </p>
 * 
 * @author Christian Bremer
 */
public interface PageBuilderFilter {

    /**
     * Determines whether an item should be added to a page or not.
     * 
     * @param pageItem
     *            the item that can be accepted or not
     * @param filter
     *            the filter
     * @return {@code true} if the item is accepted otherwise {@code false}
     */
    boolean accept(Object pageItem, Object filter);

    /**
     * Determines whether an item should be added to a page or not.
     * 
     * @param pageItemId
     *            the ID of the item
     * @param pageItemType
     *            the type (class) of the item
     * @param filter
     *            the filter
     * @return {@code true} if the item is accepted otherwise {@code false}
     */
    boolean accept(Serializable pageItemId, String pageItemType, Object filter);

}
