# 152. 乘积最大子序列
给定一个整数数组 `nums` ，找出一个序列中乘积最大的连续子序列（该序列至少包含一个数）。
**示例 1:**
```
输入: [2,3,-2,4]
输出: 6
解释: 子数组 [2,3] 有最大乘积 6。
```
**示例 2:**

```
输入: [-2,0,-1]
输出: 0
解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
```
[链接]((https://leetcode-cn.com/problems/maximum-product-subarray/)): (https://leetcode-cn.com/problems/maximum-product-subarray/)

### 解法：动态规划

```java
public int maxProduct(int[] nums) {
        int res = nums[0];
        int min = nums[0], max = nums[0]; // 表示以i这个元素为结尾的子串的最大/最小积
        for(int i = 1; i < nums.length; i++){
            if(nums[i] < 0){ // 当第i个元素为负数时，截止上一位最大的乘上nums[i]反而可能是最小的；截止上一位最小的乘上nums[i]反而可能是最大的。
                int temp = min; // 所以将min max的值进行交换，这样才能在后续使用统一的代码。
                min = max;
                max = temp;
            }
            max = Math.max(max * nums[i], nums[i]);
            min = Math.min(min * nums[i], nums[i]);
            res = Math.max(res, max);
        }
        return res;
    }
```

思路分析：

* 子序列的和，子序列的积，最大上升子序列等等涉及子序列的最值。都可以考虑使用动态规划，状态方程都可以尝试设置为与下标`i`为最后一个元素的子序列的相应的值。
    * 可以看[53. 最大子序和](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/DP/maxSubArray.md)的第二种解法思路。
    * 这题与53题不一样的地方在于，如果是求和，某个元素的正负并不会影响加和的大小。但是求积，某个元素为负则会使得乘积的大小完全转变——最大值乘上负数之后可能变为最小值了，最小值乘上负数之后可能变为最大值了。
    * 所以本题需要定义两种状态。`min[i]`表示以`nums[i]`结尾的所有连续子序列的积的最小值。`max[i]`表示以`nums[i]`结尾的所有连续子序列的积的最大值。
* 如果数组中都是正数，状态转移方程就可以写为`max[i] = Math.max(Max[i] * nums[i], nums[i])`。（因为`nums[i]`可能为负）。这是**连续子序列**，所以以上一个元素结尾的最大乘积称`nums[i]`就是可能的以`nums[i]`为最后一个元素的子序列的最大积。
* 但是数组中有可能为负，遇到某个负数时，乘积的大小完全转变.
    * 正负的转变就是说，如果当前`nums[i]`是负数，那么`nums[i] * max[i]`得到的是最小值；我们需要拿以`nums[i - 1]`结尾的子序列的最小值与`nums[i]`这个负数相乘得到最大值。可见我们要维持另外一个状态`min[i]`。
    * 另外一个状态。状态方程为`min[i] = Math.min(min[i] * nums[i], nums[i])`。同样这样写也会出现正负变换的问题，如果`nums[i]`为负，就需要拿`max[i]`来乘得到可能的最小值。
    * 为了完成这个正负变换，且状态方程写得统一，我们可以在遇到`nums[i]`为负数的时候，先将`max[i-1]`与`min[i - 1]`交换值。
* 边界条件为：以第一个元素为子序列的结尾时，`max[0]=nums[0], min[0]=nums[0]`
* 在遍历当中每一次都将res，设置为`max[i]`与`res`的大的那个。
* 这里还可以考虑**状态压缩**，因为每次计算`max[i]`与`min[i]`，都只需要`max[i - 1]`与`min[i - 1]`即可，所以我们只需要用两个变量`int min, max`即可。
* 时间复杂度为$O(n)$；空间复杂度为$O(1)$。