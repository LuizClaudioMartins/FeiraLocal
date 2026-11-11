package com.example.feiralocal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feiralocal.ui.theme.LaranjaAcao
import com.example.feiralocal.ui.theme.TextoPrincipal
import com.example.feiralocal.ui.theme.TextoSecundario
import com.example.feiralocal.ui.theme.VermelhoErro
import com.example.feiralocal.ui.theme.VerdePrimario

@Composable
fun PainelFeiranteScreen(
    onVoltarAoCatalogo: () -> Unit,
    onSair: () -> Unit
) {
    var nomeProduto by remember { mutableStateOf("") }
    var precoProduto by remember { mutableStateOf("") }
    var unidadeProduto by remember { mutableStateOf("kg") }
    var categoriaSelecionada by remember { mutableStateOf(CategoriaProduto.VERDURAS) }
    var mensagemSucesso by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // --- 1. CABEÇALHO COM VOLTAR E SAIR ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onVoltarAoCatalogo) {
                Text("← Catálogo", color = VerdePrimario, fontSize = 14.sp)
            }

            Text(
                text = "Gestão da Feira 🌱",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )

            TextButton(onClick = onSair) {
                Text("Sair", color = VermelhoErro, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Produtor conectado: ${AppData.usuarioLogado.value.nome}",
            fontSize = 13.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- 2. CADASTRO DE PRODUTO ---
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Text(
                    text = "Cadastrar Nova Colheita",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePrimario
                )

                Spacer(modifier = Modifier.height(10.dp))

                OutlinedTextField(
                    value = nomeProduto,
                    onValueChange = { nomeProduto = it },
                    label = { Text("Nome do Produto (Ex: Maçã Fuji)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = precoProduto,
                        onValueChange = { precoProduto = it },
                        label = { Text("Preço R$") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )

                    OutlinedTextField(
                        value = unidadeProduto,
                        onValueChange = { unidadeProduto = it },
                        label = { Text("Unidade (kg, maço)") },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        val preco = precoProduto.toDoubleOrNull() ?: 0.0
                        if (nomeProduto.isNotBlank() && preco > 0.0) {
                        val novoId = (AppData.listaProdutos.maxOfOrNull { it.id } ?: 0) + 1
                        val novoProduto = Produto(
                            id = novoId,
                            nome = nomeProduto,
                            preco = preco,
                            unidade = unidadeProduto.ifBlank { "unidade" },
                            categoria = categoriaSelecionada,
                            nomeFeirante = AppData.usuarioLogado.value.nome
                        )
                        AppData.adicionarProduto(novoProduto)
                        nomeProduto = ""
                        precoProduto = ""
                        mensagemSucesso = "Produto cadastrado com sucesso!"
                    }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario)
                ) {
                    Text("+ Inserir Produto na Feira", color = Color.White, fontWeight = FontWeight.Bold)
                }

                if (mensagemSucesso.isNotBlank()) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = mensagemSucesso,
                        color = VerdePrimario,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // --- 3. LISTAGEM COM EXCLUIR ---
        Text(
            text = "Produtos no Ar (${AppData.listaProdutos.size}):",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextoPrincipal
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(AppData.listaProdutos) { produto ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = produto.nome,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = TextoPrincipal
                            )
                            Text(
                                text = "R$ ${"%.2f".format(produto.preco)} / ${produto.unidade} • ${produto.nomeFeirante}",
                                fontSize = 12.sp,
                                color = TextoSecundario
                            )
                        }

                        OutlinedButton(
                            onClick = { AppData.removerProduto(produto.id) },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = VermelhoErro),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Excluir", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
