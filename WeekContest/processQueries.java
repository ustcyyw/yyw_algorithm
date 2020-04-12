package WeekContest;

/**
 * @Time : 2020年4月12日10:47:39
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

/**
 * 给你一个待查数组 queries ，数组中的元素为 1 到 m 之间的正整数。 请你根据以下规则处理所有待查项 queries[i]（从 i=0 到 i=queries.length-1）：
 * 一开始，排列 P=[1,2,3,...,m]。
 * 对于当前的 i ，请你找出待查项 queries[i] 在排列 P 中的位置（下标从 0 开始），然后将其从原位置移动到排列 P 的起始位置（即下标为 0 处）。注意， queries[i] 在 P 中的位置就是 queries[i] 的查询结果。
 * 请你以数组形式返回待查数组  queries 的查询结果。 
 *
 * 示例 1：
 * 输入：queries = [3,1,2,1], m = 5
 * 输出：[2,1,2,1]
 * 解释：待查数组 queries 处理如下：
 * 对于 i=0: queries[i]=3, P=[1,2,3,4,5], 3 在 P 中的位置是 2，接着我们把 3 移动到 P 的起始位置，得到 P=[3,1,2,4,5] 。
 * 对于 i=1: queries[i]=1, P=[3,1,2,4,5], 1 在 P 中的位置是 1，接着我们把 1 移动到 P 的起始位置，得到 P=[1,3,2,4,5] 。
 * 对于 i=2: queries[i]=2, P=[1,3,2,4,5], 2 在 P 中的位置是 2，接着我们把 2 移动到 P 的起始位置，得到 P=[2,1,3,4,5] 。
 * 对于 i=3: queries[i]=1, P=[2,1,3,4,5], 1 在 P 中的位置是 1，接着我们把 1 移动到 P 的起始位置，得到 P=[1,2,3,4,5] 。
 * 因此，返回的结果数组为 [2,1,2,1] 。
 *
 * 示例 2：
 * 输入：queries = [4,1,2,2], m = 4
 * 输出：[3,1,2,0]
 *
 * 示例 3：
 * 输入：queries = [7,5,5,8,3], m = 8
 * 输出：[6,5,0,7,5] 
 *
 * 提示：
 * 1 <= m <= 10^3
 * 1 <= queries.length <= m
 * 1 <= queries[i] <= m
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/queries-on-a-permutation-with-key
 */
public class processQueries {
    /**
     * 8ms
     */
    public int[] processQueries(int[] queries, int m) {
        int[] nums = new int[m];
        for (int i = 0; i < m; i++)
            nums[i] = i + 1;
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int index = search(nums, queries[i]);
            res[i] = index;
            change(nums, index);
        }
        return res;
    }

    private void change(int[] nums, int index) {
        int temp = nums[index];
        for (int i = index; i > 0; i--) {
            nums[i] = nums[i - 1];
        }
        nums[0] = temp;
    }

    private int search(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target)
                return i;
        }
        return -1;
    }

    /**
     * 5m
     */
    public int[] processQueries2(int[] queries, int m) {
        int[] numToIndex = new int[m + 1]; // 数字到索引的映射
        for (int i = 1; i <= m; i++)
            numToIndex[i] = i;
        int[] indexToNum = new int[m + 1];
        change(numToIndex, indexToNum, 1);
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            res[i] = numToIndex[queries[i]] - 1;
            change(numToIndex, indexToNum, queries[i]);
        }
        return res;
    }

    private void change(int[] numToIndex, int[] indexToNum, int num) {
        int index = numToIndex[num]; // 该数字现在的索引
        numToIndex[num] = 1;
        for (int i = 1; i < index; i++) // 索引在该数字之前的数indexToNum[i]，他们的索引都要+1
            numToIndex[indexToNum[i]] += 1;
        for (int i = 1; i < numToIndex.length; i++) // 更新索引到数字的映射
            indexToNum[numToIndex[i]] = i;
    }
}
