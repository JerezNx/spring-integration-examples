package xyz.jerez.spring.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.jupiter.api.Test;
import xyz.jerez.spring.json.bean.FastJsonBean;
import xyz.jerez.spring.json.bean.FastJsonBean1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author liqilin
 * @since 2021/3/5 15:28
 */
public class FastJsonTests extends BaseTest {

    @Test
    void serializer() {
//        将java对象序列化为json字符串
//        可通过SerializerFeature配置各种特性
        System.out.println(JSON.toJSONString(FastJsonBean.getInstance(), SerializerFeature.PrettyFormat));
    }

    @Test
    void deserializer() throws IOException {
        final InputStream inputStream = getJsonFile();
        final FastJsonBean bean = JSON.parseObject(inputStream, StandardCharsets.UTF_8, FastJsonBean.class, Feature.DisableFieldSmartMatch);
        System.out.println(bean);
    }

    /**
     * {@link com.alibaba.fastjson.annotation.JSONField} 注解可以配置在属性、setter、getter 上
     * 设置在setter上，就是反序列化时生效；getter上就是序列化时生效;属性上就是两者都生效
     *
     * {@link JSONField#format()} 可设置时间格式，其是JSONField的一个属性，自然区分在属性上、在setter上及在getter上
     * 其中，反序列化时，其默认支持了众多常见格式。但如果指定了反序列化的格式后，如果实际格式不符，就无法反序列化
     * eg:实际数据格式是UTC的，如果我们不指定具体格式，则会由默认支持的格式一个个试，最终反序列化成功，
     *     但如果指定了 yyyy-MM-dd HH:mm:ss，则由于格式不匹配，最终是Null
     */
    @Test
    void serializer1() {
        System.out.println(JSON.toJSONString(FastJsonBean1.getInstance(), SerializerFeature.PrettyFormat));
    }

    @Test
    void deserializer1() throws IOException {
        final InputStream inputStream = getJsonFile();
        final FastJsonBean1 bean = JSON.parseObject(inputStream, StandardCharsets.UTF_8, FastJsonBean1.class, Feature.DisableFieldSmartMatch);
        System.out.println(bean);
    }

}
