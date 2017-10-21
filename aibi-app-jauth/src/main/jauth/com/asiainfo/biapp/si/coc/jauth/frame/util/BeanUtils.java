package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.springframework.beans.BeansException;

import com.asiainfo.biapp.si.coc.jauth.frame.entity.BaseEntity;

/**
 * 对BeanUtils的copyProperties方法进行重写，不复制null属性
 * @author ljs
 *
 */
public abstract class BeanUtils extends org.springframework.beans.BeanUtils {

	public static void copyProperties(Object source, Object target)
			throws BeansException {
		Class<?> actualEditable = target.getClass();
		PropertyDescriptor[] targetPds = getPropertyDescriptors(actualEditable);
		for (PropertyDescriptor targetPd : targetPds) {
			if (targetPd.getWriteMethod() != null) {
				PropertyDescriptor sourcePd = getPropertyDescriptor(source
						.getClass(), targetPd.getName());
				if (sourcePd != null && sourcePd.getReadMethod() != null) {
					try {
						Method readMethod = sourcePd.getReadMethod();
						if (!Modifier.isPublic(readMethod.getDeclaringClass()
								.getModifiers())) {
							readMethod.setAccessible(true);
						}
						Object value = readMethod.invoke(source);
						// 这里判断以下value是否为空 当然这里也能进行一些特殊要求的处理 例如绑定时格式转换等等
						Method writeMethod = targetPd.getWriteMethod();//获取写方法(set)
						if (value != null && !(value instanceof BaseEntity)) {
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(target, value);
						}else if (value instanceof BaseEntity) {
							Object targetChd = readMethod.invoke(target);
							copyProperties(value, targetChd);//对自己所写的子对象进行递归
							if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}
							writeMethod.invoke(targetChd, value);
						}
					} catch (Throwable ex) {
//						throw new FatalBeanException(
//								"Could not copy properties from source to target",
//								ex);
						System.out.println("Could not copy property " + targetPd.getName() + " from source to target");
					}
				}
			}
		}
	}
}