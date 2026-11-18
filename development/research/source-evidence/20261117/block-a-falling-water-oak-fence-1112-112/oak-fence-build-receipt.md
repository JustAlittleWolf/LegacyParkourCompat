# Dry oak-fence falling-water build receipt

The registered Windows PowerShell product checkout was clean immediately before and after the build. Both checks reported product commit 8e0d5c40541b8ba56bfa61c1711f658ba0a01c5f and tree 6b21782fecd8584b77a16efe0dad5aad39404b4f.

Command `.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain`. Init script SHA-256 d1246e3bb9e79ee4c7de5939c60ed15b756883925757cfa4866e328d6f88afed. The before-project hook disables every Gradle Test task and the command also excludes test. Stdout lists no Test task execution.

UTC start 2026-11-18T00:26:18.8873120Z. UTC finish 2026-11-18T00:26:27.3291925Z. Gradle exit code 0.

## Relevant stdout

    > Task :compileClientJava
    > Task :jar
    > Task :sourcesJar
    > Task :build
    BUILD SUCCESSFUL in 8s
    undefined
    GRADLE_EXIT=0
    TRACKED_WORKTREE=clean before and after

The source and class archives were generated. The built mod JAR SHA-256 is 041578174b52836d4983dd05a80c81f92dbcc55b1a4bd9aee92e79c47e33e31a. Movement parity remains unverified.
