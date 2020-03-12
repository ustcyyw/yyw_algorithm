# 287. 寻找重复数

### 原题
给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。

示例 1:
输入: [1,3,4,2,2]
输出: 2

示例 2:
输入: [3,1,3,4,2]
输出: 3

说明：
不能更改原数组（假设数组是只读的）。
只能使用额外的 O(1) 的空间。
时间复杂度小于 O(n2) 。
数组中只有一个重复的数字，但它可能不止重复出现一次。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/find-the-duplicate-number)：https://leetcode-cn.com/problems/find-the-duplicate-number

**本题可以与leetcode540题对比，已总结在这 [540. 有序数组中的单一元素](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/BinarySearch/singleNonDuplicate.md)**

### 解法：二分查找（参考评论区大佬）

```java
public int findDuplicate(int[] nums) {
        int right = nums.length - 1;
        int left = 1;
        while (left < right) {
            int mid = (left + right) / 2;
            int count = 0;
            for (int num : nums) {
                if (num <= mid)
                    count++;
            }
            if (count > mid) { // mid是在没有重复元素时，小于等于mid的数的个数
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
```

思路分析：

* 题目限制：
    * 不能改变数组，所以不能使用原地排序
    * 只能使用$O(1)$的空间，不能复制数组也无法使用hash表
    * 时间复杂度要低于$O(n^2)$，不能使用暴力法。
* 数组中数字是从1到n连续的，只有一个元素重复出现了一次。如果从1,2到`i`的元素恰好有`i`个，说明这1到`i`没有重复元素。如果有重复元素，那么元素个数为`i+1`。以`i`为边界，其实就能划分查找区间。
* 所以问题可以转化为一个二分查找：
    * 如果数组中小于等于`mid`的元素个数恰好为`mid`个，则这1到`mid`的数字没有重复，查找范围修正为`[mid + 1, right]`。
    * 反之，如果数组中小于等于`mid`的元素个数大于`mid`，说明1到`mid`的数字有一个重复，查找范围修正为`[left, mid]`。
* 查找结束的条件就是当查找区间变为一个点时，这个点就是要找的答案，所以循环条件就是`while (left < right)`。
* 在`while`循环中，统计数字时，有单次遍历。二分查找的循环次数是$O(log(n))$的，所以时间复杂度为$O(nlog(n))$。

运行结果：
* 执行用时 :4 ms, 在所有 Java 提交中击败了59.72%的用户
* 内存消耗 :38.4 MB, 在所有 Java 提交中击败了10.64%的用户