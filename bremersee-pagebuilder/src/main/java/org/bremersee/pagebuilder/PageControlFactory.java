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

import org.apache.commons.lang3.StringUtils;
import org.bremersee.comparator.ComparatorItemTransformer;
import org.bremersee.comparator.ComparatorItemTransformerImpl;
import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageControlDto;

import java.util.Locale;

/**
 * <p>
 * Factory to create {@link PageControlDto}s.
 * </p>
 * <p>
 * A {@link PageControlDto} can be used to display a {@link Page} on a web
 * site.
 * </p>
 *
 * @author Christian Bremer
 */
@SuppressWarnings({"WeakerAccess", "unused", "SameParameterValue"})
public abstract class PageControlFactory {

    /**
     * The base name of the resource bundle.
     */
    protected static final String RESOURCE_BUNDLE_BASE_NAME = "bremersee-pagebuilder-i18n";

    /**
     * Default value.
     */
    public static final int DEFAULT_MAX_PAGINATION_LINKS = 7;

    /**
     * Default value.
     */
    public static final int DEFAULT_PAGE_SIZE_SELECTOR_MIN_VALUE = 10;

    /**
     * Default value.
     */
    public static final int DEFAULT_PAGE_SIZE_SELECTOR_MAX_VALUE = 100;

    /**
     * Default value.
     */
    public static final int DEFAULT_PAGE_SIZE_SELECTOR_STEP = 10;

    /**
     * Default value.
     */
    public static final boolean DEFAULT_SELECT_ALL_ENTRIES_AVAILABLE = true;

    private ComparatorItemTransformer comparatorItemTransformer = new ComparatorItemTransformerImpl();

    private String queryParamName = "q";

    private String pageSizeParamName = "s";

    private String pageNumberParamName = "p";

    private String comparatorParamName = "c";

    private int pageSizeSelectorMinValue = DEFAULT_PAGE_SIZE_SELECTOR_MIN_VALUE;

    private int pageSizeSelectorMaxValue = DEFAULT_PAGE_SIZE_SELECTOR_MAX_VALUE;

    private int pageSizeSelectorStep = DEFAULT_PAGE_SIZE_SELECTOR_STEP;

    private boolean selectAllEntriesAvailable = DEFAULT_SELECT_ALL_ENTRIES_AVAILABLE;

    private int maxPaginationLinks = DEFAULT_MAX_PAGINATION_LINKS;

    private boolean isQuerySupported = true;

    /**
     * Returns a new factory instance.
     *
     * @return the new factory instance
     */
    public static PageControlFactory newInstance() {
        return new DefaultPageControlFactory();
    }

    /**
     * Returns a new factory instance of the given class.
     *
     * @param factoryClassName the class name
     * @return the new factory instance
     * @throws InstantiationException if creating of the factory fails
     * @throws IllegalAccessException if creating of the factory fails
     * @throws ClassNotFoundException if creating of the factory fails
     */
    public static PageControlFactory newInstance(final String factoryClassName) // NOSONAR
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (PageControlFactory) Class.forName(factoryClassName).newInstance();
    }

    /**
     * Returns a new factory instance of the given class.
     *
     * @param factoryClassName the class name
     * @param classLoader      the class loader to use
     * @return the new factory instance
     * @throws InstantiationException if creating of the factory fails
     * @throws IllegalAccessException if creating of the factory fails
     * @throws ClassNotFoundException if creating of the factory fails
     */
    public static PageControlFactory newInstance(final String factoryClassName, // NOSONAR
                                                 final ClassLoader classLoader)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return (PageControlFactory) Class.forName(factoryClassName, true, classLoader).newInstance();
    }

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
     * comparator item query parameter.<br>
     * Default is {@link ComparatorItemTransformerImpl}.
     *
     * @param comparatorItemTransformer the comparator item transformer
     * @return the page control factory
     */
    public PageControlFactory setComparatorItemTransformer(final ComparatorItemTransformer comparatorItemTransformer) {
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
     * @param queryParamName the name of the query parameter
     * @return the page control factory
     */
    public PageControlFactory setQueryParamName(final String queryParamName) {
        if (StringUtils.isNotBlank(queryParamName)) {
            this.queryParamName = queryParamName;
        }
        return this;
    }

    /**
     * Gets the name of the page size query parameter. Default is 's'.
     *
     * @return the name of the page size query parameter
     */
    public String getPageSizeParamName() {
        return pageSizeParamName;
    }

    /**
     * Sets the name page size query parameter. Default is 's'.
     *
     * @param pageSizeParamName the name of page size query parameter
     * @return the page control factory
     */
    public PageControlFactory setPageSizeParamName(final String pageSizeParamName) {
        if (StringUtils.isNotBlank(pageSizeParamName)) {
            this.pageSizeParamName = pageSizeParamName;
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
     * @param pageNumberParamName the name of the page number query parameter
     * @return the page control factory
     */
    public PageControlFactory setPageNumberParamName(final String pageNumberParamName) {
        if (StringUtils.isNotBlank(pageNumberParamName)) {
            this.pageNumberParamName = pageNumberParamName;
        }
        return this;
    }

    /**
     * Gets the name of the comparator query parameter. Default is 'c'.
     *
     * @return the name of the comparator query parameter
     */
    public String getComparatorParamName() {
        return comparatorParamName;
    }

    /**
     * Sets the name of the comparator query parameter. Default is 'c'.
     *
     * @param comparatorParamName the name of the comparator query parameter
     * @return the page control factory
     */
    public PageControlFactory setComparatorParamName(final String comparatorParamName) {
        if (StringUtils.isNotBlank(comparatorParamName)) {
            this.comparatorParamName = comparatorParamName;
        }
        return this;
    }

    /**
     * Gets the minimum value, that the page size selector can have. Default is
     * '10'.
     *
     * @return the minimum value
     */
    public int getPageSizeSelectorMinValue() {
        return pageSizeSelectorMinValue;
    }

    /**
     * Sets the minimum value, that the page size selector can have. Default is
     * '10'.
     *
     * @param pageSizeSelectorMinValue the minimum value
     * @return the page control factory
     */
    public PageControlFactory setPageSizeSelectorMinValue(final int pageSizeSelectorMinValue) {
        this.pageSizeSelectorMinValue = pageSizeSelectorMinValue;
        return this;
    }

    /**
     * Gets the maximum value, that the page size selector can have. Default is
     * '100'.
     *
     * @return the maximum value
     */
    public int getMaxResultsSelectorMaxValue() {
        return pageSizeSelectorMaxValue;
    }

    /**
     * Sets the maximum value, that the page size selector can have. Default is
     * '100'.
     *
     * @param pageSizeSelectorMaxValue the maximum value
     * @return the page control factory
     */
    public PageControlFactory setPageSizeSelectorMaxValue(final int pageSizeSelectorMaxValue) {
        this.pageSizeSelectorMaxValue = pageSizeSelectorMaxValue;
        return this;
    }

    /**
     * Gets the step size of the page size selector. Default is '10'.
     *
     * @return the step size of the page size selector
     */
    public int getPageSizeSelectorStep() {
        return pageSizeSelectorStep;
    }

    /**
     * Sets the step size of the page size selector. Default is '10'.
     *
     * @param pageSizeSelectorStep the step size of the page size selector
     * @return the page control factory
     */
    public PageControlFactory setPageSizeSelectorStep(final int pageSizeSelectorStep) {
        this.pageSizeSelectorStep = pageSizeSelectorStep;
        return this;
    }

    /**
     * Returns {@code true} if the page size selector has an item to choose all
     * available entries, otherwise {@code false}. Default is 'true'.
     *
     * @return {@code true} if the page size selector has an item to choose all
     * available entries, otherwise {@code false}
     */
    public boolean isSelectAllEntriesAvailable() {
        return selectAllEntriesAvailable;
    }

    /**
     * Specifies whether the maximum results per page selector has an item to
     * choose all available entries or not. Default is 'true'.
     *
     * @param selectAllEntriesAvailable {@code true} or {@code false}
     * @return the page control factory
     */
    public PageControlFactory setSelectAllEntriesAvailable(final boolean selectAllEntriesAvailable) {
        this.selectAllEntriesAvailable = selectAllEntriesAvailable;
        return this;
    }

    /**
     * Returns the maximum numbers of pagination links. Default is '7'.
     *
     * @return the maximum numbers of pagination links
     */
    public int getMaxPaginationLinks() {
        return maxPaginationLinks;
    }

    /**
     * Sets the maximum numbers of pagination links. Default is '7'.
     *
     * @param maxPaginationLinks the maximum numbers of pagination links
     * @return the page control factory
     */
    public PageControlFactory setMaxPaginationLinks(final int maxPaginationLinks) {
        this.maxPaginationLinks = maxPaginationLinks;
        return this;
    }

    /**
     * Specifies whether a query field should be displayed on the page or not.
     * Default is 'true'.
     *
     * @return {@code true} if a query field should be displayed on the page,
     * otherwise {@code false}
     */
    public boolean isQuerySupported() {
        return isQuerySupported;
    }

    /**
     * Specifies whether a query field should be displayed on the page or not.
     * Default is 'true'.
     *
     * @param isQuerySupported {@code true} if a query field should be displayed on the page,
     *                         otherwise {@code false}
     * @return the page control factory
     */
    public PageControlFactory setQuerySupported(final boolean isQuerySupported) {
        this.isQuerySupported = isQuerySupported;
        return this;
    }

    /**
     * Creates a new {@link PageControlDto} from the given page.<br>
     * The page URL must be the plain URL (with no page control query
     * parameters), e. g.: http://example.org/myapp/mypage.html<br>
     * If the locale is not present, the default locale will be used.
     *
     * @param page        the page
     * @param transformer the page entry transformer (may be {@code null})
     * @param pageUrl     the plain page URL
     * @param locale      the locale
     * @param <E>         source type of the page entries
     * @param <T>         target type of the page entries
     * @return the created page control
     */
    public <E, T> PageControlDto newPageControl(final Page<E> page, final PageEntryTransformer<T, E> transformer,
                                                final String pageUrl, final Locale locale) {
        return newPageControl(PageBuilderUtils.createPageDto(page, transformer), pageUrl, locale);
    }

    /**
     * Creates a new {@link PageControlDto} from the given page.<br>
     * The page URL must be the plain URL (with no page control query
     * parameters), e. g.: http://example.org/myapp/mypage.html<br>
     * If the locale is not present, the default locale will be used.
     *
     * @param page    the page
     * @param pageUrl the plain page URL
     * @param locale  the locale
     * @param <E>     type of the page entries
     * @return the created page control
     */
    public abstract <E> PageControlDto newPageControl(final Page<E> page, String pageUrl, final Locale locale);

}
