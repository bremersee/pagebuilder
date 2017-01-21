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

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.Validate;
import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequest;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.bremersee.utils.CastUtils;
import org.w3c.dom.Node;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * Utility methods.
 * </p>
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public abstract class PageBuilderUtils {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER = new ObjectMapper();

    private static final Map<java.lang.reflect.AnnotatedElement, JAXBContext> JAXB_CONTEXTS = new ConcurrentHashMap<>();

    private PageBuilderUtils() {
        super();
    }

    private static JAXBContext getJaxbContext(final Class<?> valueType) throws JAXBException {
        JAXBContext jaxbContext = JAXB_CONTEXTS.get(valueType.getPackage());
        if (jaxbContext == null) {
            jaxbContext = JAXB_CONTEXTS.get(valueType);
        }
        if (jaxbContext == null) {
            try {
                jaxbContext = JAXBContext.newInstance(valueType.getPackage().getName());
            } catch (JAXBException e) { // NOSONAR
                jaxbContext = JAXBContext.newInstance(valueType);
                JAXB_CONTEXTS.put(valueType, jaxbContext);
            }
        }
        return jaxbContext;
    }

    /**
     * Casts a page.
     */
    @SuppressWarnings("unchecked")
    public static <E> Page<E> cast(final Page<?> page) {
        return (Page<E>) page;
    }

    /**
     * Creates a page.
     *
     * @param entries     the page entries
     * @param pageRequest the page request
     * @param totalSize   the total size
     * @param transformer the entry transformer (may be {@code null} - than all entries
     *                    will be added to the page without transforming)
     * @return the page
     */
    @SuppressWarnings("unchecked")
    public static <E, T> PageResult<T> createPage(final Iterable<? extends E> entries, final PageRequest pageRequest,
                                                  final long totalSize, final PageEntryTransformer<T, E> transformer) {

        final PageResult<T> page = new PageResult<>();
        page.setPageRequest(pageRequest == null ? new PageRequestDto() : pageRequest);
        page.setTotalSize(totalSize);
        if (entries != null) {
            for (E entry : entries) {
                if (transformer == null) {
                    page.getEntries().add((T) entry);
                } else {
                    T targetEntry = transformer.transform(entry);
                    page.getEntries().add(targetEntry);
                }
            }
        }
        return page;
    }

    /**
     * Transforms a page into another page.
     *
     * @param sourcePage  the source page
     * @param transformer the entry transformer (may be {@code null} - than all entries
     *                    of the source page will be added to the target page without
     *                    transforming)
     * @return the target page
     */
    public static <E, T> Page<T> createPage(final Page<? extends E> sourcePage,
                                            final PageEntryTransformer<T, E> transformer) {
        if (sourcePage == null) {
            return null;
        }
        if (transformer == null) {
            return cast(sourcePage);
        }
        return createPage(sourcePage.getEntries(), sourcePage.getPageRequest(), sourcePage.getTotalSize(), transformer);
    }

    /**
     * Transforms a page into a page DTO.
     *
     * @param page        the page
     * @param transformer the entry transformer (may be {@code null} - than all entries
     *                    of the page will be added to the DTO without transforming)
     * @return the page DTO
     */
    public static <E, T> PageDto createPageDto(final Page<? extends E> page,
                                               final PageEntryTransformer<T, E> transformer) {
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
        final List<T> entries = new ArrayList<>();
        for (E e : page.getEntries()) {
            entries.add(transformer.transform(e));
        }
        final PageDto pageDto = new PageDto();
        pageDto.setEntries(CastUtils.cast(entries));
        pageDto.setPageRequest(pageRequestDto);
        pageDto.setTotalSize(page.getTotalSize());
        return pageDto;
    }

    /**
     * Transforms a page request into a page request DTO.
     *
     * @param pageRequest the page request (can be {@code null})
     * @return the page request DTO (can be {@code null})
     */
    public static PageRequestDto createPageRequestDto(final PageRequest pageRequest) {
        final PageRequestDto pageRequestDto;
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
     * Transforms a XML node or a JSON map into an object.
     *
     * @param xmlNodeOrJsonMap the XML node or JSON map
     * @param valueType        the class of the target object
     * @param defaultObject    a default object (optional)
     * @param jaxbContext      the {@link JAXBContext} (can be null)
     * @param objectMapper     the JSON object mapper (optional)
     * @return the target object
     * @throws Exception if transformation fails
     */
    @SuppressWarnings("unchecked")
    public static <T, S extends T> T transform(final Object xmlNodeOrJsonMap, final Class<T> valueType,
                                               final S defaultObject, final JAXBContext jaxbContext,
                                               final ObjectMapper objectMapper) throws Exception { // NOSONAR

        if (xmlNodeOrJsonMap == null) {
            return defaultObject;
        }
        Validate.notNull(valueType, "valueType must not be null"); // NOSONAR
        if (valueType.isAssignableFrom(xmlNodeOrJsonMap.getClass())) {
            return valueType.cast(xmlNodeOrJsonMap);
        }
        if (xmlNodeOrJsonMap instanceof Node) {
            return xmlNodeToObject((Node) xmlNodeOrJsonMap, valueType, jaxbContext);
        }
        if (xmlNodeOrJsonMap instanceof Map) {
            return jsonMapToObject((Map<String, Object>) xmlNodeOrJsonMap, valueType, objectMapper);
        }
        throw new IllegalArgumentException("xmlNodeOrJsonMap must be of type " + valueType + ", " + Node.class.getName()
                + " or of type " + Map.class.getName());
    }

    /**
     * Transforms a XML node into an object.
     *
     * @param node        the XML node
     * @param valueType   the class of the target object
     * @param jaxbContext the {@link JAXBContext} (can be null)
     * @return the target object
     * @throws JAXBException if transformation fails
     */
    public static <T> T xmlNodeToObject(final Node node, final Class<T> valueType,
                                        final JAXBContext jaxbContext) throws JAXBException {
        if (node == null) {
            return null;
        }
        Validate.notNull(valueType, "valueType must not be null");
        if (jaxbContext == null) {
            return valueType.cast(getJaxbContext(valueType).createUnmarshaller().unmarshal(node));
        }
        return valueType.cast(jaxbContext.createUnmarshaller().unmarshal(node));
    }

    /**
     * Transforms a JSON map to an object.
     *
     * @param map          the JSON map
     * @param valueType    the class of the target object
     * @param objectMapper the JSON object mapper (optional)
     * @return the target object
     * @throws IOException if transformation fails
     */
    public static <T> T jsonMapToObject(final Map<String, Object> map, final Class<T> valueType,
                                        final ObjectMapper objectMapper) throws IOException {
        if (map == null) {
            return null;
        }
        Validate.notNull(valueType, "valueType must not be null");
        if (objectMapper == null) {
            return DEFAULT_OBJECT_MAPPER.readValue(DEFAULT_OBJECT_MAPPER.writeValueAsBytes(map), valueType);
        }
        return objectMapper.readValue(objectMapper.writeValueAsBytes(map), valueType);
    }

}
