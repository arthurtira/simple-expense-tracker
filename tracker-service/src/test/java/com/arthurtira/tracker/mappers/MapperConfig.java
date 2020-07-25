package com.arthurtira.tracker.mappers;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = {
        ExpensesMapper.class
})
public class MapperConfig {
}
