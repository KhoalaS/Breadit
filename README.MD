# Breadit 🍞

Work in progress Reddit Client using Compose and the first party GraphQL API.

It is planned to extract the API, as soon as it is in a usable state.

Schmema types are manually reconstructed from the query response fields. This will result in incomplete types at first.

## GrqphQL

Queries were extracted with this regex: 
`rg 'query\s[a-zA-Z]+\(.*?\)\s\{.*?\}'`

Some of the Enums were extracted with this regex:
`rg -NI '\.field public static final enum (.+?):Lcom/reddit/type/([a-zA-Z]+)' -or '$1|$2'`

For anyone wondering how to get Persisted Queries to work:
```kotlin
val apolloClient = ApolloClient.Builder()
            .serverUrl("https://my-server.com")
            .enableAutoPersistedQueries(true)
            .sendApqExtensions(true) // sends the query hash
            .sendDocument(false) // omits the 'query' field
            .build()
```
