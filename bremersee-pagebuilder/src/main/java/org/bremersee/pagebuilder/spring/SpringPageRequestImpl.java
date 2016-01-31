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

import java.util.LinkedHashMap;
import java.util.Map;

import org.bremersee.pagebuilder.model.PageRequestDto;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Christian Bremer
 */
public class SpringPageRequestImpl extends PageRequest implements SpringPageRequest {

    private static final long serialVersionUID = 1L;

    protected String query;

    protected final Map<String, Object> extensions = new LinkedHashMap<>();

    /**
     * Creates a {@link PageRequest} with the additional attributes of a
     * {@link PageRequestDto}.
     * 
     * @param pageNumber
     *            the page number
     * @param pageSize
     *            the page size
     * @param query
     *            the query
     * @param extensions
     *            the extensions
     */
    public SpringPageRequestImpl(int pageNumber, int pageSize, String query, Map<String, Object> extensions) {
        super(pageNumber, pageSize);
        this.query = query;
        if (extensions != null) {
            this.extensions.putAll(extensions);
        }
    }

    /**
     * Creates a {@link PageRequest} with the additional attributes of a
     * {@link PageRequestDto}.
     * 
     * @param pageNumber
     *            the page number
     * @param pageSize
     *            the page size
     * @param sort
     *            the sort (can be {@code null})
     * @param query
     *            the query
     * @param extensions
     *            the extensions
     */
    public SpringPageRequestImpl(int pageNumber, int pageSize, Sort sort, String query, Map<String, Object> extensions) {
        super(pageNumber, pageSize, sort);
        this.query = query;
        if (extensions != null) {
            this.extensions.putAll(extensions);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.spring.SpringPageRequest#getQuery()
     */
    @Override
    public String getQuery() {
        return query;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.spring.SpringPageRequest#getExtensions()
     */
    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

}
