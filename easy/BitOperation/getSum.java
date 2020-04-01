package easy.BitOperation;

/**
 * @Time : 2020年3月27日15:39:28
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 不使用运算符 + 和 -​，计算两整数 a 、b 之和。
 * <p>
 * 示例 1:
 * 输入: a = 1, b = 2
 * 输出: 3
 * <p>
 * 示例 2:
 * 输入: a = -2, b = 3
 * 输出: 1
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/sum-of-two-integers
 */
public class getSum {
    /**
     * 不直接在两个数上面使用 + 与 -
     * 而是转换成二进制数的方式进行加减
     */
    public int getSum(int a, int b) {
        int[] n1 = getBit(a);
        int[] n2 = getBit(b);
        int[] bitRes = new int[32];
        int flag = 0;
        for (int i = 0; i < 32; i++) {
            int temp = n1[i] + n2[i] + flag;
            flag = temp / 2;
            bitRes[i] = temp % 2;
        }
        if(bitRes[31] == 1){ // 所得和是负数 需要得到原码
            for(int i = 0; i < 32; i++){ // 得到反码
                if(bitRes[i] == 0) bitRes[i] = 1;
                else {
                    bitRes[i] = 0;
                    break;
                }
            }
            for(int i = 0; i < 31; i++){
                if(bitRes[i] == 0) bitRes[i] = 1;
                else bitRes[i] = 0;
            }
        }
        int res = 0;
        int product = 1;
        for (int i = 0; i < 31; i++) {
            res += bitRes[i] * product;
            product *= 2;
        }
        return bitRes[31] == 1 ? -1 * res : res;
    }

    private int[] getBit(int num) {
        int[] res = new int[32];
        int index = 0;
        while (num != 0) {
            if ((num & 1) == 1)
                res[index] = 1;
            index++;
            num >>>= 1;
        }
        return res;
    }

    /**
     * 评论区别人的做法
     * 异或就是进行相加但是排除了进位 比如 5+3： 0101 ^ 0011 = 0110 十进制的6
     * 与运算就是得到进位数 5+3：0101 & 0011 = 0001,但是这是进位数的右边一位，所以进行左移一位的操作得到 0010 十进制的2
     * 下一步继续计算 6 + 2 同样使用异或得到无进位数 0100（4），由与运算且左移1位 0100（4）
     * 再进行 4 + 4的计算 0000，1000
     * 再进行 0 + 8的计算 1000，0000 进位数为0，停止
     */
    public int getSum2(int a, int b) {
        while (b != 0) {
            // 当进位不为0时
            // 无进位累加值
            int temp = a ^ b;

            // 进位值
            int carry = (a & b) << 1;

            // a=无进位累加值 b=进位值
            a = temp;
            b = carry;
        }
        return a;
    }

}
