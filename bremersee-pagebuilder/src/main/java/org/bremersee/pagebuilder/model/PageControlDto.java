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
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * <p>
 * A {@link PageControl} can be used to display a {@link PageDto} on a web site.
 * <br/>
 * This page control can be processed by a {@link JAXBContext} and the Jackson
 * JSON processor.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "pageControl")
@XmlType(name = "pageControlType", propOrder = {
        "page",
        "pageRequestLinks",
        "pageSizeSelectorOptions",
        "pagination",
        "querySupported",
        "queryParamName",
        "pageNumberParamName",
        "pageSizeParamName",
        "comparatorParamName",
        "comparatorParamValue"
})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
@JsonAutoDetect(fieldVisibility = Visibility.NONE, 
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonPropertyOrder(value = {
        "page",
        "pageRequestLinks",
        "pageSizeSelectorOptions",
        "pagination",
        "querySupported",
        "queryParamName",
        "pageNumberParamName",
        "pageSizeParamName",
        "comparatorParamName",
        "comparatorParamValue"
})
//@formatter:on
public class PageControlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private PageDto page;
    
    private List<PageRequestLinkDto> pageRequestLinks = new ArrayList<PageRequestLinkDto>();

    private List<PageSizeSelectorOptionDto> pageSizeSelectorOptions = new ArrayList<PageSizeSelectorOptionDto>();

    private PaginationDto pagination;

    private boolean querySupported = true;

    private String queryParamName = "q";

    private String pageNumberParamName = "p";

    private String pageSizeParamName = "s";

    private String comparatorParamName = "c";

    private String comparatorParamValue;

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
     * @param pageSizeParamName
     *            the parameter name of the maximum results
     * @param pageSizeSelectorOptions
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
     */
    public PageControlDto(PageDto page, PaginationDto pagination, String pageNumberParamName, String pageSizeParamName,
            List<PageSizeSelectorOptionDto> pageSizeSelectorOptions, String comparatorParamName,
            String comparatorParamValue, boolean querySupported, String queryParamName) {
        super();
        this.page = page;
        this.pagination = pagination;
        this.pageNumberParamName = pageNumberParamName;
        this.pageSizeParamName = pageSizeParamName;
        setPageSizeSelectorOptions(pageSizeSelectorOptions);
        this.comparatorParamName = comparatorParamName;
        this.comparatorParamValue = comparatorParamValue;
        this.querySupported = querySupported;
        this.queryParamName = queryParamName;
    }

    /**
     * Returns the page.
     */
    @XmlElement(name = "page", required = true)
    @JsonProperty(value = "page", required = true)
    public PageDto getPage() {
        return page;
    }

    /**
     * Sets the page.
     */
    @JsonProperty(value = "page", required = true)
    public void setPage(PageDto page) {
        this.page = page;
    }

    @XmlElementWrapper(name = "pageRequestLinks", required = false)
    @XmlElement(name = "pageRequestLink")
    @JsonProperty(value = "pageRequestLinks", required = false)
    public List<PageRequestLinkDto> getPageRequestLinks() {
        return pageRequestLinks;
    }

    @JsonProperty(value = "pageRequestLinks", required = false)
    public void setPageRequestLinks(List<PageRequestLinkDto> pageRequestLinks) {
        if (pageRequestLinks == null) {
            pageRequestLinks = new ArrayList<PageRequestLinkDto>();
        }
        this.pageRequestLinks = pageRequestLinks;
    }

    /**
     * Returns the pagination.
     */
    @XmlElement(name = "pagination", required = false)
    @JsonProperty(value = "pagination", required = false)
    public PaginationDto getPagination() {
        return pagination;
    }

    /**
     * Sets the pagination.
     */
    @JsonProperty(value = "pagination", required = false)
    public void setPagination(PaginationDto pagination) {
        this.pagination = pagination;
    }

    /**
     * Returns the parameter name of the page number.
     */
    @XmlElement(name = "pageNumberParamName", defaultValue = "p")
    @JsonProperty(value = "pageNumberParamName", required = true)
    public String getPageNumberParamName() {
        return pageNumberParamName;
    }

    /**
     * Sets the parameter name of page number.
     */
    @JsonProperty(value = "pageNumberParamName", required = false)
    public void setPageNumberParamName(String pageNumberParamName) {
        if (StringUtils.isNotBlank(pageNumberParamName)) {
            this.pageNumberParamName = pageNumberParamName;
        }
    }

    /**
     * Returns the parameter name of the page size.
     */
    @XmlElement(name = "pageSizeParamName", defaultValue = "s")
    @JsonProperty(value = "pageSizeParamName", required = true)
    public String getPageSizeParamName() {
        return pageSizeParamName;
    }

    /**
     * Sets the parameter name of the max results.
     */
    @JsonProperty(value = "pageSizeParamName", required = false)
    public void setPageSizeParamName(String pageSizeParamName) {
        if (StringUtils.isNotBlank(pageSizeParamName)) {
            this.pageSizeParamName = pageSizeParamName;
        }
    }

    /**
     * Returns a list with page size selector options.
     */
    @XmlElementWrapper(name = "pageSizeSelectorOptions", required = false)
    @XmlElement(name = "pageSizeSelectorOption")
    @JsonProperty(value = "pageSizeSelectorOptions", required = false)
    public List<PageSizeSelectorOptionDto> getPageSizeSelectorOptions() {
        return pageSizeSelectorOptions;
    }

    /**
     * Sets the list with maximum result options.
     */
    @JsonProperty(value = "pageSizeSelectorOptions", required = false)
    public void setPageSizeSelectorOptions(List<PageSizeSelectorOptionDto> pageSizeSelectorOptions) {
        if (pageSizeSelectorOptions == null) {
            pageSizeSelectorOptions = new ArrayList<>();
        }
        this.pageSizeSelectorOptions = pageSizeSelectorOptions;
    }

    /**
     * Returns the parameter name of the comparator item.
     */
    @XmlElement(name = "comparatorParamName", defaultValue = "c")
    @JsonProperty(value = "comparatorParamName", required = true)
    public String getComparatorParamName() {
        return comparatorParamName;
    }

    /**
     * Sets the parameter name of the comparator item.
     */
    @JsonProperty(value = "comparatorParamName", required = false)
    public void setComparatorParamName(String comparatorParamName) {
        if (StringUtils.isNotBlank(comparatorParamName)) {
            this.comparatorParamName = comparatorParamName;
        }
    }

    /**
     * Returns the serialized value of the comparator item.
     */
    @XmlElement(name = "comparatorParamValue", required = false)
    @JsonProperty(value = "comparatorParamValue", required = false)
    public String getComparatorParamValue() {
        return comparatorParamValue;
    }

    /**
     * Sets the serialized value of the comparator item.
     */
    @JsonProperty(value = "comparatorParamValue", required = false)
    public void setComparatorParamValue(String comparatorParamValue) {
        this.comparatorParamValue = comparatorParamValue;
    }

    /**
     * Returns {@code true} if a query field should be displayed, otherwise
     * {@code false}.
     */
    @XmlElement(name = "querySupported", defaultValue = "true")
    @JsonProperty(value = "querySupported", required = true)
    public boolean isQuerySupported() {
        return querySupported;
    }

    /**
     * Specifies whether the query field should be displayed or not.
     */
    @JsonProperty(value = "querySupported", required = false)
    public void setQuerySupported(boolean querySupported) {
        this.querySupported = querySupported;
    }

    /**
     * Returns the parameter name of the query.
     */
    @XmlElement(name = "queryParamName", defaultValue = "q")
    @JsonProperty(value = "queryParamName", required = true)
    public String getQueryParamName() {
        return queryParamName;
    }

    /**
     * Sets the parameter name of the query.
     */
    @JsonProperty(value = "queryParamName", required = false)
    public void setQueryParamName(String queryParamName) {
        if (StringUtils.isNotBlank(queryParamName)) {
            this.queryParamName = queryParamName;
        }
    }

}
