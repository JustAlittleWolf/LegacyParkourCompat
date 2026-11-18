# Tests-disabled Gradle build receipt

- Built product commit `f4c9163e2716bae7b5471dc67f2e6520ce541df4`, tree `e85e084633ed7c3cfb4c9418c6249e1cee3a0a74`. Git status was clean before and after the build.
- Command in the registered isolated checkout on Windows PowerShell: `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`.
- The init script configures every Gradle `Test` task with `enabled = false` before project execution, and the command excludes `test`. No Gradle test task ran. The `:testing` lines in stdout are production assembly tasks of the testing application subproject.
- Authorized local cache access was used after the sandbox denied the Gradle wrapper lock. The build completed on 2026-11-18 at 01:42:50 UTC with exit code 0, `BUILD SUCCESSFUL in 10s`, and `19 actionable tasks: 3 executed, 16 up-to-date`. Client Java compiled and the mod JAR was assembled.
- Local stdout log SHA-256 `adcca05a68fd970fb9ea28ee73e02fef07f5de97158b41a89c833217d7227d6a`. Packaged JAR SHA-256 `544788226b9917733937c789ed8cddf078167ade718eec2bedeca3a5fe92db36`. The JAR contains each of the two moved providers once under `change/v1_11_2` and contains the existing water-current mixins.
- Runtime parity remains unverified.
