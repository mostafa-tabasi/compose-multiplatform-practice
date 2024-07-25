import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class GetInitialsKtTest {

    @Test
    fun testGetInitials() {
        val fullName = "Mostafa Tabasi"
        assertThat(getInitials(fullName)).isEqualTo("MT")

        val fullName2 = "Mostafa middle Tabasi"
        assertThat(getInitials(fullName2)).isEqualTo("MT")

        val fullName3 = "Mostafa"
        assertThat(getInitials(fullName3)).isEqualTo("MO")

        val fullName4 = ""
        assertThat(getInitials(fullName4)).isEqualTo("")
    }
}