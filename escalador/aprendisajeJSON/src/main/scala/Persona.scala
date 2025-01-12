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
