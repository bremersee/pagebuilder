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

import org.apache.commons.lang3.StringUtils;
import org.bremersee.comparator.ComparatorItemTransformer;
import org.bremersee.comparator.ComparatorItemTransformerImpl;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.PageRequest;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Christian Bremer
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class PageRequestBuilderImpl implements PageRequestBuilder {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected ComparatorItemTransformer comparatorItemTransformer = new ComparatorItemTransformerImpl();

    protected boolean urlEncoded = false;

    protected String charset = StandardCharsets.UTF_8.name();

    /**
     * Default constructor.
     */
    public PageRequestBuilderImpl() {
        super();
    }

    public PageRequestBuilderImpl(final ComparatorItemTransformer comparatorItemTransformer) {
        setComparatorItemTransformer(comparatorItemTransformer);
    }

    public PageRequestBuilderImpl(final ComparatorItemTransformer comparatorItemTransformer, final boolean urlEncoded,
                                  final String charset) {
        setComparatorItemTransformer(comparatorItemTransformer);
        setUrlEncoded(urlEncoded);
        setCharset(charset);
    }

    public void setComparatorItemTransformer(final ComparatorItemTransformer comparatorItemTransformer) {
        if (comparatorItemTransformer != null) {
            this.comparatorItemTransformer = comparatorItemTransformer;
        }
    }

    public void setUrlEncoded(boolean urlEncoded) {
        this.urlEncoded = urlEncoded;
    }

    public void setCharset(final String charset) {
        if (StringUtils.isNotBlank(charset)) {
            this.charset = charset;
        }
    }

    @Override
    public PageRequest buildPageRequest(final Serializable pageNumber, final Serializable pageSize,
                                        final Serializable comparatorItem, final Serializable query,
                                        final Map<String, Object> extensions) {

        //@formatter:off
        return new PageRequestDto(
                getPageNumber(pageNumber),
                getPageSize(pageSize),
                getComparatorItem(comparatorItem),
                getQuery(query),
                extensions);
        //@formatter:on
    }

    protected int getPageNumber(final Serializable pageNumber) {
        int value = 0;
        if (pageNumber != null) {
            if (pageNumber instanceof Number) {
                value = ((Number) pageNumber).intValue();
            } else {
                try {
                    value = Integer.valueOf(pageNumber.toString());
                } catch (Throwable t) { // NOSONAR
                    value = 0;
                    log.warn("Getting page number from value [" + pageNumber + "] failed. Returning " + value + ".");
                }
            }
        }
        return value >= 0 ? value : 0;
    }

    protected int getPageSize(final Serializable pageSize) {
        int value = Integer.MAX_VALUE;
        if (pageSize != null) {
            if (pageSize instanceof Number) {
                value = ((Number) pageSize).intValue();
            } else {
                try {
                    value = Integer.valueOf(pageSize.toString());
                } catch (Throwable t) { // NOSONAR
                    value = Integer.MAX_VALUE;
                    log.warn("Getting page size from value [" + pageSize + "] failed. Returning " + value + ".");
                }
            }
        }
        return value > 0 ? value : Integer.MAX_VALUE;
    }

    protected ComparatorItem getComparatorItem(final Serializable comparatorItem) {
        ComparatorItem value = null;
        if (comparatorItem != null) {
            if (comparatorItem instanceof ComparatorItem) {
                value = (ComparatorItem) comparatorItem;
            } else {
                try {
                    value = comparatorItemTransformer.fromString(comparatorItem.toString(), urlEncoded, charset);
                } catch (Throwable t) { // NOSONAR
                    value = null;
                    log.warn("Getting comparator item from value [" + comparatorItem + "] failed. Returning null.");
                }
            }
        }
        return value;
    }

    protected String getQuery(final Serializable query) {
        return query == null ? null : query.toString();
    }

}
