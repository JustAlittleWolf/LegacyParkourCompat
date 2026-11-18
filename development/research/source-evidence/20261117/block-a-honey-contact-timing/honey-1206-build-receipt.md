# Honey 1.20.6 extension build receipt

Windows PowerShell, registered product clone. HEAD 86eb0eab5b292e4c0a14e2d2a6177346ba9dd9ce and tree e72827a3ae4b4b44d26e5a890d73990a067d7ed5 were identical immediately before and after the build. The worktree was clean at both checks.

Command `.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`. Init script SHA-256 d1246e3bb9e79ee4c7de5939c60ed15b756883925757cfa4866e328d6f88afed. Its before-project hook disables every Gradle Test task. The command excluded test and stdout lists no Test execution.

UTC start 2026-11-17T23:54:16.6663364Z. UTC finish 2026-11-17T23:54:24.6647044Z. Gradle exit code 0.

## Relevant stdout

    > Task :compileClientJava
    > Task :jar
    > Task :sourcesJar
    > Task :build
    BUILD SUCCESSFUL in 7s
    undefined
    GRADLE_EXIT=0
    TRACKED_WORKTREE=clean before and after

No tests or game/runtime were invoked. Compilation and packaging leave movement parity unverified.
