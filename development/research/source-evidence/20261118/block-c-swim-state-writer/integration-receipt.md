# Product integration receipt: 1.13 swimming-state writer fixture

## Immutable product, review, and build identities

- Product ready ref to create: `refs/campaign-ready/c-swim-state-113`
- Code commit: `364b9f00769eea817e904b6591545231cb8dfa79`
- Code tree compiled and independently reviewed: `798f940e99c4bd6c60893f1d602c6e7c3bb53a23`
- Code parent / evidence-only commit: `433803266f043b58a6be477b6118544c964d1e05`
- Evidence archive commit and tree: `24d46fd51e05886fe4ecb6807ee95fc344194faf` / `9e926829a7397edbf5bc19d0eef59f19a868f031`
- Evidence bundle SHA-256: `05e20c1a2206738baf6f9ed3b10253c466a2a378407d38d52caf0c0d80f224ad`
- Candidate input, not included as product ancestry: `3f2207e63d767719cab5e081ec092b8bd1c1d95f` (tree `972a2aa91a910b08645b4db756fe54ef787fde96`)
- Independent product review: signed PASS by `c_swim_state_113_product_review` for the exact code commit/tree above. The reviewer confirmed the compiled 26.2 `Player.updateSwimming()V` call uses `INVOKESPECIAL Avatar.updateSwimming()V`.
- Product paths: `src/client/java/me/wolfii/legacyparkourcompat/mixin/PlayerMixin.java`, `src/client/java/me/wolfii/legacyparkourcompat/mechanic/hook/player/SwimmingUpdateHook.java`, `src/client/java/me/wolfii/legacyparkourcompat/change/v1_12/NativeSwimmingUpdate.java`, and `src/client/java/me/wolfii/legacyparkourcompat/change/v1_13/SwimmingUpdate.java`.

Required Windows build command:

```text
.\gradlew.bat build -x test --init-script development/disable-tests.gradle --console=plain
```

Result: `BUILD SUCCESSFUL` on code commit `364b9f00769eea817e904b6591545231cb8dfa79` and tree `798f940e99c4bd6c60893f1d602c6e7c3bb53a23`, using Temurin OpenJDK 25.0.3 on Windows. The init script disabled every Gradle `Test` task and `-x test` excluded test execution. The build reported 19 actionable tasks, 3 executed and 16 up-to-date. No tests, runtime/game, TAS, Gym, server, or Docker launch was performed. Compilation does not establish runtime parity.

## Accepted source evidence and exact scope

The evidence archive preserves the accepted comparison, destination comparison, route witness, and independent verdicts byte-for-byte. Their exact report/verdict SHA-256 values are:

| Evidence | Report SHA-256 | Verdict SHA-256 |
|---|---|---|
| 1.13 → 1.13.1 state-writer comparison | `6197165f66f254c57ed900b8d2eec75f90fa35594d43a9d93ea48857a9fedb60` | `ba21b9998e9433896a19b41c3cd66d80893d707de2858e672b8827845542463f` |
| 1.13 → 26.2 destination comparison | `a19278a995a24f7d9c2a6c30bef5ed57420a854979ca4d8046a6806986297e20` | `c22225c458959a47674060e37cddba39038661d51c315003bdc2e98e7d5b2bd8` |
| 1.13 ordinary local-route witness | `84f7ee2b9c864f0f73b24c9575f00ffeb52ff32874d744f425d2245b6a6848fa` | `8907af733db10133307031ee13c94b3c03b12b830333152a5e6dcd7f392df120` |

The implemented provider is annotated `V1_13`, which is exactly Minecraft 1.13.0. For that profile it suppresses only the superclass delegation in the existing non-flying `Player.updateSwimming` branch, and only for the accepted fixture: a local client player who is already swimming, not flying, not a passenger, with the floored feet state exactly equal to default `Blocks.WATER` and the state immediately above exactly equal to default `Blocks.STONE`. The upper-state read occurs only after the exact feet-water check. All other states delegate natively.

Resolution remains per hook: selected profiles V1_8 through V1_12 resolve to the always-delegate native fence; V1_13 resolves to the fixture-scoped provider; V1_13_1 and later have no provider and therefore retain native delegation. The mixin wrapper targets only `Player.updateSwimming()V`'s actual `Avatar.updateSwimming()V` invocation. The flying branch, other calls and state writers are unchanged. This is not a generalized solid/material/shape rule and makes no runtime-parity claim.

The central backlog's existing `1.12.2--1.13.2/swim-look.md` association concerns a separate swim look-vector operation; this batch does not associate or close it. No backlog entry directly identifying this state-writer operation was found, and no backlog or finding file was changed.
