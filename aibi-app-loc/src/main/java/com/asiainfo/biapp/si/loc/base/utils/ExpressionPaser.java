package com.asiainfo.biapp.si.loc.base.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;

/**
 * 数据探索及标签创建客户群时表达式解析
 * @author luyan3
 * @version 1.0
 */
public class ExpressionPaser {
	
	private static Logger log = Logger.getLogger(ExpressionPaser.class);
	
	/**
	 * 剔除运算符
	 */
	public static final String EXCEPT = "-";
	
	/**
	 * 且运算符
	 */
	public static final String AND = "and";
	
	/**
	 * 或运算符
	 */
	public static final String OR = "or";
	
	/**
	 * 用于标识非的情况
	 */
	public static final String UNDERLINE = "_";
	
	/**
	 * 左括号
	 */
	public static final char LEFT_Q = '(';
	
	/**
	 * 右括号
	 */
	public static final char RIGHT_Q = ')';
	
	/**
	 * 判断全是数字的正则表达式
	 */
	public static final String REG = "[0-9]*";
	
	/**
	 * 返回替换第一个剔除字符的字符串表达式
	 * @param oldStr 元素字符串
	 * @param firstExceptPos 第一个取反的字符位置
	 * @return
	 */
	public static String getNewString(String oldStr, int firstExceptPos) {
		char c = oldStr.charAt(firstExceptPos + 2);
		String front = oldStr.substring(0, firstExceptPos + 2);
		front = front.replace(EXCEPT, "and");
		String middle = "";
		String back = oldStr.substring(firstExceptPos + 2);
		// 判断剔除后面的元素是否是左括号，因为剔除后面只有2种可能：1、后面是标签元素：数字的，2、后面是括号，而且只能是左括号
		if ( c == LEFT_Q ) {
			int leftCount = 0;
			int rightPos = 0;
			// 计算剔除后面一个完整的表达式，即剔除后面的左右括号匹配的，先要获得最后一个右括号的位置
			for (int i = 1; i < back.length(); i++) {
				if (back.charAt(i) == LEFT_Q) {
					leftCount++;
				}
				if (leftCount == 0 && back.charAt(i) == RIGHT_Q) {
					rightPos = i + 1;
					break;
				} else if (back.charAt(i) == RIGHT_Q) {
					leftCount--;
				}
			}
			if (rightPos == 0) {
				String message = "没有找到对应的右括号，请检查条件是否缺少括号";
				log.error(message);
				throw new RuntimeException(message + " : " + back);
			} else {
				// 根据获得的右括号位置截取出剔除后面一个完整的表达式，即剔除后面的左右括号匹配的
				middle = back.substring(0, rightPos);
				if (rightPos + 1 < back.length()) {
					back = back.substring(rightPos + 1);
				} else {
					back = "";
				}
			}
			StringBuffer newMiddleSb = new StringBuffer();
			if (middle.length() > 0) {
				// 把上面截取的表达式根据剔除规则转换运算符，and转换为or，or转换为and，-转换为or，
				// 如果是标签（数字的）前面加下划线，表示取非。该步运算相当于把剔除符号带入表达式
				String[] element = middle.split(" ");
				for (int j = 0; j < element.length; j ++) {
					if (element[j].equals(AND)) {
						newMiddleSb.append(OR + " ");
					} else if (element[j].equals(OR)) {
						newMiddleSb.append(AND + " ");
					} else if (element[j].equals(EXCEPT)) {
						newMiddleSb.append(OR + " ");
					} else {
						if (isNumber(element[j])) {
							if (j > 0 && element[j-1].equals(EXCEPT)) {
								newMiddleSb.append(element[j] + " ");
							} else {
								newMiddleSb.append(UNDERLINE + element[j] + " ");
							}
						} else {
							newMiddleSb.append(element[j] + " ");
						}
					}
				}
			}
			middle = newMiddleSb.toString();
		} else {
			back = UNDERLINE + back;
		}
		// 把前，中，后三部分组合起来，形成新的字符串
		String newStr = front + middle + back;
		int newFirstExceptPos = newStr.indexOf(EXCEPT);
		if (newFirstExceptPos > 0) {
			// 如果还有剔除符号，递归调用该方法
			newStr = getNewString(newStr, newFirstExceptPos);
		}
		return newStr;
	}
	
	/**
	 * 判断字符串是否全是数字
	 * @param element
	 * @return
	 */
	public static boolean isNumber(String element) {
		boolean flag = false;
		Pattern pattern = Pattern.compile(REG);
		Matcher isNum = pattern.matcher(element);
		if (isNum.matches()) {
			flag = true;
		}
		return flag;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer("1 and 9 - 2 - 3 and ( 4 or 5 ) and 6 - ( 7 and 8 ) - ( 2 - 3 and ( 4 or 5 ) and ( 9 or ( 10 - 11) ) ) and 6 - 7 ");
//		StringBuffer sb = new StringBuffer("1 and 8 - ( 2 - 3 and ( 4 or 5 ) and ( 9 or ( 10 - 11) ) ) and 6 or 7 ");
//		StringBuffer sb = new StringBuffer("1 - 2 ");
//		StringBuffer sb = new StringBuffer("1 and 2");
//		StringBuffer sb = new StringBuffer("1 ");
		String oldStr = sb.substring(0, sb.lastIndexOf(" ")).toString();
		
		int firstPos = oldStr.indexOf(EXCEPT);
		String resultStr = "";
		if (firstPos > 0) {
			resultStr = ExpressionPaser.getNewString(oldStr, firstPos);
		} else {
			resultStr = oldStr;
		}
		System.out.println("1=============" + sb.toString());
		System.out.println("2=============" + resultStr);
	}

}
