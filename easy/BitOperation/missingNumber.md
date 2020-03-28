# 268. 缺失数字

### 原题
给定一个包含 0, 1, 2, ..., n 中 n 个数的序列，找出 0 .. n 中没有出现在序列中的那个数。

示例 1:
输入: [3,0,1]
输出: 2

示例 2:
输入: [9,6,4,2,3,5,7,0,1]
输出: 8

说明:
你的算法应具有线性时间复杂度。你能否仅使用额外常数空间来实现?

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/missing-number)：https://leetcode-cn.com/problems/missing-number

### 两种解法

##### 1.等差数列和

```java
public int missingNumber(int[] nums) {
        int sum = 0;
        for(int i : nums)
            sum += i;
        return nums.length * (nums.length + 1) / 2- sum;
    }
```

思路分析：

* 1+2+……+n-1+n的和可以直接使用等差数列的求和公式得到。
* 遍历数组，得到数组中所有元素的和，是缺少了一个数的和。用公式得到的和减去数组元素的和就得到了缺失的元素。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :42.7 MB, 在所有 Java 提交中击败了5.07%的用户

##### 2.异或的运用

```java
public int missingNumber2(int[] nums) {
        int res = 0;
        for(int i = 1; i <= nums.length; i++)
            res ^= i;
        for(int i : nums)
            res ^= i;
        return res;
    }
```

思路分析：

* 在本题中，我们可以想象，如果将1,2,3……n-1,n都放入数组中，那么数组中除了目标元素只出现了一次，其余元素都出现了两次。问题就变成了在一个所有元素都成对出现，但有一个元素只出现了一次的数组中找到只出现了一次的数组。
* 所以可以参考[136.只出现一次的数字](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/BitOperation/singleNumber.md)使用异或的做法。
    * 异或可以将相同的两个数变为0 即 x ^ x = 0
    * 异或具有交换律。
    * 0与一个数异或结果仍然为该数 0 ^ x = x
    * 两个相同的数进行异或操作会得到0，同时异或具有交换律，所以对于这个无序数组，成对的元素依次异或的结果为0。同时0 ^ x = x，所以最终只出现的一个元素会剩下。
* 本题先进行 `1 ^ 2 ^ 3 ^ …… ^ n - 1 ^ n`，再将这个结果与数组中每个元素进行异或得到的结果就是要找的答案。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：

* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :42.6 MB, 在所有 Java 提交中击败了5.07%的用户