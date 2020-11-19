package xyz.jerez.spring.logback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import xyz.jerez.spring.logback.testname.TestName;
import xyz.jerez.spring.logback.testpackage.TestNameInPackage;
import xyz.jerez.spring.logback.testpackage.TestPackage;

@SpringBootApplication
public class SpringLogbackApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringLogbackApplication.class, args);
        TestPackage p = context.getBean(TestPackage.class);
        p.print("package");
        TestName n = context.getBean(TestName.class);
        n.print("name");
        TestNameInPackage np = context.getBean(TestNameInPackage.class);
        np.print("name in package");
    }

}
