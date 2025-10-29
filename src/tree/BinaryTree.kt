package tree

import tree.recursive.binarytree.insertInTree
import tree.recursive.binarytree.removeInTree

///////////////////////////////////////////

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

class PersonNode(val name: String? = null, val sex: Char? = null, val age: Int? = null, val weight: Float? = null ) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

// Restringimos T para ser um tipo que pode ser comparado consigo mesmo (Comparable)
class Node<T : Comparable<T>>(val value: T) {
    var left: Node<T>? = null
    var right: Node<T>? = null
}

///////////////////////////////////////////

class BinaryTree(var root: TreeNode?) {
    fun add(value: Int): Unit {
        if(root == null) {
            root = TreeNode(value)
        }else {
            val current = this.root
            insertInTree(current, value)
        }
    }

    fun printTree() {
        tree.recursive.binarytree.printTree(this.root)
    }

    fun remove(value: Int): Unit {
        this.root = removeInTree(this.root, value)
    }
}

class PersonTree(){
    val root = PersonNode()


}