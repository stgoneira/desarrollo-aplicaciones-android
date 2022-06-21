# Caso "Anchor Books"

La editorial y Librería “Anchor Books”, tiene un stock de libros “best seller” los cuales desea dar a
conocer a través de una aplicación móvil.
La transformación digital ha sido un proceso lento para esta empresa, por lo tanto son un poco
escépticos respecto a esta forma de distribución, por lo que inicialmente solo quieren un mínimo
producto viable, para poner a prueba y mostrar a futuros inversionista y socios un sistema de
venta de libros en forma de app.
La idea es mostrar un pequeño catálogo de libros disponibles, para que los interesados puedan
visualizar detalles de los libros y hacer llegar una petición de compra.
Se te contrato como desarrollador Android, para sentar las bases de este proyecto, con la idea de
que pueda escalar en un futuro.
En el siguiente documento, usted encontrará el briefing de la aplicación Android que tiene que
desarrollar.

## Alcance del proyecto

El jefe de proyecto determinó que se debe construir un mínimo producto viable, donde los
posibles clientes puedan observar los libros en venta y poder seleccionar alguno, y hacer llegar
esta información al departamento de ventas.
La API desde donde provienen los datos es limitada, y cuenta solo con la información mínima para
poder mostrarla en la app Android. Por este mismo motivo, aún no se termina de crear ni
implementar una forma de recopilar datos de los usuarios, o procesar las ventas.
En primera instancia, se solicita generar una aplicación Android que consuma los servicios
proporcionados por la API. Dado el poco tiempo disponible, se solicita un mínimo producto
viable, pero que permita con el tiempo poder escalar la aplicación sin mayores inconvenientes.

En esta primera versión, la app realizará lo siguiente:

- Debe consumirá un servicio ​REST​ de donde provienen los datos a mostrar.
- El servicio REST contará con ​2 end points​, uno que entregará una colección ​de libros y el
segundo que entregará los detalles de estos libros dado un identificador.
- Considerando que para esta primera versión se busca tener una gran cobertura de
dispositivos manteniendo los costos de mantención bajos, la API mínima es 23 y el target
30.

Lo que se espera que haga la aplicación es que cualquier usuario que la instale sin necesidad de
autenticarse, pueda ver una lista de los Libros, ver los detalles del que seleccione y enviar un
correo al área de ventas con información predefinida.

- La primera pantalla es una lista de Libros (Recycler View).
- La segunda pantalla muestra la información en detalle del libro seleccionado.
	- Debe tener un botón para simular la intención de compra (Este deberá ejecutar un
intent implícito a una app de correo electrónico).

## Código, arquitectura y dependencias 

El jefe de proyectos entiende que, aunque el alcance de la aplicación es inicial, la arquitectura que
utilice le permitirá seguir expandiendo o modificando el proyecto de acuerdo a los resultados
iniciales, es por eso que se requiere cuide un código legible y que use específicamente la siguiente
arquitectura en conjunto con estas dependencias:

- Respetar las convenciones y estilos de codificación (indentación, reutilización, comentarios, legibilidad, convenciones de nombre, etc.)
- La arquitectura del aplicativo puede ser ​MVP-LiveData-ROOM​ o ​MVVM-LiveData-ROOM​.
	- Debe guardar los datos en la persistencia del teléfono(ROOM) y mostrarlos en las
vistas correspondientes.
- Los request HTTP deben ser realizados utilizando la librería ​Retrofit​.
- Puede utilizar los lenguajes de programación ​Kotlin​ o ​Java​.
	- Si utiliza Kotlin, debe usar Coroutines para manejar el trabajo asíncrono, según
corresponda.
	- Si utiliza Java, debe usar alguna alternativa como executors para manejar el
trabajo asíncrono, según corresponda.
- Utilice las bibliotecas que estime necesario para hacer TEST.


## Diseño del aplicativo 

El diseñador fue contratado hace poco, por lo que para la primera versión de la aplicación no ha
podido traspasar el diseño a un programa que le entregue las medidas en formato Android. Por lo
que los tamaños y distancias pueden no ser exactos en esta primera versión. Para evitar
confusiones, el jefe de proyecto ha decidido enumerar las especificaciones que no pueden faltar:

- Use la siguiente paleta para los colores de la aplicación:

![ki](data:image/svg+xml;<svg width="15" height="15"><rect x="0" y="0" width="300" height="100" fill="yellow"/></svg>)

- Aunque los tamaños pueden variar, la diagramación de los layouts se debe mantener, esto
incluye las filas de la lista y la segunda pantalla (Utilizar recomendaciones de ​Material
Design​).
- Se recomienda utilizar una actividad y múltiples fragmentos.
- No se especifican que tipos de layouts debe utilizar, por lo tanto es responsabilidad del
desarrollador, debe utilizar su criterio manteniendo como punto importante la correcta
visualización de los elementos.
- Se recomienda que la segunda pantalla tenga la posibilidad de hacer scroll para visualizar
el contenido de forma correcta.
- Todos los textos que no sean obtenidos a partir de la API REST deben ser traducibles.
- El área de diseño todavía no ha estandarizado la línea gráfica de los íconos, pero debe
tratar de utilizar al menos 2 para mejorar el apartado visual de la app. Para los íconos
tiene que utilizar vector graphics.
- Se adjuntan algunos ejemplos de pantallas tipo, pero basándonos en el tiempo, debe
tomar decisiones que permitan realizar el proyecto.


## End Points y Modelos de Datos

Ambos endpoints se deben acceder a través del verbo de request HTTP GET. El primer endpoint es
para obtener una lista de libros.

1. https://my-json-server.typicode.com/Himuravidal/anchorBooks/books

En esta respuesta se encuentran los productos agrupados por una colección donde cada libro tiene
la data reducida:

```json
{
	"id": 1,
	"author": "Chinua Achebe",
	"country": "Nigeria",
	"imageLink":
	"https://user-images.githubusercontent.com/22780253/103938792-90279200-5109-11
	eb-906a-50ac3b73e40d.jpg",
	"language": "English",
	"title": "Things Fall Apart"
}
```

A continuación, puede ver un ejemplo con 3 objetos:

```json
[
	{
		"id": 1,
		"author": "Chinua Achebe",
		"country": "Nigeria",
		"imageLink":
		"https://user-images.githubusercontent.com/22780253/103938792-90279200-5109-11
		eb-906a-50ac3b73e40d.jpg",
		"language": "English",
		"title": "Things Fall Apart"
	},
	{
		"id": 2,
		"author": "Hans Christian Andersen",
		"country": "Denmark",
		"imageLink":
		"https://user-images.githubusercontent.com/22780253/103938837-a1709e80-5109-11
		eb-9660-96b17ed8105d.jpg",
		"language": "Danish",
		"title": "Fairy tales"
	},
	{
		"id": 3,
		"author": "Dante Alighieri",
		"country": "Italy",
		"imageLink":
		"https://user-images.githubusercontent.com/22780253/103938865-a9c8d980-5109-11
		eb-9e4b-e90166846222.jpg",
		"language": "Italian",
		"title": "The Divine Comedy"
	}
]
```

El segundo endpoint corresponde al detalle y se accede indicando el ID específico:

2. https://my-json-server.typicode.com/Himuravidal/anchorBooks/bookDetail/1

En esta respuesta se encuentra tan solo 1 objeto producto que corresponde al ID, la diferencia es
que tiene todos los datos.

```json
{
	"id": 1,
	"author": "Chinua Achebe",
	"country": "Nigeria",
	"imageLink":
	"https://user-images.githubusercontent.com/22780253/103938792-90279200-5109-11
	eb-906a-50ac3b73e40d.jpg",
	"language": "English",
	"link": "https://en.wikipedia.org/wiki/Things_Fall_Apart\n",
	"pages": 209,
	"title": "Things Fall Apart",
	"year": 1958,
	"price": 12500,
	"lastPrice": 17500,
	"delivery": true
}
```

Como habrá notado hay 1 atributo (delivery) que es booleano, pero en el diseño debe aparecer como texto o como un icono.

- true: Cuenta con despacho
- false: Sin despacho.


## Funcionalidad de envío de correo

Cuando el usuario está viendo el detalle de un equipo al hacer click en un botón del detalle, tiene
que enviarse un correo con la siguiente información pre llenada:

**Destinario**: ​ventas@anchorBooks.cl
**Asunto**: Consulta por libro {TITLE} id {ID}
**Mensaje**:
"Hola
Vi el libro {TITLE} de código ​{ID}​ y me gustaría que me contactaran a este correo o al
siguiente número _________
Quedo atento."

Recuerde reemplazar lo indicado entre paréntesis cursivos por la data correspondiente al libro
seleccionado. No incluya los paréntesis cursivos en correo.
No es necesario que el usuario llene el número de ante mano, conserve los guiones bajos u otro
símbolo que le indique al usuario que ahí tiene que escribir su número cuando esté viendo el
mensaje en la aplicación de correos.

## Testing

Preocupados por la calidad del código y estabilidad de la app, se ha definido un mínimo de test a
llevar a cabo.

- Al menos un test unitario
- Al menos un test de instrumental.

Sugerencias:
-Test unitario que verifique la respuesta de los endpoints usando un servidor de pruebas
como mockwebserver.
-Test instrumental que compruebe la persistencia de datos con ROOM.
-Test unitarios sobre cualquier función.


