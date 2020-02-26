# 633.平方数之和

### 原题

给定一个非负整数 c ，你要判断是否存在**两个整数 a 和 b**，使得 a^2 + b^2 = c。

示例1:
输入: 5
输出: True
解释: 1 * 1 + 2 * 2 = 5

示例2:
输入: 3
输出: False

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/sum-of-square-numbers)：https://leetcode-cn.com/problems/sum-of-square-numbers

### 两种解法

##### 1.使用 sqrt 函数（我的解法）

```java
public boolean judgeSquareSum(int c) {
        int end = (int) Math.sqrt(c);
        for (int i = 0; i <= end; i++) {
            double temp = Math.sqrt(c - i * i);
            if (temp == (int) temp)
                return true;
        }
        return false;
    }
```

思路分析：

* 先来确定可能的整数的范围，负数平方等价于正数。题目中没有规定正整数，所以0也在可选范围内。当然如果一个整数的平方已经大于c，与另外一个数相加肯定超过了。
* 所以可能的整数范围是`[0, (int) Math.sqrt(c)]`
* 在单次遍历可能的整数时，选了第一个数`i`之后，如果满足`Math.sqrt(c - i * i)`为一个整数，则找到了符合题意的整数。在java中可以用`double temp = Math.sqrt(c - i * i);if (temp == (int) temp)`来判断。如果遍历完所有可能的整数之后，没有找到这样的组合，说明不存，返回`false`。
* 时间复杂度，遍历了`Math.sqrt(c)`个元素，$O(sqrt(c))$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :5 ms, 在所有 Java 提交中击败了36.36%的用户
* 内存消耗 :36.3 MB, 在所有 Java 提交中击败了5.18%的用户

##### 2.使用双指针（标答）

```java
public boolean judgeSquareSum2(int c){
        int end = (int) Math.sqrt(c);
        int start = 0;
        while(start <= end){
            int temp = start * start + end * end;
            if(temp == c)
                return true;
            else if(temp < c)
                start++;
            else end--;
        }
        return false;
    }
```

思路分析：

* 确定可能的整数的范围如方法1。
* 可以使用双指针的想法来完成。初始时，一个指针`start`指向备选的最小数，另外一个指针`end`指向备选的最大数。当两数相加`int temp = start * start + end * end;`等于c时找到满足条件的两整数，返回`true`；如果小于c说明备选的较小的数太小，要换一个稍微大一点的数，所以`start++`；如果大于c说明备选的较大的数太大，要换一个稍微小一点的数，所以`end--`。
* 注意循环条件，当首尾指针交叉后，所有可能情况都考虑过了，跳出循环。说明不能找打符合题意的结果。

### 反思

这个题一开始看起来很像[题目towSum](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/other/TwoSum.md)，但是本题中a与b可能是相等的，题目twoSum中数字在一个数组中，元素值可能重复，但不会使用同一个元素。所以twoSum可以使用HashSet来做，本题不行。

