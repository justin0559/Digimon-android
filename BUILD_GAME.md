# Digimon World DS for Android

This personal Android build bundles the supplied USA Nintendo DS ROM with the
open-source melonDS Android emulator. Tapping the launcher installs the bundled
game into private app storage, verifies its SHA-256, and opens it directly in
the emulator. It does not require a separate emulator installation or ROM picker.

## Install

Copy `Digimon-World-DS.apk` to your Android phone and open it. Allow installation
from that file manager if Android asks. Android 7.0 or newer is required; a
64-bit ARM phone is recommended. The APK also includes 32-bit ARM and x86-64.
The first launch needs space for a 64 MiB private game copy in addition to the
installed app. Keep several hundred megabytes free.

Both DS screens, touchscreen input, audio, and gamepad controls are supplied by
melonDS. Use the emulator's pause menu for settings and save states. Regular
in-game saves and save states use app-specific storage; uninstalling the app or
clearing its data can remove them. Back them up before doing either.

The build is signed with a development key for personal sideloading. Future
updates must use the same signing key and application ID to preserve an
in-place installation. This is not a Play Store release.

## Build from source

The complete emulator source, its submodule sources, and license notices are
included with this project. Upstream:
https://github.com/rafaelvcaetano/melonDS-android

Requirements: JDK 21 **including javac**, Android SDK Platform 37.0,
Build Tools 36.0.0 and 37.0.0, NDK 28.0.13004108, and CMake 3.22.1. Use the checked-in
Gradle wrapper. Install any additional SDK package requested by the build.

Set `ANDROID_HOME` to your SDK and `JAVA_HOME` to your JDK. Create a local
`local.properties` containing `sdk.dir=/your/android/sdk` or remove the cloud
machine's local file and use `ANDROID_HOME`. Then:

```sh
sha256sum -c bundled-rom.sha256
bash gradlew :app:assembleGitHubProdDebug --max-workers=4
```

The APK is generated under `app/build/outputs/apk/gitHubProd/debug/`.
The application ID is `org.personal.digimonworldds.dev`.

Customizations are the `BundledGameActivity`, bundled game and SHA-256 assets,
application ID, launcher entry, app label, and launcher vector icon. The optional upstream update dialog displays release notes as plain text to
avoid an unavailable Markdown dependency. The melonDS emulation core and
license notices are retained. The original
finance app repository is separate and was not modified by this work.

## Licensing

melonDS Android is distributed under GPLv3; see `LICENSE` and the notices in
its component source directories. The emulator license does not license the
game data. The ROM included in this personal build is the file supplied by
the user.
