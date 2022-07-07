package com.hae.util;

import com.hae.web.rest.errors.NoIdFieldException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.*;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

@SuppressWarnings({"rawtypes","unchecked"})
@Slf4j
public class ObjectSetUtil {

	public static boolean isPrimitive(Object object) {
		return  object instanceof Boolean ||
				object instanceof Character ||
				object instanceof String ||
				object instanceof Number ||
				object instanceof Comparable ||
				object.getClass().isPrimitive();
	}

	public static String[] getNullPropertyNames(Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for ( PropertyDescriptor pd : pds ) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if(srcValue == null) emptyNames.add(pd.getName());
		}

		emptyNames.add("version");
		emptyNames.add("createdBy");
		emptyNames.add("createdDate");
		emptyNames.add("lastModifiedBy");
		emptyNames.add("lastModifiedDate");

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static String[] getNullPropertyNames(Object source, @Nullable String... ignoreProperties) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set<String> emptyNames = new HashSet<String>();
		for ( PropertyDescriptor pd : pds ) {
			Object srcValue = src.getPropertyValue(pd.getName());
			if(srcValue == null) emptyNames.add(pd.getName());
		}

		emptyNames.add("version");
		emptyNames.add("createdBy");
		emptyNames.add("createdDate");
		emptyNames.add("lastModifiedBy");
		emptyNames.add("lastModifiedDate");

		if(ignoreProperties != null) {
			emptyNames.addAll(Arrays.asList(ignoreProperties));
		}

		String[] result = new String[emptyNames.size()];
		return emptyNames.toArray(result);
	}

	public static void copyProperties(Object source, Object target, @Nullable Class<?> editable, @Nullable String... ignoreProperties) throws BeansException {
		Assert.notNull(source, "Source must not be null");
		Assert.notNull(target, "target must not be null");

		Class<?> actualEditable = target.getClass();
		if(editable != null) {
			if(!editable.isInstance(target)) {
				throw new IllegalArgumentException("Target class [" + target.getClass().getName() + "] not assignable to Editable class [" + editable.getName() + "]");
			}
			actualEditable = editable;
		}

		PropertyDescriptor[] targetPds = BeanUtils.getPropertyDescriptors(actualEditable);
		List<String> ignoreList = (ignoreProperties != null ? Arrays.asList(ignoreProperties) : null);

		for(PropertyDescriptor targetPd : targetPds) {
			Method writeMethod = targetPd.getWriteMethod();
			if(writeMethod != null && (ignoreList == null || !ignoreList.contains(targetPd.getName()))) {
				PropertyDescriptor sourcePd = BeanUtils.getPropertyDescriptor(source.getClass(), targetPd.getName());
				if(sourcePd != null) {
					Method readMethod = sourcePd.getReadMethod();
					if(readMethod != null && ClassUtils.isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
						try {
							if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object newValue = readMethod.invoke(source);
							if(!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
								writeMethod.setAccessible(true);
							}

							readMethod = targetPd.getReadMethod();
							if(!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
								readMethod.setAccessible(true);
							}
							Object oldValue = readMethod.invoke(target);

							// 값이 다른 경우에만 set 하도록 함.
							if(!newValue.equals(oldValue)) {
								if(isPrimitive(newValue)) {
									writeMethod.invoke(target, newValue);
								}
								if(objectManyToOne(source, sourcePd)) {
									writeMethod.invoke(target, newValue);
								}
								else {
									if(oldValue != null && newValue != null) {
										if(oldValue instanceof Collection) {
											Collection oldList = (Collection)oldValue;
											Collection newList = (Collection)newValue;

											List<Object> addList = new ArrayList<>();
											List<Object> removeList = new ArrayList<>();

											try {
												// 기존 리스트에서 삭제대상과 변경대상 처리
												for (Object item : oldList) {
													Object findItem = findObject(newList, item);

													// 같은 ID가 없는 경우 삭제대상
													if(findItem == null) {
														removeList.add(item);
													}
													// 같은 ID인 경우
													else {
														copyProperties(findItem, item, null, getNullPropertyNames(findItem));
													}
												}
												// 신규 리스트에서 추가 대상 식별
												for (Object item : newList) {
													Object findItem = findObject(oldList, item);
													// 같은 ID가 없는 경우 추가 대상
													if(findItem == null) {
														addList.add(item);
													}
												}

												oldList.removeAll(removeList);
												oldList.addAll(addList);
											} catch (NoIdFieldException err) {
												// Entity가 아닌 경우에는 리스트 통으로 교체
												writeMethod.invoke(target, newValue);
											}
										}
										else {
											copyProperties(newValue, oldValue, null, getNullPropertyNames(newValue));
										}
									}
									else {
										writeMethod.invoke(target, newValue);
									}
								}
							}
						} catch(Throwable ex) {
							throw new FatalBeanException("Could not copy propertiy '" + targetPd.getName() + "' from source to target", ex);
						}
					}
				}
			}
		}
	}

	private static Object findObject(Collection list, Object src) throws IllegalArgumentException, IllegalAccessException {
		if(src == null) return null;

		Object srcValue = objectIdValue(src);
		if(srcValue == null) return null;

		for(Object find : list) {
			Object findValue = objectIdValue(find);
			if(srcValue.equals(findValue)) {
				return find;
			}
		}

		return null;
	}

	private static boolean objectManyToOne(Object entity, PropertyDescriptor pd) {
		Class<? extends Object> clazz = entity.getClass();

		log.trace("pd = " + pd.getName());

		for(Field field : clazz.getDeclaredFields()) {

			log.trace("field.getAnnotation(ManyToOne.class) = " + field.getAnnotation(ManyToOne.class));
			log.trace("field.getName() = " + field.getName());

			if(field.getAnnotation(ManyToOne.class) != null && field.getName().equals(pd.getName())) {
				return true;
			}
		}

		return false;
	}

	private static Object objectIdValue(Object entity) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> clazz = entity.getClass();

		for(Field field : clazz.getDeclaredFields()) {
			if(field.getAnnotation(Id.class) != null || field.getName().equals("id")) {
				field.setAccessible(true);
				Object value = field.get(entity);
				if(value == null) {
					return null;
				}
				else {
					return value.toString();
				}
			}
		}

		throw new NoIdFieldException("No annotated id field");
	}

	public static Object getValueByPropertyNames(Object source, String name) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		PropertyDescriptor[] pds = src.getPropertyDescriptors();

		for ( PropertyDescriptor pd : pds ) {
			if(name.equals(pd.getName())) {
				return src.getPropertyValue(name);
			}
		}

		return null;
	}
}
