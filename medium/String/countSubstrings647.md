# 647. 回文子串

### 原题
给定一个字符串，你的任务是计算这个字符串中有多少个回文子串。
具有不同开始位置或结束位置的子串，即使是由相同的字符组成，也会被计为是不同的子串。

示例 1:
输入:` "abc"`
输出: 3
解释: 三个回文子串: `"a", "b", "c"`.

示例 2:
输入:` "aaa"`
输出: 6
说明: 6个回文子串:` "a", "a", "a", "aa", "aa", "aaa"`.

注意:
输入的字符串长度不会超过1000。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/palindromic-substrings)：https://leetcode-cn.com/problems/palindromic-substrings

### 三种解法

##### 1.暴力法

```java
public int countSubstrings(String s) {
        char[] chars = s.toCharArray();
        int result = 0;
        for (int i = 2; i <= s.length(); i++) {
            for (int j = 0; j + i - 1 < s.length(); j++)
                if (isPalindrome(chars, j, j + i - 1))
                    result++;
        }
        return result + s.length();
    }

    private boolean isPalindrome(char[] chars, int start, int end) {
        for (int i = start, j = end; j > i; i++, j--) {
            if (chars[i] != chars[j])
                return false;
        }
        return true;
    }
```

思路分析：

* 首先明确如何判断一个字符串是否为回文字符串。第一个字符与最后一个字符相同，第二个字符与倒数第二个字符相同.....关于中心位置轴对称。
* 本题要求一共有多少个回文子串，那么就需要判断，索引`[i, j]`的子串是不是回文子串，遍历所有这样`[i, j]`进行判断就可以找到回文子串的总数。这是暴力的做法。
* 首先如何判断`[i, j]`的子串是回文串，根据定义来判断即可。
    * 定义`boolean isPalindrome(char[] chars, int start, int end)`，第一个参数为原字符串的字符数组表示，第二第三个参数分别是字串的开始与结束索引。
    * 在区间`[start, end]`上进行双指针的扫描，将关于中心位置对应的字符进行比较，如果发现有不相等说明不是回文子串。
    * 循环结束没有发现对应位置不相等的字符，说明是回文串。
* 主函数中
    * 外循环的`i`表示子串的长度，子串最长为`s.length()`，所以循环的条件为`i <= s.length()`。从长度2开始是因为，长度为1的子串都是回文子串，一共有`s.length()`个，最后加上即可。
    * 内循环`j`表示子串的开始索引，那么其结束索引为`j + i - 1`。调用函数`isPalindrome`进行判断。
* 最后返回`result + s.length()`，将长度为1的子串都是回文子串的计数也算进行。

运行结果：
* 执行用时 :79 ms, 在所有 Java 提交中击败了14.29%的用户
* 内存消耗 :34.3 MB, 在所有 Java 提交中击败了81.10%的用户

##### 2.动态规划

```java
public int countSubstrings2(String s) {
        char[] chars = s.toCharArray();
        int result = 0;
        boolean[][] flag = new boolean[chars.length][chars.length];
        for (int j = 0; j < chars.length; j++) {
            for (int i = j; i >= 0; i--) { // i j 的值设定保证了只使用了 flag[][]的上三角，没有出现重复
                if (chars[i] == chars[j] && (j - i < 2 || flag[i + 1][j - 1])) {
                    flag[i][j] = true;
                    result++;
                }
            }
        }
        return result;
    }
```

思路分析：

* 方法一中有很多重复的比较。本题问一共有多少个回文子串，而不是枚举全部。可能可以使用动态规划，再想一下回文子串的特点，如果子串`[i+1, j-1]`已经是回文子串，如果`chars[i] == chars[j]`，那么`[i, j]`也是回文子串。
* 如果定义`flag[i][j] == true`表示`[i, j]`为回文子串，按照刚才的描述，状态转移方程可以写为`flag[i][j] = true if flag[i + 1][j - 1] && chars[i] == chars[j]`。
* 边界条件或者说特殊子串的处理有以下三种情况：
    * 子串只有一个字符`i == j`，它一定是回文子串
    * 子串有两个字符，此时只需要`chars[i] == chars[j]`就可以断定这是个回文子串
    * 子串有三个字符，同样只需要`chars[i] == chars[j]`就可以断定这是个回文子串，最中间一个字符不影响
    * 所以转移方程也可以这样写`flag[i][j] = true if (chars[i] == chars[j] && (j - i < 2 || flag[i + 1][j - 1]))`
* 判断过程中，外循环为子串的右边界索引，内循环为子串的左边界索引。判断过程中找到一个回文子串要将计数+1还要修改状态。

运行结果：
* 执行用时 :11 ms, 在所有 Java 提交中击败了28.82%的用户
* 内存消耗 :45.6 MB, 在所有 Java 提交中击败了5.04%的用户

##### 3.中心扩展

```java
public int countSubstrings3(String s) {
        char[] chars = s.toCharArray();
        int result = 0;
        for (int i = 0; i < chars.length * 2 - 1; i++) { // 对每个可能的回文中心进行循环
            int left = i / 2; // 当中心是两个字母的间歇时i%2 = 1；当中心是字母时 left==right都落在该字母的位置
            int right = left + i % 2;
            while(left >= 0 && right < chars.length && chars[left] == chars[right]){
                left--;
                right++;
                result++;
            }
        }
        return result;
    }
```

思路分析：

* 终于来了，回文子字符串的标准处理办法。中心扩展法，类似的题目有[696. 计数二进制子串](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/String/countBinarySubstrings.md)
* 回文字符串关于中心对称，这个中心既可以是一个字符（比如子串的长度为奇数时），也可以是两个字符的中间（比如子串的长度为偶数时）。那么对于长度为n的字符串，其子串的中心一共有n+（n-1）个，n个是字母，n-1个是两个字母的间歇。
* 我们需要找到每一个可能的对称中心有能向外扩展出多少个回文子串。要想办法表示每一个回文中心，外扩的方式都一样。
    * 回文中心与子串的奇偶性有关，想必要分情况讨论。
    * 如果子串的长度为奇数，那么第一个子串只有一个字符，其左边界`left`与右边界`right`相等。
    * 如果子串的长度为偶数，那么第一个子串有两个字符，其左边界`left`与右边界`right`的关系为`right = left + 1`。
    * 所以可以通过奇偶性来控制初始时`left`与`right`的关系。
* 循环` for (int i = 0; i < chars.length * 2 - 1; i++)`，`i`表示每一个可能的回文中心，通过`i`的奇偶性来设置初始的`left, right`。内循环进行外扩，首先保证索引不超过数组边界，其次当前判断的两个字符相等。否则，当前`[left, right]`不是回文子串，向外扩的也不可能是。外扩的方式就是使`left--, right++`。
* 时间复杂度为$O(n + k)$，其中k为符合条件的子串的数目。空间复杂度为$O(1)$。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :34.3 MB, 在所有 Java 提交中击败了73.06%的用户