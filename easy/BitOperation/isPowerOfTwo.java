package easy.BitOperation;

/**
 * @Time : 2020年3月28日10:56:58
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个整数，编写一个函数来判断它是否是 2 的幂次方。
 *
 * 示例 1:
 * 输入: 1
 * 输出: true
 * 解释: 20 = 1
 *
 * 示例 2:
 * 输入: 16
 * 输出: true
 * 解释: 24 = 16
 *
 * 示例 3:
 * 输入: 218
 * 输出: false
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/power-of-two
 */
public class isPowerOfTwo {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.8 MB, 在所有 Java 提交中击败了5.50%的用户
     */
    public boolean isPowerOfTwo(int n) {
        if(n < 0)
            return false;
        int count = 0;
        while(n != 0){ // 2的幂次 二进制位级表示只有1个1存在。
            if((n & 1) == 1)
                count++;
            n >>>= 1;
            if(count > 1) return false; // 当两个1存在时，肯定不满足条件了 直接返回false
        }
        return count == 1;
    }

    /**
     * 评论区别人的做法
     * 首先 负数不可能是2的幂
     * 2的幂次 二进制位级表示只有1个1存在。
     * 有一个技巧 x & (x - 1) 将x位级中最右的一位 1 改为 0。对于2的幂，改了之后一定是一串0000000
     */
    public boolean isPowerOfTwo2(int n) {
        if(n < 0) return false;
        return (n & (n - 1)) == 0;
    }
}
