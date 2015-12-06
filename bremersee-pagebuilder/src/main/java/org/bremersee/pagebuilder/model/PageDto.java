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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.bremersee.comparator.model.ComparatorItem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * <p>
 * A page consist at least a list of items. Furthermore it may has information
 * about the size of all available items, the number of the first item of this
 * page, the maximum number of items and how they are sorted.
 * </p>
 * <p>
 * This page implementation can be processed by a {@link JAXBContext} and a
 * Jackson JSON Processor.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlRootElement(name = "page")
@XmlType(name = "pageType", propOrder = { 
		"totalSize", 
		"firstResult", 
		"maxResults", 
		"comparatorItem", 
		"entries",
		"pageSize",
		"currentPage",
		"previousFirstResult",
		"nextFirstResult"
})
@XmlAccessorType(XmlAccessType.PROPERTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, 
    getterVisibility = Visibility.PUBLIC_ONLY, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.PUBLIC_ONLY, 
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder(value = {
        "totalSize", 
        "firstResult", 
        "maxResults", 
        "comparatorItem", 
        "entries",
        "pageSize",
        "currentPage",
        "previousFirstResult",
        "nextFirstResult"
})
//@formatter:on
public class PageDto implements Page {

    /**
     * If the given page is {@code null}, {@code null} will be returned.<br/>
     * If the given page is an instance of {@code PageDto}, that instance will
     * be returned. Otherwise a new instance will be created.
     * 
     * @param page
     *            a page
     */
    public static PageDto toPageDto(Page page) {
        if (page == null) {
            return null;
        }
        if (page instanceof PageDto) {
            return (PageDto) page;
        }
        return new PageDto(page);
    }

    private static final long serialVersionUID = 1L;

    private Integer totalSize;

    private Integer firstResult;

    private Integer maxResults;

    private ComparatorItem comparatorItem;

    private List<Object> entries = new ArrayList<Object>();

    /**
     * Default constructor.
     */
    public PageDto() {
    }

    /**
     * Creates a page with the given parameters.
     * 
     * @param totalSize
     *            the size of all available entries
     * @param firstResult
     *            the first result number
     * @param maxResults
     *            the maximum number of results
     * @param comparatorItem
     *            the comparator item
     * @param entries
     *            the entries of this page
     */
    public PageDto(Integer totalSize, Integer firstResult, Integer maxResults, ComparatorItem comparatorItem,
            List<Object> entries) {
        super();
        this.totalSize = totalSize;
        this.firstResult = firstResult;
        this.maxResults = maxResults;
        this.comparatorItem = comparatorItem;
        setEntries(entries);
    }

    /**
     * Creates a page from another page.
     * 
     * @param page
     *            the other page
     */
    public PageDto(Page page) {
        if (page != null) {
            this.totalSize = page.getTotalSize();
            this.firstResult = page.getFirstResult();
            this.maxResults = page.getMaxResults();
            this.comparatorItem = page.getComparatorItem();
            setEntries(page.getEntries());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageDto [totalSize=" + totalSize + ", firstResult=" + firstResult + ", maxResults=" + maxResults
                + ", comparatorItem=" + comparatorItem + ", entries=" + entries + "]";
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((comparatorItem == null) ? 0 : comparatorItem.hashCode());
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        result = prime * result + ((firstResult == null) ? 0 : firstResult.hashCode());
        result = prime * result + ((maxResults == null) ? 0 : maxResults.hashCode());
        result = prime * result + ((totalSize == null) ? 0 : totalSize.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
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
        PageDto other = (PageDto) obj;
        if (comparatorItem == null) {
            if (other.comparatorItem != null)
                return false;
        } else if (!comparatorItem.equals(other.comparatorItem))
            return false;
        if (entries == null) {
            if (other.entries != null)
                return false;
        } else if (!entries.equals(other.entries))
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
        if (totalSize == null) {
            if (other.totalSize != null)
                return false;
        } else if (!totalSize.equals(other.totalSize))
            return false;
        return true;
    }

    /**
     * Return the size of all available entries.
     * 
     * @return the size of all available entries (may be {@code null})
     */
    @XmlElement(name = "totalSize", required = false)
    @JsonProperty(value = "totalSize", required = false)
    @Override
    public Integer getTotalSize() {
        return totalSize;
    }

    /**
     * Set the size of all available entries
     * 
     * @param totalSize
     *            the size of all available entries
     */
    @JsonProperty(value = "totalSize", required = false)
    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * Return the first result number.
     * 
     * @return the first result number
     */
    @Override
    @XmlElement(name = "firstResult", required = false)
    @JsonProperty(value = "firstResult", required = false)
    public Integer getFirstResult() {
        return firstResult;
    }

    /**
     * Set the first result number.
     * 
     * @param firstResult
     *            the first result number
     */
    @JsonProperty(value = "firstResult", required = false)
    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    /**
     * Return the maximum number of results.
     * 
     * @return the maximum number of results
     */
    @Override
    @XmlElement(name = "maxResults", required = false)
    @JsonProperty(value = "maxResults", required = false)
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * Set the maximum number of results
     * 
     * @param maxResults
     *            the maximum number of results
     */
    @JsonProperty(value = "maxResults", required = false)
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * Return the comparator item that is used for sorting.
     * 
     * @return the comparator item
     */
    @XmlElement(name = "comparatorItem", required = false)
    @JsonProperty(value = "comparatorItem", required = false)
    @Override
    public ComparatorItem getComparatorItem() {
        return comparatorItem;
    }

    /**
     * Set the comparator item that was used for sorting.
     * 
     * @param comparatorItem
     *            the comparator item
     */
    @JsonProperty(value = "comparatorItem", required = false)
    public void setComparatorItem(ComparatorItem comparatorItem) {
        this.comparatorItem = comparatorItem;
    }

    /**
     * Return the entries of this page.<br/>
     * If the serialized page was read with the Jackson JSON processor, each
     * entry will be a {@link LinkedHashMap}.
     * 
     * @return the entries of this page
     */
    @XmlElementWrapper(name = "entries", required = false)
    @XmlAnyElement(lax = true)
    @Override
    public List<Object> getEntries() {
        return entries;
    }

    /**
     * Set the entries of this page.
     * 
     * @param entries
     *            the entries of this page
     */
    public void setEntries(List<Object> entries) {
        if (entries == null) {
            entries = new ArrayList<Object>();
        }
        this.entries = entries;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getPageSize()
     */
    @XmlElement(name = "pageSize", required = false)
    @JsonProperty(value = "pageSize", required = false)
    @Override
    public Integer getPageSize() {
        return ModelUtils.getPageSize(this);
    }
    
    @JsonProperty(value = "pageSize", required = false)
    protected void setPageSize(Integer pageSize) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getCurrentPage()
     */
    @Override
    @XmlElement(name = "currentPage", required = false)
    @JsonProperty(value = "currentPage", required = false)
    public Integer getCurrentPage() {
        return ModelUtils.getCurrentPage(this);
    }
    
    @JsonProperty(value = "currentPage", required = false)
    protected void setCurrentPage(Integer currentPage) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getPreviousFirstResult()
     */
    @XmlElement(name = "previousFirstResult", required = false)
    @JsonProperty(value = "previousFirstResult", required = false)
    @Override
    public Integer getPreviousFirstResult() {
        return ModelUtils.getPreviousFirstResult(this);
    }
    
    @JsonProperty(value = "previousFirstResult", required = false)
    protected void setPreviousFirstResult(Integer previousFirstResult) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getNextFirstResult()
     */
    @XmlElement(name = "nextFirstResult", required = false)
    @JsonProperty(value = "nextFirstResult", required = false)
    @Override
    public Integer getNextFirstResult() {
        return ModelUtils.getNextFirstResult(this);
    }
    
    @JsonProperty(value = "nextFirstResult", required = false)
    protected void setNextFirstResult(Integer nextFirstResult) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getPaginationSize(int)
     */
    @Override
    public Integer getPaginationSize(int fieldSize) {
        return ModelUtils.getPaginationSize(this, fieldSize);
    }

}
