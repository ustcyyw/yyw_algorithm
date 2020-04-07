# 58. 最后一个单词的长度

### 原题
给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
如果不存在最后一个单词，请返回 0 。

说明：一个单词是指仅由字母组成、不包含任何空格字符的 最大子字符串。

示例:
输入: "Hello World"
输出: 5

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/length-of-last-word)：https://leetcode-cn.com/problems/length-of-last-word

### 解法

```java
public int lengthOfLastWord(String s) {
        char[] chars = s.toCharArray();
        if(chars.length == 0)
            return 0;
        int tail = chars.length - 1;
        for(;tail >= 0; tail--){ // 处理"a      "，先找到第一个非空的字符
            if(chars[tail] != ' ')
                break;
        }
        if(tail == -1) // "      "的情况
            return 0;
        int i = tail - 1;
        for(; i >= 0; i--){
            if(chars[i] == ' ')
                break;
        }
        return tail - i;
    }
```

思路分析：

* 以空格为分割符，要找到最后一个单词的长度。首先，如果所给的字符串长度为0，压根不存在字符，直接返回了。
* 最后一个单词，那么我们就从后往前去找第一个单词的结尾字符。注意，字符串可能是以一系列的空格结尾，比如`"a     "`。所以使用一个循环从字符串末尾寻找第一个非空格的字符，其索引用`tail`表示，当遇到第一个不非空格字符`chars[tail] != ' '`，跳出循环。
* 注意如果整个字符串均为空，那么循环结束时`tail == -1`。这种情况下也不存在单词，返回0。
* 最后一个单词的末尾索引找到后，从`int i = tail - 1`，向前找这个单词的第一个字符的前一个索引，当循环遇到空字符时就停止循环，此时`i + 1`为最后一个单词的第一个字符的索引。那么最后一个单词的长度即为`tail - i`。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :38.1 MB, 在所有 Java 提交中击败了5.43%的用户