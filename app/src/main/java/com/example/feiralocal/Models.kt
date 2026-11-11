package com.example.feiralocal

// 1\. Tipo de Utilizador: define se é Comprador ou Vendedor (Feirante)
enum class TipoUsuario {
    COMPRADOR,
    VENDEDOR
}

// 2\. Modelo de Utilizador
data class Usuario(
    val id: Int,
    val nome: String,
    val email: String,
    val tipo: TipoUsuario
)

// 3\. Categorias baseadas no FeiraLocal
enum class CategoriaProduto(val label: String) {
    TODOS("Todos"),
    VERDURAS("Verduras"),
    FRUTAS("Frutas"),
    LEGUMES("Legumes"),
    ORGANICOS("Orgânicos")
}

// 4\. Modelo de Produto
data class Produto(
    val id: Int,
    val nome: String,
    val preco: Double,
    val unidade: String, // Ex: "kg", "maço", "dúzia"
    val categoria: CategoriaProduto,
    val nomeFeirante: String
)

// 5\. Modelo de Item no Carrinho
data class ItemCarrinho(
    val produto: Produto,
    val quantidade: Int
)