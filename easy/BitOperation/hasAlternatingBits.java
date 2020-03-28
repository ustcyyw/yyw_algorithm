package easy.BitOperation;

/**
 * @Time : 2020年3月28日12:01:15
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个正整数，检查他是否为交替位二进制数：换句话说，就是他的二进制数相邻的两个位数永不相等。
 *
 * 示例 1:
 * 输入: 5
 * 输出: True
 * 解释:
 * 5的二进制数是: 101
 *
 * 示例 2:
 * 输入: 7
 * 输出: False
 * 解释:
 * 7的二进制数是: 111
 *
 * 示例 3:
 * 输入: 11
 * 输出: False
 * 解释:
 * 11的二进制数是: 1011
 *
 *  示例 4:
 * 输入: 10
 * 输出: True
 * 解释:
 * 10的二进制数是: 1010
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/binary-number-with-alternating-bits
 */
public class hasAlternatingBits {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了69.73%的用户
     * 内存消耗 :36 MB, 在所有 Java 提交中击败了5.74%的用户
     */
    public boolean hasAlternatingBits(int n) {
        int pre = n & 1;
        n >>>= 1;
        while(n != 0){
            if((n & 1) == pre)
                return false;
            pre = n & 1;
            n >>>= 1;
        }
        return true;
    }
}
