package hello.core.member.autowired;

import hello.core.member.Member;
import io.micrometer.common.lang.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutowiredTest {

    @Test
    void AutowiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {
        @Autowired(required = false)
        public void setNoBean1(Member member) {
            System.out.println("TestBean.setNoBean1 = " + member);
        }

        @Autowired
        public void setNoBean2(@Nullable Member member2) {
            System.out.println("TestBean.setNoBean1 = " +  member2);
        }

        @Autowired
        public void setNoBean3(Optional<Member> member3) {
            System.out.println("TestBean.setNoBean1 = " + member3);
        }
    }
}
