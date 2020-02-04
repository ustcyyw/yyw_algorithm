package easy.linkedList;

/**
 * @Time : 2020年2月3日16:16:31
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个链表，判断链表中是否有环。
 *
 * 为了表示给定链表中的环，我们使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。 如果 pos 是 -1，则在该链表中没有环。
 * 示例 1：
 * 输入：head = [3,2,0,-4], pos = 1
 * 输出：true
 * 解释：链表中有一个环，其尾部连接到第二个节点。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/linked-list-cycle
 */
public class hasCycle {
    /**
     * 执行用时 :11 ms, 在所有 Java 提交中击败了5.35%的用户
     * 内存消耗 :38.1 MB, 在所有 Java 提交中击败了13.83%的用户
     */
    public boolean hasCycle(ListNode head){
        Set<ListNode> vals = new HashSet<>();
        while(head != null){
            if(vals.contains(head))
                return true;
            else{
                vals.add(head);
                head = head.next;
            }
        }
        return false;
    }

    /**
     * 标答 一个追击问题
     */
    public boolean hasCycle2(ListNode head){
        if(head == null || head.next == null)
            return false;
        ListNode fast = head.next;
        ListNode slow = head;
        while (slow != fast){
            if(fast == null || fast.next == null) // 当快的游标跑到链表尾 说明没有环
                return false;
            fast = fast.next.next; // 只能移动两个结点，否则会跳过慢的游标
            slow = slow.next;
        }
        return true; // 循环结束 意味着快的游标追上慢的游标 存在换
    }
}
