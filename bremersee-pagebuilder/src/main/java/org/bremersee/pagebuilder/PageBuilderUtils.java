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

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <p>
 * Utility methods.
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class PageBuilderUtils {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    private PageBuilderUtils() {
    }

    /**
     * Calculates the first result of a given page number and the maximum
     * results.
     * 
     * @param pageNumber
     *            the page number
     * @param maxResults
     *            maximum results
     * @return the first result
     */
    public static int getFirstResult(int pageNumber, int maxResults) {
        Long firstResult = (long) pageNumber * (long) maxResults;
        if (firstResult > Integer.MAX_VALUE) {
            firstResult = (long) Integer.MAX_VALUE;
        }
        return firstResult.intValue();
    }

    /**
     * Transforms a JSON map to an object.
     * 
     * @param map
     *            the JSON map
     * @param valueType
     *            the class of the target object
     * @param objectMapper
     *            the JSON object mapper (optional)
     * @return the target object
     * @throws IOException
     *             if transformation fails
     */
    public static <T> T jsonMapToObject(Map<String, Object> map, Class<T> valueType, ObjectMapper objectMapper)
            throws IOException {
        if (map == null) {
            return null;
        }
        Validate.notNull(valueType, "valueType must not be null");
        if (objectMapper == null) {
            objectMapper = DEFAULT_OBJECT_MAPPER;
        }
        return objectMapper.readValue(objectMapper.writeValueAsBytes(map), valueType);
    }

}
