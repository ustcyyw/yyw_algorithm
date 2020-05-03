# 5402. 绝对差不超过限制的最长连续子数组
### 原题
给你一个整数数组 `nums` ，和一个表示限制的整数 `limit`，请你返回最长连续子数组的长度，该子数组中的任意两个元素之间的绝对差必须小于或者等于` limit` 。
如果不存在满足条件的子数组，则返回 0 。

示例 1：
输入：nums = [8,2,4,7], limit = 4
输出：2 
解释：所有子数组如下：
[8] 最大绝对差 |8-8| = 0 <= 4.
[8,2] 最大绝对差 |8-2| = 6 > 4. 
[8,2,4] 最大绝对差 |8-2| = 6 > 4.
[8,2,4,7] 最大绝对差 |8-2| = 6 > 4.
[2] 最大绝对差 |2-2| = 0 <= 4.
[2,4] 最大绝对差 |2-4| = 2 <= 4.
[2,4,7] 最大绝对差 |2-7| = 5 > 4.
[4] 最大绝对差 |4-4| = 0 <= 4.
[4,7] 最大绝对差 |4-7| = 3 <= 4.
[7] 最大绝对差 |7-7| = 0 <= 4. 
因此，满足题意的最长子数组的长度为 2 。

示例 2：
输入：nums = [10,1,2,4,7,2], limit = 5
输出：4 
解释：满足题意的最长子数组是 [2,4,7,2]，其最大绝对差 |2-7| = 5 <= 5 。

示例 3：
输入：nums = [4,2,2,2,4,4,2,2], limit = 0
输出：3

提示：

```
1 <= nums.length <= 10^5
1 <= nums[i] <= 10^9
0 <= limit <= 10^9
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit)：https://leetcode-cn.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit

### 解法
```java
public class longestSubarray {
    public int longestSubarray(int[] nums, int limit) {
        int n = nums.length;
        int res = 1, i = 0, j = 0;
        PriorityQueue<Integer> min = new PriorityQueue<>();
        PriorityQueue<Integer> max = new PriorityQueue<>(Collections.reverseOrder());
        while (j < n && i < n) {
            if(min.size() == 0){
                min.add(nums[j]);
                max.add(nums[j]);
                j++;
                continue;
            }
            if (Math.abs(min.peek() - nums[j]) <= limit && Math.abs(max.peek() - nums[j]) <= limit) {
                min.add(nums[j]);
                max.add(nums[j]);
                res = Math.max(res, j - i + 1);
                j++;
            }
            else {
                min.remove(nums[i]);
                max.remove(nums[i]);
                i++;
            }
        }
        return res;
    }
}
```
思路分析：
* 子数组要求是连续的，要知道子数组的长度，只需要知道子数组最左边元素的索引与最右边元素索引`i, j`，那么子数组长度为`j - i + 1`。
* 子数组中任意两个元素的绝对值小于等于`limit`，只需要子数组内的最大值与最小值之差满足即可保证任意两元素之间满足要求。所以要维持子数组中的最大元素与最小元素。
* emmm，不知道如何解释怎么会想到滑动窗口，大概是因为看到了子数组是连续的，而且子数组的每一个元素的信息都用（要知道所有元素值才知道最小和最大值呢）。维持最大元素与最小元素，可以分别使用最大，最小优先队列。
* 初始化`res = 1`，因为每个单独元素都满足条件，长度为1。
* 在窗口滑动的过程中：
    * 如果队列中元素个数为0，说明现在区间长度为0，要将当前元素`nums[j]`都放入两个优先队列中，然后`j++; continue`。
    * 如果当前元素与子区间内最小，最大元素的绝对值都小于等于`limit`即`Math.abs(min.peek() - nums[j]) <= limit && Math.abs(max.peek() - nums[j]) <= limit`。此时这个元素可以被放入子数组，要更新子数组的最小最大值就将其放入两个队列，然后还需要更新`res = Math.max(res, j - i + 1);`。最后窗口向右生长1，`j++`。
    * 否则，这个元素不能加入子数组，子数组的左边界右移动1，首先将元素`nums[i]`从队列中删除，`i++`。

运行结果：

* 43ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹