package medium.Backtracking;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Time : 2020年3月5日08:58:01
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给定一个整型数组, 你的任务是找到所有该数组的递增子序列，递增子序列的长度至少是2。
 *
 * 示例:
 * 输入: [4, 6, 7, 7]
 * 输出: [[4, 6], [4, 7], [4, 6, 7], [4, 6, 7, 7], [6, 7], [6, 7, 7], [7,7], [4,7,7]]
 * 说明:
 *
 * 给定数组的长度不会超过15。
 * 数组中的整数范围是 [-100,100]。
 * 给定数组中可能包含重复数字，相等的数字应该被视为递增的一种情况。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/increasing-subsequences
 */
public class findSubsequences491 {
    /**
     * 执行用时 :6 ms, 在所有 Java 提交中击败了92.85%的用户
     * 内存消耗 :47.3 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    private List<List<Integer>> res;
    private int n;
    public List<List<Integer>> findSubsequences(int[] nums) {
        res = new ArrayList<>();
        n = nums.length;
        if(n == 0)
            return res;
        backTrack(nums, new int[n], 0, -1, Integer.MIN_VALUE);
        return res;
    }

    private void backTrack(int[] nums, int[] temp, int curPos, int preIndex, int pre){
        if(curPos > 1){
            List<Integer> tempRes = new ArrayList<>();
            for(int i = 0; i < curPos; i++)
                tempRes.add(temp[i]);
            res.add(tempRes);
        }

        Set<Integer> set = new HashSet<>();
        for(int i = preIndex + 1; i < n; i++){
            if(!set.contains(nums[i]) && nums[i] >= pre){
                set.add(nums[i]);
                temp[curPos] = nums[i];
                backTrack(nums, temp, curPos + 1, i, nums[i]);
            }
        }
    }
}
