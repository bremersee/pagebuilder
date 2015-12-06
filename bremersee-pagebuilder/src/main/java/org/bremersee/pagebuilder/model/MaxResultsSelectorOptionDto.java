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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * @author Christian Bremer
 */
//@formatter:off
@XmlType(name = "maxResultsSelectorOptionType", propOrder = { 
        "value", 
        "displayedValue", 
        "selected" })
@XmlAccessorType(XmlAccessType.FIELD)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, 
    getterVisibility = Visibility.NONE, 
    creatorVisibility = Visibility.NONE, 
    isGetterVisibility = Visibility.NONE, 
    setterVisibility = Visibility.NONE)
@JsonInclude(Include.NON_EMPTY)
@JsonPropertyOrder(value = {
        "value",
        "displayedValue",
        "selected"
})
//@formatter:on
public class MaxResultsSelectorOptionDto implements MaxResultsSelectorOption {

    /**
     * If the given option is {@code null}, {@code null} will be returned.<br/>
     * If the given option is an instance of {@code MaxResultsSelectorOptionDto}
     * , that instance will be returned. Otherwise a new instance will be
     * created.
     * 
     * @param maxResultsSelectorOption
     *            a maximum result option
     */
    public static MaxResultsSelectorOptionDto toMaxResultsSelectorOptionDto(
            MaxResultsSelectorOption maxResultsSelectorOption) {
        if (maxResultsSelectorOption == null) {
            return null;
        }
        if (maxResultsSelectorOption instanceof MaxResultsSelectorOptionDto) {
            return (MaxResultsSelectorOptionDto) maxResultsSelectorOption;
        }
        return new MaxResultsSelectorOptionDto(maxResultsSelectorOption);
    }

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "value", required = true)
    @JsonProperty(value = "value", required = true)
    private int value;

    @XmlElement(name = "displayedValue", required = false)
    @JsonProperty(value = "displayedValue", required = false)
    private String displayedValue;

    @XmlElement(name = "selected", required = true)
    @JsonProperty(value = "selected", required = true)
    private boolean selected;

    /**
     * Default constructor.
     */
    public MaxResultsSelectorOptionDto() {
    }

    /**
     * Creates a {@link MaxResultsSelectorOptionDto} with the given parameters.
     * 
     * @param value
     *            the value
     * @param displayedValue
     *            the display value
     * @param selected
     *            {@code true} if the option is selected, otherwise
     *            {@code false}
     */
    public MaxResultsSelectorOptionDto(int value, String displayedValue, boolean selected) {
        this.value = value;
        this.displayedValue = displayedValue;
        this.selected = selected;
    }

    /**
     * Creates a {@link MaxResultsSelectorOptionDto} from another
     * {@link MaxResultsSelectorOption}.
     * 
     * @param maxResultsSelectorOption
     *            the other maximum result option
     */
    public MaxResultsSelectorOptionDto(MaxResultsSelectorOption maxResultsSelectorOption) {
        if (maxResultsSelectorOption != null) {
            this.value = maxResultsSelectorOption.getValue();
            this.displayedValue = maxResultsSelectorOption.getDisplayedValue();
            this.selected = maxResultsSelectorOption.isSelected();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [value=" + value + ", displayedValue=" + displayedValue + ", selected="
                + selected + "]";
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
        result = prime * result + value;
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
        MaxResultsSelectorOptionDto other = (MaxResultsSelectorOptionDto) obj;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public int compareTo(MaxResultsSelectorOption o) {
        return value - o.getValue();
    }

    /**
     * @return the value
     */
    @Override
    public int getValue() {
        return value;
    }

    /**
     * @return the displayedValue
     */
    @Override
    public String getDisplayedValue() {
        return displayedValue;
    }

    /**
     * @return the selected
     */
    @Override
    public boolean isSelected() {
        return selected;
    }
}
