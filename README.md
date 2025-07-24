Essa é minha solução do projeto:
https://github.com/SmileGo-Tecnologia/subscription-platform-challenge

Estou utilizando o banco de dados imbutido do spring e configurando alguns valores iniciais na classe 
LocalDataBaseConfig.

Criei uma aplicação RESTful, com autenticação e autorização usando JWT, utilizei o AuthController para fazer o controle 
de login e cadastro de usuarios.    

Os controllers SubscriptionController e PaymentController são acessados via BearerToken.

Utilizei docker desktop para instalar o rabbitmq, https://www.rabbitmq.com/docs/download, criei uma fila chamada "rpe" 
e uma exchange "rpe.exchange" e uma routing key "routing-key-rpe"

Os seguintes Requisitos não foram implementados:
* Cache e Desempenho:
     Implemente cache para consultas de assinaturas e relatórios, utilizando Redis.
* Use padrões de resiliência, como retry e circuit breaker, para chamadas à API de pagamentos.
* Relatórios
* Documentação e Testes:
  * Documentação completa dos endpoints, pode utilizar Swagger.
  * Cobertura de testes unitários (90% ou mais) e testes de integração.

Eu Criei e exportei testes no postman que está no seguinte caminho: 
"src/test/resources/RPE - Desafio.postman_collection.json" os resultados dos testes podem ser encotrados em
"RPE - Desafio.postman_test_run"
