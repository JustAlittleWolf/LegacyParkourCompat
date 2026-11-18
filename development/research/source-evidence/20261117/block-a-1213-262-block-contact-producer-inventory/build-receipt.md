# Tests-disabled Gradle build receipt

- Built product commit `5d27ed31fdc0319b694eb12c685e1d1a466a7886`, tree `20f8ef95c27ee6680535c07bf61c60726570e044`. The registered isolated checkout was clean before and after the build.
- Windows PowerShell command `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`. The init script disables every Gradle `Test` task. The command also excludes `test`. No Gradle test task ran. The `:testing` tasks assembled that production subproject.
- Authorized local Gradle cache access was used. The build completed on 2026-11-18 at 03:40:36 UTC with exit code 0, `BUILD SUCCESSFUL in 13s` and `19 actionable tasks: 4 executed, 15 up-to-date`. Client Java compiled and the mod JAR was assembled. Gradle reported existing deprecation notices.
- Local stdout SHA-256 `833556ef1a52b0decad766fd9dd0f148248f64f62f1559ebfe5a79dccc7af805`. Packaged mod JAR SHA-256 `084906c5100f84d9bb5a694b9af12bdd3c83f7a0298289226d9b8466f5c4ce95`. The packaged client mixin retains the private List target descriptor and original fallback. The provider carries the expected `V1_21_2` annotation and both endpoint accessors and candidate checks.
- Runtime parity remains unverified.
