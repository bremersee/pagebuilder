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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.Validate;
import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequest;
import org.bremersee.pagebuilder.model.PageRequestDto;

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
     * Transforms a page into a page DTO.
     * 
     * @param page
     *            the page
     * @param transformer
     *            the transformer (may be {@code null} - than all entries of the
     *            page will be added to the DTO without transforming)
     * @return the page DTO
     */
    public static <E, T> PageDto createPageDto(Page<E> page, PageEntryTransformer<T, E> transformer) {
        if (page == null) {
            return null;
        }
        if (page instanceof PageDto && transformer == null) {
            return (PageDto) page;
        }
        final PageRequestDto pageRequestDto = createPageRequestDto(page.getPageRequest());
        if (transformer == null) {
            return new PageDto(page.getEntries(), pageRequestDto, page.getTotalSize());
        }
        List<T> entries = new ArrayList<>();
        for (E e : page.getEntries()) {
            entries.add(transformer.transform(e));
        }
        return new PageDto(entries, pageRequestDto, page.getTotalSize());
    }

    /**
     * Transforms a page request into a page request DTO.
     * 
     * @param pageRequest
     *            the page request (can be {@code null})
     * @return the page request DTO (can be {@code null})
     */
    public static PageRequestDto createPageRequestDto(PageRequest pageRequest) {
        PageRequestDto pageRequestDto;
        if (pageRequest == null) {
            pageRequestDto = null;
        } else if (pageRequest instanceof PageRequestDto) {
            pageRequestDto = (PageRequestDto) pageRequest;
        } else {
            pageRequestDto = new PageRequestDto(pageRequest);
        }
        return pageRequestDto;
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
