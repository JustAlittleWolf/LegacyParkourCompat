# F-4 mounted-player lava-box main integration — 2026-10-09

## Accepted inputs

- Finding `F-4-mounted-player-lava-box.md`: source commit `019d5fcef181f6dde2a9630e0b882acfa99d6de9`, blob `bcd14cf6c38270229b035a85f929291254c3664b`, raw SHA-256 `A49BFDD5E363F9FAAE5B99FE29CFEFE393A958019460063FBB175EF2B053E034`.
- Independent source review: bounded `ACCEPT`, commit `964ae707a85126a692de3bf9d41a6db5574867d5`; report blob `b43b9b0d5af21cc8e7d7ae108e2d256db8e52d3e`, raw SHA-256 `0703E61E5FE417BF48C2BB8EA9AB4D287417FBA7F833950B4E9DB45FA5E299CA`.
- Boundary: exact 26.1 first changed release, accepted in the bounded review. Pair 1.21.11–26.1.2 remains partial; source pair freeze and runtime parity are not established.
- Code candidate `8de4544ee3519981f42bb7787741be47b4c53318` received `NEEDS CHANGES` because widening the shared scan box also widened WATER queries. The candidate and original review remain immutable.
- Corrected candidate `c31c2caca4448f0dd143362a01a8217f13bcf2a1` received bounded independent `ACCEPT`. The added fluid-state behavior suppresses guarded WATER cells while preserving the shared traversal and LAVA scan.

## Main integration

- Integrated on `main` as `f6b4ffae` (initial candidate, including preserved author run record) and `5289e70dcd58da6a9ffc9181b1a48d817de41546` (accepted correction). Input code tree: `3dde7dd35099569b5b8098548d58b23a564758c3`.
- Semantic reconciliation: retained the independent Y=256 V1_15_2 catalog ordering (StaleWaterDepth before SneakEdge) while adding both V1_21_11 F-4 behavior registrations. F-4 behavior files, mixin, existing shared traversal and affected catalog entries were checked against the accepted candidate. The two F-4 hooks remain distinct operations. No F-4 behavior was dropped.
- Test-disabled build passed on input commit `5289e70dcd58da6a9ffc9181b1a48d817de41546`, tree `3dde7dd35099569b5b8098548d58b23a564758c3`, with `.\gradlew.bat build -x test --init-script workflows/fix-implementation/runs/F005-fall-reset-no-tests-2026-10-09.init.gradle --no-daemon --console=plain`. The init script disabled `:core:test`, `:test`, `:parkourgym-server:test`, and `:testing:test`; 18 actionable tasks (3 executed, 15 up-to-date); no Test task ran.
- Main JAR `build/libs/LegacyParkourCompat+26.2-1.0.0.jar`: 290512 bytes, SHA-256 `AE67E26B9AD7A833FFB52C0B2CC8F98EE6F96DF1B56F7AAB889E1B0053C9790C`. Sources JAR: 180046 bytes, SHA-256 `C5C76A78348AC416AEA2A453692AD1EBDF9D43955CC6EDAB74992FB32E4211DE`.
- Runtime behavior and parity remain unverified. This bounded implementation does not complete the pair or resolve remaining coverage and inventory work.

## Exact report imports

Copied unchanged from the accepted source/code campaign records: main boundary memo blob `16aeb2cf14b5ca33ef3f2fc9510e69875d1a4384`, first boundary memo `239f51263c45e182b68ad031fab5525b24dbe87d`, boundary r2 memo `a87717a2433f58be8efa60c72267e6e60cac5545`, boundary review `3ba8867cfdd59a7e5b11fffba029557aaafad548`, code review `5987f34ddd2929213a8600d4067c84f3f7214f42`, and author implementation report `3fb64870fad061695ad8eea102d75579b2891670`. The source finding and bounded independent source review identities are listed above. Original REQUEST CHANGES and follow-up ACCEPT records are both retained.
