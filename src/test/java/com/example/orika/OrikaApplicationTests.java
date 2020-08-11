package com.example.orika;

import com.example.orika.model.Destination;
import com.example.orika.model.Source;
import com.example.orika.model.SourceAddress;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@SpringBootTest
class OrikaApplicationTests {

    private MapperFactory mapperFactory;

    private SourceAddress sourceAddress = SourceAddress.builder()
            .firstAddress("231 East 47th Street")
            .secondAddress("117 Sandusky Street Pittsburgh, PA 15212")
            .build();

    private Source source = Source.builder()
            .firstName("john")
            .secondName("smith")
            .address(sourceAddress)
            .build();

    @BeforeEach
    void setMapperFactory() {
        this.mapperFactory = new DefaultMapperFactory.Builder()
                .mapNulls(false)
                .build();
    }

    @Test
    void no_mapper_defined_test() {
        Destination destination = mapperFactory.getMapperFacade().map(source, Destination.class);

        Assertions.assertSame(source.getFirstName(), destination.getFirstName());
        Assertions.assertNull(destination.getLastName());
        Assertions.assertNotNull(destination.getAddress());
        Assertions.assertNull(destination.getAddress().getAddress());
    }

    @Test
    void by_default_mapper_test() {
        mapperFactory.classMap(Source.class, Destination.class)
                .byDefault()
                .register();

        Destination destination = mapperFactory.getMapperFacade().map(source, Destination.class);

        Assertions.assertSame(source.getFirstName(), destination.getFirstName());
        Assertions.assertNull(destination.getLastName());
        Assertions.assertNotNull(destination.getAddress());
        Assertions.assertNull(destination.getAddress().getAddress());
    }

    @Test
    void empty_mapper_defined_test() {
        mapperFactory.classMap(Source.class, Destination.class)
                .register();

        Destination destination = mapperFactory.getMapperFacade().map(source, Destination.class);

        Assertions.assertNull(destination.getFirstName());
        Assertions.assertNull(destination.getLastName());
        Assertions.assertNull(destination.getAddress());
    }

    @Test
    void defined_mapper_test() {
        mapperFactory.classMap(Source.class, Destination.class)
                .field("firstName", "firstName")
                .field("secondName", "lastName")
                .field("address", "address")
                .register();

        Destination destination = mapperFactory.getMapperFacade().map(source, Destination.class);

        Assertions.assertSame(source.getFirstName(), destination.getFirstName());
        Assertions.assertSame(source.getSecondName(), destination.getLastName());
        Assertions.assertNotNull(destination.getAddress());
        Assertions.assertNull(destination.getAddress().getAddress());
    }

    @Test
    void defined_nested_mapper_test() {
        mapperFactory.classMap(Source.class, Destination.class)
                .field("firstName", "firstName")
                .field("secondName", "lastName")
                .field("address.secondAddress", "address.address")
                .register();

        Destination destination = mapperFactory.getMapperFacade().map(source, Destination.class);

        Assertions.assertSame(source.getFirstName(), destination.getFirstName());
        Assertions.assertSame(source.getSecondName(), destination.getLastName());
        Assertions.assertNotNull(destination.getAddress());
        Assertions.assertSame(source.getAddress().getSecondAddress(), destination.getAddress().getAddress());
    }
}
