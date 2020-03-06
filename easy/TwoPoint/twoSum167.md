# 167.两数之和-输入有序数组

### 原题
给定一个已按照 **升序排列的有序数组**，找到两个数使得它们相加之和等于目标数。
函数应该返回这两个下标值 index1 和 index2，其中 index1 必须小于 index2。

说明:
返回的下标值（index1 和 index2）不是从零开始的。
你可以假设每个输入只对应唯一的答案，而且你不可以重复使用相同的元素。

示例:
输入: numbers = [2, 7, 11, 15], target = 9
输出: [1,2]
解释: 2 与 7 之和等于目标数 9 。因此 index1 = 1, index2 = 2 。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted)：https://leetcode-cn.com/problems/two-sum-ii-input-array-is-sorted

### 解法 双指针

```java
	public int[] twoSum(int[] numbers, int target) {
        int[] res = new int[2];
        for(int i = 0, j = numbers.length - 1; i < j; ){
            int sum = numbers[i] + numbers[j];
            if(sum > target) j--;
            else if(sum < target) i++;
            else {
                res[0] = i + 1;
                res[1] = j + 1;
                break;
            }
        }
        return res;
    }
```

思路分析：

* 题目中的重点在于，一个有序数组。这与无序数组中找两个元素加和为`target`肯定是不同的。要充分利用有序这个条件。
* 如果两个大的数相加，大于`target`，不知道将哪个替换为更小才有可能是答案；如果两个小的数相加，小于`target`，不知道将哪个替换为更大的才有可能是答案。
* 但是如果使用双指针，一个指向小元素，一个指向大元素。那么相加大于`target`时，肯定是因为大的元素太大了，要换一个稍微小一点的。相加小于`target`时，肯定是因为小的元素不够大，要换一个稍微大一点的。为什么不是大的元素换大一点的呢？因为之前已经判断过了，才会将大元素指针移到这。

* 时间复杂度，只有一次遍历，所以是$O(n)$的。空间复杂度是$O(1)$。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了98.37%的用户
* 内存消耗 :42.3 MB, 在所有 Java 提交中击败了5.06%的用户