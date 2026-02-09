# Arquitetura do Projeto CalcCupCompilationPartica

Este documento descreve a arquitetura, estrutura de camadas, tecnologias e organização de pastas do compilador da linguagem **Calc**.

---

## 1. Visão geral

O projeto é um **compilador** para a linguagem Calc, implementado em **Java**. O compilador suporta várias fases e modos de saída: análise léxica, geração de AST (árvore sintática abstrata), verificação de tipos, interpretação, tradução para C, geração de código assembly NASM (x86_64) e visualização da AST em formato DOT (Graphviz).

---

## 2. Tecnologias

| Componente | Tecnologia |
|------------|------------|
| **Linguagem** | Java |
| **Analisador léxico** | [JFlex](https://jflex.de/) (gerador de lexer) |
| **Analisador sintático** | [Java CUP](http://www2.cs.tum.edu/projects/cup/) (parser LALR) |
| **Geração de código C** | [StringTemplate 4](https://www.stringtemplate.org/) (ST-4) |
| **Build** | Makefile + shell script (`calcc.sh`) |
| **Saída assembly** | NASM (x86_64) |

### Ferramentas (pasta `tools/`)

- `jflex.jar` — geração do lexer
- `java-cup-11b.jar` — geração do parser
- `java-cup-11b-runtime.jar` — runtime do CUP em tempo de execução
- `ST-4.3.4.jar` — StringTemplate para templates de código C
- `antlr-4.13.2-complete.jar` — presente no classpath (opcional/legado)

---

## 3. Estrutura de pastas

```
CalcCupCompilationPartica/
├── CalcCompiler.java          # Ponto de entrada e orquestração dos modos
├── makefile                   # Build: geração do lexer/parser e compilação Java
├── calcc.sh                   # Wrapper para executar o compilador com classpath
├── ARQUITETURA.md             # Este documento
│
├── calc/
│   ├── parser/                # Análise léxica e sintática
│   │   ├── calc.flex          # Especificação do lexer (JFlex)
│   │   ├── calc.cup           # Gramática e ações do parser (CUP)
│   │   ├── CalcLexer.java     # Gerado por JFlex
│   │   ├── Lang2Parser.java    # Gerado por CUP
│   │   └── Lang2ParserSym.java # Símbolos/terminais (gerado por CUP)
│   │
│   └── nodes/                 # AST e visitantes
│       ├── CNode.java         # Classe base de todos os nós da AST
│       ├── CalcVisitor.java   # Interface do padrão Visitor para a AST
│       ├── Program.java       # Nó raiz: programa = lista de funções
│       ├── command/           # Comandos (atribuição, sequência, if, loop, etc.)
│       ├── decl/              # Declarações (Bind, FunDef)
│       ├── expr/              # Expressões (literais, binários, chamadas, etc.)
│       ├── types/             # Tipos da linguagem (Int, Float, Bool)
│       ├── environment/       # Ambiente de execução (Env)
│       ├── dotutils/          # Utilitários para exportação DOT (Graphviz)
│       └── visitors/          # Implementações do Visitor
│           ├── SimpleVisitor.java
│           ├── GVizVisitor.java      # AST → DOT
│           ├── InterpVisitor.java    # Interpretação
│           ├── CTranslateVisitor.java # AST → C (usa StringTemplate)
│           ├── tychkvisitor/         # Verificação de tipos
│           │   ├── TyChecker.java
│           │   ├── TypeEntry.java, VType, VTyInt, VTyFloat, etc.
│           │   └── CLTypes.java
│           └── codeGen/              # Geração de código NASM
│               ├── CodeGenVisitor.java
│               └── CodeGen.java
│
├── templates/
│   └── CTemplate.stg          # Templates StringTemplate para código C
│
├── tools/                     # JARs (JFlex, CUP, StringTemplate, etc.)
│
└── examples/                  # Programas de exemplo na linguagem Calc
    ├── exemplo.txt, exemplo1.txt, ...
    ├── math.txt
    └── rtsCalc.lst, rtsCalc.nasm  # Exemplos de saída
```

---

## 4. Arquitetura em camadas

O compilador segue um pipeline clássico de compilação, com separação entre **front-end** (análise), **AST** e **back-end** (análise semântica + geração de código/interpretação).

```
  [Fonte .txt]  →  Lexer  →  Parser  →  AST  →  Visitantes  →  Saída
       ↑              ↑         ↑        ↑           ↑
   calc.flex     calc.cup   CNode     TyChecker   .dot / exec / .c / .nasm
                       (JFlex)   (CUP)   (nodes/)   InterpVisitor
                                                CTranslateVisitor
                                                CodeGenVisitor
                                                GVizVisitor
```

### 4.1 Camada de análise (front-end)

- **Lexer** (`calc.flex` → `CalcLexer.java`): tokenização (identificadores, números, floats, palavras-chave, operadores, comentários).
- **Parser** (`calc.cup` → `Lang2Parser.java`): análise sintática LALR e construção da AST. Cada regra gramatical instancia nós concretos (`CNode`) e retorna a árvore.

### 4.2 Camada da AST (nós)

- **Raiz**: `Program` — contém uma lista de `FunDef` (definições de funções).
- **Declarações** (`decl/`): `FunDef`, `Bind` (parâmetros e variáveis locais).
- **Comandos** (`command/`): `CSeq`, `CAttr`, `If`, `Loop`, `Return`, `Print`.
- **Expressões** (`expr/`): `IntLit`, `FloatLit`, `BoolLit`, `Var`, `Plus`, `Sub`, `Times`, `Div`, `Lt`, `Lte`, `Eq`, `FCall`, etc.
- **Tipos** (`types/`): `TyInt`, `TyFloat`, `TyBool` (representação dos tipos na AST).

Todos os nós estendem `CNode` e implementam `accept(CalcVisitor v)`, permitindo o padrão **Visitor** para múltiplas passagens sobre a AST.

### 4.3 Camada de visitantes (análise semântica e back-end)

| Visitante | Função |
|-----------|--------|
| **TyChecker** | Verificação de tipos; preenche contexto de tipos e associa tipos aos nós. |
| **InterpVisitor** | Interpretação direta do programa (execução). |
| **GVizVisitor** | Geração de arquivo `.dot` para visualização da AST (Graphviz). |
| **CTranslateVisitor** | Tradução da AST para código C, usando templates em `templates/CTemplate.stg`. |
| **CodeGenVisitor** | Geração de código assembly NASM para x86_64. |
| **SimpleVisitor** | Visitante de exemplo/estrutura. |

A ordem típica em modos que geram código é: **Parser → TyChecker → (CTranslateVisitor ou CodeGenVisitor)**.

---

## 5. Fluxo de execução (CalcCompiler)

O `CalcCompiler` é o ponto de entrada. Conforme os argumentos, ele:

1. Cria o **lexer** a partir do arquivo de entrada.
2. Cria o **parser** conectado ao lexer.
3. Conforme a opção, executa um dos fluxos:

| Opção | Comportamento |
|-------|----------------|
| `-lex` | Lista os tokens (apenas lexer). |
| `-dot` | Parse → AST → GVizVisitor → grava `.dot`. |
| `-i` | Parse → AST → InterpVisitor (interpretação). |
| `-ty` | Parse → AST → TyChecker → InterpVisitor. |
| `-id` | Interpretação com debug (ambiente de execução). |
| `-viac` | TyChecker → CTranslateVisitor → grava `.c`. |
| `-c` | TyChecker → CodeGenVisitor → imprime NASM (x86_64). |

Em todos os modos que usam parser, a AST é obtida via `p.parse()`, que retorna o valor associado ao símbolo de start da gramática (tipicamente o nó `Program`).

---

## 6. Padrões de projeto

- **Visitor**: toda a AST é percorrida por visitantes especializados (tipos, interpretação, C, NASM, DOT), sem alterar as classes dos nós.
- **AST explícita**: nós da árvore são objetos Java (`CNode` e subclasses), construídos nas ações semânticas do CUP.
- **Contexto de tipos**: o `TyChecker` mantém tabelas (`TypeEntry`, `VType`, etc.) que são passadas ao `CodeGenVisitor` e ao `CTranslateVisitor` para geração de código tipada.

---

## 7. Build e execução

- **Compilar**: `make` (gera lexer/parser se necessário e compila as classes Java).
- **Limpar**: `make clean` (remove `.class` e arquivos gerados do parser).
- **Executar**: `./calcc.sh [opção] <arquivo>` ou  
  `java -cp .:tools/java-cup-11b-runtime.jar:tools/ST-4.3.4.jar CalcCompiler [opção] <arquivo>`.

Em ambiente Windows, o classpath pode usar `;` em vez de `:` e o script `calcc.sh` pode ser substituído por um `.bat` ou comando equivalente.

---

## 8. Linguagem fonte (Calc) — resumo

- **Tipos**: `Int`, `Float`, `Bool`.
- **Construções**: funções com parâmetros tipados e retorno tipado, atribuição, sequência de comandos, `if`/`else`, `while` (`?` e `:` na sintaxe dos exemplos), `return`, `print`.
- **Expressões**: literais, variáveis, operadores aritméticos e de comparação, chamadas de função.

Os arquivos em `examples/` servem como entrada de teste e referência da sintaxe aceita.

---

*Documento gerado para o projeto CalcCupCompilationPartica (TP2 — Compiladores).*
