package me.magnum.melonds.ui.bundled

import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import java.io.File
import java.security.MessageDigest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.magnum.melonds.domain.model.rom.Rom
import me.magnum.melonds.domain.model.rom.config.RomConfig
import me.magnum.melonds.ui.emulator.EmulatorActivity

/** Installs the bundled game privately, then enters the built-in emulator. */
class BundledGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val status = TextView(this).apply {
            text = "Preparing Digimon World DS…"
            textSize = 22f
            gravity = Gravity.CENTER
            setPadding(32, 32, 32, 32)
        }
        setContentView(status)
        lifecycleScope.launch {
            try {
                val game = withContext(Dispatchers.IO) { prepareGame() }
                val rom = Rom(
                    name = "Digimon World DS",
                    developerName = "Bandai Namco",
                    fileName = game.name,
                    uri = FileProvider.getUriForFile(this@BundledGameActivity, "$packageName.bundled", game),
                    parentTreeUri = null,
                    config = RomConfig.default(),
                    isDsiWareTitle = false,
                    retroAchievementsHash = "",
                )
                startActivity(EmulatorActivity.getRomEmulatorActivityIntent(this@BundledGameActivity, rom))
                finish()
            } catch (e: kotlinx.coroutines.CancellationException) {
                throw e
            } catch (e: Exception) {
                status.text = "Could not prepare the game. Check free storage, then reopen the app.\n\n${e.localizedMessage}"
            }
        }
    }

    private fun prepareGame(): File {
        val directory = File(filesDir, "bundled").apply { check(isDirectory || mkdirs()) }
        val game = File(directory, "digimon.nds")
        val expected = assets.open("digimon.sha256").bufferedReader().use { it.readText().trim() }
        fun valid(file: File): Boolean {
            if (!file.isFile || file.length() != 67108864L) return false
            val digest = MessageDigest.getInstance("SHA-256")
            file.inputStream().use { stream ->
                val buffer = ByteArray(65536)
                while (true) {
                    val count = stream.read(buffer)
                    if (count < 0) break
                    digest.update(buffer, 0, count)
                }
            }
            return digest.digest().joinToString("") { "%02x".format(it.toInt() and 255) } == expected
        }
        if (!valid(game)) {
            val temporary = File.createTempFile("digimon-", ".tmp", directory)
            try {
                assets.open("digimon.nds").use { input -> temporary.outputStream().use { input.copyTo(it) } }
                check(valid(temporary)) { "Bundled game integrity check failed" }
                check(temporary.renameTo(game)) { "Could not install game data" }
            } finally {
                temporary.delete()
            }
        }
        return game
    }
}
