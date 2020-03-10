# 451. 根据字符出现频率排序
给定一个字符串，请将字符串里的字符按照出现的频率降序排列。

示例 1:
输入:
`"tree"`
输出:
`"eert"`
解释:
'e'出现两次，'r'和't'都只出现一次。
因此'e'必须出现在'r'和't'之前。此外，"eetr"也是一个有效的答案。

示例 2:
输入:
`"cccaaa"`
输出:
`"cccaaa"`
解释:
'c'和'a'都出现三次。此外，`"aaaccc"`也是有效的答案。
注意`"cacaca"`是不正确的，因为相同的字母必须放在一起。

示例 3:
输入:
`"Aabb"`
输出:
`"bbAa"`
解释:
此外，"bbaA"也是一个有效的答案，但"Aabb"是不正确的。
注意'A'和'a'被认为是两种不同的字符。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/sort-characters-by-frequency)：https://leetcode-cn.com/problems/sort-characters-by-frequency·

### 解法：桶排序

```java
public String frequencySort(String s) {
        int[] count = new int[128];
        for(int i = 0; i < s.length(); i++)
            count[s.charAt(i)] += 1;
        List<Character>[] charsSort = (List<Character>[]) new List[s.length() + 1];
        for(int i = 0; i < count.length; i++){
            if(charsSort[count[i]] == null)
                charsSort[count[i]] = new ArrayList<>();
            charsSort[count[i]].add((char) i);
        }

        StringBuilder res = new StringBuilder();
        for(int i = charsSort.length; i >= 0; i--){
            if(charsSort[i] != null){
                for(char c : charsSort[i]){
                    for(int j = 0; j < i; j++)
                        res.append(c);
                }
            }
        }
        return res.toString();
    }
```

思路分析：

* 这个题与[前K个高频元素](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Sort/topKFrequent.md)非常类似。所以同样采用桶排序的思路去解决它。
* 本题字符串被限制为数字字母等，所以不必使用`Map`去统计出现次数，只需要使用`int[] count`，将字符与数组的索引对应。遍历原字符串的每个字符，将字符对应的索引的元素加1，`count[s.charAt(i)] += 1`。
* 将字符出现的次数与桶数组的下标对应，将元素按照出现次数放入对应的桶中。这样从索引大到小的顺序遍历桶，就可以得到出现频率降序的字符序列。
* 链表数组的大小要确保不会出现数组索引越界，如果原字符串中都是同一字符，那么出现频率为`s.length()`，要将出现次数与桶数组索引对应，出现频率为`s.length()`时，桶数组最大的索引为`s.length()`，所以桶数组的长度为`s.length()+1`。
* 不过答案不是将单个字符进行排列，最后还需要将字符重复其出现次数并进行拼接。所以在拼接结果字符串时，还要重复`i`次。

代码解释：

* 2-4行，出现次数的统计。
* 5行，java中不能直接创建泛型数组，因为有类型擦除的存在。
* 6-10行，进行桶排序
* 12行，使用`StringBuilder`类，避免频繁拼接字符串的损耗。
* 13-20行，按出现次数从高到低，从对应的桶中取出元素。并且对于每个元素，重复`i`次放入答案中（16-17行）。

运行结果：
* 执行用时 :6 ms, 在所有 Java 提交中击败了95.71%的用户
* 内存消耗 :42 MB, 在所有 Java 提交中击败了5.09%的用户