# 面试题06. 从尾到头打印链表
### 原题
输入一个链表的头节点，从尾到头反过来返回每个节点的值（用数组返回）。

示例 1：
输入：head = [1,3,2]
输出：[2,3,1]

限制：
0 <= 链表长度 <= 10000

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof)：https://leetcode-cn.com/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof

### 解法
```java
public class reversePrint {
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
```
思路分析：
* 要返回的数据结构是一个数组，数组元素顺序与链表的顺序相反。
* 首先我们得需要知道元素个数才能生成数组，这就免不了得先遍历一次链表。可以使用栈存放结点值，也可以使用List存放结点值，或者直接计数结点个数都行。
* 然后生成数组，比如我使用List，数组与List都通过索引来进行元素访问，要对链表逆序打印：那么对于数组索引为`n - i - 1`的元素对应List中索引为`i`的元素。（索引不好确定的时候举具体的数字就好了，比如List中第2个元素i=1，那么要放在数组的倒数第2个元素n- 1 - 1处）
* 时间复杂度与空间复杂度都是$O(n)$。

运行结果：

* 执行用时 :2 ms, 在所有 Java 提交中击败了59.23%的用户
* 内存消耗 :40.1 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹