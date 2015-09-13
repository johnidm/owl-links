#Owl links App

[![Deploy to Heroku](https://www.herokucdn.com/deploy/button.png)](http://owllinks.co/)

Owllinks é um projeto open source utilizado para compartilhar links relacionados a tecnologia.

A ideia do projeto é manter uma base de dados de links para consulta e uma newsletter para notificação de novos links incluídos na plataforma.
	
Estão relacionados a esse projeto uma a [API Owl Links](https://github.com/johnidm/owl-links-api), uma versão mobile [Owl Links Mobile](https://github.com/johnidm/owl-links-mobile), um [Dashboard] (https://github.com/johnidm/owl-links-dashboard) e um [Bot para o Slack](https://github.com/johnidm/owl-links-slackbot).

### Como contribuir com o projeto 

As sugestões de melhorias devem ser feitas através de novas [issues]( https://github.com/johnidm/owl-links/issues/new).

Se você quer contribuir com código a melhor forma e ver a lista de [issues](https://github.com/johnidm/owl-links/issues) abertas.

### Montando o ambiente de desenvolvimento 

As tecnologias utilizadas no projeto são:

* [Play Framework – instalado através do TapeSafe Activator](https://www.typesafe.com/)
* [PostgreSQL](http://www.postgresql.org/)
* [Mongo DB](https://www.mongodb.org/)
* [Java 8](https://www.oracle.com/java/index.html)

Você deve fazer a instalação dessas ferramentas de acordo com o Sistema Operacional utilizado.

##### Configurando variáveis de ambiente 

###### Configurações obrigatórias.

* `export OWLLINKS_MONGODB_URL="mongodb://localhost:27017/"`
* `export OWLLINKS_DATABASE_URL="postgres://postgres:postgres@localhost/owl_db"`

###### Configurações opcionais.

* `export OWLLINKS_NOTIFICATION_EMAIL="johni.douglas.marangon@gmail.com"`
* `export OWLLINKS_SMTP_HOSTNAME="smtp.gmail.com"`
* `export OWLLINKS_SMTP_PORT="465"`
* `export OWLLINKS_SMTP_EMAIL="owl.links.notifications@gmail.com"`
* `export OWLLINKS_SMTP_PASSWORD="poR4@7pu"`

##### Executando o projeto 

Na pasta raiz do projeto execute o seguinte comandos para inciar o Owllinks:

* `activator run`

### Recomendação de livros

* https://leanpub.com/playframeworknapratica
* http://www.casadocodigo.com.br/products/livro-play-framework-java
