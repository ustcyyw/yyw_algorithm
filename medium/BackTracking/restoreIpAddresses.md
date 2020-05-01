# 93. 复原IP地址
### 原题
给定一个只包含数字的字符串，复原它并返回所有可能的 IP 地址格式。

示例:

输入: "25525511135"
输出: ["255.255.11.135", "255.255.111.35"]

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/restore-ip-addresses)：https://leetcode-cn.com/problems/restore-ip-addresses

### 解法
```java
public class restoreIpAddresses {
    private char[] chars;
    private List<String> res;
    private int length;

    public List<String> restoreIpAddresses(String s) {
        res = new ArrayList<>();
        if (s.length() < 4) return res;
        chars = s.toCharArray();
        length = s.length();
        backTrack(new StringBuilder(), 0, -1, 3);
        return res;
    }
    /**
     * @param sb 存放当前已拼凑的字符
     * @param index 当前使用到第几个数字
     * @param num 当前的8位二进制数对应的十进制数字
     * @param dot 还剩余多少个点可以用
     */
    private void backTrack(StringBuilder sb, int index, int num, int dot) {
        if (num > 255) return; // 8位二进制数已经超过上限255的情况 肯定不符合条件
        if (index == length) {
            if (dot == 0)
                res.add(sb.toString());
            return;
        }
        // 当前位置要选用'.'的条件，首先之前选用了数字，其次剩余可用点至少还有一个，其次剩余数字不能超过 3 * dot
        if (num != -1 && dot >= 1 && (length - index) <= 3 * dot) {
            sb.append(".");
            backTrack(sb, index, -1, dot - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        // 当前位置选用数字，要保证后面还有可用的数字与点搭配。并且如果当前已经选择了0为第一个数字，接下来选数字都是不合法的。
        if (length - index >= dot && num != 0) {
            if (num == -1) // 在确定要选数字的时候，num == -1 说明之前没有选数字，那么将其置为0
                num = 0;
            sb.append(chars[index]);
            backTrack(sb, index + 1, num * 10 + (chars[index] - '0'), dot);
            sb.deleteCharAt(sb.length() - 1);
        }
    }
}
```
思路分析：
* ip地址:
  * 是32位的二进制数，为了方便分割成4个8位二进制数，用`.`隔开，也就是说ip地址中一点有3个`.`
  * 每一部分8位二进制数转化为10进制数方便用户查看，也就是说两个点之间的数字最大为255
  * 且ip地址可以出现0，但是不能出现01 000 001 010这样的情况
* 所以题目要求的就是满足上述条件的所有组合，涉及组合问题就想到使用回溯，关键是怎么定义递归函数。
* 定义递归函数`void backTrack(StringBuilder sb, int index, int num, int dot)`。
  * 第一个参数选用`StringBuilder`来存放构造的ip地址，避免大量字符串的拼接所以使用此类
  * 第二个参数表示当前使用原字符串`s`中索引为`index`的数字，为了方便这里使用成员变量`char[] chars`来存放`s`中的数字。配合成员变量`int length;`，表示总共有多少个数字，可以确定什么时候使用完所有数字，即`index == length`。
  * 第三个参数表示当前两个点之间的8位二进制数对应的十进制数字。
  * 第四个参数表示当前还剩余多少个`.`可以使用，因为ip地址中一定要出现3个点，所以需要这个变量来判断使用的点数。
* 接下来分析递归函数的内部：
  * 首先，按照ip地址的规定，两个点之间的十进制数小于255，所以当`num > 255`时，注定这个组合不合法，直接返回。
  * 然后如果`index == length`说明数字已经使用完了，如果`dot == 0`，点也使用完了，前面已经保证了两个点之间的十进制数小于255，后面还会看的有避免出现非法0开头的字符，此时就说明该组合式合法ip`res.add(sb.toString());`。如果剩余可用的`.`不为了，不是合法ip，直接返回。
  * 半成品字符串当前可选的要么是`.`要么是数字`chars[index]`两种可能。
  * 首先来看，当前可以选择使用`.`的条件是什么？`num != -1 && dot >= 1 && (length - index) <= 3 * dot`
    * 1.点的剩余次数大于0，`dot > 0`；
    * 2.剩余可用的数字个数`length - index`的数量要小于等于`3 * dot`（比如，后续还有4个数字，当时剩余的点只有一个，那么后面的四个数字组成的数显然大于255，就不合法。总的来说这是因为，两个点之间最多3个数字。）
    * 3.前面不能是一个点（否则出现了`..`这样的非法组合），这个可以使用变量`num`来标识，如果前面刚使用点，则`num == -1`。
    * 满足以上条件，可以尝试使用点：于是`sb.append(".");`，然后调用`backTrack(sb, index, -1, dot - 1);`，注意，由于此时没有使用数字，所以下一次依然该尝试索引为`index`的数字，并且第三个参数传入-1表示前面使用了`.`，以及点的剩余可用次数`dot - 1`。并且调用完之后需要回溯` sb.deleteCharAt(sb.length() - 1);`
  * 然后来看，选择使用数字`chars[index]`的条件？`length - index >= dot && num != 0`
    * 1.剩余可用的数字数量大于等于`.`的数量（否则，之后还剩2个数字，还有3个点的话，一定会出现`..`的非法组合）
    * 前面的数字不能是0，否则出现非法组合01 001等。
    * 使用数字时要多一个处理，如果`num == -1`，说明前面是一个`.`，将`num`置为0然后才可以计算当前的十进制数。调用` backTrack(sb, index + 1, num * 10 + (chars[index] - '0'), dot);`，第二个参数表示下一次尝试下一个索引对应的数字，第三个参数就是要去计算当前的十进制数字为多少；最后还要回溯。
* 主函数需要做的事情就是初始化成员变量的值，然后调用回溯函数，返回结果。当然如果`if (s.length() < 4) return res;`，数字的数量小于4个，显然无法满足ip地址要求，直接返回空List。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :38.6 MB, 在所有 Java 提交中击败了7.85%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹