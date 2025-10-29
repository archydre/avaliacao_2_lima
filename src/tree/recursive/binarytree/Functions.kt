package tree.recursive.binarytree

import tree.TreeNode
import tree.miscellaneous.findMin


fun insertInTree(current: TreeNode?, value: Int): Unit{
    if(value>=current?.`val`!!){
        if(current.right==null){
            current.right=TreeNode(value)
        }else{
            insertInTree(current.right, value)
        }
    }else{
        if(current.left==null){
            current.left=TreeNode(value)
        }else{
            insertInTree(current.left, value)
        }
    }
}

fun removeInTree(current: TreeNode?, value: Int): TreeNode?{
    if (current == null) {
        return null
    }

    if (value < current.`val`) {
        current.left = removeInTree(current.left, value)
        return current
    } else if (value > current.`val`) {
        current.right = removeInTree(current.right, value)
        return current
    }

    if (current.left == null) {
        return current.right
    } else if (current.right == null) {
        return current.left
    }

    val successor = findMin(current.right!!)
    current.`val` = successor.`val`
    current.right = removeInTree(current.right, successor.`val`)

    return current
}

fun printTree(root: TreeNode?) {
    if (root == null) return
    printTree(root.left)
    print("${root.`val`} - ")
    printTree(root.right)
}