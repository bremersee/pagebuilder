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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author Christian Bremer
 *
 */
public class SpringPageRequest extends PageRequest {

    private static final long serialVersionUID = 1L;
    
    protected String query;
    
    protected Object extension;

    public SpringPageRequest(int page, int size, String query, Object extension) {
        super(page, size);
        this.query = query;
        this.extension = extension;
    }

    public SpringPageRequest(int page, int size, Sort sort, String query, Object extension) {
        super(page, size, sort);
        this.query = query;
        this.extension = extension;
    }

    public String getQuery() {
        return query;
    }

    public Object getExtension() {
        return extension;
    }

}
