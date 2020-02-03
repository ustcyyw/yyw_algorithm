package easy.linkedList;

/**
 * @Time : 2020年2月2日17:37:10
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

/**
 * 将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。 
 * 示例：
 * 输入：1->2->4, 1->3->4
 * 输出：1->1->2->3->4->4
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/merge-two-sorted-lists
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class mergeTwoLists {
    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了86.85%的用户
     * 内存消耗 :36.7 MB, 在所有 Java 提交中击败了79.82%的用户
     */
    public ListNode mergeTwoLists1(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val > l2.val) {
            return merge1(l2, l1);
        }
        return merge1(l1, l2);
    }

    private ListNode merge1(ListNode first, ListNode second) {
        ListNode head = new ListNode(first.val);
        ListNode temp = head;
        first = first.next;
        while (true) {
            if (first == null && second == null)
                break;
            else if (first == null) { // 这里要注意 直接接到另一个链表的当前表头即可
                temp.next = second;
                break;
            } else if (second == null) {
                temp.next = first;
                break;
            } else {
                if (first.val <= second.val) {
                    temp.next = new ListNode(first.val);
                    temp = temp.next;
                    first = first.next;
                } else {
                    temp.next = new ListNode(second.val);
                    temp = temp.next;
                    second = second.next;
                }
            }
        }
        return head;
    }

    /**
     * 执行用时 :1 ms, 在所有 Java 提交中击败了86.96%的用户
     * 内存消耗 :40.9 MB, 在所有 Java 提交中击败了5.03%的用户
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val > l2.val) {
            return merge2(l2, l1);
        }
        return merge2(l1, l2);
    }

    private ListNode merge2(ListNode first, ListNode second) {
        ListNode head = first;
        while (second != null) {
            if (first.next == null) {
                first.next = second; // 这里要注意 直接接到另一个链表的当前表头即可
                break;
            } else {
                if (first.next.val >= second.val) {
                    ListNode temp = new ListNode(second.val);
                    second = second.next;
                    temp.next = first.next;
                    first.next = temp;
                }
            }
            first = first.next;
        }

        return head;
    }

    /**
     * 别人的做法 递归
     */
    public ListNode mergeTwoLists3(ListNode l1, ListNode l2) {
        if(l1 == null) // 递归的终止条件 当l1为null 另外一条链表是排号序的，其当前头结点即目前最小的结点
            return l2;
        if(l2 == null)
            return l1;

        // 下沉阶段 当前较小结点指向比其稍大一点的结点。回溯阶段 每次返回较小的结点。
        if(l1.val < l2.val){
            l1.next = mergeTwoLists3(l1.next, l2);
            return l1;
        }
        else{
            l2.next = mergeTwoLists3(l1, l2.next);
            return l2;
        }
    }
}


class ListNode {
    public int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}