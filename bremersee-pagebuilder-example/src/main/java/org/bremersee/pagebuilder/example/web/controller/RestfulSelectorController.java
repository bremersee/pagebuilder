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
import org.bremersee.pagebuilder.PageBuilderUtils;
import org.bremersee.pagebuilder.PageControlFactory;
import org.bremersee.pagebuilder.example.service.PersonService;
import org.bremersee.pagebuilder.model.PageControl;
import org.bremersee.pagebuilder.model.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

/**
 * @author Christian Bremer <a href="mailto:christian@bremersee.org">christian@bremersee.org</a>
 */
@Controller
public class RestfulSelectorController {

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
                .setMaxResultsParamName("max")
                .setMaxResultsSelectorMaxValue(6)
                .setMaxResultsSelectorMinValue(2)
                .setMaxResultsSelectorStep(2).setPageNumberParamName("p")
                .setQueryParamName("q")
                .setQuerySupported(true)
                .setSelectAllResultsAvailable(true);
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

    @RequestMapping(value = "/restful-selector.html", method = RequestMethod.GET)
    public String displayRestful(HttpServletRequest request, Model model,
            @RequestParam(value = "q", defaultValue = "") String query,
            @RequestParam(value = "max", defaultValue = "4") int maxResults,
            @RequestParam(value = "p", defaultValue = "0") int pageNumber,
            @RequestParam(value = "c", defaultValue = "") String comparator) {

        ComparatorItem comparatorItem = comparatorItemTransformer.fromString(comparator, false, null);

        PageDto pageDto = personService.findPersons(
                query, 
                PageBuilderUtils.getFirstResult(pageNumber, maxResults), 
                maxResults, 
                comparatorItem);

        PageControl pageControl = pageControlFactory.newPageControl(pageDto, "restful-selector.html", query, resolveLocale(request));

        model.addAttribute("pageControl", pageControl);

        return "restful-selector";
    }
}
