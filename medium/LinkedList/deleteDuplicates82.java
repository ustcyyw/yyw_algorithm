package medium.LinkedList;

import BaseClass.ListNode;

/**
 * @Time : 2020年2月14日14:27:52
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。
 *
 * 示例 1:
 * 输入: 1->2->3->3->4->4->5
 * 输出: 1->2->5
 *
 * 示例 2:
 * 输入: 1->1->1->2->3
 * 输出: 2->3
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list-ii
 */
public class deleteDuplicates82 {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了98.79%的用户
     * 内存消耗 :44.5 MB, 在所有 Java 提交中击败了5.03%的用户
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode fakeHead = new ListNode(-1);
        fakeHead.next = head;
        ListNode pre = fakeHead;
        while(head != null){
            ListNode cur = head;
            while(cur.next != null && cur.next.val == head.val)
                    cur = cur.next;
            if(head != cur){
                pre.next = cur.next;
                head = pre.next;
            } else {
                head = head.next;
                pre = pre.next;
            }
        }
        return fakeHead.next;
    }
}
