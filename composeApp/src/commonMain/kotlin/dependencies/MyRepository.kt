package dependencies

interface MyRepository {
    fun fetchTitle(): String
}

class MyRepositoryImpl(
    private val dbClient: DbClient,
) : MyRepository {

    override fun fetchTitle(): String = "Hello World!"

}