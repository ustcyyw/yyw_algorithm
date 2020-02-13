# 链表的中间结点

### 原题

给定一个带有头结点 head 的非空单链表，返回链表的中间结点。

如果有两个中间结点，则返回第二个中间结点。

示例 1：

输入：`[1,2,3,4,5]`
输出：此列表中的结点 3 (序列化形式：`[3,4,5]`)
返回的结点值为 3 。 (测评系统对该结点序列化表述是` [3,4,5]`)。
注意，我们返回了一个` ListNode` 类型的对象 `ans`，这样：
`ans.val = 3, ans.next.val = 4, ans.next.next.val = 5`, 以及 `ans.next.next.next = NULL.`

示例 2：

输入：`[1,2,3,4,5,6]`
输出：此列表中的结点 4 (序列化形式：`[4,5,6]`)
由于该列表有两个中间结点，值分别为 3 和 4，我们返回第二个结点。


提示：

给定链表的结点数介于 1 和 100 之间。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/middle-of-the-linked-list)：https://leetcode-cn.com/problems/middle-of-the-linked-list

### 解法

##### 1.快慢双指针

```java
public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while(fast != null && fast.next != null){
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
```

思路分析：

* 要找到链表的中间结点，这是一个可以用快慢指针解决的典型问题。
* 慢指针每次移动一个结点，快指针每次移动两个结点。那么，当快指针移动到链表尾时，快指针刚好移动到链表的中间结点。
* 确定了这个大的思路，接下来就要处理细节，处理细节一般都可以举例子看。比如有4个结点与5个结点的情况。见图示，我们可以发现：
    * `fast != null && fast.next != null`时，移动停止，左边的条件对应有偶数个结点的情况，右边的条件对应有奇数个结点的情况。
    * 无论链表中有奇数个结点还是偶数个结点，慢指针`slow`所指的结点即为所求。

图示：

![middleNode.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/LinkedList/middleNode.jpg?raw=true)

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :39.9 MB, 在所有 Java 提交中击败了5.06%的用户
