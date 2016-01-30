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

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;

import org.bremersee.pagebuilder.model.PageRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author Christian Bremer
 *
 */
public class PageRequestExtensionExtractorImpl implements PageRequestExtensionExtractor {
    
    protected final Logger log = LoggerFactory.getLogger(getClass());

    protected JAXBContext jaxbContext;
    
    protected ObjectMapper objectMapper;
    
    /**
     * Default constructor. 
     */
    public PageRequestExtensionExtractorImpl() {
    }

    public PageRequestExtensionExtractorImpl(JAXBContext jaxbContext, ObjectMapper objectMapper) {
        setJaxbContext(jaxbContext);
        setObjectMapper(objectMapper);
    }
    
    protected JAXBContext getJaxbContext() {
        return null;
    }
    
    public void setJaxbContext(JAXBContext jaxbContext) {
        this.jaxbContext = jaxbContext;
    }
    
    protected ObjectMapper getObjectMapper() {
        return null;
    }
    
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
    
    @PostConstruct
    public void init() {
        log.info("Initializing " + getClass().getSimpleName() + " ...");
        log.info("jaxbContext = " + getJaxbContext());
        log.info("objectMapper = " + getObjectMapper());
        log.info(getClass().getSimpleName() + " successfully initialized.");
    }
    
    @Override
    public <T, S extends T> T getPageRequestExtension(PageRequest pageRequest, Class<T> extensionType, S defaultObject) {
        
        if (pageRequest == null) {
            return defaultObject;
        }
        try {
            return PageBuilderUtils.transform(pageRequest.getExtension(), extensionType, defaultObject, getJaxbContext(), getObjectMapper());
            
        } catch (Exception e) {
            if (e instanceof RuntimeException) {
                log.error("Getting page request extension from page request [" + pageRequest + "] failed.", e);
                throw (RuntimeException)e;
            } else {
                log.warn("Getting page request extension from page request [" + pageRequest + "] failed. Returning default value [" + defaultObject + "].", e);
                return defaultObject;
            }
        }
    }

}
