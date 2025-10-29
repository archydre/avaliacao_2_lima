
class NodeComContador(var key: Int) {
    var count: Int = 1
    var left: NodeComContador? = null
    var right: NodeComContador? = null
}

private fun findMin(node: NodeComContador): NodeComContador {
    var current = node
    while (current.left != null) {
        current = current.left!!
    }
    return current
}

fun insereRepetido(root: NodeComContador?, key: Int): NodeComContador {
    if (root == null) {
        return NodeComContador(key)
    }

    if (key < root.key) {
        root.left = insereRepetido(root.left, key)
    } else if (key > root.key) {
        root.right = insereRepetido(root.right, key)
    } else {
        root.count++
    }
    return root
}

fun removeRepetido(root: NodeComContador?, key: Int): NodeComContador? {
    if (root == null) return null

    if (key < root.key) {
        root.left = removeRepetido(root.left, key)
    } else if (key > root.key) {
        root.right = removeRepetido(root.right, key)
    } else {
        if (root.count > 1) {
            root.count--
            return root
        }

        if (root.left == null) {
            return root.right
        } else if (root.right == null) {
            return root.left
        }

        val successor = findMin(root.right!!)

        root.key = successor.key
        root.count = successor.count

        successor.count = 1
        root.right = removeRepetido(root.right, successor.key)
    }
    return root
}