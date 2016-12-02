package com.hengtiansoft.xinyunlian.test.reflect;



import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用于反射处理的工具类
 * @author rujianbin
 *
 */
public class ReflectHelper {
    private static Map<String, Method> setMethodMap = new HashMap();
    private static final Map<Class<?>, Class<?>> primitive = new HashMap();
    private static final Map<Class<?>, Object> primitiveDefaultValue;
    private static final Map<Object,Boolean> booleanMap=new HashMap<Object,Boolean>();;
    static {
        primitive.put(Integer.TYPE, Integer.class);
        primitive.put(Long.TYPE, Long.class);
        primitive.put(Short.TYPE, Short.class);
        primitive.put(Float.TYPE, Float.class);
        primitive.put(Double.TYPE, Double.class);
        primitive.put(Byte.TYPE, Byte.class);
        primitive.put(Character.TYPE, Character.class);
        primitive.put(Boolean.TYPE, Boolean.class);
        primitiveDefaultValue = new HashMap();
        primitiveDefaultValue.put(Integer.TYPE, Integer.valueOf(0));
        primitiveDefaultValue.put(Long.TYPE, Integer.valueOf(0));
        primitiveDefaultValue.put(Short.TYPE, Integer.valueOf(0));
        primitiveDefaultValue.put(Float.TYPE, Integer.valueOf(0));
        primitiveDefaultValue.put(Double.TYPE, Integer.valueOf(0));
        primitiveDefaultValue.put(Byte.TYPE, Integer.valueOf(0));
        primitiveDefaultValue.put(Character.TYPE, Character.valueOf(' '));
        primitiveDefaultValue.put(Boolean.TYPE, Boolean.valueOf(false));
        booleanMap.put("Y", true);
        booleanMap.put("TRUE", true);
        booleanMap.put("N", false);
        booleanMap.put("FALSE", false);
        
    }

    public ReflectHelper() {
    }

    public static <T> T newInstance(String className) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Class clazz = Class.forName(className);
        return (T) newInstance(clazz);
    }

    public static <T> T newInstance(Class<T> clazz) throws InstantiationException, IllegalAccessException {
        return clazz.newInstance();
    }

    public static Object getFieldValue(Object obj, String fieldName) throws NoSuchMethodException {
        Method method;
        try {
            method = obj.getClass().getMethod("get" + upperCaseFirst(fieldName), new Class[0]);
        } catch (NoSuchMethodException var7) {
            try {
                method = obj.getClass().getMethod("is" + upperCaseFirst(fieldName), new Class[0]);
            } catch (NoSuchMethodException var6) {
                throw var6;
            }
        }

        try {
            return method.invoke(obj, new Object[0]);
        } catch (Exception var5) {
            return null;
        }
    }

    public static Object getFieldValueIgnoreException(Object obj, String fieldName) {
        try {
            return getFieldValue(obj, fieldName);
        } catch (NoSuchMethodException var3) {
            return null;
        }
    }

    public static void setFieldValue(Object obj, String fieldName, Object value) throws NoSuchMethodException, Exception, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if(obj != null) {
            String methodName = "set" + upperCaseFirst(fieldName);
            Method method = getMethod(obj.getClass(), methodName);
            Class[] ts = method.getParameterTypes();
            Object transformed = transformType(value, ts[0]);
            method.invoke(obj, new Object[]{transformed});
        }
    }

    private static Method getMethod(Class<?> clazz, String methodName) throws NoSuchMethodException {
        String key = clazz.getName() + "." + methodName;
        Method method = (Method)setMethodMap.get(key);
        if(method == null) {
            Method[] ms = clazz.getMethods();
            if(ms != null) {
                Method[] var8 = ms;
                int var7 = ms.length;

                for(int var6 = 0; var6 < var7; ++var6) {
                    Method m = var8[var6];
                    if(m.getName().equals(methodName)) {
                        Class[] ts = m.getParameterTypes();
                        if(ts != null && ts.length == 1) {
                            method = m;
                            setMethodMap.put(key, m);
                            break;
                        }
                    }
                }
            }
        }

        if(method == null) {
            throw new NoSuchMethodException(key);
        } else {
            return method;
        }
    }

    public static Object transformType(Object from, Type toType) throws Exception {
        Class to = (Class)toType;
        if(from == null) {
            return primitiveDefaultValue.get(to);
        } else if(to.isAssignableFrom(from.getClass())) {
            return from;
        } else if(String.class.isAssignableFrom(to)) {
            return from.toString();
        } else {
            if(Date.class.isAssignableFrom(to)) {
                try {
                    String e1 = from.toString();
                    switch(e1.length()) {
                    case 8:
                        return (new SimpleDateFormat("yyyyMMdd")).parse(e1);
                    case 10:
                        return (new SimpleDateFormat("yyyy-MM-dd")).parse(e1);
                    case 16:
                        return (new SimpleDateFormat("yyyy-MM-dd HH:mm")).parse(e1);
                    case 19:
                        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).parse(e1);
                    default:
                        return (new SimpleDateFormat("yyyy-MM-dd")).parse(e1);
                    }
                } catch (ParseException var5) {
                    ;
                }
            }else if(Boolean.class.isAssignableFrom(to)){
            	if(booleanMap.containsKey(from)){
            		return booleanMap.get(from);
            	}
            }else if(Integer.class.isAssignableFrom(to)){
            	return Integer.valueOf(from.toString().trim());
            }

            try {
                if(to.isPrimitive()) {
                    to = (Class)primitive.get(to);
                }

                Constructor e = to.getDeclaredConstructor(new Class[]{from.getClass()});
                return e.newInstance(new Object[]{from});
            } catch (Exception var4) {
                throw new Exception("ReflectHelper 无法完成" + from.getClass() + "到" + to + "的转换", var4);
            }
        }
    }
    
    public static String upperCaseFirst(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
    
}
