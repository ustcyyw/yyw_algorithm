package medium.LinkedList;

/**
 * @Time : 2020年3月8日21:42:54
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.ListNode;

/**
 * 给定一个链表，两两交换其中相邻的节点，并返回交换后的链表。
 * 你不能只是单纯的改变节点内部的值，而是需要实际的进行节点交换。
 *
 * 示例:
 * 给定 1->2->3->4, 你应该返回 2->1->4->3.
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/swap-nodes-in-pairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class swapPairs {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :34.5 MB, 在所有 Java 提交中击败了30.75%的用户
     */
    public ListNode swapPairs(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode fake = new ListNode(-1);
        fake.next = head;
        ListNode pre = fake;
        ListNode cur = head;
        while(true){
            pre.next = cur.next;
            cur.next = cur.next.next;
            pre.next.next = cur;
            if(cur.next == null || cur.next.next == null)
                return fake.next;
            cur = cur.next;
            pre = pre.next.next;
        }
    }
}
