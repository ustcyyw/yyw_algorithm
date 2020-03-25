# 349. 两个数组的交集

### 原题
给定两个数组，编写一个函数来计算它们的交集。

示例 1:
输入: nums1 = [1,2,2,1], nums2 = [2,2]
输出: [2]

示例 2:
输入: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
输出: [9,4]
说明:

输出结果中的每个元素一定是唯一的。
我们可以不考虑输出结果的顺序。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/intersection-of-two-arrays)：https://leetcode-cn.com/problems/intersection-of-two-arrays

### 解法：排序+双指针

```java
public int[] intersection(int[] nums1, int[] nums2) {
        List<Integer> temp = new ArrayList<>();
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        int pre = Integer.MIN_VALUE;
        for(int i = 0, j = 0; i < nums1.length && j < nums2.length; ){
            if(nums1[i] < nums2[j]) i++;
            else if(nums1[i] > nums2[j]) j++;
            else {
                if(pre != nums1[i]){
                    pre = nums1[i];
                    temp.add(nums1[i]);
                }
                i++;
                j++;
            }
        }
        int[] res = new int[temp.size()];
        for(int i = 0; i < res.length; i++)
            res[i] = temp.get(i);
        return res;
    }
```

思路分析：

* 找到交集，就是找到相等的元素。涉及到两个数组，并且相等比较其实也是一种次序比较。然后就想到很类似于归并排序中对两个数组的归并。
* 所以尝试一下，如果两个数组是有序的。用`i`表示数组1的索引，`j`表示数组2的索引。
    * 如果`nums1[i] < nums2[j]`，那么下一个可能相等的元素应该是`nums1[i + 1]`，所以`i++`。
    * 同理，如果`nums1[i] > nums2[j]`时，`j++`。
    * 如果`nums1[i] == nums2[j]`，这就是交集中的一个元素，同时移动两个指针去找下一个可能相等的元素`i++, j++`。但是注意题目中交集不允许出现重复元素，但是原数组可能会有重复的相等元素，这要求在找到两数组共有元素时，它不在交集中才加入交集。
* 注意，这里数组已经是排序数组，所以重复元素一定接连出现。那么出现两数组相等元素时，只要将其与前一个元素相比，不相等则说明这里的相交元素第一次出现，加入交集中。那这里为什么使用`nums[i] != nums[i - 1]`呢？避免数组越界，如果两个数组最小的元素就相等呢！？所以采用一个变量记录前一个元素的值`pre`，初始化为`int pre = Integer.MIN_VALUE;`
* 这么看来，先将数组进行排序可行，会比暴力遍历快很多。采用先排序的方法。时间复杂度为$O(nlog(n))$
* 另外，由于交集元素个数不确定，只能先使用`List`来存放元素。空间复杂度为$O(k)$，k为交集的元素个数。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了99.17%的用户
* 内存消耗 :39 MB, 在所有 Java 提交中击败了5.11%的用户