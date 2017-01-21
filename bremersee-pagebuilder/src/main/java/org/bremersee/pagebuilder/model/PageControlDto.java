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

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A {@link PageControlDto} can be used to display a {@link PageDto} on a web
 * site. <br/>
 * It contains a list of links to the other pages, a page size selector, the
 * pagination, a flag whether the business logic supports a query value or not,
 * the URL parameter names of the page request and a serialized comparator item
 * (as URL paremeter value).
 * </p>
 *
 * @author Christian Bremer
 */
//@formatter:off
@SuppressWarnings({"WeakerAccess", "unused"})
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
@ApiModel(
        value = "PageControl",
        description = "A page control can be used to display a page on a web site. "
                + "It contains a list of links to the other pages, "
                + "a page size selector, the pagination, "
                + "a flag whether the business logic supports a query value or not, "
                + "the URL parameter names of the page request "
                + "and a serialized comparator item (as URL paremeter value).")
//@formatter:on
public class PageControlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private PageDto page;

    private List<PageRequestLinkDto> pageRequestLinks = new ArrayList<>();

    private List<PageSizeSelectorOptionDto> pageSizeSelectorOptions = new ArrayList<>();

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
        super();
    }

    /**
     * Creates a page control with the given parameters.
     *
     * @param page                    the page
     * @param pagination              the pagination
     * @param pageNumberParamName     the parameter name of the page number
     * @param pageSizeParamName       the parameter name of the maximum results
     * @param pageSizeSelectorOptions the options for the maximum results selector
     * @param comparatorParamName     the parameter name of the comparator item
     * @param comparatorParamValue    the value of the comparator item
     * @param querySupported          {@code true} if a query field should be displayed otherwise
     *                                {@code false}
     * @param queryParamName          the parameter name of the query
     */
    public PageControlDto(final PageDto page, final PaginationDto pagination, // NOSONAR
                          final String pageNumberParamName, final String pageSizeParamName,
                          final List<PageSizeSelectorOptionDto> pageSizeSelectorOptions,
                          final String comparatorParamName, final String comparatorParamValue,
                          final boolean querySupported, final String queryParamName) {
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

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageControlDto [page=" + page + ", pageRequestLinks=" + pageRequestLinks + ", pageSizeSelectorOptions="
                + pageSizeSelectorOptions + ", pagination=" + pagination + ", querySupported=" + querySupported
                + ", queryParamName=" + queryParamName + ", pageNumberParamName=" + pageNumberParamName
                + ", pageSizeParamName=" + pageSizeParamName + ", comparatorParamName=" + comparatorParamName
                + ", comparatorParamValue=" + comparatorParamValue + "]";
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
        result = prime * result + ((page == null) ? 0 : page.hashCode());
        result = prime * result + ((pageNumberParamName == null) ? 0 : pageNumberParamName.hashCode());
        result = prime * result + ((pageRequestLinks == null) ? 0 : pageRequestLinks.hashCode());
        result = prime * result + ((pageSizeParamName == null) ? 0 : pageSizeParamName.hashCode());
        result = prime * result + ((pageSizeSelectorOptions == null) ? 0 : pageSizeSelectorOptions.hashCode());
        result = prime * result + ((pagination == null) ? 0 : pagination.hashCode());
        result = prime * result + ((queryParamName == null) ? 0 : queryParamName.hashCode());
        result = prime * result + (querySupported ? 1231 : 1237);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object obj) { // NOSONAR
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
        if (pageRequestLinks == null) {
            if (other.pageRequestLinks != null)
                return false;
        } else if (!pageRequestLinks.equals(other.pageRequestLinks))
            return false;
        if (pageSizeParamName == null) {
            if (other.pageSizeParamName != null)
                return false;
        } else if (!pageSizeParamName.equals(other.pageSizeParamName))
            return false;
        if (pageSizeSelectorOptions == null) {
            if (other.pageSizeSelectorOptions != null)
                return false;
        } else if (!pageSizeSelectorOptions.equals(other.pageSizeSelectorOptions))
            return false;
        if (pagination == null) {
            if (other.pagination != null)
                return false;
        } else if (!pagination.equals(other.pagination))
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

    /**
     * Returns the page.
     */
    @XmlElement(name = "page", required = true)
    @JsonProperty(value = "page", required = true)
    @ApiModelProperty(value = "The page.", required = true)
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

    @XmlElementWrapper(name = "pageRequestLinks")
    @XmlElement(name = "pageRequestLink")
    @JsonProperty(value = "pageRequestLinks")
    @ApiModelProperty(value = "The links to the other pages.", position = 1)
    public List<PageRequestLinkDto> getPageRequestLinks() {
        return pageRequestLinks;
    }

    @JsonProperty(value = "pageRequestLinks")
    public void setPageRequestLinks(List<PageRequestLinkDto> pageRequestLinks) {
        if (pageRequestLinks == null) {
            this.pageRequestLinks = new ArrayList<>();
        } else {
            this.pageRequestLinks = pageRequestLinks;
        }
    }

    /**
     * Returns the pagination.
     */
    @XmlElement(name = "pagination")
    @JsonProperty(value = "pagination")
    @ApiModelProperty(value = "The pagination information.", position = 3)
    public PaginationDto getPagination() {
        return pagination;
    }

    /**
     * Sets the pagination.
     */
    @JsonProperty(value = "pagination")
    public void setPagination(PaginationDto pagination) {
        this.pagination = pagination;
    }

    /**
     * Returns the parameter name of the page number.
     */
    @XmlElement(name = "pageNumberParamName", defaultValue = "p")
    @JsonProperty(value = "pageNumberParamName", required = true)
    @ApiModelProperty(value = "The parameter name of the page number.", position = 6, required = true, example = "p")
    public String getPageNumberParamName() {
        return pageNumberParamName;
    }

    /**
     * Sets the parameter name of page number.
     */
    @JsonProperty(value = "pageNumberParamName")
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
    @ApiModelProperty(value = "The parameter name of the page size.", position = 7, required = true, example = "s")
    public String getPageSizeParamName() {
        return pageSizeParamName;
    }

    /**
     * Sets the parameter name of the max results.
     */
    @JsonProperty(value = "pageSizeParamName")
    public void setPageSizeParamName(String pageSizeParamName) {
        if (StringUtils.isNotBlank(pageSizeParamName)) {
            this.pageSizeParamName = pageSizeParamName;
        }
    }

    /**
     * Returns a list with page size selector options.
     */
    @XmlElementWrapper(name = "pageSizeSelectorOptions")
    @XmlElement(name = "pageSizeSelectorOption")
    @JsonProperty(value = "pageSizeSelectorOptions")
    @ApiModelProperty(value = "A list with page size selector options.", position = 2)
    public List<PageSizeSelectorOptionDto> getPageSizeSelectorOptions() {
        return pageSizeSelectorOptions;
    }

    /**
     * Sets the list with maximum result options.
     */
    @JsonProperty(value = "pageSizeSelectorOptions")
    public void setPageSizeSelectorOptions(List<PageSizeSelectorOptionDto> pageSizeSelectorOptions) {
        if (pageSizeSelectorOptions == null) {
            this.pageSizeSelectorOptions = new ArrayList<>();
        } else {
            this.pageSizeSelectorOptions = pageSizeSelectorOptions;
        }
    }

    /**
     * Returns the parameter name of the comparator item.
     */
    @XmlElement(name = "comparatorParamName", defaultValue = "c")
    @JsonProperty(value = "comparatorParamName", required = true)
    @ApiModelProperty(value = "The parameter name of the comparator item.", position = 8, required = true, example = "c")
    public String getComparatorParamName() {
        return comparatorParamName;
    }

    /**
     * Sets the parameter name of the comparator item.
     */
    @JsonProperty(value = "comparatorParamName")
    public void setComparatorParamName(String comparatorParamName) {
        if (StringUtils.isNotBlank(comparatorParamName)) {
            this.comparatorParamName = comparatorParamName;
        }
    }

    /**
     * Returns the serialized value of the comparator item.
     */
    @XmlElement(name = "comparatorParamValue")
    @JsonProperty(value = "comparatorParamValue")
    @ApiModelProperty(value = "The serialized value of the comparator item.", position = 9)
    public String getComparatorParamValue() {
        return comparatorParamValue;
    }

    /**
     * Sets the serialized value of the comparator item.
     */
    @JsonProperty(value = "comparatorParamValue")
    public void setComparatorParamValue(String comparatorParamValue) {
        this.comparatorParamValue = comparatorParamValue;
    }

    /**
     * Returns {@code true} if a query field should be displayed, otherwise
     * {@code false}.
     */
    @XmlElement(name = "querySupported", defaultValue = "true")
    @JsonProperty(value = "querySupported", required = true)
    @ApiModelProperty(value = "Is a query value supported?", position = 4, required = true)
    public boolean isQuerySupported() {
        return querySupported;
    }

    /**
     * Specifies whether the query field should be displayed or not.
     */
    @JsonProperty(value = "querySupported")
    public void setQuerySupported(boolean querySupported) {
        this.querySupported = querySupported;
    }

    /**
     * Returns the parameter name of the query.
     */
    @XmlElement(name = "queryParamName", defaultValue = "q")
    @JsonProperty(value = "queryParamName", required = true)
    @ApiModelProperty(value = "The parameter name of the query value.", position = 5, required = true, example = "q")
    public String getQueryParamName() {
        return queryParamName;
    }

    /**
     * Sets the parameter name of the query.
     */
    @JsonProperty(value = "queryParamName")
    public void setQueryParamName(String queryParamName) {
        if (StringUtils.isNotBlank(queryParamName)) {
            this.queryParamName = queryParamName;
        }
    }

}
