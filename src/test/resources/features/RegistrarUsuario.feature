#language: pt

  @regressivo
Funcionalidade: Registro de Usuários
  Como usuário da API
  quero me registrar no aplicativo
  para acessar suas funcionalidades
  e receber o meu token de acesso

  Cenário: Cadastro de usuário bem-sucedido
    Dado que eu insira os seguintes dados do usuário:
      | campo       | valor          |
      | razaoSocial | Empresa Y      |
      | cnpj        | 98765432100710 |
      | senha       | senha1234      |
      | role        | 0              |
    Quando eu enviar uma requisição para o endpoint "/auth/register"
    Então o status code da resposta deve ser 200
    E que o arquivo de registro esperado seja o "Registro de usuário bem-sucedido"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado

  Cenário: Cadastro de um usuário sem sucesso ao inserir um CNPJ incorreto
    Dado que eu insira os seguintes dados do usuário:
      | campo       | valor     |
      | razaoSocial | Empresa X |
      | cnpj        | 987654321 |
      | senha       | senha1234 |
      | role        | 1         |
    Quando eu enviar uma requisição para o endpoint "/auth/register"
    Então o status code da resposta deve ser 400
    E a resposta de mensagem de erro deve ser "Por favor, informe um CNPJ válido."