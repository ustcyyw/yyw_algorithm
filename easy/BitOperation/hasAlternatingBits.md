# 693. 交替位二进制数

### 原题
给定一个正整数，检查他是否为交替位二进制数：换句话说，就是他的二进制数相邻的两个位数永不相等。

示例 1:
输入: 5
输出: True
解释:
5的二进制数是: 101

示例 2:
输入: 7
输出: False
解释:
7的二进制数是: 111

示例 3:
输入: 11
输出: False
解释:
11的二进制数是: 1011

示例 4:
输入: 10
输出: True
解释:
10的二进制数是: 1010

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/binary-number-with-alternating-bits)：https://leetcode-cn.com/problems/binary-number-with-alternating-bits

### 解法：位运算

```java
public boolean hasAlternatingBits(int n) {
        int pre = n & 1;
        n >>>= 1;
        while(n != 0){
            if((n & 1) == pre)
                return false;
            pre = n & 1;
            n >>>= 1;
        }
        return true;
    }
```

思路分析：

* 判断某个数的二进制数相邻的两个位数永不相等。所以还是要按顺序知道它二进制数的每一位的数字是多少。参考[191.只出现一次的数字](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/BitOperation/hammingWeight.md)的做法来获取每一位二进制数字。
* 这里需要拿当前位二进制的数字与前一位二进制的数字进行比较，所以在进入循环之前，先通过`int pre = n & 1`获取第一位二进制的数字。在循环过程中如果发现当前位的数字与前一位相等`(n & 1) == pre`，就返回false。
* 在循环中不断无符号右移n的同时，还要不断更新`pre =  n & 1`。
* 时间复杂度：$O(1)$ 。运行时间依赖于数字n的位数。由于这题中n是 32 位数。空间复杂度是$O(1)$的。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了69.73%的用户
* 内存消耗 :36 MB, 在所有 Java 提交中击败了5.74%的用户