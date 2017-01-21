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
import org.bremersee.comparator.model.ComparatorItem;

import javax.xml.bind.annotation.*;
import java.util.Map;

/**
 * <p>
 * A page request link.
 * </p>
 *
 * @author Christian Bremer
 */
//@formatter:off
@SuppressWarnings({"WeakerAccess", "unused"})
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "pageRequestLink")
@XmlType(name = "pageRequestLinkType", propOrder = {
        "displayedPageNumber",
        "active",
        "url"})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.PROTECTED_AND_PUBLIC,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.PROTECTED_AND_PUBLIC,
        setterVisibility = Visibility.PROTECTED_AND_PUBLIC)
@JsonPropertyOrder(value = {
        "displayedPageNumber",
        "active",
        "url"
})
@ApiModel(
        value = "PageRequestLink",
        description = "A page request link contains beside the page request information "
                + "a link (to another page) and a flag whether this page request is the "
                + "current one.",
        parent = PageRequestDto.class
)
//@formatter:on
public class PageRequestLinkDto extends PageRequestDto {

    private static final long serialVersionUID = 1L;

    private boolean active;

    private String url;

    /**
     * Default constructor.
     */
    public PageRequestLinkDto() {
        super();
    }

    public PageRequestLinkDto(PageRequestDto pageRequest, boolean active, String url) {
        if (pageRequest != null) {
            setComparatorItem(pageRequest.getComparatorItem());
            setExtensions(pageRequest.getExtensions());
            setPageNumber(pageRequest.getPageNumber());
            setPageSize(pageRequest.getPageSize());
            setQuery(pageRequest.getQuery());
        }
        setActive(active);
        setUrl(url);
    }

    public PageRequestLinkDto(int pageNumber, int pageSize, ComparatorItem comparatorItem, String query,
                              Map<String, Object> extensions, boolean active, String url) {
        super(pageNumber, pageSize, comparatorItem, query, extensions);
        this.active = active;
        this.url = url;
    }

    @Override
    public String toString() {
        return "PageRequestLinkDto [active=" + active + ", url=" + url + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + ((url == null) ? 0 : url.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageRequestLinkDto other = (PageRequestLinkDto) obj;
        if (active != other.active)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    @XmlElement(name = "displayedPageNumber", required = true)
    @JsonProperty(value = "displayedPageNumber", required = true)
    @ApiModelProperty(value = "The page number for displaying (page number + 1).", required = true, readOnly = true)
    public String getDisplayedPageNumber() {
        return Integer.valueOf(getPageNumber() + 1).toString(); // NOSONAR
    }

    @JsonProperty(value = "displayedPageNumber")
    protected void setDisplayedPageNumber(String displayedPageNumber) { // NOSONAR
    }

    @XmlElement(name = "active", required = true)
    @JsonProperty(value = "active", required = true)
    @ApiModelProperty(value = "Is this page the current one?", position = 1, required = true)
    public boolean isActive() {
        return active;
    }

    /**
     * Specifies whether this link is active or not.
     */
    @JsonProperty(value = "active", required = true)
    public void setActive(boolean active) {
        this.active = active;
    }

    @XmlElement(name = "url")
    @JsonProperty(value = "url")
    @ApiModelProperty(value = "An URL to another page.", position = 2)
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL.
     */
    @JsonProperty(value = "url")
    public void setUrl(String url) {
        this.url = url;
    }

}
