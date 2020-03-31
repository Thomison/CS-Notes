/*
输入某二叉树的前序遍历和中序遍历的结果，请重建该二叉树。

假设输入的前序遍历和中序遍历的结果中都不含重复的数字。
*/
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        
        return build(preorder, 0, preorder.length - 1, inorder, 0, preorder.length - 1);
        
    }
    
    private TreeNode build(int[] preorder, int pl, int pr, int[] inorder, int il, int ir) {
        if (pl > pr || il > ir) {
            return null;
        } 
        TreeNode root = new TreeNode(preorder[pl]);     //初始化根节点
        int lnum = 0, idx = -1;

        for (int i = il; i <= ir; i++) {            
            if (inorder[i] == root.val) {
                lnum = i - il;          //左子树的结点个数
                idx = i;                    //根节点在中序中的下标
                break;
            }
        }
        
        root.left = build(preorder, pl + 1, pl + lnum, inorder, il, idx - 1);       //左子树
        root.right = build(preorder, pl + lnum + 1, pr, inorder, idx + 1, ir);      //右子树
        
        return root;
    }
}
