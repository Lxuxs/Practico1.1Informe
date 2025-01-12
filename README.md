### Desarrollar los siguiente ítems

> **Fecha de entrega:** Semana 12 (20 de dic. de 2024)

- Tablas de datos (nombre de columna, tipo, propósito y observaciones) - README.md
  
# Proyecto Integrador - Programación Funcional y Reactiva

Este repositorio contiene los datos y herramientas desarrolladas como parte del proyecto integrador del primer avance en el curso de Programación Funcional y Reactiva.

### ***Integrantes***
- Luis Sarango
- Alex Gutiérrez

## Tablas de Datos

A continuación, se describen las columnas disponibles en el conjunto de datos, junto con sus tipos, propósito y observaciones.

| **Nombre de Columna**        | **Tipo**       | **Propósito y Observaciones**                                                                               |
|-------------------------------|----------------|-------------------------------------------------------------------------------------------------------------|
| `adult`                      | `bool`         | Indica si la película es solo para adultos.                                                                |
| `belongs_to_collection`      | `object`       | Representa la colección a la que pertenece la película, si aplica.                                         |
| `budget`                     | `int64`        | Presupuesto asignado para la producción de la película.                                                    |
| `genres`                     | `object`       | Lista de géneros asociados con la película.                                                                |
| `homepage`                   | `object`       | Página web oficial de la película.                                                                         |
| `id`                         | `int64`        | Identificador único para cada película (clave primaria).                                                   |
| `imdb_id`                    | `object`       | Identificador único de la película en IMDb.                                                                |
| `original_language`          | `object`       | Idioma original de la película (código ISO 639-1).                                                         |
| `original_title`             | `object`       | Título original de la película.                                                                            |
| `overview`                   | `object`       | Resumen o sinopsis de la película.                                                                         |
| `popularity`                 | `float64`      | Medida de popularidad basada en diversos factores como interacciones y búsquedas.                          |
| `poster_path`                | `object`       | Ruta del póster oficial de la película.                                                                    |
| `production_companies`       | `object`       | Lista de empresas que participaron en la producción de la película.                                        |
| `production_countries`       | `object`       | Países donde se produjo la película.                                                                       |
| `release_date`               | `object`       | Fecha de estreno de la película.                                                                           |
| `revenue`                    | `int64`        | Ingresos generados por la película.                                                                        |
| `runtime`                    | `int64`        | Duración de la película en minutos.                                                                        |
| `spoken_languages`           | `object`       | Idiomas hablados en la película.                                                                           |
| `status`                     | `object`       | Estado de la película (por ejemplo, lanzada, en postproducción, etc.).                                      |
| `tagline`                    | `object`       | Frase o eslogan asociado con la película.                                                                  |
| `title`                      | `object`       | Título de la película.                                                                                     |
| `video`                      | `bool`         | Indica si el registro es de un video (generalmente para trailers).                                         |
| `vote_average`               | `float64`      | Promedio de votos recibidos por la película.                                                              |
| `vote_count`                 | `int64`        | Número total de votos recibidos por la película.                                                          |
| `keywords`                   | `object`       | Palabras clave asociadas con la película.                                                                 |
| `cast`                       | `object`       | Lista de actores que participaron en la película.                                                         |
| `crew`                       | `object`       | Lista de miembros del equipo técnico que trabajaron en la película.                                       |
| `ratings`                    | `object`       | Calificaciones detalladas recibidas por la película.                                                      |

---

### Notas Adicionales

- Este conjunto de datos es fundamental para realizar análisis funcionales y reactivos que permitan extraer información relevante del mundo cinematográfico.
- Los campos como `genres`, `cast`, y `crew` contienen listas o estructuras más complejas que pueden requerir técnicas de manipulación específicas.

## Codigo
```Scala

//Importación de bibliotecas
import kantan.csv._ //Para leer y escribir archivos CSV de forma eficiente
import kantan.csv.ops._ //Proporciona operadores y métodos útiles para trabajar con CSV
import kantan.csv.generic._ //Permite la conversión automática entre CSV y clases case class.
import java.io.File // Clase para trabajar con archivos en Scala.


/* Definición de la case class para mapear cada fila del archivo CSV en un objeto Scala
 donde cada atributo corresponde a una columna del archivo.*/
case class Peliculas(
                      adult: Boolean,
                      belongs_to_collection: String,
                      budget: Int,
                      genres: String,
                      homepage: String,
                      id: Int,
                      imdb_id: String,
                      original_language: String,
                      original_title: String,
                      overview: String,
                      popularity: Float,
                      poster_path: String,
                      production_companies: String,
                      production_countries: String,
                      release_date: String,
                      revenue: Int,
                      runtime: Int,
                      spoken_languages: String,
                      status: String,
                      tagline: String,
                      title: String,
                      video: Boolean,
                      vote_average: Float,
                      vote_count: Int,
                      keywords: String,
                      cast: String,
                      crew: String,
                      ratings: String
                    )

```
- Análisis de datos en columnas numéricas (estadísticas básicas)
 ```Scala
//Definimos un objeto llamado Peliculas
object Peliculas extends App {

  // Declaramos una variable constante que almacena la ubicación del archivo CSV
  val rutadelarchivo = "data/pi_movies_small.csv"

  // Leer el archivo CSV con ';' como delimitador
  val fuenteDatos = new File(rutadelarchivo)
    .readCsv[List, Peliculas](rfc.withHeader.withCellSeparator(';'))

  // Filtrar las filas exitosas y extraer solo las películas válidas
  val filtrar = fuenteDatos.collect { case Right(pelicula) => pelicula }

  // Función para calcular estadísticas  básicas de una lista de números
  def calculoEstadisticas(datos: Seq[Double], nColumna: String): Unit = {
    if (datos.isEmpty) { //Verifica si la lista de datos está vacía
      println(f" No se encontraron datos en las columnas '$nColumna'")
    } else {
      val conteo = datos.size //Devuelve el número total de elementos en la lista.
      val suma = datos.sum // Calcula la suma total de todos los elementos en la lista.
      val media = suma / conteo // Divide la suma total de los elementos entre la cantidad de elementos.
      val minimo = datos.min // Encuentra el menor valor en la lista.
      val maximo = datos.max // Encuentra el mayor valor en la lista.
      val variance = datos.map(d => math.pow(d - media, 2)).sum / conteo
      val DesviacionEstandar = math.sqrt(variance)

      println(f"--- Estadísticas para '$nColumna' ---\n" +
        f"Conteo: $conteo\n" +
        f"Media: $media%.2f\n" +
        f"Mínimo: $minimo%.2f\n" +
        f"Máximo: $maximo%.2f\n" +
        f"Desviación Estándar: $DesviacionEstandar%.2f\n")
    }
  }

  // Extraer columnas numéricas y calcular estadísticas
  val presupuestos   = filtrar.map(_.budget.toDouble).filter(_ >= 0)
  val popularidades  = filtrar.map(_.popularity.toDouble).filter(_ >= 0)
  val ingresos       = filtrar.map(_.revenue.toDouble).filter(_ >= 0)
  val duraciones     = filtrar.map(_.runtime.toDouble).filter(_ >= 0)
  val mediasVoto     = filtrar.map(_.vote_average.toDouble).filter(_ >= 0)
  val conteosVoto    = filtrar.map(_.vote_count.toDouble).filter(_ >= 0)

  // Calcular estadísticas para cada columna numérica
  calculoEstadisticas(presupuestos, "Budget")
  calculoEstadisticas(popularidades, "Popularity")
  calculoEstadisticas(ingresos, "Revenue")
  calculoEstadisticas(duraciones, "Runtime")
  calculoEstadisticas(mediasVoto, "Vote Average")
  calculoEstadisticas(conteosVoto, "Vote Count")

```
## Capturas

### Estadísticas para 'Budget'
![image](https://github.com/user-attachments/assets/2df53f1f-28a1-4ba1-adfe-d4d68aaea50b)

### Estadísticas para 'Popularity'
![image](https://github.com/user-attachments/assets/12df374e-7e1b-40a0-8099-acdbaf29544e)


### Estadísticas para 'Revenue'
![image](https://github.com/user-attachments/assets/830038f3-4bbd-45de-a1f6-2007dd769c45)

### Estadísticas para 'Runtime' 
![image](https://github.com/user-attachments/assets/e82595a5-287d-4f48-b173-d9567b40ac2d)

### Estadísticas para 'Vote Average' 
![image](https://github.com/user-attachments/assets/e22c78ab-2017-4834-b650-0aac8e80c38a)

### Estadísticas para 'Vote Count'
![image](https://github.com/user-attachments/assets/228945ae-d5c1-4055-bd68-3ba97b551ded)

- Análisis de datos en columnas tipo texto (algunas col. - distribución de frecuencia). OJO: no considerar columnas en formato JSON
## Codigo
```Scala
// Función para analizar la frecuencia de los elementos
  def analizarFrecuencia(datos: Seq[String], nColumna: String): Unit = {  
    if (datos.isEmpty) {
      println(f"No hay datos disponibles para analizar en la columna '$nColumna'.")
    } else {
      println(f"--- Análisis de la columna '$nColumna' ---")

      val topElementosFrecuentes = datos
        .groupBy(identity)
        .view.mapValues(_.size)
        .toSeq
        .sortBy(-_._2)
        .take(5)

      println(f"Frecuencia de los 5 elementos más comunes en '$nColumna':")
      topElementosFrecuentes.foreach { case (elem, count) =>
        println(f"$elem%-50s $count")
      }
      println()
    }
  }

  // Analizar los títulos
  val titulos = filtrar.map(_.title)
  analizarFrecuencia(titulos, "title")

  // Analizar los idiomas
  val idiomas = filtrar.map(_.original_language)
  analizarFrecuencia(idiomas, "original_language")
}

```
## Capturas
### Análisis de la columna 'title'
![image](https://github.com/user-attachments/assets/fd85dfe8-4fd6-49d2-b930-a9d1113696f3)

### Análisis de la columna 'original_language'
![image](https://github.com/user-attachments/assets/7d1e25b5-87a9-48ce-ad82-0006285bea65)

# Codigo Entero 
```Scala

//Importación de bibliotecas
import kantan.csv._ //Para leer y escribir archivos CSV de forma eficiente
import kantan.csv.ops._ //Proporciona operadores y métodos útiles para trabajar con CSV
import kantan.csv.generic._ //Permite la conversión automática entre CSV y clases case class.
import java.io.File // Clase para trabajar con archivos en Scala.

// Definición de la case class para representar una película
case class Peliculas(
                      adult: Boolean,
                      belongs_to_collection: String,
                      budget: Int,
                      genres: String,
                      homepage: String,
                      id: Int,
                      imdb_id: String,
                      original_language: String,
                      original_title: String,
                      overview: String,
                      popularity: Float,
                      poster_path: String,
                      production_companies: String,
                      production_countries: String,
                      release_date: String,
                      revenue: Int,
                      runtime: Int,
                      spoken_languages: String,
                      status: String,
                      tagline: String,
                      title: String,
                      video: Boolean,
                      vote_average: Float,
                      vote_count: Int,
                      keywords: String,
                      cast: String,
                      crew: String,
                      ratings: String
                    )

object Peliculas extends App {

  // Ruta del archivo csv
  val rutadelarchivo = "data/pi_movies_small.csv"

  // Leer el archivo CSV con ';' como delimitador
  val fuenteDatos = new File(rutadelarchivo)
    .readCsv[List, Peliculas](rfc.withHeader.withCellSeparator(';'))

  // Filtrar las filas exitosas y extraer solo las películas válidas
  val filtrar = fuenteDatos.collect { case Right(pelicula) => pelicula }

  // Función para calcular estadísticas  básicas de una lista de números
  def calculoEstadisticas(datos: Seq[Double], nColumna: String): Unit = {
    if (datos.isEmpty) {
      println(f"Datos no hay para la columna '$nColumna'")
    } else {
      val conteo = datos.size
      val suma = datos.sum
      val media = suma / conteo
      val minimo = datos.min
      val maximo = datos.max
      val variance = datos.map(d => math.pow(d - media, 2)).sum / conteo
      val DesviacionEstandar = math.sqrt(variance)

      println(f"--- Estadísticas para '$nColumna' ---\n" +
        f"Conteo: $conteo\n" +
        f"Media: $media%.2f\n" +
        f"Mínimo: $minimo%.2f\n" +
        f"Máximo: $maximo%.2f\n" +
        f"Desviación Estándar: $DesviacionEstandar%.2f\n")
    }
  }

  // Extraer columnas numéricas y calcular estadísticas
  val presupuestos   = filtrar.map(_.budget.toDouble).filter(_ >= 0)
  val popularidades  = filtrar.map(_.popularity.toDouble).filter(_ >= 0)
  val ingresos       = filtrar.map(_.revenue.toDouble).filter(_ >= 0)
  val duraciones     = filtrar.map(_.runtime.toDouble).filter(_ >= 0)
  val mediasVoto     = filtrar.map(_.vote_average.toDouble).filter(_ >= 0)
  val conteosVoto    = filtrar.map(_.vote_count.toDouble).filter(_ >= 0)

  // Calcular estadísticas para cada columna numérica
  calculoEstadisticas(presupuestos, "Budget")
  calculoEstadisticas(popularidades, "Popularity")
  calculoEstadisticas(ingresos, "Revenue")
  calculoEstadisticas(duraciones, "Runtime")
  calculoEstadisticas(mediasVoto, "Vote Average")
  calculoEstadisticas(conteosVoto, "Vote Count")

  // Función para analizar la frecuencia de los elementos
  def analizarFrecuencia(datos: Seq[String], nColumna: String): Unit = {
    if (datos.isEmpty) {
      println(f"No hay datos disponibles para analizar en la columna '$nColumna'.")
    } else {
      println(f"--- Análisis de la columna '$nColumna' ---")

      val topElementosFrecuentes = datos
        .groupBy(identity)
        .view.mapValues(_.size)
        .toSeq
        .sortBy(-_._2)
        .take(5)

      println(f"Frecuencia de los 5 elementos más comunes en '$nColumna':")
      topElementosFrecuentes.foreach { case (elem, count) =>
        println(f"$elem%-50s $count")
      }
      println()
    }
  }

  // Analizar los títulos
  val titulos = filtrar.map(_.title)
  analizarFrecuencia(titulos, "title")

  // Analizar los idiomas
  val idiomas = filtrar.map(_.original_language)
  analizarFrecuencia(idiomas, "original_language")
}
```

- Consultar sobre librería play-json (trabajo json en scala) y hacer:
  
  - Usar cualquier JSON pequeño para aprender play-json
  - 
    ## Archivo json

```Scala
    [
    {
        "nombre": "Alex Gutierrez",
        "edad": 23,
        "ciudad": "Loja",
        "aficiones": ["Leer", "Ciclismo", "Videojuegos"]
    },
    {
        "nombre": "Luis Sarango",
        "edad": 23,
        "ciudad": "Quito",
        "aficiones": ["Fotografía", "Senderismo", "Correr"]
    },
    {
        "nombre": "Juan Garcia",
        "edad": 27,
        "ciudad": "Cuenca",
        "aficiones": ["Fútbol", "Viajar", "Cocinar"]
    },
    {
        "nombre": "Ana Torres",
        "edad": 35,
        "ciudad": "Ambato",
        "aficiones": ["Pintura", "Correr", "Teatro"]
    }
  ]
```


  - Usar en algunas columnas JSON para obtener datos.
 ```Scala
import play.api.libs.json._
import scala.io.Source

// Definir la clase para representar los datos
case class Persona(nombre: String,
                   edad: Int,
                   ciudad: String,
                   aficiones: Seq[String])

object Persona extends App {
  // Formato implícito para convertir entre JSON y la clase Persona
  implicit val personaFormato: OFormat[Persona] = Json.format[Persona]

  // Leer el archivo JSON
  val jsonData = Source.fromFile("data/perfil_usuario.json").getLines.mkString
  val json = Json.parse(jsonData)

  // Convertir el JSON en una lista de objetos Persona
  val personas = json.as[Seq[Persona]]

  // Imprimir las personas deserializadas de manera más legible
  println("\n--- Personas Deserializadas ---")
  personas.foreach { persona =>
    println(s"Nombre: ${persona.nombre}")
    println(s"Edad: ${persona.edad}")
    println(s"Ciudad: ${persona.ciudad}")
    println(s"Aficiones: ${persona.aficiones.mkString(", ")}\n")
  }

  // Ejemplo de acceso a los nombres
  val nombres = personas.map(_.nombre)
  println("\n--- Nombres de las Personas ---")
  println(nombres.mkString(", "))

  // Filtrar personas mayores de 25 años
  val mayoresDe25 = personas.filter(_.edad > 25)
  println("\n--- Personas Mayores de 25 Años ---")
  mayoresDe25.foreach { persona =>
    println(s"Nombre: ${persona.nombre}, Edad: ${persona.edad}")
  }

  // Acceder a la primera persona
  val primeraPersona = personas.head
  println("\n--- Primera Persona ---")
  println(s"Nombre: ${primeraPersona.nombre}")
  println(s"Edad: ${primeraPersona.edad}")
  println(s"Ciudad: ${primeraPersona.ciudad}")

  // Agregar una nueva afición a la primera persona
  val personaActualizada = primeraPersona.copy(aficiones = primeraPersona.aficiones :+ "Pintura")
  println("\n--- Persona Actualizada ---")
  println(s"Nombre: ${personaActualizada.nombre}")
  println(s"Aficiones actualizadas: ${personaActualizada.aficiones.mkString(", ")}")
}

```
# Capturas

### Perfiles
```Scala
  // Imprimir las personas deserializadas de manera más legible
  println("\n--- Personas Deserializadas ---")
  personas.foreach { persona =>
    println(s"Nombre: ${persona.nombre}")
    println(s"Edad: ${persona.edad}")
    println(s"Ciudad: ${persona.ciudad}")
    println(s"Aficiones: ${persona.aficiones.mkString(", ")}\n")
```
![image](https://github.com/user-attachments/assets/bb5ed04a-9cf9-4eb0-aae9-fd8d2509f276)

### Nombres de las Personas
```Scala
// Ejemplo de acceso a los nombres
  val nombres = personas.map(_.nombre)
  println("\n--- Nombres de las Personas ---")
  println(nombres.mkString(", "))
```
 
![image](https://github.com/user-attachments/assets/9735097d-2206-4089-9205-9d188930172a)

### Personas Mayores de 25 Años
```Scala
 // Filtrar personas mayores de 25 años
  val mayoresDe25 = personas.filter(_.edad > 25)
  println("\n--- Personas Mayores de 25 Años ---")
  mayoresDe25.foreach { persona =>
    println(s"Nombre: ${persona.nombre}, Edad: ${persona.edad}")
  }
```
![image](https://github.com/user-attachments/assets/b3524411-6474-4d02-a492-958b6859df21)

### Primera Persona
```Scala
 // Acceder a la primera persona
  val primeraPersona = personas.head
  println("\n--- Primera Persona ---")
  println(s"Nombre: ${primeraPersona.nombre}")
  println(s"Edad: ${primeraPersona.edad}")
  println(s"Ciudad: ${primeraPersona.ciudad}")
```
![image](https://github.com/user-attachments/assets/27ba9dca-5504-4f4d-81d5-4b17ea38a419)

### Persona Actualizada
```Scala
// Agregar una nueva afición a la primera persona
  val personaActualizada = primeraPersona.copy(aficiones = primeraPersona.aficiones :+ "Pintura")
  println("\n--- Persona Actualizada ---")
  println(s"Nombre: ${personaActualizada.nombre}")
  println(s"Aficiones actualizadas: ${personaActualizada.aficiones.mkString(", ")}")
}
```
![image](https://github.com/user-attachments/assets/195babfe-cdbd-4ba2-828b-b4b1ac4ee794)


