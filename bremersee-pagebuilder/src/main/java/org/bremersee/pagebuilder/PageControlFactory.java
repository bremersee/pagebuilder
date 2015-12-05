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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.bremersee.comparator.ComparatorItemTransformer;
import org.bremersee.comparator.ComparatorItemTransformerImpl;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.PageControl;
import org.bremersee.pagebuilder.model.PageControl.MaxResultsSelectorOption;
import org.bremersee.pagebuilder.model.PageControl.Pagination;
import org.bremersee.pagebuilder.model.PageControl.PaginationButton;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.utils.WebUtils;

/**
 * @author Christian Bremer
 */
public abstract class PageControlFactory {

    private static final String RESOURCE_BUNDLE_BASE_NAME = "bremersee-pagebuilder-i18n";
    
	public static final int DEFAULT_MAX_PAGINATION_BUTTONS = 7;
	
	public static final int DEFAULT_MAX_RESULTS_MIN_VALUE = 10;
	
	public static final int DEFAULT_MAX_RESULTS_MAX_VALUE = 100;
	
	public static final int DEFAULT_MAX_RESULTS_SELECTOR_STEP = 10;
	
	public static final boolean DEFAULT_SELECT_ALL_RESULTS_AVAILABLE = true;
	
	public static PageControlFactory newInstance() {
		return new DefaultFactory();
	}
	
	public static PageControlFactory newInstance(String factoryClassName) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		return (PageControlFactory) Class.forName(factoryClassName).newInstance();
	}
	
	public static PageControlFactory newInstance(String factoryClassName, ClassLoader classLoader) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
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
    
	public ComparatorItemTransformer getComparatorItemTransformer() {
        return comparatorItemTransformer;
    }

    public PageControlFactory setComparatorItemTransformer(ComparatorItemTransformer comparatorItemTransformer) {
        if (comparatorItemTransformer != null) {
            this.comparatorItemTransformer = comparatorItemTransformer;
        }
        return this;
    }

    public String getQueryParamName() {
		return queryParamName;
	}

	public PageControlFactory setQueryParamName(String queryParamName) {
	    if (StringUtils.isNotBlank(queryParamName)) {
	        this.queryParamName = queryParamName;
	    }
        return this;
	}

	public String getMaxResultsParamName() {
		return maxResultsParamName;
	}

	public PageControlFactory setMaxResultsParamName(String maxResultsParamName) {
	    if (StringUtils.isNotBlank(maxResultsParamName)) {
	        this.maxResultsParamName = maxResultsParamName;
	    }
        return this;
	}

	public String getPageNumberParamName() {
		return pageNumberParamName;
	}

	public PageControlFactory setPageNumberParamName(String pageNumberParamName) {
	    if (StringUtils.isNotBlank(pageNumberParamName)) {
	        this.pageNumberParamName = pageNumberParamName;
	    }
        return this;
	}

	public String getComparatorParamName() {
		return comparatorParamName;
	}

	public PageControlFactory setComparatorParamName(String comparatorParamName) {
	    if (StringUtils.isNotBlank(comparatorParamName)) {
	        this.comparatorParamName = comparatorParamName;
	    }
        return this;
	}

	public int getMaxResultsSelectorMinValue() {
        return maxResultsSelectorMinValue;
    }

    public PageControlFactory setMaxResultsSelectorMinValue(int maxResultsSelectorMinValue) {
        this.maxResultsSelectorMinValue = maxResultsSelectorMinValue;
        return this;
    }

    public int getMaxResultsSelectorMaxValue() {
        return maxResultsSelectorMaxValue;
    }

    public PageControlFactory setMaxResultsSelectorMaxValue(int maxResultsSelectorMaxValue) {
        this.maxResultsSelectorMaxValue = maxResultsSelectorMaxValue;
        return this;
    }

    public int getMaxResultsSelectorStep() {
        return maxResultsSelectorStep;
    }

    public PageControlFactory setMaxResultsSelectorStep(int maxResultsSelectorStep) {
        this.maxResultsSelectorStep = maxResultsSelectorStep;
        return this;
    }

    public boolean isSelectAllResultsAvailable() {
        return selectAllResultsAvailable;
    }

    public PageControlFactory setSelectAllResultsAvailable(boolean selectAllResultsAvailable) {
        this.selectAllResultsAvailable = selectAllResultsAvailable;
        return this;
    }

    public int getMaxPaginationButtons() {
        return maxPaginationButtons;
    }

    public PageControlFactory setMaxPaginationButtons(int maxPaginationButtons) {
        this.maxPaginationButtons = maxPaginationButtons;
        return this;
    }

    public boolean isQuerySupported() {
        return isQuerySupported;
    }

    public PageControlFactory setQuerySupported(boolean isQuerySupported) {
        this.isQuerySupported = isQuerySupported;
        return this;
    }

    public PageControl newPageControl(
			PageDto page, 
			String pageUrl, 
			String query, 
			Locale locale) {
		
		return buildPageControl(page, pageUrl, query, locale);
	}
	
	protected PageControl buildPageControl(
            PageDto page, 
            String pageUrl, 
            String query, 
            Locale locale) {

		Validate.notNull(page, "page must not be null");
		if (page.getMaxResults() == null || page.getMaxResults() < 1) {
		    page.setMaxResults(Integer.MAX_VALUE);
		}
		
		if (locale == null) {
			locale = Locale.getDefault();
		}
		
		PageControl pageControl = new PageControl();
		
		pageControl.setComparatorParamName(comparatorParamName);
		pageControl.setMaxResultsParamName(maxResultsParamName);
		pageControl.setPageNumberParamName(pageNumberParamName);
		pageControl.setQueryParamName(queryParamName);
		
		pageControl.setPage(page);
		
		pageControl.setComparatorParamValue(comparatorItemTransformer.toString(page.getComparatorItem(), false, null));
		
		pageControl.setMaxResultsSelectorOptions(buildMaxResultsSelectorOptions(
		        page.getMaxResults(), 
		        locale));
		
		pageControl.setPagination(buildPagination(page, pageUrl, query));
		
		pageControl.setQuerySupported(isQuerySupported);
		pageControl.setQuery(query);
		
		return pageControl;
	}

	protected String buildUrl(String url, int pageNo, int maxResults, ComparatorItem comparatorItem, String query) {
		
		String newUrl = WebUtils.addUrlParameter(url, pageNumberParamName, Integer.valueOf(pageNo).toString());
		newUrl = WebUtils.addUrlParameter(newUrl, maxResultsParamName, Integer.valueOf(maxResults).toString());
		String comparatorParamValue = comparatorItemTransformer.toString(comparatorItem, false, null);
		if (StringUtils.isNotBlank(comparatorParamValue)) {
			newUrl = WebUtils.addUrlParameter(newUrl, comparatorParamName, comparatorParamValue);
		}
		if (StringUtils.isNotBlank(query)) {
			newUrl = WebUtils.addUrlParameter(newUrl, queryParamName, query);
		}
		return newUrl;
	}
	
	protected Pagination buildPagination(
			PageDto page, 
			String pageUrl, 
			String query) {
		
		if (maxPaginationButtons < 1) {
			maxPaginationButtons = 1;
		}
		
		Validate.notEmpty(pageUrl, "pageUrl must not be empty");
		Validate.notNull(page.getPageSize(), "page.getPageSize() must not be null");
		Validate.notNull(page.getCurrentPage(), "page.getCurrentPage() must not be null");
		
		if (page.getPageSize() < maxPaginationButtons) {
			maxPaginationButtons = page.getPageSize();
		}
		
		Pagination pagination = new Pagination();
		pagination.setMaxPaginationButtons(maxPaginationButtons);
		
		for (int pageNumber = 0; pageNumber < page.getPageSize(); pageNumber++) {
			
			PaginationButton paginationEntry = new PaginationButton();
			paginationEntry.setActive(pageNumber == page.getCurrentPage());
			paginationEntry.setPageNumber(pageNumber);
			paginationEntry.setUrl(buildUrl(pageUrl, pageNumber, page.getMaxResults(), page.getComparatorItem(), query));
			
			pagination.getAllButtons().add(paginationEntry);
		}
		
		int pageNumberOfFirstButton;
		if (maxPaginationButtons % 2 == 0) {
			pageNumberOfFirstButton = page.getCurrentPage() - maxPaginationButtons / 2 + 1;
		} else {
			int middle = Double.valueOf(Math.floor(maxPaginationButtons / 2.)).intValue();
			pageNumberOfFirstButton = page.getCurrentPage() - middle;
		}
		if (pageNumberOfFirstButton < 0) {
			pageNumberOfFirstButton = 0;
		}
		for (int pageNumber = pageNumberOfFirstButton; pageNumber < pageNumberOfFirstButton + maxPaginationButtons && pageNumber < page.getPageSize(); pageNumber++) {
			pagination.getButtons().add(pagination.getAllButtons().get(pageNumber));
		}
		
		boolean firstPageDisabled = page.getCurrentPage() == 0;
		String firstPageUrl = firstPageDisabled ? "#" : buildUrl(pageUrl, 0, page.getMaxResults(), page.getComparatorItem(), query);
		PaginationButton firstPage = new PaginationButton(0, !firstPageDisabled, firstPageUrl);
		pagination.setFirstPageButton(firstPage);
		
		boolean previousPageDisabled = page.getCurrentPage() == 0;
		String previousPageUrl = previousPageDisabled ? "#" : buildUrl(pageUrl, page.getCurrentPage()-1, page.getMaxResults(), page.getComparatorItem(), query);
		PaginationButton previousPage = new PaginationButton(page.getCurrentPage()-1, !previousPageDisabled, previousPageUrl);
		pagination.setPreviousPageButton(previousPage);
		
		boolean nextPageDisabled = page.getCurrentPage() == page.getPageSize()-1;
		String nextPageUrl = nextPageDisabled ? "#" : buildUrl(pageUrl, page.getCurrentPage()+1, page.getMaxResults(), page.getComparatorItem(), query);
		PaginationButton nextPage = new PaginationButton(page.getCurrentPage()+1, !nextPageDisabled, nextPageUrl);
		pagination.setNextPageButton(nextPage);
		
		boolean lastPageDisabled = page.getCurrentPage() == page.getPageSize()-1;
		String lastPageUrl = lastPageDisabled ? "#" : buildUrl(pageUrl, page.getPageSize()-1, page.getMaxResults(), page.getComparatorItem(), query);
		PaginationButton lastPage = new PaginationButton(page.getPageSize()-1, !lastPageDisabled, lastPageUrl);
		pagination.setLastPageButton(lastPage);
		
		return pagination;
	}

	protected List<MaxResultsSelectorOption> buildMaxResultsSelectorOptions(
			int selectedMaxResults, 
			Locale locale) {
		
		Validate.isTrue(0 < maxResultsSelectorMinValue && maxResultsSelectorMinValue <= maxResultsSelectorMaxValue, 
				"0 < maxResultsSelectorMinValue && maxResultsSelectorMinValue <= maxResultsSelectorMaxValue must be 'true'");
		
		if (maxResultsSelectorMaxValue > maxResultsSelectorMinValue) {
			Validate.isTrue(maxResultsSelectorStep > 0, "maxResultsSelectorStep > 0 must be 'true'");
		}
		
		Validate.isTrue(selectedMaxResults > 0, "selectedMaxResults > 0 must be 'true'");
		
		if (locale == null) {
			locale = Locale.getDefault();
		}
		
		boolean selctedValueAdded = false;
		TreeSet<MaxResultsSelectorOption> options = new TreeSet<MaxResultsSelectorOption>();
		for (int i = maxResultsSelectorMinValue; i <= maxResultsSelectorMaxValue; i = i + maxResultsSelectorStep) {
			if (i == selectedMaxResults) {
				options.add(new MaxResultsSelectorOption(i, Integer.valueOf(i).toString(), true));
				selctedValueAdded = true;
			} else {
				options.add(new MaxResultsSelectorOption(i, Integer.valueOf(i).toString(), false));
			}
		}
		
		if (selectAllResultsAvailable) {
			
			ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, locale); 
			String displayedValue;
			if (resourceBundle.containsKey("pageBuilderFactory.maxResultsSelector.max")) {
				displayedValue = resourceBundle.getString("pageBuilderFactory.maxResultsSelector.max");
			} else {
				displayedValue = "Max";
			}
			
			if (Integer.MAX_VALUE == selectedMaxResults) {
				options.add(new MaxResultsSelectorOption(Integer.MAX_VALUE, displayedValue, true));
				selctedValueAdded = true;
			} else {
				options.add(new MaxResultsSelectorOption(Integer.MAX_VALUE, displayedValue, false));
			}
		}
		if (!selctedValueAdded) {
			options.add(new MaxResultsSelectorOption(selectedMaxResults, Integer.valueOf(selectedMaxResults).toString(), true));
		}
		return Collections.unmodifiableList(new ArrayList<MaxResultsSelectorOption>(options));
	}
	
	/**
	 * <p>
	 * The default page control factory.
	 * </p>
	 * 
	 * @author Christian Bremer
	 */
	private static class DefaultFactory extends PageControlFactory {
	}
	
}
