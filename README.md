# Bremersee PageBuilder
This project contains classes for building a page.

A page is a sublist of a list of elements. It can be obtained with a page request:

```
List<Object> allElements = new ArrayList<>();
// add elements

PageRequestDto pageRequest = new PageRequestDto();
pageRequest.setPageNumber(2);
pageRequest.setPageSize(25);
pageRequest.setComparatorItem(new ComparatorItem("name", true));

PageBuilder pageBuilder = new PageBuilderImpl();

Page<Object> page = pageBuilder.buildFilteredPage(allElements, pageRequest, null);
```

To display the page with a pagination you can create a PageControlDto:
```
Page<Object> page = pageBuilder.buildFilteredPage(allElements, pageRequest, null);

PageControlDto pageControl = PageControlFactory.newInstance()
        .newPageControl(page, "mypage.html", Locale.GERMANY);
```

The generated maven site is committed to the [gh-pages branch](https://github.com/bremersee/pagebuilder/tree/gh-pages) and visible [here](http://bremersee.github.io/pagebuilder/).

## Release 1.2.0
Release 1.2.0 is build with Java 7.

It is available at Maven Central:
```xml
<dependency>
    <groupId>org.bremersee</groupId>
    <artifactId>bremersee-pagebuilder</artifactId>
    <version>1.2.0</version>
</dependency>
```

# Bremersee PageBuilder Example
This project contains a small Spring Boot Application that demonstrates the usage of the page builder library.

It's not available at Maven Central. You may check it out and run the application with
```
$ cd pagebuilder/bremersee-pagebuilder-example
$ mvn spring-boot:run
```
or
```
$ cd pagebuilder/bremersee-pagebuilder-example
$ mvn clean package
$ java -jar target/bremersee-pagebuilder-example-1.1.0.jar
```
After the application is started you can open [http://localhost:8080/restful.html](http://localhost:8080/restful.html) in your favorite browser and have a look at the demonstration.
