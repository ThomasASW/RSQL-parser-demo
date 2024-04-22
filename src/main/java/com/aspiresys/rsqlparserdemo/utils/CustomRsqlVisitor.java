package com.aspiresys.rsqlparserdemo.utils;

import cz.jirutka.rsql.parser.ast.*;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CustomRsqlVisitor<T> implements RSQLVisitor<Specification<T>, Root<T>> {
    @Override
    public Specification<T> visit(AndNode andNode, Root<T> tRoot) {
        Specification<T> left = andNode.getChildren().get(0).accept(this, tRoot);
        Specification<T> right = andNode.getChildren().get(1).accept(this, tRoot);
        return Specification.where(left).and(right);
    }

    @Override
    public Specification<T> visit(OrNode orNode, Root<T> tRoot) {
        List<Specification<T>> specifications = new ArrayList<>();
        for (Node child : orNode.getChildren()) {
            Specification<T> specification = child.accept(this, tRoot);
            specifications.add(specification);
        }
        return specifications.stream().reduce(Specification::or).orElse(null);
    }

    @Override
    public Specification<T> visit(ComparisonNode comparisonNode, Root<T> tRoot) {
        String selector = comparisonNode.getSelector();
        String operator = comparisonNode.getOperator().getSymbol();
        List<String> arguments = comparisonNode.getArguments();

        switch (operator) {
            case "==" -> {
                String value = arguments.get(0);
                String pattern = value.replace('*', '%');
                return (root, query, builder) -> {
                    Path<?> propertyPath = getPath(selector, root);
                    return AttributeParserManager.getAttributeParserManagerInstance().getEqualAttributeValue(
                            value, propertyPath.getJavaType(),
                            builder, propertyPath, pattern
                    );
                };
            }
            case "=in=" -> {
                List<Object> values = new ArrayList<>(arguments);
                return (root, query, builder) -> getPath(selector, root).in(values);
            }
            case "!=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = getPath(selector, root);
                    return AttributeParserManager.getAttributeParserManagerInstance().getNotEqualAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=gt=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = getPath(selector, root);
                    return AttributeParserManager.getAttributeParserManagerInstance().getGreaterThanAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=ge=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = getPath(selector, root);
                    return AttributeParserManager.getAttributeParserManagerInstance().getGreaterThanOrEqualToAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=lt=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = getPath(selector, root);
                    return AttributeParserManager.getAttributeParserManagerInstance().getLessThanAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
            case "=le=" -> {
                return (root, query, builder) -> {
                    Path<?> propertyPath = getPath(selector, root);
                    return AttributeParserManager.getAttributeParserManagerInstance().getLessThanOrEqualToAttributeValue(
                            propertyPath.getJavaType(), builder, propertyPath, arguments
                    );
                };
            }
        }
        return null;
    }

    private Path<?> getPath(String selector, Root<T> root) {
        String[] selectors = selector.split("\\.");
        if (selectors.length > 1) {
            Join<T, ?> join = root.join(selectors[0]);
            return getJoinedPath(selectors, 1, join);
        } else {
            return root.get(selector);
        }
    }

    private Path<?> getJoinedPath(String[] selectors, Integer index, Join<T, ?> join) {
        if ((selectors.length - 1) > index) {
            Join<T, ?> newJoin = join.join(selectors[index]);
            return getJoinedPath(selectors, index + 1, newJoin);
        } else {
            return join.get(selectors[index]);
        }
    }
}
