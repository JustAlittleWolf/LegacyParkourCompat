# Independent technical review: F-005 fall-distance reset

## Verdict

**ACCEPT — no functional findings in the reviewed implementation.** The code change is limited to suppressing the new movement fall-distance reset for player profiles that need pre-1.18.2 behavior. The review validates this at the exact 1.18/1.18.1 and 1.18.2 source endpoints and checks the 26.2 target operation. It does not claim runtime parity.

## Review binding and scope

- **Bound implementation tip:** `f73d176e98fdf1f56c5207046266a168c1834d73` (`fix/reconcile-fall-reset-2026-10-09`). The production change is commit `b4f2cc3742cd0b93fcf9301154b5bba85351bb86`, parent `84bdc9a56bebbc91bc18cfcf1e5fbadda3637dbb`.
- **Accepted source input:** r2 boundary memo `96b6f7253491903c0da443d74102dcc451be1245`, blob `012209d04cc41482729b0bf4f66cd9c23686bf8e`, included in report-only merge `84bdc9a56bebbc91bc18cfcf1e5fbadda3637dbb`.
- **Independent source verdict:** `7dfe93834ec72bf224c5ffe4e25dc102126dee5f`, blob `d2fd6bb126012d7ae16c297e9dbb5c1fa23126ae`; accepts the conditional source witness and retains the exact 1.18.2 boundary.
- **Production diff reviewed:** four files, 47 inserted lines: `EntityMixin.java`, `PlayerFallDistanceResetBehavior.java`, `change/v1_18/FallDistanceReset.java`, and `MovementChangeCatalog.java`. The tip also contains a reconciliation-document update; it was used to bind the candidate and is outside this code review.

## Source and operation checks

The exact 1.18 `Entity.java` and 1.18.1 `Entity.java` are byte-identical (SHA-256 `69417675ae8e0a5bd88b7372baea1e73b1f5217154f39446c8ad5c92731437f1`, matching both ready source manifests). Neither `Entity.move` body contains the fall-damage-resetting clip or a `resetFallDistance()` call after collision resolution. Exact 1.18.2 `Entity.java` (SHA-256 `2228fdaca5793171cbd94038306d571a6ada78ca96f5734efb4cada5b744c10a`) adds the sequence at `move` lines 563–574: resolved movement length must exceed `1.0E-7`; prior `fallDistance` must be nonzero; resolved length squared must be at least `1.0`; a `FALLDAMAGE_RESETTING` clip must hit; then `resetFallDistance()` runs before `setPos`.

The current 26.2 target is the ready `unobfuscated` source publication. Its `Entity.java` (SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`) contains exactly one `resetFallDistance()` invocation in `Entity.move`, at line 747. The current target has its own clip endpoint calculation and an additional enclosing movement condition; the redirect leaves those gates, the clip query, resolved movement, and position write unchanged. It suppresses only the final reset call when the registered player behavior says to do so. `require = 1` matches the one invocation in the target method.

The accepted 1.18.2 r2 witness keeps the reset separate from later writers: the move writes position after the clip reset, then runs fall/collision handling and `tryCheckInsideBlocks`. The cobweb callback can independently reset distance through `makeStuckInBlock`; the accepted witness's final AABB excludes that callback cell. `checkFallDamage` still resets only on ground or accumulates for negative resolved Y; the accepted witness is airborne with positive resolved Y. The later 1.18.2 Player `isAboveGround` predicate reads `fallDistance`. The implementation does not redirect these other writers, callbacks, or consumers. The cited 1.18.2 `Entity.java`, `Player.java`, and `WebBlock.java` hashes match the frozen source manifest.

Publication bindings checked:

- 1.18.1 Mojmap source manifest: `52aa98450b5cfa55ac2bd2054f108e16df7fa939a3f061991b6a9adb434d8d2d`.
- 1.18.2 Mojmap source manifest: `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a`.
- 26.2 unobfuscated source manifest: `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

## Hook, resolver, and fallback

`EntityMixin.java:208–223` redirects only the `Entity.resetFallDistance()V` invocation inside `Entity.move`. It resolves the new singleton `PlayerFallDistanceResetBehavior` only for a `Player`; if the behavior is absent or returns false, it calls the original reset. The common mixin is already listed in `legacyparkourcompat.mixins.json`.

The hook is unique (`player.movement.fall_distance_reset`) and has one explicit registration in the static catalog at `ParkourVersion.V1_18` (`MovementChangeCatalog.java:255–257`). `ChangeResolver` chooses the closest registered version at or after the selected profile. The `V1_18` behavior returns true for selected `V1_17_1` through `V1_18` (which covers 1.18 and 1.18.1) and false for profiles outside that range. For `V1_18_2` and later, the `V1_18` change is older than the selection and does not resolve, so the vanilla reset runs. `CURRENT`/disabled profiles produce an empty profile, and non-player entities fail the explicit type check; both retain the original reset.

No duplicate registration or hook identifier was found. The change introduces no parallel movement loop, damage handler, permission change, or block-state mutation. It changes only the intended player movement state write; downstream vanilla systems continue to consume that state.

## Six review angles

1. **Requirement:** satisfied for the accepted pre-1.18.2 reset delta; exact endpoint source was checked rather than inferred from profile routing.
2. **Correctness and edge cases:** no finding; reset gates and all other movement operations remain intact, with the setter skipped only for the selected legacy player behavior.
3. **Completeness:** no missing call site, config entry, hook registration, or vanilla fallback found.
4. **Conventions and duplicates:** one thin redirect, one independent hook, one change class, and one static-catalog registration; no duplicate technical operation found.
5. **Permissions and visibility:** no permission or public API changes. Player movement state changes only on the intended historical profiles; native, disabled, and non-player behavior retain the setter.
6. **Security:** no new input, authorization, deserialization, or data-exposure path.

## Limits and handoff

No build, tests, client, server, TAS, Gym, or runtime comparison was run. The accepted source witness remains source-level evidence and is not a realized trajectory. The review is bound to implementation tip `f73d176e98fdf1f56c5207046266a168c1834d73`; later author changes are outside this verdict. The serial integration/build owner can now run the prescribed build with all Gradle test tasks disabled.
