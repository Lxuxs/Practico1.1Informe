import kantan.csv._
import kantan.csv.ops._
import kantan.csv.generic._
import java.io.File


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
