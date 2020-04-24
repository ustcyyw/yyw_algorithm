package medium.ArrayAndMatrix;

/**
 * @Time : 2020年3月3日13:08:55
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.HashMap;
import java.util.Map;

/**
 * 给你一份工作时间表 hours，上面记录着某一位员工每天的工作小时数。
 * 我们认为当员工一天中的工作小时数大于 8 小时的时候，那么这一天就是「劳累的一天」。
 * 所谓「表现良好的时间段」，意味在这段时间内，「劳累的天数」是严格 大于「不劳累的天数」。
 * 请你返回「表现良好时间段」的最大长度。
 *
 * 示例 1：
 *
 * 输入：hours = [9,9,6,0,6,6,9]
 * 输出：3
 * 解释：最长的表现良好时间段是 [9,9,6]。
 *  
 *
 * 提示：
 * 1 <= hours.length <= 10000
 * 0 <= hours[i] <= 16
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/longest-well-performing-interval
 */
public class longestWPI {
    /**
     * 执行用时 :326 ms, 在所有 Java 提交中击败了31.59%的用户
     * 内存消耗 :42.4 MB, 在所有 Java 提交中击败了5.61%的用户
     * 暴力法
     */
    public int longestWPI(int[] hours) {
        int n = hours.length;
        for(int i = 0; i < n; i++){
            hours[i] = hours[i] > 8 ? 1 : -1;
        }
        int res = 0;
        for(int i = 0; i < n; i++){
            int count = 0;
            for(int j = i; j < n; j++){
                count += hours[j];
                if(count > 0)
                    res = Math.max(res, j - i + 1);
            }
            if(n - i <= res)
                return res;
        }
        return res;
    }

    /**
     * 别人的解法：将每天转化为1（认真工作的那天）或者-1，那么当数组的前n项和大于0时，说明认真工作的天数多。
     * 如果 sum[j] - sum[i] > 0, 说明i+1 到 j 认真工作的天数多。
     * 到某一天 j 时，sum[j]确定，能不能找到某一天i(i < j) 使得 sum[j] - sum[i] = 1 成立。因为等于1是能取到的最多的天数
     * 为什么呢？假如说sum[j]=0，如果sum[j] - sum[i] = 2，说明sum[i] = -2，这是一个累加的过程，要在sum[i]出现-2，
     * 往前的元素一定会出现sum[i - x] = -1。
     * 如果我们记录累加值对应的索引，由累加值取查找索引，能查找到说明 i+1 到 j 认真工作的天数多，且已经取到最大范围（认真工作多1）
     * 假如在第  sum[x] 与 sum[x + 3] 都等于-1， 后面 sum[y] = 0 那么满足认真工作条件的情况下，涵盖最多的一定是 x + 1~y这几天
     * 所以在记录累加值对应的索引时，记录最前面即可。
     * 并且大于0的也不需要记录，设想 sum[j] - sum[i] = 1, sum[i]大于0，则sum[j]更是大于0，就意味着从第一天到j是认真工作的，
     * 记录 sum[i] 对应的i也就没有意义了
     *
     * 执行用时 :22 ms, 在所有 Java 提交中击败了71.30%的用户
     * 内存消耗 :41.3 MB, 在所有 Java 提交中击败了5.61%的用户
     *
     * 时间复杂度 n
     */
    public int longestWPI2(int[] hours) {
        int sum = 0;
        int res = 0;
        Map<Integer, Integer> sumToIndex = new HashMap<>();
        for(int i = 0; i < hours.length; i++){
            int temp = hours[i] > 8 ? 1 : -1;
            sum += temp;
            if(sum > 0)
                res = i + 1;
            else {
                if(!sumToIndex.containsKey(sum))
                    sumToIndex.put(sum, i);
                if(sumToIndex.containsKey(sum - 1))
                    res = Math.max(res, i - sumToIndex.get(sum - 1));
            }
        }
        return res;
    }
}
