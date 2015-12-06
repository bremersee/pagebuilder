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
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Christian Bremer
 */
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
    PageControlDto.MaxResultsSelectorOption.class,
    PageControlDto.Pagination.class,
    PageControlDto.PaginationButton.class
})
@JsonInclude(Include.NON_EMPTY)
public class PageControlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "page", required = true)
    @JsonProperty(value = "page", required = true)
    private PageDto page;
    
    @XmlElement(name = "pagination", required = false)
    @JsonProperty(value = "pagination", required = false)
    private Pagination pagination;
    
    @XmlElement(name = "pageNumberParamName", defaultValue = "p")
    @JsonProperty(value = "pageNumberParamName", required = true)
    private String pageNumberParamName = "p";

    @XmlElement(name = "maxResultsParamName", defaultValue = "max")
    @JsonProperty(value = "maxResultsParamName", required = true)
    private String maxResultsParamName = "max";
    
    @XmlElementWrapper(name = "maxResultsSelectorOptions", required = false)
    @XmlElement(name = "maxResultsSelectorOption")
    @JsonProperty(value = "maxResultsSelectorOptions", required = false)
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

    public PageDto getPage() {
        return page;
    }

    public void setPage(PageDto page) {
        this.page = page;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getPageNumberParamName() {
        return pageNumberParamName;
    }

    public void setPageNumberParamName(String pageNumberParamName) {
    	if (StringUtils.isNotBlank(pageNumberParamName)) {
            this.pageNumberParamName = pageNumberParamName;
    	}
    }

    public String getMaxResultsParamName() {
        return maxResultsParamName;
    }

    public void setMaxResultsParamName(String maxResultsParamName) {
    	if (StringUtils.isNotBlank(maxResultsParamName)) {
            this.maxResultsParamName = maxResultsParamName;
    	}
    }

    public List<MaxResultsSelectorOption> getMaxResultsSelectorOptions() {
        return maxResultsSelectorOptions;
    }

    public void setMaxResultsSelectorOptions(List<MaxResultsSelectorOption> maxResultsSelectorOptions) {
    	if (maxResultsSelectorOptions == null) {
    		maxResultsSelectorOptions = new ArrayList<>();
    	}
        this.maxResultsSelectorOptions = maxResultsSelectorOptions;
    }

    public String getComparatorParamName() {
        return comparatorParamName;
    }

    public void setComparatorParamName(String comparatorParamName) {
    	if (StringUtils.isNotBlank(comparatorParamName)) {
            this.comparatorParamName = comparatorParamName;
    	}
    }

    public String getComparatorParamValue() {
        return comparatorParamValue;
    }

    public void setComparatorParamValue(String comparatorParamValue) {
        this.comparatorParamValue = comparatorParamValue;
    }

    public boolean isQuerySupported() {
        return querySupported;
    }

    public void setQuerySupported(boolean querySupported) {
        this.querySupported = querySupported;
    }

    public String getQueryParamName() {
        return queryParamName;
    }

    public void setQueryParamName(String queryParamName) {
    	if (StringUtils.isNotBlank(queryParamName)) {
            this.queryParamName = queryParamName;
    	}
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @XmlType(name = "maxResultsSelectorOptionType", propOrder = {
            "value",
            "displayedValue",
            "selected"
    })
    @XmlAccessorType(XmlAccessType.FIELD)
    @JsonInclude(Include.NON_EMPTY)
    public static class MaxResultsSelectorOption implements Serializable, Comparable<MaxResultsSelectorOption> {
        
        private static final long serialVersionUID = 1L;

        @XmlElement(name = "pageNumber", required = true)
        @JsonProperty(value = "pageNumber", required = true)
        private int value;
        
        @XmlElement(name = "displayedValue", required = false)
        @JsonProperty(value = "displayedValue", required = false)
        private String displayedValue;
        
        @XmlElement(name = "selected", required = true)
        @JsonProperty(value = "selected", required = true)
        private boolean selected;
        
        public MaxResultsSelectorOption() {
        }

        public MaxResultsSelectorOption(int value, String displayedValue, boolean selected) {
            this.value = value;
            this.displayedValue = displayedValue;
            this.selected = selected;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return getClass().getSimpleName() + " [value=" + value + ", displayedValue="
                    + displayedValue + ", selected=" + selected + "]";
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + value;
            return result;
        }

        /* (non-Javadoc)
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
            MaxResultsSelectorOption other = (MaxResultsSelectorOption) obj;
            if (value != other.value)
                return false;
            return true;
        }

        @Override
        public int compareTo(MaxResultsSelectorOption o) {
            return value - o.value;
        }

        /**
         * @return the value
         */
        public int getValue() {
            return value;
        }

        /**
         * @return the displayedValue
         */
        public String getDisplayedValue() {
            return displayedValue;
        }

        /**
         * @return the selected
         */
        public boolean isSelected() {
            return selected;
        }
    }
    
    @XmlType(name = "paginationButtonType", propOrder = {
            "pageNumber",
            "active",
            "url"
    })
    @XmlAccessorType(XmlAccessType.FIELD)
    @JsonInclude(Include.NON_EMPTY)
    public static class PaginationButton implements Serializable {

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
        public PaginationButton() {
        }

        public PaginationButton(int pageNumber, boolean active, String url) {
            this.pageNumber = pageNumber;
            this.active = active;
            this.url = url;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return getClass().getSimpleName() + " [pageNumber=" + pageNumber + ", active="
                    + active + ", url=" + url + "]";
        }

        /* (non-Javadoc)
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

        /* (non-Javadoc)
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
            PaginationButton other = (PaginationButton) obj;
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
        
        public String getDisplayedPageNumber() {
            return Integer.valueOf(getPageNumber() + 1).toString();
        }

        /**
         * @return the pageNumber
         */
        public int getPageNumber() {
            return pageNumber;
        }

        /**
         * @param pageNumber the pageNumber to set
         */
        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        /**
         * @return the active
         */
        public boolean isActive() {
            return active;
        }

        /**
         * @param active the active to set
         */
        public void setActive(boolean active) {
            this.active = active;
        }

        /**
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * @param url the url to set
         */
        public void setUrl(String url) {
            this.url = url;
        }

    }
    
    @XmlType(name = "paginationType", propOrder = {
            "allButtons",
            "maxPaginationButtons",
            "firstPageButton",
            "previousPageButton",
            "buttons",
            "nextPageButton",
            "lastPageButton"
    })
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlSeeAlso({
        PageControlDto.PaginationButton.class
    })
    @JsonInclude(Include.NON_EMPTY)
    public static class Pagination implements Serializable {
        
        private static final long serialVersionUID = 1L;
        
        @XmlElementWrapper(name = "allButtons", required = false)
        @XmlElement(name = "paginationButton")
        @JsonProperty(value = "allButtons", required = false)
        private List<PaginationButton> allButtons = new ArrayList<PaginationButton>();
        
        @XmlElement(name = "maxPaginationButtons", defaultValue = "7")
        @JsonProperty(value = "maxPaginationButtons", required = true)
        private int maxPaginationButtons = 7;
        
        @XmlElement(name = "firstPageButton", required = false)
        @JsonProperty(value = "firstPageButton", required = false)
        private PaginationButton firstPageButton;
        
        @XmlElement(name = "previousPageButton", required = false)
        @JsonProperty(value = "previousPageButton", required = false)
        private PaginationButton previousPageButton;
        
        @XmlElementWrapper(name = "paginationButtons", required = false)
        @XmlElement(name = "paginationButton")
        @JsonProperty(value = "paginationButtons", required = false)
        private List<PaginationButton> buttons = new ArrayList<PaginationButton>();
        
        @XmlElement(name = "nextPageButton", required = false)
        @JsonProperty(value = "nextPageButton", required = false)
        private PaginationButton nextPageButton;
        
        @XmlElement(name = "lastPageButton", required = false)
        @JsonProperty(value = "lastPageButton", required = false)
        private PaginationButton lastPageButton;
        
        /**
         * Default constructor.
         */
        public Pagination() {
        }

        public Pagination(int maxPaginationButtons,
                Collection<? extends PaginationButton> allButtons, 
                PaginationButton firstPageButton, 
                PaginationButton previousPageButton, 
                Collection<? extends PaginationButton> paginationButtons, 
                PaginationButton nextPageButton, 
                PaginationButton lastPageButton) {
            
            if (allButtons != null) {
                this.allButtons.addAll(allButtons);
            }
            this.maxPaginationButtons = maxPaginationButtons;
            this.firstPageButton = firstPageButton;
            this.previousPageButton = previousPageButton;
            if (paginationButtons != null) {
                this.buttons.addAll(paginationButtons);
            }
            this.nextPageButton = nextPageButton;
            this.lastPageButton = lastPageButton;
        }

        /* (non-Javadoc)
         * @see java.lang.Object#toString()
         */
        @Override
        public String toString() {
            return "Pagination [maxPaginationButtons=" + maxPaginationButtons
                    + ", firstPageButton=" + firstPageButton
                    + ", previousPageButton=" + previousPageButton + ", buttons="
                    + buttons + ", nextPageButton=" + nextPageButton
                    + ", lastPageButton=" + lastPageButton + "]";
        }

        /* (non-Javadoc)
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result
                    + ((allButtons == null) ? 0 : allButtons.hashCode());
            result = prime * result + ((buttons == null) ? 0 : buttons.hashCode());
            result = prime * result
                    + ((firstPageButton == null) ? 0 : firstPageButton.hashCode());
            result = prime * result
                    + ((lastPageButton == null) ? 0 : lastPageButton.hashCode());
            result = prime * result + maxPaginationButtons;
            result = prime * result
                    + ((nextPageButton == null) ? 0 : nextPageButton.hashCode());
            result = prime
                    * result
                    + ((previousPageButton == null) ? 0 : previousPageButton
                            .hashCode());
            return result;
        }

        /* (non-Javadoc)
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
            Pagination other = (Pagination) obj;
            if (allButtons == null) {
                if (other.allButtons != null)
                    return false;
            } else if (!allButtons.equals(other.allButtons))
                return false;
            if (buttons == null) {
                if (other.buttons != null)
                    return false;
            } else if (!buttons.equals(other.buttons))
                return false;
            if (firstPageButton == null) {
                if (other.firstPageButton != null)
                    return false;
            } else if (!firstPageButton.equals(other.firstPageButton))
                return false;
            if (lastPageButton == null) {
                if (other.lastPageButton != null)
                    return false;
            } else if (!lastPageButton.equals(other.lastPageButton))
                return false;
            if (maxPaginationButtons != other.maxPaginationButtons)
                return false;
            if (nextPageButton == null) {
                if (other.nextPageButton != null)
                    return false;
            } else if (!nextPageButton.equals(other.nextPageButton))
                return false;
            if (previousPageButton == null) {
                if (other.previousPageButton != null)
                    return false;
            } else if (!previousPageButton.equals(other.previousPageButton))
                return false;
            return true;
        }

        /**
         * @return the maxPaginationButtons
         */
        public int getMaxPaginationButtons() {
            return maxPaginationButtons;
        }

        /**
         * @param maxPaginationButtons the maxPaginationButtons to set
         */
        public void setMaxPaginationButtons(int maxPaginationButtons) {
            if (maxPaginationButtons < 1) {
                maxPaginationButtons = 1;
            } else {
                this.maxPaginationButtons = maxPaginationButtons;
            }
        }

        /**
         * @return the previousPageButton
         */
        public PaginationButton getPreviousPageButton() {
            return previousPageButton;
        }

        /**
         * @param previousPageButton the previousPageButton to set
         */
        public void setPreviousPageButton(PaginationButton previousPageButton) {
            this.previousPageButton = previousPageButton;
        }

        /**
         * @return the nextPageButton
         */
        public PaginationButton getNextPageButton() {
            return nextPageButton;
        }

        /**
         * @param nextPageButton the nextPageButton to set
         */
        public void setNextPageButton(PaginationButton nextPageButton) {
            this.nextPageButton = nextPageButton;
        }

        /**
         * @return the allButtons
         */
        public List<PaginationButton> getAllButtons() {
            return allButtons;
        }

		public void setAllButtons(List<PaginationButton> allButtons) {
			if (allButtons == null) {
				allButtons = new ArrayList<>();
			}
			this.allButtons = allButtons;
		}

        /**
         * @return the buttons
         */
        public List<PaginationButton> getButtons() {
            return buttons;
        }

		public void setButtons(List<PaginationButton> buttons) {
			if (buttons == null) {
				buttons = new ArrayList<>();
			}
			this.buttons = buttons;
		}
        
        /**
         * @return the firstPageButton
         */
        public PaginationButton getFirstPageButton() {
            return firstPageButton;
        }

        /**
         * @param firstPageButton the firstPageButton to set
         */
        public void setFirstPageButton(PaginationButton firstPageButton) {
            this.firstPageButton = firstPageButton;
        }

        /**
         * @return the lastPageButton
         */
        public PaginationButton getLastPageButton() {
            return lastPageButton;
        }

        /**
         * @param lastPageButton the lastPageButton to set
         */
        public void setLastPageButton(PaginationButton lastPageButton) {
            this.lastPageButton = lastPageButton;
        }

    }
    
}
