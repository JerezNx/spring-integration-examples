package xyz.jerez.spring.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import org.junit.jupiter.api.Test;
import xyz.jerez.spring.json.bean.JacksonBean;
import xyz.jerez.spring.json.bean.JacksonBean1;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author liqilin
 * @since 2021/3/5 15:52
 */
public class JacksonTests extends BaseTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void test() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(JacksonBean.getInstance()));
    }

    @Test
    void test1() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        final InputStream inputStream = getJsonFile();
        final JacksonBean bean = objectMapper.readValue(inputStream, JacksonBean.class);
        System.out.println(bean);
    }

    /**
     * {@link JsonProperty} 不管是加在属性上，还是getter 或 setter 上，效果都一样
     * {@link com.fasterxml.jackson.annotation.JsonFormat 只会生效于 序列化，反序列化时不生效}
     */
    @Test
    void test2() throws JsonProcessingException {
        System.out.println(objectMapper.writeValueAsString(JacksonBean1.getInstance()));
    }

    @Test
    void test3() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        final InputStream inputStream = getJsonFile();
        final JacksonBean1 bean = objectMapper.readValue(inputStream, JacksonBean1.class);
        System.out.println(bean);
    }

}
