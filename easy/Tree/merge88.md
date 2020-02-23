# 88.合并两个有序数组

### 原题
给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。

说明:
初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
示例:

输入:
nums1 = [1,2,3,0,0,0], m = 3
nums2 = [2,5,6],       n = 3
输出: [1,2,2,3,5,6]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/merge-sorted-array)：https://leetcode-cn.com/problems/merge-sorted-array

----

### 解法

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
        int[] temp = new int[m];
        System.arraycopy(nums1, 0, temp, 0, m);

        int i = 0, j = 0;
        for(int k = 0; k < m + n; k++){
            if(i >= m) nums1[k] = nums2[j++];
            else if (j >= n) nums1[k] = temp[i++];
            else if(temp[i] > nums2[j]) nums1[k] = nums2[j++];
            else nums1[k] = temp[i++];
        }
    }
```

思路分析：

* 这就是归并排序中的归并步骤。实现这一步需要辅助数组，这个题中要归并到`nums1`中，那么我们就用辅助数组来存放其前m个元素。`System.arraycopy(nums1, 0, temp, 0, m);`
* 用`i,j`分别指向`temp,nums2`两个数组的当前元素。单次循环，将`nums1`的`m+n`个位置放满该放的元素。注意，当`i>=m`时，意味着`temp`中的元素已归并完毕，此时就无脑将剩余的`nums2`中的元素放到`nums1`中即可；同理`j>=n`时类似处理。
* 除了上述两种情况，就需要比较两个数组中当前元素，将小的一个放到`num1`中。
* 要复制m个元素到辅助数组，归并又需要放置n+m个元素（单层循环），所以时间复杂度为$O(2m+n)$；空间复杂度为$O(m)$。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :38.3 MB, 在所有 Java 提交中击败了5.25%的用户