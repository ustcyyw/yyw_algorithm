# 9. 回文数

### 原题
判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。

示例 1:
输入: 121
输出: true

示例 2:
输入: -121
输出: false
解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。

示例 3:
输入: 10
输出: false
解释: 从右向左读, 为 01 。因此它不是一个回文数。

进阶:
你能不将整数转为字符串来解决这个问题吗？

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/palindrome-number)：https://leetcode-cn.com/problems/palindrome-number

### 三种解法

##### 1.转换为字符串

```java
public boolean isPalindrome(int x) {
        if (x < 0)
            return false;
        String strX = String.valueOf(x);
        for (int i = 0, j = strX.length() - 1; j > i; i++, j--) {
            if (strX.charAt(i) != strX.charAt(j))
                return false;
        }
        return true;
    }
```

思路分析：

* 依据题意，一个最简单的判断就是，负数不能为回文数字。

* 回文数字的判断，如果我们使用`String strX = String.valueOf(x)`将数字变为字符串，就是对回文字符串的判断。
* 如何判断一个字符串是否为回文字符串。第一个字符与最后一个字符相同，第二个字符与倒数第二个字符相同.....关于中心位置轴对称。
* 在字符串上进行双指针的扫描，初始时指针`i = 0`指向第一个字符，`j = strX.length() - 1`指向最后一个字符。将关于中心位置对应的字符进行比较，如果发现有不相等说明不是回文子串。循环结束没有发现对应位置不相等的字符，说明是回文串。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :14 ms, 在所有 Java 提交中击败了18.32%的用户
* 内存消耗 :36.8 MB, 在所有 Java 提交中击败了21.39%的用户

##### 2.使用List存放每一位数字

```java
public boolean isPalindrome2(int x) {
        if (x < 0)
            return false;
        List<Integer> nums = new ArrayList<>();
        while (x != 0) {
            nums.add(x % 10);
            x /= 10;
        }
        int size = nums.size();
        for (int i = 0, j = size - 1; j > i; i++, j--) {
            if (!nums.get(i).equals(nums.get(j)))
                return false;
        }
        return true;
    }
```

思路分析：

* 依据题意，一个最简单的判断就是，负数不能为回文数字。

* 不转化为字符串，但是要利用回文数字关于中心对称的特点进行位置关于中心对称的数字的比较。我们可以将该数每一位的数字一次放入一个List中，同样通过双指针的移动逐步对比。
* 如何将每一位数字放入List中？
    * 十进制数对10取余就得到个位的数字。
    * 十进制数`int`除以10就丢弃个位，将十位变为个位。
    * 所以可以使用一个循环，只要`x != 0`，就将`x % 10`放入List中，然后`x /= 10`
* 之后就如同字符串那样就行双指针遍历即可。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。

运行结果：
* 执行用时 :12 ms, 在所有 Java 提交中击败了33.42%的用户
* 内存消耗 :46.2 MB, 在所有 Java 提交中击败了5.01%的用户

##### 3.翻转一半数位的数字（官方解法）

```java
public boolean isPalindrome3(int x) {
        if (x < 0 || (x % 10 == 0 && x != 0)) // x除以10余数为0，则其个位为0，如果他不为0，其最高位不可能为0，所以不可能会回文数
            return false;
        int halfReverse = 0;
        while (x > halfReverse) { // 这个条件 很妙, 在翻转过程中x < halfReverse 说明已经翻转了一半的数位。 举例子看返回值与这个循环条件
            halfReverse = halfReverse * 10 + x % 10;
            x /= 10;
        }
        return x == halfReverse || x == halfReverse / 10; // 当数字长度为奇数时，我们可以通过 revertedNumber/10 去除处于中位的数字。
    }
```

思路分析：

* 与方法二类似的地方就在于获取原数字的每一位上的数字，但是没有使用双指针。而是像[判断是否为回文链表](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/isPalindrome.md)方法三的做法一样，翻转半条链表后比较与原来的半条链表值是否相等。
* 举个例子来理解这个问题。
    * 比如判断数字123321。将后三位翻转后为123，与前三位相等。
    * 比如判断数字45674。将后两位翻转后卫47，与前两位45不相等。
* 借鉴回文链表的判断，我们的做法就是：翻转数字的后n/2位，与前n/2位构成的数字比较，如果相等则为回问子串。
* 首先：
    * 负数不能为回文数字。
    * 个位为0但是本身不为0的数字`x % 10 == 0 && x != 0`不为回文数字，因为除了0没有任何数的最高位为0
* 使用变量`int halfReverse = 0`来存放翻转数字的后n/2位。与方法二类似的方式获取每一位数字` x % 10`，并且进行移位`x /= 10`。翻转数字的处理就是将之前的乘以10加上当前位。循环结束的条件为`x < halfReverse`因为这说明至少翻转了后n/2位。
* 最后当数字长度为偶数时，直接比较`x == halfReverse`;当数字长度为奇数时，我们可以通过` revertedNumber/10 `去除处于中位的数字。
* 时间复杂度为$O(n)$，空间复杂度为$O(1)$。