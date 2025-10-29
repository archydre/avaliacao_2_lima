import kotlin.math.max // Para altura

// Nó da AVL (baseado em Pessoa), agora com 'height'
class AvlPersonNode(var pessoa: Pessoa) {
    var left: AvlPersonNode? = null
    var right: AvlPersonNode? = null
    var height: Int = 1 // Altura inicial de um novo nó (folha)
}

class AvlTreePessoa {
    var root: AvlPersonNode? = null

    // --- Funções Auxiliares de AVL ---

    private fun getHeight(node: AvlPersonNode?): Int {
        return node?.height ?: 0
    }

    private fun updateHeight(node: AvlPersonNode) {
        node.height = 1 + maxOf(getHeight(node.left), getHeight(node.right))
    }

    private fun getBalanceFactor(node: AvlPersonNode?): Int {
        return if (node == null) 0 else getHeight(node.left) - getHeight(node.right)
    }

    // Rotação Simples à Direita
    private fun rightRotate(y: AvlPersonNode): AvlPersonNode {
        val x = y.left!!
        val T2 = x.right
        x.right = y
        y.left = T2
        updateHeight(y) // Atualiza altura do y primeiro
        updateHeight(x) // Depois do x (nova raiz)
        return x
    }

    // Rotação Simples à Esquerda
    private fun leftRotate(x: AvlPersonNode): AvlPersonNode {
        val y = x.right!!
        val T2 = y.left
        y.left = x
        x.right = T2
        updateHeight(x) // Atualiza altura do x primeiro
        updateHeight(y) // Depois do y (nova raiz)
        return y
    }

    // Função de rebalanceamento (usada na inserção e remoção)
    private fun rebalance(node: AvlPersonNode): AvlPersonNode {
        updateHeight(node)
        val balance = getBalanceFactor(node)

        // Caso Esquerda-Esquerda (LL)
        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node)
        }
        // Caso Esquerda-Direita (LR)
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left!!)
            return rightRotate(node)
        }
        // Caso Direita-Direita (RR)
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node)
        }
        // Caso Direita-Esquerda (RL)
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right!!)
            return leftRotate(node)
        }

        return node // Já balanceado
    }

    // --- Funções Requeridas (F1-F4) ---

    // F1: Inserção [cite: 33]
    fun insert(pessoa: Pessoa) {
        root = insertRec(root, pessoa)
    }

    private fun insertRec(node: AvlPersonNode?, pessoa: Pessoa): AvlPersonNode {
        // 1. Inserção BST padrão
        if (node == null) {
            return AvlPersonNode(pessoa)
        }
        if (pessoa.nome < node.pessoa.nome) {
            node.left = insertRec(node.left, pessoa)
        } else if (pessoa.nome > node.pessoa.nome) {
            node.right = insertRec(node.right, pessoa)
        } else {
            println("Erro: Nome '${pessoa.nome}' já existe.")
            return node // Não insere duplicatas
        }

        // 2. Atualiza altura e Rebalanceia
        return rebalance(node)
    }

    // F3: Remoção [cite: 35]
    fun remove(nome: String) {
        root = removeRec(root, nome)
    }

    private fun findMin(node: AvlPersonNode): AvlPersonNode {
        var current = node
        while (current.left != null) current = current.left!!
        return current
    }

    private fun removeRec(node: AvlPersonNode?, nome: String): AvlPersonNode? {
        if (node == null) {
            println("Erro: Nome '$nome' não encontrado para remoção.")
            return null
        }

        // 1. Remoção BST padrão
        if (nome < node.pessoa.nome) {
            node.left = removeRec(node.left, nome)
        } else if (nome > node.pessoa.nome) {
            node.right = removeRec(node.right, nome)
        } else {
            // Nó encontrado
            if (node.left == null || node.right == null) {
                // Nó com 0 ou 1 filho
                return node.left ?: node.right
            } else {
                // Nó com 2 filhos
                val successor = findMin(node.right!!)
                node.pessoa = successor.pessoa // Copia dados do sucessor
                node.right = removeRec(node.right, successor.pessoa.nome) // Remove o sucessor
            }
        }

        // 2. Atualiza altura e Rebalanceia
        // (O nó 'node' pode ter mudado, mas o rebalanceamento cuida disso)
        return rebalance(node)
    }

    // F2: Listagem (Em ordem) [cite: 34]
    fun list() {
        println("--- Lista de Pessoas (Ordenado por NOME) ---")
        listRec(root)
        println("--------------------------------------------")
    }

    private fun listRec(node: AvlPersonNode?) {
        if (node != null) {
            listRec(node.left)
            println(node.pessoa)
            listRec(node.right)
        }
    }

    // F4: Consulta [cite: 36]
    fun search(nome: String): Pessoa? {
        var current = root
        while (current != null) {
            if (nome == current.pessoa.nome) {
                return current.pessoa
            }
            current = if (nome < current.pessoa.nome) {
                current.left
            } else {
                current.right
            }
        }
        return null // Não encontrado
    }
}

// B.2: Menu de Opções [cite: 37]
fun menuAvl() {
    val avlTree = AvlTreePessoa()
    var running = true

    // Dados de exemplo
    avlTree.insert(Pessoa("Carlos", 'M', 30, 75.5f))
    avlTree.insert(Pessoa("Ana", 'F', 25, 60.0f))
    avlTree.insert(Pessoa("Bruno", 'M', 40, 85.0f))
    avlTree.insert(Pessoa("Daniela", 'F', 22, 55.2f))

    while (running) {
        println("\n--- MENU ÁRVORE AVL (PESSOAS) ---")
        println("1. Inserir Pessoa [F1]")
        println("2. Remover Pessoa [F3]")
        println("3. Listar Pessoas (Ordenado) [F2]")
        println("4. Consultar Pessoa [F4]")
        println("0. Sair")
        print("Escolha uma opção: ")

        try {
            when (readLine()?.toIntOrNull()) {
                1 -> { // Inserção [cite: 33]
                    print("Nome: ")
                    val nome = readLine() ?: ""
                    print("Sexo (M/F): ")
                    val sexo = readLine()?.firstOrNull()?.uppercaseChar() ?: ' '
                    print("Idade: ")
                    val idade = readLine()?.toIntOrNull() ?: 0
                    print("Peso: ")
                    val peso = readLine()?.toFloatOrNull() ?: 0f
                    if (nome.isNotEmpty() && (sexo == 'M' || sexo == 'F') && idade > 0) {
                        avlTree.insert(Pessoa(nome, sexo, idade, peso))
                        println("$nome inserido(a).")
                    } else {
                        println("Dados inválidos.")
                    }
                }
                2 -> { // Remoção [cite: 35]
                    print("Nome para remover: ")
                    val nome = readLine() ?: ""
                    if(nome.isNotEmpty()) {
                        avlTree.remove(nome)
                        println("$nome removido (se existia).")
                    }
                }
                3 -> { // Listagem [cite: 34]
                    avlTree.list()
                }
                4 -> { // Consulta [cite: 36]
                    print("Nome para consultar: ")
                    val nome = readLine() ?: ""
                    val pessoa = avlTree.search(nome)
                    if (pessoa != null) {
                        println("Encontrado: $pessoa")
                    } else {
                        println("Pessoa não encontrada.")
                    }
                }
                0 -> running = false
                else -> println("Opção inválida.")
            }
        } catch (e: Exception) {
            println("Erro: ${e.message}")
        }
    }
}

/* // Para rodar o menu:
fun main() {
    menuAvl()
}
*/