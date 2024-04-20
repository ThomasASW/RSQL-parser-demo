package com.aspiresys.rsqlparserdemo.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeParserManager {

    private static AttributeParserManager single_instance = null;

    public final String EQUAL = "==";
    public final String NOT_EQUAL = "!=";
    public final String GREATER_THAN = "=gt=";
    public final String GREATER_THAN_OR_EQUAL_TO = "=ge=";
    public final String LESS_THAN = "=lt=";
    public final String LESS_THAN_OR_EQUAL_TO = "=le=";

    Map<Class, IAttributeParser> equalAttibuteRegistry;
    Map<Class, IAttributeParser> notEqualAttributeRegistry;
    Map<Class, IAttributeParser> greaterThanAttributeRegistry;
    Map<Class, IAttributeParser> greaterThanOrEqualToAttributeRegistry;
    Map<Class, IAttributeParser> lessThanAttributeRegistry;
    Map<Class, IAttributeParser> lessThanOrEqualToAttributeRegistry;

    public AttributeParserManager() {
        this.equalAttibuteRegistry = new HashMap<>();
        this.notEqualAttributeRegistry = new HashMap<>();
        this.greaterThanAttributeRegistry = new HashMap<>();
        this.greaterThanOrEqualToAttributeRegistry = new HashMap<>();
        this.lessThanAttributeRegistry = new HashMap<>();
        this.lessThanOrEqualToAttributeRegistry = new HashMap<>();
        this.registerEqualDataTypes();
        this.registerNotEqualDataTypes();
        this.registerGreaterThanDataTypes();
        this.registerGreaterThanOrEqualToDataTypes();
        this.registerLessThanDataTypes();
        this.registerLessThanOrEqualToDataTypes();
    }

    // Method
    // Static method to create instance of Singleton class
    public static AttributeParserManager getAttributeParserManagerInstance() {
        // To ensure only one instance is created
        if (single_instance == null) {
            single_instance = new AttributeParserManager();
        }
        return single_instance;
    }

    private void registerEqualDataTypes() {
        this.registerAttributeParser(EQUAL, Boolean.class, new BooleanAttributeParser());
        this.registerAttributeParser(EQUAL, Byte.class, new ByteAttributeParser());
        this.registerAttributeParser(EQUAL, Short.class, new ShortAttributeParser());
        this.registerAttributeParser(EQUAL, Integer.class, new IntegerAttributeParser());
        this.registerAttributeParser(EQUAL, Long.class, new LongAttributeParser());
        this.registerAttributeParser(EQUAL, Float.class, new FloatAttributeParser());
        this.registerAttributeParser(EQUAL, Double.class, new DoubleAttributeParser());
    }

    private void registerNotEqualDataTypes() {
        this.registerAttributeParser(NOT_EQUAL, Boolean.class, new BooleanAttributeParser());
        this.registerAttributeParser(NOT_EQUAL, Byte.class, new ByteAttributeParser());
        this.registerAttributeParser(NOT_EQUAL, Short.class, new ShortAttributeParser());
        this.registerAttributeParser(NOT_EQUAL, Integer.class, new IntegerAttributeParser());
        this.registerAttributeParser(NOT_EQUAL, Long.class, new LongAttributeParser());
        this.registerAttributeParser(NOT_EQUAL, Float.class, new FloatAttributeParser());
        this.registerAttributeParser(NOT_EQUAL, Double.class, new DoubleAttributeParser());
    }

    private void registerGreaterThanDataTypes() {
        this.registerAttributeParser(GREATER_THAN, Integer.class, new IntegerAttributeParser());
        this.registerAttributeParser(GREATER_THAN, Long.class, new LongAttributeParser());
        this.registerAttributeParser(GREATER_THAN, Float.class, new FloatAttributeParser());
        this.registerAttributeParser(GREATER_THAN, Double.class, new DoubleAttributeParser());
        this.registerAttributeParser(GREATER_THAN, LocalDateTime.class, new LocalDateTimeAttributeParser());
    }

    private void registerGreaterThanOrEqualToDataTypes() {
        this.registerAttributeParser(GREATER_THAN_OR_EQUAL_TO, Integer.class, new IntegerAttributeParser());
        this.registerAttributeParser(GREATER_THAN_OR_EQUAL_TO, Long.class, new LongAttributeParser());
        this.registerAttributeParser(GREATER_THAN_OR_EQUAL_TO, Float.class, new FloatAttributeParser());
        this.registerAttributeParser(GREATER_THAN_OR_EQUAL_TO, Double.class, new DoubleAttributeParser());
        this.registerAttributeParser(GREATER_THAN_OR_EQUAL_TO, LocalDateTime.class, new LocalDateTimeAttributeParser());
    }

    private void registerLessThanDataTypes() {
        this.registerAttributeParser(LESS_THAN, Integer.class, new IntegerAttributeParser());
        this.registerAttributeParser(LESS_THAN, Long.class, new LongAttributeParser());
        this.registerAttributeParser(LESS_THAN, Float.class, new FloatAttributeParser());
        this.registerAttributeParser(LESS_THAN, Double.class, new DoubleAttributeParser());
        this.registerAttributeParser(LESS_THAN, LocalDateTime.class, new LocalDateTimeAttributeParser());
    }

    private void registerLessThanOrEqualToDataTypes() {
        this.registerAttributeParser(LESS_THAN_OR_EQUAL_TO, Integer.class, new IntegerAttributeParser());
        this.registerAttributeParser(LESS_THAN_OR_EQUAL_TO, Long.class, new LongAttributeParser());
        this.registerAttributeParser(LESS_THAN_OR_EQUAL_TO, Float.class, new FloatAttributeParser());
        this.registerAttributeParser(LESS_THAN_OR_EQUAL_TO, Double.class, new DoubleAttributeParser());
        this.registerAttributeParser(LESS_THAN_OR_EQUAL_TO, LocalDateTime.class, new LocalDateTimeAttributeParser());
    }

    private void registerAttributeParser(String operation, Class clazz, IAttributeParser attributeParser) {
        switch (operation) {
            case EQUAL:
                this.registerEqualAttributeParser(clazz, attributeParser);
                break;
            case NOT_EQUAL:
                this.registerNotEqualAttributeParser(clazz, attributeParser);
                break;
            case GREATER_THAN:
                this.registerGreaterThanAttributeParser(clazz, attributeParser);
                break;
            case GREATER_THAN_OR_EQUAL_TO:
                this.registerGreaterThanOrEqualToAttributeParser(clazz, attributeParser);
                break;
            case LESS_THAN:
                this.registerLessThanAttributeParser(clazz, attributeParser);
                break;
            case LESS_THAN_OR_EQUAL_TO:
                this.registerLessThanOrEqualToAttributeParser(clazz, attributeParser);
                break;
        }
    }

    private void registerEqualAttributeParser(Class clazz, IAttributeParser attributeParser) {
        this.equalAttibuteRegistry.put(clazz, attributeParser);
    }

    private void registerNotEqualAttributeParser(Class clazz, IAttributeParser attributeParser) {
        this.notEqualAttributeRegistry.put(clazz, attributeParser);
    }

    private void registerGreaterThanAttributeParser(Class clazz, IAttributeParser attributeParser) {
        this.greaterThanAttributeRegistry.put(clazz, attributeParser);
    }

    private void registerGreaterThanOrEqualToAttributeParser(Class clazz, IAttributeParser attributeParser) {
        this.greaterThanOrEqualToAttributeRegistry.put(clazz, attributeParser);
    }

    private void registerLessThanAttributeParser(Class clazz, IAttributeParser attributeParser) {
        this.lessThanAttributeRegistry.put(clazz, attributeParser);
    }

    private void registerLessThanOrEqualToAttributeParser(Class clazz, IAttributeParser attributeParser) {
        this.lessThanOrEqualToAttributeRegistry.put(clazz, attributeParser);
    }


    public Predicate getEqualAttributeValue(String value, Class clazz,
                                            CriteriaBuilder builder, Path<?> propertyPath, String pattern) {
        if (clazz == String.class) {
            return builder.like((jakarta.persistence.criteria.Expression<String>) propertyPath, pattern);
        } else {
            return builder.equal(propertyPath, this.equalAttibuteRegistry.get(clazz).valueOf(value));
        }
    }

    public Predicate getNotEqualAttributeValue(Class clazz,
                                               CriteriaBuilder builder, Path<?> propertyPath, List<String> arguments) {
        if (clazz == String.class) {
            return builder.notEqual(propertyPath, arguments.get(0));
        } else {
            return builder.notEqual(propertyPath, this.notEqualAttributeRegistry.get(clazz).valueOf(arguments.get(0)));
        }
    }

    public Predicate getGreaterThanAttributeValue(Class clazz,
                                                  CriteriaBuilder builder, Path propertyPath, List<String> arguments) {
        IAttributeParser attributeParser = this.greaterThanAttributeRegistry.get(clazz);
        if (clazz == Integer.class)
            return builder.greaterThan(propertyPath, (Integer) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Long.class)
            return builder.greaterThan(propertyPath, (Long) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Float.class)
            return builder.greaterThan(propertyPath, (Float) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Double.class)
            return builder.greaterThan(propertyPath, (Double) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == LocalDateTime.class)
            return builder.greaterThan(propertyPath, (LocalDateTime) attributeParser.valueOf(arguments.get(0)));
        else
            return null;
    }

    public Predicate getGreaterThanOrEqualToAttributeValue(Class clazz,
                                                           CriteriaBuilder builder, Path propertyPath, List<String> arguments) {
        IAttributeParser attributeParser = this.greaterThanAttributeRegistry.get(clazz);
        if (clazz == Integer.class)
            return builder.greaterThanOrEqualTo(propertyPath, (Integer) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Long.class)
            return builder.greaterThanOrEqualTo(propertyPath, (Long) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Float.class)
            return builder.greaterThanOrEqualTo(propertyPath, (Float) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Double.class)
            return builder.greaterThanOrEqualTo(propertyPath, (Double) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == LocalDateTime.class)
            return builder.greaterThanOrEqualTo(propertyPath, (LocalDateTime) attributeParser.valueOf(arguments.get(0)));
        else
            return null;
    }

    public Predicate getLessThanAttributeValue(Class clazz,
                                               CriteriaBuilder builder, Path propertyPath, List<String> arguments) {
        IAttributeParser attributeParser = this.greaterThanAttributeRegistry.get(clazz);
        if (clazz == Integer.class)
            return builder.lessThan(propertyPath, (Integer) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Long.class)
            return builder.lessThan(propertyPath, (Long) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Float.class)
            return builder.lessThan(propertyPath, (Float) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Double.class)
            return builder.lessThan(propertyPath, (Double) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == LocalDateTime.class)
            return builder.lessThan(propertyPath, (LocalDateTime) attributeParser.valueOf(arguments.get(0)));
        else
            return null;
    }

    public Predicate getLessThanOrEqualToAttributeValue(Class clazz,
                                                        CriteriaBuilder builder, Path propertyPath, List<String> arguments) {
        IAttributeParser attributeParser = this.greaterThanAttributeRegistry.get(clazz);
        if (clazz == Integer.class)
            return builder.lessThanOrEqualTo(propertyPath, (Integer) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Long.class)
            return builder.lessThanOrEqualTo(propertyPath, (Long) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Float.class)
            return builder.lessThanOrEqualTo(propertyPath, (Float) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == Double.class)
            return builder.lessThanOrEqualTo(propertyPath, (Double) attributeParser.valueOf(arguments.get(0)));
        else if (clazz == LocalDateTime.class)
            return builder.lessThanOrEqualTo(propertyPath, (LocalDateTime) attributeParser.valueOf(arguments.get(0)));
        else
            return null;
    }

    class BooleanAttributeParser implements IAttributeParser {
        @Override
        public Boolean valueOf(String value) {
            return Boolean.parseBoolean(value);
        }
    }

    class ByteAttributeParser implements IAttributeParser {
        @Override
        public Byte valueOf(String value) {
            return Byte.parseByte(value);
        }
    }

    class ShortAttributeParser implements IAttributeParser {
        @Override
        public Short valueOf(String value) {
            return Short.parseShort(value);
        }
    }

    class IntegerAttributeParser implements IAttributeParser {
        @Override
        public Integer valueOf(String value) {
            return Integer.parseInt(value);
        }
    }

    class LongAttributeParser implements IAttributeParser {
        @Override
        public Long valueOf(String value) {
            return Long.parseLong(value);
        }
    }

    class FloatAttributeParser implements IAttributeParser {
        @Override
        public Float valueOf(String value) {
            return Float.parseFloat(value);
        }
    }

    class DoubleAttributeParser implements IAttributeParser {
        @Override
        public Double valueOf(String value) {
            return Double.parseDouble(value);
        }
    }

    class LocalDateTimeAttributeParser implements IAttributeParser {
        @Override
        public LocalDateTime valueOf(String value) {
            return LocalDateTime.parse(value);
        }
    }

}