package top.kwseeker.dsaa.leecode;

public class DecimalComplementCode1009 {

    public static void main(String[] args) {
        System.out.println(bitwiseComplement(10));
    }

    static int bitwiseComplement(int N) {
        String binaryStr = Integer.toBinaryString(N);
        int len = binaryStr.length();
        int max = (1 << len) - 1;
        return N ^ max;
    }
}
