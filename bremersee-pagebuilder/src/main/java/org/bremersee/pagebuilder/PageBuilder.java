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

import java.util.Collection;

import org.bremersee.comparator.ObjectComparator;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.PageDto;

/**
 * <p>
 * A page builder creates a {@link PageDto} form a list of items.
 * </p>
 * <p>
 * There are two ways to create a page:
 * <ul>
 * <li>The list of items that build the page is pre-filtered.<br/>
 * The list of items is perhaps the result of a SQL query.<br/>
 * Than the page must build with
 * {@link PageBuilder#buildPage(Collection, Integer, Integer, Integer, ComparatorItem)}
 * .</li>
 * <li>The list of items that build the page is not filtered and contains all
 * available items.<br/>
 * Than the page must build with
 * {@link PageBuilder#buildFilteredPage(Collection, Integer, Integer, ObjectComparator, Object)}
 * .</li>
 * </ul>
 * </p>
 * 
 * @author Christian Bremer
 * 
 */
public interface PageBuilder {

	/**
	 * Creates a page with the specified parameters. The entries will not be
	 * filtered nor sorted.
	 * 
	 * @param entries
	 *            the entries on the page
	 * @param firstResult
	 *            the number of the first entry of all available entries
	 * @param maxResults
	 *            the maximum number of entries (
	 *            {@code size of entries <= maxResults}
	 * @param totalSize
	 *            the size of all available entries
	 * @param comparatorItem
	 *            the comparator item
	 * @return the page with specified parameters
	 */
	//@formatter:off
    PageDto buildPage(
    		Collection<? extends Object> entries, 
    		Integer firstResult, 
    		Integer maxResults, 
    		Integer totalSize,
            ComparatorItem comparatorItem);
	//@formatter:on

	/**
	 * Creates a page with the specified parameters. The entries will be
	 * filtered:
	 * <ul>
	 * <li>First sort the entries, if an object comparator is present.</li>
	 * <li>Than get all accepted items, if a filter is present.</li>
	 * <li>Add {@code maxResults} items from {@code firstResult} position to the
	 * page.</li>
	 * <li>The total size attribute of the page is the size of the specified
	 * entries.</li>
	 * </ul>
	 * 
	 * @param entries
	 *            all available entries that should be filtered by
	 *            {@code firstResult}, {@code maxResults} and {@code filter},
	 *            the total size is the size of the entries
	 * @param firstResult
	 *            the number of the first entry of the specified entries
	 * @param maxResults
	 *            the maximum number of entries (
	 *            {@code size of page <= maxResults})
	 * @param objectComparator
	 *            an object comparator (optional)
	 * @param filter
	 *            a filter to get accepted items (optional)
	 * @return the page with filtered entries
	 */
	//@formatter:off
    PageDto buildFilteredPage(
    		Collection<? extends Object> entries, 
    		Integer firstResult,
    		Integer maxResults,
            ObjectComparator objectComparator, 
            Object filter);
	//@formatter:on

}
