# 面试题58 - II. 左旋转字符串
### 原题
字符串的左旋转操作是把字符串前面的若干个字符转移到字符串的尾部。请定义一个函数实现字符串左旋转操作的功能。比如，输入字符串`"abcdefg"`和数字2，该函数将返回左旋转两位得到的结果`"cdefgab"`。

示例 1：
输入: s =` "abcdefg"`, k = 2
输出: `"cdefgab"`

示例 2：
输入: s =` "lrloseumgh"`, k = 6
输出: `"umghlrlose"`

限制：
`1 <= k < s.length <= 10000`

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof)：https://leetcode-cn.com/problems/zuo-xuan-zhuan-zi-fu-chuan-lcof

### 解法
```java
public class reverseLeftWords {
    public String reverseLeftWords(String s, int n) {
        return s.substring(n + 1) + s.substring(0, n + 1);
    }
}
```
思路分析：
* 观察示例，发现就是通过n将前n个字符从头截取之后放到原字符串末尾。
* 利用java中String的`substring()`就可以解决。不使用这个API，无法就是分两段循环，先将后一部分逐个字符放到`StringBuilder`实例中，再将前一部分逐个字符放入即可。

运行结果：

* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.7 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹