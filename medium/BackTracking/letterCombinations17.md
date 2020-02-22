# 17.电话号码的组合

### 原题

给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。

给出数字到字母的映射如下（与电话按键相同）**即数字键盘九键**。注意 1 不对应任何字母。

示例:

输入：`"23"`
输出：`["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].`
说明:
尽管上面的答案是按字典序排列的，但是你可以任意选择答案输出的顺序。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number)：https://leetcode-cn.com/problems/letter-combinations-of-a-phone-number

------

### 一种解法

##### 1.递归回溯

```java
private static String[] map = {
            "",
            "",
            "abc",
            "def",
            "ghi",
            "jkl",
            "mno",
            "pqrs",
            "tuv",
            "wxyz"
    };
    private List<String> combinations = null;
    public List<String> letterCombinations(String digits) {
        if(digits == null || digits.equals(""))
            return new ArrayList<String>();

        combinations = new ArrayList<>();
        combinateAt("", digits, 0);
        return combinations;
    }

    private void combinateAt(String pre, String digits, int curIndex){
        if(curIndex == digits.length()){
            combinations.add(pre);
            return;
        }
        int curDigit = digits.charAt(curIndex) - '0';
        for(char c : map[curDigit].toCharArray()){
            combinateAt(pre + c, digits, curIndex + 1);
        }
    }
```

思路分析：

* 当确定了第一个数字选取对应的某个字母后，要得到最终的字母字符串，就只需要再确定第2到第n个字母对应的字母；再选定第二个字母之后，要得到最终的字母字符串，就只需要再确定第3到第n个字母......以此类推，直到选定第n个字母后，就得到了一种结果。
* 如果用`String pre`表示前`curIndex -1 `已经被选定的字母字符串，那么再将第`curIndex`选定一个字母与`pre`拼接，我们就完成了前`curIndex`字母的选定的一种可能，接下来要去选`curIndex + 1`个数字对应的字母。这就是递归的关系。
* 递归结束的条件，换句话说，就是没有下一位可以选的字母。即`curIndex == digits.length()`
* 在某个`curIndex`递归时，每个可能的字母都应该考虑，所以会有单层循环。
* 在某一位上都进行了循环，所以时间复杂度为$O(3^n*4^m)$，其中m为数字7，9出现的次数，n为其余数字出现的次数。由于结果也有$3^n*4^m$种，所以空间复杂度为$O(3^n*4^m)$。

代码解释：

* `String[] map`，类变量，索引即为数字0~9，值即为数字键盘上对应的字母。这是固定的，所以写为类变量。
* `List<String> combinations`成员变量，方便将递归中最后结果放入期中。
* 15~16行，很坑的特殊情况 😥 单独处理。
* 28行，将字符转化成数字0~9。
* 29~30行，将该位数字对应的可能的字母均进行考虑，所以是一个for-each循环，确定当前字母后`pre + c`就行进行下一位选取字母时，已经固定的。进行下一位字母的选取，所以`curIndex + 1`。

运行结果：
* 执行用时 :9 ms, 在所有 Java 提交中击败了5.09%的用户
* 内存消耗 :38.2 MB, 在所有 Java 提交中击败了5.22%的用户

----

### 反思

* 这个问题本身很直观，如果给定的数字字符串的长度是固定的n，就可以直接写固定的n层循环来达到便利所有可能出现的结果。
* 但是并没有固定，所以虽然是枚举完所有结果，但是却不能直接采用多层循环。这个时候就只能采用递归（回溯）的方式，关键就在于找递归的关系。
* 为什么我的运行结果很慢？主要是因为大量的字符串的拼接 每次都需要创建新的字符串对象。如果改用`StringBuilder`对象 或者直接使用char[]转化为字符串会快非常多。