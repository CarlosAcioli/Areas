# Areas

Application to mark specific companies and contracts and their views

## ⚙ Built with what tools?

This application is built with the following technologies:

* Android Studio - IDE for developing native Android applications
* Kotlin - Google's official language for creating apps in Android Studio
* Kotlin Coroutines - Kotlin tool for concurrent schedules without blocking the main thread
* Kotlin Flow - Kotlin tool for asynchronous programming used to emit states to the viewModel
* Room Database - Android system database; Abstraction of a SQL
* Dagger Hilt - addiction injections using the Dagger-Hilt
* Retrofit - library to do API consumption in Android Studio

 ## 🛠️ Architecture

 For a robust architecture, and fully scalable for new modules and features:

 * MVVM - Recent architecture pattern, suitable for handling the data relationship to the view-model's in a secure manner
 * Clean Architecture - Division of the application into "data-domain-presentation" layers for greater organization and thus an ease in scaling the application to more users and new features
Cada módulo de Arquitetura é feito para cada recurso novo implementado na aplicação, pois é a maneira mais adequada para uma possível maior demanda da aplicação a qualquer momento 

 ## 💡 Diferencials 

 Additional points of the application developed by myself for more versatility in business logic

 * [Own backend](https://github.com/CarlosAcioli/KtorAPI) built with [KTOR](https://ktor.io/), creating an API with routes for each insertion of a document to put into a non-relational database
 * Creation of a non-relational database with [MongoDB](https://www.mongodb.com/pt-br), great implementation for the application context, after studies of SQL and NoSQL databases, their differences and best implementations for each context, MongoDB was the most suitable technology for scalability, simplicity and efficiency with data
