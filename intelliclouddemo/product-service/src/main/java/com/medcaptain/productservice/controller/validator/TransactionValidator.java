package com.medcaptain.productservice.controller.validator;

import com.medcaptain.enums.HttpResponseCode;
import com.medcaptain.exception.ApiRuntimeException;
import com.medcaptain.productservice.annotation.VerifyNotEmpty;
import com.medcaptain.productservice.entity.dto.mongo.ProductEvent;
import com.medcaptain.productservice.entity.dto.mongo.ProductProperty;
import com.medcaptain.productservice.entity.dto.mongo.ProductServiceEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 对新增功能进行参数校验，必要字段是否为空
 * hengyu.luo
 */
public class TransactionValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(ProductProperty.class) || aClass.equals(ProductEvent.class) || aClass.equals(ProductServiceEntity.class);
    }

    @Override
    public void validate(Object o, Errors errors) {
        if (null == o) {
            throw new ApiRuntimeException(HttpResponseCode.ERROR_OBJECT_IS_NULL, null);
        }
        Class c = o.getClass();
        try {
            Method[] methodList = c.getMethods();
            for (Method method : methodList) {
                if (method.getAnnotation(VerifyNotEmpty.class) != null) {
                    if ("".equals(method.invoke(o)))
                        throw new ApiRuntimeException(HttpResponseCode.ERROR_TRANSACTION_NAME_IS_NULL, null);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
