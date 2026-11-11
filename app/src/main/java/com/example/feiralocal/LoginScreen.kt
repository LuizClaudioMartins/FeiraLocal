package com.example.feiralocal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.feiralocal.ui.theme.LaranjaAcao
import com.example.feiralocal.ui.theme.TextoPrincipal
import com.example.feiralocal.ui.theme.TextoSecundario
import com.example.feiralocal.ui.theme.VerdeEscuro
import com.example.feiralocal.ui.theme.VerdePrimario

@Composable
fun LoginScreen(
    onLoginSucesso: () -> Unit
) {
    // Alterna entre a aba "Entrar" e "Criar Conta"
    var modoCadastro by remember { mutableStateOf(false) }

    // Campos do formulário
    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("maria@email.com") }
    var senha by remember { mutableStateOf("123456") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var tipoEscolhido by remember { mutableStateOf(TipoUsuario.COMPRADOR) }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // --- 1. CABEÇALHO ---
        Text(
            text = "FeiraLocal 🌱",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = VerdePrimario
        )
        Text(
            text = if (modoCadastro) "Crie a sua conta de feirante ou cliente" else "Do produtor rural direto para a sua mesa",
            fontSize = 14.sp,
            color = TextoSecundario
        )

        Spacer(modifier = Modifier.height(24.dp))

        // --- 2. ABAS: ENTRAR vs CRIAR CONTA ---
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(
                onClick = { modoCadastro = false },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (!modoCadastro) VerdePrimario else Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Entrar", color = Color.White)
            }

            Button(
                onClick = {
                    modoCadastro = true
                    email = ""
                    senha = ""
                },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (modoCadastro) VerdePrimario else Color.LightGray
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Criar Conta", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // --- 3. CAMPOS DO FORMULÁRIO ---

        // Campo Nome (só aparece no modo cadastro)
        if (modoCadastro) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Seu Nome Completo") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(14.dp))

            // Seleção de Tipo de Perfil (Comprador ou Vendedor)
            Text(
                text = "Eu quero:",
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = TextoPrincipal,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = tipoEscolhido == TipoUsuario.COMPRADOR,
                    onClick = { tipoEscolhido = TipoUsuario.COMPRADOR },
                    label = { Text("Comprar na Feira") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = VerdePrimario,
                        selectedLabelColor = Color.White
                    )
                )
                FilterChip(
                    selected = tipoEscolhido == TipoUsuario.VENDEDOR,
                    onClick = { tipoEscolhido = TipoUsuario.VENDEDOR },
                    label = { Text("Vender / Feirante") },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = LaranjaAcao,
                        selectedLabelColor = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.height(14.dp))
        }

        // Campo E-mail
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Campo Senha com proteção visual
        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            singleLine = true,
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                TextButton(onClick = { senhaVisivel = !senhaVisivel }) {
                    Text(
                        text = if (senhaVisivel) "Ocultar" else "Ver",
                        color = VerdePrimario,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // --- 4. BOTÃO DE AÇÃO (ENTRAR OU CRIAR) ---
        Button(
            onClick = {
                if (modoCadastro) {
                    // Cadastra um novo utilizador real com os dados que você digitou
                    val novoId = (AppData.listaUsuarios.maxOfOrNull { it.id } ?: 0) + 1
                    val novoUsuario = Usuario(
                        id = novoId,
                        nome = nome.ifBlank { "Novo Utilizador" },
                        email = email.ifBlank { "usuario@feiralocal.com" },
                        tipo = tipoEscolhido
                    )
                    AppData.cadastrarUsuario(novoUsuario)
                    AppData.usuarioLogado.value = novoUsuario
                    onLoginSucesso()
                } else {
                    // Entra com utilizador existente
                    val usuarioEncontrado = AppData.listaUsuarios.find { it.email == email }
                        ?: AppData.listaUsuarios.first()
                    AppData.usuarioLogado.value = usuarioEncontrado
                    onLoginSucesso()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (modoCadastro && tipoEscolhido == TipoUsuario.VENDEDOR) LaranjaAcao else VerdePrimario
        )
        ) {
        Text(
            text = if (modoCadastro) "Cadastrar e Entrar" else "Entrar",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }

        // --- 5. ATALHOS RÁPIDOS DA PESQUISA (Apenas na aba Entrar) ---
        if (!modoCadastro) {
            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Atalhos Rápidos de Demonstração:",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextoPrincipal
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedButton(
                onClick = {
                    AppData.usuarioLogado.value = AppData.listaUsuarios.first { it.tipo == TipoUsuario.COMPRADOR }
                    onLoginSucesso()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Entrar como Dona Maria (Compradora)", fontSize = 13.sp, color = VerdeEscuro)
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    AppData.usuarioLogado.value = AppData.listaUsuarios.first { it.tipo == TipoUsuario.VENDEDOR }
                    onLoginSucesso()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("Entrar como Seu José (Feirante)", fontSize = 13.sp, color = LaranjaAcao)
            }
        }
    }
}