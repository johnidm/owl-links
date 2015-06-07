#Owl links App
[http://owl-links.herokuapp.com/](http://owl-links.herokuapp.com/)
=================================

Owl link (em tradução livre Coruja links) é um projeto open source para compartilhamento e distribuição de links.

A ideia do projeto é manter uma base de dados de links(baseda em tags) para posterior consulta, e também manter uma newsletter para notificação de novos links cadastrados.

O projeto foi escrito utilizando o [Play Framework 2.3.x](https://www.playframework.com/) e Java 8 entre outras bibliotecas Java.

Estão realcionados a esse projeto uma API - [API Owl Links](https://github.com/johnidm/owl-links-api), uma versão mobile [Owl Links Mobile](https://github.com/johnidm/owl-links-mobile) e um [Dashboard] (https://github.com/johnidm/owl-links-dashboard).


### Você quer contribuir com o projeto

[Instale o Typesafe Activator](http://www.johnidouglas.com.br/install-typesafe-activator-play-framework/)

Configure as variáveis de ambiente

URL de acesso a base de dados postgre
```
OWLLINKS_DATABASE_URL
```

URL de acesso ao MongoDB
```
OWLLINKS_MONGODB_URL
```

Dados de SMTP para envio de emails
```
OWLLINKS_SMTP_HOSTNAME
OWLLINKS_SMTP_PORT
OWLLINKS_SMTP_EMAIL
OWLLINKS_SMTP_PASSWORD
```

E-mail para notificações
```
OWLLINKS_NOTIFYCATION_EMAIL 
```

```
git clone https://github.com/johnidm/owl-links
```

Caso esteja usando o Eclipse, execute:

```
activator eclipse
```

Depois execute:

```
activator ~run
```

Pronto agora é só codar.

### Recomendaçao de livros

* https://leanpub.com/playframeworknapratica
* http://www.casadocodigo.com.br/products/livro-play-framework-java
