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
import java.util.Collection;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * <p>
 * A pagination can be used to display pagination buttons on a web site.
 * </p>
 * 
 * @author Christian Bremer
 */
//@formatter:off
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
    PaginationButtonDto.class
})
@JsonAutoDetect(fieldVisibility = Visibility.DEFAULT, 
    getterVisibility = Visibility.NONE, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.NONE, 
    setterVisibility = Visibility.NONE)
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder(value = {
        "allButtons",
        "maxPaginationButtons",
        "firstPageButton",
        "previousPageButton",
        "buttons",
        "nextPageButton",
        "lastPageButton"
})
//@formatter:on
public class PaginationDto implements Pagination {

    /**
     * If the given pagination is {@code null}, {@code null} will be returned.
     * <br/>
     * If the given pagination is an instance of {@code PaginationDto}, that
     * instance will be returned. Otherwise a new instance will be created.
     * 
     * @param pagination
     *            a pagination
     */
    public static PaginationDto toPaginationDto(Pagination pagination) {
        if (pagination == null) {
            return null;
        }
        if (pagination instanceof PaginationDto) {
            return (PaginationDto) pagination;
        }
        return new PaginationDto(pagination);
    }

    private static final long serialVersionUID = 1L;

    @XmlElementWrapper(name = "allButtons", required = false)
    @XmlElement(name = "paginationButton", type = PaginationButtonDto.class)
    @JsonProperty(value = "allButtons", required = false)
    @JsonDeserialize(contentAs = PaginationButtonDto.class)
    private List<PaginationButton> allButtons = new ArrayList<PaginationButton>();

    @XmlElement(name = "maxPaginationButtons", defaultValue = "7")
    @JsonProperty(value = "maxPaginationButtons", required = true)
    private int maxPaginationButtons = 7;

    @XmlElement(name = "firstPageButton", required = false, type = PaginationButtonDto.class)
    @JsonProperty(value = "firstPageButton", required = false)
    @JsonDeserialize(as = PaginationButtonDto.class)
    private PaginationButton firstPageButton;

    @XmlElement(name = "previousPageButton", required = false, type = PaginationButtonDto.class)
    @JsonProperty(value = "previousPageButton", required = false)
    @JsonDeserialize(as = PaginationButtonDto.class)
    private PaginationButton previousPageButton;

    @XmlElementWrapper(name = "paginationButtons", required = false)
    @XmlElement(name = "paginationButton", type = PaginationButtonDto.class)
    @JsonProperty(value = "paginationButtons", required = false)
    @JsonDeserialize(contentAs = PaginationButtonDto.class)
    private List<PaginationButton> buttons = new ArrayList<PaginationButton>();

    @XmlElement(name = "nextPageButton", required = false, type = PaginationButtonDto.class)
    @JsonProperty(value = "nextPageButton", required = false)
    @JsonDeserialize(as = PaginationButtonDto.class)
    private PaginationButton nextPageButton;

    @XmlElement(name = "lastPageButton", required = false, type = PaginationButtonDto.class)
    @JsonProperty(value = "lastPageButton", required = false)
    @JsonDeserialize(as = PaginationButtonDto.class)
    private PaginationButton lastPageButton;

    /**
     * Default constructor.
     */
    public PaginationDto() {
    }

    /**
     * Creates a pagination with the given parameters.
     * 
     * @param maxPaginationButtons
     *            the maximum numbers of pagination buttons.
     * @param allButtons
     *            all pagination buttons
     * @param firstPageButton
     *            the first pagination button
     * @param previousPageButton
     *            the previous pagination button
     * @param paginationButtons
     *            the pagination buttons
     * @param nextPageButton
     *            the next pagination button
     * @param lastPageButton
     *            the last pagination button
     */
    public PaginationDto(int maxPaginationButtons, Collection<? extends PaginationButton> allButtons,
            PaginationButton firstPageButton, PaginationButton previousPageButton,
            Collection<? extends PaginationButton> paginationButtons, PaginationButton nextPageButton,
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

    /**
     * Creates a pagination from another one.
     * 
     * @param pagination
     *            the other pagination
     */
    public PaginationDto(Pagination pagination) {
        if (pagination != null) {
            if (pagination.getAllButtons() != null) {
                for (PaginationButton button : pagination.getAllButtons()) {
                    if (button != null) {
                        this.allButtons.add(PaginationButtonDto.toPaginationButtonDto(button));
                    }
                }
            }
            this.maxPaginationButtons = pagination.getMaxPaginationButtons();
            this.firstPageButton = PaginationButtonDto.toPaginationButtonDto(pagination.getFirstPageButton());
            this.previousPageButton = PaginationButtonDto.toPaginationButtonDto(pagination.getPreviousPageButton());
            if (pagination.getButtons() != null) {
                for (PaginationButton button : pagination.getButtons()) {
                    if (button != null) {
                        this.buttons.add(PaginationButtonDto.toPaginationButtonDto(button));
                    }
                }
            }
            this.nextPageButton = PaginationButtonDto.toPaginationButtonDto(pagination.getNextPageButton());
            this.lastPageButton = PaginationButtonDto.toPaginationButtonDto(pagination.getLastPageButton());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Pagination [maxPaginationButtons=" + maxPaginationButtons + ", firstPageButton=" + firstPageButton
                + ", previousPageButton=" + previousPageButton + ", buttons=" + buttons + ", nextPageButton="
                + nextPageButton + ", lastPageButton=" + lastPageButton + "]";
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
        result = prime * result + ((allButtons == null) ? 0 : allButtons.hashCode());
        result = prime * result + ((buttons == null) ? 0 : buttons.hashCode());
        result = prime * result + ((firstPageButton == null) ? 0 : firstPageButton.hashCode());
        result = prime * result + ((lastPageButton == null) ? 0 : lastPageButton.hashCode());
        result = prime * result + maxPaginationButtons;
        result = prime * result + ((nextPageButton == null) ? 0 : nextPageButton.hashCode());
        result = prime * result + ((previousPageButton == null) ? 0 : previousPageButton.hashCode());
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
        PaginationDto other = (PaginationDto) obj;
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

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Pagination#getMaxPaginationButtons()
     */
    @Override
    public int getMaxPaginationButtons() {
        return maxPaginationButtons;
    }

    /**
     * Sets the maximum number of pagination buttons.
     */
    public void setMaxPaginationButtons(int maxPaginationButtons) {
        if (maxPaginationButtons < 1) {
            maxPaginationButtons = 1;
        } else {
            this.maxPaginationButtons = maxPaginationButtons;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Pagination#getPreviousPageButton()
     */
    @Override
    public PaginationButton getPreviousPageButton() {
        return previousPageButton;
    }

    /**
     * Sets the previous pagination button.
     */
    public void setPreviousPageButton(PaginationButton previousPageButton) {
        this.previousPageButton = previousPageButton;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Pagination#getNextPageButton()
     */
    @Override
    public PaginationButton getNextPageButton() {
        return nextPageButton;
    }

    /**
     * Sets the next pagination button.
     */
    public void setNextPageButton(PaginationButton nextPageButton) {
        this.nextPageButton = nextPageButton;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Pagination#getAllButtons()
     */
    @Override
    public List<PaginationButton> getAllButtons() {
        return allButtons;
    }

    /**
     * Sets all pagination buttons.
     */
    public void setAllButtons(List<PaginationButton> allButtons) {
        if (allButtons == null) {
            allButtons = new ArrayList<>();
        }
        this.allButtons = allButtons;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Pagination#getButtons()
     */
    @Override
    public List<PaginationButton> getButtons() {
        return buttons;
    }

    /**
     * Sets the pagination buttons.
     */
    public void setButtons(List<PaginationButton> buttons) {
        if (buttons == null) {
            buttons = new ArrayList<>();
        }
        this.buttons = buttons;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Pagination#getFirstPageButton()
     */
    @Override
    public PaginationButton getFirstPageButton() {
        return firstPageButton;
    }

    /**
     * Sets the first pagination button.
     */
    public void setFirstPageButton(PaginationButton firstPageButton) {
        this.firstPageButton = firstPageButton;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Pagination#getLastPageButton()
     */
    public PaginationButton getLastPageButton() {
        return lastPageButton;
    }

    /**
     * Sets the last pagination button.
     */
    public void setLastPageButton(PaginationButton lastPageButton) {
        this.lastPageButton = lastPageButton;
    }

}
