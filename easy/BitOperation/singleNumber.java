package easy.BitOperation;

/**
 * @Time : 2020年3月1日00:06:43
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 *
 * 说明：
 * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
 *
 * 示例 1:
 * 输入: [2,2,1]
 * 输出: 1
 *
 * 示例 2:
 * 输入: [4,1,2,1,2]
 * 输出: 4
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/single-number
 */
public class singleNumber {
    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for(int i : nums){
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        for(int i : nums){
            if(map.get(i) == 1)
                return i;
        }
        return -1;
    }
    /**
     * 用位运算来做更快，思路的3点来源
     * 1.异或可以将相同的两个数变为0 x^x = 0
     * 2.异或具有交换律
     * 3.0与一个数异或结果仍然为该数 0 ^ x = x
     *
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.75%的用户
     * 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.02%的用户
     */
    public int singleNumber2(int[] nums) {
        int res = 0;
        for(int i : nums)
            res ^= i;
        return res;
    }
}
