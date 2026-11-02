#language: pt

  @regressivo
Funcionalidade: Efetuação de login

  Contexto: Cadastro de um usuário bem-sucedido
    Dado que eu insira os seguintes dados do usuário:
      | campo       | valor          |
      | razaoSocial | Empresa Verde  |
      | cnpj        | 12345678906543 |
      | senha       | senha1234      |
      | role        | 0              |
    Quando eu enviar uma requisição para o endpoint "/auth/register"
    Então o status code da resposta deve ser 200
    E que o arquivo de registro esperado seja o "Registro de usuário bem-sucedido"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado

  Cenário: Login de usuário bem-sucedido
    Dado que eu efetue o login com o seguinte usuário:
      | campo | valor          |
      | cnpj  | 12345678906543 |
      | senha | senha1234      |
    Quando eu enviar uma requisição ao endpoint "/auth/login"
    Então o status code da resposta deve ser 200
    E que o arquivo esperado seja o "Login bem-sucedido"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado