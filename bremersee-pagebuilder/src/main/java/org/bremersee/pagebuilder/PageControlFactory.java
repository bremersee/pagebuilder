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

package org.bremersee.pagebuilder;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.bremersee.comparator.ComparatorItemTransformer;
import org.bremersee.comparator.ComparatorItemTransformerImpl;
import org.bremersee.pagebuilder.model.PageControlDto;
import org.bremersee.pagebuilder.model.PageDto;

/**
 * <p>
 * 
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class PageControlFactory {

	protected static final String RESOURCE_BUNDLE_BASE_NAME = "bremersee-pagebuilder-i18n";

	public static final int DEFAULT_MAX_PAGINATION_BUTTONS = 7;

	public static final int DEFAULT_MAX_RESULTS_MIN_VALUE = 10;

	public static final int DEFAULT_MAX_RESULTS_MAX_VALUE = 100;

	public static final int DEFAULT_MAX_RESULTS_SELECTOR_STEP = 10;

	public static final boolean DEFAULT_SELECT_ALL_RESULTS_AVAILABLE = true;

	public static PageControlFactory newInstance() {
		return new DefaultPageControlFactory();
	}

	public static PageControlFactory newInstance(String factoryClassName)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (PageControlFactory) Class.forName(factoryClassName).newInstance();
	}

	public static PageControlFactory newInstance(String factoryClassName, ClassLoader classLoader)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (PageControlFactory) Class.forName(factoryClassName, true, classLoader).newInstance();
	}

	private ComparatorItemTransformer comparatorItemTransformer = new ComparatorItemTransformerImpl();

	private String queryParamName = "q";

	private String maxResultsParamName = "max";

	private String pageNumberParamName = "p";

	private String comparatorParamName = "c";

	private int maxResultsSelectorMinValue = DEFAULT_MAX_RESULTS_MIN_VALUE;

	private int maxResultsSelectorMaxValue = DEFAULT_MAX_RESULTS_MAX_VALUE;

	private int maxResultsSelectorStep = DEFAULT_MAX_RESULTS_SELECTOR_STEP;

	private boolean selectAllResultsAvailable = DEFAULT_SELECT_ALL_RESULTS_AVAILABLE;

	private int maxPaginationButtons = DEFAULT_MAX_PAGINATION_BUTTONS;

	private boolean isQuerySupported = true;

	/**
	 * Gets the comparator item transformer, that reads and writes the
	 * comparator item query parameter.
	 * 
	 * @return the comparator item transformer
	 */
	public ComparatorItemTransformer getComparatorItemTransformer() {
		return comparatorItemTransformer;
	}

	/**
	 * Sets the comparator item transformer, that reads and writes the
	 * comparator item query parameter.<br/>
	 * Default is {@link ComparatorItemTransformerImpl}.
	 * 
	 * @param comparatorItemTransformer
	 *            the comparator item transformer
	 * @return the page control factory
	 */
	public PageControlFactory setComparatorItemTransformer(ComparatorItemTransformer comparatorItemTransformer) {
		if (comparatorItemTransformer != null) {
			this.comparatorItemTransformer = comparatorItemTransformer;
		}
		return this;
	}

	/**
	 * Gets the name of the query parameter. Default is 'q'.
	 * 
	 * @return the name of the query parameter
	 */
	public String getQueryParamName() {
		return queryParamName;
	}

	/**
	 * Sets the name of the query parameter. Default is 'q'.
	 * 
	 * @param queryParamName
	 *            the name of the query parameter
	 * @return the page control factory
	 */
	public PageControlFactory setQueryParamName(String queryParamName) {
		if (StringUtils.isNotBlank(queryParamName)) {
			this.queryParamName = queryParamName;
		}
		return this;
	}

	/**
	 * Gets the name of the max. results query parameter. Default is 'max'.
	 * 
	 * @return the name of the max. results query parameter
	 */
	public String getMaxResultsParamName() {
		return maxResultsParamName;
	}

	/**
	 * Sets the name of the max. results query parameter. Default is 'max'.
	 * 
	 * @param maxResultsParamName
	 *            the name of the max. results query parameter
	 * @return the page control factory
	 */
	public PageControlFactory setMaxResultsParamName(String maxResultsParamName) {
		if (StringUtils.isNotBlank(maxResultsParamName)) {
			this.maxResultsParamName = maxResultsParamName;
		}
		return this;
	}

	/**
	 * Gets the name of the page number query parameter. Default is 'p'.
	 * 
	 * @return the name of the page number query parameter
	 */
	public String getPageNumberParamName() {
		return pageNumberParamName;
	}

	/**
	 * Sets the name of the page number query parameter. Default is 'p'.
	 * 
	 * @param pageNumberParamName
	 *            the name of the page number query parameter
	 * @return the page control factory
	 */
	public PageControlFactory setPageNumberParamName(String pageNumberParamName) {
		if (StringUtils.isNotBlank(pageNumberParamName)) {
			this.pageNumberParamName = pageNumberParamName;
		}
		return this;
	}

	/**
	 * Gets the name of the comparator query parameter. Default is 'q'.
	 * 
	 * @return the name of the comparator query parameter
	 */
	public String getComparatorParamName() {
		return comparatorParamName;
	}

	/**
	 * Sets the name of the comparator query parameter. Default is 'q'.
	 * 
	 * @param comparatorParamName
	 *            the name of the comparator query parameter
	 * @return the page control factory
	 */
	public PageControlFactory setComparatorParamName(String comparatorParamName) {
		if (StringUtils.isNotBlank(comparatorParamName)) {
			this.comparatorParamName = comparatorParamName;
		}
		return this;
	}

	/**
	 * Gets the minimum value, that the maximum results per page selector can
	 * have. Default is '10'.
	 * 
	 * @return the minimum value
	 */
	public int getMaxResultsSelectorMinValue() {
		return maxResultsSelectorMinValue;
	}

	/**
	 * Sets the minimum value, that the maximum results per page selector can
	 * have. Default is '10'.
	 * 
	 * @param maxResultsSelectorMinValue
	 *            the minimum value
	 * @return the page control factory
	 */
	public PageControlFactory setMaxResultsSelectorMinValue(int maxResultsSelectorMinValue) {
		this.maxResultsSelectorMinValue = maxResultsSelectorMinValue;
		return this;
	}

	/**
	 * Gets the maximum value, that the maximum results per page selector can
	 * have. Default is '100'.
	 * 
	 * @return the maximum value
	 */
	public int getMaxResultsSelectorMaxValue() {
		return maxResultsSelectorMaxValue;
	}

	/**
	 * Sets the maximum value, that the maximum results per page selector can
	 * have. Default is '100'.
	 * 
	 * @param maxResultsSelectorMaxValue
	 *            the maximum value
	 * @return the page control factory
	 */
	public PageControlFactory setMaxResultsSelectorMaxValue(int maxResultsSelectorMaxValue) {
		this.maxResultsSelectorMaxValue = maxResultsSelectorMaxValue;
		return this;
	}

	/**
	 * Gets the step size of the maximum results per page selector. Default is
	 * '10'.
	 * 
	 * @return the step size of the maximum results per page selector
	 */
	public int getMaxResultsSelectorStep() {
		return maxResultsSelectorStep;
	}

	/**
	 * Sets the step size of the maximum results per page selector. Default is
	 * '10'.
	 * 
	 * @param maxResultsSelectorStep
	 *            the step size of the maximum results per page selector
	 * @return the page control factory
	 */
	public PageControlFactory setMaxResultsSelectorStep(int maxResultsSelectorStep) {
		this.maxResultsSelectorStep = maxResultsSelectorStep;
		return this;
	}

	/**
	 * Returns {@code true} if the maximum results per page selector has an item
	 * to choose all available entries, otherwise {@code false}. Default is
	 * 'true'.
	 */
	public boolean isSelectAllResultsAvailable() {
		return selectAllResultsAvailable;
	}

	/**
	 * Specifies whether the maximum results per page selector has an item to
	 * choose all available entries or not. Default is 'true'.
	 * 
	 * @param selectAllResultsAvailable
	 *            {@code true} or {@code false}
	 * @return the page control factory
	 */
	public PageControlFactory setSelectAllResultsAvailable(boolean selectAllResultsAvailable) {
		this.selectAllResultsAvailable = selectAllResultsAvailable;
		return this;
	}

	/**
	 * Returns the maximum numbers of pagination buttons. Default is '7'.
	 * 
	 * @return the maximum numbers of pagination buttons
	 */
	public int getMaxPaginationButtons() {
		return maxPaginationButtons;
	}

	/**
	 * Sets the maximum numbers of pagination buttons. Default is '7'.
	 * 
	 * @param maxPaginationButtons
	 *            the maximum numbers of pagination buttons
	 * @return the page control factory
	 */
	public PageControlFactory setMaxPaginationButtons(int maxPaginationButtons) {
		this.maxPaginationButtons = maxPaginationButtons;
		return this;
	}

	/**
	 * Specifies whether a query field should be displayed on the page or not.
	 * Default is 'true'.
	 * 
	 * @return {@code true} if a query field should be displayed on the page,
	 *         otherwise {@code false}
	 */
	public boolean isQuerySupported() {
		return isQuerySupported;
	}

	/**
	 * Specifies whether a query field should be displayed on the page or not.
	 * Default is 'true'.
	 * 
	 * @param isQuerySupported
	 *            {@code true} if a query field should be displayed on the page,
	 *            otherwise {@code false}
	 * @return the page control factory
	 */
	public PageControlFactory setQuerySupported(boolean isQuerySupported) {
		this.isQuerySupported = isQuerySupported;
		return this;
	}

	/**
	 * Creates a new {@link PageControlDto} from the given page.<br/>
	 * The page URL must be the plain URL (with no page control query
	 * parameters), e. g.: http://example.org/myapp/mypage.html<br/>
	 * The query is the value of the query field of the page and is optional.
	 * <br/>
	 * If the locale is not present, the default locale will be used.
	 * 
	 * @param page
	 *            the page
	 * @param pageUrl
	 *            the plain page URL
	 * @param query
	 *            the query value
	 * @param locale
	 *            the locale
	 * @return the created page control
	 */
	public abstract PageControlDto newPageControl(PageDto page, String pageUrl, String query, Locale locale);

}
