# 53. 最大子序和

### 原题
给定一个整数数组 `nums` ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

**示例:**
```
输入: [-2,1,-3,4,-1,2,1,-5,4],
输出: 6
解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
```
**进阶:**
如果你已经实现复杂度为 O(*n*) 的解法，尝试使用更为精妙的分治法求解。

[链接](https://leetcode-cn.com/problems/maximum-subarray)：https://leetcode-cn.com/problems/maximum-subarray

### 两种解法

##### 1.贪心算法

```java
public int maxSubArray(int[] nums) {
        int res = nums[0];
        int sum = nums[0];
        for(int i = 1; i < nums.length; i++){
            if(sum < 0) // 如果sum已经是负资产了，说明前面选定的子序列对于后面不论子序列在哪里结束都是负面影响，从当前元素开始尝试
                sum = nums[i];
            else // 如果sum是正的，当前元素为必然要加上，当前元素为负则加上看sum还是不是正资产，判断交由下一次循环来判断。
                sum += nums[i];
            res = Math.max(res, sum); // 因为每次循环都在记录最大和，所以不会出现遗漏。
        }
        return res;
    }
```

思路分析：

* 首先，如果在求加和的时候，如果前面`[0,i - 1]`的元素的和为负，那么无论`nums[i]`为多少，我们都应该将前面的和舍弃，因为加上`nums[i]`总会有`sum + nums[i] < nums[i]`。
    * 那么`sum`的值应该为多少，将其设置为`nums[i]`。
    * 如果`nums[i]`为负怎么办？会在下一次循环时的第一个判断当负资产丢掉。
* 如果前面的`[0, i - 1]`的元素和为正，那么无论`nums[i]`为多少，我们都应该继续加和，因为加上`nums[i]`总会有`sum + nums[i] > nums[i]`。
    * 如果`nums[i]`为负怎么办？只要``sum + nums[i]`为正，就可以先保留看与后面相加的情况，因为总的来说`sum + nums[i]`是总资产。如果`sum + nums[i]`为负数，则会在下一次循环的第一个判断被我们当负资产丢弃。
* 由于要求最大的子序列之和，所以每遍历一个元素都需要看目前的累加和是否最大。`res = Math.max(res, sum);`
* 时间复杂度是$O(n)$的，因为只有一次遍历；空间复杂度是$O(1)$的。

运行结果：

* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户

* 内存消耗 :41.6 MB, 在所有 Java 提交中击败了8.46%的用户

##### 2.动态规划

```java
public int maxSubArray2(int[] nums) {
       int[] count = new int[nums.length]; // count[i] 表示 0~i 这些元素 所有以nums[i]为结尾的连续子序列中 的最大和
       count[0] = nums[0];
       for(int i = 1; i < nums.length; i++){
           count[i] = Math.max(count[i - 1] + nums[i], nums[i]);
       }
       int res = Integer.MIN_VALUE;
       for(int i : count)
           res = Math.max(res, i);
       return res;
    }
```

思路分析：

* 子序列的和，子序列的积，最大上升子序列等等涉及子序列的最值。都可以考虑使用动态规划，状态方程都可以尝试设置为与下标`i`为最后一个元素的子序列的相应的值
    * 比如本题`count[i]`为以`nums[i]`为最后一个元素的所有子序列的最大和。
    * 比如[152.乘积最大子序列](https://leetcode-cn.com/problems/maximum-product-subarray/)，状态可以设置为以`nums[i]`为最后一个元素的所有子序列的最大值。（不过称的话，因子的正负影响乘机大小，所以初步这么想之后还要细细推敲一下，需要两个状态——最大状态与最小状态）
* 确定状态之后，转移方程很简单。如果`count[i - 1] + nums[i] > nums[i] `，意味着以`nums[i - 1]`结尾的子序列再加上`nums[i]`比`nums[i]`更大，那么以`nums[i]`结尾的子序列的最大和就为`count[i - 1] + nums[i]`。
* 初状态` count[0] = nums[0];`
* 最后只要遍历所有`count[]`的元素，找到最大的那个值即为结果。

* 时间复杂度是$O(n)$的，因为进行了单层遍历；空间复杂度是$O(1)$的。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了98.60%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了8.61%的用户