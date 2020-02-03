## 合并两个有序单向链表

[toc]

### 原题目

将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 

示例：

输入：1->2->4, 1->3->4
输出：1->1->2->3->4->4

来源：力扣（LeetCode）
[原题链接](https://leetcode-cn.com/problems/merge-two-sorted-lists)：https://leetcode-cn.com/problems/merge-two-sorted-lists

***

### 三种解法

##### 1.类似归并排序中的归并（我的第一解）

```java
public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val > l2.val) {
            return merge1(l2, l1);
        }
        return merge1(l1, l2);
    }

    private ListNode merge1(ListNode first, ListNode second) {
        ListNode head = new ListNode(first.val);
        ListNode temp = head;
        first = first.next;
        while (true) {
            if (first == null && second == null)
                break;
            else if (first == null) { // 这里要注意 直接接到另一个链表的当前表头即可
                temp.next = second;
                break;
            } else if (second == null) {
                temp.next = first;
                break;
            } else {
                if (first.val <= second.val) {
                    temp.next = new ListNode(first.val);
                    temp = temp.next;
                    first = first.next;
                } else {
                    temp.next = new ListNode(second.val);
                    temp = temp.next;
                    second = second.next;
                }
            }
        }
        return head;
    }
```

思路分析：

* 如果链表中其一为`null`直接返回另外一个链表。
* 创建一条新列表，要将`l1`与`l2`中小的值放入头节点。
* 新链表的生长**类似归并排序**，每次将新`l1`与`l2`中当前最小的值插入新链表尾
* 链表有需要特别注意的地方：链表尾的处理。`first == null && second == null`此时循环就结束；当`l1`与`l2`中其一元素已存放完时（i.e.`first == null`或者`second == null`），新链表直接指向另一条链表的当前结点即可。

代码解释：

* 由于无法确定新链表头结点是`l1`的头结点还是`l2`的头结点。所以要有一个判断，于是设计一个辅助函数`private ListNode merge1(ListNode first, ListNode second)`，传入的第一个参数作为新链表的头结点。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了86.85%的用户
* 内存消耗 :36.7 MB, 在所有 Java 提交中击败了79.82%的用户

##### 2.插队：将一链表插入另一链表（我的第二解）

```java
public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val > l2.val) {
            return merge2(l2, l1);
        }
        return merge2(l1, l2);
    }

    private ListNode merge2(ListNode first, ListNode second) {
        ListNode head = first;
        while (second != null) {
            if (first.next == null) {
                first.next = second; // 这里要注意 直接接到另一个链表的当前表头即可
                break;
            } else {
                if (first.next.val >= second.val) {
                    ListNode temp = new ListNode(second.val);
                    second = second.next;
                    temp.next = first.next;
                    first.next = temp;
                }
            }
            first = first.next;
        }

        return head;
    }
```

思路分析：

* 如果链表中其一为`null`直接返回另外一个链表。

* 以头结点较小的链表为基础链表，逐渐将另外一条链表的元素一个一个插入进来。以`first`为基础链表，每一次都需要将`first`的下一个结点值与`second`的当前结点的值进行比较。
* 插入结点后或者当前不需要插入结点，`first`均指向下一个结点，实现链表的不断生长。直到`second != null`条件成立，次数`second`中所有元素插入`first`中。
* 链表有需要特别注意的地方：链表尾的处理。当`first`没有下一个元素时，（i.e.`first == null`)直接指向另一条链表的当前结点即可。

图示：

![mergeTwoLists.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/mergeTwoLists.jpg?raw=true)

代码解释：

* `first.next == null`时，直接指向另一条链表的当前结点`first.next = second;`结束循环返回结果
* `first.next.val >= second.val`此时将`second`的当前结点插入。
* `first = first.next;`每个循环最后都执行此语句，从图示中可以看出不管何种情况，`first`都要向下一节点移动。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了86.96%的用户
* 内存消耗 :40.9 MB, 在所有 Java 提交中击败了5.03%的用户。速度比解法1快了一点点点，但是内存多用很多。:joy:

##### 3.官方标答：递归

```java
public ListNode mergeTwoLists3(ListNode l1, ListNode l2) {
        if(l1 == null) // 递归的终止条件 当l1为null 另外一条链表是排号序的，其当前头结点即目前最小的结点
            return l2;
        if(l2 == null)
            return l1;

        // 下沉阶段 当前较小结点指向比其稍大一点的结点。回溯阶段 每次返回较小的结点。
        if(l1.val < l2.val){
            l1.next = mergeTwoLists3(l1.next, l2);
            return l1;
        }
        else{
            l2.next = mergeTwoLists3(l1, l2.next);
            return l2;
        }
    }
```

思路分析：

* 用两个链表中倒数第二大的结点指向最大的结点；再用倒数第三大的结点指向倒数第二大的结点;以此类推最终将两个列表合并。
* 下沉阶段：两条链表当前结点中较小的结点指向下一个结点。
* 回溯阶段：返回值为两条链表中较小的结点。
* 下沉终止条件：也就是递归终止条件，两条链表其一为`null`时，返回另外一条链表当前结点。

运行结果：

* 我并没有提交

### 其它

作图工具 [Processon](https://www.processon.com/)。地址：https://www.processon.com/
