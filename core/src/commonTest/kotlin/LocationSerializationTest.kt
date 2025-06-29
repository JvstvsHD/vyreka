import de.jvstvshd.vyreka.core.Location
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class LocationSerializationTest {

    @Test
    fun test() {
        val location = Location(1, 2, 3)
        val encoded = Json.encodeToString(location)
        println(encoded)
        val decoded = Json.decodeFromString<Location>(encoded)
        println(decoded)
        assertEquals(location, decoded, "Locations should be equal")
    }
}