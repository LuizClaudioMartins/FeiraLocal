package com.example.feiralocal

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

object AppData {

    // Utilizador atualmente ativo na sessão (começa com a Dona Maria)
    var usuarioLogado = mutableStateOf(
        Usuario(1, "Dona Maria", "maria@email.com", TipoUsuario.COMPRADOR)
    )

    // Lista de Utilizadores (permite cadastrar novos ou alternar na apresentação)
    val listaUsuarios = mutableStateListOf(
        Usuario(1, "Dona Maria", "maria@email.com", TipoUsuario.COMPRADOR),
        Usuario(2, "Seu José", "jose@feiralocal.com", TipoUsuario.VENDEDOR)
    )

    // Lista de Produtos do FeiraLocal (baseada no projeto UX/UI)
    val listaProdutos = mutableStateListOf(
        Produto(1, "Alface Crespa Hidropónica", 3.50, "maço", CategoriaProduto.VERDURAS, "Horta do Seu José"),
        Produto(2, "Tomate Italiano Orgânico", 8.90, "kg", CategoriaProduto.ORGANICOS, "Sítio Boa Vista"),
        Produto(3, "Banana Prata", 6.00, "kg", CategoriaProduto.FRUTAS, "Frutas da Terra"),
        Produto(4, "Cenoura Selecionada", 5.20, "kg", CategoriaProduto.LEGUMES, "Horta do Seu José"),
        Produto(5, "Rúcula Fresca", 4.00, "maço", CategoriaProduto.VERDURAS, "Sítio Boa Vista")
    )

    // Carrinho de Compras
    val carrinho = mutableStateListOf<ItemCarrinho>()

    // --- FUNÇÕES DE NEGÓCIO ---

    // 1. Cadastrar novo produto (usado pelo feirante)
    fun adicionarProduto(produto: Produto) {
        listaProdutos.add(produto)
    }

    // 2. Excluir produto da feira
    fun removerProduto(produtoId: Int) {
        listaProdutos.removeAll { it.id == produtoId }
    }

    // 3. Cadastrar novo utilizador (comprador ou vendedor)
    fun cadastrarUsuario(usuario: Usuario) {
        listaUsuarios.add(usuario)
    }

    // 4. Excluir utilizador
    fun removerUsuario(usuarioId: Int) {
        listaUsuarios.removeAll { it.id == usuarioId }
    }

    // 5. Adicionar ao carrinho (ou somar quantidade se já existir)
    fun adicionarAoCarrinho(produto: Produto) {
        val index = carrinho.indexOfFirst { it.produto.id == produto.id }
        if (index != -1) {
            val itemExistente = carrinho[index]
            carrinho[index] = itemExistente.copy(quantidade = itemExistente.quantidade + 1)
        } else {
            carrinho.add(ItemCarrinho(produto = produto, quantidade = 1))
        }
    }

    // 6. Remover ou decrementar item do carrinho
    fun removerDoCarrinho(produtoId: Int) {
        val index = carrinho.indexOfFirst { it.produto.id == produtoId }
        if (index != -1) {
            val item = carrinho[index]
            if (item.quantidade > 1) {
                carrinho[index] = item.copy(quantidade = item.quantidade - 1)
            } else {
                carrinho.removeAt(index)
            }
        }
    }

    // 7. Limpar o carrinho (após compra finalizada com sucesso)
    fun limparCarrinho() {
        carrinho.clear()
    }

    // 8. Total do carrinho calculado em tempo real
    fun calcularTotal(): Double {
        return carrinho.sumOf { it.produto.preco * it.quantidade }
    }
}

