# Discovery: 1.13.2 to 1.14.4

- Status: partial
- Scope: exact Java Edition 1.13.2 (A) and 1.14.4 (B), client player movement.
- Repository revision at restart: `5a68659bd869e6028d151a8aed3cd91a9eae88c9`; 2026-09-27.
- Selected namespace: Ornithe Feather `feather-gen2` named mappings for both exact releases. Both mapping files declare Tiny v2 `official`, `intermediary`, `named` columns. The compared player and input classes retain matching named package/class paths. Member correspondence still requires per-slice inspection.
- Source preparation: `decompileMinecraft --versions=1.13.2 --mappings=feather` and `decompileMinecraft --versions=1.14.4 --mappings=feather`, each with `-g .gradle-user-home --no-daemon --console=plain`. Both tasks resolved the requested release exactly, logged `Finished <version> using ornithe-feather`, and ended `BUILD SUCCESSFUL` on 2026-09-27. The Gradle executable came from a local Gradle 9.7.1 distribution. Raw task logs were not retained.
- Toolchain: Gradle 9.7.1; JDK 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; default 4G decompiler heap. Relevant source bodies must still be checked for decompiler damage. The 1.14.4 remapper repaired access for 6 classes and 61 members; its visible Vineflower duplicate-processing notices concerned `ModelBakery`. The 1.13.2 output included a mismatched `Scheduler$Tasks` signature notice.

The previous `blocked: mapping alignment` conclusion was wrong. It checked only existing output folders and did not query `feather` for 1.14.4. The explicit task successfully resolved `net.ornithemc:feather-gen2:1.14.4+build.2`. An explicit `yarn` probe for 1.13.2 reported `yarn has no builds for this version`; Yarn and Legacy Yarn need no cross-family assumption for this run. The 1.14.4 Mojmap output cited in the previous checkpoint remains separate provenance and is not used as paired evidence here.

## Artifact manifest

Paths below are relative to this manifest. Sources and cache artifacts are ignored local files. The shared source directory in the primary checkout was empty at restart; both verified Feather source trees were copied there afterward for other discovery worktrees. Each copy has the expected Java file count and a matching `KeyboardInput.java` SHA-256. Cache artifacts remain in this worktree.

### A — 1.13.2

- Requested/resolved: `1.13.2` / `1.13.2`.
- Client jar: `../../../../build/minecraft-decompile-cache/1.13.2/client.jar`; SHA-256 `3410887BA652F25792C7675BFAF9140E73B60E93CFBF113A803F8A98CB05C0F9`.
- Mapping coordinate: `net.ornithemc:feather-gen2:1.13.2+build.2`; mapping jar `../../../../build/minecraft-decompile-cache/yarn/feather-gen2-1.13.2+build.2-mergedv2.jar`, SHA-256 `317384D4FACCC2939C3B14252993D31745EB42AAD59F2F0EA78893CBE4E7283B`; Tiny file in the same cache, SHA-256 `B3FD787448AED2C6E115D965D9EDBB437CE47794BA63C7883E9014FD41262F71`.
- Remapped jar: `../../../../build/minecraft-decompile-cache/1.13.2/client-ornithe-feather.jar`; SHA-256 `AA1C9F0EB08787CD8C7FB9C49F3EF858895C341C4F63BC0127B05165BA2B39E3`. This regenerated jar hash differs from the older owner checkpoint; the mapping Tiny hash and original client hash match that checkpoint. Findings added from this point must use the current source hashes.
- Version metadata SHA-256 `26DDCD28289A44D4AE4179307B7193CE0227D2DA57350E717FF1D8C6E27E8C95`.
- Source root: `../../../../decompiled_minecraft/1.13.2/ornithe-feather/`; 2,711 Java files.

### B — 1.14.4

- Requested/resolved: `1.14.4` / `1.14.4`.
- Client jar: `../../../../build/minecraft-decompile-cache/1.14.4/client.jar`; SHA-256 `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`.
- Mapping coordinate: `net.ornithemc:feather-gen2:1.14.4+build.2`; mapping jar `../../../../build/minecraft-decompile-cache/yarn/feather-gen2-1.14.4+build.2-mergedv2.jar`, SHA-256 `3162806B9FB266D7E6D2C594BE8D4CC6C91DDB09B4E55D1B566E5429EDBD793F`; Tiny file in the same cache, SHA-256 `60D4906621C873DADBA96425D1A233349C9AFA407D09BFC8371202AF6A71F25C`.
- Remapped jar: `../../../../build/minecraft-decompile-cache/1.14.4/client-ornithe-feather.jar`; SHA-256 `8F825DE778F12D872A544E46567297D4436D6979B73FC0F482579D9851C1DB82`.
- Version metadata SHA-256 `615F466A39D2C19AE9B6E2401AA7BD07DBDA337F976BCCE383326B8D6BA4E532`.
- Source root: `../../../../decompiled_minecraft/1.14.4/ornithe-feather/`; 3,250 Java files.

### Cited source hashes

| Side | Path under source root | SHA-256 |
| --- | --- | --- |
| A | `net/minecraft/client/entity/living/player/Input.java` | `9E704CFE7FDC55C4EAB78670E60CF392BA6817ADBD5E7451D3A86F11DA8BF50E` |
| B | `net/minecraft/client/entity/living/player/Input.java` | `ACB63D46FB6E6A1CB6B285A5D0702FB62575F7D54344AD1F0F9D51E8F671F33F` |
| A | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `7BE11425906BE051C83E275F359816546E4677B16D212156380E8D2E9258654A` |
| B | `net/minecraft/client/entity/living/player/KeyboardInput.java` | `5932453A9E48E7A798AE1BE1CD3A4BF660B6B3BE43BB7E5DC22686B3C4A82526` |
| A | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2583495F3A02B4791AA036E6A8D354D7596C4984761969A0F29B29D8D9BF42BF` |
| B | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `708AF6A3880FB58B67BF4604A5509B351719A9C2A0C06A8EC261586435BF00CE` |
| B | `net/minecraft/entity/Entity.java` | `7315A496C195DA767DE9D4936D3ADB6EFC3C419DC0F0E95D6F32781B0DA1BA55` |

## Correspondence and call order

Both `LocalClientPlayerEntity` classes extend the corresponding `ClientPlayerEntity`; both use an `Input` field whose `KeyboardInput` implementation samples movement keys before item-use attenuation and sprint checks. A `LocalClientPlayerEntity.mobTick()` calls `input.tick()` at line 702; B calls `input.tick(bl4, isSpectator())` at line 630. The method signatures and caller order establish correspondence despite the added arguments.

A `KeyboardInput.tick()` lines 13–49 resets and increments/decrements float movement fields from the four direction keys. B `KeyboardInput.tick(boolean, boolean)` lines 13–25 computes the same -1, 0 or 1 directional values from those key booleans, then applies the same `(float)(value * 0.3)` arithmetic under a changed gate. A and B `Input` expose the same movement fields and `getMovement()` vector, so the sampled values feed the local player path. B's first extra argument is `LocalClientPlayerEntity.m_63723874() || Entity.m_99544176()`; B `m_63723874()` lines 596–598 depends on flying, swimming and crouch/standing pose tests, while `Entity.m_99544176()` lines 1822–1824 tests swimming pose outside water. Mapping coverage leaves some of these members unnamed, so inspect their callers and state writers rather than inferring from names.

## Coverage ledger

- Stage 1, raw four-direction keyboard sampling before sneak slowdown: `compared-no-difference` for -1, 0 and 1 values under corresponding key states. A `KeyboardInput.tick()` lines 13–45 and B `KeyboardInput.tick(boolean, boolean)` lines 13–21; caller ordering at A `LocalClientPlayerEntity.java:698-704`, B `:625-632`. This scoped conclusion does not cover crouch, item use or sprint gating.
- Stage 1, sneak/pose/spectator slowdown gate: `in-progress`. A `KeyboardInput.java:46-49` applies 0.3 when the sneak key is down. B `KeyboardInput.java:22-25` checks `!spectator && (sneakKey || visual pose predicates)`. Resolve pose defaults, state writers and player reachability on both sides before a movement finding or no-difference claim.
- Stage 1 remaining input/tick order, sprint, jump, auto-jump, flight and riding: `pending`.
- Stages 2–7: `pending`; no other movement slice has been compared with this paired Feather source yet. Existing adjacent-run findings are navigation hints, not evidence for this pair.

## Dependency queue and blockers

- `INPUT-POSE`: resolve B's unmapped visual crouch and crawl methods through pose selection, getters and callers, then check corresponding A behavior and the preconditions for changed slowdown.
- `SOURCE-DIAGNOSTICS`: inspect any remapper/decompiler warnings that touch future movement members; existing success messages do not certify every body.
- `REMAINING-STAGES`: inventory and compare the full source-navigation stages 1–7, including block/fluid resources and external player motion inputs.

## Finding index

No confirmed independently scoped movement finding has yet been written for this pair. The changed slowdown gate remains a candidate pending `INPUT-POSE` closure.

## Resume checkpoint

- Last completed slice: raw four-direction keyboard sampling.
- Next bounded slice: A/B `LocalClientPlayerEntity.mobTick`, `KeyboardInput.tick` and the B pose predicates at `LocalClientPlayerEntity.java:596-598` / `Entity.java:1818-1824`; follow their A counterparts and state writers.
- Remaining work: all other stage 1 slices and stages 2–7. Recheck hashes if regenerating either ignored source tree.

## Source audit closure

- Coverage: one bounded compared-no-difference slice, one in-progress slice, and the remaining stages pending. No stage is fully closed.
- Mapping alignment: resolved by same-family `feather-gen2` mappings for both exact releases. The source comparison is now partial, not blocked.
- Limits: no complete source audit, gameplay validation or implementation has been performed. No behavior equivalence across these releases is claimed.
- Runtime validation: not performed (separate workflow).
