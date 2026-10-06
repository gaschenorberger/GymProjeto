# Driver JDBC do PostgreSQL

O projeto usa o arquivo `postgresql-42.7.13.jar` nesta pasta para acessar o PostgreSQL.

O JAR já está configurado no classpath do projeto em `nbproject/project.properties`,
portanto não é necessário adicioná-lo manualmente no NetBeans.

Sem o driver ou sem uma conexão disponível, o sistema informa o motivo no console e
continua em modo demonstrativo em memória.
