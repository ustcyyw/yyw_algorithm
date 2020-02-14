# 删除排序链表中重复元素（只保留只出现过一次的元素）

### 原题

给定一个排序链表，删除所有含有重复数字的节点，**只保留原始链表中 没有重复出现 的数字**。

示例 1:

输入: 1->2->3->3->4->4->5
输出: 1->2->5
示例 2:

输入: 1->1->1->2->3
输出: 2->3

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii)：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii

----

### 解法

##### 1.pre head cur三指针的使用（我的解法）

```java
public ListNode deleteDuplicates(ListNode head) {
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode pre = fakeHead;
        while(head != null){
            ListNode cur = head;
            while(cur.next != null && cur.next.val == head.val)
                    cur = cur.next;
            if(head != cur){
                pre.next = cur.next;
                head = pre.next;
            } else {
                head = head.next;
                pre = pre.next;
            }
        }
        return fakeHead.next;
    }
```

思路分析：

* 注意本题要将重复元素都删掉，而不是重复元素保留一个，删掉其它。
* 设想一种特殊的情况，就是链表中只有一种重复元素，那么只使用`head`指针就没法完成，所以创建一个伪头指针`ListNode fakeHead = new ListNode(-1);`，并且使用一个前置指针`pre`，这样在调整`pre.next`时，就可以删除所有重复元素。
* 接下来，还有一个存在的问题，就是要找到重复元素所在的最后一个结点。这是个排序好的链表，所以使用一个`cur`指针从当前结点移动到与之重复的最后一个结点。如果`cur`没有移动，也就是`head == cur`，说明这个元素不重复，要保留。
* 移动`cur`的条件之一`cur.next != null`是为了使用`cur.next.val`时不会出现异常。外循环的条件就是正常的遍历链表的条件。
* 单次遍历链表，时间复杂度为$O(n)$，只使用了辅助的指针，空间复杂度为$O(1)$。

图示：以1->2->3->3->4->4->5为输入链表来演示。

![deleteDuplicates82.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/LinkedList/deleteDuplicates82.jpg?raw=true)

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了98.79%的用户
* 内存消耗 :44.5 MB, 在所有 Java 提交中击败了5.03%的用户