package easy.linkedList;

/**
 * @Time : 2020年2月6日19:15:04
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.Helper;
import BaseClass.ListNode;

/**
 * 请判断一个链表是否为回文链表。
 * <p>
 * 示例 1:
 * 输入: 1->2
 * 输出: false
 * <p>
 * 示例 2:
 * 输入: 1->2->2->1
 * 输出: true
 * 进阶：
 * 你能否用 O(n) 时间复杂度和 O(1) 空间复杂度解决此题？
 */
public class isPalindrome {
    /**
     * 执行用时 :3 ms, 在所有 Java 提交中击败了31.34%的用户
     * 内存消耗 :40.3 MB, 在所有 Java 提交中击败了97.74%的用户
     * <p>
     * 额外维持了一条等长链表 所以空间复杂度 n， 时间复杂度 有两次遍历链表 也是 n
     */
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;
        ListNode reverseNode = reverse(head);
        while (head != null) {
            if (head.val != reverseNode.val)
                return false;
            head = head.next;
            reverseNode = reverseNode.next;
        }
        return true;
    }

    private ListNode reverse(ListNode node) {
        ListNode result = null;
        while (node != null) {
            ListNode temp = new ListNode(node.val);
            temp.next = result;
            result = temp;
            node = node.next;
        }
        return result;
    }

    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了46.90%的用户
     * 内存消耗 :41.9 MB, 在所有 Java 提交中击败了24.26%的用户
     * <p>
     * 额外维持半条链表
     */
    public boolean isPalindrome2(ListNode head) {
        if (head == null || head.next == null)
            return true;

        ListNode slow = head; // 一块一慢两个指针，为了找到需要翻转的半条链表的起点。遍历半个链表
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode reverseHalf = reverse(slow); // 额外维持的半条链表 遍历半个链表
        while (reverseHalf != null) { // 两个半长链表的比较 遍历两个 半长链表
            if (head.val != reverseHalf.val)
                return false;
            reverseHalf = reverseHalf.next;
            head = head.next;
        }
        return true;
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了99.67%的用户
     * 内存消耗 :41.1 MB, 在所有 Java 提交中击败了53.48%的用户
     */
    public boolean isPalindrome3(ListNode head) {
        if (head == null || head.next == null)
            return true;

        ListNode slow = head; // 一块一慢两个指针，为了找到需要翻转的半条链表的起点。遍历半个链表
        ListNode fast = head;
        ListNode pre = null; // 用于辅助翻转前半条链表
        while (fast != null && fast.next != null) {
            ListNode cur = slow;
            slow = slow.next;
            fast = fast.next.next;
            cur.next = pre;
            pre = cur;
        }
        if (fast != null) slow = slow.next;
        while (pre != null) { // 两个半长链表的比较 遍历两个 半长链表
            if (pre.val != slow.val)
                return false;
            slow = slow.next;
            pre = pre.next;
        }
        return true;
    }

    public static void main(String[] args) {
        int[] test = {1, 1, 2, 1};
        ListNode head = Helper.makeLinkedList(test);
        isPalindrome ispalindrome = new isPalindrome();
        System.out.println(ispalindrome.isPalindrome(head));
    }
}
