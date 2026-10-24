# Digimon World DS — Android app

[**Download the Android APK**](https://github.com/justin0559/Digimon-android/raw/refs/heads/main/downloads/Digimon-World-DS.apk)

Open the link on your Android phone, download the APK, then open it to install.
Allow installation from your browser or file manager if Android asks. Requires
Android 7.0 or newer; a 64-bit ARM phone is recommended.

The game and melonDS emulator are bundled. Tap **Digimon World DS** to start the
supplied USA game directly, with both DS screens and on-screen controls.
Leave several hundred megabytes free for installation and the private game copy.
This APK is development-signed for personal sideloading.

## Validation

The production/debug APK build and APK signature verification passed. The bundled
ROM checksum was verified. Installation and automatic launch through the Nintendo
licensing screens were checked on an Android 9 x86-64 software emulator. Real-phone
performance, audio output, a full playthrough, and an in-game save/load cycle have
not been verified. See [validation details](downloads/Validation.txt).

## Source and builds

The complete emulator source and modifications are vendored in this repository.
See [build instructions](BUILD_GAME.md), [upstream commit IDs](UPSTREAM_COMMIT.txt),
and the [upstream README](UPSTREAM_README.md). No Git submodule initialization is
needed. Upstream publishing workflows were omitted from this personal project.

melonDS Android source is licensed under GPLv3; see [LICENSE](LICENSE) and the
component notices. The emulator license does not license game data. The bundled
ROM is the file supplied by the repository owner for this personal build.

Saves use app-specific storage. Back them up before uninstalling or clearing app
data, as those operations can erase saves.
