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
import java.util.List;

import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequest;

/**
 * @author Christian Bremer
 */
public class PageResult<E> implements Page<E> {

    private static final long serialVersionUID = 1L;

    private List<E> entries = new ArrayList<E>();

    private PageRequest pageRequest;

    private long totalSize;

    /**
     * Default constructor.
     */
    public PageResult() {
    }

    public PageResult(Collection<? extends E> entries) {
        this(entries, null, 0L);
    }

    public PageResult(Collection<? extends E> entries, long totalSize) {
        this(entries, null, totalSize);
    }

    public PageResult(Collection<? extends E> entries, PageRequest pageRequest, long totalSize) {
        super();
        if (entries != null) {
            this.entries.addAll(entries);
        }
        setPageRequest(pageRequest);
        setTotalSize(totalSize);
    }

    @Override
    public String toString() {
        return "PageResult [entries=" + entries + ", pageRequest=" + pageRequest + ", totalSize=" + totalSize + "]";
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        result = prime * result + ((pageRequest == null) ? 0 : pageRequest.hashCode());
        result = prime * result + (int) (totalSize ^ (totalSize >>> 32));
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Page))
            return false;
        Page<?> other = (Page<?>) obj;
        if (getEntries() == null) {
            if (other.getEntries() != null)
                return false;
        } else if (!getEntries().equals(other.getEntries()))
            return false;
        if (getPageRequest() == null) {
            if (other.getPageRequest() != null)
                return false;
        } else if (!getPageRequest().equals(other.getPageRequest()))
            return false;
        if (getTotalSize() != other.getTotalSize())
            return false;
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getEntries()
     */
    @Override
    public List<E> getEntries() {
        return entries;
    }

    /**
     * Sets the elements of the page.
     */
    public void setEntries(List<E> entries) {
        if (entries == null) {
            entries = new ArrayList<E>();
        }
        this.entries = entries;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getPageRequest()
     */
    @Override
    public PageRequest getPageRequest() {
        return pageRequest;
    }

    /**
     * Sets the page request.
     */
    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getTotalSize()
     */
    @Override
    public long getTotalSize() {
        return totalSize;
    }

    /**
     * Sets the size of all available elements.
     */
    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.bremersee.pagebuilder.model.Page#getTotalPages()
     */
    @Override
    public int getTotalPages() {
        if (getTotalSize() <= 0L) {
            return 1;
        }
        return (int) Math.ceil((double) getTotalSize() / (double) getPageRequest().getPageSize());
    }

    /**
     * Creates a page with data transfer objects of this page.
     * 
     * @param transformer
     *            the transformer to transform this entries into the data
     *            transfer objects.
     * @return the page DTO
     */
    public <T> PageDto toPageDto(PageEntryTransformer<T, E> transformer) {
        return PageBuilderUtils.createPageDto(this, transformer);
    }

}
