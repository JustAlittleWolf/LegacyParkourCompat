# Development infrastructure

Product code remains under `src/`. This directory contains:

- [tas-client](tas-client/README.md): exact-version recording and playback.
- [parkourgym-server](parkourgym-server/README.md): isolated player worlds and movement-friendly test server.
- [testing](testing/README.md): client/process coordination and recording comparison.
- `disable-tests.gradle`: disables every Gradle `Test` task when passed as an init script, including included builds.
- `export_implementation_base.py`: exports neutral infrastructure and explicit findings to a fresh repository for independent implementation.

Gradle module task names remain `:testing:*` and `:parkourgym-server:*`; the TAS included build is named `tas-client`. Files and runtime directories now live below `development/`. Existing ignored runtime directories in another checkout are not automatically moved or launched.

Only the integrator creates an independent implementation base:

```powershell
py -3 development/export_implementation_base.py --destination C:\work\candidate --branch fix/finding-name --finding research/findings/example.md
```

On Linux/macOS use `python3` and an appropriate absolute destination. The destination must not exist. The export uses an allowlist, omits product mixins, concrete hooks/changes, reports and Git history, and creates a fresh branch and initial commit. It is context isolation, not a filesystem permission boundary. Workers receive only the export, the assigned source roots and the implementation contract. Findings must be independently accepted before assignment; the exporter does not grant acceptance. The integrator imports candidate changes into the real product repository and resolves duplicate operations there.

The exported build has empty registration and mixin lists and no UI or server implementation. New candidates use the same resolver, registry and profile APIs. Build from the export with `gradlew.bat build -x test --init-script disable-tests.gradle`; compilation and runtime launches remain separate permissions.
