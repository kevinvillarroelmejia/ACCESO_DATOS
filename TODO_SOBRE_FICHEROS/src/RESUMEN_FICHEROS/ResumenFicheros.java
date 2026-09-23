package RESUMEN_FICHEROS;


public class ResumenFicheros {
	public static void main(String[] args) {
		System.out.println("Abre este fichero y léelo: todo el resumen está en los comentarios.");
	}
}
/*
 * ============================================================
 * RESUMEN DE FICHEROS Y XML — REPASO RÁPIDO ANTES DEL EXAMEN
 * ============================================================
 * Solo lo que aparece en DAM_JAVA y ACCESO_DATOS.
 * Detalle: TEORIA/*.java · Práctica: EJERCICIOS/*.java · Origen: AUDITORIA.md
 *
 * ORDEN DE ESTUDIO
 *   1 SistemaDeFicheros   2 FicherosTexto_Lectura   3 FicherosTexto_Escritura
 *   4 FicherosBinarios_Datos   5 FicherosBinarios_Objetos   6 AccesoAleatorio   7 XML_DOM
 *
 * REGLAS QUE VALEN PARA TODO
 *   - SIEMPRE try/catch (catch (Exception e) + e.getMessage()).
 *   - SIEMPRE cerrar: close() o try-with-resources  try (... = new ...) { }
 *   - Rutas en constantes; File.separator para que valga en Windows y Linux;
 *     en Windows la barra se escribe doble: "D:\\carpeta\\fichero.txt".
 *   - Otra forma de excepciones: los métodos con  throws Exception  y el
 *     try/catch en el main (acceso aleatorio, XML, examen de animes).
 *
 *
 * ============================================================
 * 1 · SISTEMA DE FICHEROS
 * ============================================================
 *
 * TIPO: directorios y ficheros del disco (no lee ni escribe contenido)
 * Clase: File                          new File(ruta) NO crea nada
 * Apertura: -
 * Métodos importantes:
 *   new File(".").getAbsolutePath()    ruta del directorio actual
 *   System.getProperty("user.dir")     base de las rutas relativas
 *   exists()      -> boolean, existe (fichero o directorio)
 *   isFile()      -> boolean, existe y es un fichero
 *   mkdir()       -> crea 1 nivel      mkdirs() -> crea todos los niveles (usar este)
 *   createNewFile() -> true creado / false ya existía o no pudo
 *   getFreeSpace()  -> long en bytes; /1024/1024/1024 = GB (1024.0 para decimales)
 *   Crear fichero seguro: new FileWriter(ruta, true) + close()
 * Excepciones: Exception (FileWriter y createNewFile la lanzan)
 * OJO: if (!dir.mkdirs())  ->  error. Poner if (dir.mkdir()) es la lógica al revés.
 *
 *
 * ============================================================
 * 2 · FICHEROS DE TEXTO — LECTURA  (secuencial)
 * ============================================================
 *
 * TIPO: .txt / .csv (legibles)
 * Clases: FileReader, BufferedReader, File, Scanner, Path, Files
 * Apertura:
 *   new BufferedReader(new FileReader(ruta))
 *   new Scanner(new File(ruta))
 *   Path.of(ruta)  (no lanza excepción)
 * Lectura:
 *   while ((linea = lector.readLine()) != null) { ... }     null = fin
 *   while (lector.hasNextLine()) { linea = lector.nextLine(); }
 *   (ArrayList<String>) Files.readAllLines(path)             todo -> lista
 *   Files.readString(path)                                   todo -> String (con \n)
 * Recorridos: while compacto (el que usamos), do-while, while con lectura previa (el peor)
 * Procesar líneas:
 *   split(",")  split(";")  split(", ")  split(",\\s*")  split("\\s+")
 *   indexOf(":") + substring(0, pos) / substring(pos + 1)
 *   saltar cabecera: lector.readLine() antes del while
 *   ignorar líneas malas: if (campos.length == N)  /  try-catch dentro del while
 *   cargar en HashMap: login (usuario:clave), animes (17 Naruto), respuestas test
 * Excepciones: Exception, IOException, FileNotFoundException, NumberFormatException
 *
 *
 * ============================================================
 * 3 · FICHEROS DE TEXTO — ESCRITURA
 * ============================================================
 *
 * TIPO: .txt / .csv
 * Clases: FileWriter, BufferedWriter, PrintWriter, Files, Paths, StandardCharsets, StandardOpenOption
 * Modos: ESCRIBIR (borra y escribe, como >)   AÑADIR (al final, como >>)
 * Apertura / Escritura:
 *   new FileWriter(ruta[, true])                      write("texto\n")
 *   new BufferedWriter(new FileWriter(ruta[, true]))  write(...) + newLine()
 *   new PrintWriter(ruta)                             print / println / printf("%s %d %.2f")
 *   new PrintWriter(ruta, StandardCharsets.UTF_8)
 *   new PrintWriter(new FileWriter(ruta, true))       <- añadir con printf
 *   Files.write(Paths.get(ruta), lista[, UTF_8][, CREATE, APPEND])
 *   Files.writeString(ruta, texto[, UTF_8][, CREATE, APPEND])
 * Modificar un fichero: leer todo a un ArrayList -> cambiar -> reescribir el mismo fichero
 * Leer y escribir a la vez: try (BufferedReader l = ...; PrintWriter p = ...) { }
 * Excepciones: Exception (todas lanzan IOException)
 *
 *
 * ============================================================
 * 4 · FICHEROS BINARIOS DE DATOS  (secuencial)
 * ============================================================
 *
 * TIPO: .dat / .bin  (no legibles, ocupan menos, más rápidos)
 * Clases: DataOutputStream, DataInputStream, FileOutputStream, FileInputStream
 *         (+ BufferedOutputStream / BufferedInputStream en el ejercicio de votos)
 * Apertura:
 *   new DataOutputStream(new FileOutputStream(ruta))   OUTPUT = escribir
 *   new DataInputStream(new FileInputStream(ruta))     INPUT  = leer
 * Escritura: writeInt  writeDouble  writeBoolean  writeChar  writeUTF
 * Lectura:   readInt   readDouble   readBoolean   readChar   readUTF
 *            ¡MISMO ORDEN Y MISMOS TIPOS que al escribir!
 * Recorrer:  while (binario.available() > 0) { leer un registro }
 *            o un for si sabemos cuántos datos hay (4 votos)
 * Modificar: leer todo -> cambiar -> grabar todo
 * Excepciones: Exception (fichero no existe, leer de más, tipo distinto)
 *
 *
 * ============================================================
 * 5 · FICHEROS BINARIOS DE OBJETOS  (serialización / persistencia)
 * ============================================================
 *
 * Clase del objeto: implements Serializable  (en la padre; las hijas lo heredan)
 *   NO se graban: atributos static y atributos transient
 * Clases: ObjectOutputStream, ObjectInputStream, FileOutputStream, FileInputStream
 * Apertura:
 *   new ObjectOutputStream(new FileOutputStream(ruta))
 *   new ObjectInputStream(new FileInputStream(ruta))
 * Escritura: writeObject(objeto)  /  writeObject(lista)  /  writeObject(hashMap)
 * Lectura:   (Tarea) readObject()  /  (ArrayList<Tarea>) readObject()   CAST OBLIGATORIO
 *   "COMO HE GUARDADO UNA LISTA RECUPERO UNA LISTA"
 * Añadir / modificar: leer lista -> add / setX -> writeObject(lista) otra vez
 * Mostrar: método propio (mostrarTarea) o toString() con @Override
 * Patrón de examen: texto -> ArrayList de objetos -> .dat -> leer .dat y filtrar
 * Excepciones: Exception (clase no Serializable, fichero no existe, cast erróneo)
 *
 *
 * ============================================================
 * 6 · ACCESO ALEATORIO
 * ============================================================
 *
 * TIPO: .dat con REGISTROS DE TAMAÑO FIJO ("acceder donde queremos")
 * Clase: RandomAccessFile
 * Apertura: new RandomAccessFile(ruta, "r")   solo leer (excepción si no existe)
 *           new RandomAccessFile(ruta, "rw")  leer y escribir (lo crea si no existe)
 * Tamaños: int 4 · double 8 · boolean 1 · char 2 bytes
 *   TAMANYO_REGISTRO = TAMANYO_NOMBRE * 2 + 4   (nombre de 20 chars + int = 44)
 * Métodos: length()  seek(pos)  writeChar(c)  writeInt(n)  readChar()  readInt()
 *   (raf.write(c) graba 1 byte: error en Ficheros_accesoAletorio)
 * Posición:   (registro - 1) * TAMANYO_REGISTRO
 * ¿Existe?:   posicion >= raf.length() -> NO
 * Nº registros: raf.length() / TAMANYO_REGISTRO
 * Añadir:     raf.seek(raf.length())
 * escribirNombre: char[] con el nombre + espacios -> writeChar uno a uno
 * leerNombre:     readChar TAMANYO_NOMBRE veces -> trim()
 * Modificar:  seek -> leer -> seek OTRA VEZ -> escribir encima
 * Borrar:     borrado lógico: '*' + nombre.substring(1)   (si empieza por '*' está borrado)
 * Huecos:     crear el fichero con "*" en cada posición = libre
 * Excepciones: métodos con throws Exception, try/catch en el main
 *
 *
 * ============================================================
 * SECUENCIAL vs ALEATORIO
 * ============================================================
 *   Secuencial (texto, Data..., Object...): de principio a fin; para modificar se reescribe todo.
 *   Aleatorio (RandomAccessFile): directo a la posición con seek; se modifica un registro en su sitio.
 *
 *   REGLA PARA ELEGIR (TodoSobreFicheros/TEORIA_FICHEROS):
 *     ¿Texto legible?        -> fichero de texto
 *     ¿Objetos / primitivos? -> fichero binario
 *     ¿Posición directa?     -> acceso aleatorio
 *
 *
 * ============================================================
 * 7 · XML
 * ============================================================
 *
 * Clase: DocumentBuilderFactory, DocumentBuilder (javax.xml.parsers)
 *        Document, NodeList, Node, Element (org.w3c.dom)   <- Document de org.w3c.dom, NO de swing
 * Lectura (DOM: todo el XML a memoria):
 *   DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
 *   DocumentBuilder builder = factory.newDocumentBuilder();
 *   Document doc = builder.parse("agenda.xml");
 *   NodeList lista = doc.getElementsByTagName("contacto");
 *   for (int i = 0; i < lista.getLength(); i++) {
 *       Element contacto = (Element) lista.item(i);
 *       String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
 *   }
 * Escritura: NO la hemos dado (solo leer y buscar).
 * Métodos: newInstance · newDocumentBuilder · parse · getElementsByTagName · getLength · item · getTextContent
 * Buscar: for con bandera  (i < lista.getLength() && encontrado == false)
 * Excepciones: throws Exception (también en el main)
 * ============================================================
 */
