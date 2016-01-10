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
import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageControlDto;
import org.bremersee.pagebuilder.model.PageRequestLinkDto;
import org.bremersee.pagebuilder.model.PageSizeSelectorOptionDto;
import org.bremersee.pagebuilder.model.PaginationDto;
import org.bremersee.utils.WebUtils;

/**
 * <p>
 * The default page control factory.
 * </p>
 * 
 * @author Christian Bremer
 */
class DefaultPageControlFactory extends PageControlFactory {

    @Override
    public <E> PageControlDto newPageControl(Page<E> page, String pageUrl, Locale locale) {

        Validate.notNull(page, "page must not be null");

        if (pageUrl == null) {
            pageUrl = "";
        }

        if (locale == null) {
            locale = Locale.getDefault();
        }

        if (page.getEntries().size() == 0 && page.getPageRequest().getPageNumber() > 0) {
            throw new IllegalArgumentException(
                    "Building page control failed, because there are no entries and page number is greater than 0.");
        }

        PageControlDto pageControl = new PageControlDto();

        pageControl.setPage(PageBuilderUtils.createPageDto(page, null));

        pageControl.setPageNumberParamName(getPageNumberParamName());
        pageControl.setPageSizeParamName(getPageSizeParamName());

        pageControl.setComparatorParamName(getComparatorParamName());
        pageControl.setComparatorParamValue(
                getComparatorItemTransformer().toString(page.getPageRequest().getComparatorItem(), false, null));

        pageControl.setQueryParamName(getQueryParamName());
        pageControl.setQuerySupported(isQuerySupported());

        for (int pageNumber = 0; pageNumber < page.getTotalPages(); pageNumber++) {
            PageRequestLinkDto pageRequestLink = new PageRequestLinkDto();
            pageRequestLink.setActive(pageNumber == page.getPageRequest().getPageNumber());
            pageRequestLink.setPageNumber(pageNumber);
            pageRequestLink.setUrl(buildUrl(pageUrl, pageNumber, page.getPageRequest().getPageSize(),
                    page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery()));
            pageControl.getPageRequestLinks().add(pageRequestLink);
        }

        pageControl
                .setPageSizeSelectorOptions(buildPageSizeSelectorOptions(page.getPageRequest().getPageSize(), locale));

        pageControl.setPagination(buildPagination(page, pageControl.getPageRequestLinks(), pageUrl));

        return pageControl;
    }

    private String buildUrl(String url, int pageNo, int pageSize, ComparatorItem comparatorItem, String query) {

        String newUrl = WebUtils.addUrlParameter(url, getPageNumberParamName(), Integer.toString(pageNo));
        newUrl = WebUtils.addUrlParameter(newUrl, getPageSizeParamName(), Integer.toString(pageSize));
        String comparatorParamValue = getComparatorItemTransformer().toString(comparatorItem, false, null);
        if (StringUtils.isNotBlank(comparatorParamValue)) {
            newUrl = WebUtils.addUrlParameter(newUrl, getComparatorParamName(), comparatorParamValue);
        }
        if (StringUtils.isNotBlank(query)) {
            newUrl = WebUtils.addUrlParameter(newUrl, getQueryParamName(), query);
        }
        return newUrl;
    }

    private <E> PaginationDto buildPagination(Page<E> page, List<PageRequestLinkDto> pageRequestLinks, String pageUrl) {

        Validate.notEmpty(pageUrl, "pageUrl must not be empty");

        int maxPaginationLinks = getMaxPaginationLinks();
        if (maxPaginationLinks < 1) {
            maxPaginationLinks = 1;
        }

        if (page.getTotalPages() < maxPaginationLinks) {
            maxPaginationLinks = page.getTotalPages();
        }

        PaginationDto pagination = new PaginationDto();
        pagination.setMaxPaginationLinks(maxPaginationLinks);

        int pageNumberOfFirstButton;
        if (maxPaginationLinks % 2 == 0) {
            pageNumberOfFirstButton = page.getPageRequest().getPageNumber() - maxPaginationLinks / 2 + 1;
        } else {
            int middle = Double.valueOf(Math.floor(maxPaginationLinks / 2.)).intValue();
            pageNumberOfFirstButton = page.getPageRequest().getPageNumber() - middle;
        }
        if (pageNumberOfFirstButton < 0) {
            pageNumberOfFirstButton = 0;
        }
        for (int pageNumber = pageNumberOfFirstButton; pageNumber < pageNumberOfFirstButton + maxPaginationLinks
                && pageNumber < page.getTotalPages(); pageNumber++) {

            pagination.getLinks().add(pageRequestLinks.get(pageNumber));
        }

        while (page.getTotalPages() >= maxPaginationLinks && pageNumberOfFirstButton - 1 >= 0
                && pagination.getLinks().size() < maxPaginationLinks) {

            pageNumberOfFirstButton = pageNumberOfFirstButton - 1;
            pagination.getLinks().add(0, pageRequestLinks.get(pageNumberOfFirstButton));
        }

        boolean firstPageDisabled = page.getPageRequest().getPageNumber() == 0;
        String firstPageUrl = firstPageDisabled ? "#"
                : buildUrl(pageUrl, 0, page.getPageRequest().getPageSize(), page.getPageRequest().getComparatorItem(),
                        page.getPageRequest().getQuery());
        PageRequestLinkDto firstPage = new PageRequestLinkDto(pageRequestLinks.get(0), !firstPageDisabled,
                firstPageUrl);
        pagination.setFirstPageLink(firstPage);

        boolean previousPageDisabled = page.getPageRequest().getPageNumber() == 0;
        String previousPageUrl = previousPageDisabled ? "#"
                : buildUrl(pageUrl, page.getPageRequest().getPageNumber() - 1, page.getPageRequest().getPageSize(),
                        page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery());
        int previousPageNumber = previousPageDisabled ? 0 : page.getPageRequest().getPageNumber() - 1;
        PageRequestLinkDto previousPage = new PageRequestLinkDto(pageRequestLinks.get(previousPageNumber),
                !previousPageDisabled, previousPageUrl);
        pagination.setPreviousPageLink(previousPage);

        boolean nextPageDisabled = page.getPageRequest().getPageNumber() == page.getTotalPages() - 1;
        String nextPageUrl = nextPageDisabled ? "#"
                : buildUrl(pageUrl, page.getPageRequest().getPageNumber() + 1, page.getPageRequest().getPageSize(),
                        page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery());
        int nextPageNumber = nextPageDisabled ? page.getTotalPages() - 1 : page.getPageRequest().getPageNumber() + 1;
        PageRequestLinkDto nextPage = new PageRequestLinkDto(pageRequestLinks.get(nextPageNumber), !nextPageDisabled,
                nextPageUrl);
        pagination.setNextPageLink(nextPage);

        boolean lastPageDisabled = page.getPageRequest().getPageNumber() == page.getTotalPages() - 1;
        String lastPageUrl = lastPageDisabled ? "#"
                : buildUrl(pageUrl, page.getTotalPages() - 1, page.getPageRequest().getPageSize(),
                        page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery());
        PageRequestLinkDto lastPage = new PageRequestLinkDto(pageRequestLinks.get(page.getTotalPages() - 1),
                !lastPageDisabled, lastPageUrl);
        pagination.setLastPageLink(lastPage);

        return pagination;
    }

    private List<PageSizeSelectorOptionDto> buildPageSizeSelectorOptions(int selectedPageSize, Locale locale) {

        final int pageSizeSelectorMinValue = getPageSizeSelectorMinValue();
        final int pageSizeSelectorMaxValue = getMaxResultsSelectorMaxValue();
        final int pageSizeSelectorStep = getPageSizeSelectorStep();

        Validate.isTrue(0 < pageSizeSelectorMinValue && pageSizeSelectorMinValue <= pageSizeSelectorMaxValue,
                "0 < pageSizeSelectorMinValue && pageSizeSelectorMinValue "
                        + "<= pageSizeSelectorMaxValue must be 'true'");

        if (pageSizeSelectorMaxValue > pageSizeSelectorMinValue) {
            Validate.isTrue(pageSizeSelectorStep > 0, "pageSizeSelectorStep > 0 must be 'true'");
        }

        Validate.isTrue(selectedPageSize > 0, "selectedMaxResults > 0 must be 'true'");

        if (locale == null) {
            locale = Locale.getDefault();
        }

        boolean selctedValueAdded = false;
        TreeSet<PageSizeSelectorOptionDto> options = new TreeSet<>();
        for (int i = pageSizeSelectorMinValue; i <= pageSizeSelectorMaxValue; i = i + pageSizeSelectorStep) {
            if (i == selectedPageSize) {
                options.add(new PageSizeSelectorOptionDto(i, Integer.valueOf(i).toString(), true));
                selctedValueAdded = true;
            } else {
                options.add(new PageSizeSelectorOptionDto(i, Integer.valueOf(i).toString(), false));
            }
        }

        if (isSelectAllEntriesAvailable()) {

            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME, locale);
            String displayedValue;
            if (resourceBundle.containsKey("pageBuilderFactory.pageSizeSelector.max")) {
                displayedValue = resourceBundle.getString("pageBuilderFactory.pageSizeSelector.max");
            } else {
                displayedValue = "Max";
            }

            if (Integer.MAX_VALUE == selectedPageSize) {
                options.add(new PageSizeSelectorOptionDto(Integer.MAX_VALUE, displayedValue, true));
                selctedValueAdded = true;
            } else {
                options.add(new PageSizeSelectorOptionDto(Integer.MAX_VALUE, displayedValue, false));
            }
        }
        if (!selctedValueAdded) {
            options.add(new PageSizeSelectorOptionDto(selectedPageSize, Integer.valueOf(selectedPageSize).toString(),
                    true));
        }
        return Collections.unmodifiableList(new ArrayList<PageSizeSelectorOptionDto>(options));
    }

}
