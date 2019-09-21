# <img alt='mrCiphER=' src='https://lh3.googleusercontent.com/5NF-jrGobC17Gj0luyiVKXoqgBAGysq_60lcUJpmTD9BEN0IO3z1MsE_WClQgpR4-KM=s180-rw' height="35" width="auto" /> mrCiphER= ![](https://img.shields.io/badge/android-9.0-green)

<a href='https://play.google.com/store/apps/details?id=es.uah.edu.miguelangelgarciar.mraes&pcampaignid=MKT-Other-global-all-co-prtnr-py-PartBadge-Mar2515-1'><img alt='Get it on Google Play' src='https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png' height="70" width="auto" /></a>

## Introducción

Aplicación de cifrado y descifrado en Android. mrCiphER= cuenta con algunos de los algoritmos más conocidos de cifrado que existen actualmente (tales como AES, RSA o 3DES). Además, se han añadido otros como DES y Vigenere para trastear con ellos, ya que éstos se consideran no seguros para el cifrado en la actualidad.
Para exportar el contenido cifrado, se hace uso de la codificación Base64, por lo que el contenido puede ser recuperado en cualquier momento.

## Motivación

Este proyecto nació días después de una clase de criptografía en la que, gracias a algoritmos que transforman los datos de manera sutil y concisa, se demostraba cómo se podía almacenar contenido delicado en medios no seguros para diversos fines. En la actualidad, muchas aplicaciones y protocolos cuentan con su propia criptografía de manera transparente al usuario. Esta aplicación acerca la criptografía al usuario, pudiendo incluso cifrar su propio contenido de diferentes maneras posibles.

La aplicación debe su nombre a "Miguel Romeral's Cipher" de manera abreviada y con el formato parecido a Base64, con el característico = para poder decodificar las cadenas de texto.

El fin último de esta aplicación es puramente académico.

## Instalación

Esta aplicación requiere de Android 9 (API 28) como mínimo para poder ser instalada. De lo contrario, el smartphone no será capaz de instalarla.

Cualquier usuario puede descargarse la última versión desde el siguiente enlace: http://bit.ly/2kYgyNj

## Funcionalidades y Capturas

### AES-128

La idea original de mrCiphER= (luego se nos fue de las manos).

 ![Cifrado con AES](https://lh3.googleusercontent.com/p83Hc17t7mAdokIIo8xByEZfRrKAwlgh3QsWWqJOEtqMGdbS_Q-X0exJ54prlXHDbgcV=w1600-h734-rw "Cifrado con AES")
![Descifrado con AES](https://lh3.googleusercontent.com/c8mRWC8WEAXZj0Nvls8g0iL7t7Y65dc7P9EeETf3d7mVInt-erWOzprEY9BSf7tKblA=w1600-h734-rw "Descifrado con AES")

### RSA

La infraestructura de clave pública dominante. Antes tendrás que intercambiar la clave pública con tus amigos.

![Cifrado con RSA](https://lh3.googleusercontent.com/NmZ9by6Hk49YSgaQDidCx71AMKNUo6QGgPmYy977iSM7UBs9j9ApuW-tasw6N64xIA=w1600-h734-rw "Cifrado con RSA")

Si un amigo te pasa su clave pública (se puede exportar justo debajo del cifrado RSA), puedes almacenarla en la memoria del teléfono e importarla para operar con ella.

*Importante: las claves RSA son generadas en el teléfono en la instalación.*

### 3DES (ede)

¿Posiblemente el método más seguro de la aplicación? ¡Sin duda! La clave es de 168 bits (la de AES 128), por lo que puedes cifrar contenido sin miedo.

![Cifrado con Triple DES](https://lh3.googleusercontent.com/Ak8TX1LkT8bSgvfDRGcwKiWH35FNy1I9DrcM9zlNNWR5KXDeTvnniaw2mUMc_SjtBw=w1600-h734-rw "Cifrado con Triple DES")

### DES

Aunque este algoritmo ya no se considera seguro, no está de más incluirlo.

### Vigenere

Cifrado Vigenere, ¡como los de antes! Si la clave es una única letra, se obtiene un cifrado César.

![Cifrado con Vigenere](https://lh3.googleusercontent.com/yFSFpu0tBw6WxEif2Ob_7C4onmxwiWnsulZJ6nw5OpKH0_RNJa6GNmvRHXb3Hg_Kow=w1600-h734-rw "Cifrado con Vigenere")

### Generador de Hashes

Se pueden crear hashes en SHA-512 (más seguro).

![Generador de Hash SHA-512](https://lh3.googleusercontent.com/16BxObMufc-YvVuixnk_fHHGoKKdkEuNRwBdTlljKi1ZA2acbJzjPwHTDDCcia0XiNA=w1600-h734-rw "Generador de Hash SHA-512")

Incluso MD5, aunque sea menos seguro, entre otros algoritmos.

### Herramienta Base64

A veces no queremos que el contenido se cifre en Base64, mejor en hexadecimal.

![Herramienta Base64](https://lh3.googleusercontent.com/xcQA61wavCgTDaH6So-JcKFee8CVzZrnRSfSlrVr1xscWlxZVJaSMiYeGf3sGLPxNQ=w1600-h734-rw "Observando el contenido en Base64")

### Registro de cambios y modificaciones

Lista de novedades en cada una de las versiones lanzadas

### Acerca De

Acceso al código en GitHub de la aplicación, contacto con el desarrollador e incluso un enlace hacia la política de privacidad.

### ¡Y una actividad secreta!

(No vale inspeccionar el código antes)

## Pruebas

Puesto que se trata de una aplicación en desarrollo, algunas de las funcionalidades están bajo pruebas por el propio desarrollador. Son bienvenidas sugerencias sobre la aplicación (añadir nuevas funcionalidades, errores encontrados, etc) al correo electrónico del desarrollador (ver en Contacto). 

Existe un canal beta de versiones de la aplicación en Google Play con el fin de ser probada por todos los usuarios que lo deseen, proporcionando el feedback correspondiente para el desarrollador.

Para trastear con el código, puedes duplicar el proyecto en cualquier momento. Este proyecto está desarrollado en el IDE Android Studio en su versión 3.0. De esta forma, podrás incluir los cambios que consideres necesarios e incluso añadir nuevas funcionalidades.
Como este proyecto es un proyecto personal desarrollado durante el tiempo libre del desarrollador, por lo que en este momento no dispone de la atención propia de una aplicación en producción.

## Próximas funcionalidades

* Información y ayuda acerca de cada algoritmo.

* Lector de notificaciones y descifrado automático de mensajes cifrados y codificados en Base64.

## Contacto

Correo electrónico: [miguelangel.garciar@edu.uah.es](mailto:miguelangel.garciar@edu.uah.es)

LinkedIn: [in/miguelromeral](https://www.linkedin.com/in/miguelromeral/)

Twitter: [@MiguelRomeral](https://twitter.com/MiguelRomeral)

## Licencia

Licencia Pública General GNU 3.0

## Política de Privacidad

Puedes leer la [política de privacidad de esta aplicación](https://github.com/miguelromeral/mrAES/blob/master/politica-privacidad-mrcipher-es.md) pulsando en el enlace.
