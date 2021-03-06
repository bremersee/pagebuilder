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

package org.bremersee.pagebuilder.example;

import org.bremersee.comparator.ComparatorItemTransformer;
import org.bremersee.comparator.ComparatorItemTransformerImpl;
import org.bremersee.comparator.ObjectComparatorFactory;
import org.bremersee.pagebuilder.PageBuilder;
import org.bremersee.pagebuilder.PageBuilderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author Christian Bremer
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "org.bremersee.pagebuilder.example.domain")
@EntityScan(basePackages = "org.bremersee.pagebuilder.example.domain")
public class PageBuilderExampleApplication {

    @Bean
    public ObjectComparatorFactory objectComparatorFactory() {
        return ObjectComparatorFactory.newInstance();
    }

    @Bean
    public PageBuilder pageBuilder() {
        PageBuilderImpl pageBuilder = new PageBuilderImpl();
        pageBuilder.setObjectComparatorFactory(objectComparatorFactory());
        return pageBuilder;
    }

    @Bean
    public ComparatorItemTransformer comparatorItemTransformer() {
        return new ComparatorItemTransformerImpl();
    }

    public static void main(String[] args) {
        SpringApplication.run(PageBuilderExampleApplication.class, args);
    }

}
