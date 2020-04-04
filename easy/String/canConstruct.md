# 383. 赎金信

### 原题
给定一个赎金信 (ransom) 字符串和一个杂志(magazine)字符串，判断第一个字符串ransom能不能由第二个字符串magazines里面的字符构成。如果可以构成，返回 true ；否则返回 false。
(题目说明：为了不暴露赎金信字迹，要从杂志上搜索各个需要的字母，组成单词来表达意思。)

注意：
你可以假设两个字符串均只含有小写字母。

```
canConstruct("a", "b") -> false
canConstruct("aa", "ab") -> false
canConstruct("aa", "aab") -> true
```

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/ransom-note

### 三种解法

##### 1.排序与双指针

```java
public boolean canConstruct(String ransomNote, String magazine) {
        char[] r = ransomNote.toCharArray();
        char[] m = magazine.toCharArray();
        Arrays.sort(r);
        Arrays.sort(m);
        int i = 0, j = 0;
        while(i < r.length && j < m.length){
            if(r[i] > m[j]) j++;
            else if (r[i] < m[j]) return false;
            else { // 一个字母只能用一次，所以当相等时，两个指针都需要移动
                i++;
                j++;
            }
        }
        return i == r.length;
    }
```

思路分析：

* 题目要求`magazine`中的字母是否可以构成`ransomNote`，且`magazine`中的字母不能重复使用。那么对于`ransomNote`中的每一个字符，都去`magazine`中查找是否存在（找到一个后，要从`magazine`中剔除）。
* 用暴力查找太慢。借鉴判断字符串相等的思路，双指针分别指向两个字符串的第一个字符，依次对比，双指针一起移动。我们可以将`magazine`与`ransomNote`对应的字符数组`m`与`r`进行排序，然后使用双指针逐一对比字符。
* 用指针`i`指向`r[]`的第一个字符，用`j`指向`m`的第一个字符。之后对于两个字符数组中的字符进行判断，为了防止索引越界，循环条件为`while(i < r.length && j < m.length)`。
    * 当`r[i] > m[j]`时，`m`中当前字符不能拿来构成`r[i]`，尝试用`m`中的下一个字符`j++`
    * 当`r[i] < m[j]`时，`m`中当前字符已经验证过无法构成`r[i]`而`m`中当前元素及后续元素都比`r[i]`大，所以`r[i]`这个字符找不到可以构建的，答案就是`return false`
    * 其余情况，即`r[i] == m[j]`，`r[i]`找到可以用于构建的，所以去判断下一个字符`r++`，注意由于`magazine`中字母不能重复使用，所以`j++`使用`m`中下一个字母。
* 当循环结束后，如果`i == r.length`说明对于`r`中的每个字母都在`m`中找到对应的字母，此时可以按照题目要求构建出`randomNote`，返回`true`，否则返回`false`。
* 双指针的遍历是线性的，但是排序是线性对数的，所以时间复杂度为$O(nlog(n)+mlog(m))$，空间复杂度为$O(m + n)$。

运行结果：
* 执行用时 :8 ms, 在所有 Java 提交中击败了45.37%的用户
* 内存消耗 :42 MB, 在所有 Java 提交中击败了5.04%的用户
##### 2.哈希表计数

```java
public boolean canConstruct2(String ransomNote, String magazine) {
        Map<Character, Integer> rMap = new HashMap<>((int)(ransomNote.length() / 0.75F + 1.0F));
        Map<Character, Integer> mMap = new HashMap<>((int)(ransomNote.length() / 0.75F + 1.0F));
        for(char c : ransomNote.toCharArray()){
            rMap.put(c, rMap.getOrDefault(c, 0) + 1);
        }
        for(char c : magazine.toCharArray()){
            mMap.put(c, mMap.getOrDefault(c, 0) + 1);
        }
        for(char key : rMap.keySet()){ // 要细心啊！可能第二个字符串中没有出现过第一个字符串中的字符，所以得用getOrDefault()
            if(rMap.get(key) > mMap.getOrDefault(key, 0)) // 第二个字符串可以有剩余
                return false;
        }
        return true;
    }
```

思路分析：

* 如果`randomNote`中的某个字母出现了x次，而这个字母在`magazine`中出现了至少x次，就这个字母来说，就可以构建完成。如果对于`randomNote`中的每个字母都满足这样的条件，就可以构成整个`randomNote`。所以只需要对两个字符串中出现的字母进行计数统计就可以。
* 使用HashMap进行计数，就是常规操作了。以字母为键，以该字母出现次数为值，对字符串进行单次遍历。遍历过程中对每个字母如此处理：`rMap.put(c, rMap.getOrDefault(c, 0) + 1);`
* 遍历完成后，对于`randomNote`中出现的每个字母`for(char key : rMap.keySet())`，判断这个字母在`magazine`的出现次数(没有出现则出现次数为0`mMap.getOrDefault(key, 0)`)是否大于`randomNote`中出现的次数`rMap.get(key)`。
    * 否则返回`false`
* 循环结束说明满足构建要求。
* 时间复杂度为$O(m + n)$。空间复杂度为$O(m + n)$。

运行结果：
* 执行用时 :16 ms, 在所有 Java 提交中击败了32.57%的用户
* 内存消耗 :42.4 MB, 在所有 Java 提交中击败了5.04%的用户

##### 3.用数组代替哈希表

```java
public boolean canConstruct3(String ransomNote, String magazine) {
        int[] countR = new int[26];
        int[] countM = new int[26];
        for(char c : ransomNote.toCharArray())
            countR[c - 'a'] += 1;
        for(char c : magazine.toCharArray())
            countM[c - 'a'] += 1;
        for(int i = 0; i < 26; i++){
            if(countR[i] > countM[i])
                return false;
        }
        return true;
    }
```

思路分析：

* 注意到题目中说，字符只能是小写字母，那么我们可以使用`int[]`来代替散列表的功能，以`c - 'a'`为数组索引，值依然为该字母出现的次数。
* 逻辑结构与方法2一样，不够这样节约了散列函数计算的时间，也节约了散列冲突的处理时间，所以会快很多。
* 时间复杂度为$O(m + n)$。空间复杂度为$O(1)$（数组长度是常数）。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了81.69%的用户
* 内存消耗 :41.4 MB, 在所有 Java 提交中击败了5.04%的用户