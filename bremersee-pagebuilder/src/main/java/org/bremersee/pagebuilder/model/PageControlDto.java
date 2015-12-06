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
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * <p>
 * A {@link PageControl} can be used to display a {@link Page} on a web site.
 * <br/>
 * This page control can be processed by a {@link JAXBContext} and the Jackson
 * JSON processor.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlRootElement(name = "pageControl")
@XmlType(name = "pageControlType", propOrder = {
        "page",
        "pagination",
        "pageNumberParamName",
        "maxResultsParamName",
        "maxResultsSelectorOptions",
        "comparatorParamName",
        "comparatorParamValue",
        "querySupported",
        "queryParamName",
        "query"
})
@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({
    MaxResultsSelectorOptionDto.class,
    PaginationDto.class,
    PaginationButtonDto.class
})
@JsonAutoDetect(fieldVisibility = Visibility.ANY, 
    getterVisibility = Visibility.NONE, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.NONE, 
    setterVisibility = Visibility.NONE)
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder(value = {
        "page",
        "pagination",
        "pageNumberParamName",
        "maxResultsParamName",
        "maxResultsSelectorOptions",
        "comparatorParamName",
        "comparatorParamValue",
        "querySupported",
        "queryParamName",
        "query"
})
//@formatter:on
public class PageControlDto implements PageControl {

    /**
     * If the given page control is {@code null}, {@code null} will be returned.
     * <br/>
     * If the given Page control is an instance of {@code PageControlDto} , that
     * instance will be returned. Otherwise a new instance will be created.
     * 
     * @param pageControl
     *            a page control
     */
    public static PageControlDto toPageControlDto(PageControl pageControl) {
        if (pageControl == null) {
            return null;
        }
        if (pageControl instanceof PageControlDto) {
            return (PageControlDto) pageControl;
        }
        return new PageControlDto(pageControl);
    }

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "page", required = true, type = PageDto.class)
    @JsonProperty(value = "page", required = true)
    @JsonDeserialize(as = PageDto.class)
    private Page page;

    @XmlElement(name = "pagination", required = false, type = PaginationDto.class)
    @JsonProperty(value = "pagination", required = false)
    @JsonDeserialize(as = PaginationDto.class)
    private Pagination pagination;

    @XmlElement(name = "pageNumberParamName", defaultValue = "p")
    @JsonProperty(value = "pageNumberParamName", required = true)
    private String pageNumberParamName = "p";

    @XmlElement(name = "maxResultsParamName", defaultValue = "max")
    @JsonProperty(value = "maxResultsParamName", required = true)
    private String maxResultsParamName = "max";

    @XmlElementWrapper(name = "maxResultsSelectorOptions", required = false)
    @XmlElement(name = "maxResultsSelectorOption", type = MaxResultsSelectorOptionDto.class)
    @JsonProperty(value = "maxResultsSelectorOptions", required = false)
    @JsonDeserialize(contentAs = MaxResultsSelectorOptionDto.class)
    private List<MaxResultsSelectorOption> maxResultsSelectorOptions = new ArrayList<MaxResultsSelectorOption>();

    @XmlElement(name = "comparatorParamName", defaultValue = "c")
    @JsonProperty(value = "comparatorParamName", required = true)
    private String comparatorParamName = "c";

    @XmlElement(name = "comparatorParamValue", required = false)
    @JsonProperty(value = "comparatorParamValue", required = false)
    private String comparatorParamValue;

    @XmlElement(name = "querySupported", defaultValue = "true")
    @JsonProperty(value = "querySupported", required = true)
    private boolean querySupported = true;

    @XmlElement(name = "queryParamName", defaultValue = "q")
    @JsonProperty(value = "queryParamName", required = true)
    private String queryParamName = "q";

    @XmlElement(name = "query", required = false)
    @JsonProperty(value = "query", required = false)
    private String query;

    /**
     * Default constructor.
     */
    public PageControlDto() {
    }

    /**
     * Creates a page control with the given parameters.
     * 
     * @param page
     *            the page
     * @param pagination
     *            the pagination
     * @param pageNumberParamName
     *            the parameter name of the page number
     * @param maxResultsParamName
     *            the parameter name of the maximum results
     * @param maxResultsSelectorOptions
     *            the options for the maximum results selector
     * @param comparatorParamName
     *            the parameter name of the comparator item
     * @param comparatorParamValue
     *            the value of the comparator item
     * @param querySupported
     *            {@code true} if a query field should be displayed otherwise
     *            {@code false}
     * @param queryParamName
     *            the parameter name of the query
     * @param query
     *            the query value
     */
    public PageControlDto(Page page, Pagination pagination, String pageNumberParamName, String maxResultsParamName,
            List<MaxResultsSelectorOption> maxResultsSelectorOptions, String comparatorParamName,
            String comparatorParamValue, boolean querySupported, String queryParamName, String query) {
        super();
        this.page = page;
        this.pagination = pagination;
        this.pageNumberParamName = pageNumberParamName;
        this.maxResultsParamName = maxResultsParamName;
        setMaxResultsSelectorOptions(maxResultsSelectorOptions);
        this.comparatorParamName = comparatorParamName;
        this.comparatorParamValue = comparatorParamValue;
        this.querySupported = querySupported;
        this.queryParamName = queryParamName;
        this.query = query;
    }

    /**
     * Creates a page control from another page control.
     * 
     * @param pageControl
     *            the other page control
     */
    public PageControlDto(PageControl pageControl) {
        if (pageControl != null) {
            this.page = PageDto.toPageDto(pageControl.getPage());
            this.pagination = PaginationDto.toPaginationDto(pageControl.getPagination());
            this.pageNumberParamName = pageControl.getPageNumberParamName();
            this.maxResultsParamName = pageControl.getMaxResultsParamName();
            if (pageControl.getMaxResultsSelectorOptions() != null) {
                for (MaxResultsSelectorOption o : pageControl.getMaxResultsSelectorOptions()) {
                    if (o != null) {
                        getMaxResultsSelectorOptions()
                                .add(MaxResultsSelectorOptionDto.toMaxResultsSelectorOptionDto(o));
                    }
                }
            }
            this.comparatorParamName = pageControl.getComparatorParamName();
            this.comparatorParamValue = pageControl.getComparatorParamValue();
            this.querySupported = pageControl.isQuerySupported();
            this.queryParamName = pageControl.getQueryParamName();
            this.query = pageControl.getQuery();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageControlDto [page=" + page + ", pagination=" + pagination + ", pageNumberParamName="
                + pageNumberParamName + ", maxResultsParamName=" + maxResultsParamName + ", maxResultsSelectorOptions="
                + maxResultsSelectorOptions + ", comparatorParamName=" + comparatorParamName + ", comparatorParamValue="
                + comparatorParamValue + ", querySupported=" + querySupported + ", queryParamName=" + queryParamName
                + ", query=" + query + "]";
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
        result = prime * result + ((comparatorParamName == null) ? 0 : comparatorParamName.hashCode());
        result = prime * result + ((comparatorParamValue == null) ? 0 : comparatorParamValue.hashCode());
        result = prime * result + ((maxResultsParamName == null) ? 0 : maxResultsParamName.hashCode());
        result = prime * result + ((maxResultsSelectorOptions == null) ? 0 : maxResultsSelectorOptions.hashCode());
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + ((pageNumberParamName == null) ? 0 : pageNumberParamName.hashCode());
        result = prime * result + ((pagination == null) ? 0 : pagination.hashCode());
        result = prime * result + ((query == null) ? 0 : query.hashCode());
        result = prime * result + ((queryParamName == null) ? 0 : queryParamName.hashCode());
        result = prime * result + (querySupported ? 1231 : 1237);
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
        PageControlDto other = (PageControlDto) obj;
        if (comparatorParamName == null) {
            if (other.comparatorParamName != null)
                return false;
        } else if (!comparatorParamName.equals(other.comparatorParamName))
            return false;
        if (comparatorParamValue == null) {
            if (other.comparatorParamValue != null)
                return false;
        } else if (!comparatorParamValue.equals(other.comparatorParamValue))
            return false;
        if (maxResultsParamName == null) {
            if (other.maxResultsParamName != null)
                return false;
        } else if (!maxResultsParamName.equals(other.maxResultsParamName))
            return false;
        if (maxResultsSelectorOptions == null) {
            if (other.maxResultsSelectorOptions != null)
                return false;
        } else if (!maxResultsSelectorOptions.equals(other.maxResultsSelectorOptions))
            return false;
        if (page == null) {
            if (other.page != null)
                return false;
        } else if (!page.equals(other.page))
            return false;
        if (pageNumberParamName == null) {
            if (other.pageNumberParamName != null)
                return false;
        } else if (!pageNumberParamName.equals(other.pageNumberParamName))
            return false;
        if (pagination == null) {
            if (other.pagination != null)
                return false;
        } else if (!pagination.equals(other.pagination))
            return false;
        if (query == null) {
            if (other.query != null)
                return false;
        } else if (!query.equals(other.query))
            return false;
        if (queryParamName == null) {
            if (other.queryParamName != null)
                return false;
        } else if (!queryParamName.equals(other.queryParamName))
            return false;
        if (querySupported != other.querySupported)
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#getPage()
     */
    @Override
    public Page getPage() {
        return page;
    }

    /**
     * Sets the page.
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#getPagination()
     */
    @Override
    public Pagination getPagination() {
        return pagination;
    }

    /**
     * Sets the pagination.
     */
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#getPageNumberParamName()
     */
    @Override
    public String getPageNumberParamName() {
        return pageNumberParamName;
    }

    /**
     * Sets the parameter name of page number.
     */
    public void setPageNumberParamName(String pageNumberParamName) {
        if (StringUtils.isNotBlank(pageNumberParamName)) {
            this.pageNumberParamName = pageNumberParamName;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#getMaxResultsParamName()
     */
    @Override
    public String getMaxResultsParamName() {
        return maxResultsParamName;
    }

    /**
     * Sets the parameter name of the max results.
     */
    public void setMaxResultsParamName(String maxResultsParamName) {
        if (StringUtils.isNotBlank(maxResultsParamName)) {
            this.maxResultsParamName = maxResultsParamName;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bremersee.pagebuilder.model.PageControl#getMaxResultsSelectorOptions(
     * )
     */
    @Override
    public List<MaxResultsSelectorOption> getMaxResultsSelectorOptions() {
        return maxResultsSelectorOptions;
    }

    /**
     * Sets the list with maximum result options.
     */
    public void setMaxResultsSelectorOptions(List<MaxResultsSelectorOption> maxResultsSelectorOptions) {
        if (maxResultsSelectorOptions == null) {
            maxResultsSelectorOptions = new ArrayList<>();
        }
        this.maxResultsSelectorOptions = maxResultsSelectorOptions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#getComparatorParamName()
     */
    @Override
    public String getComparatorParamName() {
        return comparatorParamName;
    }

    /**
     * Sets the parameter name of the comparator item.
     */
    public void setComparatorParamName(String comparatorParamName) {
        if (StringUtils.isNotBlank(comparatorParamName)) {
            this.comparatorParamName = comparatorParamName;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bremersee.pagebuilder.model.PageControl#getComparatorParamValue()
     */
    @Override
    public String getComparatorParamValue() {
        return comparatorParamValue;
    }

    /**
     * Sets the serialized value of the comparator item.
     */
    public void setComparatorParamValue(String comparatorParamValue) {
        this.comparatorParamValue = comparatorParamValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#isQuerySupported()
     */
    @Override
    public boolean isQuerySupported() {
        return querySupported;
    }

    /**
     * Specifies whether the query field should be displayed or not.
     */
    public void setQuerySupported(boolean querySupported) {
        this.querySupported = querySupported;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#getQueryParamName()
     */
    @Override
    public String getQueryParamName() {
        return queryParamName;
    }

    /**
     * Sets the parameter name of the query.
     */
    public void setQueryParamName(String queryParamName) {
        if (StringUtils.isNotBlank(queryParamName)) {
            this.queryParamName = queryParamName;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PageControl#getQuery()
     */
    @Override
    public String getQuery() {
        return query;
    }

    /**
     * Sets the query value.
     */
    public void setQuery(String query) {
        this.query = query;
    }

}
