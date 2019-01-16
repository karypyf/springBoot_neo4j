package com.example.demo;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

/**
 * 简单封装Jackson,提供一些操作json的静态方法
 * 
 * @author PYF
 * @date 2018年3月19日 下午7:36:22
 */
public class JsonSimple {

  private static Logger logger = LoggerFactory.getLogger(JsonSimple.class);
  private static ObjectMapper mapper = null;

  static {
    mapper = new ObjectMapper();
    // 设置输出时包含属性的风格
    mapper.setSerializationInclusion(Include.NON_EMPTY);
    // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  }

  /**
   * Object可以是POJO，也可以是Collection或数组。 如果对象为Null, 返回"null". 如果集合为空集合, 返回"[]".
   */
  public static String toJson(Object object) {

    try {
      return mapper.writeValueAsString(object);
    } catch (IOException e) {
      logger.warn("write to json string error:" + object, e);
      return null;
    }
  }

  /**
   * 默认返回Map<String,?>
   * 
   * @see #fromJson(String, JavaType)
   */
  @SuppressWarnings("rawtypes")
  public static Map fromJson(String jsonString) {
    return fromJson(jsonString, Map.class);
  }

  /**
   * 反序列化POJO或简单Collection如List<String>.或 List（默认返回Map）
   * 
   * 如果JSON字符串为Null或"null"字符串, 返回Null. 如果JSON字符串为"[]", 返回空集合.
   * 
   * 如需反序列化复杂Collection如List<MyBean>, 请使用fromJson(String, JavaType)
   * 
   * @see #fromJson(String, JavaType)
   */
  public static <T> T fromJson(String jsonString, Class<T> clazz) {
    if (StringUtils.isEmpty(jsonString)) {
      return null;
    }

    try {
      return mapper.readValue(jsonString, clazz);
    } catch (IOException e) {
      logger.warn("parse json string error:" + jsonString, e);
      return null;
    }
  }

  /**
   * 反序列化复杂Collection如List<Bean>, 先使用createCollectionType()或contructMapType()构造类型, 然后调用本函数.
   * 
   * @see #createCollectionType(Class, Class...)
   */
  @SuppressWarnings("unchecked")
  public static <T> T fromJson(String jsonString, JavaType javaType) {
    if (StringUtils.isEmpty(jsonString)) {
      return null;
    }

    try {
      return (T) mapper.readValue(jsonString, javaType);
    } catch (IOException e) {
      logger.warn("parse json string error:" + jsonString, e);
      return null;
    }
  }

  /**
   * 构造Collection类型.
   */
  public static JavaType contructCollectionType(
      @SuppressWarnings("rawtypes") Class<? extends Collection> collectionClass,
      Class<?> elementClass) {
    return mapper.getTypeFactory().constructCollectionType(collectionClass, elementClass);
  }

  /**
   * 构造Map类型.
   */
  public static JavaType contructMapType(
      @SuppressWarnings("rawtypes") Class<? extends Map> mapClass, Class<?> keyClass,
      Class<?> valueClass) {
    return mapper.getTypeFactory().constructMapType(mapClass, keyClass, valueClass);
  }

  /**
   * 当JSON里只含有Bean的部分屬性時，更新一個已存在Bean，只覆蓋該部分的屬性.
   */
  public static void update(String jsonString, Object object) {
    try {
      mapper.readerForUpdating(object).readValue(jsonString);
    } catch (JsonProcessingException e) {
      logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
    } catch (IOException e) {
      logger.warn("update json string:" + jsonString + " to object:" + object + " error.", e);
    }
  }

  /**
   * 輸出JSONP格式數據.
   */
  public static String toJsonP(String functionName, Object object) {
    return toJson(new JSONPObject(functionName, object));
  }
}
