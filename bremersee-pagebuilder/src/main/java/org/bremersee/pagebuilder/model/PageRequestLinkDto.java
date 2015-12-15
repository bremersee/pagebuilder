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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.bremersee.comparator.model.ComparatorItem;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * <p>
 * A page request link.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "pageRequestLink")
@XmlType(name = "pageRequestLinkType", propOrder = { 
        "displayedPageNumber",
        "active", 
        "url" })

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
//@formatter:on
public class PageRequestLinkDto extends PageRequestDto {

    private static final long serialVersionUID = 1L;

    private boolean active;

    private String url;

    /**
     * Default constructor.
     */
    public PageRequestLinkDto() {
    }

    public PageRequestLinkDto(PageRequestDto pageRequest, boolean active, String url) {
        if (pageRequest != null) {
            setComparatorItem(pageRequest.getComparatorItem());
            setExtension(pageRequest.getExtension());
            setPageNumber(pageRequest.getPageNumber());
            setPageSize(pageRequest.getPageSize());
            setQuery(pageRequest.getQuery());
        }
        setActive(active);
        setUrl(url);
    }
    
    public PageRequestLinkDto(int pageNumber, int pageSize, ComparatorItem comparatorItem, String query,
            Object extension, boolean active, String url) {
        super(pageNumber, pageSize, comparatorItem, query, extension);
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
    public String getDisplayedPageNumber() {
        return Integer.valueOf(getPageNumber() + 1).toString();
    }
    
    @JsonProperty(value = "displayedPageNumber", required = false)
    protected void setDisplayedPageNumber(String displayedPageNumber) {
    }

    @XmlElement(name = "active", required = true)
    @JsonProperty(value = "active", required = true)
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

    @XmlElement(name = "url", required = false)
    @JsonProperty(value = "url", required = false)
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL.
     */
    @JsonProperty(value = "url", required = false)
    public void setUrl(String url) {
        this.url = url;
    }

}
