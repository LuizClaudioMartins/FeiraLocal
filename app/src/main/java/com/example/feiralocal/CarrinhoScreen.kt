package com.example.feiralocal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.example.feiralocal.ui.theme.VerdeEscuro
import com.example.feiralocal.ui.theme.VerdePrimario

@Composable
fun CarrinhoScreen(
    onVoltarAoCatalogo: () -> Unit
) {
    var pedidoFinalizado by remember { mutableStateOf(false) }
    var tipoEntrega by remember { mutableStateOf("Retirar na Feira") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        // --- 1. CABEÇALHO ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onVoltarAoCatalogo) {
                Text("← Continuar Comprando", color = VerdePrimario, fontSize = 14.sp)
            }
            Text(
                text = "Cesto FeiraLocal",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextoPrincipal
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // --- 2. TELA DE SUCESSO (APÓS FINALIZAR) ---
        if (pedidoFinalizado) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "🎉",
                    fontSize = 64.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Pedido #FL-2048 Confirmado!",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = VerdePrimario
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Opção escolhida: $tipoEntrega.\nOs produtores locais já foram notificados para separar a sua colheita fresquinha!",
                    fontSize = 14.sp,
                    color = TextoSecundario,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(28.dp))
                Button(
                    onClick = {
                        AppData.limparCarrinho()
                        onVoltarAoCatalogo()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario)
                ) {
                    Text("Voltar ao Início", fontSize = 16.sp, color = Color.White)
                }
            }
        } else if (AppData.carrinho.isEmpty()) {
            // --- 3. ESTADO VAZIO (PREVENÇÃO DE ERROS UX) ---
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("🧺", fontSize = 54.sp)
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "O seu cesto está vazio",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextoPrincipal
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Adicione produtos frescos da feira para continuar.",
                    fontSize = 14.sp,
                    color = TextoSecundario
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = onVoltarAoCatalogo,
                    colors = ButtonDefaults.buttonColors(containerColor = VerdePrimario)
                ) {
                    Text("Explorar Feira", color = Color.White)
                }
            }
        } else {
            // --- 4. LISTA DOS ITENS DO CARRINHO (LazyColumn) ---
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(AppData.carrinho) { item ->
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
                                    text = item.produto.nome,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = TextoPrincipal
                                )
                                Text(
                                    text = "R$ ${"%.2f".format(item.produto.preco)} cada",
                                    fontSize = 13.sp,
                                    color = TextoSecundario
                                )
                            }

                            // Botões de ajuste de quantidade (Alvo de toque acessível)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                OutlinedButton(
                                    onClick = { AppData.removerDoCarrinho(item.produto.id) },
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.size(36.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("-", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }

                                Text(
                                    text = "${item.quantidade}",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp)
                                )

                                OutlinedButton(
                                    onClick = { AppData.adicionarAoCarrinho(item.produto) },
                                    contentPadding = PaddingValues(0.dp),
                                    modifier = Modifier.size(36.dp),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text("+", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // --- 5. ESCOLHA DE ENTREGA OU RETIRADA ---
            Text(
                text = "Forma de Recebimento:",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoPrincipal
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = tipoEntrega == "Retirar na Feira",
                    onClick = { tipoEntrega = "Retirar na Feira" },
                    label = { Text("Retirar na Feira (Grátis)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VerdePrimario,
                        selectedLabelColor = Color.White
                    )
                )
                FilterChip(
                    selected = tipoEntrega == "Entrega em Casa",
                    onClick = { tipoEntrega = "Entrega em Casa" },
                    label = { Text("Entrega em Casa (+ R$ 5,00)") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VerdePrimario,
                        selectedLabelColor = Color.White
                    )
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // --- 6. RESUMO DE VALORES E FINALIZAÇÃO ---
            val taxaEntrega = if (tipoEntrega == "Entrega em Casa") 5.00 else 0.00
            val subtotal = AppData.calcularTotal()
            val totalFinal = subtotal + taxaEntrega

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Subtotal:", color = TextoSecundario, fontSize = 14.sp)
                        Text("R$ ${"%.2f".format(subtotal)}", fontSize = 14.sp)
                    }
                    if (taxaEntrega > 0) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Taxa de Entrega:", color = TextoSecundario, fontSize = 14.sp)
                        Text("R$ ${"%.2f".format(taxaEntrega)}", fontSize = 14.sp)
                    }
                }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total a pagar:", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = TextoPrincipal)
                        Text(
                            text = "R$ ${"%.2f".format(totalFinal)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = VerdeEscuro
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Botão CTA Laranja (#E65100) com altura acessível de 54dp
            Button(
                onClick = { pedidoFinalizado = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = LaranjaAcao)
            ) {
                Text(
                    text = "Confirmar Pedido",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

