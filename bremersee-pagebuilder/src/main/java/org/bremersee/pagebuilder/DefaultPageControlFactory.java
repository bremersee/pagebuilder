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
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.PageControlDto;
import org.bremersee.pagebuilder.model.PageControlDto.MaxResultsSelectorOption;
import org.bremersee.pagebuilder.model.PageControlDto.Pagination;
import org.bremersee.pagebuilder.model.PageControlDto.PaginationButton;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.utils.WebUtils;

/**
 * <p>
 * The default page control factory.
 * </p>
 * 
 * @author Christian Bremer
 */
class DefaultPageControlFactory extends PageControlFactory {

    public PageControlDto newPageControl(
			PageDto page, 
			String pageUrl, 
			String query, 
			Locale locale) {
		
		return buildPageControl(page, pageUrl, query, locale);
	}
	
	protected PageControlDto buildPageControl(
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
		
		PageControlDto pageControl = new PageControlDto();
		
		pageControl.setComparatorParamName(getComparatorParamName());
		pageControl.setMaxResultsParamName(getMaxResultsParamName());
		pageControl.setPageNumberParamName(getPageNumberParamName());
		pageControl.setQueryParamName(getQueryParamName());
		
		pageControl.setPage(page);
		
		pageControl.setComparatorParamValue(getComparatorItemTransformer().toString(page.getComparatorItem(), false, null));
		
		pageControl.setMaxResultsSelectorOptions(buildMaxResultsSelectorOptions(
		        page.getMaxResults(), 
		        locale));
		
		pageControl.setPagination(buildPagination(page, pageUrl, query));
		
		pageControl.setQuerySupported(isQuerySupported());
		pageControl.setQuery(query);
		
		return pageControl;
	}

	protected String buildUrl(String url, int pageNo, int maxResults, ComparatorItem comparatorItem, String query) {
		
		String newUrl = WebUtils.addUrlParameter(url, getPageNumberParamName(), Integer.valueOf(pageNo).toString());
		newUrl = WebUtils.addUrlParameter(newUrl, getMaxResultsParamName(), Integer.valueOf(maxResults).toString());
		String comparatorParamValue = getComparatorItemTransformer().toString(comparatorItem, false, null);
		if (StringUtils.isNotBlank(comparatorParamValue)) {
			newUrl = WebUtils.addUrlParameter(newUrl, getComparatorParamName(), comparatorParamValue);
		}
		if (StringUtils.isNotBlank(query)) {
			newUrl = WebUtils.addUrlParameter(newUrl, getQueryParamName(), query);
		}
		return newUrl;
	}
	
	protected Pagination buildPagination(
			PageDto page, 
			String pageUrl, 
			String query) {
		
		int maxPaginationButtons = getMaxPaginationButtons();
		
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
		
		final int maxResultsSelectorMinValue = getMaxResultsSelectorMinValue();
		final int maxResultsSelectorMaxValue = getMaxResultsSelectorMaxValue();
		final int maxResultsSelectorStep = getMaxResultsSelectorStep();
		
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
		
		if (isSelectAllResultsAvailable()) {
			
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
	
}
