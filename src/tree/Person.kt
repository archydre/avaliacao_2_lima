package tree

data class Pessoa(val nome: String, val sexo: Char, val idade: Int, val peso: Float)

class PersonNode(val pessoa: Pessoa) {
    var left: PersonNode? = null
    var right: PersonNode? = null
}

// Modifique a assinatura da função para usar PessoaLinkedList
fun gerarListasPorSexo(
    root: PersonNode?,
    listaHomens: PessoaLinkedList, // Em vez de MutableList<Pessoa>
    listaMulheres: PessoaLinkedList // Em vez de MutableList<Pessoa>
) {
    if (root == null) return

    gerarListasPorSexo(root.left, listaHomens, listaMulheres)

    // A lógica de inserção agora usa nosso método "insereFinal"
    if (root.pessoa.sexo == 'M') {
        listaHomens.insereFinal(root.pessoa)
    } else if (root.pessoa.sexo == 'F') {
        listaMulheres.insereFinal(root.pessoa)
    }

    gerarListasPorSexo(root.right, listaHomens, listaMulheres)
}


class PessoaListNode(val pessoa: Pessoa) {
    var next: PessoaListNode? = null
}

class PessoaLinkedList {
    var head: PessoaListNode? = null
    private var tail: PessoaListNode? = null

    fun insereFinal(pessoa: Pessoa) {
        val newNode = PessoaListNode(pessoa)
        if (head == null) {
            head = newNode
            tail = newNode
        } else {
            tail?.next = newNode
            tail = newNode
        }
    }

    fun printNomes() {
        var current = head
        while (current != null) {
            print("${current.pessoa.nome} -> ")
            current = current.next
        }
        println("null")
    }
}