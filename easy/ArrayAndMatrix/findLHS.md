# 最长和谐子序列

### 原题
和谐数组是指一个数组里元素的最大值和最小值之间的差别正好是1。
现在，给定一个整数数组，你需要在所有可能的子序列中找到最长的和谐子序列的长度。

示例 1:
输入: [1,3,2,2,5,2,3,7]
输出: 5
原因: 最长的和谐数组是：[3,2,2,2,3].
说明: 输入的数组长度最大不超过20,000.

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/longest-harmonious-subsequence)：https://leetcode-cn.com/problems/longest-harmonious-subsequence

### 两种解法

##### 1.使用HashMap记录每个值出现的次数

```java
	public int findLHS(int[] nums) {
        int result = 0;
        Map<Integer, Integer> countMap = new HashMap<>((int) (nums.length / 0.75F + 1.0F));
        for (int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }
        for (int key : countMap.keySet()) {
            if (countMap.containsKey(key + 1))
                result = Math.max(countMap.get(key) + countMap.get(key + 1), result);
        }
        return result;
    }
```

思路分析：

* 由题目给的例子可以知道，这个子序列并不需要是原数组中连续的元素。和谐子序列中的差最多为1，换句话说子序列中的元素有且仅有两个值，并且相差为1。所以要找最长子序列的长度，只需要枚举出所有只包含两个元素且值相差1的子序列，找出包含元素的数量最大值。
* 统计出每一个值出现了几次，可以使用`Map<Integer, Integer> countMap`，Map的键是数组中元素的值，Map的值则是出现的次数。
* 然后对于原数组中每一个出现的值，去查找有米有比它大1的值
    * 如果没有意味着，这个值无法和其他值构成和谐子序列。
    * 如果有，构成了和谐子序列，就去查看其长度。这个长度就是两个值出现的次数之和。与之前记录的最长长度比较，保留更大的长度。
* 由于只遍历了一次数组，遍历了一次数组中出现的值，散列表的插入与查找均摊成本是$O(1)$的，所以时间复杂度为$O（n+m）$。空间复杂度就是散列表所占空间。

运行结果：
* 执行用时 :36 ms, 在所有 Java 提交中击败了80.42%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了19.90%的用户
##### 2.排序，将值相等的元素集中

```java
	public int findLHS2(int[] nums) {
        if (nums.length == 0)
            return 0;
        int result = 0;
        Arrays.sort(nums);
        int preStart = 0, curStart = 0, nextStart = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[preStart]) {
                curStart = i;
                break;
            }
        }
        while (true) {
            for (int i = curStart + 1; i < nums.length; i++) {
                if (nums[i] != nums[curStart]) {
                    nextStart = i;
                    break;
                }
            }
            if (nextStart == curStart) { // 这里面很容易错啊！ emmm
                if (nums[curStart] - nums[preStart] == 1)
                    result = Math.max(result, nums.length - preStart);
                return result;
            }
            if (nums[curStart] - nums[preStart] == 1)
                result = Math.max(result, nextStart - preStart);
            preStart = curStart;
            curStart = nextStart;
        }
    }
```

思路分析：

* 关于最长子序列，如同方法一所说，要统计出每一个值出现了几次。另外一种方式就是将相等的元素放到一起，这样一段一段相等的序列，其长度就是每个值出现的次数。所以先对数组进行排序。
* 要知道两个相差1的值分别出现了几次，就需要三个指针。`preStart`,`curStart`,`nextStart`。
* 如图来查看三个指针的作用。

![findLHS.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/ArrayAndMatrix/findLHS.jpg?raw=true)

* 通过三个指针可以知道上一个值的大小，当前值的大小，相减是否为一可以判断是否为和谐子序列。另外通过`nextStart - preStart`就能知道上一个值与当前值一共出现了几次。同时，当我们找不到下一个元素时`nextStart == curStart`，说明所有可取的值都已经取过了，将最后两个值的情况考虑之后即返回结果，代码的20~24行。

运行结果：
* 执行用时 :31 ms, 在所有 Java 提交中击败了86.78%的用户
* 内存消耗 :51.2 MB, 在所有 Java 提交中击败了5.21%的用户
