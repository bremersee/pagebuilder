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

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.bremersee.pagebuilder.PageBuilder;
import org.bremersee.pagebuilder.PageBuilderUtils;
import org.bremersee.pagebuilder.PageResult;
import org.bremersee.pagebuilder.example.domain.Address;
import org.bremersee.pagebuilder.example.domain.Person;
import org.bremersee.pagebuilder.example.domain.PersonRepository;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.bremersee.pagebuilder.spring.PageBuilderSpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @author Christian Bremer
 */
@Service
public class PersonServiceImpl implements PersonService {
    
    @Autowired
    protected PlatformTransactionManager transactionManager;
    
    @Autowired
    protected PersonRepository personRepository;

    @Autowired
    protected PageBuilder pageBuilder;
    
    @PostConstruct
    public void init() {
        TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.execute(new TransactionCallbackWithoutResult() {
            
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                personRepository.save(new Person("Heinrich", "Heine", new Address("Rue de Chanson", "Paris", "12345")));
                personRepository.save(new Person("Virginia", "Woolf", new Address("Mrs Dalloway Street", "London", "1882")));
                personRepository.save(new Person("Thomas", "Mann", new Address("Buddenbrooks Str. 3", "Lübeck", "23552")));
                personRepository.save(new Person("Malcomlm", "Lowry", new Address("Under the Popocatépetl", "Quauhnáhuac", "1947")));
                personRepository.save(new Person("Annete von", "Droste-Hülshoff", new Address("Im Grase 8", "Gievenbeck", "1848")));
                personRepository.save(new Person("Wilhelm", "Raabe", new Address("Sperlingsgasse 33", "Braunschweig", "38102")));
                personRepository.save(new Person("Klaus", "Mann", new Address("Mephistopheles Linje 2", "Hamburg", "6789")));
                personRepository.save(new Person("Guillermo Cabrera", "Infante", new Address("Nachtigallengasse 18", "Havanna", "1958")));
                personRepository.save(new Person("James", "Joyce", new Address("Eccles Street No. 7", "Dublin", "1606")));
                personRepository.save(new Person("Heinrich", "Mann", new Address("Unter dem Tan 66", "Lübeck", "23552")));
            }
        });
    }
    
    @Override
    public PageDto findPersons(PageRequestDto pageRequest) {

        if (pageRequest == null) {
            pageRequest = new PageRequestDto();
        }
        
        if (StringUtils.isBlank(pageRequest.getQuery())) {
            Pageable pageable = PageBuilderSpringUtils.toSpringPageRequest(pageRequest);
            Page<Person> springPage = personRepository.findAll(pageable);
            PageResult<Person> page = PageBuilderSpringUtils.fromSpringPage(springPage);
            return PageBuilderUtils.createPageDto(page, null);
        }
        
        List<Person> persons = personRepository.findByQuery(pageRequest.getQuery());
        org.bremersee.pagebuilder.model.Page<Person> page = pageBuilder.buildFilteredPage(persons, pageRequest, null);
        return PageBuilderUtils.createPageDto(page, null);
    }
    
}
