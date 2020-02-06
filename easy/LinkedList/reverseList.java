package easy.linkedList;

/**
 * @Time : 2020年2月5日21:03:22
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.Stack;

/**
 * 反转一个单链表。
 *
 * 示例:
 *
 * 输入: 1->2->3->4->5->NULL
 * 输出: 5->4->3->2->1->NULL
 * 进阶:
 * 你可以迭代或递归地反转链表。你能否用两种方法解决这道题？
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/reverse-linked-list
 */
public class reverseList {
    /**
     *执行用时 :
     * 0 ms, 在所有 Java 提交中击败了100%的用户
     * 内存消耗 :37.1 MB, 在所有 Java 提交中击败了30.96%的用户
     */
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode reverseHead = null;
        while(head != null){
            ListNode temp = new ListNode(head.val);
            temp.next = reverseHead;
            reverseHead = temp;
            head = head.next;
        }
        return reverseHead;
    }

    /**
     *递归的做法
     */
    public ListNode reverseList2(ListNode head) {
        if(head == null || head.next == null)
            return head;
        ListNode tail = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return tail;
    }
}
