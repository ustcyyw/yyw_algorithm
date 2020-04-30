# 202. 快乐数
### 原题
编写一个算法来判断一个数 n 是不是快乐数。
「快乐数」定义为：对于一个正整数，每一次将该数替换为它每个位置上的数字的平方和，然后重复这个过程直到这个数变为 1，也可能是 **无限循环** 但始终变不到 1。如果 可以变为  1，那么这个数就是快乐数。
如果 n 是快乐数就返回 True ；不是，则返回 False 。

示例：
输入：19
输出：true
解释：

```
1^2 + 9^2 = 82
8^2 + 2^2 = 68
6^2 + 8^2 = 100
1^2 + 0^2 + 0^2 = 1
```

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/happy-number)：https://leetcode-cn.com/problems/happy-number

### 2种解法
##### 1.使用哈希表判断循环
```java
public boolean isHappy(int n) {
        Set<Integer> set = new HashSet<>();
        set.add(n);
        while (true){
            n = create(n);
            if(n == 1)
                return true;
            if(set.contains(n))
                return false;
            set.add(n);
        }
    }

    private int create(int n) {
        int sum = 0;
        while (n != 0){
            sum += n % 10 * (n % 10);
            n /= 10;
        }
        return sum;
    }
```
思路分析：
* 根据题目意思，如果一个数按其规则变化最终可以为1则是快乐数，否则按规则变化会陷入无限循环。所以我们需要判断在按规则变化数字时：
    * 数字是否变为1。如果是，返回`true`
    * 在这个过程中是否出现了循环。如果是，返回`false`
* 首先辅助函数`int create(int n)`，按题目变换规则得到转换后的数字。通过循环，不断将n对10取余数平方加和`sum += n % 10 * (n % 10);`，然后`n /= 10;`去进行下一个数字的计算。
* 由于这里是对数字考虑是否出现循环，可以使用`Set<Integer> set = new HashSet<>();`来存放出现过的数字。
    * 首先将原本的数字`set.add(n);`。
    * 然后使用循环来模拟数字变化的过程。`n = create(n);`得到这一次转化后的数字，
    * 然后判断是否为1。如果不是则判断是否存与已出现数字的集合中，如果存在`if(set.contains(n))`，说明出现了循环,返回`false`。否则将该数字放入集合中。
* 根据题目的描述，这个循环一定会通过条件的判断返回。
* 时间复杂度与空间复杂度都取决于在数字变换的过程中，会有多少个不同的数字出现。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了51.98%的用户
* 内存消耗 :36.6 MB, 在所有 Java 提交中击败了8.33%的用户

##### 2.使用快慢指针判断循环
```java
public boolean isHappy2(int n){
        int slow = n, fast = create(n);
        while(fast != 1){
            if(fast == slow) return false; // 快指针等于慢指针，这个说明在计算过程中循环了，数字之间构成了环。
            fast = create(create(fast));
            slow = create(slow);
        }
        return true;
    }

	private int create(int n) {
        int sum = 0;
        while (n != 0){
            sum += n % 10 * (n % 10);
            n /= 10;
        }
        return sum;
    }
```
思路分析：
* 同样如方法一中的分析，这个问题就是判断
    * 变换过程中是否会出现循环
    * 或者数字是否变为1
* 数字的变换总是沿着同一个规则，所以前面一个数字变换后是哪一个数字是确定的，这就像链表中上一个节点的`node.next`是确定的。所以这个问题就可以类比为，一个链表中是否存在环，如果不存在环，最后一个元素是1。参考题目[141. 环形链表](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/hasCycle.md)的做法，可以使用快慢指针来解决。
* 就像一快一慢两个运动员，如果在直道赛跑，不存在追击问题；如果是在环道赛跑，快的绕了一圈可以追上慢的。
* 本题中，由于数字变为1后再利用该变换规则得到的还是1，所以判断是否走到链表尾只需要判断`fast`是否为1。如果`fast == slow`，快指针已经追上慢指针，成环。快指针每次行走两步，也就是连续两次变换` fast = create(create(fast));`。如果循环结束而没有返回，说明`fast == 1`，此时就返回`true`。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了99.89%的用户
* 内存消耗 :36.4 MB, 在所有 Java 提交中击败了8.33%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹