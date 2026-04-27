# Decisiones de Diseño — BiblioTech

## Arquitectura en Capas

El sistema se organizó en cuatro capas bien definidas:

| Capa         | Responsabilidad                                |
|:-------------|:-----------------------------------------------|
| `model`      | Entidades del dominio (Records, Clases, Enums) |
| `repository` | Acceso y persistencia de datos en memoria      |
| `service`    | Lógica de negocio e implementación de reglas   |
| `Main`       | Punto de entrada y CLI                         |

Esta separación permite modificar cualquier capa sin afectar las demás. 
Por ejemplo, reemplazar el almacenamiento en memoria por una base de datos real solo requeriría cambiar las implementaciones de repositorio, sin tocar la lógica de negocio.

---

## Principios SOLID

### SRP — Single Responsibility
Cada clase tiene una única responsabilidad:
- `LibroServiceImpl` gestiona únicamente la lógica de libros.
- `SocioServiceImpl` gestiona únicamente la lógica de socios.
- `PrestamoServiceImpl` gestiona únicamente el ciclo de préstamos.

### OCP — Open/Closed
El sistema es extensible sin modificar código existente. 
Para agregar un nuevo tipo de recurso (por ejemplo, una Revista) basta con crear un nuevo record que implemente la interfaz `Recurso`, sin modificar ninguna clase existente.

### LSP — Liskov Substitution
`SocioEstudiante` y `SocioDocente` son intercambiables en cualquier contexto donde se espera un `Socio`, respetando el contrato definido por `getLimitePrestamos()`.

### ISP — Interface Segregation
Se definieron interfaces específicas y separadas (`LibroService`, `SocioService`, `PrestamoService`) en lugar de una única interfaz general, evitando que las implementaciones dependan de métodos que no utilizan.

### DIP — Dependency Inversion
Los servicios dependen de la interfaz genérica `Repository<T, ID>` y no de implementaciones concretas. Todas las dependencias se inyectan por constructor, lo que facilita el testing y el reemplazo de implementaciones.

---

## Uso de Records

Se utilizaron records de Java para las entidades inmutables del sistema: 
`Libro`, `Ebook` y `Prestamo`. Esto garantiza que los datos no puedan ser modificados accidentalmente una vez creados, reduciendo errores y mejorando la legibilidad del código.

---

## Uso de Optional

Se evitó el uso de `null` en todos los retornos de búsqueda. 
Los métodos como `buscarPorId()` y `buscarPorDni()` retornan `Optional<T>`, obligando al código cliente a manejar explícitamente el caso en que no se encuentre un resultado.

---

## Jerarquía de Excepciones

Se definió `BibliotecaException` como clase base checked, de la cual heredan todas las excepciones de negocio:

| Excepción                           | Situación                               |
|:------------------------------------|:----------------------------------------|
| `LibroNoEncontradoException`        | El ISBN no existe en el sistema         |
| `LibroNoDisponibleException`        | El libro ya está prestado               |
| `SocioNoEncontradoException`        | El DNI no existe en el sistema          |
| `LimitePrestamosAlcanzadoException` | El socio alcanzó su límite de préstamos |

Este diseño evita el uso de `RuntimeException` genéricas y permite un manejo granular y controlado de cada error de negocio.

---

## Almacenamiento en Memoria

Se implementó el almacenamiento usando:
- `HashMap` para libros y socios, permitiendo acceso en O(1) por clave (ISBN y DNI respectivamente).
- `ArrayList` para el historial de préstamos, preservando el orden cronológico de las transacciones.

Esta decisión facilita una futura migración a persistencia real (base de datos o archivos) sin necesidad de modificar la lógica de negocio.