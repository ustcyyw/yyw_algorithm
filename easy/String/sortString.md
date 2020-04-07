# 1370. 上升下降字符串

### 原题
给你一个字符串 s ，请你根据下面的算法重新构造字符串：

1. 从 s 中选出 最小 的字符，将它 接在 结果字符串的后面。

2. 从 s 剩余字符中选出 最小 的字符，且该字符比上一个添加的字符大，将它 接在 结果字符串后面。

3. 重复步骤 2 ，直到你没法从 s 中选择字符。

4. 从 s 中选出 最大 的字符，将它 接在 结果字符串的后面。

5. 从 s 剩余字符中选出 最大 的字符，且该字符比上一个添加的字符小，将它 接在 结果字符串后面。

6. 重复步骤 5 ，直到你没法从 s 中选择字符。

7. 重复步骤 1 到 6 ，直到 s 中所有字符都已经被选过。

在任何一步中，如果最小或者最大字符不止一个 ，你可以选择其中任意一个，并将其添加到结果字符串。
请你返回将 s 中字符重新排序后的 结果字符串 。 

示例 1：
输入：s = "aaaabbbbcccc"
输出："abccbaabccba"
解释：第一轮的步骤 1，2，3 后，结果字符串为 result = "abc"
第一轮的步骤 4，5，6 后，结果字符串为 result = "abccba"
第一轮结束，现在 s = "aabbcc" ，我们再次回到步骤 1
第二轮的步骤 1，2，3 后，结果字符串为 result = "abccbaabc"
第二轮的步骤 4，5，6 后，结果字符串为 result = "abccbaabccba"

示例 2：
输入：s = "rat"
输出："art"
解释：单词 "rat" 在上述算法重排序以后变成 "art"

示例 3：
输入：s = "leetcode"
输出："cdelotee"

示例 4：
输入：s = "ggggggg"
输出："ggggggg"

示例 5：
输入：s = "spo"
输出："ops"

提示：

`1 <= s.length <= 500`
s 只包含小写英文字母。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/increasing-decreasing-string)：https://leetcode-cn.com/problems/increasing-decreasing-string

### 解法

```java
public String sortString(String s) {
        int[] count = new int[26];
        for(char c : s.toCharArray())
            count[c - 'a'] += 1;
        StringBuilder res = new StringBuilder();
        while(res.length() != s.length()){
            for(int i = 0; i < 26; i++){
                if(count[i] == 0)
                    continue;
                res.append((char)(i + 'a'));
                count[i] -= 1;
            }
            for(int i = 25; i >= 0; i--){
                if(count[i] == 0)
                    continue;
                res.append((char)(i + 'a'));
                count[i] -= 1;
            }
        }
        return res.toString();
    }
```

思路分析：

* 从构造步骤的描述看：
    * 需要不断在字符串后面拼接字符，所以使用`StringBuilder res = new StringBuilder();`来存放答案
    * 先按从小到大的顺序选取一轮字母，再从大到小选取一轮字母。字母被选过就不可以重用，所以涉及到字母的计数，选取了该字母，就将该字母的剩余可用次数-1。
    * 涉及到多轮选取，所以会有外循环。最终所有字母都需要用上，所以外循环的循环条件为`res.length() != s.length()`。（所有字母用完时，`res`的长度即为原字符串的长度）
* 对于有限个字母的计数，经典方法：可以用`int[]`代替`HashMap`，（本题只会出现小写字母）以字符减去`a`为键，出现次数为值。
* 当统计完成每个字母的出现次数后，`count[i]`就表示字母`(char)(i + 'a')`的剩余可用次数
* 先按从小到大的顺序选取一轮字母`for(int i = 0; i < 26; i++)`，如果`count[i] == 0`，说明该键对应的字母已经用完了`continue`，否则就将该字母`(char)(i + 'a')`添加到`res`的末尾，并且将其可用次数减一。再按从大到小顺序选取一轮字母`for(int i = 25; i >= 0; i--)`，处理方式一致。
* 外循环结束后，返回`res.toString()`即可。
* 时间复杂度，由于只是将原字符串的每个字符重排了一次，内循环的数组访问次数是常数26次，所以时间复杂度为$O(n)$。空间复杂度为$O(n)$。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了97.2%的用户
* 内存消耗 :39.5 MB, 在所有 Java 提交中击败了100.00%的用户