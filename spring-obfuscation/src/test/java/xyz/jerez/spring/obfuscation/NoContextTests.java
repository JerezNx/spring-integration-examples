package xyz.jerez.spring.obfuscation;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liqilin
 * @since 2020/12/5 17:09
 */
public class NoContextTests {

    @Test
    void match() {
        String regex = "(T_V)([\\d])|(xx)+";
        String source = "T_V123,TX_V1,T_V234";
//        String source = "T_V1";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        System.out.println("是否完全匹配:" + matcher.matches());
        if (matcher.find()) {
            for (int i = 0; i <= matcher.groupCount(); i++) {
                String group = matcher.group(i);
                System.out.println("number " + i + ":" + group );
            }
        }
    }

}
