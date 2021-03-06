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

package org.bremersee.pagebuilder.test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Christian Bremer
 */
public class XmlSchemaTests {

    private JAXBContext jaxbContext;

    @Before
    public void createJAXBContext() throws JAXBException {
        //@formatter:off
        this.jaxbContext = JAXBContext.newInstance(
                org.bremersee.comparator.model.ObjectFactory.class.getPackage().getName()
                + ":"
                + org.bremersee.pagebuilder.model.ObjectFactory.class.getPackage().getName()
                );
        //@formatter:on
    }

    @Test
    public void testXmlSchema() throws Exception {

        System.out.println("Testing XML schema ...");

        BufferSchemaOutputResolver res = new BufferSchemaOutputResolver();
        jaxbContext.generateSchema(res);
        System.out.println(res);

        System.out.println("OK\n");
    }

}
