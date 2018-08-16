package top.kwseeker.algorithms.caculateprime;

import java.util.*;

/**
 * 计算素数指数（又称素数）的方法有三种：
 * 1. 根据定义判断
 * 2. 如果一个数 n 不是素数则一定可以被因式分解为两个数的乘积（除了1*自身），且两个数一个小于等于此数的平方根，
 *    另一个大于等于此数的平方根，则只需要判断能否被 2 -- sqrt(n) 之间的整数整除即可。
 * 3. 通过素数分布规律进行判断(先判断是否在6的倍数两边，是的话再通过方法2判断)
 *    分布的规律：大于等于5的质数一定和6的倍数相邻，这条定理是怎么推导出来的暂不管了
 *
 * 找出2-n所有的素数
 * 4. 对于一个数m不能被小于m的所有素数整除的数一定是素数
 */
// TODO: 下面各个算法的复杂度是多少？
public class CalculatePrime {

    //check
    public static void checkNum(int num) {
        if(num < 2) {
            System.out.println("请输入不小于2的整数！");
        }
    }

    //方法2
    public static boolean isPrime2(int num) {
        checkNum(num);

        int tmp = new Double(Math.floor(Math.sqrt((double)num))).intValue();
        for (int i = 2; i < tmp; i++) {
            if(num%i == 0) {
                return false;
            }
        }
        return  true;
    }

    //方法3
    public static boolean isPrime3(int num) {
        checkNum(num);

        if(num >= 5) {
            if((num+1)%6==0 || (num-1)%6==0){
                return isPrime2(num);
            } else {
                return false;
            }
        } else {
            switch (num) {
                case 2:
                    return true;
                case 3:
                    return true;
                default:
                    return false;
            }
        }
    }

    //获取2-num中所有素数
    //TODO： 还有很大优化空间，查0-1000000之间的素数，运行了150s左右；使用专业数学算法性能应该可以优化上千倍
    public static Set<Integer> getAllPrime(int num) {
        boolean isPrime;

        checkNum(num);

        Set<Integer> integerSet = new LinkedHashSet<>();    //顺序集合
        integerSet.add(2);
        for (int i = 3; i <= num; i++) {
            if(i < 5) {   //对于2-4
                switch (i) {
                    case 3:
                        integerSet.add(3);
                        break;
                    case 4:
                        //nothing to do
                        break;
                }
            } else {    //5-num
                //首先判断是否为6的倍数的两边的数
                if((i+1)%6==0 || (i-1)%6==0){
                    isPrime = true;
                    Iterator<Integer> iterator = integerSet.iterator();
                    while (iterator.hasNext()) {
                        if(i % iterator.next() == 0) {
                            isPrime = false;
                            break;  //不是素数
                        }
                    }
                    if(isPrime) {
                        integerSet.add(i);
                    }
                }
            }
        }
        return integerSet;
    }

    public static void main(String[] args) {
        int testNum;
        long timeBefore, timeAfter;
        boolean isPrimeNum;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a num: ");

        testNum = scanner.nextInt();

        //方法2耗时
        timeBefore = System.nanoTime();
        isPrimeNum = isPrime2(testNum);
        System.out.println(String.valueOf(testNum) + (isPrimeNum?"是素数":"不是素数"));
        timeAfter = System.nanoTime();
        System.out.println("方法2计算耗时<ns>： " + (timeAfter - timeBefore));

        //方法3耗时
        timeBefore = System.nanoTime();
        isPrimeNum = isPrime3(testNum);
        System.out.println(String.valueOf(testNum) + (isPrimeNum?"是素数":"不是素数"));
        timeAfter = System.nanoTime();
        System.out.println("方法3计算耗时<ns>： " + (timeAfter - timeBefore));

        //获取2-10000之间所有的素数
        timeBefore = System.currentTimeMillis();
        Set<Integer> allPrimeSet = CalculatePrime.getAllPrime(10000);
        timeAfter = System.currentTimeMillis();
        System.out.println("获取2-10000中所有素数计算耗时<ms>： " + (timeAfter - timeBefore));
        StringBuffer sb = new StringBuffer();
        for (Integer i: allPrimeSet ) {
            sb.append("  ");
            sb.append(i);
        }
        System.out.println("2-10000之间所有的素数<共" + allPrimeSet.size() + "个>： " + "\n" + sb);
    }

}

//结果
/*
Please enter a num:
9973
9973是素数
方法2计算耗时<ns>： 450200
9973是素数
方法3计算耗时<ns>： 24000
获取2-10000中所有素数计算耗时<ms>： 12
2-10000之间所有的素数<共1229个>：
...
 */