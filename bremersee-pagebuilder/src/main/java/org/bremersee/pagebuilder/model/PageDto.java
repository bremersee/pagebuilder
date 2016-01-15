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
public class PageDto implements Page<Object> {

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

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageDto [entries=" + entries + ", pageRequest=" + pageRequest + ", totalSize=" + totalSize + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        result = prime * result + ((pageRequest == null) ? 0 : pageRequest.hashCode());
        result = prime * result + (int) (totalSize ^ (totalSize >>> 32));
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
        PageDto other = (PageDto) obj;
        if (entries == null) {
            if (other.entries != null)
                return false;
        } else if (!entries.equals(other.entries))
            return false;
        if (pageRequest == null) {
            if (other.pageRequest != null)
                return false;
        } else if (!pageRequest.equals(other.pageRequest))
            return false;
        if (totalSize != other.totalSize)
            return false;
        return true;
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
     * Sets the size of all available elements.
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
    
}
