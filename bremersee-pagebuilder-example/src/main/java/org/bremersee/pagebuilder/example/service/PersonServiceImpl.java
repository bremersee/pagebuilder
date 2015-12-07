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

package org.bremersee.pagebuilder.example.service;

import java.util.List;

import org.bremersee.comparator.ObjectComparator;
import org.bremersee.comparator.ObjectComparatorFactory;
import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.PageBuilder;
import org.bremersee.pagebuilder.example.domain.Person;
import org.bremersee.pagebuilder.example.domain.PersonRepository;
import org.bremersee.pagebuilder.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Christian Bremer
 */
@Service
public class PersonServiceImpl implements PersonService {
    
    @Autowired
    protected PersonRepository personRepository;

    @Autowired
    protected ObjectComparatorFactory objectComparatorFactory;
    
    @Autowired
    protected PageBuilder pageBuilder;
    
    /* (non-Javadoc)
     * @see org.bremersee.comparator.example.service.PersonService#findPersons(java.lang.String, java.lang.Integer, java.lang.Integer, org.bremersee.comparator.model.ComparatorItem)
     */
    @Override
    public Page findPersons(String query, Integer firstResult, Integer maxResults, ComparatorItem comparatorItem) {

        if (comparatorItem == null) {
            comparatorItem = new ComparatorItem("id", true);
        }
        
        ObjectComparator objectComparator = objectComparatorFactory.newObjectComparator(comparatorItem);
        
        List<Person> persons = personRepository.findByQuery(query);
        Page page = pageBuilder.buildFilteredPage(persons, firstResult, maxResults, objectComparator, null);
        return page;
    }
    
}
