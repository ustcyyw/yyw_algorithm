# 653. 两数之和 IV - 输入 BST

给定一个二叉搜索树和一个目标结果，如果 BST 中存在两个元素且它们的和等于给定的目标结果，则返回 true。

案例 1:
输入: 

 ```
    5
   / \
  3   6
 / \   \
2   4   7
 ```
Target = 9
输出: True

案例 2:
输入: 
  ```
 	5
   / \
  3   6
 / \   \
2   4   7
  ```
Target = 28
输出: False

来源：力扣（LeetCode）
[链接](https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst)：https://leetcode-cn.com/problems/two-sum-iv-input-is-a-bst

### 解法：中序遍历+双指针

```java
	private List<Integer> nums;
    public boolean findTarget(TreeNode root, int k) {
        nums = new ArrayList<>();
        midTraversal(root);
        int i = 0, j = nums.size() - 1;
        while(i < j){
            int sum = nums.get(i) + nums.get(j);
            if(sum < k) i++;
            else if(sum > k) j--;
            else return true;
        }
        return false;
    }

    private void midTraversal(TreeNode x){
        if(x == null)
            return;
        midTraversal(x.left);
        nums.add(x.val);
        midTraversal(x.right);
    }
```

思路分析：

* 判断BST中的两个元素是否能加和为一个定值。BST有一个很重要的性质，就是中序遍历BST可以得到一个有序的数组。而有序数组中两个元素是否能加和为`k`，这样与序数组中两个元素和为`k`，几乎没有区别。唯一的区别就是前者可能找不到这样的两个元素。
* [双指针解决有序数组的两数之和问题](https://github.com/ustcyyw/yyw_algorithm/blob/master/easy/TwoPoint/twoSum167.md)
* [二叉树的中序遍历（递归与非递归）](https://github.com/ustcyyw/yyw_algorithm/blob/master/medium/Tree/inorderTraversal.md)
* 有了上述两个问题的基础，我们通过中序遍历先得到有序数组，然后使用双指针。两个元素不能加和为`k`，就是说当两指针已经相遇了依旧没有找到两个满足的元素，就返回`false`。所以第6行，循环条件是`i < j`。循环内部第10行找到了这样的两个元素即返回。
* 时间复杂度，中序遍历与双指针都是$O(n)$，所以这里时间复杂度为$O(2n)$。空间复杂度由于使用了一个List来存放元素，所以为$O(n)$。

运行结果：
* 执行用时 :3 ms, 在所有 Java 提交中击败了93.15%的用户
* 内存消耗 :41.5 MB, 在所有 Java 提交中击败了51.69%的用户