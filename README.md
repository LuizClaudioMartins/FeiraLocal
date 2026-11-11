```
# FeiraLocal 🌱 — Marketplace Agrícola Acessível

> **Projeto Interdisciplinar: Desenvolvimento Para Dispositivos Móveis > Interfaces Digitais (UX/UI)**  
> Curso: Análise e Desenvolvimento de Sistemas — Faculdade ESUDA (2026)

---

## 👥 Integrantes da Equipe
* **Gabriel Normandio**
* **Luiz Claudio Martins**
* **Pethrus Souza**

---

## 📌 Sobre o Projeto

O **FeiraLocal** é um aplicativo Android nativo desenvolvido em **Kotlin** com **Jetpack Compose**, projetado para conectar pequenos produtores agrícolas da agricultura familiar diretamente aos consumidores urbanos.

Diferente de e-commerces convencionais com interfaces poluídas e navegação complexa, o FeiraLocal foi construído sob uma **arquitetura inclusiva e centrada no usuário**, solucionando barreiras reais de inclusão digital diagnosticadas durante a pesquisa de campo de UX.

---

## 🎯 Personas da Pesquisa de UX

O design do sistema foi fundamentado no atendimento direto a duas personas centrais:

* **Dona Maria (68 anos) — Compradora:** Possui visão reduzida (presbiopia), pouca familiaridade com smartphones modernos e receio de cometer erros em transações digitais. Necessita de tipografia legível, botões grandes e confirmações transparentes.
* **Seu José (54 anos) — Feirante / Produtor Rural:** Utiliza o celular no ambiente iluminado e movimentado da feira livre. Precisa de fluxos ágeis para cadastrar colheitas diárias, alterar preços e visualizar pedidos sem burocracia técnica.

---

## ♿ Acessibilidade &amp; Diretrizes WCAG 2.1 (Nível AA)

A interface cumpre rigorosamente as normas internacionais de acessibilidade digital:

1. **Alvos de Toque Ergonômicos:** Todos os botões, chips e controles de incremento/decremento respeitam a área mínima recomendada de **48dp × 48dp** (com botão principal atingindo 54dp), prevenindo toques acidentais por mãos trêmulas ou dedos calejados.
2. **Alto Contraste Visual:** 
   * Texto principal (`#212121`) sobre fundo claro (`#F9FBE7`): **16.1:1** (superando amplamente o mínimo exigido de 4.5:1).
   * Botão de Ação CTA Laranja (`#E65100`) com texto branco: **4.8:1** (conforme a WCAG para elementos interativos).
3. **Prevenção de Erros &amp; Feedback Visual:** Telas de estado vazio amigáveis, máscaras de senha com alternância explícita textual (*"Ver"* / *"Ocultar"*) e confirmação visual de pedidos com modalidade de entrega calculada em tempo real.

---

## 🛠️ Tecnologias e Arquitetura

* **Linguagem:** Kotlin 1.9+ (Null Safety estrita, tipos imutáveis e lambdas de alta ordem).
* **Interface:** Jetpack Compose &amp; Material Design 3.
* **Layouts Eficientes:** `LazyColumn` e `LazyRow` para reciclagem reativa de nós visuais em memória, evitando quedas de quadros (*jank*).
* **Gerenciamento de Estado:** `mutableStateListOf` e `mutableStateOf` com recomposição inteligente e isolamento em camada singleton de dados (`AppData`).
* **Design System Centralizado:** Paleta semântica com tokens declarativos em `ui.theme/Color.kt` e `Theme.kt`.

---

## 📂 Estrutura do Código-Fonte

```text
com.example.feiralocal/
├── Models.kt                 # Modelagem de dados (Usuario, Produto, ItemCarrinho, CategoriaProduto)
├── AppData.kt                # Estado reativo compartilhado, catálogo e regras de negócio
├── LoginScreen.kt            # Autenticação com senha mascarada, cadastro e atalhos de personas
├── CatalogoScreen.kt         # Vitrine do comprador com LazyRow (filtros) e LazyColumn (produtos)
├── CarrinhoScreen.kt         # Gestão de itens, cálculo de frete/retirada e tela de sucesso
├── PainelFeiranteScreen.kt   # Cadastro de colheitas e exclusão de itens pelo produtor
├── MainActivity.kt           # Orquestrador de fluxo, ciclo de vida e @Previews do Compose
└── ui.theme/
    ├── Color.kt              # Paleta cromática oficial (#2E7D32, #E65100, #F9FBE7, etc.)
    └── Theme.kt              # Configuração do MaterialTheme e tokens de superfície

```

---

## 🚀 Como Executar o Projeto

1. Clone o repositório ou faça o download dos arquivos:

```
git clone [suspicious link removed]

```

1. Abra o **Android Studio** (versão Ladybug ou superior recomendada).
2. Selecione a opção **Open** e escolha a pasta raiz do projeto.
3. Aguarde a sincronização automática do Gradle.
4. Conecte um aparelho Android via USB (com Depuração USB ativa) ou inicie um emulador (AVD).
5. Pressione `Shift + F10` ou clique no botão verde **Run 'app'**.

---

## 📱 Demonstração Rápida em Apresentações

O app conta com botões dedicados de demonstração na tela de login:

* **Entrar como Dona Maria:** Acesso instantâneo à jornada de compra, seleção de categorias e checkout.
* **Entrar como Seu José:** Acesso à área administrativa de colheitas com inserção dinâmica e exclusão em tempo real.