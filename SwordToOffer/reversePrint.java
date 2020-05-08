package SwordToOffer;

/**
 * @Time : 2020年4月14日00:37:36
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc : 已总结
 */

import BaseClass.ListNode;

import java.util.ArrayList;
import java.util.List;

/**
 *输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。
 *
 * 示例 1：
 * 输入：head = [1,3,2]
 * 输出：[2,3,1]
 *
 * 限制：
 * 0 <= 链表长度 <= 10000
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof
 */
public class reversePrint {
    /**
     * 执行用时 :2 ms, 在所有 Java 提交中击败了59.23%的用户
     * 内存消耗 :40.1 MB, 在所有 Java 提交中击败了100.00%的用户
     */
    public int[] reversePrint(ListNode head) {
        if(head == null)
            return new int[0];
        List<Integer> temp = new ArrayList<>();
        while(head != null){
            temp.add(head.val);
            head = head.next;
        }
        int n = temp.size();
        int[] res =new int[n];
        for(int i = 0; i < n; i++){
            res[n - i - 1] = temp.get(i);
        }
        return res;
    }
}
