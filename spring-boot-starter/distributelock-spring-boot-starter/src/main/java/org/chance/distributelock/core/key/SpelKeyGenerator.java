package org.chance.distributelock.core.key;

import org.chance.distributelock.api.key.KeyGenerator;
import org.chance.distributelock.core.exception.EvaluationConvertException;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * SpelKeyGenerator
 *
 * @author GengChao
 * @email chao_geng@sui.com
 * @date 2019/9/23
 */
public class SpelKeyGenerator extends CachedExpressionEvaluator implements KeyGenerator {

    private final ConversionService conversionService;
    private final Map<ExpressionKey, Expression> conditionCache = new ConcurrentHashMap<>();
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>();

    public SpelKeyGenerator(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    @Override
    public List<String> resolveKeys(final String lockKeyPrefix, final String expression, final Object object, final Method method, final Object[] args) {
        final EvaluationContext context = new MethodBasedEvaluationContext(object, method, args, super.getParameterNameDiscoverer());
        context.setVariable("executionPath", object.getClass().getCanonicalName() + "." + method.getName());

        final List<String> keys = convertResultToList(getExpression(this.conditionCache, new AnnotatedElementKey(method, object.getClass()), expression).getValue(context));

        if (keys.stream().anyMatch(Objects::isNull)) {
            throw new EvaluationConvertException("null keys are not supported: " + keys);
        }

        if (StringUtils.isEmpty(lockKeyPrefix)) {
            return keys;
        }

        return keys.stream().map(key -> lockKeyPrefix + key).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    protected List<String> convertResultToList(final Object expressionValue) {
        final List<String> list;

        if (expressionValue == null) {
            throw new EvaluationConvertException("Expression evaluated in a null");
        }

        if (expressionValue instanceof Iterable) {
            final TypeDescriptor genericCollection = TypeDescriptor.collection(Collection.class, TypeDescriptor.valueOf(Object.class));
            final TypeDescriptor stringList = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class));

            list = (List<String>) conversionService.convert(expressionValue, genericCollection, stringList);
        } else if (expressionValue.getClass().isArray()) {
            final TypeDescriptor genericArray = TypeDescriptor.array(TypeDescriptor.valueOf(Object.class));
            final TypeDescriptor stringList = TypeDescriptor.collection(List.class, TypeDescriptor.valueOf(String.class));

            list = (List<String>) conversionService.convert(expressionValue, genericArray, stringList);
        } else {
            list = Collections.singletonList(expressionValue.toString());
        }

        if (CollectionUtils.isEmpty(list)) {
            throw new EvaluationConvertException("Expression evaluated in an empty list");
        }

        return list;
    }

}
