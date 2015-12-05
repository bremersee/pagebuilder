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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bremersee.comparator.ObjectComparator;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.PageDto;

/**
 * <p>
 * Default page builder implementation.
 * </p>
 * 
 * @author Christian Bremer
 */
public class PageBuilderImpl implements PageBuilder {
	
    /**
     * An optional filter.
     */
	private PageBuilderFilter pageBuilderFilter;
	
	/**
	 * Return the filter for this page builder or {@code null} if no filter is present.
	 * @return the filter or {@code null}
	 */
	protected PageBuilderFilter getPageBuilderFilter() {
        return pageBuilderFilter;
    }

	/**
	 * Set the filter for this page builder.
	 * @param pageBuilderFilter the filter for this page builder
	 */
    public void setPageBuilderFilter(PageBuilderFilter pageBuilderFilter) {
        this.pageBuilderFilter = pageBuilderFilter;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
	public String toString() {
		return getClass().getName();
	}
	
	/* (non-Javadoc)
	 * @see org.bremersee.pagebuilder.PageBuilder#buildPage(java.util.Collection, java.lang.Integer, java.lang.Integer, java.lang.Integer, org.bremersee.comparator.model.ComparatorItem)
	 */
	@Override
	public PageDto buildPage(Collection<? extends Object> entries,
			Integer firstResult, Integer maxResults, Integer totalSize, ComparatorItem comparatorItem) {
		
		PageDto page = new PageDto();
		page.setComparatorItem(comparatorItem);
		page.setFirstResult(firstResult);
		page.setMaxResults(maxResults);
		page.setTotalSize(totalSize);
		if (entries != null) {
			page.getEntries().addAll(entries);
		}
		return page;
	}
	
	/* (non-Javadoc)
	 * @see org.bremersee.pagebuilder.PageBuilder#buildFilteredPage(java.util.Collection, java.lang.Integer, java.lang.Integer, org.bremersee.comparator.ObjectComparator, java.lang.Object)
	 */
	@Override
	public PageDto buildFilteredPage(Collection<? extends Object> entries,
			Integer firstResult, Integer maxResults,
			ObjectComparator objectComparator, Object permission) {
		
		final int _firstResult = firstResult == null || firstResult < 0 ? 0 : firstResult;
		final int _maxResults = maxResults == null || maxResults < 0 ? Integer.MAX_VALUE : maxResults;
		
		PageDto page = new PageDto();
		
		int n = 0;
		if (entries != null) {
			
			if (objectComparator != null) {
				if (!(entries instanceof List)) {
					entries = new ArrayList<Object>(entries);
				}
				Collections.sort((List<?>)entries, objectComparator);
				page.setComparatorItem(objectComparator.getComparatorItem());
			}
			
			for (final Object entry : entries) {
				
				if (permission == null || accept(entry, permission)) {
					
					if (_firstResult <= n && page.getEntries().size() < _maxResults) {
						page.getEntries().add(entry);
					}
					n++;
				}
			}
		}
		
		page.setFirstResult(_firstResult);
		page.setMaxResults(_maxResults);
		page.setTotalSize(n);
		return page;
	}
	
	private boolean accept(Object entry, Object filter) {
		if (pageBuilderFilter != null) {
			return pageBuilderFilter.accept(entry, filter);
		}
		return true;
	}
}
