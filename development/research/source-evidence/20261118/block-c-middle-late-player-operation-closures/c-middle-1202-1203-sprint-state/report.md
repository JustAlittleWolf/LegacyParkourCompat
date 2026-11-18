# 1.20.2→1.20.3 sprint-state source comparison

## Verdict

No source-confirmed change in the bounded ordinary local on-foot sprint-state machine. The exact `LocalPlayer`, `KeyboardInput`, and `Input` source files are byte-identical across these releases. The inherited sprint getter/setter path in `Entity` and `LivingEntity` also has unchanged behavior. No independent difference finding is warranted for this operation in this pair.

## Exact source identities

Both sides are Mojang-mapped decompilations (Mojmap).

- **1.20.2:** `decompiled_minecraft/1.20.2/mojmap/`. Exact version and mapping are identified by `mojmap.ready.json` (`versionId=1.20.2`, `mapping=mojmap`), paired with `mojmap.provenance.json`. The ready marker binds the source-manifest SHA-256 to `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`; that value matches the full SHA-256 of `mojmap.sources.sha256`. `artifacts.sha256` pins `1.20.2/client.jar` to `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, `client_mappings.txt` to `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`, and `client-mojmap.jar` to `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`. Full SHA-256: `mojmap.ready.json` `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; `mojmap.provenance.json` `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`.
- **1.20.3:** `decompiled_minecraft/1.20.3/mojmap/`, with hidden `.decompile-provenance.json` (full SHA-256 `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`). Its pinned inputs identify the 1.20.3 client JAR (`.../1.20.3.json` metadata input and client artifact) and Mojang `client.txt` mapping input. Full SHA-256 of the pinned client JAR: `bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`; mapping file: `ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`.

Relevant complete source-file SHA-256 values (each independently match the corresponding source manifest/provenance entry):

| Relative source | 1.20.2 SHA-256 | 1.20.3 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` |
| `net/minecraft/client/player/Input.java` | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` |
| `net/minecraft/client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` |
| `net/minecraft/world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `net/minecraft/world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |

## Compared slices and result

- **Keyboard refresh and input representation:** `KeyboardInput.tick` samples up/down/left/right, computes signed float impulses, then samples jump and shift; the optional slowdown factor is applied to both impulses last (`KeyboardInput.java:12–34`). `Input.hasForwardImpulse()` remains strict `forwardImpulse > 1.0E-5F` (`Input.java:18–24`). Both files are byte-identical.
- **Sprint tick sequencing:** `LocalPlayer.aiStep` decrements a positive `sprintTriggerTime` first (`:646–649`); snapshots jump, shift and sprint impulse before calling `input.tick` (`:655–665`); and then uses the newly refreshed input in later sprint checks. `keySprint` is queried directly from the current options at the initiation sites rather than stored in `Input`. In the ordinary unmounted path, item use may clear the double-tap timer; key shift clears it at `:666–670,686–688`. Sprint-start eligibility is computed after those samples. The double-tap path arms the timer to `7` only when it is expired and the sprint key is up; otherwise it calls `setSprinting(true)` (`:690–699`). The held-sprint-key path is evaluated next (`:701–703`). A newly started sprint can therefore reach the continuation checks in the same tick.
- **Ordered continuation/cancel gates:** If sprinting, loss of forward impulse or the opaque food-start gate forms `$$8`; the next ordered cancel reason `$$9` adds major horizontal collision (horizontal without minor collision) or in-water/not-underwater (`:705–714`). Swimming uses its separate ordered expression `(!onGround && !input.shiftKeyDown && $$8) || !isInWater`; non-swimming uses `$$9`. These gates and write sites are identical in both files. The helper `hasEnoughImpulseToStartSprinting` retains the underwater `Input.hasForwardImpulse()` versus dry `forwardImpulse >= 0.8` split (`:1013–1034`).
- **Actual local sprint flag writes:** `LocalPlayer` calls `setSprinting(true/false)` at `:697,702,710,713`. `LivingEntity.setSprinting` still calls `Entity.setSprinting`, removes the sprint speed modifier, and re-adds it only when enabling (`1.20.2 LivingEntity.java:1954–1961`; `1.20.3:1951–1958`). `Entity.isSprinting` and `setSprinting` still read/write shared flag 3 (`1.20.2 Entity.java:2154–2160`; `1.20.3:2155–2161`).

The only diffs in the checked `Entity` and `LivingEntity` files concern unrelated operations; sprint methods above were checked directly at their complete method bodies. File-level `LocalPlayer`, `KeyboardInput`, and `Input` diffs are empty, and their complete-file SHA-256 values match.

## Bounds

Covered: ordinary local on-foot keyboard-driven sprint initiation, timer decrement/clear/arm behavior, pre-refresh and post-refresh samples, held-key initiation, continuation cancellation order, and actual inherited sprint-flag/modifier writes.

Not covered: mounted behavior; effect and food production or the internals of those sprint gates; jump behavior beyond its sampled sprint-gate role; active-item slowdown math; geometry/fluids beyond the direct opaque sprint gates; unrelated sprint consumers such as rendering, particles and attack behavior. This is a bounded source comparison, not runtime parity evidence.