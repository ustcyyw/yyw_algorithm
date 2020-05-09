package SwordToOffer;

/**
 * @Time : 2020年5月9日16:32:55
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;

/**
 * 从扑克牌中随机抽5张牌，判断是不是一个顺子，即这5张牌是不是连续的。
 * 2～10为数字本身，A为1，J为11，Q为12，K为13，而大、小王为 0 ，可以看成任意数字。A 不能视为 14。
 * <p>
 * 示例 1:
 * 输入: [1,2,3,4,5]
 * 输出: True
 * <p>
 * 示例 2:
 * 输入: [0,0,1,2,5]
 * 输出: True
 * <p>
 * 限制：
 * 数组长度为 5 
 * 数组的数取值为 [0, 13] .
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/bu-ke-pai-zhong-de-shun-zi-lcof
 */
public class isStraight {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了84.84%的用户
     * 内存消耗 :37.5 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public boolean isStraight(int[] nums) {
        Arrays.sort(nums);
        int countZero = 0, i = 0, gap = 0;
        for (; i < 5 && nums[i] == 0; i++)
            countZero++;
        for (i = i + 1; i < 5; i++) {
            if (nums[i] == nums[i - 1])
                return false;
            if (nums[i] - nums[i - 1] > 1)
                gap += nums[i] - nums[i - 1] - 1;
        }
        return countZero >= gap;
    }
}
