package com.somnus.exception;

public class ExceptionTestCase {

    /** 有错误 */
	/*public static int test(int b) { 
		try { 
			int num = 10/b; return num; 
		} catch (Exception e) { 
			System.out.println("exception"); 
		} finally {
			System.out.println("finally"); 
		} 
	}*/


    /**
     * 解决办法:a、在catch块中加入return语句，因为一旦出现异常，catch中的语句可以保证函数会有一个返回值
     *
     * @param b
     * @return
     */
    public static int testA(int b) {
        try {
            int num = 10 / b;
            return num;
        } catch (Exception e) {
            System.out.println("exception");
            return b;
        } finally {
            System.out.println("finally");
        }
    }

    /**
     * 解决办法:b、在finally块中加入return语句，同样只要系统不退出，finally语句块会始终得到执行的
     *
     * @param b
     * @return
     */
    @SuppressWarnings("finally")
    public static int testB(int b) {
        try {
            int num = 10 / b;
            return num;
        } catch (Exception e) {
            System.out.println("exception");
        } finally {
            System.out.println("finally");
            return b;
        }
    }

    /**
     * 解决办法:c、在函数末尾加入return语句
     *
     * @param b
     * @return
     */
    public static int testC(int b) {
        try {
            int num = 10 / b;
            return num;
        } catch (Exception e) {
            System.out.println("exception");
        } finally {
            System.out.println("finally");
        }
        return b;
    }

    public static void main(String args[]) {
        //異常
        System.out.println(test1(0));
        System.out.println(test2(0));
        System.out.println(test3(0));
        //正常
        System.out.println(test1(5));
        System.out.println(test2(5));
        System.out.println(test3(5));

    }

    /**
     * try块中有return语句，那么是先执行return语句，还是先执行finally语句
     * 认为是先执行finally语句，再执行return语句，但是这是错误的，
     *
     * 事实上是先执行return语句，再执行finally语句，然后将结果返回，
     * 也可以说return语句执行了两次，一次在finally之前，一次在finally之后，但是返回的确是第一次执行的值
     */
    /**
     * 异常
     *
     * @param b = 0
     * @param b = 5
     * @return 2
     */
    public static int test1(int b) {
        try {
            b = 10 / b;
            return b;
        } catch (Exception e) {
            b += 10;
        } finally {
            b += 10;
        }
        return b;
    }

    /**
     * 异常
     *
     * @param b = 0
     * @param b = 5
     * @return 2
     */
    public static int test2(int b) {
        try {
            b = 10 / b;
            return b;
        } catch (Exception e) {
            b += 10;
            return b;
        } finally {
            b += 10;
        }
    }

    /**
     * 异常
     *
     * @param b = 0
     * @param b = 5
     * @return 12
     */
    @SuppressWarnings("finally")
    public static int test3(int b) {
        try {
            b = 10 / b;
            return b;
        } catch (Exception e) {
            b += 10;
        } finally {
            b += 10;
            return b;
        }
    }

}
