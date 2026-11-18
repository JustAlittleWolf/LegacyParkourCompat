# Wall TNT build receipt

The test-disabled Windows Gradle build completed on 2026-11-18 at 08:50 UTC.

- Exact PowerShell command `.\gradlew.bat build -x test --init-script development/disable-tests.gradle`.
- Product base `f4fd15ed8398d942f89142144ca941521ed4dbc1`. Built commit `ee9b93460ee0f5c8e9917f5449dbf624ec13d7f7`, tree `da3efdcc89502b2aca7be56175e885e0dcbd96b7`. The checkout was clean at both build boundaries. The receipt commit adds only this record and the linked review to the built tree.
- [Product review](wall-tnt-v1112-only-product-review.md) SHA-256 `c6eb14a5b7876aeb7fe4fd1aeb7469969794dbc5dac4e7836195b4dd0323578a`. Accepted source claim SHA-256 `f74d0ffeab51dc49965e03d10e34eba56bbb68b14d88fdd2be880cec879f846c`. Independent source audit SHA-256 `54f61d88b659c282b1fc543373c338b59a7d45266e3767404b1c4fd9526a7ff7`.
- Gradle wrapper 9.7.1 and Eclipse Adoptium OpenJDK 25.0.3 ran with an isolated copy of the dependency cache. The local Gradle user-home init script set offline mode (SHA-256 `9d0ff31bd239160ebe7fac1a94f660b3745cdbf9d7904d3c62408dcb47e5cfe5`). The tracked init script disables all Gradle `Test` tasks through `beforeProject` and `configureEach` (Git blob `9e17af62db84ed0d2907845d1381f1353cb0adeb`). The build log records no executed test task.
- Exit 0, `BUILD SUCCESSFUL in 1m 42s`, 19 actionable tasks executed. `:compileClientJava` and `:jar` executed. The full console log is retained beside the isolated checkout as `b-wall-only-build-approved-20261118.log` (2,918 bytes, SHA-256 `53d00d9459237917e1197da8a45f5adeb4f7fd39a3580b302ce5f63686825a9f`).
- Product artifact `build/libs/LegacyParkourCompat+26.2-1.0.0.jar` (1,275,340 bytes, SHA-256 `efdb44bdbc6591a3b6229bf9619a5042b4e0e938b2b58891b3e85194bd1f3950`). The archive contains `change/v1_11_2/WallNeighborMask.class` in the mod package (entry SHA-256 `822c69c11704893fa210795cceac9c8ca1584f6c112afb498be42f659c0d87a4`). `javap` confirms the isolated TNT helper is present.
- Verification covers compilation and packaging. Runtime movement parity remains unverified.
