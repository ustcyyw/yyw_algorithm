package easy.BitOperation;

/**
 * @Time : 2020年3月28日11:14:32
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个整数 (32 位有符号整数)，请编写一个函数来判断它是否是 4 的幂次方。
 *
 * 示例 1:
 * 输入: 16
 * 输出: true
 *
 * 示例 2:
 * 输入: 5
 * 输出: false
 *
 * 进阶：
 * 你能不使用循环或者递归来完成本题吗？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/power-of-four
 */
public class isPowerOfFour {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.6 MB, 在所有 Java 提交中击败了5.21%的用户
     * 在类载入的时候计算一次 后面都是直接使用了
     */
    private static int flag; // flag = 715827882
    static{
        int temp = 2;
        for(int i = 0; i <= 14; i++){
            flag += temp;
            temp <<= 2;
        }
    }

    /**
     * 相比于2的幂，4的幂也只有一位是1，但是这一位必须位于1，3，5这样的奇数位置。
     * 所以用一个奇数位为0的mask与要检查的数相与，如果为0，才能说明那一位1出在奇数位上
     * 其实 flag = 0xaaaaaaaa
     */
    public boolean isPowerOfFour(int num) {
        return num > 0 && (num & (num - 1)) == 0 && (num & flag) == 0;
    }
}
