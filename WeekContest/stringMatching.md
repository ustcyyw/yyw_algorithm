# 5380. 数组中的字符串匹配

### 184周周赛第一题 easy
给你一个字符串数组 words ，数组中的每个字符串都可以看作是一个单词。请你按 任意 顺序返回 words 中是其他单词的子字符串的所有单词。
如果你可以删除 words[j] 最左侧和/或最右侧的若干字符得到 word[i] ，那么字符串 words[i] 就是 words[j] 的一个子字符串。 

示例 1：
输入：words = ["mass","as","hero","superhero"]
输出：["as","hero"]
解释："as" 是 "mass" 的子字符串，"hero" 是 "superhero" 的子字符串。
["hero","as"] 也是有效的答案。

示例 2：
输入：words = ["leetcode","et","code"]
输出：["et","code"]
解释："et" 和 "code" 都是 "leetcode" 的子字符串。

示例 3：
输入：words = ["blue","green","bu"]
输出：[]

提示：
`1 <= words.length <= 100`
`1 <= words[i].length <= 30`
`words[i] `仅包含小写英文字母。
题目数据 保证 每个 `words[i] `都是独一无二的。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/string-matching-in-an-array

### 解法：直接按题目模拟

```java
public List<String> stringMatching(String[] words) {
        List<String> res = new ArrayList<>();
        Arrays.sort(words, (String x, String y) -> x.length() - y.length());
        for(int i = 0; i < words.length; i++){
            for(int j = i + 1; j < words.length; j++){
                if(words[j].contains(words[i])){
                    res.add(words[i]);
                    break;
                }
            }
        }
        return res;
    }
```

思路分析：

* 数据量很小，可以直接按题目思路模拟。题目中的要求就是，某一个字符串要是字符串数组中别的字符串的子字符串。
* 首先，长的字符串不可能是短的子字符串，所以可以将字符串数组按照长度进行升序排序`Arrays.sort(words, (String x, String y) -> x.length() - y.length())`（利用lambda表示），这样在找`word[i]`是否是别的字符串的子串时，从`j = i + 1`开始遍历即可。
* 判断包含与否，直接调用`string.contains()`方法即可，如果已经确定`word[i]`为某个字符串的子串，就打断内循环。😡自己写了判断子串的方法，结果还出了一次错，太🐎了。
* 时间复杂度为$O(n^2)$，空间复杂度与满足题意的字符串个数成正比

运行结果：

* 4ms