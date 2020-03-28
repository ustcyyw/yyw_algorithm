package easy.BitOperation;

/**
 * @Time : 2020年3月27日17:11:04
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 两个整数之间的汉明距离指的是这两个数字对应二进制位不同的位置的数目。
 * 给出两个整数 x 和 y，计算它们之间的汉明距离。
 * <p>
 * 注意：
 * 0 ≤ x, y < 2^31.
 * <p>
 * 示例:
 * 输入: x = 1, y = 4
 * 输出: 2
 * 解释:
 * 1   (0 0 0 1)
 * 4   (0 1 0 0)
 * ↑   ↑
 * <p>
 * 上面的箭头指出了对应二进制位不同的位置。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/hamming-distance
 */
public class hammingDistance {
    /**
     * 注意 x y都是正数
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.3 MB, 在所有 Java 提交中击败了5.05%的用户
     */
    public int hammingDistance(int x, int y) {
        int count = 0;
        while (y != 0 || x != 0) { // 直到移位使得都为0，那么更高位均为0，是相等的就不用去统计了。
            if ((y & 1) != (x & 1)) // 除了当前位，一定都变0（和0进行与运算），当前位则保留（和1进行与运算）
                count++;
            y >>= 1; // 右移动一位
            x >>= 1;
        }
        return count;
    }

    private int hammingDistance2(int x, int y) {
        int n = x ^ y;
        int count = 0;
        while (n != 0) {
            count++;
            n = n & (n - 1);
        }
        return count;
    }
}
