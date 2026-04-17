package com.mdt.bot;

import java.util.Stack;

public final class MathHelper {

	public static Number solve(String expression) {
		Stack<Character> oprs = new Stack<>();
		Stack<String> nums = new Stack<>();
		boolean addedOp = false;
		
		for (int i = 0; i < expression.length(); i++) {
			char curr = expression.charAt(i);
			if (getPrecedence(curr) != -1) {
				oprs.add(curr);
				addedOp = true;
			}
			else {
				if (!addedOp && nums.size() > 0) {
					nums.add(nums.pop() + Character.toString(curr));
				} 
				else {
					nums.add(Character.toString(curr));
				}
				addedOp = false;
				
			}
			
			if (oprs.size() > 0) {
				if (getPrecedence(oprs.peek()) == 0) {
//					oprs.pop();
//					while (getPrecedence(oprs.peek()) != 4) {
//						Number temp = getNum(nums.pop());
//						nums.add(getValue(oprs.pop(), getNum(nums.pop()), temp).toString());
//						System.out.println(nums.peek());
//					} oprs.pop();
				}
			}
			
		}
		
		System.out.println(oprs);
		System.out.println(nums);
		
		return new Integer(0);
	}
	
	public static Number getValue(char op, Number n1, Number n2) {
		double a = n1.doubleValue(), b = n2.doubleValue();
		switch(op) {
			case '-':
				return getNumberConversion(a - b);
			case '+':
				return getNumberConversion(a + b);
			case '*':
				return getNumberConversion(a * b);
			case '/':
				return b == 0? Double.POSITIVE_INFINITY : getNumberConversion(a / b);
			case '%':
				return b == 0? Double.POSITIVE_INFINITY : getNumberConversion(a % b);
			case '^':
				return expHelper(a, b);
			default:
				return -1;
		}
	}
	
	private static int getPrecedence(char op) {
		switch(op) {
			case '-':
				return 1;
			case '+':
				return 1;
			case '*':
				return 2;
			case '/':
				return 2;
			case '%':
				return 2;
			case '^':
				return 3;
			case '(':
				return 4;
			case ')':
				return 0;
			default:
				return -1;
		}
	}
	
	public static Number getNum(String num) {
		double value = 0; 
		int decimal = num.indexOf('.') == -1? num.length() - 1 : num.indexOf('.') - 1;
		for (int i = 0; i < num.length(); i++) {
			switch(num.charAt(i)) {
				case '1':
					value += 1 * Math.pow(10, decimal);
					break;
				case '2':
					value += 2 * Math.pow(10, decimal);
					break;
				case '3':
					value += 3 * Math.pow(10, decimal);
					break;
				case '4':
					value += 4 * Math.pow(10, decimal);
					break;
				case '5':
					value += 5 * Math.pow(10, decimal);
					break;
				case '6':
					value += 6 * Math.pow(10, decimal);
					break;
				case '7':
					value += 7 * Math.pow(10, decimal);
					break;
				case '8':
					value += 8 * Math.pow(10, decimal);
					break;
				case '9':
					value += 9 * Math.pow(10, decimal);
					break;
				case '.':
					decimal++;
					break;
			}
			decimal--;
		}
		return value;
	}
	
	private static Number getNumberConversion(Number num) {
		if (num.doubleValue() == 0) {return 0;}
		if (num.doubleValue() % num.intValue() == 0) {return new Integer(num.intValue());} 
		return new Double(num.doubleValue());
	}
	
	private static Number expHelper(Number a, Number b) {
		double value = a.doubleValue();
		if (getNumberConversion(b).getClass() == Integer.class) {
			for (int i = 0; i < b.intValue(); i++) {value *= a.doubleValue();}
			return getNumberConversion(value);
		} return Math.pow(a.doubleValue(), b.doubleValue());
		
	}
	
	public static String getValueString(char op, Number a, Number b) {
		return "`" + getNumberConversion(a) + " " + op + " " + getNumberConversion(b) + " = " + getValue(op, a, b) + "`";
	}
	
	public static String randomString(Number a, Number b) {
		double n1 = a.doubleValue(), n2 = b.doubleValue();
		double min = Math.min(n1, n2), max = Math.max(n1, n2);
		return "A random number between "+ getNumberConversion(min) + " and " + getNumberConversion(max) + " is `"+(Math.random()*(max-min)+min)+"`";
	}

}
