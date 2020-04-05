# 242. 有效的字母异位词

### 原题
给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。
异位词的定义：构成字符及每个字符出现的次数都相等。

示例 1:
输入: s = "anagram", t = "nagaram"
输出: true
示例 2:
输入: s = "rat", t = "car"
输出: false

说明:
你可以假设字符串只包含小写字母。

进阶:
如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/valid-anagram)：https://leetcode-cn.com/problems/valid-anagram

### 两种解法

##### 1.HashMap进行计数

```java
public boolean isAnagram(String s, String t) {
        if (s.length() != t.length())
            return false;
        Map<Character, Integer> charMap1 = new HashMap<>((int) (s.length() / 0.75F + 1.0F));
        Map<Character, Integer> charMap2 = new HashMap<>((int) (s.length() / 0.75F + 1.0F));
        for (char c : s.toCharArray())
            charMap1.put(c, charMap1.getOrDefault(c, 0) + 1);
        for (char c : t.toCharArray())
            charMap2.put(c, charMap2.getOrDefault(c, 0) + 1);
        if (charMap1.size() != charMap2.size())
            return false;
        for (char c : s.toCharArray()) {
            if (!charMap1.get(c).equals(charMap2.getOrDefault(c, 0))) {
                return false;
            }
        }
        return true;
    }
```

思路分析：

* 异位词的定义非常清楚，出现的字符相同且每个出现字符的出现次数相同。所以需要对两个字符串中的出现字符及出现次数进行统计。
* 在进行统计之前，可以先简单判断如果给定的两个字符串不一样长，那么肯定无法满足异位词定义，直接返回`false`。
* 使用HashMap进行计数，就是常规操作了。以字母为键，以该字母出现次数为值，对字符串进行单次遍历。遍历过程中对每个字母如此处理：`charMap.put(c, charMap.getOrDefault(c, 0) + 1);`
* 遍历完成后，首先判断两个Map中键的数量是否相等，如果不相等说明两个字符串中出现的字符数目不一样，那么肯定无法满足异位词定义，直接返回`false`。
* 对于`s`中出现的每个字符`for(char c : s.toCharArray())`，判断这个字符在`s`的出现次数`charMap1.get(c)`是否等于`t`中出现的次数(没有出现则出现次数为0`charMap2.getOrDefault(c, 0)`)。
    * 否则返回`false`
* 循环结束说明满足异位词定义，返回`true`。
* 时间复杂度为$O(m + n)$。空间复杂度为$O(m + n)$。

运行结果：
* 执行用时 :28 ms, 在所有 Java 提交中击败了16.24%的用户
* 内存消耗 :45.6 MB, 在所有 Java 提交中击败了5.08%的用户
##### 2.数组代替HashMap

```java
public boolean isAnagram2(String s, String t) {
        int[] count1 = new int[26];
        for(char c : s.toCharArray())
            count1[c - 97] += 1;
        for(char c : t.toCharArray())
            count1[c - 97] -= 1;
        for(int i = 0; i < 26; i++){
            if(count1[i] != 0)
                return false;
        }
        return true;
    }
```

思路分析：

* 注意到题目中说，字符只能是小写字母(当然只有unicode 字符也一样)，那么我们可以使用`int[]`来代替散列表的功能，以`c - 'a'`为数组索引，值依然为该字母出现的次数。
* 对于字符串`s`，每一个字母都将其对应的数组元素值+1。而对于字符串`t`，每一个字母都将其对应的数组元素值-1。那么遍历完两个字符串后，字符串中的元素必须都为0，这两个字符串才满足异位词的定义。否则说明有字符出现次数不一致。
* 时间复杂度为$O(m + n)$。空间复杂度为$O(1)$（数组长度是常数）。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了99.86%的用户
* 内存消耗 :43.4 MB, 在所有 Java 提交中击败了5.08%的用户
