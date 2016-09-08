package my.ssh.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by zzy on 2015/9/30 0030.
 */
public class BeanUtils extends org.springframework.beans.BeanUtils {

    public static Map<String, String> toMap(Object bean) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        return org.apache.commons.beanutils.BeanUtils.describe(bean);
    }

    public static <T> T toBean(Class<T> clazz, Map map) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        T object = clazz.newInstance();
        org.apache.commons.beanutils.BeanUtils.populate(object, map);
        return object;
    }

    public static Object toBean(Object obj, Map map) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        org.apache.commons.beanutils.BeanUtils.populate(obj, map);
        return obj;
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null/* ||(Collection.class.isAssignableFrom(pd.getPropertyType()) && ((Collection)srcValue).size()==0)*/) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public static void copyPropertiesIgnoreNull(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    public static Serializable getPrimaryKeyValue(Object entry) throws InvocationTargetException, IllegalAccessException {
        if (null == entry) {
            return null;
        }
        Class c = entry.getClass();
        Method[] methods = c.getDeclaredMethods();
        for (Method m : methods) {
            if (null != m.getAnnotation(Id.class)) {
                return (Serializable) m.invoke(entry);
            }
        }

        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (null != f.getAnnotation(Id.class)) {
                for (Method m : methods) {
                    if (m.getName().equalsIgnoreCase("get" + f.getName())) {
                        return (Serializable) m.invoke(entry);
                    }
                }
            }
        }

        return null;
    }
}
