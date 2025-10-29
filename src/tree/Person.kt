import java.util.LinkedList


data class Pessoa(val nome: String, val sexo: Char, val idade: Int, val peso: Float)

class PersonNode(val pessoa: Pessoa) {
    var left: PersonNode? = null
    var right: PersonNode? = null
}

fun gerarListasPorSexo(
    root: PersonNode?,
    listaHomens: MutableList<Pessoa>,
    listaMulheres: MutableList<Pessoa>
) {
    if (root == null) return

    gerarListasPorSexo(root.left, listaHomens, listaMulheres)

    if (root.pessoa.sexo == 'M') {
        listaHomens.add(root.pessoa)
    } else if (root.pessoa.sexo == 'F') {
        listaMulheres.add(root.pessoa)
    }

    gerarListasPorSexo(root.right, listaHomens, listaMulheres)
}