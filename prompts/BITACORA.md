# Bitacora de prompts 
  
Laboratorio 06: Fundamentos de Ingenieria de Prompts. 
  
Herramienta de IA usada: (escribe aqui cual usaste) 
  
## Ejercicio 2: Tokens y ventana de contexto 
  
## Ejercicio 3: Temperatura 
  
## Ejercicio 4: Prompt vago vs estructurado 
  
## Ejercicio 5: Anatomia de un prompt 

| Texto | Caracteres | Tokens | 
|-------|------------|--------| 
| Los estudiantes programan en Java. | | | 
| The students program in Java. | | | 
| desafortunadamente | | | 

- En el paso 4, cuando preguntamos dando los datos, la IA responde de acuerdo a ellos.
- En el paso 5, cuando lo hacemos sin los datos, segun si se tiene activado o no la memoria entre chats, recordara y contestara lo adecuado, de lo contrario pedira informacion para su respuesta.


| Temperatura | % de BiblioTec | Nombres en los 5 intentos |
|-------------|----------------|---------------------------| 
| 0 | 100%|BiblioTec, BiblioTec, BiblioTec, BiblioTec, BiblioTec| 
| 0.5 | 65.3%|BiblioTec, LibroYa, BiblioTec, BiblioTec, LibroYa| 
| 1 | 44.5%|BiblioTec, PaginaLibre, BiblioTec, LectoGo, LectoGo| 
| 1.8 | 32.2%|BiblioTec, PrestaLibro, BiblioTec, LibroYa, BiblioTec| 

| Criterio | Prompt vago | Prompt estructurado | 
|----------|-------------|---------------------| 
| Menciona el objetivo del sistema |No|Si| 
| Menciona a los usuarios principales |No|Si| 
| Tiene exactamente 3 funcionalidades |No|Si| 
| Esta en 3 parrafos |No|Si| 
| Lo usaria en un informe real |Si|Si| 


| Componente | Texto de mi prompt | 
|------------|--------------------| 
| Rol | Desarrollador de Java profesional| 
| Instruccion | Crea un programa para gestionar los productos de una tienda| 
| Contexto | Eres el experto esta vez, y te necesitan para hacer el programa, dejando explicaciones del porque de tus decisiones en el proyecto, para los futuros desarrolladores que lo mejoraran, continuaran, o simplemente aprenderan.| 
| Ejemplo | Utiliza esta forma de hacer los metodos por favor: getPrecio(), setPrecio(double Precio) ["getters and setters"]| 
| Formato | Explicalo paso a paso, anexando tu logica y explicandola de manera simplificada.| 


