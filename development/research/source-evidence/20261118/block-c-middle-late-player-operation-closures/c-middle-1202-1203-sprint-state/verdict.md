# Independent source audit: 1.20.2–1.20.3 sprint state

## Verdict

**Accepted for the stated bounded claim:** the audited ordinary local on-foot sprint input and sprint-state paths have no source-confirmed change between Mojang-mapped 1.20.2 and 1.20.3. No independent difference finding is warranted for that bounded operation. This verdict accepts source comparison only; it does not establish runtime parity or cover the unexamined dependencies listed below.

Reviewed immutable report: `c-middle-1202-1203-sprint-state/report.md`, SHA-256 `32cd1266830ca1303a3cdfef80683cfc4b4dffe7d374b6cb2053aee5105da82f`.

## Source identity and hash check

Read-only canonical sources were under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`.

- 1.20.2 `mojmap.ready.json` declares `versionId=1.20.2`, `mapping=mojmap`; its source manifest SHA-256 is `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887`, matching the full hash of `mojmap.sources.sha256`. The ready marker’s full hash is `5ff3322f287c250ee914c7ca3840d053bffc3c2c5f35e91b59314d948292483f`; the adjacent provenance hash is `1dad9f725f6cb25b6568700fa0294f83b66ea9633b0d62ed354d7bd8b45d4f05`; the artifact-manifest hash is `ee67da9733953b30ae4077c587184e59772b745d780abd27a7b023780b459f54`. Its manifest pins client JAR `fa1a19be56a506426308abbc1cad85f299a7fc6dae4335559351ba0246713fda`, Mojang mappings `d4e2eb2b32ae7bd2e95ea3a8391813a7b6795e20884cf01d989dce68f906e2fd`, and mapped archive `3dce01cbbb74e367414c811440ca6e80bc2ec18284386720ce413f9aec0ba901`.
- 1.20.3 `.decompile-provenance.json` full SHA-256 is `c3d605e55b287e800a86af8567a75edcb009d1a9a1744cfcb4e6f47667dcee0f`. Its recorded inputs identify the exact 1.20.3 metadata (`05e727aac55594b6014e9714ad38fa16af09f075daf5efa0f5743f2453fa8fc2`), client JAR (`bef21938224902dd925e2d89dcfe1d2540656d4cf7a5abd2b93e0402d135c655`) and Mojang `client.txt` mappings (`ad03c803d866062909dd378ed5f1611687dc0ed0a76cc7fd57e968a99d641e5f`). The five reviewed source hashes below agree with that provenance’s `sources` entries.

Direct SHA-256 checks agree with each release’s source hash record/provenance:

| Source | 1.20.2 | 1.20.3 |
|---|---|---|
| `client/player/KeyboardInput.java` | `a8064906872955a3520398ab5b2a326552d424f41887d1294aa6a038e2623ff0` | same |
| `client/player/Input.java` | `b302ffbc45c5f900ea18a4d4af2df6fa0454ea7cb7744a0d249061e5fcb97fbb` | same |
| `client/player/LocalPlayer.java` | `bb5cbfb03656a1866bb77c00431618befe792081223b35db4bd121ecbb151fd5` | same |
| `world/entity/Entity.java` | `d7ee49aaea5e862b92508e767562cabc8f10515605e8fb67565c8c8d5c23b01b` | `07383522bff169938136638ef8c3244ca511b56ca4266913524f99f9821331b9` |
| `world/entity/LivingEntity.java` | `5c481da1ffc8684c4b30171c92e61fad751e6a3a708d5bc6486ac9f96ff69828` | `f7bc53db24c1798f19f9bd6f6356c86d5e560e9c8e8aac60decaf15cc785e07d` |

## Independent trace

- `KeyboardInput.tick` (both: `KeyboardInput.java:21–34`) samples directional keys, calculates signed float impulses, samples jump and shift, then optionally scales both movement impulses. `Input.hasForwardImpulse` is the strict `forwardImpulse > 1.0E-5F` predicate (`Input.java:22–24`). These complete files are byte-identical.
- `LocalPlayer.aiStep` (both: `LocalPlayer.java:646–715`) decrements a positive sprint timer first. It snapshots jumping, shift and the pre-refresh sprint impulse at `:655–657`, then calls `input.tick` at `:664`; subsequent eligibility/continuation checks therefore see refreshed input while `$$1`/`$$2` remain pre-refresh samples. The field has no other writes: the complete occurrence list is declaration `:115`, decrement `:647–648`, clears `:669` and `:687`, expiry/key-up test and arm-to-7 at `:694–695`. In an on-foot run, the first qualifying double-tap check arms the timer while sprint key is up; a subsequent qualifying check sets sprint true. The sprint key is queried directly in the held-key initiation branch at `:701–702`.
- `canStartSprinting` at `:1013–1020` requires not already sprinting, sufficient impulse and food, no item use or blindness, an allowed passenger/vehicle case, and no fall flying. Its dry impulse threshold is `>= 0.8`; underwater it uses `Input.hasForwardImpulse` (`:1027–1030`). The initiation branch additionally requires ground/underwater and the pre-refresh `!shift && !enoughImpulse` double-tap condition (`:690–703`).
- The continuation branch at `:705–714` clears sprint for loss of positive forward impulse or the food gate, major horizontal collision, or in-water but not underwater. Swimming has its separate ordered conditional at `:708–710`. `LocalPlayer` calls `setSprinting` only at `:697`, `:702`, `:710`, `:713` in both sources.
- Although the containing files differ, the inherited sprint flag methods are unchanged in behavior: `Entity.isSprinting` reads shared flag 3 and `Entity.setSprinting` writes it (1.20.2 `Entity.java:2154–2160`; 1.20.3 `:2155–2161`). `LivingEntity.setSprinting` calls the superclass setter, removes the sprint movement-speed modifier, then adds the transient modifier only when enabling (1.20.2 `LivingEntity.java:1954–1961`; 1.20.3 `:1951–1958`).

## Scope notes

1. The report documents the item-use branch’s `0.2F` impulse scaling and timer clear at `LocalPlayer.java:666–670`, while its bounds say “active-item slowdown math” was not covered. This is a boundary-description ambiguity, not a counterexample to the no-difference claim: `LocalPlayer.java` is byte-identical across the pair. Treat active-item behavior as outside the accepted operation-specific comparison unless a separate bounded review is requested.
2. `aiStep` contains passenger, water and swimming branches. The report explicitly excludes mounted behavior and limits its accepted claim to ordinary on-foot sprinting; do not expand this verdict to vehicle eligibility or vehicle-dependent movement. Identical `LocalPlayer` bytes alone do not audit changes in the vehicle or other dependencies.
3. Food-gate internals, active-item slowdown math, jump effects beyond the sampled gate, water/geometry dependencies and non-sprint consumers remain outside this verdict. No runtime or test evidence was gathered.
