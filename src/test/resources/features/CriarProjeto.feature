#language: pt

  @funcional
Funcionalidade: Criar um projeto

  Cenário: Login de usuário bem-sucedido para adquirir token
    Dado que eu efetue o login com o seguinte usuário:
      | campo | valor          |
      | cnpj  | 12345678909091 |
      | senha | senha1234      |
    Quando eu enviar uma requisição ao endpoint "/auth/login"
    Então o status code da resposta deve ser 200
    E que o arquivo esperado seja o "Login bem-sucedido"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado

  Cenário: Criação de um projeto bem-sucedido
    Dado que eu insira os seguintes dados de um projeto:
      | campo                 | valor                                       |
      | empresaDesenvolvedora | 15                                          |
      | nome                  | Esverdear o mundo                           |
      | descricao             | Vamos deixar o nosso mundo verde novamente! |
      | bioma                 | AMAZONIA                                    |
      | latitude              | -5.123456                                   |
      | longitude             | -65.789012                                  |
      | status                | APROVADO                                    |
    Quando eu enviar uma requisição direcionada ao endpoint "/api/projetos"
    Então o status code da resposta da requisição deve ser 201
    E que o arquivo esperado seja o "Projeto criado com sucesso"
    Então a resposta da requisição deve estar em conformidade com o contrato selecionado