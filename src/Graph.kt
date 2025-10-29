import java.io.File
import java.util.Scanner

const val INFINITO = Int.MAX_VALUE / 2 // Para evitar overflow na soma de Floyd-Warshall

/**
 * C.1: Implementação da Classe Graph em Kotlin [cite: 40]
 */
class Graph {
    val V: Int // Número de Vértices [cite: 48]
    var A: Int = 0 // Número de Arcos [cite: 50]

    // Matrizes pedidas na especificação da classe C++
    var adj: Array<IntArray> // Matriz de adjacência (usarei para pesos) [cite: 54]
    var dist: Array<IntArray> // Matriz de distâncias (para Floyd-Warshall) [cite: 56]
    var grau: IntArray // Grau (de saída, para grafos direcionados) [cite: 58]

    // Construtor principal
    constructor(numVertices: Int) {
        this.V = numVertices
        this.adj = Array(V) { IntArray(V) { 0 } }
        this.dist = Array(V) { IntArray(V) { INFINITO } }
        this.grau = IntArray(V) { 0 }

        // Distância de um nó para ele mesmo é 0
        for (i in 0 until V) {
            dist[i][i] = 0
        }
    }

    // Construtor C.1: Lendo de um arquivo "*.txt" [cite: 41]
    // Formato esperado:
    // Linha 1: V (Número de vértices)
    // Linhas seguintes: v1 v2 peso (v1 e v2 base 0)
    constructor(filename: String) : this(
        // Lê a primeira linha para obter V
        File(filename).bufferedReader().useLines { lines ->
            lines.firstOrNull()?.trim()?.toIntOrNull() ?: 0
        }
    ) {
        if (V == 0) {
            println("Arquivo $filename vazio ou formato inválido (V=0).")
            return
        }

        try {
            File(filename).bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line -> // Pula a primeira linha (V)
                    val parts = line.trim().split(" ").mapNotNull { it.toIntOrNull() }
                    if (parts.size >= 2) {
                        val v1 = parts[0]
                        val v2 = parts[1]
                        // Peso padrão 1 se não especificado
                        val peso = if (parts.size == 3) parts[2] else 1
                        insereArco(v1, v2, peso)
                    }
                }
            }
            println("Grafo lido de $filename com $V vértices e $A arcos.")
        } catch (e: Exception) {
            println("Erro ao ler arquivo $filename: ${e.message}")
        }
    }


    // --- Funções C.1 ---

    /**
     * Insere um arco direcionado de v1 para v2 com um peso.
     */
    fun insereArco(v1: Int, v2: Int, peso: Int) {
        if (v1 >= V || v2 >= V || v1 < 0 || v2 < 0) {
            println("Erro: Vértice inválido ($v1 ou $v2).")
            return
        }
        if (adj[v1][v2] == 0) { // Arco novo
            A++
            grau[v1]++ // Grau de saída
        }
        adj[v1][v2] = peso
        dist[v1][v2] = peso // Matriz de distâncias inicializa com pesos diretos
    }

    /**
     * Remove o arco direcionado de v1 para v2.
     */
    fun removeArco(v1: Int, v2: Int) {
        if (v1 >= V || v2 >= V || v1 < 0 || v2 < 0) {
            println("Erro: Vértice inválido ($v1 ou $v2).")
            return
        }
        if (adj[v1][v2] != 0) { // Arco existe
            adj[v1][v2] = 0
            dist[v1][v2] = INFINITO // Remove da matriz de distâncias
            A--
            grau[v1]--
        }
    }

    /**
     * Listagem do grafo (formato lista de adjacência).
     */
    /**
     * Listagem do grafo (formato lista de adjacência) - Versão "do zero"
     */
    fun listarGrafo() {
        println("\n--- Listagem do Grafo (V=$V, A=$A) ---")
        for (i in 0 until V) {
            print("Vértice $i -> ")
            var temVizinho = false

            // Loop manual em vez de .filter
            for (j in 0 until V) {
                if (adj[i][j] != 0) {
                    print("$j (Peso: ${adj[i][j]}); ")
                    temVizinho = true
                }
            }

            if (!temVizinho) {
                println("Nenhum")
            } else {
                println() // Apenas para quebrar a linha
            }
        }
    }

    /**
     * Exibição das matrizes de representação (Adjacência).
     */
    fun exibirMatrizAdjacencia() {
        println("\n--- Matriz de Adjacência (Pesos) ---")
        print("   ")
        (0 until V).forEach { print("%-4d".format(it)) }
        println("\n---" + "----".repeat(V))

        for (i in 0 until V) {
            print("%-2d| ".format(i))
            for (j in 0 until V) {
                print("%-4d".format(adj[i][j]))
            }
            println()
        }
    }

    /**
     * Exibição da Matriz de Distâncias (resultado do Floyd-Warshall).
     */
    fun exibirMatrizDistancias() {
        println("\n--- Matriz de Distâncias Mínimas (Floyd-Warshall) ---")
        print("   ")
        (0 until V).forEach { print("%-4s".format(it)) }
        println("\n---" + "----".repeat(V))

        for (i in 0 until V) {
            print("%-2d| ".format(i))
            for (j in 0 until V) {
                if (dist[i][j] == INFINITO) {
                    print("%-4s".format("INF"))
                } else {
                    print("%-4d".format(dist[i][j]))
                }
            }
            println()
        }
    }

    /**
     * Exibição do grau (de saída) dos vértices.
     */
    fun exibirGrauVertices() {
        println("\n--- Grau de Saída dos Vértices ---")
        grau.forEachIndexed { i, g ->
            println("Vértice $i: Grau $g")
        }
    }

    // --- Função C.2 ---

    /**
     * C.2: Implementa o algoritmo de Floyd-Warshall [cite: 64]
     */
    fun calcularFloydWarshall() {
        // A matriz 'dist' já foi inicializada com pesos diretos e 0 na diagonal.

        // k = vértice intermediário
        for (k in 0 until V) {
            // i = vértice de origem
            for (i in 0 until V) {
                // j = vértice de destino
                for (j in 0 until V) {
                    // Se o caminho i -> k -> j é menor que o caminho direto i -> j
                    val distIK = dist[i][k]
                    val distKJ = dist[k][j]

                    if (distIK != INFINITO && distKJ != INFINITO) {
                        if (dist[i][j] > distIK + distKJ) {
                            dist[i][j] = distIK + distKJ
                        }
                    }
                }
            }
        }

        println("\nCálculo de Floyd-Warshall concluído.")
        exibirMatrizDistancias()
    }
}

/*
// Exemplo de uso de Grafos (C.1 e C.2)
// Supondo um arquivo "grafo.txt" com:
// 4
// 0 1 5
// 0 3 10
// 1 2 3
// 2 3 1

fun main() {
    // menuAvl() // Roda o menu da AVL

    // Teste dos Grafos
    // (Descomente e crie o arquivo "grafo.txt" no diretório raiz do projeto)
    
    // val g = Graph("grafo.txt") // [cite: 41]
    
    // g.listarGrafo() // [cite: 40]
    // g.exibirMatrizAdjacencia() // [cite: 40]
    // g.exibirGrauVertices() // [cite: 40]
    
    // g.calcularFloydWarshall() // [cite: 64]
    
    // g.removeArco(1, 2)
    // println("\nApós remover arco 1->2:")
    // g.listarGrafo()
}
*/