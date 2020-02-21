# 485最大连续1的个数

### 原题

给定一个二进制数组， 计算其中最大连续1的个数。

示例 1:

输入: `[1,1,0,1,1,1]`
输出: 3
解释: 开头的两位和最后的三位都是连续1，所以最大连续1的个数是 3.
注意：

输入的数组只包含 0 和1。
输入数组的长度是正整数，且不超过 10,000。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/max-consecutive-ones)：https://leetcode-cn.com/problems/max-consecutive-ones

----

### 两种解法

##### 1.单次循环，计数所有连续1的长度

```java
public int findMaxConsecutiveOnes(int[] nums) {
        int result = 0;
        int temp = 0;
        for (int num : nums) {
            if (num == 1)
                temp++;
            else {
                result = Math.max(result, temp);
                temp = 0;
            }
        }
        return Math.max(result, temp);
    }
```

思路分析：

* 问题很直观，单次遍历所有元素即可。使用变量`int temp`来记录连续1的个数，所以当元素为0时要将其重置为0，并且更新最长的长度`result = Math.max(result, temp);`
* 不过不能漏掉最后一个元素是1的情况，所以要在循环结束之后再更新一次`result`
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了69.75%的用户
* 内存消耗 :38.7 MB, 在所有 Java 提交中击败了64.94%的用户

##### 2.单次循环，记录0元素的索引

```java
public int findMaxConsecutiveOnes2(int[] nums) {
        int pre = -1; // 用来表示上一个为0元素的索引
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                result = Math.max(result, i - pre - 1);
                pre = i;
            }
        }
        result = Math.max(result, nums.length - pre - 1);
        return result;
    }
```



思路分析：

* 换一个角度，分别记录连续1的片断前后的两个0的索引，通过索引的计算也可以得到连续1的长度。
* 使用`pre`记录前一个0的索引，在单次遍历中遇到0元素时`i`即为后一个0的索引，此时更新`result = Math.max(result, i - pre - 1);`。并且更新`pre = i`，因为上一个连续的1片段长度已记录，要移位`pre`。
* 同样，不能忽略最后一个元素为1的情况，这时相当于不存在的下一个元素为0，其索引为`nums.length`。所以更新最大长度的做法也是`Math.max(result, nums.length - pre - 1);`

运行结果：
* 执行用时 :4 ms, 在所有 Java 提交中击败了36.84%的用户
* 内存消耗 :39.4 MB, 在所有 Java 提交中击败了36.06%的用户