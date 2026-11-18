# V1_12 Brewing Stand AutoJump build and review receipt

The build owner ran the following from the isolated Windows product checkout. The source matched commit `f6c68a7145a0caeac53307cfec3996d21c9b27bb`, tree `8d47ae54e05e3111298f2f0fb3b15a34b7f8f7e2`.

```powershell
& .\gradlew.bat build -x test --init-script development/disable-tests.gradle 2>&1 | Tee-Object -FilePath .git/brewing-reconciliation-build.log
```

Gradle exited 0 with 19 executed tasks in 17 seconds, including client and server compilation and JAR assembly. The explicit `-x test` exclusion was combined with the init script, SHA-256 `d1246e3bb9e79ee4c7de5939c60ed15b756883925757cfa4866e328d6f88afed`. It configures every Gradle `Test` task with `enabled = false` through `gradle.beforeProject`. The task log contains zero Test task entries. Runtime movement parity remains unverified.

| Evidence | Raw SHA-256 |
| --- | --- |
| [Gradle output](build.log) | `0190f5e0b6662cb00d1b8ed463d71212b37e82ba1c07f4ebe5ff7b545d91c90c` |
| `build/libs/LegacyParkourCompat+26.2-1.0.0.jar` | `c1e18090ad07cce816ae236323b4e1424f588f6ec4ef4ce623e94866ce1bef5c` |
| Packaged V1_12 `AutoJumpCollisionOrder.class` | `b81cafbe2b418e6396b00ea9585cc98070bae75207859a441634814ebe09f099` |
| [Independent ACCEPT](verdict.md) | `6f408aa20d28037314cc0d88f1246de716eb6620e777eddb95cdea54693d8ce9` |

The corrected 1.12.2 Ornithe Feather `LocalClientPlayerEntity.java` digest `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` matches both the canonical file and its entry in the source manifest `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`. The archived [step-control report](step-control.md) retains its original bytes.
