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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.bremersee.comparator.model.ComparatorItem;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author Christian Bremer
 */
@XmlRootElement(name = "pageRequest")
@XmlType(name = "pageRequestType", propOrder = { "firstResult", "maxResults", "comparatorItem", "query", "extension" })
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(Include.NON_EMPTY)
public class PageRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "firstResult", required = false)
    @JsonProperty(value = "firstResult", required = false)
    private Integer firstResult;

    @XmlElement(name = "maxResults", required = false)
    @JsonProperty(value = "maxResults", required = false)
    private Integer maxResults;

    @XmlElement(name = "comparatorItem", required = false)
    @JsonProperty(value = "comparatorItem", required = false)
    private ComparatorItem comparatorItem;

    @XmlElement(name = "query", required = false)
    @JsonProperty(value = "query", required = false)
    private String query;

    @XmlAnyElement(lax = true)
    @JsonProperty(value = "extension", required = false)
    private Object extension;

    /**
     * Default constructor.
     */
    public PageRequestDto() {
    }

    /**
     * Creates a page request with the specified parameters.
     * 
     * @param firstResult
     *            the first result (may be {@code null})
     * @param maxResults
     *            the number of maximum results (may be {@code null})
     * @param comparatorItem
     *            the comparator item (may be {@code null})
     * @param query
     *            the search query (may be {@code null})
     */
    public PageRequestDto(Integer firstResult, Integer maxResults, ComparatorItem comparatorItem, String query) {
        super();
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.comparatorItem = comparatorItem;
        this.query = query;
    }

    /**
     * Creates a page request with the specified parameters.
     * 
     * @param firstResult
     *            the first result (may be {@code null})
     * @param maxResults
     *            the number of maximum results (may be {@code null})
     * @param comparatorItem
     *            the comparator item (may be {@code null})
     * @param query
     *            the search query (may be {@code null})
     * @param extension
     *            a custom extension (may be {@code null})
     */
    public PageRequestDto(Integer firstResult, Integer maxResults, ComparatorItem comparatorItem, String query,
            Object extension) {
        super();
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.comparatorItem = comparatorItem;
        this.query = query;
        this.extension = extension;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageRequestDto [firstResult=" + firstResult + ", maxResults=" + maxResults + ", comparatorItem="
                + comparatorItem + ", query=" + query + ", extension=" + extension + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comparatorItem == null) ? 0 : comparatorItem.hashCode());
        result = prime * result + ((extension == null) ? 0 : extension.hashCode());
        result = prime * result + ((firstResult == null) ? 0 : firstResult.hashCode());
        result = prime * result + ((maxResults == null) ? 0 : maxResults.hashCode());
        result = prime * result + ((query == null) ? 0 : query.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageRequestDto other = (PageRequestDto) obj;
        if (comparatorItem == null) {
            if (other.comparatorItem != null)
                return false;
        } else if (!comparatorItem.equals(other.comparatorItem))
            return false;
        if (extension == null) {
            if (other.extension != null)
                return false;
        } else if (!extension.equals(other.extension))
            return false;
        if (firstResult == null) {
            if (other.firstResult != null)
                return false;
        } else if (!firstResult.equals(other.firstResult))
            return false;
        if (maxResults == null) {
            if (other.maxResults != null)
                return false;
        } else if (!maxResults.equals(other.maxResults))
            return false;
        if (query == null) {
            if (other.query != null)
                return false;
        } else if (!query.equals(other.query))
            return false;
        return true;
    }

    /**
     * Returns the first result (may be {@code null}).
     * 
     * @return the first result
     */
    public Integer getFirstResult() {
        return firstResult;
    }

    /**
     * Sets the first result.
     * 
     * @param firstResult
     *            the first result (may be {@code null})
     */
    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    /**
     * Returns the number of maximum results (may be {@code null}).
     * 
     * @return the number of maximum results
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * Sets the number of maximum results
     * 
     * @param maxResults
     *            the number of maximum results (may be {@code null})
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * Returns the comparator item (may be {@code null}).
     * 
     * @return the comparator item
     */
    public ComparatorItem getComparatorItem() {
        return comparatorItem;
    }

    /**
     * Sets the comparator item
     * 
     * @param comparatorItem
     *            the comparator item (may be {@code null})
     */
    public void setComparatorItem(ComparatorItem comparatorItem) {
        this.comparatorItem = comparatorItem;
    }

    /**
     * Returns the search query (may be {@code null}).
     * 
     * @return the search query
     */
    public String getQuery() {
        return query;
    }

    /**
     * Sets the search query
     * 
     * @param query
     *            the search query (may be {@code null})
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Returns a custom extension (may be {@code null}).
     * 
     * @return a custom extension
     */
    public Object getExtension() {
        return extension;
    }

    /**
     * Sets a custom extension.
     * 
     * @param extension
     *            a custom extension (may be {@code null})
     */
    public void setExtension(Object extension) {
        this.extension = extension;
    }

}
