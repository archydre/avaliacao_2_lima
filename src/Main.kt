import tree.BinaryTree
import tree.TreeNode
import tree.ehCheia
import tree.miscellaneous.findMax
// ... (no topo do Main.kt, adicione os imports necessários se faltarem)
import tree.PersonNode
import tree.Pessoa
import tree.gerarListasPorSexo
import tree.PessoaLinkedList // Importe a nova classe

// ...

/**
 * Menu de demonstração para a Parte A (BST).
 */
fun menuBST() {
    println("\n--- PARTE A: ÁRVORE BINÁRIA DE BUSCA (TESTES) ---")

    // --- A.1: Implementação BST (Números) ---
    println("\nA.1.a: Implementando BST com 20, 5, 12, 36, 27, 45, 9, 2, 6, 17, 40") [cite: 16]
    // O seu Main.kt atual começa com 5, mas o PDF pede para começar com 20
    val binary = BinaryTree(TreeNode(20))
    listOf(5, 12, 36, 27, 45, 9, 2, 6, 17, 40).forEach { binary.add(it) } [cite: 16]
    binary.printTree()

    println("\nA.1.b: Removendo 9, 5, e 20...") [cite: 17]
    binary.remove(9)
    binary.remove(5)
    binary.remove(20)
    binary.printTree()

    // --- A.2: Encontrar Maior Valor --- [cite: 22]
    if (binary.root != null) {
        val maxNode = findMax(binary.root!!)
        println("\n\nA.2: Maior valor na árvore A.1.b: ${maxNode.`val`}")
    } else {
        println("\n\nA.2: Árvore A.1.b está vazia.")
    }

    // --- A.3: Listas por Sexo --- [cite: 23, 24]
    println("\n\nA.3: Gerando listas por sexo (Árvore de Pessoas)...")
    var pRoot: PersonNode? = PersonNode(Pessoa("Carlos", 'M', 30, 75.5f))
    // (A implementação de inserção na árvore de Pessoas não foi pedida,
    // então criar uma árvore manualmente para o teste está OK)
    val ana = PersonNode(Pessoa("Ana", 'F', 25, 60.0f))
    val bruno = PersonNode(Pessoa("Bruno", 'M', 40, 85.0f))
    val beatriz = PersonNode(Pessoa("Beatriz", 'F', 22, 55.2f))

    pRoot?.left = ana
    pRoot?.right = bruno
    pRoot?.left?.right = beatriz // Ana -> Beatriz

    // Use as NOVAS classes de lista
    val homens = PessoaLinkedList()
    val mulheres = PessoaLinkedList()
    gerarListasPorSexo(pRoot, homens, mulheres)

    print("Homens (Ordenado): ")
    homens.printNomes() // Ana -> Beatriz
    print("Mulheres (Ordenado): ")
    mulheres.printNomes() // Beatriz -> null (Ops, Ana -> Beatriz. A ordem deve ser Ana, Beatriz)
    // Corrigindo a árvore de teste para ordem correta:
    // pRoot = Carlos
    // pRoot.left = Ana
    // pRoot.left.right = Beatriz (Ana < Beatriz < Carlos) - Está correto.
    // pRoot.right = Bruno (Carlos > Bruno) - Opa, erro. Bruno < Carlos.

    // Vamos refazer a árvore de teste A.3 corretamente
    pRoot = PersonNode(Pessoa("Carlos", 'M', 30, 75.5f))
    pRoot.left = PersonNode(Pessoa("Ana", 'F', 25, 60.0f))
    pRoot.left?.right = PersonNode(Pessoa("Beatriz", 'F', 22, 55.2f))
    pRoot.right = PersonNode(Pessoa("Daniel", 'M', 40, 85.0f)) // D > C

    val homensCorrigido = PessoaLinkedList()
    val mulheresCorrigido = PessoaLinkedList()
    gerarListasPorSexo(pRoot, homensCorrigido, mulheresCorrigido)

    println("\n(Teste A.3 Corrigido):")
    print("Homens (Ordenado): ")
    homensCorrigido.printNomes() // Esperado: Carlos -> Daniel -> null
    print("Mulheres (Ordenado): ")
    mulheresCorrigido.printNomes() // Esperado: Ana -> Beatriz -> null


    // --- A.4: Contador de Repetidos --- [cite: 25]
    println("\n\nA.4: Testando contador de duplicatas...")
    var cRoot: NodeComContador? = null
    cRoot = insereRepetido(cRoot, 10)
    cRoot = insereRepetido(cRoot, 5)
    cRoot = insereRepetido(cRoot, 15)
    cRoot = insereRepetido(cRoot, 10) // Repetido 1
    cRoot = insereRepetido(cRoot, 10) // Repetido 2

    println("Contagem do nó '10' (esperado 3): ${cRoot?.key} -> ${cRoot?.count}")
    cRoot = removeRepetido(cRoot, 10)
    println("Contagem do nó '10' após 1 remoção (esperado 2): ${cRoot?.key} -> ${cRoot?.count}")
    cRoot = removeRepetido(cRoot, 10)
    println("Contagem do nó '10' após 2 remoções (esperado 1): ${cRoot?.key} -> ${cRoot?.count}")

    // --- A.5: Verificar se é Cheia --- [cite: 26, 27]
    println("\n\nA.5: Verificando se as árvores são 'cheias'...")
    println("Árvore A.1.b é cheia? (false) -> ${ehCheia(binary.root)}")

    val fullTree = BinaryTree(TreeNode(10))
    fullTree.add(5)
    fullTree.add(20)
    println("Árvore (10, 5, 20) é cheia? (true) -> ${ehCheia(fullTree.root)}") [cite: 28, 29]

    val notFullTree = BinaryTree(TreeNode(10))
    notFullTree.add(5)
    println("Árvore (10, 5) é cheia? (false) -> ${ehCheia(notFullTree.root)}") [cite: 28]

    println("Árvore vazia é cheia? (true) -> ${ehCheia(null)}") [cite: 29]

    println("\n--- Fim Testes Parte A ---")
}