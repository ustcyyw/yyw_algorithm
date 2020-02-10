# 有效的括号

[toc]

### 原题

给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。

有效字符串需满足：

* 左括号必须用相同类型的右括号闭合。
* 左括号必须以正确的顺序闭合。
* 注意空字符串可被认为是有效字符串。

示例 1:

输入: "()"
输出: true
示例 2:

输入: "()[]{}"
输出: true
示例 3:

输入: "(]"
输出: false
示例 4:

输入: "([)]"
输出: false
示例 5:

输入: "{[]}"
输出: true

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/valid-parentheses)：https://leetcode-cn.com/problems/valid-parentheses

---

### 两种解法

##### 1.使用栈存放左括号（我的解法）

```java
 public boolean isValid(String s) {
        if (s.length() % 2 == 1) // 奇数个字符的字符串 显然无法完成括号匹配
            return false;
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char theChar = s.charAt(i);
            if (theChar == '(' || theChar == '{' || theChar == '[')
                stack.push(theChar);
            else {
                if (stack.empty()) // 栈中已无左括号，此时字符为右括号，无法匹配。
                    return false;
                char preChar = stack.peek();
                if ((preChar == '{' && theChar == '}') || (preChar == '(' && theChar == ')') || (preChar == '[' && theChar == ']'))
                    stack.pop();
                else return false;
            }
        }
        return stack.empty();
    }
```

思路分析：

* 字符串长度为奇数显然无法完成匹配。在字符串长度为偶数的情况下讨论。
* 括号匹配一个左括号一个右括号，难处理的是括号的嵌套，因为这需要跳过中间的部分去寻找匹配的右括号。处理这个问题？ :sob:我也不知道怎么想到用栈来处理，我能想起来是因为之前看《算法第四版》时见过类似的于是就直接拿来用了。（扩展里提一下）
* 遇到左括号就将其压入栈，遇到右括号就与栈顶元素对比，括号不匹配或者栈中已经不存在左括号`stack.empty()`为`true`，说明匹配失败。如果括号匹配说明，这一组匹配成功，它可能是嵌套在其它匹配括号中的，所以此时要将当前栈顶的左括号弹出。
* 如果最后最终，栈中没有剩余元素，也就是没有剩下左括号，说明刚好完成匹配，括号字符串有效。否则匹配失败，括号字符串无效。
* 遍历一次字符串，每个元素最多一次入栈一次出栈，出栈入栈的操作是$O(1)$的，所以时间复杂度为$O(n)$。最坏的情况下字符串中都是左括号，则栈中有`n`个元素，所以空间复杂度为$O(n)$。

代码分析：

* 7~8行，遇到左括号则入栈。
* 9~16行，右括号的处理。10.11行`if (stack.empty()) return false;`栈中不存在左括号的情况。13.14行当前括号匹配的情况。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了93.57%的用户
* 内存消耗 :34.0 MB, 在所有 Java 提交中击败了92.78%的用户
##### 2.类似栈的做法但将封装省去（官方标答）

```java
public boolean isValid2(String s) {
        char[] charArray = new char[s.length() + 1];

        int p = 1;

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                charArray[p++] = c;
            } else {
                p--;
                if (c == ')' && charArray[p] != '(') {
                    return false;
                }
                if (c == '}' && charArray[p] != '{') {
                    return false;
                }
                if (c == ']' && charArray[p] != '[') {
                    return false;
                }
            }
        }
        return p == 1; // 如果左括号还有剩余 括号没有一一对应，属于无效情况
    }
```

思路分析：

* 标答其实也是跟方法一中思路一样，最大的改变就是去掉了栈的封装。直接用栈中的数组来完成这个功能。
* 实现栈的一个方法就是使用数组，在将元素放入栈顶时就是将元素存放在指针所在的位置，然后指针右移一位`charArray[p++] = c`。弹出元素直接使`p--`，这样就使得栈顶元素为` charArray[p-1]`，当次弹出的元素可以用`charArray[p]`访问。
* 其余括号匹配的规则一致。左括号入栈，遇到右括号就弹出左括号看是否能匹配。最后当栈中无元素，也就是`p == 1`时，匹配成功。

代码分析：无

---

### 扩展

关于在《算法》第四版提到的算法。用栈处理括号匹配还可以去解决字符串表示的式子的运算。一个栈存放数字，一个栈存放运算符。遇到右括号说明需要完成一次计算，弹出一个运算符，再根据运算符是单目还是双目弹出一个或者两个数字，计算结果再放入数字栈中准备下一次的运算。