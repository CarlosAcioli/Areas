# Areas

Aplicativo para marcar empresas e contratos específicos e as suas visualizações

## ⚙ Construído com quais ferramentas?

Essa aplicação foi construída com as seguintes tecnologias:

* Android Studio - IDE para programação de aplicações nativas Android
* Kotlin - Linguagem oficializada pela Google para criar aplicativos no Android Studio
* Kotlin Coroutines - Ferramenta do Kotlin para programações simultâneas sem bloquear a main thread
* Kotlin Flow - Ferramenta do Kotlin para programação assíncrona usada para emitir estados para a viewModel
* Room Database - banco de dados do sistema Android; abstração de um SQL
* Dagger Hilt - injeções de dependência utilizando o Dagger-Hilt
* Retrofit - biblioteca para fazer consumo de API's no Android Studio

 ## 🛠️ Arquitetura

 Para uma arquitetura robusta, e totalmente escalável para novos módulos e recursos:

 * MVVM - Padrão de arquitura recente, adequado para lidar com a relação de dados para com a view-model's e de maneira segura
 * Clean Architecture - Divisão da aplicação em camadas "data-domain-presentation" para maior organização e assim uma facilidade em escalar o aplicativo para mais usuários e novos recursos

Cada módulo de Arquitetura é feito para cada recurso novo implementado na aplicação, pois é a maneira mais adequada para uma possível maior demanda da aplicação a qualquer momento 

 ## 💡 Diferenciais 

 Pontos adicionais da aplicação desenvolvidos por mim mesmo para mais versatilidade na lógica de negócios

 * [Backend próprio](https://github.com/CarlosAcioli/KtorAPI) feito com [KTOR](https://ktor.io/), criação de uma API com rotas para cada criação de um documento para inserir em um banco de dados não relacional
 * Criação de um banco de dados NoSQL com [MongoDB](https://www.mongodb.com/pt-br), ótima implementação para o contexto da aplicação, após estudos dos bancos de dados SQL e NoSQL, suas diferenças e melhores implementações para cada contexto, MongoDB foi a tecnologia mais adequada para escalabidade, simplicidade e eficiência com os dados 
