# 914. 卡牌分组

### 原题
给定一副牌，每张牌上都写着一个整数。
此时，你需要选定一个数字 X，使我们可以将整副牌按下述规则分成 1 组或更多组：
每组都有 X 张牌。
组内所有的牌上都写着相同的整数。
仅当你可选的 X >= 2 时返回 true。

示例 1：
输入：[1,2,3,4,4,3,2,1]
输出：true
解释：可行的分组是 [1,1]，[2,2]，[3,3]，[4,4]

示例 2：
输入：[1,1,1,2,2,2,3,3]
输出：false
解释：没有满足要求的分组。

示例 3：
输入：[1]
输出：false
解释：没有满足要求的分组。

示例 4：
输入：[1,1]
输出：true
解释：可行的分组是 [1,1]

示例 5：
输入：[1,1,2,2,2,2]
输出：true
解释：可行的分组是 [1,1]，[2,2]，[2,2]

提示：
```
1 <= deck.length <= 10000
0 <= deck[i] < 10000
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/x-of-a-kind-in-a-deck-of-cards)：https://leetcode-cn.com/problems/x-of-a-kind-in-a-deck-of-cards

### 两种解法

##### 1.试探找到所有分组的公约数

```java
public boolean hasGroupsSizeX(int[] deck) {
        Map<Integer, Integer> count = new HashMap<>();
        for(int i : deck)
            count.put(i, count.getOrDefault(i, 0) + 1);
        int min = Integer.MAX_VALUE;
        for(int key : count.keySet())
            min = Math.min(min, count.get(key));
        for(int i = 2; i <= min; i++){
            boolean flag = true;
            for(int key : count.keySet())
                if(count.get(key) % i != 0){ // 如果有一组相同数字无法整除 每组分i就不可以
                    flag = false;
                    break;
                }
            if(flag) return true;
        }
        return false;
    }
```

思路分析：

* 要将牌分为多个组，每个组都有X张牌且组内数字一定相同，所以每一个数字出现的次数都是X的整数倍。也就是说X是所有数字出现次数的公约数。根据题目要求，这个公约数要大于等于2。
* 目标明确，我们需要先去统计每个数字出现的次数，然后找到每个数字出现的次数的一个大于等于2的公约数。
* 涉及到统计次数，显然可以使用哈希表，键为某个数字，值为该数字出现的次数。遍历` for(int i : deck)`计数`count.put(i, count.getOrDefault(i, 0) + 1);`。
    * 反思：题目有设置 `0 <= deck[i] < 10000`，所以不用map也可以，用数组来做映射也可以，索引为元素的值；值则为该元素出现的次数。就像有限多个字母的作法。
* 然后尝试去找到一个大于等于2的公约数，试探到什么时候为止呢？用`min`表示出现的最小次数，公约数最大可能为`min`，因为超过了`min`，那么出现的最小次数一定不能被整除。对计数哈希表`count`中所有键遍历，找到最小的出现次数。
* 公约数的尝试`[2, min]`的外循环，在每次外循环开始时，先设置`boolean flag = true`，用来标识在对每个出现次数求余时，是否出现不整出的情况，如果出现了就设置`flag = false`。内循环结束后判断如果`flag = true`说明所有出现次数都被整除了，此时找到了符合题意的X，返回`true`。否则进行下一个可能的公约数的尝试
* 当尝试结束而没有返回，说明没有找到符合题意的X，最终返回`false`。

运行结果：
* 执行用时 :13 ms, 在所有 Java 提交中击败了45.99%的用户
* 内存消耗 :41.7 MB, 在所有 Java 提交中击败了5.51%的用户

##### 2.直接求所有分组的公约数

```java
public boolean hasGroupsSizeX2(int[] deck) {
        Map<Integer, Integer> count = new HashMap<>();
        for(int i : deck)
            count.put(i, count.getOrDefault(i, 0) + 1);
        int pre = count.get(deck[0]);
        for(int key : count.keySet()){
            int temp = count.get(key);
            pre = pre > temp ? maxDivisor(pre, temp) : maxDivisor(temp, pre);
            if(pre == 1)
                return false;
        }
        return pre != 1;
    }

 	private int maxDivisor(int p, int q){
        if(p % q == 0) return q;
        else return maxDivisor(q, p % q);
    }
```

思路分析：

* 统一要去找每个数字的出现次数的公约数，这个公约数要大于1。一样如同方法一，先去统计每个数字出现的次数。
* 这里我们采用直接求出所有出现次数最大公约数的方法。如果最大公约数为1，说明无法找到符合题意的X；否则这个最大公约数就是一个符合题意的X。
* a,b,c的最大公约数：可以先求出a和b的最大公约数d，然后求d与c的最大公约数就是结果。这里同样，不断将两个出现次数的最大公约数`pre`与下一个出现次数`int temp = count.get(key)`求最大公约数。
* 求最大公约数采用辗转相除法，定义辅助函数`int maxDivisor(int p, int q)`，传参时要求`p > q`。所以在不断归约求最大公约数时`pre = pre > temp ? maxDivisor(pre, temp) : maxDivisor(temp, pre);`。
* 在规约过程中如果发现`pre`已经为1，就直接返回`false`。否则等规约结束后判断`pre`是否不为1。

运行结果：

* 执行用时 :13 ms, 在所有 Java 提交中击败了45.99%的用户
* 内存消耗 :41.7 MB, 在所有 Java 提交中击败了5.51%的用户