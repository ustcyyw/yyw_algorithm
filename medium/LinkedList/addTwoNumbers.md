# 两链表代表的数字相加

### 原题

给定两个非空链表来代表两个非负整数。数字最高位位于链表开始位置。它们的每个节点只存储单个数字。将这两数相加会返回一个新的链表。

 

你可以假设除了数字 0 之外，这两个数字都不会以零开头。

进阶:

如果输入链表不能修改该如何处理？换句话说，你不能对列表中的节点进行翻转。

示例:

输入: (7 -> 2 -> 4 -> 3) + (5 -> 6 -> 4)
输出: 7 -> 8 -> 0 -> 7

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/add-two-numbers-ii)：https://leetcode-cn.com/problems/add-two-numbers-ii

---

### 两种解法

##### 1.使用栈得到正确相加顺序（我的第一解）

```java
public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int flag = 0; // 用来表示是否进位
        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();
        for (ListNode temp = l1; temp != null; temp = temp.next) // 通过使用栈 使得链表元素表示个数字按个位十位百位的顺序取出
            s1.push(temp.val);
        for (ListNode temp = l2; temp != null; temp = temp.next)
            s2.push(temp.val);
        ListNode result = null;
        while (!s1.empty() || !s2.empty()) {
            int a1 = !s1.empty() ? s1.pop() : 0; // 取出当前位的数字
            int a2 = !s2.empty() ? s2.pop() : 0;
            int cur = a1 + a2 + flag;
            int single; // 表示相加之和进位后该位的数字
            if (cur < 10) {
                single = cur;
                flag = 0;
            } else {
                single = cur - 10;
                flag = 1;
            }
            ListNode temp = new ListNode(single);
            temp.next = result;
            result = temp;
        }
        if (flag == 1) {
            ListNode temp = new ListNode(1);
            temp.next = result;
            return temp;
        }
        return result;
    }
```

思路分析：

* 所给的链表链表头结点代表该数字的最高位，顺序遍历链表是从高位到低位。要得到正确的相加顺序，需要将链表元素进行翻转。
* 一种可行的办法是使用栈，在顺序遍历链表的时候将链表中元素放入栈中。当遍历完后，栈顶元素就是该数字的个位；栈底元素就是该数字的最高位。
* 当存放各个数位数字的栈中还有元素时，相加没有结束。**但是！**还要注意，在最高位完成相加后，如果有进位，还需要将进位的'1'表示出来。
* 相加过程中，需要注意进位问题`flag`变量来表示是否进位，如果进位则其值为1，且在需要进位的情况下，该位保留下的数字`single`需要减去10。
* 用一个链表来表示相加后的数，每次当前位的数字都插入到链表头即可。
* 使用两个栈存放元素，最后还会生成新链表假设长度为$l$ 空间复杂度$O(n+m+l)$。遍历链表及按位相加，时间复杂度$O(n+m+l)$。

代码分析：

* 3~8行 顺序将链表元素入栈使得从栈中弹出的数字符合相加顺序。
* 11~12行 得到两个加数，当其中一个栈空了就意味着该数字该数位为0。13行 加和别忘记进位的1。
* 15~21行 进位及当前数位数字的处理。也可以简化为`flag = cur / 10; single = cur % 10`。
* 22~24行 当前数位的数字插入链表头。
* 26~30行 别忘了最后可能的进位数字的处理(`flag == 1`时)。

运行结果：
* 执行用时 :7 ms, 在所有 Java 提交中击败了56.85%的用户
* 内存消耗 :45.7 MB, 在所有 Java 提交中击败了9.49%的用户
##### 2.使用链表翻转得到正确相加顺序（参考答案）

```java
public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode result = null;
        ListNode tailA = reverse(l1);
        ListNode tailB = reverse(l2);
        int flag = 0;
        while (tailA != null || tailB != null || flag == 1) { // 一直相加到没有加数而且没有进位
            int single = 0;
            int a1 = tailA != null ? tailA.val : 0;
            int a2 = tailB != null ? tailB.val : 0;
            int sum = a1 + a2 + flag;
            if (sum >= 10) {
                flag = 1;
                single = sum - 10;
            } else {
                flag = 0;
                single = sum;
            }
            ListNode temp = new ListNode(single);
            temp.next = result;
            result = temp;
            tailA = tailA != null ? tailA.next : null;
            tailB = tailB != null ? tailB.next : null;
        }
        return result;
    }

    private ListNode reverse(ListNode node) {
        if (node == null || node.next == null)
            return node;
        ListNode p = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return p;
    }
```

思路分析：

* 将链表元素顺序进行翻转另外一种直接的办法就是直接将链表翻转。翻转链表的递归方法已经归纳过。
* 使用链表来存放数字，就需要注意链表尾的处理。在不断相加过程中使用`tailA = tailA != null ? tailA.next : null;`确保不会对`null`进行获取实例变量的操作。
* 整体思路与方法一基本一致。这里循环条件`tailA != null || tailB != null || flag == 1`表示：一直相加到没有加数而且没有进位。这个比方法一简洁，当然方法一也可以如此修改。
* 由于没有使用辅助栈，链表是就地翻转，所以空间复杂度$O(1)$；时间复杂度同方法一。

代码分析：类似方法一，偷懒 :smile_cat:

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了85.27%的用户
* 内存消耗 :44.3 MB, 在所有 Java 提交中击败了65.78%的用户