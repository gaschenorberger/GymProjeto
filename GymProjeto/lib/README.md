# Driver JDBC do MySQL

Coloque nesta pasta o arquivo `mysql-connector-j-8.x.x.jar` para utilizar o MySQL.

O código não possui dependência de compilação com o driver, mas ele precisa estar no
classpath durante a execução. No NetBeans, use **Propriedades do Projeto > Bibliotecas >
Adicionar JAR/Pasta** e selecione o arquivo desta pasta.

Sem o driver ou sem uma conexão disponível, o sistema informa o motivo no console e
continua em modo demonstrativo em memória.
