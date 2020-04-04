# 1160. 拼写单词

### 原题
给你一份『词汇表』（字符串数组） words 和一张『字母表』（字符串） chars。
假如你可以用 chars 中的『字母』（字符）拼写出 words 中的某个『单词』（字符串），那么我们就认为你掌握了这个单词。
注意：每次拼写（指拼写词汇表中的一个单词）时，chars 中的每个字母都只能用一次。
返回词汇表 words 中你掌握的所有单词的 长度之和。 

示例 1：
输入：`words = ["cat","bt","hat","tree"], chars = "atach"`
输出：6
解释： 
可以形成字符串 "cat" 和 "hat"，所以答案是 3 + 3 = 6。

示例 2：
输入：`words = ["hello","world","leetcode"], chars = "welldonehoneyr"`
输出：10
解释：
可以形成字符串 "hello" 和 "world"，所以答案是 5 + 5 = 10。

提示：

```
1 <= words.length <= 1000
1 <= words[i].length, chars.length <= 100
```

所有字符串中都仅包含小写英文字母

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/find-words-that-can-be-formed-by-characters)：https://leetcode-cn.com/problems/find-words-that-can-be-formed-by-characters

### 解法

```java
public int countCharacters(String[] words, String chars) {
        int res = 0;
        int[] countChars = getCount(chars);
        for(int i = 0; i < words.length; i++){
            int n = words[i].length();
            if(n > chars.length())
                continue;
            if(isValid(words[i],n , getCount(words[i]), countChars))
                res += n;
        }
        return res;
    }

    private int[] getCount(String s){
        int[] count = new int[26];
        for(int i = 0; i < s.length(); i++)
            count[s.charAt(i) - 'a'] += 1;
        return count;
    }

    private boolean isValid(String wordStr, int n, int[] word, int[] chars){
        for(int i = 0; i < n; i++){
            int index = wordStr.charAt(i) - 'a';
            if(word[index] > chars[index])
                return false;
        }
        return true;
    }
```

思路分析：

* 对于每一个词汇，词汇中的每一个字母都要能在`chars`中找到，并且`chars`中的字母不能重用。所以不单要知道每个词汇中出现了哪些字母，还需要知道每个字母出项了多少次。对于`chars`也一样。
* 所以写一个辅助函数`getCount(String s)`来统计字符串中各个字母出现了多少次。
    * 一般来说统计出现次数使用HashMap，但是字符串中只出现小写字母，可以使用`int[]`来代替哈希表。以`s.charAt(i) - 'a'`为键，键与字母一一对应，以该字母出现的次数为值。
    * 所以遍历字符串中的每个字符，然后`count[s.charAt(i) - 'a'] += 1;`，完成对字符串中字母出现次数的统计
* 判断一个词汇是否能由`chars`中的字母构成的辅助函数为`boolean isValid(String wordStr, int n, int[] word, int[] chars)`
    * 第一个参数是待判断的词汇，第二个参数是该词汇的长度，第三个参数是该词汇的字符统计结果数组，第四个参数是字母表字符统计结果数组。
    * 对于`wordStr`中的每个字母，`int index = wordStr.charAt(i) - 'a'`得到该字母对应的键，通过键去查看出现次数。如果`word[index] > chars[index]`，该字母在字母表中不够，直接返回`false`。如果循环完都没有出现这样的情况，说明该词汇可以拼写出来。
* 主函数中第三行先得到字母表字符统计结果数组，因为一直要用。然后对于每个词汇，如果词汇的长度大于字母表长度，那肯定不行，就跳过当此循环；否则调用`isValid`，满足题意就在结果中加上`n = words[i].length();`

运行结果：
* 执行用时 :5 ms, 在所有 Java 提交中击败了93.25%的用户
* 内存消耗 :42.2 MB, 在所有 Java 提交中击败了5.08%的用户