# 面试题50. 第一个只出现一次的字符
### 原题
在字符串 s 中找出第一个只出现一次的字符。如果没有，返回一个单空格。

示例:

s = "abaccdeff"
返回 "b"

s = "" 
返回 " "


限制：
0 <= s 的长度 <= 50000

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof)：https://leetcode-cn.com/problems/di-yi-ge-zhi-chu-xian-yi-ci-de-zi-fu-lcof

### 解法:使用数组代替哈希表计数
```java
public class firstUniqChar {
    public char firstUniqChar(String s) {
        int[] count = new int[256];
        char[] chars = s.toCharArray();
        for(char c : chars)
            count[c]++;
        for(char c : chars){
            if(count[c] == 1)
                return c;
        }
        return ' ';
    }
}
```
思路分析：
* 要求只出现一次的第一个字符，就需要去计数每一个字符出现了几次。一般都是用哈希表，但是字符往多了数按照扩展的ASCII码也就256个，就可以使用`int[] count`来代替哈希表：键就是字符对应的数，值就是该字符出现的次数。
* 第一次遍历计数每个字符出现的次数`for(char c : chars) count[c]++;`。第二次遍历按顺序去查看该字符出现了几次，如果该字符出现了1次，它就是第一个仅出现一次的字符，直接返回。
* 如果第二次遍历没有返回，就说明没有仅出现一次的字符，返回`' '`。
* 时间复杂度为$O(n)$，空间复杂度为$O(n)$（因为使用了辅助的字符数组）。

运行结果：

* 执行用时 :4 ms, 在所有 Java 提交中击败了99.92%的用户
* 内存消耗 :39.7 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹