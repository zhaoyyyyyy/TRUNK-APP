package com.asiainfo.biapp.si.coc.jauth.frame.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

public final class ArrayUtil extends ArrayUtils {
	public static final String[] EMPTY_STRING_ARRAY = new String[0];

	public static String[] toStringArray(String value, String delim) {
		if (value != null) {
			return StringUtil.split(delim, value);
		}
		return EMPTY_STRING_ARRAY;
	}

	public static Object[] removeElement(Object[] arr, Object target) {
		List<Object> list = new ArrayList<Object>(arr.length);
		for (int i = 0; i < arr.length; ++i) {
			if ((target == arr[i])
					|| ((target != null) && (target.equals(arr[i])))) {
				continue;
			}
			list.add(arr[i]);
		}

		return list.toArray();
	}

	public static String[] toString(Object[] array) {
		String[] result = (String[]) null;
		if (isEmpty(array)) {
			return result;
		}
		result = new String[array.length];
		Object[] arrayOfObject = array;
		int j = array.length;
		for (int i = 0; i < j; ++i) {
			Object obj = arrayOfObject[i];
			result[i] = String.valueOf(obj);
			++i;
		}

		return result;
	}

	public static List<Object> toList(Object array) {
		if (array == null) {
			return new ArrayList<Object>();
		}

		if (array instanceof Object[]) {
			return Arrays.asList((Object[]) array);
		}

		int size = Array.getLength(array);
		ArrayList<Object> list = new ArrayList<Object>();
		for (int i = 0; i < size; ++i) {
			list.add(Array.get(array, i));
		}

		return list;
	}

	public static boolean isEmptyStringArray(String[] array) {
		if ((array == null) || (array.length == 0)) {
			return true;
		}

		String[] arrayOfString = array;
		int j = array.length;
		for (int i = 0; i < j; ++i) {
			String item = arrayOfString[i];
			if (!StringUtil.isEmpty(item)) {
				return false;
			}
		}

		return true;
	}

	public static Object[] removeElements(Object[] arr, Object target) {
		List<Object> list = new ArrayList<Object>(arr.length);
		for (int i = 0; i < arr.length; ++i) {
			if ((target == arr[i])
					|| ((target != null) && (target.equals(arr[i])))) {
				continue;
			}
			list.add(arr[i]);
		}

		return list.toArray();
	}
}
