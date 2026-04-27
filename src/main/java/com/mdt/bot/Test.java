package com.mdt.bot;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		System.out.println(MathHelper.getNum("000000012345.678"));
		System.out.println("2/(10+(2.4%2+1))*4^7");
		System.out.println( MathHelper.solve("2/(10+(2.4%2+1))*4^7"));
//		MathHelper.solve("2/10+2.4%2+1*4^7");
	}

}
