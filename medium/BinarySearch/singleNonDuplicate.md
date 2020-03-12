# 540. 有序数组中的单一元素

### 原题
给定一个只包含整数的有序数组，每个元素都会出现两次，唯有一个数只会出现一次，找出这个数。

示例 1:
输入: [1,1,2,3,3,4,4,8,8]
输出: 2

示例 2:
输入: [3,3,7,7,10,11,11]
输出: 10

注意: 您的方案应该在 O(log n)时间复杂度和 O(1)空间复杂度中运行。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/single-element-in-a-sorted-array)：https://leetcode-cn.com/problems/single-element-in-a-sorted-array

这个题可以和leetcode[287. 寻找重复数](https://leetcode-cn.com/problems/find-the-duplicate-number/)对比。该题是除了某一个元素出现了两次，其余都只出现了一次，找出重复的数。（PS 我也整理在了本目录下，可以找一下看看）

### 解法：二分查找

```java
public int singleNonDuplicate(int[] nums) {
        if(nums.length == 1)
            return nums[0];
        if(nums[0] != nums[1]) // 这两处的处理是为了防止递归中的 mid + 1与mid - 1越界
            return nums[0];
        int hi = nums.length - 1;
        if(nums[hi - 1] != nums[hi])
            return nums[hi];
        return find(nums, 2, hi - 2);
    }

    private int find(int[] nums, int lo, int hi){
        if(lo > hi) return nums[lo];
        int mid = (hi - lo) / 2 + lo;
        int count = mid - lo + 1;
        if(nums[mid] == nums[mid - 1]){
            if(count % 2 == 0)
                return find(nums, mid + 1, hi);
            else
                return find(nums, lo, mid - 2); // 这里一定要注意 要将作为中间点相等的两个元素放在下一次查找的区间外
        }
        else if(nums[mid] == nums[mid + 1]){
            if(count % 2 == 1)
                return find(nums, mid + 2, hi);
            else
                return find(nums, lo, mid - 1);
        }
        else return nums[mid];
    }
```

思路分析：

* 给定的是一个排序数组，单次遍历很直观但是达不到题目的要求，要求时间复杂度为线性对数级别的。看起来很想是用二分查找。可以尝试一下，我们的目标是找到只出现了一次的元素，所以过程就是二分然后缩小答案所在的区间。
* 需要思考如何去缩小查找区间，以及查找结束的条件。先定义一个辅助递归函数`private int find(int[] nums, int lo, int hi)`，`lo`，`hi`分别表示查找区间的左右边界（闭区间）。
* 如何缩小查找区间，是这个题的难点：
    * 可以用题目给的示例做一个初步判断。首先查看`nums[mid]`，但是我们不是查找目标值，而是查看某个元素有没有重复。所以单一的`nums[mid]`没有任何意义，要将其与前面一个元素，后面一个元素比较才有意义。如果它和前一个元素，后一个元素都不相等，那就说明我们要找的元素就是他，递归结束`return nums[mid];`
    * 除了要找的元素以外，其它元素都出现了两次。所以包含了要找的元素的区间，区间内的元素个数为奇数。不包含要找元素的区间，其元素个数为偶数。当然这里**要求相等的元素不能被拆分在两个区间**。例如`[1,1,2,3,3]`有5个元素，`[4,4,8,8]`有四个元素。但是`[3,4,4,8,8]`这样的子区间不可以出现。因为这样会使得元素个数奇偶性判明目标元素位置失效。
    * 由此也可以看出排序数组的意义在于，使得相等的元素连在一起。
    * `nums[mid]`不知道是重复元素中的前一个还是后一个。所以先判断`if(nums[mid] == nums[mid - 1])`，如果成立，`nums[mid]`是重复元素中的后一个。
        * 区间`[lo, mid]`的元素个数为`int count = mid - lo + 1;`
        * 如果说`count`是偶数，那么`[lo, mid]`内都是成对出现的元素，所以答案要在`[mid + 1, hi]`中寻找。`return find(nums, mid + 1, hi);`。注意，这里将`nums[mid]`与其重复元素都放到了左边区间。不违背前面所说的原则。
        * 如果说`count`是奇数，说明`[lo, mid]`内部有一个元素没有重复，所以答案要在`[lo, mid - 2]`中查找，`return find(nums, lo, mid - 2);`。为什么右边界是`mid - 2`，因为`mid, mid - 1`这两个元素已经是相等的了。注意，这里将`nums[mid]`与其重复元素都放到了右边区间。不违背前面所说的原则。
    * `else if(nums[mid] == nums[mid + 1])`，说明`nums[mid]`为重复元素中的前一个。
        * 如果说`count`是奇数，那么`[lo, mid - 1]`的元素成对出现，目标元素只能在`[mid + 2, hi]`中查找` return find(nums, mid + 2, hi);`，为什么右边界是`mid + 2`，因为`mid, mid + 1`这两个元素已经是相等的了。注意，这里将`nums[mid]`与其重复元素都放到了左边区间。不违背前面所说的原则。
        * 类似可以知道`coutn`为偶数时`return find(nums, lo, mid - 1);`
* 难点处理完后，不要忘记特殊情况，如数组只有一个元素情况。另外我们从`find`函数中看到，会用到索引`mid + 1, mid - 1`，所以为了避免越界，先将两端的情况排除：
    * `if(nums[0] != nums[1])`说明第一个元素是目标元素。
    * `if(nums[hi - 1] != nums[hi])`说明最后一个元素是目标元素。
* 边界处理完后，第一第二，倒数第一，第二个元素已经检查过了，所以调用`find`时，查找边界是`[2, hi - 2]`。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.6 MB, 在所有 Java 提交中击败了5.10%的用户