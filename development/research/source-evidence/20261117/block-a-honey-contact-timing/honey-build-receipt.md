# Honey tests-disabled build receipt

- Checkout was the registered product clone on Windows PowerShell. HEAD was 3aecd25b23e8d49c198fa6e0d9ebc5daee222a85 and its tree was 8a5d1edb6838cdfb3de7155c62f43063401e8198 immediately before and after the build. The worktree was clean at both checks.
- Command was `.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`.
- Init script SHA-256 was d1246e3bb9e79ee4c7de5939c60ed15b756883925757cfa4866e328d6f88afed. Its before-project hook configures each Gradle Test task with enabled = false. The command also passed -x test. Stdout lists no Test task execution.
- UTC start 2026-11-17T23:44:09.5231900Z. UTC finish 2026-11-17T23:44:14.8543674Z. Gradle exit code 0.

## Relevant stdout

    > Task :compileClientJava UP-TO-DATE
    > Task :jar UP-TO-DATE
    > Task :buildSrc:compileJava UP-TO-DATE
    BUILD SUCCESSFUL in 4s
    19 actionable tasks: 19 up-to-date
    GRADLE_EXIT=0
    TRACKED_WORKTREE=clean before and after

The exact-commit rerun reused up-to-date task outputs. The original integration run on the same source tree executed compileClientJava, jar and sourcesJar with the same tests-disabled command. Runtime movement parity remains unverified.
