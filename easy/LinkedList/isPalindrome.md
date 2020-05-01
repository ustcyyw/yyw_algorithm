# 判断是否为回文链表

### 原题
请判断一个链表是否为回文链表。

示例 1:
输入: 1->2
输出: false

示例 2:
输入: 1->2->2->1
输出: true
进阶：
你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？

[原题链接](https://leetcode-cn.com/problems/palindrome-linked-list/)

---

### 三种解法

##### 1.维持一条翻转链表（我的第一解）

```java
public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;
        ListNode reverseNode = reverse(head);
        while (head != null) {
            if (head.val != reverseNode.val)
                return false;
            head = head.next;
            reverseNode = reverseNode.next;
        }
        return true;
    }

private ListNode reverse(ListNode node) {
        ListNode result = null;
        while (node != null) {
            ListNode temp = new ListNode(node.val);
            temp.next = result;
            result = temp;
            node = node.next;
        }
        return result;
    }
```

思路分析：

* 回文链表具有什么样的性质：从左往右值出现的顺序与从右到左值出现的顺序一致。
* 所以我将整条链表翻转之后，与原链表值出现的顺序一致。
* 得到翻转后的链表，只需要逐个对应结点的值进行比较，如果对应值都相等则说明是回文链表。
* 额外维持了一条翻转链表，空间复杂度$O(n)$。翻转链表时需要遍历一遍链表，在核查对应值时需要遍历两个链表，所以时间复杂度为$O(n)$。

代码分析：

* `private ListNode reverse(ListNode node)`私有函数通过将原链表元素不断插入新链表头得到翻转后的链表头结点。
* 5~10行，逐个对应结点的值进行比较。如果出现对应值不相等的情况直接返回`false`。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了31.34%的用户
* 内存消耗 :40.3 MB, 在所有 Java 提交中击败了97.74%的用

##### 2.维持半条翻转链表（我的第二解）

```java
public boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null)
            return true;

        ListNode slow = head; // 一块一慢两个指针，为了找到需要翻转的半条链表的起点。遍历半个链表
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode reverseHalf = reverse(slow); // 额外维持的半条链表 遍历半个链表
        while (reverseHalf != null) { // 两个半长链表的比较 遍历两个 半长链表
            if (head.val != reverseHalf.val)
                return false;
            reverseHalf = reverseHalf.next;
            head = head.next;
        }
        return true;
    }
```

思路分析：

* 延伸一下回文链表的性质：它关于中垂线是轴对称的，也就是说只要翻转其中一半，则两条"半链表"值出现的顺序是一致的。
* 所以关键变为我如何找到后半条链表的头结点呢？有了之前删除链表倒数第N个元素及判断链表是否成环的铺垫就自然想到可以使用双指针，一快一慢两个指针。快指针一次移动两个结点，慢指针一次移动一个结点。
* 接下来就是画图看看，指针运动完成后的情况。这里不能忽略了分情况讨论，指针有奇数个结点和有偶数个结点是不一样的。
* 由下图可以看到，运动终止条件为`fast != null && fast.next != null`。运动结束的时候，`slow`指针指在后半条链表头结点(链表结点数为偶数时)或者指在链表中间结点(链表结点数为奇数时)。`ListNode reverseHalf = reverse(slow);`得到翻转链表头结点。
* 然后同方法一一样逐个结点值对比。时间复杂度$O(n)$，空间复杂度$O(n/2)$
* 但是由于比较链表的长度变为原来一半，翻转链表的长度变原来一半，比方法一快一些。

图示：

![isPalindrome1.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/isPalindrome1.jpg?raw=true)
代码解释：
* 7~10行，找到链表中间结点，其余同方法一，私有函数reverse见方法一。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了46.90%的用户
* 内存消耗 :41.9 MB, 在所有 Java 提交中击败了24.26%的用户

##### 3.不需要维持翻转链表（标答）

```java
public boolean isPalindrome3(ListNode head) {
        if (head == null || head.next == null)
            return true;

        ListNode slow = head; // 一块一慢两个指针，为了找到需要翻转的半条链表的起点。遍历半个链表
        ListNode fast = head;
        ListNode pre = null; // 用于辅助翻转前半条链表
        while (fast != null && fast.next != null) {
            ListNode cur = slow;
            slow = slow.next;
            fast = fast.next.next;
            cur.next = pre;
            pre = cur;
        }
        if (fast != null) slow = slow.next;
        while (pre != null) { // 两个半长链表的比较 遍历两个 半长链表
            if (pre.val != slow.val)
                return false;
            slow = slow.next;
            pre = pre.next;
        }
        return true;
    }
```

思路分析：

* 回文链表的性质：它关于中垂线是轴对称的，也就是说只要翻转其中一半，则两条"半链表"值出现的顺序是一致的。方法二中将后半链表进行了翻转，额外维持一条新的半链表。官方标答的思路就是，在找中间结点的过程中直接将前半个链表进行翻转。
* 需要注意在链表节点数为奇数时，也就是当遍历结束后`fast != null`的情况，要将`slow`结点跳过中间结点，否则两条半链表不一样长。
* 如何在查找中间结点是翻转链表，看下面图示。时间复杂度$O(n)$，空间复杂度$O(1)$

图示：

![isPalindrome2.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/isPalindrome2.jpg?raw=true)

代码解释：

* 图示的过程就是 8~14行的循环。
* 15行`if (fast != null) slow = slow.next;`用于处理链表节点数为奇数的情况。

----

* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹