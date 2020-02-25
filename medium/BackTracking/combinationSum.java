package medium.Backtracking;

/**
 * @Time : 2020年2月23日19:33:41
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 *
 * 输入: candidates = [2,3,6,7], target = 7,
 * 所求解集为:
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * 示例 2:
 *
 * 输入: candidates = [2,3,5], target = 8,
 * 所求解集为:
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum
 */
public class combinationSum {
    private List<List<Integer>> result = null;

    /**
     * 执行用时 :5 ms, 在所有 Java 提交中击败了79.45%的用户
     * 内存消耗 :41.2 MB, 在所有 Java 提交中击败了7.23%的用户
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        result = new ArrayList<>();
        if(candidates.length == 0)
            return result;
        List<Integer> temp = new ArrayList<>();
        temp.add(Integer.MIN_VALUE);
        backTrack(candidates, target, temp, 0);
        return result;
    }

    // 要求不重复，所以元素的选定要比列表中最后一个元素大
    private void backTrack(int[] candidates, int target, List<Integer> curItems, int curSum){
        if(curSum == target){
            List<Integer> temp = new ArrayList<>(curItems);
            temp.remove(0);
            result.add(temp);
            return;
        }

        int pre = curItems.get(curItems.size() - 1);
        for(int i : candidates){
            if(i >= pre && curSum + i <= target){
                curItems.add(i);
                backTrack(candidates, target, curItems, curSum + i);
                curItems.remove(curItems.size() - 1);
            }
        }
    }
}
