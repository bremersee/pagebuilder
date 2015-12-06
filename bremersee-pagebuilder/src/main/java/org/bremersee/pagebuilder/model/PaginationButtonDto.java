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
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * <p>
 * A pagination button.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
@XmlType(name = "paginationButtonType", propOrder = { 
        "pageNumber", 
        "active", 
        "url" })
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, 
    getterVisibility = Visibility.NONE, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.NONE, 
    setterVisibility = Visibility.NONE)
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder(value = {
        "pageNumber",
        "active",
        "url"
})
//@formatter:on
public class PaginationButtonDto implements PaginationButton {

    /**
     * If the given pagination button is {@code null}, {@code null} will be
     * returned.<br/>
     * If the given pagination button is an instance of
     * {@code PaginationButtonDto}, that instance will be returned. Otherwise a
     * new instance will be created.
     * 
     * @param paginationButton
     *            a pagination button
     */
    public static PaginationButtonDto toPaginationButtonDto(PaginationButton paginationButton) {
        if (paginationButton == null) {
            return null;
        }
        if (paginationButton instanceof PaginationButtonDto) {
            return (PaginationButtonDto) paginationButton;
        }
        return new PaginationButtonDto(paginationButton);
    }

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "pageNumber", required = true)
    @JsonProperty(value = "pageNumber", required = true)
    private int pageNumber;

    @XmlElement(name = "active", required = true)
    @JsonProperty(value = "active", required = true)
    private boolean active;

    @XmlElement(name = "url", required = false)
    @JsonProperty(value = "url", required = false)
    private String url;

    /**
     * Default constructor.
     */
    public PaginationButtonDto() {
    }

    /**
     * Creates a pagination button with the given values.
     * 
     * @param pageNumber
     *            the page number
     * @param active
     *            specifies whether the button is active or not
     * @param url
     *            the URL
     */
    public PaginationButtonDto(int pageNumber, boolean active, String url) {
        this.pageNumber = pageNumber;
        this.active = active;
        this.url = url;
    }

    /**
     * Creates a pagination button from another one.
     * 
     * @param paginationButton
     *            the other button
     */
    public PaginationButtonDto(PaginationButton paginationButton) {
        if (paginationButton != null) {
            this.pageNumber = paginationButton.getPageNumber();
            this.active = paginationButton.isActive();
            this.url = paginationButton.getUrl();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [pageNumber=" + pageNumber + ", active=" + active + ", url=" + url + "]";
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
        result = prime * result + (active ? 1231 : 1237);
        result = prime * result + pageNumber;
        result = prime * result + ((url == null) ? 0 : url.hashCode());
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
        PaginationButtonDto other = (PaginationButtonDto) obj;
        if (active != other.active)
            return false;
        if (pageNumber != other.pageNumber)
            return false;
        if (url == null) {
            if (other.url != null)
                return false;
        } else if (!url.equals(other.url))
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.bremersee.pagebuilder.model.PaginationButton#getDisplayedPageNumber()
     */
    public String getDisplayedPageNumber() {
        return Integer.valueOf(getPageNumber() + 1).toString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PaginationButton#getPageNumber()
     */
    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Sets the page number.
     */
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PaginationButton#isActive()
     */
    @Override
    public boolean isActive() {
        return active;
    }

    /**
     * Specifies whether this button is active or not.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.PaginationButton#getUrl()
     */
    @Override
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL.
     */
    public void setUrl(String url) {
        this.url = url;
    }

}
