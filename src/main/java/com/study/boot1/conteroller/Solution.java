package com.study.boot1.conteroller;

import java.util.ArrayList;

public class Solution {

	public class TreeNode {
		int val = 0;
		TreeNode left = null;
		TreeNode right = null;

		public TreeNode(int val) {
			this.val = val;

		}

	}

	public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
		ArrayList<ArrayList<Integer>> list = new ArrayList<ArrayList<Integer>>();
		if (root == null || root.val > target) {
			return list;
		}
		ArrayList<Integer> path = new ArrayList<Integer>();
		findPath(root, target, list, path);
		return list;
	}

	private void findPath(TreeNode root, int target, ArrayList<ArrayList<Integer>> list, ArrayList<Integer> path) {
		// 如果节点为空，或者值小于target此条路径清空
		if (root == null || root.val > target) {
			path.clear();
		} else if (root.val == target) {// 如果当前节点等于target且为叶子节点则直接将它添加到path中，否则这条路径清空
			if (root.left == null && root.right == null) {
				path.add(root.val);
				list.add(path);
			} else {
				path.clear();
			}
		} else { // 当根节点的值小于target，则递归去寻找它的左右子树
			path.add(root.val);
			ArrayList<Integer> path2 = new ArrayList<Integer>();
			path2.addAll(path);
			target -= root.val;
			findPath(root.left, target, list, path);
			findPath(root.right, target, list, path2);
		}
	}


	public static void main(String[] args) {

	}
}
