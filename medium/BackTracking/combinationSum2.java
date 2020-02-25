package medium.Backtracking;

/**
 * @Time : 2020年2月24日15:02:21
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 * candidates 中的每个数字在每个组合中只能使用一次。
 *
 * 说明：
 *
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 *
 * 示例 2:
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum-ii
 */

/**
 * 一个关键词是组合，不是排列，不能有重复，靠后的数要比前面的大
 * 另一个关键字是，元素不允许重复使用，需要记录某个元素是否被使用；由于原序列还有相等元素还需要再当前位记录已使用过的值
 */
public class combinationSum2 {
    private List<List<Integer>> result = null;
    private boolean[] used = null;

    /**
     * 执行用时 :7 ms, 在所有 Java 提交中击败了48.46%的用户
     * 内存消耗 :41.2 MB, 在所有 Java 提交中击败了8.49%的用户
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        result = new ArrayList<>();
        if(candidates.length == 0)
            return result;
        used = new boolean[candidates.length];
        backTrack2(candidates, target, 0, new ArrayList<>(), 0);
        return result;
    }

    private void backTrack2(int[] candidates, int target, int pre, List<Integer> temp, int curSum){
        if(curSum == target){
            result.add(new ArrayList<>(temp));
            return;
        }

        HashSet<Integer> set = new HashSet<>();
        for(int i = 0; i < candidates.length; i++){
            int num = candidates[i];
            if(!used[i] && !set.contains(num) && num >= pre && curSum + num <= target){
                temp.add(num);
                used[i] = true;
                set.add(num);
                backTrack2(candidates, target,num ,temp, curSum + num);
                temp.remove(temp.size() - 1);
                used[i] = false;
            }
        }
    }

    /**
     * 评论区一个更巧的做法 将candidate数组进行排序，这样既可以记录已经使用的值也可以解决重复问题
     * 执行用时 :5 ms, 在所有 Java 提交中击败了62.38%的用户
     * 内存消耗 :38.9 MB, 在所有 Java 提交中击败了25.85%的用户
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        result = new ArrayList<>();
        if(candidates.length == 0)
            return result;
        Arrays.sort(candidates);
        backTrack(candidates, target, -1, new ArrayList<>(), 0);
        return result;
    }

    private void backTrack(int[] candidates, int target, int preIndex, List<Integer> temp, int curSum){
        if(curSum == target){
            result.add(new ArrayList<>(temp));
            return;
        }

        for(int i = preIndex + 1; i < candidates.length; i++){
            // 利用排序列表的性质，排除重复，在当前位如果要选的数字和上一个选过的数字相同，会造成重复，所以跳过。
            if(i > preIndex + 1 && candidates[i] == candidates[i - 1])
                continue;
            int num = candidates[i];
            if(curSum + num <= target){
                temp.add(num);
                backTrack(candidates, target,i ,temp, curSum + num);
                temp.remove(temp.size() - 1);
            }
        }
    }
}
