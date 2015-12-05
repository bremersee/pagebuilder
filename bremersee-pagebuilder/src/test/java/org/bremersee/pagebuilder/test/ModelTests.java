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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.bremersee.pagebuilder.test.model.Address;
import org.bremersee.pagebuilder.test.model.Person;
import org.bremersee.pagebuilder.test.model.Pet;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 *
 */
public class ModelTests {

    private JAXBContext jaxbContext;

    @Before
    public void createJAXBContext() throws JAXBException {
        this.jaxbContext = JAXBContext.newInstance(
                org.bremersee.comparator.model.ObjectFactory.class.getPackage().getName()
                + ":"
                + org.bremersee.pagebuilder.model.ObjectFactory.class.getPackage().getName()
                + ":"
                + org.bremersee.pagebuilder.test.model.ObjectFactory.class.getPackage().getName()
                );
    }
    
    private List<Object> createPageEntries() {
        Pet pet0 = new Pet("Doggy");
        Pet pet1 = new Pet("Catty");
        
        Address addr0 = new Address("Here");
        Address addr1 = new Address("There");
        
        Person p0 = new Person("Rumpel", "Stielzchen", addr0);
        Person p1 = new Person("Rot", "Kaeppchen", addr1);
        Person p2 = new Person("Cindy", "Rella", null);
        
        List<Object> list = new ArrayList<Object>();
        list.add(pet0);
        list.add(p0);
        list.add(p1);
        list.add(p2);
        list.add(pet1);
        return list;
    }

    @Test
    public void testXmlPageDto() throws Exception {

        System.out.println("Testing XML PageDto ...");
        
        PageDto page = new PageDto();
        page.getEntries().addAll(createPageEntries());
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        m.marshal(page, out);
        
        String xmlStr = new String(out.toByteArray(), "UTF-8");
        
        System.out.println(xmlStr);
        
        PageDto readPage = (PageDto) jaxbContext.createUnmarshaller().unmarshal(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
        
        m.marshal(readPage, System.out);
        
//        PageDto parsedPageDto = new PageDto();
//        for (Object o : readPage.getEntries()) {
//            org.w3c.dom.Element elem = (org.w3c.dom.Element)o;
//            Object knownXmlObj = jaxbContext.createUnmarshaller().unmarshal(elem);
//            System.out.println(knownXmlObj);
//            parsedPageDto.getEntries().add(knownXmlObj);
//        }
        
        TestCase.assertEquals(page, readPage);

        System.out.println("OK\n");
    }
    
    @Test
    public void testJsonPageDto() throws Exception {

        System.out.println("Testing JSON PageDto ...");
        
        ObjectMapper om = new ObjectMapper();
        
        PageDto page = new PageDto();
        page.getEntries().addAll(createPageEntries());
        
        String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(page);
        
        System.out.println(jsonStr);
        
        PageDto readPage = (PageDto) om.readValue(jsonStr, PageDto.class);
        
        System.out.println(readPage);
        
        for (Object o : readPage.getEntries()) {
            System.out.println(o.getClass().getName() + " = " + o);
        }
        
//        TestCase.assertEquals(page, readPage);

        System.out.println("OK\n");
    }
    
    @Test
    public void testXmlPageRequestDto() throws Exception {

        System.out.println("Testing XML PageRequestDto ...");
        
        ComparatorItem comparatorItem = new ComparatorItem("foo", true);
        comparatorItem.next("bar", false);
        
        Address addr0 = new Address("Here");
        Person p0 = new Person("Rumpel", "Stielzchen", addr0);
        
        PageRequestDto request = new PageRequestDto(22, 100, comparatorItem, "foo bar", p0);
        
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        
        m.marshal(request, out);
        
        String xmlStr = new String(out.toByteArray(), "UTF-8");
        
        System.out.println(xmlStr);
        
        PageRequestDto readRequest = (PageRequestDto) jaxbContext.createUnmarshaller().unmarshal(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));
        
        m.marshal(readRequest, System.out);
        
        TestCase.assertEquals(request, readRequest);

        System.out.println("OK\n");
    }
    
    @Test
    public void testJsonPageRequestDto() throws Exception {

        System.out.println("Testing JSON PageRequestDto ...");
        
        ComparatorItem comparatorItem = new ComparatorItem("foo", true);
        comparatorItem.next("bar", false);
        
        Address addr0 = new Address("Here");
        Person p0 = new Person("Rumpel", "Stielzchen", addr0);
        
        PageRequestDto request = new PageRequestDto(22, 100, comparatorItem, "foo bar", p0);
        
        ObjectMapper om = new ObjectMapper();
        
        String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        
        System.out.println(jsonStr);
        
        PageRequestDto readRequest = om.readValue(jsonStr, PageRequestDto.class);
        
        System.out.println(readRequest);
        
        String newJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(readRequest);
        System.out.println(newJsonStr);
        
        TestCase.assertEquals(jsonStr, newJsonStr);
        
        System.out.println("OK\n");
    }
    
}
