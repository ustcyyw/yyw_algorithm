# 面试题52. 两个链表的第一个公共节点

### 原题

编写一个程序，找到两个单链表相交的起始节点。

示例 1：

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/160_example_1.png)

输入：`intersectVal = 8, listA = [4,1,8,4,5], listB = [5,0,1,8,4,5], skipA = 2, skipB = 3`
输出：Reference of the node with value = 8
输入解释：相交节点的值为 8 （注意，如果两个列表相交则不能为 0）。从各自的表头开始算起，链表 A 为 `[4,1,8,4,5]`，链表 B 为 `[5,0,1,8,4,5]`。在 A 中，相交节点前有 2 个节点；在 B 中，相交节点前有 3 个节点。

示例 2：

![img](https://assets.leetcode-cn.com/aliyun-lc-upload/uploads/2018/12/14/160_example_3.png)

输入：`intersectVal = 0, listA = [2,6,4], listB = [1,5], skipA = 3, skipB = 2`
输出：null
输入解释：从各自的表头开始算起，链表 A 为 `[2,6,4]`，链表 B 为 `[1,5]`。由于这两个链表不相交，所以 intersectVal 必须为 0，而 skipA 和 skipB 可以是任意值。
解释：这两个链表不相交，因此返回 null。

*注意*：

如果两个链表没有交点，返回 null.
在返回结果后，两个链表仍须保持原有的结构。
可假定整个链表结构中没有循环。
程序尽量满足 O(n) 时间复杂度，且仅用 O(1) 内存。

来源：力扣（LeetCode）
链接：https://leetcode-cn.com/problems/intersection-of-two-linked-lists

---

### 两种解法

##### 1.双链指针同时移动，确保同时到链表尾

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lengthA = getLength(headA), lengthB = getLength(headB);
        ListNode a = headA, b = headB;
        if(lengthA > lengthB){
            for(int i = 0; i < lengthA - lengthB; i++)
                a = a.next;
        } else {
            for(int i = 0; i < lengthB - lengthA; i++)
                b = b.next;
        }
        while(a != b){
            a = a.next;
            b = b.next;
        }
        return a;
    }
    private int getLength(ListNode head){
        int length = 0;
        for(ListNode temp = head; temp != null; temp = temp.next, length++);
        return length;
    }
```

思路分析：

* 如果让两指针分别走一步便判断一次，如果是同一结点就是交结点。这样的做法是错误的，原因在于：两条链表不一样长，其到达交点的路程不一样。如何处理这个问题？我让路程长指针先走几个结点，就可以保证两根指针最后同时到达链表尾，也就可以同时到达交结点（如果有的话）
* 所以得先确定哪个指针路程长，让其先走几个结点。辅助函数`int getLength(ListNode head)`用于计数某个链表的长度：通过移动指针`temp`的循环确定链表长度。
* 通过`lengthA`与`lengthB`大小，判断哪个指针先走，先走的指针要走的步数即为`abs(lengthA-lengthB)`。
* "站在同一起跑线后"，就可以指针每移动一次，判断是否走到同一个结点，若是，该结点即为交结点。
    * 对于没有交点的情况，最终a与b会同时成为`null`，然后`while`循环结束，返回`a`也就是`null`。
* 时间复杂度涉及到遍历链表$O(m + n)$，空间复杂度，由于只使用了四个常量（两个`int`，两个`node`）,所以是$O(1)$的。

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户

* 内存消耗 :41.4 MB, 在所有 Java 提交中击败了17.447%的用户

##### 3.双指针法

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;
        ListNode L = headA;
        ListNode R = headB;
        int count=0; //当一个节点达到一个链表的尾部，则从另一个链表继续，同时count++
        while (L != R) {
            L = L.next;
            R = R.next;
            if (L == null) {
                L = headB;
                count ++;
            }
            if (R == null) {
                R = headA;
                count++;
            }
            if (count >2) {
                return null;
            }
        }
        return L;
    }
```

思路分析：

* 其实核心思想跟我的第二解一致，都是保证双链指针最后同时达到终点，只是达成这个结果采取的前期预备工作不一样，所以代码实现不一样。参照方法2
* 时间复杂度与空间复杂度同方法2。

代码解释：

* 这个解法在指针`R`与`L`都指向另外一个链表后，它们已经在“同一起跑线”，所以两个指针同时移动一位，当他们为同一结点时`while`循环结束，返回交结点。
* 当`count=2`时，实际上保证了双链表指针最后同时到达终点。但是如果到达终点都没有指到同一个结点，说明并没有相交结点。此时`R`与`L`都再次指向另外一个链表后`count = 4`，所以回返回`null`。

