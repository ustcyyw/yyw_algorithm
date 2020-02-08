package medium.LinkedList;

import BaseClass.ListNode;

/**
 * @Time : 2020年2月7日20:06:56
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个头结点为 root 的链表, 编写一个函数以将链表分隔为 k 个连续的部分。
 * 每部分的长度应该尽可能的相等: 任意两部分的长度差距不能超过 1，也就是说可能有些部分为 null。
 * 这k个部分应该按照在链表中出现的顺序进行输出，并且排在前面的部分的长度应该大于或等于后面的长度。
 * 返回一个符合上述规则的链表的列表。
 *
 * 举例： 1->2->3->4, k = 5 // 5 结果 [ [1], [2], [3], [4], null ]
 *
 * 示例 1：
 * 输入:
 * root = [1, 2, 3], k = 5
 * 输出: [[1],[2],[3],[],[]]
 * 解释:
 * 输入输出各部分都应该是链表，而不是数组。
 * 例如, 输入的结点 root 的 val= 1, root.next.val = 2, \root.next.next.val = 3, 且 root.next.next.next = null。
 * 第一个输出 output[0] 是 output[0].val = 1, output[0].next = null。
 * 最后一个元素 output[4] 为 null, 它代表了最后一个部分为空链表。
 * 示例 2：
 *
 * 输入:
 * root = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10], k = 3
 * 输出: [[1, 2, 3, 4], [5, 6, 7], [8, 9, 10]]
 * 解释:
 * 输入被分成了几个连续的部分，并且每部分的长度相差不超过1.前面部分的长度大于等于后面部分的长度。
 *  
 * 提示:
 * <p>
 * root 的长度范围： [0, 1000].
 * 输入的每个节点的大小范围：[0, 999].
 * k 的取值范围： [1, 50].
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/split-linked-list-in-parts
 */
public class splitListToParts {
    /**
     *执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     *内存消耗 :37.5 MB, 在所有 Java 提交中击败了5.34%的用户
     */
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
}
