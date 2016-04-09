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
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bremersee.comparator.ComparatorItemTransformer;
import org.bremersee.comparator.ComparatorItemTransformerImpl;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.PageRequest;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Christian Bremer
 *
 */
public class PageRequestBuilderImpl implements PageRequestBuilder {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected ComparatorItemTransformer comparatorItemTransformer = new ComparatorItemTransformerImpl();

    protected boolean urlEncoded = false;

    protected String charset = StandardCharsets.UTF_8.name();

    /**
     * Default constructor.
     */
    public PageRequestBuilderImpl() {
    }

    public PageRequestBuilderImpl(ComparatorItemTransformer comparatorItemTransformer) {
        setComparatorItemTransformer(comparatorItemTransformer);
    }

    public PageRequestBuilderImpl(ComparatorItemTransformer comparatorItemTransformer, boolean urlEncoded,
            String charset) {
        setComparatorItemTransformer(comparatorItemTransformer);
        setUrlEncoded(urlEncoded);
        setCharset(charset);
    }

    public void setComparatorItemTransformer(ComparatorItemTransformer comparatorItemTransformer) {
        if (comparatorItemTransformer != null) {
            this.comparatorItemTransformer = comparatorItemTransformer;
        }
    }

    public void setUrlEncoded(boolean urlEncoded) {
        this.urlEncoded = urlEncoded;
    }

    public void setCharset(String charset) {
        if (StringUtils.isNotBlank(charset)) {
            this.charset = charset;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bremersee.pagebuilder.PageRequestBuilder#buildPageRequest(java.io.
     * Serializable, java.io.Serializable, java.io.Serializable,
     * java.io.Serializable, java.util.Map)
     */
    @Override
    public PageRequest buildPageRequest(Serializable pageNumber, Serializable pageSize, Serializable comparatorItem,
            Serializable query, Map<String, Object> extensions) {

        //@formatter:off
        return new PageRequestDto(
                getPageNumber(pageNumber), 
                getPageSize(pageSize), 
                getComparatorItem(comparatorItem),
                getQuery(query), 
                extensions);
        //@formatter:on
    }

    protected int getPageNumber(Serializable pageNumber) {
        int value = 0;
        if (pageNumber != null) {
            if (pageNumber instanceof Number) {
                value = ((Number) pageNumber).intValue();
            } else {
                try {
                    value = Integer.valueOf(pageNumber.toString());
                } catch (Throwable t) {
                    value = 0;
                    log.warn("Getting page number from value [" + pageNumber + "] failed. Returning " + value + ".");
                }
            }
        }
        return value >= 0 ? value : 0;
    }

    protected int getPageSize(Serializable pageSize) {
        int value = Integer.MAX_VALUE;
        if (pageSize != null) {
            if (pageSize instanceof Number) {
                value = ((Number) pageSize).intValue();
            } else {
                try {
                    value = Integer.valueOf(pageSize.toString());
                } catch (Throwable t) {
                    value = Integer.MAX_VALUE;
                    log.warn("Getting page size from value [" + pageSize + "] failed. Returning " + value + ".");
                }
            }
        }
        return value > 0 ? value : Integer.MAX_VALUE;
    }

    protected ComparatorItem getComparatorItem(Serializable comparatorItem) {
        ComparatorItem value = null;
        if (comparatorItem != null) {
            if (comparatorItem instanceof ComparatorItem) {
                value = (ComparatorItem) comparatorItem;
            } else {
                try {
                    value = comparatorItemTransformer.fromString(comparatorItem.toString(), urlEncoded, charset);
                } catch (Throwable t) {
                    value = null;
                    log.warn("Getting comparator item from value [" + comparatorItem + "] failed. Returning null.");
                }
            }
        }
        return value;
    }

    protected String getQuery(Serializable query) {
        return query == null ? null : query.toString();
    }

}
