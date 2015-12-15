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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 * A pagination can be used to display pagination links or buttons on a web site:
 * <pre>
 *  ----    ---    ---    ---     ===     ---    ---    ---    ----
 * | << |  | < |  | 1 |  | 2 |  || 3 ||  | 4 |  | 5 |  | > |  | >> | 
 *  ----    ---    ---    ---     ===     ---    ---    ---    ----
 * First   Prev.  Page   Page     Page   Page   Page   Next    Last 
 * Page    Page   No 0   No 1     No 2   No 3   No 4   Page    Page
 *                               active
 *                |                                 |
 *                 ------ maxPaginationLinks -------
 * </pre>
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "pagination")
@XmlType(name = "paginationType", propOrder = {
        "maxPaginationLinks",
        "firstPageLink",
        "previousPageLink",
        "links",
        "nextPageLink",
        "lastPageLink"
})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
@JsonAutoDetect(fieldVisibility = Visibility.NONE, 
    getterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC, 
    setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonPropertyOrder(value = {
        "maxPaginationLinks",
        "firstPageLink",
        "previousPageLink",
        "links",
        "nextPageLink",
        "lastPageLink"
})
//@formatter:on
public class PaginationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int maxPaginationLinks = 7;

    private PageRequestLinkDto firstPageLink;

    private PageRequestLinkDto previousPageLink;

    private List<PageRequestLinkDto> links = new ArrayList<PageRequestLinkDto>();

    private PageRequestLinkDto nextPageLink;

    private PageRequestLinkDto lastPageLink;

    /**
     * Default constructor.
     */
    public PaginationDto() {
    }

    /**
     * Returns the maximum number of pagination links.
     */
    @XmlElement(name = "maxPaginationLinks", defaultValue = "7")
    @JsonProperty(value = "maxPaginationLinks", required = true)
    public int getMaxPaginationLinks() {
        return maxPaginationLinks;
    }

    /**
     * Sets the maximum number of pagination links.
     */
    @JsonProperty(value = "maxPaginationLinks", required = false)
    public void setMaxPaginationLinks(int maxPaginationButtons) {
        if (maxPaginationButtons < 1) {
            maxPaginationButtons = 1;
        } else {
            this.maxPaginationLinks = maxPaginationButtons;
        }
    }

    /**
     * Returns the first pagination link.
     */
    @XmlElement(name = "firstPageLink", required = false)
    @JsonProperty(value = "firstPageLink", required = false)
    public PageRequestLinkDto getFirstPageLink() {
        return firstPageLink;
    }

    /**
     * Sets the first pagination link.
     */
    @JsonProperty(value = "firstPageLink", required = false)
    public void setFirstPageLink(PageRequestLinkDto firstPageLink) {
        this.firstPageLink = firstPageLink;
    }

    /**
     * Returns the previous pagination link.
     */
    @XmlElement(name = "previousPageLink", required = false)
    @JsonProperty(value = "previousPageLink", required = false)
    public PageRequestLinkDto getPreviousPageLink() {
        return previousPageLink;
    }

    /**
     * Sets the previous pagination link.
     */
    @JsonProperty(value = "previousPageLink", required = false)
    public void setPreviousPageLink(PageRequestLinkDto previousPageLink) {
        this.previousPageLink = previousPageLink;
    }

    /**
     * Returns the pagination links.
     */
    @XmlElementWrapper(name = "links", required = false)
    @XmlElement(name = "link")
    @JsonProperty(value = "links", required = false)
    public List<PageRequestLinkDto> getLinks() {
        return links;
    }

    /**
     * Sets the pagination links.
     */
    @JsonProperty(value = "links", required = false)
    public void setLinks(List<PageRequestLinkDto> links) {
        if (links == null) {
            links = new ArrayList<>();
        }
        this.links = links;
    }

    /**
     * Returns the next pagination link.
     */
    @XmlElement(name = "nextPageLink", required = false)
    @JsonProperty(value = "nextPageLink", required = false)
    public PageRequestLinkDto getNextPageLink() {
        return nextPageLink;
    }

    /**
     * Sets the next pagination link.
     */
    @JsonProperty(value = "nextPageLink", required = false)
    public void setNextPageLink(PageRequestLinkDto nextPageLink) {
        this.nextPageLink = nextPageLink;
    }

    /**
     * Returns the last pagination link.
     */
    @XmlElement(name = "lastPageLink", required = false)
    @JsonProperty(value = "lastPageLink", required = false)
    public PageRequestLinkDto getLastPageLink() {
        return lastPageLink;
    }

    /**
     * Sets the last pagination link.
     */
    @JsonProperty(value = "lastPageLink", required = false)
    public void setLastPageLink(PageRequestLinkDto lastPageLink) {
        this.lastPageLink = lastPageLink;
    }

}
