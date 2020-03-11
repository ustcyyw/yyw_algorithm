# 24.两两交换链表中的节点

### 原题
给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。

示例:

给定 1->2->3->4, 你应该返回 2->1->4->3.

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/swap-nodes-in-pairs)：https://leetcode-cn.com/problems/swap-nodes-in-pairs

### 解法

```java
public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode fake = new ListNode(-1);
        fake.next = head;
        ListNode pre = fake;
        ListNode cur = head;
        while(true){
            pre.next = cur.next;
            cur.next = cur.next.next;
            pre.next.next = cur;
            if(cur.next == null || cur.next.next == null)
                return fake.next;
            cur = cur.next;
            pre = pre.next.next;
        }
    }
```

思路分析：

* 首先，如果链表为空或者只有一个结点时，不需要成对交换，直接返回`head`。
* 改变链表的指针可以画示意图看我们需要几个变量来指向哪些结点。且经常使用一个技巧：创建一个不存在的结点来指向原链表的头结点。
* `while`循环的代码运行的过程如下图所示

![swapPairs图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/LinkedList/swapPairs%E5%9B%BE%E7%A4%BA.png?raw=true)

* 循环终止的条件为`cur.next == null || cur.next.next == null`分别对应原链表有偶数个结点和奇数个，结点的情况。循环结束的时候返回`fake.head`就是交换后链表真正的头部。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :34.5 MB, 在所有 Java 提交中击败了30.75%的用户

