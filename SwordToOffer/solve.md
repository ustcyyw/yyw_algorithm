# 03. 数组中重复的数字
### 原题
找出数组中重复的数字。
在一个长度为 n 的数组 nums 里的所有数字都在 0～n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。

示例 1：
输入：
[2, 3, 1, 0, 2, 5, 3]
输出：2 或 3 

限制：
2 <= n <= 100000

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof)：https://leetcode-cn.com/problems/shu-zu-zhong-zhong-fu-de-shu-zi-lcof

### 解法
```java
public int findRepeatNumber(int[] nums) {
        int[] count = new int[nums.length];
        for(int i : nums){
            if(count[i] == 1)
                return i;
            count[i] = 1;
        }
        return -1;
    }
```
思路分析：
* 要找到某个至少重复出现了两次的数字，显然会想到计数数字出现的次数，在遍历数组的时候如果发现某一个数字已经出现了两次，那就可以直接返回，因为题目只要求找到任意一个重复的数字。
* 一般计数会想到使用哈希表，但是本题题目中规定了数字的范围为`[0, n - 1]`。有限多种可能，而且是数字，所以可以使用数组来代替哈希表，数组的索引为键，代表某个数字，数组的值为值，代表索引对应数字的出现次数。
* 时间复杂度为$O(n)$，空间复杂度为$O(n)$。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了92.46%的用户
* 内存消耗 :47.4 MB, 在所有 Java 提交中击败了100.00%的用

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹