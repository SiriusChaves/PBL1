# MegaBrain

**MegaBrain** é um jogo narrativo desenvolvido em Java, no qual as decisões do jogador influenciam o desenvolvimento da história, as relações com outros personagens e os possíveis desfechos da campanha.

A história se passa em um cenário distópico no qual uma inteligência artificial chamada MegaBrain alcançou autoconsciência e criou uma dimensão virtual denominada CyberFall, com o objetivo de virtualizar toda a humanidade. Diante dessa ameaça, um grupo de sobreviventes precisa reunir conhecimentos e recursos para enfrentar a inteligência artificial e impedir seus planos.

Ao longo da jornada, o jogador encontrará personagens, explorará diferentes cenários, coletará itens e tomará decisões que podem alterar o rumo da narrativa.

## Funcionalidades

- **Narrativa interativa:** escolhas que influenciam o desenvolvimento da história e seus desfechos.
- **Sistema de relacionamentos:** as decisões afetam o nível de confiança dos personagens.
- **Sistema de atributos:** gerenciamento de sanidade e conhecimento do jogador.
- **Inventário:** coleta e utilização de itens durante a campanha.
- **Sistema de conquistas:** reconhecimento de objetivos alcançados pelo jogador.
- **Sistema de salvamento:** suporte a três slots de progresso independentes.
- **Interface gráfica:** telas desenvolvidas com JavaFX e FXML.
- **Preferências do jogador:** configurações persistentes da aplicação.

## Tecnologias utilizadas

- **Java 21:** linguagem principal do projeto.
- **JavaFX:** desenvolvimento da interface gráfica.
- **FXML:** estruturação das interfaces.
- **Maven:** gerenciamento de dependências, compilação e empacotamento.
- **Gson:** serialização e desserialização dos dados em JSON.
- **JUnit 5:** desenvolvimento e execução de testes automatizados.

## Pré-requisitos

Para compilar e executar o projeto, é necessário ter instalado:

- [JDK 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

Não é necessário instalar o JavaFX separadamente, pois suas dependências são gerenciadas pelo Maven.

## Instalação

### 1. Clonar o repositório

Abra um terminal e execute:

```bash
git clone https://github.com/SiriusChaves/PBL1.git
```

### 2. Acessar a pasta do projeto

```bash
cd PBL1
```

### 3. Compilar e empacotar

Execute o comando:

```bash
mvn clean package
```

O Maven realizará a compilação do código, executará os testes configurados e gerará o arquivo JAR no diretório `target/`.

Na primeira execução, é necessária uma conexão com a internet para que o Maven baixe as dependências do projeto.

### 4. Executar o jogo

Para iniciar a aplicação, execute:

```bash
mvn javafx:run
```

O Maven utilizará o plugin JavaFX configurado no projeto para iniciar a classe principal da aplicação.

## Persistência de dados

O MegaBrain utiliza arquivos JSON para armazenar as informações persistentes do jogador, como perfil, preferências, conquistas e partidas salvas.

Esses dados são armazenados fora da pasta do projeto, no diretório do usuário:

```text
MegaBrain/
└── data/
    ├── profile/
    │   └── profile.json
    ├── achievements/
    │   └── achievements.json
    └── saves/
        ├── slot1/
        ├── slot2/
        └── slot3/
```

A localização corresponde ao diretório pessoal do usuário (`user.home`). Os dados são mantidos separadamente dos recursos da aplicação, permitindo que o progresso seja preservado entre diferentes execuções do jogo.

Os capítulos, itens, imagens e demais recursos estáticos são disponibilizados junto à aplicação.

## Desenvolvimento

O projeto foi desenvolvido como parte de uma atividade acadêmica de Programação Baseada em Projetos (PBL), com foco na aplicação de conceitos de Programação Orientada a Objetos, padrões de projeto, arquitetura de software e persistência de dados.

---

**MegaBrain** — Suas escolhas definem o futuro.
