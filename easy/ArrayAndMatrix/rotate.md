# 189. 旋转数组

### 原题
给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。

示例 1:
输入: [1,2,3,4,5,6,7] 和 k = 3
输出: [5,6,7,1,2,3,4]
解释:
向右旋转 1 步: [7,1,2,3,4,5,6]
向右旋转 2 步: [6,7,1,2,3,4,5]
向右旋转 3 步: [5,6,7,1,2,3,4]

示例 2:
输入: [-1,-100,3,99] 和 k = 2
输出: [3,99,-1,-100]
解释: 
向右旋转 1 步: [99,-1,-100,3]
向右旋转 2 步: [3,99,-1,-100]

说明:
尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
要求使用空间复杂度为 O(1) 的 原地 算法。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/rotate-array)：https://leetcode-cn.com/problems/rotate-array

### 两种解法

##### 1.数组copy

```java
public void rotate(int[] nums, int k) {
        int temp = k % nums.length;
        if (temp == 0) return;
        int[] tempArr = new int[temp];
        System.arraycopy(nums, nums.length - temp, tempArr, 0, temp);
        System.arraycopy(nums, 0, nums, temp, nums.length - temp);
        System.arraycopy(tempArr, 0, nums, 0, temp);
    }
```

思路分析：

* 关于旋转的描述，以及结果的周期性请看题目[61. 旋转链表](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/LinkedList/rotateRight.md)。对于数组也同样，所以将k简化成`int temp = k % nums.length`。如果`temp == 0`说明不需要旋转，直接返回。
* 选择`temp`次就是将数组最后`temp`个元素放到数组最前面，然后其余元素往后移动`temp`个位置。这个过程可以用辅助数组来存放修改时会被覆盖的元素，然后使用`System.arraycopy()`来完成移动。
* 首先，将后`temp`个元素存放在辅助数组`tempArr`中，从原数组索引为`nums.length - temp`开始到原数组最后一个元素，复制这`temp`个元素到辅助数组中，从辅助数组索引0处开始复制`System.arraycopy(nums, nums.length - temp, tempArr, 0, temp);`。
* 然后将原数组的前`nums.length - temp`个元素后移`temp`个位置，也就是将这些元素从索引`temp`往后填充`System.arraycopy(nums, 0, nums, temp, nums.length - temp);`
* 然后再将辅助数组的元素复制回原数组前`temp`个位置。`System.arraycopy(tempArr, 0, nums, 0, temp);`
* 时间复杂度为线性的，空间复杂度为`k % nums.length`。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.8 MB, 在所有 Java 提交中击败了5.19%的用户

##### 2.数组翻转（官方，原地修改）

```java
public void rotate2(int[] nums, int k) {
        k = k % nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    private void reverse(int[] nums, int lo, int hi) {
        while (lo < hi) {
            exch(nums, lo++, hi--);
        }
    }

    private void exch(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
```

思路分析：

* 旋转将后面的元素放置到前面，前面的元素放置到后面。如果翻转数组可以到达这个目的，但是旋转到数组前面的元素与向后移动的元素，这两个分组内的元素顺序也翻转了。那么再将他们翻转过来即可。

    * 1 2 3 4 5 6 7 整个数组翻转为 7 6 5 4 3 2 1
    * 将前k个进行翻转 5 6 7 4 3 2 1
    * 再将剩余元素翻转 5 6 7 1 2 3 4
    * 这样就完成了旋转。
* 翻转函数就是一个双指针`lo, hi`，将当前两个指针所指的两个元素交换，然后两个指针向内移动，直到两个指针交叉。
* 时间复杂度为线性的，空间复杂度为$O(1)$。

