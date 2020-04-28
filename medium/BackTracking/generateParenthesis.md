# 22. 括号生成
### 原题
数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。

示例：
输入：n = 3
输出：

```
	[
       "((()))",
       "(()())",
       "(())()",
       "()(())",
       "()()()"
     ]
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/generate-parentheses)：https://leetcode-cn.com/problems/generate-parentheses

### 解法
```java
public class generateParenthesis {
    private List<String> res;
    public List<String> generateParenthesis(int n) {
        res = new ArrayList<>();
        backTrack(n, n, new StringBuilder());
        return res;
    }

    /**
     * 左括号如果还剩余 1个 右括号也剩余1个，那么必须先使用左括号。左括号还剩1个，右括号还剩3个，那么都可以使用
     * @param left 剩余可用的左括号
     * @param right 剩余可用的右括号
     */
    private void backTrack(int left, int right, StringBuilder sb){
        if(right == 0){
            res.add(sb.toString());
            return;
        }
        if(left == right){
            sb.append('(');
            backTrack(left - 1, right, sb);
            sb.deleteCharAt(sb.length() - 1);
        } else {
            if(left > 0){ // 这里要注意 如果没有剩余的左括号 就不能递归 否则无限递归了
                sb.append('(');
                backTrack(left - 1, right, sb);
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(')'); // 为什么右括号不需要检查剩余 因为递归调用一开始的条件，当右括号无剩余时已经找到需要的组合了，就返回了
            backTrack(left, right - 1, sb);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
```
思路分析：
* 这个问题是一个组合问题，所以自然想到使用回溯算法。有效括号，就是从左往右数括号的过程中左括号的数量一定大于等于右括号的数量，并且最终左右括号的数量相等。
* 根据有效括号的特点，显然我们在搭建组合的时候就需要使用两个可以表示当前已使用的左右括号的数量。这里我选择`left, right`分表表示剩余的可用的左右括号的数量。
    * 如此一来使用`n - left`就是已使用的左括号数量，可行
* 然后定义回溯函数`void backTrack(int left, int right, StringBuilder sb)`：
    * 第三个参数使用`StringBuilder`为了避免频繁的拼接字符串，且方便回溯时删除字符。
    * 根据有效括号的定义，最后使用的一定是右括号，所以当`right == 0`时（注意`left`一定是小于等于`right`的）表示已经选完所有括号，` res.add(sb.toString());`，然后返回。
    * 当`left == right`时，已使用的左括号数量等于已使用的右括号数量，这时下一个括号就只能选择左括号。`sb.append('(');`并且由于当前使用了一个左括号，所以递归调用`backTrack(left - 1, right, sb);`。完成调用之后回溯就是`sb.deleteCharAt(sb.length() - 1);`，删掉当前选的括号。
    * 否则：如果左括号还有剩余，当前可以使用左括号。或者当前可以使用右括号。类似于上一条的递归与回溯
        * 这里为什么不需要判断`right > 0`但是需要判断`left > 0`.因为`right == 0`会在函数的一开头就返回，不可能出现`right !> 0`的情况。
* 主函数生成结果List(成员变量简化回溯函数传参)`res = new ArrayList<>();`然后调用回溯函数，再返回结果即可。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了99.02%的用户
* 内存消耗 :39.2 MB, 在所有 Java 提交中击败了5.04%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹