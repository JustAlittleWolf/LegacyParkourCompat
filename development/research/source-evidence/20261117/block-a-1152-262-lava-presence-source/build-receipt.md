# Tests-disabled Gradle build receipt

- Built product commit `471efa593809dc24c8cc747df21244b9badb9176`, tree `110158277bcc66b01d3f7c0870bc6cfb29032c9f`. The registered isolated checkout was clean before and after the build.
- Windows PowerShell command `gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`. The init script configures every Gradle `Test` task with `enabled = false`. The command also excludes `test`. No Gradle test task ran. The `:testing` tasks assembled that production subproject.
- Authorized local cache access was used. The build completed on 2026-11-18 at 01:55:04 UTC with exit code 0, `BUILD SUCCESSFUL in 10s` and `19 actionable tasks: 4 executed, 15 up-to-date`. Client Java and resources built and the mod JAR was assembled. Gradle reported existing deprecation notices.
- Local build stdout SHA-256 `0651e90babc7834217d0ed5dbe433f94b78460bcc92f17a1377b8e808724407d`. Packaged mod JAR SHA-256 `3af0de2876f0f7f7170b25b0eade8cee813c5a9922074fcad0bb5eadf320f81f`. Packaged classes and mixin configuration contain one new provider with the expected emulation annotation and one client-side configured-fluid accessor with the exact descriptor. The earlier and later providers remain present.
- Runtime parity remains unverified.
