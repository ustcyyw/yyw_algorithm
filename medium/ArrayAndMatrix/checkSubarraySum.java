package medium;

/**
 * @Time : 2020年5月15日09:36:27
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个包含非负数的数组和一个目标整数 k，
 * 编写一个函数来判断该数组是否含有连续的子数组，其大小至少为 2，总和为 k 的倍数，即总和为 n*k，其中 n 也是一个整数。
 *
 * 示例 1:
 *
 * 输入: [23,2,4,6,7], k = 6
 * 输出: True
 * 解释: [2,4] 是一个大小为 2 的子数组，并且和为 6。
 * 示例 2:
 *
 * 输入: [23,2,6,4,7], k = 6
 * 输出: True
 * 解释: [23,2,6,4,7]是大小为 5 的子数组，并且和为 42。
 * 说明:
 *
 * 数组的长度不会超过10,000。
 * 你可以认为所有数字总和在 32 位有符号整数范围内。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/continuous-subarray-sum
 */
public class checkSubarraySum {
    /**
     * 执行用时 :21 ms, 在所有 Java 提交中击败了47.96%的用户
     * 内存消耗 :40.4 MB, 在所有 Java 提交中击败了20.00%的用户
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        int sum = 0;
        int[] preSum = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            preSum[i + 1] = sum;
        }
        for (int i = 0; i < preSum.length; i++) {
            for (int j = i + 2; j < preSum.length; j++) {
                int temp = preSum[j] - preSum[i];
                if ((temp == 0 && k == 0) || (k != 0 && temp % k == 0))
                    return true;
            }
        }
        return false;
    }

    /**
     * 执行用时 :4 ms, 在所有 Java 提交中击败了67.83%的用户
     * 内存消耗 :40.7 MB, 在所有 Java 提交中击败了20.00%的用户
     * (A - B) % k = (A % K - B) % k
     * 所以 (A - B) % k == 0  ->  (A % k- B) % k == 0  ->  A % k - B == n * k (n = 0, 1, 2……)
     *      -> A % k = B + n * k, 两边再同时区模 A % k % k = B % k + n * k % k  ->  A % k = B % k
     * 也就是说，如果如果区间[i + 1, j]的子数组是k的n倍，那么(preSum[j] - preSum[i]) % k == 0
     * 等价于 preSum[j] % k == preSum[i] % k
     * 用map记录前缀和取余 及其对应的索引，通过判断map中是否出现相同的键来判断有没有和为 n * k的子数组
     */
    public boolean checkSubarraySum2(int[] nums, int k){
        int sum = 0;
        Map<Integer, Integer> map = new HashMap<>(); // 键为 preSum % k, 值为索引，当然要特殊处理k == 0的情况
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            int temp = k == 0 ? sum : sum % k;
            if(map.containsKey(temp)){ // 出现相同的键，如果子数组长度少于2， 不需要更新值。
                if(i - map.get(temp) > 1) // 子数组要求长度至少为2。
                    return true;
                continue;
            }
            map.put(temp, i);
        }
        return false;
    }
}
