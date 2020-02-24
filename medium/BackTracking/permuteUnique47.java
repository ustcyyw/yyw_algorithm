package medium.Backtracking;

/**
 * @Time : 2020年2月23日13:48:12
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * 给定一个可包含重复数字的序列，返回所有不重复的全排列。
 * 示例:
 * 输入: [1,1,2]
 * 输出:
 * [
 *   [1,1,2],
 *   [1,2,1],
 *   [2,1,1]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/permutations-ii
 */
public class permuteUnique47 {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了96.34%的用户
     * 内存消耗 :41.3 MB, 在所有 Java 提交中击败了17.43%的用户
     */
    private List<List<Integer>> result = null;
    private boolean[] used = null;

    public List<List<Integer>> permuteUnique(int[] nums) {
        result = new ArrayList<>();
        if(nums.length == 0)
            return result;
        used = new boolean[nums.length];
        backTrack(nums, new int[nums.length], 0);
        return result;
    }

    private void backTrack(int[] nums, int[] temp, int curIndex){
        if(curIndex == nums.length){
            List<Integer> tempRes = new ArrayList<>();
            for(int i : temp)
                tempRes.add(i);
            result.add(tempRes);
            return;
        }
        // 与46题的区别，有重复数字。如果某一位选定重复数字，不会得到有效结果，所以当不是当前位重复数字时才进行递归。
        HashSet<Integer> unique = new HashSet<>();
        for(int i = 0; i < nums.length; i++){
            if(!used[i] && !unique.contains(nums[i])){
                used[i] = true;
                unique.add(nums[i]);
                temp[curIndex] = nums[i];
                backTrack(nums, temp, curIndex + 1);
                used[i] = false; // 回溯 在选定这个数字的递归调用回溯到这里时，这个数字在之后的情况中，是没有用过的。
            }
        }
    }
}
