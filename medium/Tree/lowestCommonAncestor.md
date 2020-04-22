# 236. 二叉树的最近公共祖先
### 原题
给定一个二叉树, 找到该树中两个指定节点的最近公共祖先。

百度百科中最近公共祖先的定义为：“对于有根树 T 的两个结点 p、q，最近公共祖先表示为一个结点 x，满足 x 是 p、q 的祖先且 x 的深度尽可能大（一个节点也可以是它自己的祖先）。”

例如，给定如下二叉树:  root = [3,5,1,6,2,0,8,null,null,7,4] 

示例 1:
输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 1
输出: 3
解释: 节点 5 和节点 1 的最近公共祖先是节点 3。

示例 2:
输入: root = [3,5,1,6,2,0,8,null,null,7,4], p = 5, q = 4
输出: 5
解释: 节点 5 和节点 4 的最近公共祖先是节点 5。因为根据定义最近公共祖先节点可以为节点本身。

说明:
所有节点的值都是唯一的。
p、q 为不同节点且均存在于给定的二叉树中。

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree)：https://leetcode-cn.com/problems/lowest-common-ancestor-of-a-binary-tree

### 2种解法
##### 1.递归
```java
private TreeNode res;
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        res = null;
        contains(root, p, q);
        return res;
    }

    private boolean contains(TreeNode x, TreeNode p, TreeNode q){
        if(x == null) return false;
        boolean leftCon = contains(x.left, p, q);
        boolean rightCon = contains(x.right, p, q);
        if((x == p && rightCon) || (x == q && leftCon) || (x == p && leftCon)|| (x == q && rightCon) ||(leftCon && rightCon)) {
            res = x;
            return true;
        }
        return x == p || x == q || leftCon || rightCon;
    }
```
思路分析：
* 给定结点`p,q`，对于结点`x`，x为`p,q`的最近公共祖先结点，有且仅有下面五种情况：
    * x为q且x的左子树种包含p
    * x为q且x的右子树种包含p
    * x为p且x的左子树种包含q
    * x为p且x的右子树种包含q
    * x的左子树和右子树分别包含p，q。
* 为什么只有这五种情况，可以逆向来想，如果x不是p，q的最近公共祖先结点，有两个含义：
    * x不是公共祖先节点，以x为根的树不同时包含p与q。
    * x是公共祖先节点，但不是最近的，那么x的左右子树只有其一包含了p，q。
    * 除了这两种情况，就剩余上述所说的五种情况。
* 所以我们需要判断以某个结点为根的树是否含有p，q之一，定义函数`boolean contains(TreeNode x, TreeNode p, TreeNode q)`，但在函数内部我们还需要判断结点x是否是最近公共祖先结点。
    * 递归结束条件，`x == null`，显然空树不含p，q，返回`false`
    * 递归调用`boolean leftCon = contains(x.left, p, q); `得到左子树是否含有p或者q。`boolean rightCon = contains(x.right, p, q);`得到右子树是否含有p或者q。
    * 如果满足一开始说的五种情况`(x == p && rightCon) || (x == q && leftCon) || (x == p && leftCon)|| (x == q && rightCon) ||(leftCon && rightCon)`，则找到了最近公共子节点`res = x;`，并且以x为根的树总包含p，q，返回`true`。
    * 否则，如果x为q或者p，或者左右子树只有其一含有p，q，返回true。否则返回false。` return x == p || x == q || leftCon || rightCon;`。
    * 注意如果`res`被赋值过一次，然后返回结果，p与q都会在包含这棵树的子树中，其余结点的递归调用中`leftCon`与`rightCon`只会有一个为true，并且其余结点不会等于q或者q。所以遍历每个结点的过程中，`res`的赋值只会有一次，就是要找的最近公共祖先结点。
* 所以主函数只需要将`res`赋值为`null`，然后再调用`contains(root, p, q)`，然后返回`res`即可。
* 时间复杂度为$O(n)$，空间复杂度与树高成正比。

运行结果：
* 执行用时 :8 ms, 在所有 Java 提交中击败了99.70%的用户
* 内存消耗 :42 MB, 在所有 Java 提交中击败了5.02%的用户

##### 2.记忆化搜索
（PS：这是隔了很久再来做时没回想起第一种方法，重新想的，最优解是第一种做法）

```java
Map<TreeNode, Integer> map;

    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        map = new HashMap<>();
        return helper(root, p, q);
    }

    private TreeNode helper(TreeNode root, TreeNode p, TreeNode q){
        if (contains(root.left, p.val, q.val) == 2)
            return helper(root.left, p, q);
        else if (contains(root.right, p.val, q.val) == 2)
            return helper(root.right, p, q);
        return root;
    }

    // -1表示都没找到, 0表示找到p，1表示找到q，2表示都找到
    private int contains(TreeNode x, int p, int q) {
        if (x == null) return -1;
        if (map.containsKey(x))
            return map.get(x);
        int temp;
        if (x.val == p) {
            if (contains(x.left, p, q) == 1 || contains(x.right, p, q) == 1)
                temp = 2;
            else temp = 0;
        } else if (x.val == q) {
            if (contains(x.left, p, q) == 0 || contains(x.right, p, q) == 0)
                temp = 2;
            else temp = 1;
        } else {
            int left = contains(x.left, p, q), right = contains(x.right, p, q);
            if (left == 2 || right == 2 || (left == 0 && right == 1) || (left == 1 && right == 0))
                temp = 2;
            else if (left == 0 || right == 0)
                temp = 0;
            else if (left == 1 || right == 1)
                temp = 1;
            else temp = -1;
        }
        map.put(x, temp);
        return temp;
    }
```
思路分析：
* 以x为结点的树中，关于p与q的情况只有四种，定义函数`int contains(TreeNode x, int p, int q)`判断四种情况：
    * p,q都在该树中，返回-2
    * p,q都不在该树中，返回-1
    * p在该树中，但q不在该树中，返回0
    * q在该树中，但p不在该树中，返回1
* `contains`的判断逻辑有点复杂：
    * 如果x的值为p，就去看左子树或者右子树是否能找到q（也就是`contains(x.left, p, q) == 1 || contains(x.right, p, q) == 1`），能找到则x包含了p与q，返回2；否则只包含q，返回0
    * 如果x的值为q，对称的逻辑
    * 如果x的值不为q也不为p，则去查看左右子树的情况`int left = contains(x.left, p, q), right = contains(x.right, p, q);`
        * 如果左子树或者右子树有其一包含了p，q或者左右子树分别包含p，q，返回2
        * 否则左右子树有其一包含了p，返回0
        * 否则左右子树有其一包含了q，返回1
        * 否则返回-1
* 函数`TreeNode helper(TreeNode root, TreeNode p, TreeNode q)`，返回值为p与q的最近公共祖先结点。
    * 如果x的右子树同时包含p与q，说明右子树中才有p与q的最近公共祖先结点。
    * 对于左子树分析同理。
    * 否则，左右子树各含p，q中的一个，当前结点就是要找的答案（因为题目规定一定含有p与q的最近公共祖先结点，所以不存在找不到的情况）
* 但是如果直接按刚才的思路做，会有很多的重复计算，这个时候就可以在递归中加入记忆化搜索。使用散列表，结点为值，表示包含p，q情况的数字为值。在`contains`的递归中首先去查看是否已经计算过该结点情况，如果有就直接返回，如果没有就按逻辑计算，最后返回前将计算结果进行存储。

运行结果：
* 执行用时 :11 ms, 在所有 Java 提交中击败了35.86%的用户
* 内存消耗 :42.3 MB, 在所有 Java 提交中击败了100.00%的用户

----
* 更多LeetCode题解请看[题解仓库](https://github.com/ustcyyw/yyw_algorithm)
* 题解框架由小工具自动生产，参考[工具项目](https://github.com/ustcyyw/markdown_tool)
* [我的github](https://github.com/ustcyyw)还有别的小项目也很好玩。卑微求个~小星星蟹蟹