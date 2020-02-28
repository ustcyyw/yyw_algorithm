# 617.合并二叉树

### 原题
给定两个二叉树，想象当你将它们中的一个覆盖到另一个上时，两个二叉树的一些节点便会重叠。
你需要将他们合并为一个新的二叉树。合并的规则是如果两个节点重叠，那么将他们的值相加作为节点合并后的新值，否则不为 NULL 的节点将直接作为新二叉树的节点。

示例 1:
输入: 
```Tree 1                     Tree 2                  
          1                         2                             
         / \                       / \                            
        3   2                     1   3                        
       /                           \   \                      
      5                             4   7  
```

输出: 
合并后的树:

```
         3
	    / \
	   4   5
	  / \   \ 
	 5   4   7
```

注意: 合并必须从两个树的根节点开始。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/merge-two-binary-trees)：https://leetcode-cn.com/problems/merge-two-binary-trees

### 两种解法

##### 1.递归（我的第一解）

```java
 public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if(t1 == null && t2 == null) return null;
        if(t1 == null) // 其中一个结点为null 直接返回另外一个结点即可完成合并
            return t2;
        if(t2 == null)
            return t1;
        t1.left = mergeTrees(t1.left, t2.left); // 因为会涉及到t1.left为null的情况，这时递归调用会返回t2.left，这个时候主树需要更新结点的链接
        t1.right = mergeTrees(t1.right, t2.right);
        t1.val = t1.val + t2.val;
        return t1;
    }
```

思路分析：

* 归并两棵树，其中不但涉及到结点值的更新，也涉及到链接的更新，我们选定一棵主树，让另外一棵树归并到主树上。
* 递归的下沉阶段在两颗树中不断搜索，上浮阶段不断更新结点左右子链接然后更新结点的值。
* 递归放回值为更新完成的结点，所以更新链接的代码为` t1.left = mergeTrees(t1.left, t2.left);`与`t1.right = mergeTrees(t1.right, t2.right);`，参数的意义显然就是，要归并的结点是两树中位置对应的节点。
* 再看递归结束的条件：
    * `t1 == null && t2 == null`当前位置都是空结点，不需要归并，返回`null`。
    * `t1 == null`，`t2 == null`都意味着一棵树的结点缺失，返回另外一个结点即可。
* 时间复杂度，由于每个结点只访问了一次，所以$O(n)$。空间复杂度，就是函数调用栈的空间，其$O(h)$,h为树的高度。对于平衡二叉树为logn，完全不平衡二叉树则是n。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.1 MB, 在所有 Java 提交中击败了11.18%的用户
##### 2.迭代（我的第二解）

```java
public TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
        if(t1 == null) return t2;
        if(t2 == null) return t1;
        Stack<TreeNode> s1 = new Stack<>();
        Stack<TreeNode> s2 = new Stack<>();
        s1.push(t1);
        s2.push(t2);
        while(!s1.empty()){ // s1 s2出入栈是同步的 所以条件写一个就行
            TreeNode cur1 = s1.pop(); // 栈顶元素即为当前要合并的
            TreeNode cur2 = s2.pop();
            if(cur1.left != null && cur2.left != null){ // 当前节点的左子结点 有四种情况，但是均为null的不用处理。
                s1.push(cur1.left);
                s2.push(cur2.left);
            } else if(cur1.left == null) { // cur1.left == null 的两种情况处理方式使用这行代码均正确。
                cur1.left = cur2.left;
            }
            if(cur1.right != null && cur2.right != null){
                s1.push(cur1.right);
                s2.push(cur2.right);
            } else if(cur1.right == null){
                cur1.right = cur2.right;
            }
            cur1.val = cur1.val + cur2.val;
        }
        return t1;
    }
```

思路分析：

* DFS进行搜索，由于是遍历两棵树，所以需要使用两个栈。
* 归并一定要求结点是对应位置的，所以在结点入栈时一定要注意位置对应。比如我们想将某个位置的两颗树中的左结点入栈，得先保证`cur1.left != null && cur2.left != null`，这样才能位置对应。
* 当选定的主树某位置没有左（右）子节点时，将其左（右）子节点设定为另一棵树的左（右）子节点即可，这部分就不能再接着遍历了。代码14-15行（20-21行）。
* 在处理完入栈或者更新链接后，要更新当前结点的值。`cur1.val = cur1.val + cur2.val;`
* 最坏的情况，栈中的元素个数为N/2，所以空间复杂度$O(N)$；时间复杂度同方法1，遍历了一次所有结点$O(N)$。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了9.69%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了11.01%的用户