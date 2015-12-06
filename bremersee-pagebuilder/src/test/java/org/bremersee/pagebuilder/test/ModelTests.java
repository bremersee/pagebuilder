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
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.bremersee.comparator.model.ComparatorItem;
import org.bremersee.pagebuilder.model.MaxResultsSelectorOption;
import org.bremersee.pagebuilder.model.MaxResultsSelectorOptionDto;
import org.bremersee.pagebuilder.model.Page;
import org.bremersee.pagebuilder.model.ModelUtils;
import org.bremersee.pagebuilder.model.PageControlDto;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.bremersee.pagebuilder.model.PaginationButtonDto;
import org.bremersee.pagebuilder.model.PaginationDto;
import org.bremersee.pagebuilder.test.model.Address;
import org.bremersee.pagebuilder.test.model.Person;
import org.bremersee.pagebuilder.test.model.Pet;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

/**
 * @author Christian Bremer
 */
public class ModelTests {

    private JAXBContext jaxbContext;

    @Before
    public void createJAXBContext() throws JAXBException {
        this.jaxbContext = JAXBContext
                .newInstance(org.bremersee.comparator.model.ObjectFactory.class.getPackage().getName() + ":"
                        + org.bremersee.pagebuilder.model.ObjectFactory.class.getPackage().getName() + ":"
                        + org.bremersee.pagebuilder.test.model.ObjectFactory.class.getPackage().getName());
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

    private PageDto createPageDto() {
        PageDto page = new PageDto();
        page.getEntries().addAll(createPageEntries());
        page.setComparatorItem(new ComparatorItem("name", true));
        page.setFirstResult(0);
        page.setMaxResults(2);
        page.setTotalSize(page.getEntries().size());
        return page;
    }

    @Test
    public void testXmlPageDto() throws Exception {

        System.out.println("Testing XML PageDto ...");

        PageDto page = createPageDto();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(page, out);

        String xmlStr = new String(out.toByteArray(), "UTF-8");

        System.out.println(xmlStr);

        PageDto readPage = (PageDto) jaxbContext.createUnmarshaller()
                .unmarshal(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));

        m.marshal(readPage, System.out);

        TestCase.assertEquals(page, readPage);

        System.out.println("OK\n");
    }

    @Test
    public void testJsonPageDto() throws Exception {

        System.out.println("Testing JSON PageDto ...");

        ObjectMapper om = new ObjectMapper();

        PageDto page = createPageDto();

        String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(page);

        System.out.println(jsonStr);

        PageDto readPage = (PageDto) om.readValue(jsonStr, PageDto.class);

        System.out.println(readPage);

        parseJsonReadPage(readPage, om);

        TestCase.assertEquals(page, readPage);

        System.out.println("OK\n");
    }

    private void parseJsonReadPage(Page readPage, ObjectMapper om) throws Exception {
        List<Object> jsonEntries = new ArrayList<>(readPage.getEntries().size());
        for (Object o : readPage.getEntries()) {
            System.out.println(o.getClass().getName() + " = " + o);
            Object obj;
            @SuppressWarnings({ "rawtypes", "unchecked" })
            Map<String, Object> map = (Map) o;
            if (map.containsKey("name")) {
                // it's a pet
                obj = ModelUtils.jsonMapToObject(map, Pet.class, om);
            } else {
                // it's a person
                obj = ModelUtils.jsonMapToObject(map, Person.class, om);
            }
            jsonEntries.add(obj);
        }
        readPage.getEntries().clear();
        readPage.getEntries().addAll(jsonEntries);
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

        PageRequestDto readRequest = (PageRequestDto) jaxbContext.createUnmarshaller()
                .unmarshal(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));

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

    private List<MaxResultsSelectorOption> createMaxResultsSelectorOptions() {
        return Arrays.asList((MaxResultsSelectorOption) new MaxResultsSelectorOptionDto(10, "10", true),
                (MaxResultsSelectorOption) new MaxResultsSelectorOptionDto(20, "20", false));
    }

    private PaginationDto createPaginationDto() {
        // that must not make sense
        PaginationButtonDto b0 = new PaginationButtonDto(0, true, "http://localhost/app/page.html?p=0");
        PaginationButtonDto b1 = new PaginationButtonDto(1, true, "http://localhost/app/page.html?p=1");
        PaginationButtonDto b2 = new PaginationButtonDto(2, true, "http://localhost/app/page.html?p=2");
        PaginationButtonDto b3 = new PaginationButtonDto(3, true, "http://localhost/app/page.html?p=3");
        PaginationButtonDto b4 = new PaginationButtonDto(4, true, "http://localhost/app/page.html?p=4");
        PaginationDto pagination = new PaginationDto();
        pagination.getAllButtons().addAll(Arrays.asList(b0, b1, b2, b3, b4));
        pagination.getButtons().addAll(Arrays.asList(b1, b2, b3));
        pagination.setFirstPageButton(b0);
        pagination.setPreviousPageButton(b1);
        pagination.setNextPageButton(b3);
        pagination.setLastPageButton(b4);
        pagination.setMaxPaginationButtons(3);
        return pagination;
    }

    private PageControlDto createPageControlDto() {
        PageControlDto pc = new PageControlDto();
        pc.setComparatorParamValue("name,asc");
        pc.setMaxResultsSelectorOptions(createMaxResultsSelectorOptions());
        pc.setPage(createPageDto());
        pc.setPagination(createPaginationDto());
        pc.setQuery("doggy");
        return pc;
    }

    @Test
    public void testXmlPageControlDto() throws Exception {

        System.out.println("Testing XML PageControlDto ...");

        PageControlDto pageControl = createPageControlDto();

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(pageControl, out);

        String xmlStr = new String(out.toByteArray(), "UTF-8");

        System.out.println(xmlStr);

        PageControlDto readPageControl = (PageControlDto) jaxbContext.createUnmarshaller()
                .unmarshal(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));

        m.marshal(readPageControl, System.out);

        TestCase.assertEquals(pageControl, readPageControl);

        System.out.println("OK\n");
    }

    @Test
    public void testJsonPageControlDto() throws Exception {

        try {
            System.out.println("Testing JSON PageControlDto ...");

            PageControlDto pageControl = createPageControlDto();

            ObjectMapper om = new ObjectMapper();

            // // that works
            // AnnotationIntrospector primary = new
            // JacksonAnnotationIntrospector();
            // AnnotationIntrospector secondary = new
            // JaxbAnnotationIntrospector();
            // AnnotationIntrospector pair =
            // AnnotationIntrospector.pair(primary, secondary);
            // om.setAnnotationIntrospector(pair);

            String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(pageControl);

            System.out.println(jsonStr);

            PageControlDto readPageControl = om.readValue(jsonStr, PageControlDto.class);
            parseJsonReadPage(readPageControl.getPage(), om);
            // System.out.println(readPageControl);

            String newJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(readPageControl);
            System.out.println(newJsonStr);

            TestCase.assertEquals(pageControl, readPageControl);

            System.out.println("OK\n");

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
