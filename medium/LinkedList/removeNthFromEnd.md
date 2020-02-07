# 删除链表倒数第 n 个节点

### 原题
给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。
示例：
给定一个链表: 1->2->3->4->5, 和 n = 2.
当删除了倒数第二个节点后，链表变为 1->2->3->5.
说明：
给定的 n 保证是有效的。
进阶：
你能尝试使用一趟扫描实现吗？
来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/remove-nth-node-from-end-of-list

###  两种解法

##### 1.先遍历得到链表长度（我的解法）

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
        // 要特别注意 如果要删的结点是头结点这种情况， 可以使用伪头结点
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        int count = 0;
        for (; head != null; head = head.next) // 得到链表的长度
            count++;
        ListNode temp = fakeHead;
        for (int i = 0; i < count - n; i++)
            temp = temp.next;
        temp.next = temp.next.next;
        return fakeHead.next;
    }
```

思路分析：

* 要删除倒数第n个结点，关键就在于确定要删哪一个结点，将指针指到要删除的结点的上一个结点，通过`temp.next = temp.next.next;`即可完成删除。

* 有一种特殊情况，要删除的结点即为第一个结点时，要将指针移动到其前一个结点，就需要一个伪头结点。

* 确定要删除的结点，可以先得到链表长度`count`，然后通过移动`count - n`次就可以到达要删除结点的上一个结点（因为使用了伪头结点）。

* 这种方法最坏遍历了两次列表，时间复杂度 $O(n)$， 只使用了一个 int 和一个伪头 空间复杂度 $O(1)$

代码解释：

* 5~7行确定链表长度。
* 9~10行将指针移动到要删除结点的上一个结点。

运行结果：
* 1 ms, 在所有 Java 提交中击败了64.53%的用户
* 内存消耗 :35 MB, 在所有 Java 提交中击败了15.79%的用户
##### 2.双指针确定删除结点（官方标答）

```java
public ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode fakeNode = new ListNode(-1);
        fakeNode.next = head;
        ListNode fast = fakeNode; // 使用双指针 间隔为n 在前面的指针到达终结点的时候，后一个指针刚好距离终结点n，也就是倒数第n个结点的上一个结点
        for(int i = 0; i < n; i++)
            fast = fast.next;
        ListNode slow = fakeNode;
        while(fast.next != null){
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return fakeNode.next;
    }
```

思路分析：

* 确定要删除哪个结点，还可以使用双指针。
* 双指针 间隔为n 在前面的指针到达终结点的时候，后一个指针刚好距离终结点n，也就是倒数第n个结点的上一个结点。

代码解释：

* 5~6行 让`fast`指针移动到距离`slow`指针n处。
* 8~11 双指针移动，将`slow`指向要删除结点的上一个结点。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了64.53%的用户
* 内存消耗 :34.9 MB, 在所有 Java 提交中击败了43.16%的用户