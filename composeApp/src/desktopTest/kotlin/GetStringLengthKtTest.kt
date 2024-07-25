import assertk.assertThat
import assertk.assertions.isEqualTo
import kotlin.test.Test

class GetStringLengthKtTest {

    @Test
    fun testGetStringLength() {
        val string = "abd"
        assertThat(getStringLength(string)).isEqualTo(3)

        val string2 = ""
        assertThat(getStringLength(string2)).isEqualTo(0)
    }
}