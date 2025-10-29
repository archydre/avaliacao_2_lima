package tree.miscellaneous

import tree.TreeNode

fun findMin(node: TreeNode): TreeNode {
    var current = node
    while (current.left != null) {
        current.left?.let {
            current = it
        }
    }
    return current
}

fun findMax(node: TreeNode): TreeNode {
    var current = node
    while (current.right != null) {
        current.right?.let {
            current = it
        }
    }
    return current
}