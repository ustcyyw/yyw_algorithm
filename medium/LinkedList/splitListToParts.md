# 分割链表为k部分

### 原题

给定一个头结点为 root 的链表, 编写一个函数以将链表分隔为 k 个连续的部分。

**每部分的长度应该尽可能的相等: 任意两部分的长度差距不能超过 1**，也就是说可能有些部分为 null。

这k个部分应该按照在链表中出现的顺序进行输出，并且排在**前面的部分的长度应该大于或等于后面的长度**。

返回一个符合上述规则的链表的列表。

举例： `1->2->3->4, k = 5 // 5` 结果 `[ [1], [2], [3], [4], null ]`

示例 1：

输入: 
`root = [1, 2, 3], k = 5`
输出:` [[1],[2],[3],[],[]]`
解释:
输入输出各部分都应该是链表，而不是数组。
例如, 输入的结点 `root` 的` val= 1, root.next.val = 2, \root.next.next.val = 3`, 且 `root.next.next.next = null`。
第一个输出` output[0]` 是 `output[0].val = 1, output[0].next = null`。
最后一个元素 `output[4] 为 null`, 它代表了最后一个部分为空链表。
示例 2：

输入: 
`root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3`
输出:` [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]`
解释:
输入被分成了几个连续的部分，并且每部分的长度相差不超过1.前面部分的长度大于等于后面部分的长度。


提示:

root 的长度范围： [0, 1000].
输入的每个节点的大小范围：[0, 999].
k 的取值范围： [1, 50].

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/split-linked-list-in-parts)：https://leetcode-cn.com/problems/split-linked-list-in-parts

---

### 我的解法

```java
public ListNode[] splitListToParts(ListNode root, int k) {
        if (k == 1)
            return new ListNode[]{root};

        ListNode[] result = new ListNode[k];

        int length = 0;
        for (ListNode temp = root; temp != null; temp = temp.next)
            length++;
        int remainder = length % k, quotient = length / k;

        result[0] = root;
        for (int i = 1; i < remainder + 1; i++) {
            for (int j = 0; j < quotient; j++)
                root = root.next;
            ListNode cur = root;
            root = root.next;
            cur.next = null;
            result[i] = root;
        }
        if (quotient == 0)
            return result;
        for (int i = remainder + 1; i < k; i++) {
            for (int j = 0; j < quotient - 1; j++)
                root = root.next;
            ListNode cur = root;
            root = root.next;
            cur.next = null;
            result[i] = root;
        }
        return result;
    }
```

思路分析：

* 特殊情况，当`k==1`时不需要分组，直接返回结果。
* 分成的子链表任意两个部分不能长度不能超过一，且前面的子链表至少与后面的链表长度相等。意思就是要尽可能的均分，在无法均分时前面的链表多分1个结点，这就需要事先知道链表长度，才能知道每组分多少个元素。
* 均分原则，但是不总能刚好均分，会多出一些结点无法每组都给1个，所以先确定每组至少分几个`quotient = length / k`，`quotient`即表示每组至少分几个。然后确定有多少组能多分一个`remainder = length % k`，`remainder`即表示有几组需要多分1个结点。
* 所以前`remainder`组要分`quotient+1`个结点，剩余组分`quotient`个结点。
* 第一个子链表的头结点即为`root`。之后先将分`quotient+1`个结点的子链表的尾结点找到，其下一个结点即为另外一个子链表的头。既然是分割链表，就需要`cur.next = null;`。然后同理，将分`quotient`个结点的子链表的尾结点找到。（这部分看图示，以原题示例2为例子）
* 注意：如果`quotient==0`，除了分一个结点的子链表，其余都为`null`，不需要分配。
* 算法中维持一个`ListNode[]`，其长度为`k`，所以空间复杂度$O(k)$。第一遍遍历列表得到链表长，第二次遍历链表即能完成分割，时间复杂度为$O(n)$。

图示：![splitListToParts.jpg](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/LinkedList/splitListToParts.jpg?raw=true)

代码分析：

* 7~9：结点个数计算。
* 13~20行：处理前`remainder`组，使其分得`quotient+1`个结点；23-30行，剩余分组的处理。

运行结果：

* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.5 MB, 在所有 Java 提交中击败了5.34%的用户