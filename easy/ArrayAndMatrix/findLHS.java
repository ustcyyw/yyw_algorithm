package easy.ArrayAndMatrix;

/**
 * @Time : 2020年2月14日19:55:34
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 和谐数组是指一个数组里元素的最大值和最小值之间的差别正好是1。
 * 现在，给定一个整数数组，你需要在所有可能的子序列中找到最长的和谐子序列的长度。
 * <p>
 * 示例 1:
 * 输入: [1,3,2,2,5,2,3,7]
 * 输出: 5
 * 原因: 最长的和谐数组是：[3,2,2,2,3].
 * 说明: 输入的数组长度最大不超过20,000.
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-harmonious-subsequence
 */
public class findLHS {
    /**
     * 执行用时 :36 ms, 在所有 Java 提交中击败了80.42%的用户
     * 内存消耗 :41.5 MB, 在所有 Java 提交中击败了19.90%的用户
     */
    public int findLHS(int[] nums) {
        int result = 0;
        Map<Integer, Integer> countMap = new HashMap<>((int) (nums.length / 0.75F + 1.0F));
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        for (int key : countMap.keySet()) {
            if (countMap.containsKey(key + 1))
                result = Math.max(countMap.get(key) + countMap.get(key + 1), result);
        }
        return result;
    }

    /**
     * 执行用时 :31 ms, 在所有 Java 提交中击败了86.78%的用户
     * 内存消耗 :51.2 MB, 在所有 Java 提交中击败了5.21%的用户
     */
    public int findLHS2(int[] nums) {
        if (nums.length == 0)
            return 0;
        int result = 0;
        Arrays.sort(nums);
        int preStart = 0, curStart = 0, nextStart = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[preStart]) {
                curStart = i;
                break;
            }
        }
        while (true) {
            for (int i = curStart + 1; i < nums.length; i++) {
                if (nums[i] != nums[curStart]) {
                    nextStart = i;
                    break;
                }
            }
            if (nextStart == curStart) { // 这里面很容易错啊！ emmm
                if (nums[curStart] - nums[preStart] == 1)
                    result = Math.max(result, nums.length - preStart);
                return result;
            }
            if (nums[curStart] - nums[preStart] == 1)
                result = Math.max(result, nextStart - preStart);
            preStart = curStart;
            curStart = nextStart;
        }
    }
}
