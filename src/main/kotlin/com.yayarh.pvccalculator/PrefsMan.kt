import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.*

class PrefsMan {

    object KEYS {
        const val PVC_PRICE = "pvcPrice"
        const val GLASS_PRICE = "glassPrice"
        const val SHUTTER_PRICE = "shutterPrice"
    }

    private val kPrefs = KsPrefs()

    fun setPvcPrice(price: Int) {
        kPrefs.push(KEYS.PVC_PRICE, price)
    }

    fun getPvcPrice(): Int {
        return try {
            return kPrefs.pull(KEYS.PVC_PRICE, "0").toIntOrNull() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun setGlassPrice(price: Int) {
        kPrefs.push(KEYS.GLASS_PRICE, price)
    }

    fun getGlassPrice(): Int {
        return try {
            kPrefs.pull(KEYS.GLASS_PRICE, "0").toIntOrNull() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    fun setShutterPrice(price: Int) {
        kPrefs.push(KEYS.SHUTTER_PRICE, price)
    }

    fun getShutterPrice(): Int {
        return try {
            kPrefs.pull(KEYS.SHUTTER_PRICE, "0").toIntOrNull() ?: 0
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    private inner class KsPrefs {
        val prefsFile = File("prefs.txt")

        fun push(key: String, value: Any) {
            val modifiedPrefs = readAllPrefs().toMutableMap().apply { put(key, value.toString()) }
            saveAllPrefs(modifiedPrefs)
        }

        fun pull(key: String, defaultValue: String): String {
            return (readAllPrefs()[key]) ?: defaultValue
        }

        private fun saveAllPrefs(allPrefs: Map<String, Any>) {
            prefsFile.createNewFile()
            BufferedWriter(FileWriter(prefsFile)).use {
                allPrefs.forEach { (key, value) ->
                    it.write("$key=$value")
                    it.newLine()
                }
            }
        }

        private fun readAllPrefs(): Map<String, String> {
            prefsFile.createNewFile()

            val allPrefs = mutableMapOf<String, String>()

            Scanner(prefsFile).use {
                while (it.hasNextLine()) {
                    val line = it.nextLine()
                    allPrefs[line.substringBefore("=")] = line.substringAfter("=")
                }
            }

            return allPrefs
        }

    }


}