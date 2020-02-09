#  链表奇偶结点聚集

[toc]

### 原题

给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。

**请尝试使用原地算法完成**。你的算法的空间复杂度应为 O(1)，时间复杂度应为 O(nodes)，nodes 为节点总数。

示例 1:

输入: 1->2->3->4->5->NULL
输出: 1->3->5->2->4->NULL
示例 2:

输入: 2->1->3->5->6->4->7->NULL 
输出: 2->3->6->7->1->5->4->NULL
说明:

应当保持奇数节点和偶数节点的相对顺序。
链表的第一个节点视为奇数节点，第二个节点视为偶数节点，以此类推。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/odd-even-linked-list)：https://leetcode-cn.com/problems/odd-even-linked-list

### 两种解法

##### 1.生成一奇一偶链表再连接（我的第一解）

```java
public ListNode oddEvenList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode odd = new ListNode(-1), even = new ListNode(-1); // 为了将元素插入表尾，创建两链表尾。第一个元素的插入需要使用伪头
        ListNode oddHead = odd, evenHead = even;
        int count = 0;
        while(head != null){
            if(count % 2 == 0){
                ListNode temp = new ListNode(head.val);
                odd.next = temp;
                odd = temp;
            } else {
                ListNode temp = new ListNode(head.val);
                even.next = temp;
                even = temp;
            }
            count++;
            head = head.next;
        }
        odd.next = evenHead.next;
        return oddHead.next;
    }
```

思路分析：

* 要将奇数位置的结点与偶数位置的结点聚集，最直观的办法就是分别创建都是奇数结点或偶数结点的两条链表。最后将奇数链表尾连上偶数链表头即可。
* 特殊地，如果链表为空或者只有一个结点，不需要聚合直接返回。
* 单次遍历原链表时，使用变量`count`计算当前是第几个结点，再通过`count % 2`即可判断奇偶。每次将当前结点插入对应链表的表尾即可。
* 在第一个元素插入链表尾时，以及将奇数链表尾连接偶数链表头时，都需要用到伪结点
* 遍历原链表，且每个结点都进行一次 插入链表尾 的操作，所以时间复杂度为$O(n)$；使用了额外的两条新链表，新结点个数为n，所以空间复杂度为$O(n)$。
* 这个算法中涉及多次创建新结点，所以速度会稍慢。

代码分析：

* 4~5行，伪结点创建，两条新链表尾指针的定义。
* 7~18行，遍历原链表，不断将结点插入对应新链表尾。
* 20行，将奇数链表尾连接偶数链表头。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了29.82%的用户
* 内存消耗 :37.6 MB, 在所有 Java 提交中击败了24.22%的用户

##### 2.原地算法（我的第二解）

```java
public ListNode oddEvenList2(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode temp = null, oddTail = head, evenTail = head.next;
        while(evenTail != null && evenTail.next != null){ // 循环条件左边是对应有奇数个结点的链表，右边对应有偶数个结点的链表
            temp = evenTail.next; // 要交换到前面的奇数结点
            evenTail.next = temp.next;
            temp.next = oddTail.next;
            oddTail.next = temp;
            oddTail = oddTail.next; // 奇数偶数结点尾指针移动到尾部
            evenTail = evenTail.next;
        }
        return head;
    }
```

思路分析：

* 采用原地算法，不能生成新结点。则需要在一次遍历当中想办法通过结点`.next`的指向改变，使得结点聚合。
* 要聚合结点，需要至少两个指针指向奇数链表和偶数链表的尾部。在聚合过程中，前一部分是聚合完成的，后一部分是还没聚合的。聚合完成的结点数总是偶数，所以偶数链表尾的下一个结点肯定是原链表的奇数结点。
* 这样就可以确定当前要聚合的两个结点且当前奇数链表与偶数链表的尾结点，可以画图看看怎么调整`.next`完成当前的聚合及移动尾指针。下面看图示吧~
* 还要注意的一个点是，循环终止条件`evenTail != null && evenTail.next != null`分别对应原链表有奇数个结点，有偶数个结点的情况。
* 没有新建结点节约了很多时间，没有额外维持新列表，空间复杂度$O(1)$；单次遍历链表，时间复杂度为$O(n)$。

图示：![oddEvenList.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/LinkedList/oddEvenList.jpg?raw=true)

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :36 MB, 在所有 Java 提交中击败了76.96%的用户

### 补充

对于方法二，实现原地算法时，有一个更简洁的代码。改进的地方是**没有必要再循环过程中总是让奇数链表尾指向偶数链表头，返回之前做一次即可**

```java
		ListNode odd=head;
        ListNode even=head.next;
        ListNode tem=even; 
while(even!=null&&even.next!=null){
            odd.next=even.next;
            odd=odd.next;
            even.next=odd.next;
            even=even.next;
        }
        odd.next=tem;
```

