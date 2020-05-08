# 面试题05. 替换空格
### 原题
请实现一个函数，把字符串 s 中的每个空格替换成"%20"。

示例 1：
输入：s = "We are happy."
输出："We%20are%20happy."

限制：
0 <= s 的长度 <= 10000

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/ti-huan-kong-ge-lcof
著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。

### 解法
```java
class Solution {
    public String replaceSpace(String s) {
        StringBuilder res = new StringBuilder();
        for(char c : s.toCharArray()){
            if(c == ' ')
                res.append("%20");
            else 
                res.append(c);
        }
        return res.toString();
    }
}
```
思路分析：
* 对字符串的字符进行遍历，如果遇到空格字符，就替换为`"%20"`。这里使用`StringBuilder res`，避免字符串的拼接频繁生成新的对象。

运行结果：

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹