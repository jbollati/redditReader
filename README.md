
# Android Reddit Reader


## Contexto

El presente curso ha sido diseñado por [Diego Mercado](https://github.com/mercadodiego) para la materia optativa _"Programación en Android: Introducción"_ de la Facultad de Astronomía, Matemática y Física (FaMAF) perteneciente a la Universidad Nacional de Córdoba, Argentina. 



## Step 1 - Activities Assignment

### Objetivos

* Conocer la comunicación entre Actividades y su ciclo de vida

### Enunciado
* En la acción del Sign inLogin invocar LoginActivity
* Una vez finalizado, debera mostrar el nombre del usuario logueado en pantalla

## Step 2 - Layout Assignment

### Objetivos

* Conocer los principios básicos de Layouts, View y ViewGroups en Android
* Modificar y agregar recursos gráficos y de texto a la aplicación 

### Enunciado

1. Todos los textos deben aparecer tanto en inglés como en castellano
2. Cambiar el nombre de la aplicación para que aparezca como "Reddit Reader" en inglés y "Lector de Reddit" en castellano
3. Cambiar el ícono de la aplicación por el de ./images/reddit_icon.png 
4. Cambiar el nombre de paquete de ar.edu.unc.famaf.activitiesassignment a ar.edu.unc.famaf.redditreader
5. En la vista principal debe mostrarse una vista igual a ./images/screenshot1.jpg ![Alt text](/images/screenshot1.jpg?raw=true "captura de imagen") 

## Step 3 - Adapters Assignment

### Objetivos

* Implementar una [ListView](https://developer.android.com/reference/android/widget/ListView) que obtenga su contenido desde un propio [ArrayAdapter](https://developer.android.com/reference/android/widget/ArrayAdapter.html) 

### Enunciado

1. Descargar el tag "adapters_assignment" del repositorio https://github.com/mercadodiego/RedditReader
2. La clase `ar.edu.unc.famaf.redditreader.model.PostModel` representa un Post en Reddit. La misma ya está creada pero vacía, debe agregar los atributos relativos al título, autor, fecha de creación, numero de comentarios e imagen de *preview/thumbnail* con sus correspondientes *setter/getter*
3. Implementar el método `List<PostModel> getTopPosts()` de la clase `ar.edu.unc.famaf.redditreader.backend.Backend`. El mismo debe devolver siempre 5 instancias de `PostModel` con contenido falso o *dummy* 
4. Crear la clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter` que extienda de `android.widget.ArrayAdapter` e re-implementar los métodos necesarios
5. `NewsActivityFragment` debe mostrar una [ListView](https://developer.android.com/reference/android/widget/ListView) que ocupe todo su espacio y debe desplegar el contenido de cada uno de los Posts siguiendo el diseño implementado en la actividad previa de [LayoutAssignment](https://github.com/mercadodiego/RedditReader/blob/layout_assignment/README.md). Tener en cuenta que el título debe poder siempre mostrarse y la altura de cada fila debe ajustarse para permitirlo
6. Implementar un *ViewHolder* en nuestra clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter` para mejorar la performance de la [ListView](https://developer.android.com/reference/android/widget/ListView). Mayor información en: [Hold View Objects in a View Holder](https://developer.android.com/training/improving-layouts/smooth-scrolling.html#ViewHolder)

## Step 4 - Threads Assignment

### Objetivos

* Implementar una [AsyncTask](https://developer.android.com/reference/android/os/AsyncTask.html) dentro de una [ListView](https://developer.android.com/reference/android/widget/ListView) para ir descargando el contenido de las _thumbnails_ desde Internet 

### Pre-Requsitos

* Haber completado la actividad de [adapters_assignment](https://github.com/mercadodiego/RedditReader/tree/adapters_assignment) 

### Enunciado

1. La clase `ar.edu.unc.famaf.redditreader.ui.PostAdapter` debe implementar una AsyncTask que dada una URL previamente definida en la clase  `ar.edu.unc.famaf.redditreader.model.PostModel`, permita descargar dicha imagen y que sea parte de la ImageView representativa del *preview/thumbnail*
2. Debe emplearse un `android.widget.ProgressBar` animado mientras la imagen está siendo descargada 