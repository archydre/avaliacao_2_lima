// Imports da Parte A (pacote tree)
import tree.BinaryTree
import tree.TreeNode
import tree.ehCheia
import tree.miscellaneous.findMax
import tree.PersonNode
import tree.Pessoa
import tree.gerarListasPorSexo
import tree.PessoaLinkedList

/**
 * Ponto de entrada principal da Atividade II.
 * Apresenta um menu para navegar entre as Partes A, B e C.
 */
fun main() {
    var running = true
    while (running) {
        println("\n--- ATIVIDADE II (KOTLIN) ---")
        println("1. Parte A: Executar Testes da Árvore Binária de Busca")
        println("2. Parte B: Menu Interativo Árvore AVL (Pessoas)")
        println("3. Parte C: Menu Interativo Grafos")
        println("0. Sair da Aplicação")
        print("Escolha uma opção: ")

        try {
            when (readLine()?.toIntOrNull()) {
                1 -> menuBST()       // Roda os testes da Parte A
                2 -> menuAvl()       // Chama a função de AvlTree.kt [cite: 37]
                3 -> menuGrafos()    // Roda o menu da Parte C
                0 -> {
                    running = false
                    println("Até logo!")
                }
                else -> println("Opção inválida.")
            }
        } catch (e: Exception) {
            println("Erro inesperado no menu principal: ${e.message}")
        }
    }
}

/**
 * Menu de demonstração para a Parte A (BST).
 * Executa todos os testes (A.1 a A.5) sequencialmente.
 */
fun menuBST() {
    println("\n--- PARTE A: ÁRVORE BINÁRIA DE BUSCA (TESTES) ---")

    // --- A.1: Implementação BST (Números) ---
    println("\nA.1.a: Inserindo 20, 5, 12, 36, 27, 45, 9, 2, 6, 17, 40")
    val binary = BinaryTree(TreeNode(20))
    listOf(5, 12, 36, 27, 45, 9, 2, 6, 17, 40).forEach { binary.add(it) }
    print("Árvore (em ordem): ")
    binary.printTree()

    println("\n\nA.1.b: Removendo 9, 5, e 20...")
    binary.remove(9)
    binary.remove(5)
    binary.remove(20)
    print("Árvore resultante: ")
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
    // Criando uma árvore de teste válida para A.3
    var pRoot: PersonNode? = PersonNode(Pessoa("Carlos", 'M', 30, 75.5f))
    pRoot?.left = PersonNode(Pessoa("Ana", 'F', 25, 60.0f))
    pRoot?.left?.right = PersonNode(Pessoa("Beatriz", 'F', 22, 55.2f))
    pRoot?.right = PersonNode(Pessoa("Daniel", 'M', 40, 85.0f)) // D > C

    val homens = PessoaLinkedList()
    val mulheres = PessoaLinkedList()
    gerarListasPorSexo(pRoot, homens, mulheres)

    print("Homens (Ordenado): ")
    homens.printNomes() // Esperado: Carlos -> Daniel -> null
    print("Mulheres (Ordenado): ")
    mulheres.printNomes() // Esperado: Ana -> Beatriz -> null


    // --- A.4: Contador de Repetidos --- [cite: 25]
    println("\n\nA.4: Testando contador de duplicatas...")
    var cRoot: NodeComContador? = null
    cRoot = insereRepetido(cRoot, 10)
    cRoot = insereRepetido(cRoot, 5)
    cRoot = insereRepetido(cRoot, 15)
    cRoot = insereRepetido(cRoot, 10) // Repetido 1
    cRoot = insereRepetido(cRoot, 10) // Repetido 2

    // (Acessando diretamente pois não temos uma função de busca para esta árvore)
    var noDez = cRoot
    println("Contagem do nó '10' (esperado 3): ${noDez?.key} -> ${noDez?.count}")
    cRoot = removeRepetido(cRoot, 10)
    println("Contagem do nó '10' após 1 remoção (esperado 2): ${noDez?.key} -> ${noDez?.count}")
    cRoot = removeRepetido(cRoot, 10)
    println("Contagem do nó '10' após 2 remoções (esperado 1): ${noDez?.key} -> ${noDez?.count}")

    // --- A.5: Verificar se é Cheia --- [cite: 26, 27]
    println("\n\nA.5: Verificando se as árvores são 'cheias'...")
    println("Árvore A.1.b é cheia? (Esperado: false) -> ${ehCheia(binary.root)}")

    val fullTree = BinaryTree(TreeNode(10))
    fullTree.add(5)
    fullTree.add(20)
    println("Árvore (10, 5, 20) é cheia? (Esperado: true) -> ${ehCheia(fullTree.root)}")

    val notFullTree = BinaryTree(TreeNode(10))
    notFullTree.add(5)
    println("Árvore (10, 5) é cheia? (Esperado: false) -> ${ehCheia(notFullTree.root)}")

    println("Árvore vazia é cheia? (Esperado: true) -> ${ehCheia(null)}")

    println("\n--- Fim Testes Parte A ---")
}

/**
 * Menu interativo para a Parte C (Grafos).
 */
fun menuGrafos() {
    println("\n--- PARTE C: GRAFOS ---")
    println("OBS: O arquivo .txt [cite: 41] deve estar na raiz do projeto.")
    print("Digite o nome do arquivo (ex: grafo.txt): ")
    val filename = readLine() ?: "grafo.txt"

    val g: Graph
    try {
        g = Graph(filename)
    } catch (e: Exception) {
        println("Erro ao carregar o arquivo '$filename': ${e.message}")
        return
    }

    if (g.V == 0) {
        println("Grafo não pôde ser inicializado. Voltando ao menu.")
        return
    }

    var running = true
    while(running) {
        println("\n--- Menu Grafos (V=${g.V}, A=${g.A}) ---")
        println("1. Listar Grafo (Lista Adj.)")
        println("2. Exibir Matriz de Adjacência")
        println("3. Exibir Graus de Saída")
        println("4. Inserir Arco")
        println("5. Remover Arco")
        println("6. Calcular Floyd-Warshall (Caminho Mínimo)")
        println("7. Exibir Matriz de Distâncias (Pós-Floyd)")
        println("0. Voltar ao Menu Principal")
        print("Escolha: ")

        try {
            when (readLine()?.toIntOrNull()) {
                1 -> g.listarGrafo()
                2 -> g.exibirMatrizAdjacencia()
                3 -> g.exibirGrauVertices()
                4 -> {
                    print("Vértice de Origem (0-${g.V - 1}): ")
                    val v1 = readLine()?.toIntOrNull() ?: -1
                    print("Vértice de Destino (0-${g.V - 1}): ")
                    val v2 = readLine()?.toIntOrNull() ?: -1
                    print("Peso: ")
                    val peso = readLine()?.toIntOrNull() ?: 1
                    g.insereArco(v1, v2, peso)
                }
                5 -> {
                    print("Vértice de Origem (0-${g.V - 1}): ")
                    val v1 = readLine()?.toIntOrNull() ?: -1
                    print("Vértice de Destino (0-${g.V - 1}): ")
                    val v2 = readLine()?.toIntOrNull() ?: -1
                    g.removeArco(v1, v2)
                }
                6 -> g.calcularFloydWarshall()
                7 -> g.exibirMatrizDistancias()
                0 -> running = false
                else -> println("Opção inválida.")
            }
        } catch (e: Exception) {
            println("Erro na operação do grafo: ${e.message}")
        }
    }
}