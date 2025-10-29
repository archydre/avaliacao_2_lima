import tree.*

fun main(){
    val binary = BinaryTree(TreeNode(5))

    binary.add(20)
    binary.add(5)
    binary.add(12)
    binary.add(36)
    binary.add(27)
    binary.add(45)
    binary.add(9)
    binary.add(2)
    binary.add(6)
    binary.add(17)
    binary.add(40)

    binary.printTree()

    binary.remove(12)

    println("\nremove 12")
    binary.printTree()
    binary.remove(5)

    println("\nremove 5")
    binary.printTree()

    binary.remove(20)

    println("\nremove 20")
    binary.printTree()

    println("hello world")
}