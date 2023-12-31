﻿# MercadoJava
Documentação SA6 - Desenvolvimento de Sistema de Mercado em Java

Introdução:
	
Desenvolvemos um sistema em Java com uma interface gráfica (GUI) para simular um mercado. Essencialmente, a aplicação permite a identificação do tipo de cliente, podendo ser classificado como VIP ou não. Além disso, o programa inclui um sistema de vendas de produtos, onde a busca é realizada por códigos de barras. A conclusão da compra e a aplicação de descontos em produtos previamente registrados são realizadas se o cliente for VIP. O software controla o estoque, mantendo um registro da quantidade de produtos, permitindo a atualização desses valores e a adição de novos itens ao catálogo. Todos os dados são centralizados em um banco de dados, garantindo a organização e armazenamento estruturado para facilitar o acesso, manipulação e recuperação eficiente das informações.

Componentes:
A partir das aplicações realizadas no programa, no qual foi subdividido em 3 telas principais que ilustram a estruturação do código, evidencia-se os resultados voltados para os seguintes elementos:

1.  Tela de Cadastro de Cliente VIP: Permitir o cadastro de um novo cliente. Elementos: Campos para inserção de dados do cliente, botão para cadastrar o novo cliente, permitindo que o operador identifique se o cliente é VIP ou não. Elementos da Interface: Campo de pesquisa através de CPF, botão para cadastrar novo cliente.

2. Tela de Registro de Vendas: Registrar as vendas de produtos a partir de um código de barras. Elementos:  Campo para inserção do código de barras do produto. Lista dinâmica dos produtos adicionados à venda. Lista final dos produtos, quantidades e preços. Total da compra. Opções de pagamento como cartão e dinheiro e um botão para finalizar a compra. Botões para adicionar e remover produtos. Indicação do desconto aplicado, finalizar a compra, exibindo o total e permitindo o pagamento

3. Tela de Gerenciamento de Estoque: Gerenciar o estoque da loja, atualizando quantidades e cadastrando novos produtos. Elementos: Lista de produtos em estoque. Campos para atualização de quantidades. Botões para adicionar novo produto. Indicadores visuais de estoque baixo, Tela de Cadastro de Novo Produto: Permitir o cadastro de um novo produto no estoque. Elementos da Campos para inserção de dados do novo produto e um botão funcional para cadastrar o novo produto


Objetivos

1.Implementar a lógica de identificação do cliente como VIP ou não. 
2.Criar funcionalidades para o registro de vendas de produtos através de códigos de barras. 
3.Integrar um banco de dados para armazenar informações sobre clientes, produtos e vendas.
4.Desenvolver a aplicação de descontos para produtos cadastrados, caso o cliente seja VIP. 
5.Fazer o gerenciamento de estoque, para garantir a atualização das quantidades e o cadastramento de novos produtos.
6.Fazer a saída de logs do Sistemas.
7.Desenvolver a interface gráfica utilizando para gera uma experiência visual amigável ao usuário.

No desenvolvimento da aplicação, foi utilizado JDBC, PostgreSQL, VSCode, e a linguagem de programação Java para realizar todo o sistema e suas respectivas funcionalidades, para atingir todos os critérios críticos e desejáveis implementados neste projeto.

Como usar

Assim que o programa é aberto a tela de Vendas é aberta, com opções para o colaborador efetuar a compra, editar, apagar e atualizar. Com locais para gerenciar a data da venda e a quantidade dos produtos.
A segunda tela (Tab) é a de estoque, que serve para cadastrar os produtos, com nome, código, quantidade e preço, cada produto é salvo na parte inferior da tela e também há a possibilidade de editar e apagar caso precise.
A terceira e ultima tela é para o cadastro de clientes, onde se precisa do Nome, CPF e Telefone para o cadastro de um novo cliente VIP, que pode ser selecionado na aba de vendas para receber acesso as promoções e descontos.


