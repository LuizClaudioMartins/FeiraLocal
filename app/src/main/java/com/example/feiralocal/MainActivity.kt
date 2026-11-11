package com.example.feiralocal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.feiralocal.ui.theme.FeiraLocalTheme

enum class TelaAtual {
    LOGIN,
    CATALOGO,
    CARRINHO,
    PAINEL_FEIRANTE
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FeiraLocalTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppFeiraLocal()
                }
            }
        }
    }
}

@Composable
fun AppFeiraLocal() {
    var telaAtual by remember { mutableStateOf(TelaAtual.LOGIN) }

    when (telaAtual) {
        TelaAtual.LOGIN -> {
            LoginScreen(
                onLoginSucesso = {
                    if (AppData.usuarioLogado.value.tipo == TipoUsuario.VENDEDOR) {
                        telaAtual = TelaAtual.PAINEL_FEIRANTE
                    } else {
                        telaAtual = TelaAtual.CATALOGO
                    }
                }
            )
        }

        TelaAtual.CATALOGO -> {
            CatalogoScreen(
                onAbrirCarrinho = { telaAtual = TelaAtual.CARRINHO },
                onAbrirPainelFeirante = { telaAtual = TelaAtual.PAINEL_FEIRANTE },
                onSair = { telaAtual = TelaAtual.LOGIN }
            )
        }

        TelaAtual.CARRINHO -> {
            CarrinhoScreen(
                onVoltarAoCatalogo = { telaAtual = TelaAtual.CATALOGO }
            )
        }

        TelaAtual.PAINEL_FEIRANTE -> {
            PainelFeiranteScreen(
                onVoltarAoCatalogo = { telaAtual = TelaAtual.CATALOGO },
                onSair = { telaAtual = TelaAtual.LOGIN }
            )
        }
    }
}

// PREVIEWS INDIVIDUAIS + FLUXO COMPLETO

@Preview(showBackground = true, name = "1. Tela de Login")
@Composable
fun PreviewLogin() {
    FeiraLocalTheme {
        LoginScreen(onLoginSucesso = {})
    }
}

@Preview(showBackground = true, name = "2. Catálogo do Comprador")
@Composable
fun PreviewCatalogo() {
    FeiraLocalTheme {
        CatalogoScreen(
            onAbrirCarrinho = {},
            onAbrirPainelFeirante = {},
            onSair = {}
        )
    }
}

@Preview(showBackground = true, name = "3. Carrinho / Checkout")
@Composable
fun PreviewCarrinho() {
    FeiraLocalTheme {
        CarrinhoScreen(onVoltarAoCatalogo = {})
    }
}

@Preview(showBackground = true, name = "4. Painel do Feirante")
@Composable
fun PreviewPainelFeirante() {
    FeiraLocalTheme {
        PainelFeiranteScreen(
            onVoltarAoCatalogo = {},
            onSair = {}
        )
    }
}

@Preview(showBackground = true, name = "5. App FeiraLocal Completo (Interativo)", heightDp = 700)
@Composable
fun PreviewAppCompleto() {
    FeiraLocalTheme {
        AppFeiraLocal()
    }
}