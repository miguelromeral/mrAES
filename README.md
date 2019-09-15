# mrCiphER= (beta)

Descarga la versión beta gratis desde https://um62b.app.goo.gl/mrCiphER

## Introducción

Aplicación de cifrado y descifrado en Android. mrCiphER= cuenta con algunos de los algoritmos más conocidos de cifrado que existen actualmente (tales como AES, RSA o 3DES). Además, se han añadido otros como DES y Vigenere para trastear con ellos, ya que éstos se consideran no seguros para el cifrado en la actualidad.
Para exportar el contenido cifrado, se hace uso de la codificación Base64, por lo que el contenido puede ser recuperado en cualquier momento.

## Motivación

Este proyecto nació días después de una clase de criptografía en la que, gracias a algoritmos que transforman los datos de manera sutil y concisa, se demostraba cómo se podía almacenar contenido delicado en medios no seguros para diversos fines. En la actualidad, muchas aplicaciones y protocolos cuentan con su propia criptografía de manera transparente al usuario. Esta aplicación acerca la criptografía al usuario, pudiendo incluso cifrar su propio contenido de diferentes maneras posibles.

La aplicación debe su nombre a "Miguel Romeral's Cipher" de manera abreviada y con el formato parecido a Base64, con el característico = para poder decodificar las cadenas de texto.

El fin último de esta aplicación es puramente académico.

## Instalación

Esta aplicación requiere de Android 9 (API 28) como mínimo para poder ser instalada. De lo contrario, el smartphone no será capaz de instalarla.

Cualquier usuario puede descargarse el fichero .apk desde el siguiente enlace: https://um62b.app.goo.gl/mrCiphER
La aplicación es compilada y subida a una carpeta en Dropbox. En el enlace se encuentra la versión más reciente de la aplicación.
Es importante destacar que, puesto que no es una aplicación que se encuentre en Google Play, para poder instalarla será necesario tener el teléfono configurado para que permita la instalación de aplicaciones de origenes desconocidos (y confiar, además, en nuestra aplicación).

## Funcionalidades y Capturas

### AES-128

La idea original de mrCiphER= (luego se nos fue de las manos).

 ![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133022.png "Cifrado con AES")
![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133048.png "Descifrado con AES")

### RSA

La infraestructura de clave pública dominante. Antes tendrás que intercambiar la clave pública con tus amigos.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180419-230459.png "Cifrado con RSA")
![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180419-230516.png "Descifrado con RSA")

Si un amigo te pasa su clave pública (se puede exportar justo debajo del cifrado RSA), puedes almacenarla en la memoria del teléfono e importarla para operar con ella.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180419-230627.png "Conseguimos confidencialidad con Raúl")


### 3DES (ede)

¿Posiblemente el método más seguro de la aplicación? ¡Sin duda! La clave es de 168 bits (la de AES 128), por lo que puedes cifrar contenido sin miedo.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133243.png "Cifrado con Triple DES")

### DES

Aunque este algoritmo ya no se considera seguro, no está de más incluirlo.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133344.png "Cifrado con DES")

### Vigenere

Cifrado Vigenere, ¡como los de antes! Si la clave es una única letra, se obtiene un cifrado César.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133435.png "Cifrado con Vigenere")

### Generador de Hashes

Se pueden crear hashes en SHA-512 (más seguro).

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133609.png "Generador de Hash SHA-512")

Incluso MD5, aunque sea menos seguro.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133523.png "Generador de Hash MD5")
![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133536.png "Generador de Hash MD5")

### Herramienta Base64

A veces no queremos que el contenido se cifre en Base64, mejor en hexadecimal.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133703.png "Observando el contenido en Base64")

### Comprobación automática de actualizaciones

También se pueden comprobar si existen actualizaciones de forma manual. Se hace uso de Google Firebase para tener la versión más reciente.

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-134522.png "Actualizaciones disponibles")
![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180416-133730.png "Versión actualizada a la versión más reciente")

### Registro de cambios y modificaciones

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180419-230644.png "Cambios en las versiones")

### Contacto con el desarrollador

![alt text](https://github.com/miguelromeral/mrAES/blob/master/Capturas/Redimensionadas/screenshot_20180419-230654.png "Contacto con el desarrollador")

### ¡Y una actividad secreta! (no vale inspeccionar el código antes)

## Pruebas

Puesto que se trata de una aplicación en desarrollo, algunas de las funcionalidades están bajo pruebas por el propio desarrollador. Son bienvenidas sugerencias sobre la aplicación (añadir nuevas funcionalidades, errores encontrados, etc) al correo electrónico del desarrollador (ver en Contacto). 

Para trastear con el código, puedes duplicar el proyecto en cualquier momento. Este proyecto está desarrollado en el IDE Android Studio en su versión 3.0. De esta forma, podrás incluir los cambios que consideres necesarios e incluso añadir nuevas funcionalidades.
Como este proyecto es un proyecto personal desarrollado durante el tiempo libre del desarrollador, por lo que en este momento no dispone de la atención propia de una aplicación en producción.

## Próximas funcionalidades

* Información y ayuda acerca de cada algoritmo.

* Lector de notificaciones y descifrado automático de mensajes cifrados y codificados en Base64.

## Contacto

Correo electrónico: miguelangel.garciar@edu.uah.es

LinkedIn: Miguel Romeral (https://www.linkedin.com/in/miguelromeral/)

Twitter: @MiguelRomeral (https://twitter.com/MiguelRomeral)

## Licencia

Licencia Pública General GNU 3.0

## Política de Privacidad

Puedes leer la [política de privacidad de esta aplicación](https://github.com/miguelromeral/mrAES/blob/master/politica-privacidad-mrcipher-es.md) pulsando en el enlace.