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

import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.Validate;
import org.bremersee.comparator.model.ComparatorItem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * A page request defines which page is requested (by it's page number), how
 * many elements the page may have and how the elements are sorted. Furthermore
 * a query value can be specified and custom extensions, which may be handled by
 * the business logic.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "pageRequest")
@XmlType(name = "pageRequestType", propOrder = { 
		"pageNumber", 
		"pageSize",
		"firstResult", 
		"comparatorItem", 
		"query", 
		"extensions" 
})
@XmlSeeAlso({
    PageRequestLinkDto.class
})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
@JsonAutoDetect(fieldVisibility = Visibility.NONE, 
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonPropertyOrder(value = {
        "pageNumber",
        "pageSize", 
        "firstResult", 
        "comparatorItem", 
        "query", 
        "extensions" 
})
@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes({ 
    @Type(value = PageRequestDto.class, name = "PageRequestDto"),
    @Type(value = PageRequestLinkDto.class, name = "PageRequestLinkDto") 
})
@ApiModel(
        value = "PageRequest", 
        description = "A page request defines which page is requested (by it's page number), "
                + "how many elements the page may have, and how the elements are sorted. "
                + "Furthermore a query value can be specified "
                + "and custom extensions, which may be handled by the business logic.",
        discriminator = "type",
        subTypes = { PageRequestLinkDto.class }
)
//@formatter:on
public class PageRequestDto implements PageRequest, Comparable<PageRequest> {

    private static final long serialVersionUID = 1L;

    private int pageNumber = 0;

    private int pageSize = Integer.MAX_VALUE;

    private ComparatorItem comparatorItem;

    private String query;

    private Map<String, Object> extensions = new LinkedHashMap<>();

    /**
     * Default constructor.
     */
    public PageRequestDto() {
    }

    /**
     * Creates a page request by another page request.
     * @param pageRequest the other page request (may by {@code null})
     */
    public PageRequestDto(PageRequest pageRequest) {
        if (pageRequest != null) {
            setComparatorItem(pageRequest.getComparatorItem());
            setExtensions(pageRequest.getExtensions());
            // setFirstResult(pageRequest.getFirstResult());
            setPageNumber(pageRequest.getPageNumber());
            setPageSize(pageRequest.getPageSize());
            setExtensions(pageRequest.getExtensions());
        }
    }

    /**
     * Creates a page request by the page number and size.
     * @param pageNumber the page number
     * @param pageSize the page size
     */
    public PageRequestDto(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, null, null, null);
    }

    /**
     * Creates a page request by the page number, size and comparator item.
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param comparatorItem the comparator item (may be {@code null})
     */
    public PageRequestDto(int pageNumber, int pageSize, ComparatorItem comparatorItem) {
        this(pageNumber, pageSize, comparatorItem, null, null);
    }

    /**
     * Creates a page request by the page number, size, comparator item and a query value.
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param comparatorItem the comparator item (may be {@code null})
     * @param query the query value
     */
    public PageRequestDto(int pageNumber, int pageSize, ComparatorItem comparatorItem, String query) {
        this(pageNumber, pageSize, comparatorItem, query, null);
    }

    /**
     * Creates a page request by the page number, size, comparator item, a query value and custom extensions.
     * @param pageNumber the page number
     * @param pageSize the page size
     * @param comparatorItem the comparator item (may be {@code null})
     * @param query the query value
     * @param extensions the custom extensions
     */
    public PageRequestDto(int pageNumber, int pageSize, ComparatorItem comparatorItem, String query,
            Map<String, Object> extensions) {
        super();
        setPageNumber(pageNumber);
        setPageSize(pageSize);
        setComparatorItem(comparatorItem);
        setQuery(query);
        setExtensions(extensions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageRequestDto [pageNumber=" + pageNumber + ", pageSize=" + pageSize + ", comparatorItem="
                + comparatorItem + ", query=" + query + ", extensions=" + extensions + "]";
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
        result = prime * result + ((extensions == null) ? 0 : extensions.hashCode());
        result = prime * result + pageNumber;
        result = prime * result + pageSize;
        result = prime * result + ((query == null) ? 0 : query.hashCode());
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
        if (!(obj instanceof PageRequest))
            return false;
        PageRequest other = (PageRequest) obj;
        if (getComparatorItem() == null) {
            if (other.getComparatorItem() != null)
                return false;
        } else if (!getComparatorItem().equals(other.getComparatorItem()))
            return false;
        if (getExtensions() == null) {
            if (other.getExtensions() != null)
                return false;
        } else if (!getExtensions().equals(other.getExtensions()))
            return false;
        if (getPageNumber() != other.getPageNumber())
            return false;
        if (getPageSize() != other.getPageSize())
            return false;
        if (getQuery() == null) {
            if (other.getQuery() != null)
                return false;
        } else if (!getQuery().equals(other.getQuery()))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(PageRequest pageRequest) {
        if (pageRequest == null) {
            return -1;
        }
        return getPageNumber() - pageRequest.getPageNumber();
    }

    /**
     * Returns the requested page number (starting with 0).
     */
    @XmlElement(name = "pageNumber", required = true)
    @JsonProperty(value = "pageNumber", required = true)
    @ApiModelProperty(value = "The page number.", position = 0, required = true)
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the requested page number (starting with 0).
     */
    @JsonProperty(value = "pageNumber", required = true)
    public void setPageNumber(int pageNumber) {
        Validate.isTrue(pageNumber >= 0, "Page number must be 0 or greater than 0.");
        this.pageNumber = pageNumber;
    }

    /**
     * Returns the maximum number of elements on the page (is always greater
     * than 0).
     */
    @XmlElement(name = "pageSize", required = true)
    @JsonProperty(value = "pageSize", required = true)
    @ApiModelProperty(value = "The maximum number of elements on the page.", position = 1, required = true)
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Sets the maximum number of elements on the page (must be greater than 0).
     */
    @JsonProperty(value = "pageSize", required = true)
    public void setPageSize(int pageSize) {
        Validate.isTrue(pageSize >= 1, "Page number must be 1 or greater than 1.");
        this.pageSize = pageSize;
    }

    /**
     * Returns the first result (offset).
     */
    @XmlElement(name = "firstResult", required = false)
    @JsonProperty(value = "firstResult", required = false)
    @ApiModelProperty(value = "The first result (offset).", position = 2, required = false, readOnly = true)
    public int getFirstResult() {
        // make sure that first result (= offset) is not bigger than
        // Integer.MAX_VALUE
        long firstResult = (long) getPageNumber() * (long) getPageSize();
        if (firstResult < (long) Integer.MAX_VALUE) {
            return Long.valueOf(firstResult).intValue();
        }
        return Integer.MAX_VALUE;
    }

    /**
     * Sets the first result (offset).
     */
    @JsonProperty(value = "firstResult", required = false)
    protected void setFirstResult(Integer firstResult) {
    }

    /**
     * Returns the comparator item (may be {@code null}).
     */
    @XmlElement(name = "comparatorItem", required = false)
    @JsonProperty(value = "comparatorItem", required = false)
    @ApiModelProperty(value = "The comparator item.", position = 3, required = false)
    public ComparatorItem getComparatorItem() {
        return comparatorItem;
    }

    /**
     * Sets the comparator item (may be {@code null}).
     */
    @JsonProperty(value = "comparatorItem", required = false)
    public void setComparatorItem(ComparatorItem comparatorItem) {
        this.comparatorItem = comparatorItem;
    }

    /**
     * Returns the search query (may be {@code null}).
     */
    @XmlElement(name = "query", required = false)
    @JsonProperty(value = "query", required = false)
    @ApiModelProperty(value = "A query value.", position = 4, required = false)
    public String getQuery() {
        return query;
    }

    /**
     * Sets the search query (may be {@code null}).
     */
    @JsonProperty(value = "query", required = false)
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * Returns a custom extension (may be {@code null}).
     */
    @JsonProperty(value = "extensions", required = false)
    @ApiModelProperty(value = "Custom extensions.", position = 4, required = false)
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    /**
     * Sets a custom extension (may be {@code null}).
     */
    @JsonProperty(value = "extensions", required = false)
    public void setExtensions(Map<String, Object> extensions) {
        if (extensions == null) {
            this.extensions.clear();
        } else if (extensions instanceof LinkedHashMap) {
            this.extensions = extensions;
        } else {
            this.extensions.clear();
            ;
            this.extensions.putAll(extensions);

        }
    }

}
