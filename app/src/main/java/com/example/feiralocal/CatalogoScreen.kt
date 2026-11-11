package com.example.feiralocal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feiralocal.ui.theme.LaranjaAcao
import com.example.feiralocal.ui.theme.TextoPrincipal
import com.example.feiralocal.ui.theme.TextoSecundario
import com.example.feiralocal.ui.theme.VermelhoErro
import com.example.feiralocal.ui.theme.VerdePrimario

@Composable
fun CatalogoScreen(
    onAbrirCarrinho: () -> Unit,
onAbrirPainelFeirante: () -> Unit,
onSair: () -> Unit
) {
    var categoriaSelecionada by remember { mutableStateOf(CategoriaProduto.TODOS) }

    val produtosFiltrados = remember(categoriaSelecionada, AppData.listaProdutos.size) {
        if (categoriaSelecionada == CategoriaProduto.TODOS) {
            AppData.listaProdutos
        } else {
            AppData.listaProdutos.filter { it.categoria == categoriaSelecionada }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // --- 1. CABEÇALHO COM BOAS-VINDAS E BOTÕES DE AÇÃO ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "FeiraLocal 🌱",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePrimario
                )
                Text(
                    text = "Olá, ${AppData.usuarioLogado.value.nome}",
                    fontSize = 14.sp,
                    color = TextoSecundario
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                // Atalho para Painel do Feirante
                OutlinedButton(
                    onClick = onAbrirPainelFeirante,
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text("Feirante", fontSize = 12.sp, color = VerdePrimario)
                }

                // Botão Sair / Logout
                TextButton(
                    onClick = onSair,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text("Sair", fontSize = 13.sp, color = VermelhoErro, fontWeight = FontWeight.SemiBold)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. FILTROS HORIZONTAIS (LazyRow) ---
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(CategoriaProduto.values()) { categoria ->
                val selecionado = categoria == categoriaSelecionada
                FilterChip(
                    selected = selecionado,
                    onClick = { categoriaSelecionada = categoria },
                    label = { Text(categoria.label) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VerdePrimario,
                        selectedLabelColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- 3. LISTAGEM DE PRODUTOS (LazyColumn) ---
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(produtosFiltrados) { produto ->
                CardProdutoItem(
                    produto = produto,
                    onAdicionar = { AppData.adicionarAoCarrinho(produto) }
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- 4. BOTÃO DO CARRINHO ---
        val totalItens = AppData.carrinho.sumOf { it.quantidade }
        Button(
            onClick = onAbrirCarrinho,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = LaranjaAcao)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (totalItens > 0) "Ver Carrinho ($totalItens)" else "Ver Carrinho",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
                )
                if (totalItens > 0) {
                Text(
                    text = "R$ ${"%.2f".format(AppData.calcularTotal())}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
            }
        }
    }
}

@Composable
fun CardProdutoItem(
    produto: Produto,
    onAdicionar: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = produto.nome,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Text(
                    text = produto.nomeFeirante,
                    fontSize = 12.sp,
                    color = TextoSecundario
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "R$ ${"%.2f".format(produto.preco)} / ${produto.unidade}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = VerdePrimario
                )
            }

            Button(
                onClick = onAdicionar,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario),
                modifier = Modifier.defaultMinSize(minHeight = 44.dp)
            ) {
                Text("+ Adicionar", fontSize = 13.sp, color = Color.White)
            }
        }
    }
}