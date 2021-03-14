package xyz.jerez.spring.validator.util;

import com.alibaba.fastjson.JSON;
import org.springframework.util.Assert;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * bean util
 *
 * @author liqilin
 * @since 2020/12/18 15:22
 */
public class BeanUtils {

    public static void setProperty(Object object, String propertyName, Object params) throws Exception {
        final PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(object.getClass(), propertyName);
        Assert.notNull(propertyDescriptor, "无此属性");
        Method writeMethod = propertyDescriptor.getWriteMethod();
        writeMethod.invoke(object, params);
    }

    public static Object getProperty(Object object, String propertyName) throws Exception {
        final PropertyDescriptor propertyDescriptor = org.springframework.beans.BeanUtils.getPropertyDescriptor(object.getClass(), propertyName);
        Assert.notNull(propertyDescriptor, "无此属性");
        Method readMethod = propertyDescriptor.getReadMethod();
        return readMethod.invoke(object);
    }

    public static <T> T deepClone(T t) {
        if (t == null) {
            return null;
        }
        return JSON.parseObject(JSON.toJSONString(t), (Type) t.getClass());
    }

    public static <T> List<T> deepClone(List<T> list) {
        if (list == null) {
            return null;
        }
        if (list.size() == 0) {
            return new ArrayList<>();
        }
        final  Class<T> clazz = (Class<T>) list.get(0).getClass();
        return JSON.parseArray(JSON.toJSONString(list), clazz);
    }
}
