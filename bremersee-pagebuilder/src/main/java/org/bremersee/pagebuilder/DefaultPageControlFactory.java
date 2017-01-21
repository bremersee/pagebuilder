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
import org.apache.commons.lang3.Validate;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.*;
import org.bremersee.utils.WebUtils;

import java.util.*;

/**
 * <p>
 * The default page control factory.
 * </p>
 *
 * @author Christian Bremer
 */
class DefaultPageControlFactory extends PageControlFactory {

    @Override
    public <E> PageControlDto newPageControl(final Page<E> page, final String pageUrl, final Locale locale) {

        Validate.notNull(page, "page must not be null");

        final String pUrl = StringUtils.isBlank(pageUrl) ? "" : pageUrl;

        if (page.getEntries().isEmpty() && page.getPageRequest().getPageNumber() > 0) {
            throw new IllegalArgumentException(
                    "Building page control failed, because there are no entries and page number is greater than 0.");
        }

        final PageControlDto pageControl = new PageControlDto();

        pageControl.setPage(PageBuilderUtils.createPageDto(page, null));

        pageControl.setPageNumberParamName(getPageNumberParamName());
        pageControl.setPageSizeParamName(getPageSizeParamName());

        pageControl.setComparatorParamName(getComparatorParamName());
        pageControl.setComparatorParamValue(getComparatorItemTransformer().toString(
                page.getPageRequest().getComparatorItem(), false, null));

        pageControl.setQueryParamName(getQueryParamName());
        pageControl.setQuerySupported(isQuerySupported());

        for (int pageNumber = 0; pageNumber < page.getTotalPages(); pageNumber++) {
            PageRequestLinkDto pageRequestLink = new PageRequestLinkDto();
            pageRequestLink.setActive(pageNumber == page.getPageRequest().getPageNumber());
            pageRequestLink.setPageNumber(pageNumber);
            pageRequestLink.setUrl(buildUrl(pUrl, pageNumber, page.getPageRequest().getPageSize(),
                    page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery()));
            pageControl.getPageRequestLinks().add(pageRequestLink);
        }

        pageControl.setPageSizeSelectorOptions(buildPageSizeSelectorOptions(
                page.getPageRequest().getPageSize(), locale == null ? Locale.getDefault() : locale));

        pageControl.setPagination(buildPagination(page, pageControl.getPageRequestLinks(), pUrl));

        return pageControl;
    }

    private String buildUrl(final String url, final int pageNo, final int pageSize,
                            final ComparatorItem comparatorItem, final String query) {

        String newUrl = WebUtils.addUrlParameter(url, getPageNumberParamName(), Integer.toString(pageNo));
        newUrl = WebUtils.addUrlParameter(newUrl, getPageSizeParamName(), Integer.toString(pageSize));
        final String comparatorParamValue = getComparatorItemTransformer().toString(
                comparatorItem, false, null);
        if (StringUtils.isNotBlank(comparatorParamValue)) {
            newUrl = WebUtils.addUrlParameter(newUrl, getComparatorParamName(), comparatorParamValue);
        }
        if (StringUtils.isNotBlank(query)) {
            newUrl = WebUtils.addUrlParameter(newUrl, getQueryParamName(), query);
        }
        return newUrl;
    }

    private <E> PaginationDto buildPagination(final Page<E> page, // NOSONAR
                                              final List<PageRequestLinkDto> pageRequestLinks,
                                              final String pageUrl) {

        Validate.notEmpty(pageUrl, "pageUrl must not be empty");

        int maxPaginationLinks = getMaxPaginationLinks();
        if (maxPaginationLinks < 1) {
            maxPaginationLinks = 1;
        }

        if (page.getTotalPages() < maxPaginationLinks) {
            maxPaginationLinks = page.getTotalPages();
        }

        final PaginationDto pagination = new PaginationDto();
        pagination.setMaxPaginationLinks(maxPaginationLinks);

        int pageNumberOfFirstButton;
        if (maxPaginationLinks % 2 == 0) {
            pageNumberOfFirstButton = page.getPageRequest().getPageNumber() - maxPaginationLinks / 2 + 1;
        } else {
            int middle = (int) Math.floor(maxPaginationLinks / 2.);
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

        final boolean firstPageDisabled = page.getPageRequest().getPageNumber() == 0;
        final String firstPageUrl = firstPageDisabled ? "#"
                : buildUrl(pageUrl, 0, page.getPageRequest().getPageSize(), page.getPageRequest().getComparatorItem(),
                page.getPageRequest().getQuery());
        final PageRequestLinkDto firstPage = new PageRequestLinkDto(pageRequestLinks.get(0), !firstPageDisabled,
                firstPageUrl);
        pagination.setFirstPageLink(firstPage);

        final boolean previousPageDisabled = page.getPageRequest().getPageNumber() == 0;
        final String previousPageUrl = previousPageDisabled ? "#"
                : buildUrl(pageUrl, page.getPageRequest().getPageNumber() - 1, page.getPageRequest().getPageSize(),
                page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery());
        final int previousPageNumber = previousPageDisabled ? 0 : page.getPageRequest().getPageNumber() - 1;
        final PageRequestLinkDto previousPage = new PageRequestLinkDto(pageRequestLinks.get(previousPageNumber),
                !previousPageDisabled, previousPageUrl);
        pagination.setPreviousPageLink(previousPage);

        final boolean nextPageDisabled = page.getPageRequest().getPageNumber() == page.getTotalPages() - 1;
        final String nextPageUrl = nextPageDisabled ? "#"
                : buildUrl(pageUrl, page.getPageRequest().getPageNumber() + 1, page.getPageRequest().getPageSize(),
                page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery());
        final int nextPageNumber = nextPageDisabled ? page.getTotalPages() - 1 : page.getPageRequest().getPageNumber() + 1;
        final PageRequestLinkDto nextPage = new PageRequestLinkDto(pageRequestLinks.get(nextPageNumber), !nextPageDisabled,
                nextPageUrl);
        pagination.setNextPageLink(nextPage);

        final boolean lastPageDisabled = page.getPageRequest().getPageNumber() == page.getTotalPages() - 1;
        final String lastPageUrl = lastPageDisabled ? "#"
                : buildUrl(pageUrl, page.getTotalPages() - 1, page.getPageRequest().getPageSize(),
                page.getPageRequest().getComparatorItem(), page.getPageRequest().getQuery());
        final PageRequestLinkDto lastPage = new PageRequestLinkDto(pageRequestLinks.get(page.getTotalPages() - 1),
                !lastPageDisabled, lastPageUrl);
        pagination.setLastPageLink(lastPage);

        return pagination;
    }

    private List<PageSizeSelectorOptionDto> buildPageSizeSelectorOptions(final int selectedPageSize, // NOSONAR
                                                                         final Locale locale) {

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

        boolean selctedValueAdded = false;
        TreeSet<PageSizeSelectorOptionDto> options = new TreeSet<>();
        for (int i = pageSizeSelectorMinValue; i <= pageSizeSelectorMaxValue; i = i + pageSizeSelectorStep) {
            if (i == selectedPageSize) {
                options.add(new PageSizeSelectorOptionDto(i, Integer.valueOf(i).toString(), true)); // NOSONAR
                selctedValueAdded = true;
            } else {
                options.add(new PageSizeSelectorOptionDto(i, Integer.valueOf(i).toString(), false)); // NOSONAR
            }
        }

        if (isSelectAllEntriesAvailable()) {

            ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_BASE_NAME,
                    locale == null ? Locale.getDefault() : locale);
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
            options.add(new PageSizeSelectorOptionDto(selectedPageSize,
                    Integer.valueOf(selectedPageSize).toString(), // NOSONAR
                    true));
        }
        return Collections.unmodifiableList(new ArrayList<>(options));
    }

}
