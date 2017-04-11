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

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A pagination can be used to display pagination links or buttons on a web site:
 * <pre>
 *  ----    ---    ---    ---     ===     ---    ---    ---    ----
 * | &lt;&lt; |  | &lt; |  | 1 |  | 2 |  || 3 ||  | 4 |  | 5 |  | &gt; |  | &gt;&gt; |
 *  ----    ---    ---    ---     ===     ---    ---    ---    ----
 * First   Prev.  Page   Page     Page   Page   Page   Next    Last
 * Page    Page   No 0   No 1     No 2   No 3   No 4   Page    Page
 *                               active
 *                |                                 |
 *                 ------ maxPaginationLinks -------
 * </pre>
 *
 * @author Christian Bremer
 */
//@formatter:off
@SuppressWarnings({"WeakerAccess", "unused"})
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
@ApiModel(
        value = "Pagination",
        description = "A pagination can be used to display pagination links or buttons on a web site.")
//@formatter:on
public class PaginationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int maxPaginationLinks = 7;

    private PageRequestLinkDto firstPageLink;

    private PageRequestLinkDto previousPageLink;

    private List<PageRequestLinkDto> links = new ArrayList<>();

    private PageRequestLinkDto nextPageLink;

    private PageRequestLinkDto lastPageLink;

    /**
     * Default constructor.
     */
    public PaginationDto() {
        super();
    }

    @Override
    public String toString() {
        return "PaginationDto [maxPaginationLinks=" + maxPaginationLinks + ", firstPageLink=" + firstPageLink
                + ", previousPageLink=" + previousPageLink + ", links=" + links + ", nextPageLink=" + nextPageLink
                + ", lastPageLink=" + lastPageLink + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((firstPageLink == null) ? 0 : firstPageLink.hashCode());
        result = prime * result + ((lastPageLink == null) ? 0 : lastPageLink.hashCode());
        result = prime * result + ((links == null) ? 0 : links.hashCode());
        result = prime * result + maxPaginationLinks;
        result = prime * result + ((nextPageLink == null) ? 0 : nextPageLink.hashCode());
        result = prime * result + ((previousPageLink == null) ? 0 : previousPageLink.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) { // NOSONAR
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PaginationDto other = (PaginationDto) obj;
        if (firstPageLink == null) {
            if (other.firstPageLink != null)
                return false;
        } else if (!firstPageLink.equals(other.firstPageLink))
            return false;
        if (lastPageLink == null) {
            if (other.lastPageLink != null)
                return false;
        } else if (!lastPageLink.equals(other.lastPageLink))
            return false;
        if (links == null) {
            if (other.links != null)
                return false;
        } else if (!links.equals(other.links))
            return false;
        if (maxPaginationLinks != other.maxPaginationLinks)
            return false;
        if (nextPageLink == null) {
            if (other.nextPageLink != null)
                return false;
        } else if (!nextPageLink.equals(other.nextPageLink))
            return false;
        if (previousPageLink == null) {
            if (other.previousPageLink != null)
                return false;
        } else if (!previousPageLink.equals(other.previousPageLink))
            return false;
        return true;
    }

    /**
     * Returns the maximum number of pagination links.
     *
     * @return the maximum number of pagination links
     */
    @XmlElement(name = "maxPaginationLinks", defaultValue = "7")
    @JsonProperty(value = "maxPaginationLinks", required = true)
    @ApiModelProperty(value = "The maximum number of pagination links.", required = true, example = "7")
    public int getMaxPaginationLinks() {
        return maxPaginationLinks;
    }

    /**
     * Sets the maximum number of pagination links.
     *
     * @param maxPaginationButtons the maximum number of pagination links
     */
    @JsonProperty(value = "maxPaginationLinks")
    public void setMaxPaginationLinks(int maxPaginationButtons) {
        if (maxPaginationButtons < 1) {
            this.maxPaginationLinks = 1;
        } else {
            this.maxPaginationLinks = maxPaginationButtons;
        }
    }

    /**
     * Returns the first pagination link.
     *
     * @return the first pagination link
     */
    @XmlElement(name = "firstPageLink")
    @JsonProperty(value = "firstPageLink")
    @ApiModelProperty(value = "The first pagination link.", position = 1)
    public PageRequestLinkDto getFirstPageLink() {
        return firstPageLink;
    }

    /**
     * Sets the first pagination link.
     *
     * @param firstPageLink the first pagination link
     */
    @JsonProperty(value = "firstPageLink")
    public void setFirstPageLink(PageRequestLinkDto firstPageLink) {
        this.firstPageLink = firstPageLink;
    }

    /**
     * Returns the previous pagination link.
     *
     * @return the previous pagination link
     */
    @XmlElement(name = "previousPageLink")
    @JsonProperty(value = "previousPageLink")
    @ApiModelProperty(value = "The previous pagination link.", position = 2)
    public PageRequestLinkDto getPreviousPageLink() {
        return previousPageLink;
    }

    /**
     * Sets the previous pagination link.
     *
     * @param previousPageLink the previous pagination link
     */
    @JsonProperty(value = "previousPageLink")
    public void setPreviousPageLink(PageRequestLinkDto previousPageLink) {
        this.previousPageLink = previousPageLink;
    }

    /**
     * Returns the pagination links.
     *
     * @return the pagination links
     */
    @XmlElementWrapper(name = "links")
    @XmlElement(name = "link")
    @JsonProperty(value = "links")
    @ApiModelProperty(value = "The pagination links.", position = 3)
    public List<PageRequestLinkDto> getLinks() {
        return links;
    }

    /**
     * Sets the pagination links.
     *
     * @param links the pagination links
     */
    @JsonProperty(value = "links")
    public void setLinks(List<PageRequestLinkDto> links) {
        if (links == null) {
            this.links = new ArrayList<>();
        } else {
            this.links = links;
        }
    }

    /**
     * Returns the next pagination link.
     *
     * @return the next pagination link
     */
    @XmlElement(name = "nextPageLink")
    @JsonProperty(value = "nextPageLink")
    @ApiModelProperty(value = "The next pagination link.", position = 4)
    public PageRequestLinkDto getNextPageLink() {
        return nextPageLink;
    }

    /**
     * Sets the next pagination link.
     *
     * @param nextPageLink the next pagination link
     */
    @JsonProperty(value = "nextPageLink")
    public void setNextPageLink(PageRequestLinkDto nextPageLink) {
        this.nextPageLink = nextPageLink;
    }

    /**
     * Returns the last pagination link.
     *
     * @return the last pagination link
     */
    @XmlElement(name = "lastPageLink")
    @JsonProperty(value = "lastPageLink")
    @ApiModelProperty(value = "The last pagination link.", position = 5)
    public PageRequestLinkDto getLastPageLink() {
        return lastPageLink;
    }

    /**
     * Sets the last pagination link.
     *
     * @param lastPageLink the last pagination link
     */
    @JsonProperty(value = "lastPageLink")
    public void setLastPageLink(PageRequestLinkDto lastPageLink) {
        this.lastPageLink = lastPageLink;
    }

}
