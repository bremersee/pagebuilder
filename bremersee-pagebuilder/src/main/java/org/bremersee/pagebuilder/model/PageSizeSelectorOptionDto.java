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

/**
 * <p>
 * An option of page size selector.
 * </p>
 *
 * @author Christian Bremer
 */
//@formatter:off
@SuppressWarnings({"WeakerAccess", "unused"})
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "pageSizeSelectorOption")
@XmlType(name = "pageSizeSelectorOptionType", propOrder = {
        "value",
        "displayedValue",
        "selected"})

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.ALWAYS)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
        getterVisibility = Visibility.PUBLIC_ONLY,
        creatorVisibility = Visibility.NONE,
        isGetterVisibility = Visibility.PUBLIC_ONLY,
        setterVisibility = Visibility.PUBLIC_ONLY)
@JsonPropertyOrder(value = {
        "value",
        "displayedValue",
        "selected"
})
@ApiModel(
        value = "PageSizeSelectorOption",
        description = "An option of page size selector.")
//@formatter:on
public class PageSizeSelectorOptionDto implements Serializable, Comparable<PageSizeSelectorOptionDto> {

    private static final long serialVersionUID = 1L;

    private int value;

    private String displayedValue;

    private boolean selected;

    /**
     * Default constructor.
     */
    public PageSizeSelectorOptionDto() {
        super();
    }

    /**
     * Creates a {@link PageSizeSelectorOptionDto} with the given parameters.
     *
     * @param value          the value
     * @param displayedValue the display value
     * @param selected       {@code true} if the option is selected, otherwise
     *                       {@code false}
     */
    public PageSizeSelectorOptionDto(int value, String displayedValue, boolean selected) {
        this.value = value;
        this.displayedValue = displayedValue;
        this.selected = selected;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [value=" + value + ", displayedValue=" + displayedValue + ", selected="
                + selected + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + value;
        return result;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PageSizeSelectorOptionDto other = (PageSizeSelectorOptionDto) obj;
        if (value != other.value)
            return false;
        return true;
    }

    /**
     * Compares the values of the options.
     *
     * @param o the other options
     */
    @Override
    public int compareTo(PageSizeSelectorOptionDto o) {
        return value - o.getValue();
    }

    /**
     * Returns the value (the page size) of this option.
     *
     * @return the value (the page size) of this option
     */
    @XmlElement(name = "value", required = true)
    @JsonProperty(value = "value", required = true)
    @ApiModelProperty(value = "The value (the page size) of this option.", required = true)
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of this option.
     *
     * @param value the value of this option
     */
    @JsonProperty(value = "value", required = true)
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Returns the displayed value (normally the page size plus one).
     *
     * @return the displayed value (normally the page size plus one)
     */
    @XmlElement(name = "displayedValue")
    @JsonProperty(value = "displayedValue")
    @ApiModelProperty(value = "The displayed value (normally the page size plus one).", position = 1)
    public String getDisplayedValue() {
        if (StringUtils.isBlank(displayedValue)) {
            return Integer.toString(value + 1);
        }
        return displayedValue;
    }

    /**
     * Sets the displayed value of this option.
     *
     * @param displayedValue the displayed value of this option
     */
    @JsonProperty(value = "displayedValue")
    public void setDisplayedValue(String displayedValue) {
        this.displayedValue = displayedValue;
    }

    /**
     * Returns {@code true} if the option is selected, otherwise {@code false}.
     *
     * @return {@code true} if the option is selected, otherwise {@code false}
     */
    @XmlElement(name = "selected", required = true)
    @JsonProperty(value = "selected", required = true)
    @ApiModelProperty(value = "Is this option selected?.", position = 2, required = true)
    public boolean isSelected() {
        return selected;
    }

    /**
     * Sets whether this option is selected or not.
     *
     * @param selected {@code true} if this option is selected, otherwise {@code false}
     */
    @JsonProperty(value = "selected")
    public void setSelected(boolean selected) {
        this.selected = selected;
    }

}
