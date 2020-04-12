package com.daphnis.mybatis.util;

import java.lang.reflect.Field;

/**
 * 反射工具类
 */
public class ReflectUtil {

  public static Field getFieldByFieldName(Object obj, String fieldName) {
    for (Class<?> superClass = obj.getClass(); superClass != Object.class;
        superClass = superClass.getSuperclass()) {
      try {
        return superClass.getDeclaredField(fieldName);
      } catch (NoSuchFieldException e) {
      }
    }
    return null;
  }

  public boolean containsField(Object obj, String fieldName) {
    return getFieldByFieldName(obj, fieldName) != null;
  }

  /**
   * 根据字段名称，利用反射访问对象中的私有字段
   *
   * @param obj       目标对象
   * @param fieldName 字段名称
   * @return
   * @throws SecurityException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  public static Object getValueByFieldName(Object obj, String fieldName)
      throws SecurityException, IllegalArgumentException, IllegalAccessException {
    Field field = getFieldByFieldName(obj, fieldName);
    Object value = null;
    if (field != null) {
      if (field.isAccessible()) {
        value = field.get(obj);
      } else {
        field.setAccessible(true);
        value = field.get(obj);
        field.setAccessible(false);
      }
    }
    return value;
  }

  /**
   * obj fieldName设置的属性值.
   *
   * @param obj
   * @param fieldName
   * @param value
   * @throws SecurityException
   * @throws NoSuchFieldException
   * @throws IllegalArgumentException
   * @throws IllegalAccessException
   */
  public static void setValueByFieldName(Object obj, String fieldName, Object value)
      throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
    Field field = obj.getClass().getDeclaredField(fieldName);
    if (field.isAccessible()) {
      field.set(obj, value);
    } else {
      field.setAccessible(true);
      field.set(obj, value);
      field.setAccessible(false);
    }
  }


}
