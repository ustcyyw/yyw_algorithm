package easy.BitOperation;

/**
 * @Time : 2020年3月28日12:13:14
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个正整数，输出它的补数。补数是对该数的二进制表示取反。
 *
 * 示例 1:
 * 输入: 5
 * 输出: 2
 * 解释: 5 的二进制表示为 101（没有前导零位），其补数为 010。所以你需要输出 2 。
 *
 * 示例 2:
 * 输入: 1
 * 输出: 0
 * 解释: 1 的二进制表示为 1（没有前导零位），其补数为 0。所以你需要输出 0 。
 *
 * 注意:
 *
 * 给定的整数保证在 32 位带符号整数的范围内。
 * 你可以假定二进制数不包含前导零位。
 * 本题与 1009 https://leetcode-cn.com/problems/complement-of-base-10-integer/ 相同
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/number-complement
 */
public class findComplement {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.3 MB, 在所有 Java 提交中击败了5.33%的用户
     */
    public int findComplement(int num) {
        int move = 0;
        int res = 0;
        while(num != 0){
            int temp = num & 1;
            if(temp == 0) res += (1 << move);
            move++;
            num >>>= 1;
        }
        return res;
    }
}
