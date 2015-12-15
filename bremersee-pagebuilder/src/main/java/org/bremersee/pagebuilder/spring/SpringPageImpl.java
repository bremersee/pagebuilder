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

package org.bremersee.pagebuilder.spring;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * @author Christian Bremer
 */
class SpringPageImpl<T> extends PageImpl<T> {

    private static final long serialVersionUID = 1L;

    private Pageable pageable;

    /**
     * Constructor of a {@link SpringPageImpl}.
     * 
     * @param content
     *            the content of this page, must not be null
     * @param pageable
     *            the paging information, can be null
     * @param total
     *            the total amount of items available. The total might be
     *            adapted considering the length of the content given, if it is
     *            going to be the content of the last page. This is in place to
     *            mitigate inconsistencies
     */
    protected SpringPageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        this.pageable = pageable;
    }

    /**
     * Returns the original paging information.
     * 
     * @return the paging information, can be null
     */
    protected Pageable getPageable() {
        return pageable;
    }

}
