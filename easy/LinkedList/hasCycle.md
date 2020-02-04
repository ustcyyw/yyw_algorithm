## 判断链表是否有环

### 原题目

给定一个链表，判断链表中是否有环。

为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
示例 1：
输入：head = [3,2,0,-4], pos = 1
输出：true
解释：链表中有一个环，其尾部连接到第二个节点。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/linked-list-cycle

---

### 两种解法

##### 1.使用 HashSet 判断是否重复结点（我的第一解）

```java
public boolean hasCycle(ListNode head){
        Set<ListNode> vals = new HashSet<>();
        while(head != null){
            if(vals.contains(head))
                return true;
            else{
                vals.add(head);
                head = head.next;
            }
        }
        return false;
    }
```

思路分析：

* 用散列表记录走过的结点，如果出现重复则说明走回来了，成环。如果走到链表尾`head == null`也说明没有环。
* 散列表的存储与查询均摊约为$O(1)$。但是本题中散列表需要扩容且散列函数计算要占用很多时间。所以虽然外循环是$O(n)$的，运行耗时也不低。

运行结果：
* 执行用时 :11 ms, 在所有 Java 提交中击败了5.35%的用户
* 内存消耗 :38.1 MB, 在所有 Java 提交中击败了13.83%的用户

##### 2.双指针法or追击（官方标答）

```java
public boolean hasCycle2(ListNode head){
        if(head == null || head.next == null)
            return false;
        ListNode fast = head.next;
        ListNode slow = head;
        while (slow != fast){
            if(fast == null || fast.next == null) // 当快的游标跑到链表尾 说明没有环
                return false;
            fast = fast.next.next; // 只能移动两个结点，否则会跳过慢的游标
            slow = slow.next;
        }
        return true; // 循环结束 意味着快的游标追上慢的游标 存在环
    }
```

思路分析：

* 就像一快一慢两个运动员，如果在直道赛跑，不存在追击问题；如果是在环道赛跑，快的绕了一圈可以追上慢的。
* 让`ListNode fast = head.next;ListNode slow = head;`当两指针下一次指到同一结点时，说明`fast`绕了一圈追上`slow`，成环。
* 当然，如果`fast`已经跑到赛道尾(i.e.`fast == null || fast.next == null`)，说明赛道并没有环。
* 要判断`fast.next == null`的原因是：保证`fast`每次移动两个结点。为什么要移动两个结点而不是三个结点？这样可以保证仅用一圈就能检查出是否有环，但如果让`fast`每次移动三个结点，有可能刚好跨过了`slow`，其实最后结果也不会错，但会慢很多。

运行结果：

* 没有进行提交。

