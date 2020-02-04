## 删除有序列表中重复元素

[toc]

### 原题目

给定一个**排序**链表，删除所有重复的元素，使得每个元素只出现一次。

示例 1:
输入: 1->1->2
输出: 1->2
示例 2:
输入: 1->1->2->3->3
输出: 1->2->3

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list)：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list

***

### 两种解法

##### 1.顺序跳过重复元素（我的第一解）

```java
public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return null;
        ListNode temp = head;
        while (temp.next != null) { // 注意尾结点的处理
            if (temp.next.val != temp.val)
                temp = temp.next;
            else {
                ListNode cursor = temp.next; // 外循环条件保证cursor非null
                while (cursor.next != null && cursor.next.val == cursor.val) // cursor 经过循环成为相同元素的最后一个结点
                    cursor = cursor.next;
                temp.next = cursor.next;
            }
        }
        return head;
    }
```

思路分析：

* 当指针`temp`所指当前结点的值与下一节点值不同时，下一个结点保留，指针移位到下一节点。
* 当指针`temp`所指当前结点的值与下一节点值相同时，如果直接跳过下一个结点，可能指针指到的结点值还是相同的，这样就造成错误。如何跳过所有相同值，就需要另外一个指针`cursor`指到具有相同值的最后一个结点。让`temp`指到`cursor`下一个结点，从而跳过所有重复值。
* `cursor`在移动时，只要下一节点存在且值相同则移位。
* 要使用`.next`，就必须注意非`null`的判断。

图示：

![deleteDuplicates.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/deleteDuplicates.jpg?raw=true)

运行结果:

* 执行用时 :1 ms, 在所有 Java 提交中击败了98.64%的用户 
* 内存消耗 :36.8 MB, 在所有 Java 提交中击败了34.58%的用户

##### 2.递归（我的第二解）

```java
public ListNode deleteDuplicates2(ListNode head){
        if (head == null || head.next == null)
            return head;
        if(head.next.val != head.val){
            head.next = deleteDuplicates(head.next);
            return head;
        }
        else {
            head.next = deleteDuplicates(head.next);
            return head.next;
        }
    }
```

思路分析：

* 原理与图示都和第一个解法一致。
* 函数的返回值为没有重复元素的排序列表的头结点。所以终止条件显然是该结点为空或者该结点没有下一个结点`head == null || head.next == null`
* 下沉阶段：当前结点指向下一个与其值不同的结点。
* 上浮阶段：如果当前结点与下一个结点值相同，返回下一个结点；否则返回当前结点。从这里可以很明显看出，每次返回值都是没有重复元素的排序列表的头结点。
* 相比解法一 节约了使用`cursor`的内存，不过递归调用中函数调用也要占用栈内存。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了98.64%的用户
* 内存消耗 :36 MB, 在所有 Java 提交中击败了98.48%的用户

----

##### 其它

作图工具 [Processon](https://www.processon.com/)。地址：https://www.processon.com/