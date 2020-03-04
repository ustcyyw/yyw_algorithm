package easy.ArrayAndMatrix;

/**
 * @Time : 2020年2月13日18:10:25
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.*;

/**
 * 给定一个非空且只包含非负数的整数数组 nums, 数组的度的定义是指数组里任一元素出现频数的最大值。
 * 你的任务是找到与 nums 拥有相同大小的度的最短连续子数组，返回其长度。
 * <p>
 * 示例 1:
 * 输入: [1, 2, 2, 3, 1]
 * 输出: 2
 * 解释:
 * 输入数组的度是2，因为元素1和2的出现频数最大，均为2.
 * <p>
 * 连续子数组里面拥有相同度的有如下所示:
 * [1, 2, 2, 3, 1], [1, 2, 2, 3], [2, 2, 3, 1], [1, 2, 2], [2, 2, 3], [2, 2]
 * 最短连续子数组[2, 2]的长度为2，所以返回2.
 * 示例 2:
 * 输入: [1,2,2,3,1,4,2]
 * 输出: 6
 * <p>
 * 注意:
 * nums.length 在1到50,000区间范围内。
 * nums[i] 是一个在0到49,999范围内的整数。
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/degree-of-an-array
 */
public class findShortestSubArray {
    /**
     * 执行用时 :25 ms, 在所有 Java 提交中击败了83.29%的用户
     * 内存消耗 :53 MB, 在所有 Java 提交中击败了5.01%的用户
     */
    public int findShortestSubArray(int[] nums) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int num : nums) {
            if (countMap.containsKey(num))
                countMap.put(num, countMap.get(num) + 1);
            else
                countMap.put(num, 1);
        }
        // 得到数组的度
        int maxCount = 0;
        for (int key : countMap.keySet()) {
            if (countMap.get(key) > maxCount)
                maxCount = countMap.get(key);
        }
        if (maxCount == 1)
            return 1;
        // 出现频率最高的数 可能不止一个
        List<Integer> maxNums = new ArrayList<>();
        for (int key : countMap.keySet()) {
            if (countMap.get(key) == maxCount)
                maxNums.add(key);
        }
        // 最小的数组，它至少得刚好包括出现频率最高的数
        int minLength = nums.length;
        for (int num : maxNums) {
            // 区间开始的索引
            int start = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] == num) {
                    start = i;
                    break;
                }
            }
            int end = 0;
            for (int j = start + 1, remain = maxCount - 1; j < nums.length; j++) {
                if (nums[j] == num)
                    remain--;
                if (remain == 0) {
                    end = j;
                    break;
                }
            }
            minLength = Math.min(end - start + 1, minLength);
        }
        return minLength;
    }

    /**
     * 执行用时 :43 ms, 在所有 Java 提交中击败了42.63%的用户
     * 内存消耗 :50.4 MB, 在所有 Java 提交中击败了5.01%的用户
     * 官方标答
     */
    public int findShortestSubArray2(int[] nums) {
        Map<Integer, Integer> left = new HashMap<>(), right = new HashMap<>(), count = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int x = nums[i];
            left.putIfAbsent(x, i); // 如果x是第一次出现，放入Map中，从而left可以记录某个数字第一次出现的索引
            right.put(x, i);
            count.put(x, count.getOrDefault(x, 0) + 1); // 记录x出现的次数
        }
        int maxCount = Collections.max(count.values());
        int result = nums.length;
        for (int key : count.keySet()) {
            if (count.get(key) == maxCount)
                result = Math.min(result, right.get(key) - left.get(key) + 1);
        }
        return result;
    }
}
