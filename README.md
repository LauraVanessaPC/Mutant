# Mutant-app
API Rest que expone ciertas operaciones como analizar una matriz de caracteres que se corresponden con la secuencia de un adn humano, para determinar si es mutante o no; también lista los adn's verificados que se encuentran almacenados en base de datos, y muestra las cifras estadísticas que segmentan los humanos de los mutantes y la proporción de estos últimos sobre los primeros.

Este proyecto es construído con Arquitectura Limpia. Separa la capa del dominio de la infraestructura.
Dentro de la capa del dominio nos encontramos con las entidades de negocio y los casos de uso que hacen referencia a la lógica del negocio.
En la capa de infraestructura están los driven-adapters que configuran o adaptan las implementaciones externas al core de nuestro sistema, como lo es la conexión a base de datos y las operaciones sobre la misma. También, están los entry-points que referencian esos puntos de entrada a la aplicación o el inicio de los flujos de negocio.

Este proyecto para conexión a base de datos utiliza una base de datos MongoDB embebida y en memoria, que es una base de datos documental que sirve para almacenar los adn's verificados y el resultado de dicha verificación.

Para suplir el requisito de la concurrencia de usuarios o peticiones, se hace uso de programación reactiva y se implementan los Monos y los Flux que permiten gestionar los flujo de datos asíncronos y optimizar el uso de recursos.

Este proyecto fue subido a la nube de AWS mediante el servicio Elastic Beanstalk, y la url base desde donde se puede acceder al API rest diseñada, es la siguiente:
http://mutantapp-env.eba-32pxjiwp.us-east-1.elasticbeanstalk.com/

Esta Api tiene 3 operaciones expuestas:
1. mutant/
2. stats/
3. list/

Para saber más acerca de cómo consumirla, dirigirse al archivo documentOpenAPI.yaml que se encuentra en la raíz del proyecto, y que referencia la documentación en OpenAPI v.3.0.1. Puede ser visto desde el editor de Swagger en línea: https://editor.swagger.io/.



