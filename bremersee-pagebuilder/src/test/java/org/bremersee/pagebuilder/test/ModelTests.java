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
import org.bremersee.pagebuilder.PageBuilderUtils;
import org.bremersee.pagebuilder.model.PageControlDto;
import org.bremersee.pagebuilder.model.PageDto;
import org.bremersee.pagebuilder.model.PageRequestDto;
import org.bremersee.pagebuilder.model.PageRequestLinkDto;
import org.bremersee.pagebuilder.model.PageSizeSelectorOptionDto;
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
    
    private PageRequestDto createPageRequestDtoWithExtension() {
        ComparatorItem comparatorItem = new ComparatorItem("foo", true);
        comparatorItem.next("bar", false);
        Address addr0 = new Address("Here");
        Person p0 = new Person("Rumpel", "Stielzchen", addr0);
        return new PageRequestDto(22, 100, comparatorItem, "foo bar", p0);
    }

    @Test
    public void testXmlPageRequestDto() throws Exception {

        System.out.println("Testing XML PageRequestDto ...");

        PageRequestDto request = createPageRequestDtoWithExtension();

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

        PageRequestDto request = createPageRequestDtoWithExtension();

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
    
    @Test
    public void testXmlPageRequestLinkDto() throws Exception {

        System.out.println("Testing XML PageRequestLinkDto ...");

        PageRequestLinkDto request = new PageRequestLinkDto(createPageRequestDtoWithExtension(), true, "http://example.org");

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(request, out);

        String xmlStr = new String(out.toByteArray(), "UTF-8");

        System.out.println(xmlStr);

        PageRequestLinkDto readRequest = (PageRequestLinkDto) jaxbContext.createUnmarshaller()
                .unmarshal(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));

        m.marshal(readRequest, System.out);

        TestCase.assertEquals(request, readRequest);

        System.out.println("OK\n");
    }

    @Test
    public void testJsonPageRequestLinkDto() throws Exception {

        System.out.println("Testing JSON PageRequestDto ...");

        PageRequestLinkDto request = new PageRequestLinkDto(createPageRequestDtoWithExtension(), true, "http://example.org");

        ObjectMapper om = new ObjectMapper();

        String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(request);

        System.out.println(jsonStr);

        PageRequestLinkDto readRequest = om.readValue(jsonStr, PageRequestLinkDto.class);

        System.out.println(readRequest);

        String newJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(readRequest);
        System.out.println(newJsonStr);

        TestCase.assertEquals(jsonStr, newJsonStr);

        System.out.println("OK\n");
    }
    
    @Test
    public void testXmlPageSizeSelectorOptionDto() throws Exception {

        System.out.println("Testing XML PageSizeSelectorOptionDto ...");

        PageSizeSelectorOptionDto option = new PageSizeSelectorOptionDto(25, "26", true);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Marshaller m = jaxbContext.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        m.marshal(option, out);

        String xmlStr = new String(out.toByteArray(), "UTF-8");

        System.out.println(xmlStr);

        PageSizeSelectorOptionDto readOption = (PageSizeSelectorOptionDto) jaxbContext.createUnmarshaller()
                .unmarshal(new ByteArrayInputStream(xmlStr.getBytes("UTF-8")));

        m.marshal(readOption, System.out);

        TestCase.assertEquals(option, readOption);

        System.out.println("OK\n");
    }

    @Test
    public void testJsonPageSizeSelectorOptionDto() throws Exception {

        System.out.println("Testing JSON PageSizeSelectorOptionDto ...");

        PageSizeSelectorOptionDto option = new PageSizeSelectorOptionDto(25, "26", true);

        ObjectMapper om = new ObjectMapper();

        String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(option);

        System.out.println(jsonStr);

        PageSizeSelectorOptionDto readOption = om.readValue(jsonStr, PageSizeSelectorOptionDto.class);

        System.out.println(readOption);

        String newJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(readOption);
        System.out.println(newJsonStr);

        TestCase.assertEquals(jsonStr, newJsonStr);

        System.out.println("OK\n");
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
    
    private PageRequestDto createPageRequestDto() {
        PageRequestDto req = new PageRequestDto();
        req.setComparatorItem(new ComparatorItem("name"));
        req.setPageNumber(0);
        req.setPageSize(Integer.MAX_VALUE);
        return req;
    }

    private PageDto createPageDto() {
        PageDto page = new PageDto();
        page.getEntries().addAll(createPageEntries());
        page.setTotalSize(page.getEntries().size());
        page.setPageRequest(createPageRequestDto());
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

    private void parseJsonReadPage(PageDto readPage, ObjectMapper om) throws Exception {
        List<Object> jsonEntries = new ArrayList<>(readPage.getEntries().size());
        for (Object o : readPage.getEntries()) {
            System.out.println(o.getClass().getName() + " = " + o);
            Object obj;
            @SuppressWarnings({ "rawtypes", "unchecked" })
            Map<String, Object> map = (Map) o;
            if (map.containsKey("name")) {
                // it's a pet
                obj = PageBuilderUtils.jsonMapToObject(map, Pet.class, om);
            } else {
                // it's a person
                obj = PageBuilderUtils.jsonMapToObject(map, Person.class, om);
            }
            jsonEntries.add(obj);
        }
        readPage.getEntries().clear();
        readPage.getEntries().addAll(jsonEntries);
    }

    private List<PageSizeSelectorOptionDto> createMaxResultsSelectorOptions() {
        return Arrays.asList(new PageSizeSelectorOptionDto(10, "10", true),
                new PageSizeSelectorOptionDto(20, "20", false));
    }

    private PaginationDto createPaginationDto() {
        // that must not make sense
        PageRequestLinkDto b0 = new PageRequestLinkDto(0, Integer.MAX_VALUE, new ComparatorItem("name"), null, null, true, "http://localhost/app/page.html?p=0");
        PageRequestLinkDto b1 = new PageRequestLinkDto(1, Integer.MAX_VALUE, new ComparatorItem("name"), null, null, true, "http://localhost/app/page.html?p=1");
        PageRequestLinkDto b2 = new PageRequestLinkDto(2, Integer.MAX_VALUE, new ComparatorItem("name"), null, null, true, "http://localhost/app/page.html?p=2");
        PageRequestLinkDto b3 = new PageRequestLinkDto(3, Integer.MAX_VALUE, new ComparatorItem("name"), null, null, true, "http://localhost/app/page.html?p=3");
        PageRequestLinkDto b4 = new PageRequestLinkDto(4, Integer.MAX_VALUE, new ComparatorItem("name"), null, null, true, "http://localhost/app/page.html?p=4");
        PaginationDto pagination = new PaginationDto();
        pagination.getLinks().addAll(Arrays.asList(b1, b2, b3));
        pagination.setFirstPageLink(b0);
        pagination.setPreviousPageLink(b1);
        pagination.setNextPageLink(b3);
        pagination.setLastPageLink(b4);
        pagination.setMaxPaginationLinks(3);
        return pagination;
    }

    private PageControlDto createPageControlDto() {
        PageControlDto pc = new PageControlDto();
        pc.setComparatorParamValue("name,asc");
        pc.setPageSizeSelectorOptions(createMaxResultsSelectorOptions());
        pc.setPage(createPageDto());
        pc.setPagination(createPaginationDto());
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

        System.out.println("Testing JSON PageControlDto ...");

        PageControlDto pageControl = createPageControlDto();

        ObjectMapper om = new ObjectMapper();

        String jsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(pageControl);

        System.out.println(jsonStr);

        PageControlDto readPageControl = om.readValue(jsonStr, PageControlDto.class);
        parseJsonReadPage(readPageControl.getPage(), om);

        String newJsonStr = om.writerWithDefaultPrettyPrinter().writeValueAsString(readPageControl);
        System.out.println(newJsonStr);

        TestCase.assertEquals(pageControl, readPageControl);

        System.out.println("OK\n");
    }

}
