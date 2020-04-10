# 61. 旋转链表

### 原题
给定一个链表，旋转链表，将链表每个节点向右移动 k 个位置，其中 k 是非负数。

示例 1:
输入: 1->2->3->4->5->NULL, k = 2
输出: 4->5->1->2->3->NULL
解释:
向右旋转 1 步: 5->1->2->3->4->NULL
向右旋转 2 步: 4->5->1->2->3->NULL

示例 2:
输入: 0->1->2->NULL, k = 4
输出: 2->0->1->NULL
解释:
向右旋转 1 步: 2->0->1->NULL
向右旋转 2 步: 1->2->0->NULL
向右旋转 3 步: 0->1->2->NULL
向右旋转 4 步: 2->0->1->NULL

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/rotate-list)：https://leetcode-cn.com/problems/rotate-list

### 解法

```java
public ListNode rotateRight(ListNode head, int k) {
        if(k == 0 || head == null)
            return head;
        int length = 1;
        ListNode tail = head;
        for (; tail.next != null; tail = tail.next)
            length++;
        int temp = k % length;
        if(temp == 0) return head;
        ListNode pre = head;
        for(int i = 0; i < length - temp - 1; i++)
            pre = pre.next;
        ListNode res = pre.next;
        pre.next = null;
        tail.next = head;
        return res;
    }
```

思路分析：

* 首先，如果`k == 0`意味着不旋转，`head == null`空链表也无法选择。这两种特殊情况的处理方式就是直接将`head`返回。
* 然后可以先以链表 1->2->3->4->5->NULL为例子，画图看一看，不同的k旋转后的结果是什么。如下图：
    * ![rotateRight图示.png](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/LinkedList/rotateRight%E5%9B%BE%E7%A4%BA.png?raw=true)
    * k = 0和5的链表一样，k = 1和6的链表相同，继续画可以发现k = 2 和 7的链表相同。其实旋转是一个循环的过程，刚好以链表的长度为一个周期。
    * 所以只需要处理 k = 1,2,3……length - 1。其中length为链表的长度。（代码8，9两行通过取余数，来将要处理的k化简为旋转`temp`个位置，如果`temp == 0`也不需要旋转）
    * 继续观察这个图，可以发现旋转总是将原链表的尾结点连接到原链表的头结点（第15行），并且要找到旋转后的头结点`res`，然后原链表中`res`的前置结点与`res`的链接断开，返回`res`（第14行，16行）。
    * 这里我们既然又要找到原链表的尾结点，又要知道链表的长度，所以可以使用一个循环，一次完成两个工作。（第5-7行）
* 找规律，哪一个结点是旋转后的头结点。还是以上图为例子：
    * k = 1时，旋转后的头结点是5，从头结点开始移动4次就得到它
    * k = 2时，旋转后的头结点是4，从头结点开始移动3次就得到它
    * k = 3时，旋转后的头结点是3，从头结点开始移动2次就得到它
    * 所以从头结点移动`length - k`就得到了旋转后的头结点
* 但是要断开旋转后的头结点与其前置结点的链接，我们去找其前置节点`pre`，移动`length - temp - 1`次（第11到12行），然后再通过`res = pre.next`就得到了旋转后的头结点。`pre.next = null`断开他们的链接。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.4 MB, 在所有 Java 提交中击败了5.13%的用户