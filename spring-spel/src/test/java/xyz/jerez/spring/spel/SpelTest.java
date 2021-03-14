package xyz.jerez.spring.spel;

import org.junit.jupiter.api.Test;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * @author liqilin
 * @since 2021/3/8 15:42
 */
public class SpelTest {

    @Test
    void test() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("('Hello' + ' World').concat(#end)");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("end", "!");
        System.out.println(expression.getValue(context));
    }

    @Test
    void test1() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#key.split(',')");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("key", "aa,bb,cc");
        System.out.println(expression.getValue(context));
    }

    @Test
    void test2() {
        ExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression("#key.contains('aaa')");
        EvaluationContext context = new StandardEvaluationContext();
        context.setVariable("key", "aa,bb,cc");
        System.out.println(expression.getValue(context));
    }

}
