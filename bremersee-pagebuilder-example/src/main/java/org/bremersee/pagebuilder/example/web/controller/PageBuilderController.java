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

package org.bremersee.pagebuilder.example.web.controller;

import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.bremersee.comparator.ComparatorItemTransformer;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.PageControlFactory;
import org.bremersee.pagebuilder.example.service.PersonService;
import org.bremersee.pagebuilder.model.PageControlDto;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author Christian Bremer
 */
@Controller
public class PageBuilderController {

    @Autowired
    protected PersonService personService;

    @Autowired
    protected ComparatorItemTransformer comparatorItemTransformer;
    
    @Autowired(required = false)
    protected LocaleResolver localeResolver;

    protected PageControlFactory pageControlFactory;

    @PostConstruct
    public void init() {
        pageControlFactory = PageControlFactory.newInstance()
                .setComparatorItemTransformer(comparatorItemTransformer)
                .setComparatorParamName("c")
                .setPageSizeParamName("s")
                .setPageSizeSelectorMaxValue(6)
                .setPageSizeSelectorMinValue(2)
                .setPageSizeSelectorStep(2).setPageNumberParamName("p")
                .setQueryParamName("q")
                .setQuerySupported(true)
                .setSelectAllEntriesAvailable(true);
    }
    
    protected Locale resolveLocale(HttpServletRequest request) {
        Locale locale;
        if (localeResolver != null) {
            locale = localeResolver.resolveLocale(request);
        } else {
            locale = request.getLocale();
        }
        if (locale == null || StringUtils.isBlank(locale.getLanguage())) {
            locale = Locale.getDefault();
        }
        return locale;
    }

    @RequestMapping(value = "/restful.html", method = RequestMethod.GET)
    public String displayRestful(HttpServletRequest request, Model model,
            @RequestParam(value = "p", defaultValue = "0") int pageNumber,
            @RequestParam(value = "s", defaultValue = "4") int pageSize,
            @RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(value = "c", defaultValue = "") String comparator) {

        if (StringUtils.isBlank(comparator)) {
            comparator = comparatorItemTransformer.toString(new ComparatorItem("id"), false, null);
        }
        
        PageRequestDto pageRequest = new PageRequestDto(pageNumber, pageSize, comparatorItemTransformer.fromString(comparator, false, null), query);

        PageDto page = personService.findPersons(pageRequest);

        PageControlDto pageControl = pageControlFactory.newPageControl(page, "restful.html", resolveLocale(request));

        model.addAttribute("pageControl", pageControl);

        return "restful";
    }
    
    @RequestMapping(value = "/ajax.html", method = RequestMethod.GET)
    public String displayAjax(HttpServletRequest request, Model model,
            @RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(value = "s", defaultValue = "4") int pageSize,
            @RequestParam(value = "p", defaultValue = "0") int pageNumber,
            @RequestParam(value = "c", defaultValue = "") String comparator) {

        if (StringUtils.isBlank(comparator)) {
            comparator = comparatorItemTransformer.toString(new ComparatorItem("id"), false, null);
        }
        
        PageRequestDto pageRequest = new PageRequestDto(pageNumber, pageSize, comparatorItemTransformer.fromString(comparator, false, null), query);

        PageDto page = personService.findPersons(pageRequest);

        PageControlDto pageControl = pageControlFactory.newPageControl(page, "ajax.html", resolveLocale(request));

        model.addAttribute("pageControl", pageControl);

        return "ajax";
    }
    
    @RequestMapping(
            value = "/page-control.json", 
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody PageControlDto getPageControl(
            HttpServletRequest request,
            @RequestBody PageRequestDto pageRequest) {
        
        PageDto page = personService.findPersons(pageRequest);

        PageControlDto pageControl = pageControlFactory.newPageControl(page, "ajax.html", resolveLocale(request));

        return pageControl;
    }
    
}
