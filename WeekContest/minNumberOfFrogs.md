# 5390. 数青蛙
### 原题
给你一个字符串 croakOfFrogs，它表示不同青蛙发出的蛙鸣声（字符串 "croak" ）的组合。由于同一时间可以有多只青蛙呱呱作响，所以 croakOfFrogs 中会混合多个 “croak” 。请你返回模拟字符串中所有蛙鸣所需不同青蛙的最少数目。
注意：要想发出蛙鸣 "croak"，青蛙必须 依序 输出 ‘c’, ’r’, ’o’, ’a’, ’k’ 这 5 个字母。如果没有输出全部五个字母，那么它就不会发出声音。
如果字符串 croakOfFrogs 不是由若干有效的 "croak" 字符混合而成，请返回 -1 。

示例 1：
输入：croakOfFrogs = "croakcroak"
输出：1 
解释：一只青蛙 “呱呱” 两次

示例 2：
输入：croakOfFrogs = "crcoakroak"
输出：2 
解释：最少需要两只青蛙，“呱呱” 声用黑体标注
第一只青蛙 "crcoakroak"
第二只青蛙 "crcoakroak"

示例 3：
输入：croakOfFrogs = "croakcrook"
输出：-1
解释：给出的字符串不是 "croak" 的有效组合。

示例 4：
输入：croakOfFrogs = "croakcroa"
输出：-1

提示：

1 <= croakOfFrogs.length <= 10^5
字符串中的字符只有 'c', 'r', 'o', 'a' 或者 'k'

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/minimum-number-of-frogs-croaking)：https://leetcode-cn.com/problems/minimum-number-of-frogs-croaking

### 解法
```java
public class minNumberOfFrogs {
    private static Map<Character, Integer> map = new HashMap<>(); // 某字符在count[]数组中的索引
    static {
        map.put('r', 1);
        map.put('o', 2);
        map.put('a', 3);
    }

    public int minNumberOfFrogs(String croakOfFrogs) {
        int res = 0, cur = 0;
        int[] count = new int[4]; // 分别记数 c r o a 结尾的字符串的个数
        for (char c : croakOfFrogs.toCharArray()) {
            if (c == 'c'){
                count[0] += 1;
                cur++;
                res = Math.max(cur, res);
            }
            else if (c == 'k'){
                count[3] -= 1;
                cur--;
            }
            else {
                int index = map.get(c);
                count[index] += 1;
                count[index - 1] -= 1;
            }
            for(int i : count)
                if(i < 0)
                    return -1;
        }
        return cur > 0 ? -1 : res;
    }
}
```
思路分析：
* 数青蛙，青蛙呱呱叫，这题目真暴力。
* 观察题目的示例二，我们发现按`c,r,o,a,k`的顺序凑成字符串，如果凑的过程中插入了不符合顺序的字符，那么就需要另外一只青蛙，而如果字符`c,r,o,a,k`，凑齐了之后这只青蛙就可以进行下一次吟唱了。青蛙可以重复利用。
* 所以我们只需要计数，在扫描字符串的过程中，有多少现存`croak`不完整字符串。
    * 如果出现了字母`c`，就有一个新的不完整字符串产生，以c结尾的字符串数量加1，需要新的青蛙来呱呱。
    * 如果出现了字母`k`，`croak`完整，这只青蛙唱完了这个呱呱，不完整字符串减少，并且以`a`结尾的字符串数目减少1.(把`k`添加到`croa`而完整，且a结尾的字符串数目就减少1)
    * 出去其它三个字母，没有产生新的不完整字符。比如，出现的字母是`o`，那么添加到`cr`后面，以r结尾的不完整字符数目-1，以o结尾的不完整字符数目+1，都是同一只青蛙在呱呱，青蛙数目不需要增加。
* 根据上述描述，使用`int[] count = new int[4]`，4个`int`分别记数` c， r ，o ，a` 结尾的字符串的个数。以`int cur`表示现有的不完整字符串的数目。以`Map<Character, Integer> map`记录，`r,o,a`在`count[]`中对应的索引。
    * `if (c == 'c')`，以c结尾的不完整字符串数目加1，需要一只青蛙来呱呱`count[0] += 1; cur++; `
    * `if (c == 'k')`，不完整字符串消失一个，一只青蛙休息，以a结尾的不完整字符串-1。`count[3] -= 1; cur--;` 
    * 其余情况，以该字母结尾的不完整字符串+1`count[index] += 1;`，其前一个字母结尾的不完整字符串-1`count[index - 1] -= 1;`
* 要得到最少多少只青蛙能完成吟唱，就是在扫描过程中现存不完整字符串的最大值。只有在出现字母`c`时，才会增加现存不完整字符串，所以`if (c == 'c')`，更新结果`res = Math.max(cur, res);`
* 但是还有注意不能完成吟唱的情况，有两种：
    * 新不完整字符串的产生不是以`c`开头的，而是以其他字母开头（没有能与之拼接的不完整字符串），这样就会使得`count[]`中有元素为-1。所以在循环中，还要判断对于`for(int i : count)`，如有`i < 0`返回-1.
    * 另外一种情况就是，如果扫描完成还有不完整字符串，即`cur > 0`，也要返回-1。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：

* medium 19ms

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹