# 删除链表中指定值的结点

[toc]

### 原题

删除链表中等于给定值 **val** 的所有节点。

示例:
输入: 1->2->6->3->4->5->6, val = 6
输出: 1->2->3->4->5

---

### 三种解法

##### 1.递归（我的第一解）

```java
public ListNode removeElements(ListNode head, int val) {
        if (head == null)
            return null;
        if (head.val == val) {
            head.next = removeElements(head.next, val);
            return head.next;
        } else {
            head.next = removeElements(head.next, val);
            return head;
        }
    }
```

思路分析：

* 下沉阶段：当前结点都指向一个已经删除指定值的链表头
* 上浮阶段：如果当前结点值是指定值，就返回其下一个结点，下一个结点已经是删除了指定值的链表头结点；否则当前结点为没有指定值的链表头结点，返回其本身。
* 递归次数为链表长度，时间复杂度为$O(n)$。迭代使用栈内存，空间复杂度为$O(n)$。
* 这个递归的做法和单向有序链表删除重复元素类似。

运行结果：
* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :40.3 MB, 在所有 Java 提交中击败了5.09%的用户

##### 2.循环（我的第二解）

```java
public ListNode removeElements2(ListNode head, int val) {
        while (head != null && head.val == val) // 与删除重复元素不一样的地方！！第一个元素 开头几个元素都是指定值的情况，需要先把这个情况排除
            head = head.next;
        if (head == null)
            return null;
        ListNode temp = head;
        while (temp.next != null) {
            if (temp.next.val != val)
                temp = temp.next;
            else {
                ListNode cursor = temp.next;
                while (cursor.next != null && cursor.next.val == val)
                    cursor = cursor.next;
                temp.next = cursor.next;
            }
        }
        return head;
    }
```

思路分析：

* 可以类似[删除重复元素](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/deleteDuplicates.md)的做法，链接中有图解。使用两个指针，`cursor`定位到指定值的结点的最后一个，防止单个单个结点删除时，连续指定值结点情况下的漏删。
* 这里特别要注意一种情况，如果要删除的元素是开头一个或几个。要处理这种情况，需要先把开头的指定值的结点删除。这里只需要一位一位移动直到开头没有要删除的结点为止。
* 经过上一个步骤，要判断当前指针是否为空。
* 只遍历一次链表，时间复杂度$O(n)$，空间复杂度只是用两个辅助指针结点$O(1)$。

代码解释：

* `while (head != null && head.val == val) head = head.next;`这里删除开头一个或几个元素。
* 8~9，下一个元素不需要删除的情况，直接将`temp`指针移动一位即可。
* 12~13行就是将`cursor`指针移动到连续要删除结点的最后一个结点的过程。删除这一连串的结点`temp.next = cursor.next;`

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户

* 内存消耗 :39.5 MB, 在所有 Java 提交中击败了51.36%的用户

##### 3.使用伪头结点的循环（官方解答）

```java
public ListNode removeElements3(ListNode head, int val){
        if(head == null)
            return null;
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode temp = fakeHead;
        while(temp.next != null){
            if(temp.next.val != val)
                temp = temp.next;
            else {
                ListNode cursor = temp.next;
                while(cursor.next != null && cursor.next.val == val)
                    cursor = cursor.next;
                temp.next = cursor.next;
            }
        }
        return fakeHead.next;
    }
```

思路分析：

* 这是处理“要删除的元素是开头一个或几个”这一特殊情况的另外一种做法，这种做法更简洁且可以用于别的地方。
* 代码主体与解法2一致，注意如何设置伪头及返回值。
* 时间复杂度与空间复杂度与方法2一致。

**注意：当要删除的一个或多个节点位于链表的头部时，事情会变得复杂。可以通过哨兵节点去解决它，哨兵节点广泛应用于树和链表中，如伪头、伪尾、标记等，它们是纯功能的，通常不保存任何数据，其主要目的是使链表标准化，如使链表永不为空、永不无头、简化插入和删除。**

