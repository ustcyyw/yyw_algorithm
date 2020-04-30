package medium.Backtracking;

/**
 * @Time : 2020年2月24日16:17:44
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :已总结
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一组不含重复元素的整数数组 nums，返回该数组所有可能的子集（幂集）。
 * 说明：解集不能包含重复的子集。
 * 示例:
 * 输入: nums = [1,2,3]
 * 输出:
 * [
 * [3],
 *   [1],
 *   [2],
 *   [1,2,3],
 *   [1,3],
 *   [2,3],
 *   [1,2],
 *   []
 * ]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/subsets
 */
public class subsets78 {
    private List<List<Integer>> result = null;

    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :39.3 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public List<List<Integer>> subsets(int[] nums) {
        result = new ArrayList<>();
        result.add(new ArrayList<>()); // 空集
        if (nums.length == 0)
            return result;
        for (int i = 1; i <= nums.length; i++) {
            backTrack(nums, i, 0, new int[i], 0);
        }

        return result;
    }

    private void backTrack(int[] nums, int k, int start, int[] temp, int curIndex) {
        if(curIndex == k){
            List<Integer> tempRes = new ArrayList<>();
            for(int i : temp)
                tempRes.add(i);
            result.add(tempRes);
            return;
        }
        // 这里的剪树枝条件，可以参考77题 子集其实也就是一中组合，所以在选数的时候，如果选得太大，会导致后续不够选。
        for(int i = start; i < nums.length - k + curIndex + 1; i++){
            temp[curIndex] = nums[i];
            backTrack(nums, k, i + 1, temp, curIndex + 1);
        }
    }
}
