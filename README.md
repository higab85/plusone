# PlusOne

## Requisitos

- [ ] Plus One será una aplicación (en principio para Android, luego se verá la posibilidad de ampliarlo a otros Sistemas Operativos) que te permitirá crear y buscar eventos.
En estos eventos se realizarán una actividad y el usuario se apuntará al que quiera o creará uno nuevo. 
 - [ ] Las actividades son muy variadas, desde eventos deportivos, espectáculos, comidas o incluso eventos didácticos, como charlas o conferencias. 
 - [ ] El usuario que cree el evento deberá especificar información que describa las características del evento: Personas necesarias, Hora, Duración, Lugar, Temática. También podrá dar detalles (si son aplicables) como la experiencia necesaria, precio y mas información. 
 - [ ] El usuario que cree el evento también podrá decidir si habilitar el chat, y cuanta información personal quiere que se comparta entre los interesados del evento (foto de perfil, sexo, edad, etc.).
 - [ ] Tecnología de geolocalización para saber exactamente dónde va a ser el evento.
 - [ ] Chat con las personas que están apuntadas al evento para poder quedar o preguntar.
 - [ ] Búsqueda de eventos
 - [ ] En la página donde se muestren los eventos, debe haber una manera de filtrarlo por nivel de privacidad, distancia, hora, precio(es una actividad gratuita, o es una comida)
 - [ ] Una página de perfil, donde el usuario pueda describirse, subir una foto de perfil, y cerrar la sesión.
 - [ ] Una página de ajustes donde el usuario pueda decidir el tipo de eventos que quiera encontrar(ej. Alguien debe poder decir que solo quiere eventos organizados por chicas entre los 20 y los 40 años). 
 - [ ] La página principal debe dejar al usuario rápidamente elegir que tipo de evento quiere (entre comer, actividad cultural, deporte, café/cerveza, hablar, u otros), buscar eventos, y crear eventos.

## Análisis
- Móviles Android
- Ordenadores
- Lenguaje de programación: Kotlin
- Herramientas: Repositorio, Open Office, Internet, IDE.
- Idiomas: Inglés, Francés y Español.
- Servidores
- Tecnología GPS
- Navegador
- API de mapas
- BBDD

## Plan de Viabilidad
- Oferta: Competencia: MeetUp, Tinder, FacebookEvents, BlaBlaCar
- Demanda: Personas proactivas a partir de 18 años
- Entorno: A nivel de legislación: el uso de la aplicación, a partir de 18 años
- Modelo de negocio: Sponsors, descuentos
- Cultura: Personas abiertas

## Google Maps Android API + Google Places API para Android
Google Maps Android API es una aplicación totalmente gratuita e ilimitada. Permite integrar diversas bases de mapas, edificios en 3D, y añadir imágenes Street view y satélite. Esta API es muy popular y es a su vez, también, la más utilizada, ya que posee un gran potencial: su documentación es muy completa y es muy fácil de utilizar, especialmente para los sistemas Android.
  Además, se puede añadir Google Places API para los sistemas Android que permite añadir diversas funcionalidades útiles, como la geolocalización la cual permite encontrar personas en la misma zona. Con esto, podemos integrar un base de datos sobre todos los edificios de una zona geográfica, y permitirá encontrar todos los puntos de interés como los teatros, canchas deportivas, cinemas, etc... Esta API es gratuita, su límite se establece hasta 1000 solicitudes al día, lo que es ampliamente suficiente para desarrollar y probar nuestra aplicación.  

### ¿Cómo funciona?

1. Descargar y utilizar «Android Studio » (desarrollo en Java).
2. Añadir el package de los servicios Google Play a Android Studio.
3. Desarrollar.

Más información: 
- [Precios](https://developers.google.com/maps/pricing-and-plans/#details)
- [MAPS](https://developers.google.com/maps/documentation/android-api/?hl=fr)
- [Places](https://developers.google.com/places/?hl=fr)
- [First steps](https://developers.google.com/maps/documentation/android-api/start?hl=fr)

## Conclusión 

En definitiva, buscamos una aplicación móvil (en principio para Android) que te permita buscar y crear eventos para realizar actividades con un número de personas de las que no se disponen.

### Por qué?
Lo primero de todo, creemos que hay un gran número de personas que no realizan ciertas actividades, por miedo a ir solos o, por no ser el número de personas suficiente para desempeñarla (ej. Queremos jugar un partido de pádel por parejas y somos 3).
Consideramos que con nuestra aplicación los problemas enunciados anteriormente se solventarán. 
El usuario, al poder crear todo tipo de eventos, también generamos una relación social entre ellos, no una simple quedada puntual, lo cual creemos interesante. Conseguir que personas con gustos afines se relacionen entre sí.

### ¿Viabilidad?
Creemos que es viable, porque hemos visto una necesidad en las personas para este tipo de Aplicación.
Por ejemplo, a quién de nosotros no le ha pasado alguna vez ir a un campo de fútbol sala y que uno de tus amigos sea baja, pues con nuestra aplicación podrás buscar ese jugador que te falta. O querer ir a un evento y que tus amigos no puedan/quieran y no te guste ir solo, pues con nuestra aplicación podrás encontrar personas que como tú estén interesadas en ir.

