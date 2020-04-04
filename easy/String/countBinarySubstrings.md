# 696. 计数二进制子串

### 原题
给定一个字符串 s，计算具有相同数量0和1的非空(连续)子字符串的数量，并且这些子字符串中的所有0和所有1都是组合在一起的。
重复出现的子串要计算它们出现的次数。

示例 1 :
输入: "00110011"
输出: 6
解释: 有6个子串具有相同数量的连续1和0：“0011”，“01”，“1100”，“10”，“0011” 和 “01”。
请注意，一些重复出现的子串要计算它们出现的次数。
另外，“00110011”不是有效的子串，因为所有的0（和1）没有组合在一起。

示例 2 :
输入: "10101"
输出: 4
解释: 有4个子串：“10”，“01”，“10”，“01”，它们具有相同数量的连续1和0。

注意：
`s.length` 在1到50,000之间。
s 只包含“0”或“1”字符。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/count-binary-substrings)：https://leetcode-cn.com/problems/count-binary-substrings

### 两种解法

##### 1.中心扩展（类似回文子串的判断）

```java
public int countBinarySubstrings(String s) {
        int result = 0;
        char[] chars = s.toCharArray();
        for(int i = 1; i < s.length(); i++){
            int left = i - 1, right = i;
            char leftChar = chars[left], rightChar = chars[right];
            if(leftChar == rightChar)
                continue;
            while(left >= 0 && right < s.length() && chars[left] == leftChar && chars[right] == rightChar){
                left--;
                right++;
                result++;
            }
        }
        return result;
    }
```

思路分析：

* 看完题目给的示例，如果将0与1看成对应，那么符合题目要求的子串就关于其中心（中间两个元素的中间）成轴对称。就如同回文字符串关于中心轴对称。不过有一点不同，就是在本问题中对称中心只能是两个字符的中间，而回文字符串的中心可以是某一个字母
* 所以这个题也可以类似于统计回文字符串的数目那样，用中心扩展的方法。本问题中对称中心只能是两个字符的中间，可以用整形序列`[1, s.length() - 1]`来表示回文中心的位置，那么`left = i - 1, right = i `就是最中心的两个字符。
    * 比如`i = 1`时`left = 0, right = 1 `，就是第一个可能的满足题目要求的子串。由原字符串中第一，第二个字符构成。
    * 所以遍历每一个回文中心的循环为`for(int i = 1; i < s.length(); i++)`
* 在循环中首先要保证，回文中心左右的两个字符`char leftChar = chars[left], rightChar = chars[right];`一个是0，另外一个是1。
    * 如果都是1或者都是0，那么没有以这个为中心的符合要求的字串，后续就不用从中心向外扩展了`if(leftChar == rightChar) continue;`。
    * 如果`leftChar != rightChar`，就以此为中心，。使用一个while循环来计数这个中心有多少个符合题意的子串。循环条件首先保证索引不越界，其次`leftChar`左边的字符都要和它相等，`rightChar`右边的字符都要和它相等。不断向外扩展就是`left--; right++`
* 时间复杂度为$O(n + k)$，其中k为符合条件的子串的数目。空间复杂度为$O(1)$。

运行结果：
* 执行用时 :11 ms, 在所有 Java 提交中击败了88.22%的用户
* 内存消耗 :38 MB, 在所有 Java 提交中击败了61.89%的用户

##### 2.分组计数

```java
public int countBinarySubstrings2(String s) {
        int result = 0;
        char[] chars = s.toCharArray();
        int preCount = 0, curCount = 1;
        for(int i = 1; i < chars.length; i++){
            if(chars[i - 1] == chars[i]) // 记录当前组的数量
                curCount++;
            else {
                result += Math.min(preCount, curCount); // 将连续的0,1分组后，相邻两组能满足题意的子串数量即为 Math.min(preCount, curCount)
                preCount = curCount;
                curCount = 1;
            }
        }
        return result + Math.min(preCount, curCount); // 循环结束后还有最后一组要和前一组进行计数
    }
```

思路分析：

* 给定的原字符串`s`只由0,1构成，可以将其看成多组连续的0与多组连续的1组成的。而且0与1的组是交替出现的（不交替那不就归为一组完事了）。
* 假设有4个1与3个0相连，”1111000“ 可以构成的子串有111000，1100，10，共3个；假设有3个0与2个1相连”00011“，可以构成的子串有0011，01，共两个。可以发现我们只要知道相连的两个分组的长度，就可以直接得到这两个组可以构成几个符合题意的子串，就是两分组的中较短的长度。
* 所以只需要通过一次遍历，记录相连两分组的长度即可。`preCount`表示前一个分组的长度，`curCount`表示当前分组的长度。
    * 在遍历过程中，如果当前元素与上一个元素相等`chars[i - 1] == chars[i]`（注意这里索引，所以循环从`i=1`开始），当前分组的长度+1。
    * 反之，当前分组结束，可以更新`result += Math.min(preCount, curCount);`。并且重置`preCount = curCount; curCount = 1`。这里为什么`curCount`重置为1呢？因为当前元素已经是下一个分组的第一个元素了，下一次循环是这个分组的第二个元素，然后才会让`curCount++`为2。
* 循环结束，最后一个分组在循环中没有更新`resutlt`，将其补上，然后返回。
* 时间复杂度为$O(n)$。空间复杂度为$O(1)$。

运行结果：
* 执行用时 :18 ms, 在所有 Java 提交中击败了47.50%的用户
* 内存消耗 :38 MB, 在所有 Java 提交中击败了61.89%的用户