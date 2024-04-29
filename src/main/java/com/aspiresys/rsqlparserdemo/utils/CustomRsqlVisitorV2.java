package com.aspiresys.rsqlparserdemo.utils;

import cz.jirutka.rsql.parser.ast.*;
import org.apache.commons.lang3.StringUtils;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CustomRsqlVisitorV2<T> implements RSQLVisitor<Specification<T>, Root<T>> {

    @Override
    public Specification<T> visit(OrNode orNode, Root<T> root) {
        List<Specification<T>> specifications = new ArrayList<>();
        for (Node child : orNode.getChildren()) {
            Specification<T> specification = child.accept(this, root);
            specifications.add(specification);
        }
        return specifications.stream().reduce(Specification::or).orElse(null);
    }

    @Override
    public Specification<T> visit(ComparisonNode node, Root<T> r) {
        String selector = node.getSelector();
        String operator = node.getOperator().getSymbol();
        List<String> arguments = node.getArguments();

        switch (operator) {
            case "==" -> {
                String value = arguments.get(0);
                String pattern = value.replace('*', '%');
                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    return AttributeParserManager.getAttributeParserManagerInstance().getEqualAttributeValue(
                            value, propertyPath.getJavaType(),
                            builder, propertyPath, pattern
                    );
                };
            }
            case "=like=" -> {
                String value = arguments.get(0);
                value = value.trim();
                boolean containsWildcard = value.contains("*");
                if (!StringUtils.isNumeric(value)) {
                    if (!containsWildcard) {
                        value = "*" + value + "*";
                    }
                }
                String pattern = value.replace('*', '%');
                final String finalValue = value;


                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    return AttributeParserManager.getAttributeParserManagerInstance().getEqualAttributeValue(
                            finalValue, propertyPath.getJavaType(),
                            builder, propertyPath, pattern
                    );
                };
            }
            case "=in=" -> {
                List<String> values = new ArrayList<>(arguments);
                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    List<Object> inputValues = castArguments(values, propertyPath);
                    return propertyPath.in(inputValues);
                };
            }
            case "!=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    return AttributeParserManager.getAttributeParserManagerInstance().getNotEqualAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=gt=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    return AttributeParserManager.getAttributeParserManagerInstance().getGreaterThanAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=ge=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    return AttributeParserManager.getAttributeParserManagerInstance().getGreaterThanOrEqualToAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=lt=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    return AttributeParserManager.getAttributeParserManagerInstance().getLessThanAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=le=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = parseNestedProperty(root, selector);
                    return AttributeParserManager.getAttributeParserManagerInstance().getLessThanOrEqualToAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
        }
        return null;
    }

    private Path<?> parseNestedProperty(Path<?> root, String property) {
        if (property.contains(".")) {
            String[] nestedProperties = property.split("\\.");
            Path<?> path = root;
            for (String nestedProperty : nestedProperties) {
                path = path.get(nestedProperty);
            }
            return path;
        } else {
            return root.get(property);
        }
    }

    private List<Object> castArguments(List<String> values, Path<?> propertyExpression) {
        Class<?> type = propertyExpression.getJavaType();
        return values.stream().map(arg -> {
            if (type.equals(Integer.class) || type.equals(int.class)) return Integer.parseInt(arg);
            else if (type.equals(Long.class) || type.equals(long.class)) return Long.parseLong(arg);
            else if (type.equals(Double.class) || type.equals(double.class)) return Double.parseDouble(arg);
            else if (type.equals(Float.class) || type.equals(float.class)) return Float.parseFloat(arg);
            else if (type.equals(Byte.class) || type.equals(byte.class)) return Byte.parseByte(arg);
            else if (type.equals(Short.class) || type.equals(short.class)) return Short.parseShort(arg);
            else if (type.equals(Boolean.class) || type.equals(boolean.class)) return Boolean.parseBoolean(arg);
            else if (type.equals(Character.class) || type.equals(char.class)) return arg.charAt(0);
            else return arg;
        }).collect(Collectors.toList());
    }

    @Override
    public Specification<T> visit(AndNode andNode, Root<T> root) {
        List<Specification<T>> specifications = new ArrayList<>();
        for (Node child : andNode.getChildren()) {
            Specification<T> specification = child.accept(this, root);
            specifications.add(specification);
        }
        return specifications.stream().reduce(Specification::and).orElse(null);
    }
}
