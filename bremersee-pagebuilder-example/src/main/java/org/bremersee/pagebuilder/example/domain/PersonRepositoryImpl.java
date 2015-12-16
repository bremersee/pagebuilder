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

package org.bremersee.pagebuilder.example.domain;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

/**
 * @author Christian Bremer
 */
@Component
public class PersonRepositoryImpl implements PersonRepositoryCustom {
    
    @PersistenceContext
    protected EntityManager entityManager;

    /* (non-Javadoc)
     * @see org.bremersee.comparator.example.domain.PersonRepository#findByQuery(java.lang.String)
     */
    @Override
    public List<Person> findByQuery(String query) {
        //@formatter:off
        String queryStr = '%' + query.toLowerCase() + '%';
        String jpaQuery = "select e from Person e "
                + " where lower(e.firstname) like :query"
                + " or lower(e.lastname) like :query"
                + " or lower(e.address.city) like :query"
                + " or lower(e.address.street) like :query"
                + " or lower(e.address.postalCode) like :query"
                ;
        
        return entityManager
                .createQuery(jpaQuery, Person.class)
                .setParameter("query", queryStr)
                .getResultList();
        //@formatter:on
    }

}
