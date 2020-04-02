# 面试题 01.06. 字符串压缩

### 原题
字符串压缩。利用字符重复出现的次数，编写一种方法，实现基本的字符串压缩功能。比如，字符串`aabcccccaaa`会变为`a2b1c5a3`。若“压缩”后的字符串没有变短，则返回原先的字符串。你可以假设字符串中只包含大小写英文字母（a至z）。

示例1:
 输入：`"aabcccccaaa"`
 输出：`"a2b1c5a3"`

示例2:
 输入：`"abbccd"`
 输出：`"abbccd"`
 解释：`"abbccd"`压缩后为`"a1b2c2d1"`，比原字符串长度更长。

提示：
字符串长度在[0, 50000]范围内。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/compress-string-lcci)：https://leetcode-cn.com/problems/compress-string-lcci

### 解法：双指针

```java
public String compressString(String S) {
        int n = S.length();
        if(n <= 1)
            return S;
        char[] chars = S.toCharArray();
        StringBuilder res = new StringBuilder();
        int i = 0, j = 1;
        for(; j < n; j++){
            if(chars[j] != chars[i]){
                res.append(chars[i]);
                res.append(j - i);
                i = j;
            }
        }
        res.append(chars[i]);
        res.append(j - i);
        return res.length() < n ? res.toString() : S;
    }
```

思路分析：

* 题目中所给的输入字符串，相等的字母都是连着的。根据压缩规则，需要知道每一个字母重复出现了几次，只需要用一个指针`i`指向当前字母的第一次出现的索引，另外一个指针`j`指向下一个字母第一次出现的索引。那么`j - i`就是当前字母出现的次数。
* 最终的结果为一个字符串，为了避免频繁的拼接，使用`StringBuilder res = new StringBuilder();`来进行字符串的压缩。
* 初始状态`i`指向数组的第一个元素，`j`从第二个元素开始。
    * 判断`chars[j]`与`chars[i]`是否相等，如果相等`j`还没有指向下一个字母第一次出现的索引，于是`j++`。
    * 反之，`j`已经是下一个字母第一次出现的索引。此时先将当前字母`chars[i]`添加到结果中`res.append(chars[i])`，然后将这个字母出现的次数也添加到结果中`res.append(j - i);`。并且此时`j`所指的元素就是下一个要压缩的字母第一次出现的位置，所以令`i = j`。
    * 循环直到结束`j = S.length()`
* 不能忘记，循环结束时`j = S.length()`，还有最后一个字母没有添加到结果中，同样将最后一个字母添加进去。
* 最后要判断压缩字符串的长度是否比之前短，如果是则返回压缩字符串，否则返回原字符串。
* 时间复杂度为$O(n)$，空间复杂度$O(m)$，m为压缩字符串的长度。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.4 MB, 在所有 Java 提交中击败了100.00%的用户