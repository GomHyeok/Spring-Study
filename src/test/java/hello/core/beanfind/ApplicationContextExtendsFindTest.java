package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모로 조회시 자식이 둘 이상이면 중복 오류")
    void findByParantType() {
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));
    }

    @Test
    @DisplayName("부모로 조회시 자식이 둘 이상이면 중복 오류시 빈 이름으로")
    void findByParantTypeBeanName() {
        DiscountPolicy rateDiscount = ac.getBean("rateDiscount", DiscountPolicy.class);

        assertThat(rateDiscount).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("하위 타입으로 조회")
    void findBySubType() {
        DiscountPolicy rateDiscount = ac.getBean(RateDiscountPolicy.class);

        assertThat(rateDiscount).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        assertThat(beansOfType.size()).isEqualTo(2);
        for (String s : beansOfType.keySet()) {
            System.out.println("key = " + s + " value = " + beansOfType.get(s));
        }
    }

    @Test
    @DisplayName(" 모두 조회하기")
    void findAllBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String s : beansOfType.keySet()) {
            System.out.println("key = " + s + " value = " + beansOfType.get(s));
        }
    }

    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscount() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscount() {
            return new FixDiscountPolicy();
        }
    }

}
