# 101. 对称二叉树

### 原题
给定一个二叉树，检查它是否是镜像对称的。

例如，二叉树 [1,2,2,3,4,4,3] 是对称的。

```
	1
   / \
  2   2
 / \ / \
3  4 4  3
```

但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:

```
	1
   / \
  2   2
   \   \
   3    3
```


进阶：
你可以运用递归和迭代两种方法解决这个问题吗？

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/symmetric-tree)：https://leetcode-cn.com/problems/symmetric-tree

### 两种解法

##### 1.递归

```java
public boolean isSymmetric(TreeNode root) {
        if(root == null) return true;
        return isSame(invert(root.left), root.right);
    }

    private TreeNode invert(TreeNode x){
        if(x == null) return null;
        TreeNode right = invert(x.right);
        x.right = invert(x.left);
        x.left = right;
        return x;
    }

    private boolean isSame(TreeNode p, TreeNode q){
        if(p == null && q == null) return true;
        if(p == null || q == null) return false;
        if(p.val != q.val) return false;
        else return isSame(p.left, q.left) && isSame(p.right, q.right);
    }
```

思路分析：

* 树镜像对称：也就是关于树的中轴线翻转之后，与原来的树相同。翻转一颗树，其根结点没有翻转，所以判断一棵树是否镜像对称，可以将其左子树翻转然后判断翻转后的左子树与右子树是否相同（当然也可以判断翻转后的右子树与左子树是否相同，开心就好😜）。
* 如何翻转一棵树，看我仓库里的题解[226. 翻转二叉树](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/Tree/invertTree.md)。这里再复述一下递归地翻转树的方法，辅助函数`TreeNode invert(TreeNode x)`
    * 递归结束的条件为`root == null`
    * 如果当前结点不为`null`，那么先将其左右子树进行翻转，然后交换左右子树。
    * 返回值为完成了翻转后的当前结点。
* 如何判断两棵树是否相同， 可以参考仓库的题解[100. 相同的树](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/Tree/isSameTree100.md)，需要判断树相同的相似题目还有仓库的题解[572. 另一个树的子树](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/Tree/isSubtree.md)。这里再复述一下递归的做法，辅助函数`boolean isSame(TreeNode p, TreeNode q)`
    * 根节点值相同，且左右子树相同才能判断一颗树相同。这就是递归的关系。
    * 递归结束的条件，当两颗树均是`null`时他们相等，当两颗树其中之一为`null`另外一颗非`null`时他们不相等。
    * 其它情况下，先判断当前节点的值是否相等，如果相等再判断当前节点的左右子树是否均相同
* 回到主函数，如果`root == null`，空树显然是满足镜像对称的。否则，翻转其左子树，判断翻转后的左子树与右子树是否相同，所以返回的结果为`return isSame(invert(root.left), root.right);`
* 时间复杂度为$O(n)$，因为是对结点进行了两次遍历，空间复杂度与树高成正比。

运行结果：
* 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :37.6 MB, 在所有 Java 提交中击败了29.11%的用户

##### 2.迭代

```java
public boolean isSymmetric2(TreeNode root) {
        if(root == null) return true;
        Stack<TreeNode> left = new Stack<>();
        Stack<TreeNode> right = new Stack<>();
        left.add(root.left);
        right.add(root.right);
        while(!left.isEmpty()){
            TreeNode tempL = left.pop();
            TreeNode tempR = right.pop();
            if(tempL != null && tempR != null){
                if(tempL.val != tempR.val)
                    return false;
                left.add(tempL.right);
                left.add(tempL.left);
                right.add(tempR.left);
                right.add(tempR.right);
            }
            if((tempL == null && tempR != null) || (tempL != null && tempR == null))
                return false;
        }
        return true;
    }
```

思路分析：

* 树镜像对称：换一种说法就是，关于中轴线位置对称的位置，要么都没有结点，要么结点成对存在且值相等。根结点不必判断，所以判断是从根结点的左右子树开始。
* 迭代就需要用栈或者队列来对结点进行遍历。如何进行关于中轴线对称位置的结点对比：
    * 比如树的每一层最左边位置结点与最右边位置结点进行对比；左边第二个位置结点与右边倒数第二个位置结点进行对比……。只使用一个栈或者队列的遍历无法做到，因为一次弹出的两个结点是水平相邻的。
    * 所以想到使用两个栈和队列，其中一个存放左子树的结点`Stack<TreeNode> left`，另外一个存放右子树的结点`Stack<TreeNode> right`。
    * 元素添加的顺序也很重要，如果左子树结点的添加顺序是先右子结点再左子结点，为了保证关于中轴线对称，右子树结点的添加顺序是先左子结点再右子结点。
* 然后就是考虑遍历过程中，对应位置结点的对比。每一次从`left, right`各弹出一个结点：
    * 如果两个结点都不为空，但是值不相等就说明这棵树不镜像对称，返回`false`，否则继续进行遍历，如上述方式进行添加子结点：左子树先右后左`left.add(tempL.right); left.add(tempL.left);`，右子树反之。
    * 如果两个结点有一个为空，一个不为空，也说明这棵树不镜像对称，返回`false`。
    * 弹出的两个结点都为空，就继续循环遍历即可。
* 当遍历结束没有发现镜像不对称的结点，就说明这棵树满足镜像对称。
* 时间复杂度为$O(n)$，因为对所有结点进行了遍历。

运行结果：
* 执行用时 :2 ms, 在所有 Java 提交中击败了10.60%的用户
* 内存消耗 :38 MB, 在所有 Java 提交中击败了15.86%的用户