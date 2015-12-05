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
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.bremersee.comparator.model.ComparatorItem;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * <p>
 * A page consist at least a list of items. Furthermore it may has
 * information about the size of all available items, the number of the first
 * item of this page, the maximum number of items and how they are sorted.
 * </p>
 * 
 * @author Christian Bremer
 */
@XmlRootElement(name = "page")
@XmlType(name = "pageType", propOrder = { "totalSize", "firstResult", "maxResults", "comparatorItem", "entries" })
@XmlAccessorType(XmlAccessType.FIELD)
@JsonInclude(Include.NON_EMPTY)
public class PageDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "totalSize", required = false)
    @JsonProperty(value = "totalSize", required = false)
    private Integer totalSize;

    @XmlElement(name = "firstResult", required = false)
    @JsonProperty(value = "firstResult", required = false)
    private Integer firstResult;

    @XmlElement(name = "maxResults", required = false)
    @JsonProperty(value = "maxResults", required = false)
    private Integer maxResults;

    @XmlElement(name = "comparatorItem", required = false)
    @JsonProperty(value = "comparatorItem", required = false)
    private ComparatorItem comparatorItem;

    @XmlElementWrapper(name = "entries", required = false)
    @XmlAnyElement(lax = true)
    private List<Object> entries = new ArrayList<Object>();

    /**
     * Default constructor.
     */
    public PageDto() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PageDto [totalSize=" + totalSize + ", firstResult=" + firstResult + ", maxResults=" + maxResults
                + ", comparatorItem=" + comparatorItem + ", entries=" + entries + "]";
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
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        result = prime * result + ((firstResult == null) ? 0 : firstResult.hashCode());
        result = prime * result + ((maxResults == null) ? 0 : maxResults.hashCode());
        result = prime * result + ((totalSize == null) ? 0 : totalSize.hashCode());
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
        PageDto other = (PageDto) obj;
        if (comparatorItem == null) {
            if (other.comparatorItem != null)
                return false;
        } else if (!comparatorItem.equals(other.comparatorItem))
            return false;
        if (entries == null) {
            if (other.entries != null)
                return false;
        } else if (!entries.equals(other.entries))
            return false;
        if (firstResult == null) {
            if (other.firstResult != null)
                return false;
        } else if (!firstResult.equals(other.firstResult))
            return false;
        if (maxResults == null) {
            if (other.maxResults != null)
                return false;
        } else if (!maxResults.equals(other.maxResults))
            return false;
        if (totalSize == null) {
            if (other.totalSize != null)
                return false;
        } else if (!totalSize.equals(other.totalSize))
            return false;
        return true;
    }

    /**
     * Return the size of all available entries.
     * 
     * @return the size of all available entries (may be {@code null})
     */
    public Integer getTotalSize() {
        return totalSize;
    }

    /**
     * Set the size of all available entries
     * 
     * @param totalSize
     *            the size of all available entries
     */
    public void setTotalSize(Integer totalSize) {
        this.totalSize = totalSize;
    }

    /**
     * Return the first result number.
     * 
     * @return the first result number
     */
    public Integer getFirstResult() {
        return firstResult;
    }

    /**
     * Set the first result number.
     * 
     * @param firstResult
     *            the first result number
     */
    public void setFirstResult(Integer firstResult) {
        this.firstResult = firstResult;
    }

    /**
     * Calculate the previous first result number. It requires that
     * {@code totalSize}, {@code maxResults} and {@code firstResult} are set.
     * Otherwise {@code null} will be returned.<br/>
     * If the previous first number is smaller than {@code 0}, {@code null} will
     * be returned, too.
     * 
     * @return the previous first result number or {@code null}
     */
    @JsonIgnore
    public Integer getPreviousFirstResult() {

        if (getTotalSize() != null && getFirstResult() != null && getMaxResults() != null) {
            long previous = getFirstResult() - getMaxResults();
            if (previous <= Integer.MIN_VALUE) {
                return null;
            }
            return (int) previous < 0 ? null : (int) previous;
        }
        return null;
    }

    /**
     * Calculate the next first result number. It requires that
     * {@code totalSize}, {@code maxResults} and {@code firstResult} are set.
     * Otherwise {@code null} will be returned.<br/>
     * If the next first number is bigger than {@link Integer#MAX_VALUE} or
     * {@code totalSize}, {@code null} will be returned, too.
     * 
     * @return the next first result number or {@code null}
     */
    @JsonIgnore
    public Integer getNextFirstResult() {

        if (getTotalSize() != null && getFirstResult() != null && getMaxResults() != null) {
            long next = getFirstResult() + getMaxResults();
            if (next >= Integer.MAX_VALUE) {
                return null;
            }
            return (int) next >= getTotalSize() ? null : (int) next;
        }
        return null;
    }

    /**
     * Return the maximum number of results.
     * 
     * @return the maximum number of results
     */
    public Integer getMaxResults() {
        return maxResults;
    }

    /**
     * Set the maximum number of results
     * 
     * @param maxResults
     *            the maximum number of results
     */
    public void setMaxResults(Integer maxResults) {
        this.maxResults = maxResults;
    }

    /**
     * Calculate the number of available pages. It requires that
     * {@code totalSize} and {@code maxResults} are set. Otherwise {@code null}
     * will be returned.
     * 
     * @return the number of available pages or {@code null}
     */
    @JsonIgnore
    public Integer getPageSize() {
        if (getTotalSize() != null && getMaxResults() != null && getMaxResults() > 0) {
            return Double.valueOf(Math.ceil((double) getTotalSize() / (double) getMaxResults())).intValue();
        }
        return null;
    }

    /**
     * Calculate the current page number. It requires that {@code totalSize} and
     * {@code maxResults} are set. Otherwise {@code null} will be returned.
     * 
     * @return the current page number or {@code null}
     */
    @JsonIgnore
    public Integer getCurrentPage() {
        if (getFirstResult() != null && getMaxResults() != null && getMaxResults() > 0) {
            return Double.valueOf(Math.floor((double) getFirstResult() / (double) getMaxResults())).intValue();
        }
        return null;
    }

    /**
     * Calculate the pagination size depending on the number of fields (
     * {@code fieldSize}). It requires the the page size is not {@code null}.
     * 
     * <pre>
     *    ---    ---    ---    ---    ---      ---    ---    ---    ---
     *   | 1 |  | 2 |  | 3 |  | 4 |  | 5 |    | 6 |  | 7 |  | 8 |  | 9 | 
     *    ---    ---    ---    ---    ---      ---    ---    ---    ---
     *   Page1  Page2  Page3  Page4  Page5    Page6  Page7  Page8  Page9
     *   |                               |    |                               |
     *    -------------------------------      -------------------------------
     *              field size                           field size
     *   |                                                                    |
     *    --------------------------------------------------------------------
     *                            pagination size = 2
     * </pre>
     * 
     * @param fieldSize
     *            the field size
     * @return the pagination size or {@code null}
     */
    public Integer getPaginationSize(int fieldSize) {

        Integer pageSize = getPageSize();
        if (pageSize != null) {
            if (pageSize == 0) {
                return 0;
            }
            if (fieldSize < pageSize) {
                fieldSize = pageSize;
            }
            return Double.valueOf(Math.ceil((double) pageSize / (double) fieldSize)).intValue();
        }
        return null;
    }

    /**
     * Return the comparator item that is used for sorting.
     * 
     * @return the comparator item
     */
    public ComparatorItem getComparatorItem() {
        return comparatorItem;
    }

    /**
     * Set the comparator item that was used for sorting.
     * 
     * @param comparatorItem
     *            the comparator item
     */
    public void setComparatorItem(ComparatorItem comparatorItem) {
        this.comparatorItem = comparatorItem;
    }

    /**
     * Return the entries of this page.<br/>
     * If the serialized page was read with the Jackson JSON processor, each
     * entry will be a {@link LinkedHashMap}.
     * 
     * @return the entries of this page
     */
    public List<Object> getEntries() {
        return entries;
    }

    /**
     * Set the entries of this page.
     * 
     * @param entries
     *            the entries of this page
     */
    public void setEntries(List<Object> entries) {
        if (entries == null) {
            entries = new ArrayList<Object>();
        }
        this.entries = entries;
    }

}
