# Tarea para escoger una tarifa

## Introducción

Respecto al enunciado TestJava2023_1.txt se han realizado los siguientes cambios:

- Se ha cambiado el nombre de la tabla Prices a Tariffs. Price puede dar lugar a confusión y aquí se ha decidido usar para denominar al **Value Object** que representa el precio de un producto por el par divisa y precio.
- Para ser consistente con el primer cambio descrito, se ha cambiado el nombre de PRICE_LIST a TARIFF_LIST.

## Diseño

### Tecnologías

- **Java 21**
- **Spring Boot**
- **H2 Database**
- **JUnit 5**
- **JaCoCo**
- **ArchUnit** para test de arquitectura.
- **Jmolecules** para anotaciones de arquitectura.
- **Lombok** para reducir boilerplate.

Se ha utilizado **Spring JDBC** que permite un performance óptimo para el caso de uso descrito. Se ha optado por no utilizar **Spring Data JPA** ya que no aportaría valor añadido en este caso y añade complejidad innecesaria. Los valores y el schema de la base de datos se han cargado mediante **data.sql** y **schema.sql**. En un caso real se utilizaría **Flyway** o **Liquibase** para gestionar las migraciones de base de datos. Se ha optado por **H2** para la base de datos ya que es una base de datos en memoria y no requiere configuración adicional.

La relación de las excepciones con las respuestas se ha realizado mediante un **ControllerAdvice**

### Arquitectura

#### Onion

Se ha decidido usar la arquitectura **Onion** para el desarrollo de la aplicación. Se ha optado por esta arquitectura porque combina la arquitectura **Ports and Adapters** con las capas típicas del **Domain Driven Design**. Para verificar que se sigue correctamente esta arquitectura, hemos utilizado **ArchUnit** aprovechando las anotaciones de **Jmolecules** lo cuál nos permite verificar que las dependencias entre las capas sean correctas. De esta forma, nos aseguramos que cualquier incremento en la funcionalidad de la aplicación no rompa los principios arquitectónicos del proyecto.

Utilizamos **Ports and Adapters**, sin embargo, podemos observar que, en *application* *PickingTariffQueryHandler* no está implementado como un adaptador. Cuando utilizamos de forma estricta **Ports and Adapters**, se optaría por crear un puerto *PickingTariffUseCase* pero no es algo obligatorio en la **Onion** y creo que en muchos trae más desventajas en forma de complejidad añadida que beneficios. Para verificar que se sigue correctamente estos principios, hemos utilizado también **ArchUnit**.

#### Decisiones

Se ha decidido implementar el **criterio de selección** de tarifa en la query SQL. Esto puede ser considerado un antipatrón, y por lo general yo optaría siempre por hacerlo en el dominio (me plantearía usar el patrón típico de DDD **Specification**), pero en el enunciado se hace hincapié en delegar esta responsabilidad a la base de datos. Esto, estrictamente hablando no es un **Repository**, no emula una colección. Sería más correcto tratarlo como un **QueryHandler**, ya que contiene una lógica muy específica para una **Query** muy concreta.

Aunque tengamos validación en el Controller (implementado en *infrastructure*) también la realizamos en *application* ya que nunca sabemos si el Controller será el único punto de entrada a nuestra aplicación. De esta forma, creamos una **Anticorruption layer**.

### Domain Driven Design

Se ha seguido el patrón **Domain Driven Design** para el desarrollo de la aplicación. Se ha creado el **AggregateRoot** *Tariffs*. Este agregado hace referencia a otros dos agregados que entendemos implícitos en el ejercicio propuesto: *Product* y *Brand*. Se ha decidido referenciarlos mediante **Value Objects** representando su identidad (práctica descrita en el RedBook), dicha identidad reside en sus respectivos módulos. La identidad del **AggregateRoot** se representa con su Id y sus equals y hashCode se basan en este atributo. Se han dejado los campos como *final* ya que en este ejercicio no se requiere modificación, pero en un caso real donde el scope incluyera modificar, se debería implementar la lógica de la operación en el propio agregado.

Se han establecido dos **Value Objects** que dotan de riqueza al dominio:
- **Price**: Representa el precio de un producto por el par divisa y precio.
- **Schedule**: Representa el horario de aplicación de una tarifa.

Se han implementado de forma inmutable y con una identidad basada en sus atributos, siguiendo los principios de **DDD**. Para esto, utilizamos los Records de Java.

Se podría argumentar que *Tariffs* es un **modelo anémico**, pero se ha decidido limitarse a lo descrito en el enunciado para no añadir complejidad innecesaria.

La capa de **dominio** estrictamente no debería tener anotaciones ni usar librerías de terceros, pero se ha decidido utilizar las anotaciones de validación de Jakarta para no cargar con mucho boilerplate el ejercicio.

### Testing

- Se han implementado tests E2E con los casos descritos en el TXT. Normalmente, sería preferible implementarlos en un proyecto aparte, pero por coherencia con lo pedido se han implementado en el mismo proyecto.
- Se han implementado test unitarios para todas las clases principales del proyecto. Los DTOs o Value Objects han quedado fuera del scope de los tests unitarios ya que su implementación es trivial y no aporta valor para un ejercicio de este tipo. Tiene un coverage del 97% en líneas y del 100% en branches.
- Se han implementado test de integración para el **QueryRunner**. Es importante verificar aquí los casos límite ya que es la propia base de datos la que se encarga de seleccionar la tarifa. En caso de haber implementado la lógica en el dominio (tomando un performance hit), esta lógica se probaría en unitarios. En un caso real lo implementaríamos con un TestContainer de la base de datos desplegada en producción.
- Se ha implementado un test de integración para probar desde el punto de entrada (Controller) hasta *application*.
- Podría haberse implementado un test de integración desde *application* hasta *infrastructure* pero en este caso aportaría poco valor. En una implementación real se debería implementar para completar el approach **Bottom-Up** que se ha seguido.
- Se han implementado test de arquitectura con **ArchUnit** para verificar que se siguen los principios de **Onion** y **Ports and Adapters**.
