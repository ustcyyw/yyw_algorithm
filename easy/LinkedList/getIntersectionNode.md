## 找到两条链表交点结点

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

### 三种解法

##### 1.HashSet 记录走过的结点（我的第一解）

```java
public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        HashSet<ListNode> nodes = new HashSet<>();
        while (headA != null || headB != null) {
            if (headA != null) {
                if (!nodes.contains(headA)) {
                    nodes.add(headA);
                    headA = headA.next;
                } else return headA;
            } else {
                if (!nodes.contains(headB)) {
                    nodes.add(headB);
                    headB = headB.next;
                } else return headB;
            }
        }
        return null;
    }
```

思路分析：

* 特殊地：两个链表中有`null`时肯定没有交结点。
* 使用一个 HashSet 来记录两链表指针走过的结点。两链表指针同时开始移动，每次均移动一个结点。如果哈希表中存在了某个结点，说明这是第二次走到此结点，即为交叉结点；如果不存在某个结点，将其放入哈希表。
* 如果两指针均走到尾结点依旧没有走过相同结点，说明没有交结点。
* 时间复杂度$O(m + n)$，空间复杂度$O(m + n)$

代码解释:

* 要注意尾结点的处理，所以在`while (headA != null || headB != null)`循环内部还需要此类判断`headA != null`

运行结果：

* 执行用时 :15 ms, 在所有 Java 提交中击败了9.82%的用户
* 内存消耗 :43.2 MB, 在所有 Java 提交中击败了14.09%的用户

##### 2.双链指针同时移动，确保同时到链表尾（我的第二解）

```java
public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;
        int lengthA = 0, lengthB = 0;
        ListNode tempA = headA, tempB = headB; // 为了保持原表结果 需要temp
        while (tempA.next != null) { // 这里保证了temp 最后都是该链的最后一个结点
            lengthA++;
            tempA = tempA.next;
        }
        while (tempB.next != null) {
            lengthB++;
            tempB = tempB.next;
        }
        if (tempA != tempB) // 如果两个链表有交叉点 他们的链表尾一定是同一个结点
            return null;
        tempA = headA; // temp 复位
        tempB = headB;
        if (lengthA > lengthB) { // A链更长 所以让tempA先移动 确保移动之后两个temp在同一个起跑线
            for (int i = 0; i < lengthA - lengthB; i++)
                tempA = tempA.next;
        } else {
            for (int i = 0; i < lengthB - lengthA; i++)
                tempB = tempB.next;
        }
        while (true) {
            if (tempA == tempB)
                return tempA;
            tempA = tempA.next;
            tempB = tempB.next;
        }
    }
```

思路分析：

* 不能让两指针分别走一步便判断一次，如果是同一结点就是交结点。原因在于，两条链表不一样长，其到达交点的路程不一样。如何处理这个问题？我让路程长指针先走几个结点，就可以保证两根指针最后同时到达链表尾，也就可以同时到达交结点（如果有的话）
* 所以得先确定哪个指针路程长，让其先走几个结点。通过移动指针`temp`的循环确定`lengthA/B`，就可以确定。
* 如果两个链表有交叉点 他们的链表尾一定是同一个结点，所以在确定`lengthA/B`循环结束后先判断是否有交点，这样在没有交点的情况可以节约时间。
* 通过`lengthA`与`lengthB`大小，判断哪个指针先走，先走的指针要走的结点个数即为`abs(lengthA-lengthB)`。
* "站在同一起跑线后"，就可以指针每移动一次，判断是否走到同一个结点，若是，该结点即为交结点。
* 时间复杂度涉及到遍历链表$O(m + n)$，空间复杂度，由于只使用了四个常量（两个`int`，两个`node`）,所以是$O(1)$的。

代码分析：

* 6~13行：计算``lengthA/B``

* 16~24行：使其站在同一起跑线
* 25~30行：找出相交结点

运行结果：

* 执行用时 :1 ms, 在所有 Java 提交中击败了100.00%的用户

* 内存消耗 :41.4 MB, 在所有 Java 提交中击败了17.447%的用户

##### 3.双指针法（官方标答）

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

