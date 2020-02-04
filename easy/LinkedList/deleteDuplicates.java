package easy.linkedList;

/**
 * @Time : 2020年2月3日15:11:04
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 * <p>
 * 示例 1:
 * 输入: 1->1->2
 * 输出: 1->2
 * 示例 2:
 * 输入: 1->1->2->3->3
 * 输出: 1->2->3
 * <p>
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/remove-duplicates-from-sorted-list
 */
public class deleteDuplicates {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了98.64%的用户
     * 内存消耗 :36.8 MB, 在所有 Java 提交中击败了34.58%的用户
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return null;
        ListNode temp = head;
        while (temp.next != null) { // 注意尾结点的处理
            if (temp.next.val != temp.val)
                temp = temp.next;
            else {
                ListNode cursor = temp.next; // 外循环条件保证cursor非null
                while (cursor.next != null && cursor.next.val == cursor.val) // cursor 经过循环成为相同元素的最后一个结点
                    cursor = cursor.next;
                temp.next = cursor.next;
            }
        }
        return head;
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了98.64%的用户
     * 内存消耗 :36 MB, 在所有 Java 提交中击败了98.48%的用户
     * @param head
     * @return
     */
    public ListNode deleteDuplicates2(ListNode head){
        if (head == null || head.next == null)
            return head;
        if(head.next.val != head.val){
            head.next = deleteDuplicates(head.next);
            return head;
        }
        else {
            head.next = deleteDuplicates(head.next);
            return head.next;
        }
    }
}
