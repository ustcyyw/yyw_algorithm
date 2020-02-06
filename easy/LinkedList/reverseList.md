# 反转链表

[toc]

### 原题
反转一个单链表。

示例:

输入: 1->2->3->4->5->NULL

输出: 5->4->3->2->1->NULL

进阶:

你可以迭代或递归地反转链表。你能否用两种方法解决这道题？

来源：力扣（LeetCode）

[链接](https://leetcode-cn.com/problems/reverse-linked-list)：https://leetcode-cn.com/problems/reverse-linked-list

---

### 两种解法

##### 1.迭代（我的解法）

```java
public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode reverseHead = null;
        while(head != null){
            ListNode temp = new ListNode(head.val);
            temp.next = reverseHead;
            reverseHead = temp;
            head = head.next;
        }
        return reverseHead;
    }
```

思路分析：

* 创建一个新的结点做为翻转链表的头结点，该结点值就是原链表的第一个元素。
* 每次原链表指针`head`往右一位就将`head`所指的结点插入到新链表的链表头，这样就可以实现链表翻转。
* 只遍历了一次链表，所以时间复杂度为$O(n)$；只使用一个新的头结点且过程中只使用一个辅助结点`temp`空间复杂度$O(1)$。
* 注意如果是空链表或者链表只有一个结点`head == null || head.next == null`，直接返回就行。

代码分析：

* 将节点插入链表头

```
ListNode temp = new ListNode(head.val);
            temp.next = reverseHead;
            reverseHead = temp;
```

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100%的用户
* 内存消耗 :37.1 MB, 在所有 Java 提交中击败了30.96%的用户

##### 2.递归(官方标答)

```java
public ListNode reverseList2(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode tail = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return tail;
    }
```

图示：

![reverseList.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/reverseList.jpg?raw=true)

思路分析：

* 当e->d已经完成翻转的时候，如何翻转d与c，我们需要将`c.next.next = c`。`c.next`也就是`d`，这里就实现了`d`指向`c`，并且不可以忘记要将`c.next = null`使`c`不再指向`d`。
* 迭代的下沉阶段，其实是不断调用`ListNode tail = reverseList2(head.next);`使得`tail`即为原链表的尾结点，也就是翻转链表的头。
* 上浮阶段：不断完成当前结点与下一结点的翻转过程，且不断返回`tali`也就是翻转链表头结点。
* 递归次数为n次，时间复杂度$O(n)$，递归使用栈内存，空间复杂度$O(n)$。

运行结果：

* 并没有提交 23333 :sweat_smile:

