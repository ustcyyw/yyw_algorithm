# 5388. 重新格式化字符串
### 原题
给你一个混合了数字和字母的字符串 s，其中的字母均为小写英文字母。
请你将该字符串重新格式化，使得任意两个相邻字符的类型都不同。也就是说，字母后面应该跟着数字，而数字后面应该跟着字母。
请你返回 重新格式化后 的字符串；如果无法按要求重新格式化，则返回一个 空字符串 。

示例 1：
输入：s = "a0b1c2"
输出："0a1b2c"
解释："0a1b2c" 中任意两个相邻字符的类型都不同。 "a0b1c2", "0a1b2c", "0c2a1b" 也是满足题目要求的答案。

示例 2：
输入：s = "leetcode"
输出：""
解释："leetcode" 中只有字母，所以无法满足重新格式化的条件。

示例 3：
输入：s = "1229857369"
输出：""
解释："1229857369" 中只有数字，所以无法满足重新格式化的条件。

示例 4：
输入：s = "covid2019"
输出："c2o0v1i9d"

示例 5：
输入：s = "ab123"
输出："1a2b3"

提示：
1 <= s.length <= 500
s 仅由小写英文字母和/或数字组成。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/reformat-the-string)：https://leetcode-cn.com/problems/reformat-the-string

### 解法
```java
public String reformat(String s) {
        List<Character> nums = new ArrayList<>();
        List<Character> letters = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            char temp = s.charAt(i);
            if (temp >= '0' && temp <= '9')
                nums.add(temp);
            else letters.add(temp);
        }
        int temp = letters.size() - nums.size();
        if (temp >= 2 || temp <= -2)
            return "";
        StringBuilder res = new StringBuilder();
        if (temp == 1) {
            res.append(letters.get(letters.size() - 1));
            return create(nums, letters, res, letters.size() - 2);
        } else if (temp == -1) {
            res.append(nums.get(nums.size() - 1));
            return create(letters, nums, res, nums.size() - 2);
        } else
            return create(letters, nums, res, nums.size() - 1);
    }

    private String create(List<Character> one, List<Character> two, StringBuilder sb, int hi) {
        for (int i = hi; i >= 0; i--) {
            sb.append(one.get(i));
            sb.append(two.get(i));
        }
        return sb.toString();
    }
```
思路分析：
* 要将只有小写字母和数字构成的字符串构造成数字与字母交替出项的形式
    * 先字母或者先数字均可。
    * 如果原字符串已经是数字字母间隔出现，需要重新构造一个字符串。
* 由于数字字母要间隔出现，所以一定要求字母个数与数字个数差的绝对值为小于等于1
    * 字母更多，就让新字符串以字母开头
    * 数字更多，就让新字符串以数字开头
    * 一样多，随你开心就好?
* 要知道字母个数，数字个数，而且需要将他们分开成两组，交替取出一个元素。为了避免构造出跟原字符串一样的字符串，从各组的最后的元素开始往前取。可以遍历判断每一个字符，然后放入相应的`List`中。
* 计算`int temp = letters.size() - nums.size();`：
    * 如果`temp >= 2 || temp <= -2`，数字字母个数差太多，总会有成双的，无法构造出满足题意的字符串，返回`""`
    * 如果`temp == 1`，字母多一个，新字符串以字母开头。`res.append(letters.get(letters.size() - 1));`。然后调用辅助函数并返回`return create(nums, letters, res, letters.size() - 2);`
    * 如果`temp == -1`，与上一种情况对称写。
    * 如果`temp == 1`，数字字母开头都可以，直接返回`create(letters, nums, res, nums.size() - 1);`
* 辅助函数`String create(List<Character> one, List<Character> two, StringBuilder sb, int hi)`。
    * 第一个参数表示在辅助函数中循环时先取的哪一个组别。因为在调用辅助函数前，先往`StringBuilder sb`中添加了字符or字母，所以在辅助函数内部需要区分先取哪一个组别。第二个参数表示在辅助函数中后取的哪一个组别。
    * 最后一个参数表示从List的哪一个索引开始取，这个参数之所以需要，是因为两个组别长度不一样，在外部有一个组别已经取了一个元素。
* 时间复杂度，生成两个分组与辅助函数都是线性遍历$O(1)$。空间复杂度就是字符串的长度。

运行结果：

* easy 8ms。

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](123)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹