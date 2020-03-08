# 5352. 生成每种字符都是奇数个的字符串

### 原题
给你一个整数 n，请你返回一个含 n 个字符的字符串，其中每种字符在该字符串中都恰好出现 奇数次 。
返回的字符串必须只含小写英文字母。如果存在多个满足题目要求的字符串，则返回其中任意一个即可。

示例 1：
输入：n = 4
输出："pppz"
解释："pppz" 是一个满足题目要求的字符串，因为 'p' 出现 3 次，且 'z' 出现 1 次。当然，还有很多其他字符串也满足题目要求，比如："ohhh" 和 "love"。

示例 2：
输入：n = 2
输出："xy"
解释："xy" 是一个满足题目要求的字符串，因为 'x' 和 'y' 各出现 1 次。当然，还有很多其他字符串也满足题目要求，比如："ag" 和 "ur"。

示例 3：
输入：n = 7
输出："holasss"

提示：
1 <= n <= 500

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/generate-a-string-with-characters-that-have-odd-counts)：https://leetcode-cn.com/problems/generate-a-string-with-characters-that-have-odd-counts

### 解法

```java
	public String generateTheString(int n) {
        StringBuilder sb = new StringBuilder();
        if (n % 2 == 0) {
            for (int i = 0; i < n - 1; i++)
                sb.append('a');
            sb.append('b');
        } else {
            for (int i = 0; i < n; i++)
                sb.append('a');
        }
        return sb.toString();
    }
```

思路分析：

* 只需要找到满足条件的一个字符串。条件是：字符串中的每一个字符，出现次数都是奇数。即没有限定要有几个字符，也没有限定每个字符至少至多能出现几次。
* 那么找最简单的越方便，如果给定的n是奇数，那么我只用一个小写字母构成该长度的字符串就满足题意。如果n是偶数，那么我就用一个小写字母构成n-1位，第n位选另外一个小写字母也满足了题意。
* 为了避免频繁的字符串拼接，使用`StringBuilder`类。
* 时间复杂度是$O(n)$的，空间复杂度也是$O(n)$。

运行结果：执行时间1ms。