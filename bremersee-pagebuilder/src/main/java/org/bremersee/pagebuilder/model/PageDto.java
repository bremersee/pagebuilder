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
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * <p>
 * A page is a sublist of a list of elements.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "page")
@XmlType(name = "pageType", propOrder = { 
        "entries",
        "pageRequest",
		"totalSize", 
		"totalPages"
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
@JsonAutoDetect(fieldVisibility = Visibility.NONE, 
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonPropertyOrder(value = {
        "entries",
        "pageRequestDto",
        "totalSize",
        "totalPages"
})
//@formatter:on
public class PageDto implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private List<Object> entries = new ArrayList<Object>();

    private PageRequestDto pageRequest;
    
    private long totalSize;

    /**
     * Default constructor.
     */
    public PageDto() {
    }

    public PageDto(Collection<? extends Object> entries) {
        this(entries, null, 0L);
    }

    public PageDto(Collection<? extends Object> entries, long totalSize) {
        this(entries, null, totalSize);
    }

    public PageDto(Collection<? extends Object> entries, PageRequestDto pageRequest, long totalSize) {
        super();
        if (entries != null) {
            this.entries.addAll(entries);
        }
        setPageRequest(pageRequest);
        setTotalSize(totalSize);
    }

    /**
     * Returns the elements of the page.<br/>
     * If the serialized page was read with the Jackson JSON processor, each
     * element will be a {@link LinkedHashMap}.
     * 
     * @return the elements of the page
     */
    @XmlElementWrapper(name = "entries", required = false)
    @XmlAnyElement(lax = true)
    public List<Object> getEntries() {
        return entries;
    }

    /**
     * Sets the elements of the page.
     */
    public void setEntries(List<Object> entries) {
        if (entries == null) {
            entries = new ArrayList<Object>();
        }
        this.entries = entries;
    }
    
    /**
     * Returns the page request.
     */
    @XmlElement(name = "pageRequest", required = true)
    @JsonProperty(value = "pageRequest", required = true)
    public PageRequestDto getPageRequest() {
        if (pageRequest == null) {
            pageRequest = new PageRequestDto();
        }
        return pageRequest;
    }

    /**
     * Sets the page request.
     */
    @JsonProperty(value = "pageRequest", required = false)
    public void setPageRequest(PageRequestDto pageRequest) {
        this.pageRequest = pageRequest;
    }

    /**
     * Returns the size of all available elements.
     */
    @XmlElement(name = "totalSize", required = true)
    @JsonProperty(value = "totalSize", required = true)
    public long getTotalSize() {
        return totalSize;
    }

    /**
     * Set the size of all available elements.
     */
    @JsonProperty(value = "totalSize", required = false)
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * Returns the number of all pages.
     */
    @XmlElement(name = "totalPages", required = true)
    @JsonProperty(value = "totalPages", required = true)
    public int getTotalPages() {
        if (getTotalSize() <= 0L) {
            return 1;
        }
        return (int) Math.ceil((double) getTotalSize() / (double) getPageRequest().getPageSize());
    }
    
    /**
     * Sets the number of all pages.
     */
    @JsonProperty(value = "totalPages", required = false)
    protected void setTotalPages(int totalPages) {
    }
    
//    @XmlElement(name = "isFirstPage", required = true)
//    @JsonProperty(value = "isFirstPage", required = true)
//    @Override
//    public boolean isFirstPage() {
//        return getPageRequest().getPageNumber() == 0;
//    }
//
//    @JsonProperty(value = "isFirstPage", required = false)
//    protected void setFirstPage(boolean isFirstPage) {
//    }
//
//    @XmlTransient
//    @JsonIgnore
//    @Override
//    public PageRequest getFirstPageRequest() {
//        //@formatter:off
//        if (isFirstPage()) {
//            return getPageRequest();
//        }
//        return new PageRequestDto(
//                0, 
//                getPageRequest().getPageSize(), 
//                getPageRequest().getComparatorItem(), 
//                getPageRequest().getQuery(), 
//                getPageRequest().getExtension());
//        //@formatter:on
//    }
//    
//    @XmlElement(name = "firstPageRequest", required = true)
//    @JsonProperty(value = "firstPageRequest", required = true)
//    protected PageRequestDto getFirstPageRequestDto() {
//        return PageRequestDto.toPageRequestDto(getFirstPageRequest());
//        
//    }
//    
//    @JsonProperty(value = "firstPageRequest", required = false)
//    protected void setFirstPageRequestDto(PageRequestDto pageRequestDto) {
//    }
//    
//    @XmlElement(name = "isPreviousPageRequestAvailable", required = true)
//    @JsonProperty(value = "isPreviousPageRequestAvailable", required = true)
//    @Override
//    public boolean isPreviousPageRequestAvailable() {
//        return getPageRequest().getPageNumber() > 0;
//    }
//    
//    @JsonProperty(value = "isPreviousPageRequestAvailable", required = false)
//    protected void setPreviousPageRequestAvailable(boolean isPreviousPageRequestAvailable) {
//    }
//    
//    @XmlTransient
//    @JsonIgnore
//    @Override
//    public PageRequest getPreviousPageRequest() {
//        //@formatter:off
//        if (!isPreviousPageRequestAvailable()) {
//            return getPageRequest();
//        }
//        return new PageRequestDto(
//                getPageRequest().getPageNumber() - 1, 
//                getPageRequest().getPageSize(), 
//                getPageRequest().getComparatorItem(), 
//                getPageRequest().getQuery(), 
//                getPageRequest().getExtension());
//        //@formatter:on
//    }
//    
//    @XmlElement(name = "previousPageRequest", required = true)
//    @JsonProperty(value = "previousPageRequest", required = true)
//    protected PageRequestDto getPreviousPageRequestDto() {
//        return PageRequestDto.toPageRequestDto(getPreviousPageRequest());
//    }
//    
//    @JsonProperty(value = "previousPageRequest", required = false)
//    protected void setPreviousPageRequestDto(PageRequestDto pageRequestDto) {
//    }
//    
//    @XmlElement(name = "isNextPageRequestAvailable", required = true)
//    @JsonProperty(value = "isNextPageRequestAvailable", required = true)
//    @Override
//    public boolean isNextPageRequestAvailable() {
//        return getPageRequest().getPageNumber() + 1 < getTotalPages();
//    }
//    
//    @JsonProperty(value = "isNextPageRequestAvailable", required = false)
//    protected void setNextPageRequestAvailable(boolean isNextPageRequestAvailable) {
//    }
//    
//    @XmlTransient
//    @JsonIgnore
//    @Override
//    public PageRequest getNextPageRequest() {
//        //@formatter:off
//        if (!isNextPageRequestAvailable()) {
//            return getPageRequest();
//        }
//        return new PageRequestDto(
//                getPageRequest().getPageNumber() + 1, 
//                getPageRequest().getPageSize(), 
//                getPageRequest().getComparatorItem(), 
//                getPageRequest().getQuery(), 
//                getPageRequest().getExtension());
//        //@formatter:on
//    }
//    
//    @XmlElement(name = "nextPageRequest", required = true)
//    @JsonProperty(value = "nextPageRequest", required = true)
//    protected PageRequestDto getNextPageRequestDto() {
//        return PageRequestDto.toPageRequestDto(getNextPageRequest());
//    }
//    
//    @JsonProperty(value = "nextPageRequest", required = false)
//    protected void setNextPageRequestDto(PageRequestDto pageRequestDto) {
//    }
//    
//    @XmlElement(name = "isLastPage", required = true)
//    @JsonProperty(value = "isLastPage", required = true)
//    @Override
//    public boolean isLastPage() {
//        return getPageRequest().getPageNumber() + 1 == getTotalPages();
//    }
//    
//    @JsonProperty(value = "isLastPage", required = false)
//    protected void setLastPage(boolean isLastPage) {
//    }
//    
//    @XmlTransient
//    @JsonIgnore
//    @Override
//    public PageRequest getLastPageRequest() {
//        //@formatter:off
//        if (isLastPage()) {
//            return getPageRequest();
//        }
//        return new PageRequestDto(
//                getTotalPages() - 1, 
//                getPageRequest().getPageSize(), 
//                getPageRequest().getComparatorItem(), 
//                getPageRequest().getQuery(), 
//                getPageRequest().getExtension());
//        //@formatter:on
//    }
//    
//    @XmlElement(name = "lastPageRequest", required = true)
//    @JsonProperty(value = "lastPageRequest", required = true)
//    protected PageRequestDto getLastPageRequestDto() {
//        return PageRequestDto.toPageRequestDto(getLastPageRequest());
//    }
//    
//    @JsonProperty(value = "lastPageRequest", required = false)
//    protected void setLastPageRequestDto(PageRequestDto pageRequestDto) {
//    }
    
    
    
    
    
    
//    /**
//     * Return the first result number.
//     * 
//     * @return the first result number
//     */
//    @Override
//    @XmlElement(name = "firstResult", required = false)
//    @JsonProperty(value = "firstResult", required = false)
//    public Integer getFirstResult() {
//        return firstResult;
//    }
//
//    /**
//     * Set the first result number.
//     * 
//     * @param firstResult
//     *            the first result number
//     */
//    @JsonProperty(value = "firstResult", required = false)
//    public void setFirstResult(Integer firstResult) {
//        this.firstResult = firstResult;
//    }
//
//    /**
//     * Return the maximum number of results.
//     * 
//     * @return the maximum number of results
//     */
//    @Override
//    @XmlElement(name = "maxResults", required = false)
//    @JsonProperty(value = "maxResults", required = false)
//    public Integer getMaxResults() {
//        return maxResults;
//    }
//
//    /**
//     * Set the maximum number of results
//     * 
//     * @param maxResults
//     *            the maximum number of results
//     */
//    @JsonProperty(value = "maxResults", required = false)
//    public void setMaxResults(Integer maxResults) {
//        this.maxResults = maxResults;
//    }
//
//    /**
//     * Return the comparator item that is used for sorting.
//     * 
//     * @return the comparator item
//     */
//    @XmlElement(name = "comparatorItem", required = false)
//    @JsonProperty(value = "comparatorItem", required = false)
//    @Override
//    public ComparatorItem getComparatorItem() {
//        return comparatorItem;
//    }
//
//    /**
//     * Set the comparator item that was used for sorting.
//     * 
//     * @param comparatorItem
//     *            the comparator item
//     */
//    @JsonProperty(value = "comparatorItem", required = false)
//    public void setComparatorItem(ComparatorItem comparatorItem) {
//        this.comparatorItem = comparatorItem;
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.bremersee.pagebuilder.model.Page#getCurrentPage()
//     */
//    @Override
//    @XmlElement(name = "currentPage", required = false)
//    @JsonProperty(value = "currentPage", required = false)
//    public Integer getCurrentPage() {
//        return ModelUtils.getCurrentPage(this);
//    }
//    
//    @JsonProperty(value = "currentPage", required = false)
//    protected void setCurrentPage(Integer currentPage) {
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.bremersee.pagebuilder.model.Page#getPreviousFirstResult()
//     */
//    @XmlElement(name = "previousFirstResult", required = false)
//    @JsonProperty(value = "previousFirstResult", required = false)
//    @Override
//    public Integer getPreviousFirstResult() {
//        return ModelUtils.getPreviousFirstResult(this);
//    }
//    
//    @JsonProperty(value = "previousFirstResult", required = false)
//    protected void setPreviousFirstResult(Integer previousFirstResult) {
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.bremersee.pagebuilder.model.Page#getNextFirstResult()
//     */
//    @XmlElement(name = "nextFirstResult", required = false)
//    @JsonProperty(value = "nextFirstResult", required = false)
//    @Override
//    public Integer getNextFirstResult() {
//        return ModelUtils.getNextFirstResult(this);
//    }
//    
//    @JsonProperty(value = "nextFirstResult", required = false)
//    protected void setNextFirstResult(Integer nextFirstResult) {
//    }
//
//    /*
//     * (non-Javadoc)
//     * 
//     * @see org.bremersee.pagebuilder.model.Page#getPaginationSize(int)
//     */
//    @Override
//    public Integer getPaginationSize(int fieldSize) {
//        return ModelUtils.getPaginationSize(this, fieldSize);
//    }

}
