# 342. 4的幂

### 原题
给定一个整数 (32 位有符号整数)，请编写一个函数来判断它是否是 4 的幂次方。

示例 1:
输入: 16
输出: true

示例 2:
输入: 5
输出: false

进阶：
你能不使用循环或者递归来完成本题吗？

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/power-of-four)：https://leetcode-cn.com/problems/power-of-four

### 解法：位运算

```java
public boolean isPowerOfFour(int num) {
        return num > 0 && (num & (num - 1)) == 0 && (num & 0xaaaaaaaa) == 0;
    }
```

思路分析：

* 4的幂也是2的幂，参考[231. 2的幂](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/BitOperation/isPowerOfTwo.md)。但是4的幂更特殊一些，除了它一定非负，二进制表示只有1位是1以外，唯一的1出现在二进制中的奇数位（比如0，4，16，对应的二进制1出现在0，3，5位）
* 所以该数字在满足为2的幂的基础上`num > 0 && (num & (num - 1)) == 0`，还要去判断二进制的1是否出现在奇数位置。位的与运算，某一位其中之一为0，那么该位的结果就是0。所以可以使用1010这样的序列与要判断的数相与，如果结果为0，就可以保证1只出现在奇数位上。
    * 1010这样的32位数字，用16进制表示就是0xaaaaaaaa。
* 所以判断依旧为`num > 0 && (num & (num - 1)) == 0 && (num & 0xaaaaaaaa) == 0`。
* 时间复杂度：$O(1)$ 。空间复杂度是$O(1)$。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :36.6 MB, 在所有 Java 提交中击败了5.21%的用户

