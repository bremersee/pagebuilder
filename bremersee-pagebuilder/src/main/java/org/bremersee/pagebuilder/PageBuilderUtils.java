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

/**
 * <p>
 * Utility methods.
 * </p>
 * 
 * @author Christian Bremer
 */
public abstract class PageBuilderUtils {

    private PageBuilderUtils() {
    }

    /**
     * Calculates the first result of a given page number and the maximum
     * results.
     * 
     * @param pageNumber
     *            the page number
     * @param maxResults
     *            maximum results
     * @return the first result
     */
    public static int getFirstResult(int pageNumber, int maxResults) {
        Long firstResult = (long) pageNumber * (long) maxResults;
        if (firstResult > Integer.MAX_VALUE) {
            firstResult = (long) Integer.MAX_VALUE;
        }
        return firstResult.intValue();
    }

}
