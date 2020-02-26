package medium.Backtracking;

/**
 * @Time : 2020年2月24日15:55:21
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.ArrayList;
import java.util.List;

/**
 * 找出所有相加之和为 n 的 k 个数的组合。组合中只允许含有 1 - 9 的正整数，并且每种组合中不存在重复的数字。
 * 说明：
 * 所有数字都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 * 输入: k = 3, n = 7
 * 输出: [[1,2,4]]
 * 示例 2:
 * 输入: k = 3, n = 9
 * 输出: [[1,2,6], [1,3,5], [2,3,4]]
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/combination-sum-iii
 */
public class combinationSum3 {
    private List<List<Integer>> result = null;

    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :36.8 MB, 在所有 Java 提交中击败了5.08%的用户
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        result = new ArrayList<>();
        if (k >= n || n == 0)
            return result;
        backTrack3(k, n, 0, 0, 0, new int[k]);
        return result;
    }

    private void backTrack3(int k, int n, int pre, int curSum, int curIndex, int[] temp) {
        if (curSum == n && curIndex == k){
            List<Integer> tempRes = new ArrayList<>();
            for(int i : temp)
                tempRes.add(i);
            result.add(tempRes);
            return;
        }
        if(curIndex == k) // 这里要注意 元素个数有k个 但和不满足的 没有继续的可能
            return;
        // 初始化保证了不重复，第1个条件是减枝，第2个条件是可以取的元素的最大值为9
        for(int i = pre + 1; i <= n - curSum && i <= 9; i++){
            temp[curIndex] = i;
            backTrack3(k, n, i, curSum + i, curIndex + 1, temp);
        }
    }
}
