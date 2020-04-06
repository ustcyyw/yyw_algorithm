# 205. 同构字符串

### 原题
给定两个字符串 s 和 t，判断它们是否是同构的。
如果 s 中的字符可以被替换得到 t ，那么这两个字符串是同构的。
所有出现的字符都必须用另一个字符替换，同时保留字符的顺序。两个字符不能映射到同一个字符上，但字符可以映射自己本身。

示例 1:
输入: s = "egg", t = "add"
输出: true

示例 2:
输入: s = "foo", t = "bar"
输出: false

示例 3:
输入: s = "paper", t = "title"
输出: true
说明:
你可以假设 s 和 t 具有相同的长度。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/isomorphic-strings)：https://leetcode-cn.com/problems/isomorphic-strings

### 两种解法

##### 1.数组保存字符映射关系一

```java
public boolean isIsomorphic(String s, String t) {
        return canMap(s, t) && canMap(t, s);
    }

    private boolean canMap(String s, String t){
        int[] map = new int[128]; // ascii 128个字符
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        for(int i = 0; i < chars1.length; i++){
            if(map[chars1[i]] == 0)
                map[chars1[i]] = chars2[i];
            else {
                if(map[chars1[i]] != chars2[i])
                    return false;
            }
        }
        return true;
    }
```

思路分析：

* 按照题目意思，s中的字符要被替换，然后成为t。替换过程就是一个映射问题，第一次进行替换后，映射不能改变，并且映射是一一对应的（两个字符不能映射到同一个字符上）。
* 映射自然想到要使用HashMap，但是对于题目有限的字符，比如本题中是ascii 128个字符，可以使用数组代替哈希表。键为字符串1中的字符，值为字符串2中对应位置的字符，这是建立了从字符串1到字符串2的索引。
* 辅助函数`boolean canMap(String s, String t)`，遍历字符串1中的字符串：
    * 如果映射还没有建立`map[chars1[i]] == 0`，就创建映射`map[chars1[i]] = chars2[i]`。这个时候是对于字符串1中该字符的第一次替换
    * 如果映射已经建立，就判断按照映射关系替换1中的字符，是否与字符串2对应的字符相同，如果不相同`map[chars1[i]] != chars2[i]`说明无法按照一一替换的原则完成，直接返回`false`。
    * 如果遍历完成，说明以字符串1可以完成替换。
* 但是仅仅这样做只满足了映射不能改变这个规定。比如`ab`与`aa`，这样做会让字母a与b都映射到a，映射没有改变，但是违背了一一对应原则。所以需要最后答案要返回`canMap(s, t) && canMap(t, s);`才能保证映射不变且映射是一一的。
* 时间复杂度为$O(n)$的，空间复杂度，由于只使用了常数长度的辅助数组，是$O(1)$的。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了98.19%的用户
* 内存消耗 :43.3 MB, 在所有 Java 提交中击败了5.03%的用户

##### 2.数组保存字符映射关系二

```java
public boolean isIsomorphic2(String s, String t) {
        int[] map1 = new int[128];
        int[] map2 = new int[128];
        char[] chars1 = s.toCharArray();
        char[] chars2 = t.toCharArray();
        for(int i = 0; i < chars1.length; i++){
            if(map1[chars1[i]] == 0 && map2[chars2[i]] == 0) { // 还没有建立相互的映射
                map1[chars1[i]] = chars2[i]; // 从1到2的映射 角标表示1中的字符，值表示隐射到的值
                map2[chars2[i]] = chars1[i];
            } else if (map1[chars1[i]] == chars2[i] && map2[chars2[i]] == chars1[i])
                continue;
            else return false;
        }
        return true;
    }
```

思路分析：

* 方法二与方法一基本一致，只不过在处理一一对应这个规定上更清晰。使用两个`int[]`数组`map1`与`map2`分别表示s到t的字符映射与t到s的字符映射。
* 遍历s，t中的字符
    * 当映射还没有建立时`map1[chars1[i]] == 0 && map2[chars2[i]] == 0`，创建映射`map1[chars1[i]] = chars2[i]; map2[chars2[i]] = chars1[i];`
    * 如果映射已经建立，就去比较当前两个字符是否符合一一映射，如果符合`map1[chars1[i]] == chars2[i] && map2[chars2[i]] == chars1[i]`，继续遍历。否则直接返回`false`
    * 遍历完成后，说明映射不变且映射是一一对应的，就得到了异构定义的成立，返回`true`。

* 时间复杂度为$O(n)$的，空间复杂度，由于只使用了常数长度的辅助数组，是$O(1)$的。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了98.19%的用户
* 内存消耗 :42 MB, 在所有 Java 提交中击败了5.03%的用户