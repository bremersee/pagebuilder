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

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * @author Christian Bremer <a href="mailto:christian@bremersee.org">christian@bremersee.org</a>
 */
@Component
public class PersonRepositoryImpl implements PersonRepository {

    private final List<Person> userDb = new ArrayList<>();
    
    /**
     * Default constructor.
     */
    public PersonRepositoryImpl() {
        userDb.add(new Person(1L, "Heinrich", "Heine", new Address(1L, "Rue de Chanson", "Paris", "12345")));
        userDb.add(new Person(2L, "Virginia", "Woolf", new Address(2L, "Mrs Dalloway Street", "London", "1882")));
        userDb.add(new Person(3L, "Thomas", "Mann", new Address(3L, "Buddenbrooks Str. 3", "Lübeck", "23552")));
        userDb.add(new Person(4L, "Malcomlm", "Lowry", new Address(4L, "Under the Popocatépetl", "Quauhnáhuac", "1947")));
        userDb.add(new Person(5L, "Annete von", "Droste-Hülshoff", new Address(5L, "Im Grase 8", "Gievenbeck", "1848")));
        userDb.add(new Person(6L, "Wilhelm", "Raabe", new Address(6L, "Sperlingsgasse 33", "Braunschweig", "38102")));
        userDb.add(new Person(7L, "Klaus", "Mann", new Address(7L, "Mephistopheles Linje 2", "Hamburg", "6789")));
        userDb.add(new Person(8L, "Guillermo Cabrera", "Infante", new Address(8L, "Nachtigallengasse 18", "Havanna", "1958")));
        userDb.add(new Person(9L, "James", "Joyce", new Address(9L, "Eccles Street No. 7", "Dublin", "1606")));
        userDb.add(new Person(10L, "Heinrich", "Mann", new Address(10L, "Unter dem Tan 66", "Lübeck", "23552")));
    }

    /* (non-Javadoc)
     * @see org.bremersee.comparator.example.domain.PersonRepository#findAll()
     */
    @Override
    public List<Person> findAll() {
        List<Person> userDb = new ArrayList<>(this.userDb);
        return userDb;
    }

    /* (non-Javadoc)
     * @see org.bremersee.comparator.example.domain.PersonRepository#findByQuery(java.lang.String)
     */
    @Override
    public List<Person> findByQuery(String query) {
        if (query == null || query.length() < 3) {
            return findAll();
        }
        String _query = query.toLowerCase();
        List<Person> userDb = new ArrayList<>();
        for (Person u : this.userDb) {
            if (u.getFirstname().toLowerCase().contains(_query) 
                    || u.getLastname().toLowerCase().contains(_query) 
                    || u.getAddress().getCity().toLowerCase().contains(_query) 
                    || u.getAddress().getStreet().toLowerCase().contains(_query) 
                    || u.getAddress().getPostalCode().toLowerCase().contains(_query)) {
                userDb.add(u);
            }
        }
        return userDb;
    }

}
