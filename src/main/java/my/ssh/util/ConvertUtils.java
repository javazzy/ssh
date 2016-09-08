package my.ssh.util;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by zzy on 2015/12/29 0029.
 */
public class ConvertUtils {


    /**
     * 将字符串根据参数类型转换相应类型
     * @param classType
     * @param data
     * @return
     */
    private static Serializable sringToClassType(Class<?> classType, String data){

        if (!classType.equals(String.class)){
            if(org.apache.commons.lang3.StringUtils.isBlank(data)) {
                return null;
            }else{
                if (classType.equals(Integer.class) || classType.equals(int.class)) {
                    return Integer.parseInt(data);
                } else if (classType.equals(Long.class) || classType.equals(long.class)) {
                    return Long.parseLong(data);
                } else if (classType.equals(Float.class) || classType.equals(float.class)) {
                    return Float.parseFloat(data);
                } else if (classType.equals(Double.class) || classType.equals(double.class)) {
                    return Double.parseDouble(data);
                } else if (classType.equals(Short.class) || classType.equals(short.class)) {
                    return Short.parseShort(data);
                } else if (classType.equals(Boolean.class) || classType.equals(boolean.class)) {
                    return Boolean.parseBoolean(data);
                }
            }
        }
        return data;
    }

    /**
     * 将字符串值转换为泛型中带有ID注解的成员变量的类型的值
     * @param key 字符串值
     * @param c
     * @return 目标类型值
     * @throws Exception
     */
    public static Serializable toPrimaryData(Class<?> c,String key) throws Exception {
        Serializable keyObj = null;
        Method[] methods = c.getDeclaredMethods();
        for(Method m : methods){
            if(null != m.getAnnotation(Id.class)){
                if (null == keyObj) {
                    keyObj = ConvertUtils.sringToClassType(m.getReturnType(), key);
                    break;
                }
            }
        }

        if (null != keyObj) {
            return keyObj;
        }

        Field[] fields = c.getDeclaredFields();
        for (Field f : fields) {
            if (null != f.getAnnotation(Id.class)) {
                if (null == keyObj) {
                    keyObj = ConvertUtils.sringToClassType(f.getType(), key);
                    break;
                }
            }
        }
        if (null != keyObj) {
            return keyObj;
        }else{
            throw new Exception("参数无效！");
        }
    }
}
