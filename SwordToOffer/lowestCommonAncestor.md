# 235. 二叉搜索树的最近公共祖先
### 原题
给定一个二叉搜索树, 找到该树中两个指定节点的最近公共祖先。

百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

例如，给定如下二叉搜索树:  root = [6,2,8,0,4,7,9,null,null,3,5] 

示例 1:
输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 8
输出: 6 
解释: 节点 2 和节点 8 的最近公共祖先是 6。

示例 2:
输入: root = [6,2,8,0,4,7,9,null,null,3,5], p = 2, q = 4
输出: 2
解释: 节点 2 和节点 4 的最近公共祖先是 2, 因为根据定义最近公共祖先节点可以为节点本身。


说明:
所有节点的值都是唯一的。
p、q 为不同节点且均存在于给定的二叉搜索树中。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree)：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-search-tree

### 3种解法
##### 1.借鉴普通二叉树的做法（我的第一解）
```java
private TreeNode res;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        res = null;
        if(q.val > p.val)
            contains(root, p, q);
        else
            contains(root, q, p);
        return res;
    }

    private boolean contains(TreeNode x, TreeNode p, TreeNode q){
        if(x == null) return false;
        if(x.val < p.val)
            return contains(x.right, p, q);
        if(x.val > q.val)
            return contains(x.left, p, q);
        boolean leftCon = contains(x.left, p, q);
        boolean rightCon = contains(x.right, p, q);
        if((x == p && rightCon) || (x == q && leftCon) || (leftCon && rightCon)) {
            res = x;
            return true;
        }
        return x == p || x == q || leftCon || rightCon;
    }
```
思路分析：
* 首先[236. 二叉树的最近公共祖先](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Tree/lowestCommonAncestor.md)这一题中已经对普通二叉树进行了解答，搜索二叉树是特殊的二叉树，也适用于使用这个方法来解答。下面用类似的思路来解答。
* 给定结点`p,q`（假设`p.val < q.val`），对于结点`x`，x为`p,q`的最近公共祖先结点，有且仅有下面三种情况：
    * x为q且x的左子树种包含p
    * x为p且x的右子树种包含q
    * x的左子树和右子树分别包含p，q。
* 为什么只有这三种情况，可以逆向来想，如果x不是p，q的最近公共祖先结点，有两个含义：
    * x不是公共祖先节点，以x为根的树不同时包含p与q。
    * x是公共祖先节点，但不是最近的，那么x的左右子树只有其中之一同时包含p，q。
    * 除了这两种情况，就剩余上述所说的三种情况。
* 所以我们需要判断以某个结点为根的树是否含有p，q之一，定义函数`boolean contains(TreeNode x, TreeNode p, TreeNode q)`（规定`p.val < q.val`），但在函数内部我们还需要判断结点x是否是最近公共祖先结点。
    * 递归结束条件，`x == null`，显然空树不含p，q，返回`false`
    * 如果`x.val < p.val`，x的值比p，q的值都小，那么以x为根的树，是否包含p，q之一，就需要去其右子树种查找`return contains(x.right, p, q);`。如果`x.val > q.val`，同理`return contains(x.left, p, q);`
    * 递归调用`boolean leftCon = contains(x.left, p, q); `得到左子树是否含有p。`boolean rightCon = contains(x.right, p, q);`得到右子树是否含有q。
    * 如果满足一开始说的五种情况`(x == p && rightCon) || (x == q && leftCon) || (leftCon && rightCon)`，则找到了最近公共子节点`res = x;`，并且以x为根的树总包含p，q，返回`true`。
    * 否则，如果x为q或者p，或者左右子树只有其一含有p，q，返回true。否则返回false。` return x == p || x == q || leftCon || rightCon;`。
    * 注意如果`res`被赋值过一次，然后返回结果，p与q都会在包含这棵树的子树中，其余结点的递归调用中`leftCon`与`rightCon`只会有一个为true，并且其余结点不会等于q或者q。所以遍历每个结点的过程中，`res`的赋值只会有一次，就是要找的最近公共祖先结点。
* 所以主函数只需要将`res`赋值为`null`，然后根据p，q的值调用`contains()`函数，保证第二个参数是p，q中较小的结点，然后返回`res`即可。
* 时间复杂度为$O(n)$，空间复杂度与树高成正比。

运行结果：
* 执行用时 :7 ms, 在所有 Java 提交中击败了78.21%的用户
* 内存消耗 :41.9 MB, 在所有 Java 提交中击败了5.02%的用户

##### 2.利用BST的有序性
```java
public TreeNode lowestCommonAncestor3(TreeNode root, TreeNode p, TreeNode q) {
        if(p.val > q.val) return find(root, q, p);
        else return find(root, p, q);
    }

    private TreeNode find(TreeNode x, TreeNode p, TreeNode q){
        if(x == p || x == q) return x;
        if(p.val < x.val && q.val > x.val) return x;
        else if(q.val < x.val) return find(x.left, p, q);
        return find(x.right, p ,q);
    }
```
思路分析：
* 这个方法充分利用搜索二叉树的有序性质。
* 函数`TreeNode find(TreeNode x, TreeNode p, TreeNode q)`找到以x为根的子树中p与q的最近公共祖先结点。规定，`p.val < q.val`。
    * 如果x等于p或者q，根据题目设定，p与q一定有公共祖先节点，那么显然此时x就是最近公共祖先结点。
    * 如果`p.val < x.val && q.val > x.val`，p在x的左子树，q在x的右子树。那么x的子结点不可能同时包含p与q，所以x就是最近公共祖先结点。
    * 如果`q.val < x.val`，说明p，q都在x的左子树中，x是公共祖先结点，但不是最近的，所以去x的左子树查找最近的`return find(x.left, p, q)`。
    * 否则，就是对应着`q.val > x.val`的情况，说明p，q都在x的右子树中所以去x的左子树查找最近的`return find(x.right, p, q)`。
* 主函数中，注意规定`p.val < q.val`。所以当`p.val > q.val`，返回`find(root, q, p)`；否则返回` find(root, p, q);`
* 时间复杂度为$O(n)$，对结点进行一次遍历。空间复杂度与树高成正比。

运行结果：
* 执行用时 :6 ms, 在所有 Java 提交中击败了100.00%的用户
* 内存消耗 :41.1 MB, 在所有 Java 提交中击败了100.00%的用户

##### 3.官方标答
```java
public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if(root.val > p.val && root.val > q.val)
            return lowestCommonAncestor2(root.left, p, q);
        if(root.val <p.val && root.val < q.val)
            return lowestCommonAncestor2(root.right, p, q);
        return root;
    }
```
思路分析：
* 与方法2一致，但是这个解答写的更简洁~

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹