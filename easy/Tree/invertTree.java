package easy.Tree;

/**
 * @Time : 2020年2月20日15:43:03
 * @Author : yyw@ustc
 * @E-mail : yang0@mail.ustc.edu.cn
 * @Github : https://github.com/ustcyyw
 * @desc :
 */

import BaseClass.TreeNode;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 翻转一棵二叉树。
 *
 * 示例：
 * 输入：
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * 输出：
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/invert-binary-tree
 */
public class invertTree {
    /**
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.3 MB, 在所有 Java 提交中击败了5.04%的用户
     * 要完成当前结点的翻转，得先保证其左右子树已经翻转完成。
     * 翻转过程中需要不断更新链接，右链接指向反转完成的左子树；左连接指向反转完成的右子树
     */
    public TreeNode invertTree(TreeNode root) {
        if(root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.right = left;
        root.left = right;
        return root;
    }

    /**
     * 迭代的方法 需要在每个结点都进行一次翻转，所以BFS DFS都是可以的 只要能遍历
     * 执行用时 :0 ms, 在所有 Java 提交中击败了100.00%的用户
     * 内存消耗 :37.1 MB, 在所有 Java 提交中击败了5.04%的用户
     */

    public TreeNode invertTreeBFS(TreeNode root){
        if(root == null) return null;
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while(!queue.isEmpty()){
            TreeNode curNode = queue.remove();
            TreeNode left = curNode.left;
            TreeNode right = curNode.right;
            curNode.left = right;
            curNode.right = left;
            if(left != null) queue.add(left);
            if(right != null) queue.add(right);
        }
        return root;
    }

    public TreeNode invertTreeDFS(TreeNode root){
        if(root == null) return null;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.empty()){
            TreeNode curNode = stack.pop();
            TreeNode left = curNode.left;
            TreeNode right = curNode.right;
            curNode.left = right;
            curNode.right = left;
            if(left != null) stack.push(left);
            if(right != null) stack.push(right);
        }
        return root;
    }
}
