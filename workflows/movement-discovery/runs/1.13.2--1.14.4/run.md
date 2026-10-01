# Discovery: 1.13.2 to 1.14.4

- Status: partial
- Scope: exact Java Edition 1.13.2 (A) and 1.14.4 (B), client player movement.
- Repository revision at restart: `5a68659bd869e6028d151a8aed3cd91a9eae88c9`; 2026-09-27.
- Selected namespace: Ornithe Feather `feather-gen2` named mappings for both exact releases. Both mapping files declare Tiny v2 `official`, `intermediary`, `named` columns. The compared player and input classes retain matching named package/class paths. Member correspondence still requires per-slice inspection.
- Source preparation: exact Feather Gen2 builds were republished by the sole source-preparation owner to the stable read-only shared staging tree. Canonical markers are `../../../../build/stable-shared-minecraft/ready/1.13.2--feather.json`, `1.14--feather.json` (group base), `1.14.1--feather.json` (patch-boundary check), and `1.14.4--feather.json`; each marker confirms its requested and resolved release. The local ignored junction `build/stable-shared-minecraft` points to the shared staging root; no source tree was copied into this repository.
- Toolchain: Gradle 9.7.1; JDK 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; default 4G decompiler heap. Relevant source bodies must still be checked for decompiler damage. The 1.14.4 remapper repaired access for 6 classes and 61 members; its visible Vineflower duplicate-processing notices concerned `ModelBakery`. The 1.13.2 output included a mismatched `Scheduler$Tasks` signature notice.

The previous `blocked: mapping alignment` conclusion was wrong. It checked only existing output folders and did not query `feather` for 1.14.4. The explicit task successfully resolved `net.ornithemc:feather-gen2:1.14.4+build.2`. An explicit `yarn` probe for 1.13.2 reported `yarn has no builds for this version`; Yarn and Legacy Yarn need no cross-family assumption for this run. The 1.14.4 Mojmap output cited in the previous checkpoint remains separate provenance and is not used as paired evidence here.

## Artifact manifest

Paths below are relative to this manifest. Sources, cache artifacts, and readiness markers are in the shared read-only staging tree and remain outside Git.

### A — 1.13.2

- Requested/resolved: `1.13.2` / `1.13.2`.
- Ready marker: `../../../../build/stable-shared-minecraft/ready/1.13.2--feather.json`.
- Client jar: `../../../../build/stable-shared-minecraft/cache/1.13.2/client.jar`; SHA-256 `3410887BA652F25792C7675BFAF9140E73B60E93CFBF113A803F8A98CB05C0F9`.
- Mapping coordinate: `net.ornithemc:feather-gen2:1.13.2+build.2`; mapping jar `../../../../build/stable-shared-minecraft/cache/yarn/feather-gen2-1.13.2+build.2-mergedv2.jar`, SHA-256 `317384D4FACCC2939C3B14252993D31745EB42AAD59F2F0EA78893CBE4E7283B`; Tiny file in the same cache, SHA-256 `B3FD787448AED2C6E115D965D9EDBB437CE47794BA63C7883E9014FD41262F71`.
- Remapped jar: `../../../../build/stable-shared-minecraft/cache/1.13.2/client-ornithe-feather.jar`; SHA-256 `C300BD63A9142774E7D00DC6C718D35675E07FA8A1BC4FA96E39457E4CD40CFF`.
- Version metadata SHA-256 `26DDCD28289A44D4AE4179307B7193CE0227D2DA57350E717FF1D8C6E27E8C95`.
- Source root: `../../../../build/stable-shared-minecraft/sources/1.13.2/ornithe-feather/`; 2,711 Java files.

### Group base — 1.14

- Requested/resolved: `1.14` / `1.14`.
- Ready marker: `../../../../build/stable-shared-minecraft/ready/1.14--feather.json`.
- Client jar SHA-256 `93907CBF0655AA5F10D049EFA4D745F4250EECC5D0D5D1268046EEC3144F323A`; mapping `net.ornithemc:feather-gen2:1.14+build.2`, mapping jar SHA-256 `57F0E3227099D010C4A0C7F2FEA03DBD665BC7E612AD204DB80FA5B5728E65A3`, Tiny SHA-256 `D865D01921C115A1C20DFDC1B7CDCA1C59FF1694CAD54BD79E146D553417D31F`; remapped jar SHA-256 `E56A5DA7238098083726E35B52F32F8EEEE1133C9FEF7EF853DB104DCBFE1EC7`.
- Source root: `../../../../build/stable-shared-minecraft/sources/1.14/ornithe-feather/`; 3,152 Java files.
- Relevant hashes: `KeyboardInput.java` `5932453A9E48E7A798AE1BE1CD3A4BF660B6B3BE43BB7E5DC22686B3C4A82526`; `LocalClientPlayerEntity.java` `2B1AD3B3416949A9DA2607A3EC2251AA69B2D9EEBD76638C92E20749D625338F`; `Entity.java` `15E9D4CDE45D857CA3117CCFE2D5D9071C08E7EE4A67B4053F7B006FE7A4BFA9`; `PlayerEntity.java` `F73879BD42103FA45F39CFE178AC82C7574C6306F00555AF7F7E795976275DD7`.

### Patch boundary check — 1.14.1

- Requested/resolved: `1.14.1` / `1.14.1`.
- Ready marker: `../../../../build/stable-shared-minecraft/ready/1.14.1--feather.json`.
- Mapping coordinate: `net.ornithemc:feather-gen2:1.14.1+build.2`; original client SHA-256 `7194D1326CF796F62AC55F3CB56F851C232895639AE27510F3207D5A73F91814`; remapped client SHA-256 `3C053F062CDEB1E433BFA03F83A393BE59C5BD7FD589896D2A4788CEA76D5EBE`.
- Source root: `../../../../build/stable-shared-minecraft/sources/1.14.1/ornithe-feather/`; 3,155 Java files.
- `LocalClientPlayerEntity.java` SHA-256 `2B1AD3B3416949A9DA2607A3EC2251AA69B2D9EEBD76638C92E20749D625338F`, byte-identical to the 1.14 group base for the relevant gate and caller.

### B — 1.14.4

- Requested/resolved: `1.14.4` / `1.14.4`.
- Ready marker: `../../../../build/stable-shared-minecraft/ready/1.14.4--feather.json`.
- Client jar: `../../../../build/stable-shared-minecraft/cache/1.14.4/client.jar`; SHA-256 `B3B2A798E2D67B566008FE4A03767AE2C7FF3F8C7BA6751E7B71FC7299672D0A`.
- Mapping coordinate: `net.ornithemc:feather-gen2:1.14.4+build.2`; mapping jar `../../../../build/stable-shared-minecraft/cache/yarn/feather-gen2-1.14.4+build.2-mergedv2.jar`, SHA-256 `3162806B9FB266D7E6D2C594BE8D4CC6C91DDB09B4E55D1B566E5429EDBD793F`; Tiny file in the same cache, SHA-256 `60D4906621C873DADBA96425D1A233349C9AFA407D09BFC8371202AF6A71F25C`.
- Remapped jar: `../../../../build/stable-shared-minecraft/cache/1.14.4/client-ornithe-feather.jar`; SHA-256 `CAD9600844D14B71AFBC5B276003550AABF8335F20EAC308F9EB4EDA12D960A4`.
- Version metadata SHA-256 `615F466A39D2C19AE9B6E2401AA7BD07DBDA337F976BCCE383326B8D6BA4E532`.
- Source root: `../../../../build/stable-shared-minecraft/sources/1.14.4/ornithe-feather/`; 3,250 Java files.

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
| 1.14 base | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2B1AD3B3416949A9DA2607A3EC2251AA69B2D9EEBD76638C92E20749D625338F` |
| 1.14 base | `net/minecraft/entity/Entity.java` | `15E9D4CDE45D857CA3117CCFE2D5D9071C08E7EE4A67B4053F7B006FE7A4BFA9` |
| 1.14 base | `net/minecraft/entity/living/player/PlayerEntity.java` | `F73879BD42103FA45F39CFE178AC82C7574C6306F00555AF7F7E795976275DD7` |
| 1.14.1 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `2B1AD3B3416949A9DA2607A3EC2251AA69B2D9EEBD76638C92E20749D625338F` |

## Correspondence and call order

Both `LocalClientPlayerEntity` classes extend the corresponding `ClientPlayerEntity`; both use an `Input` field whose `KeyboardInput` implementation samples movement keys before item-use attenuation and sprint checks. A `LocalClientPlayerEntity.mobTick()` calls `input.tick()` at line 702; B calls `input.tick(bl4, isSpectator())` at line 630. The method signatures and caller order establish correspondence despite the added arguments.

A `KeyboardInput.tick()` lines 13–49 resets and increments/decrements float movement fields from the four direction keys. B `KeyboardInput.tick(boolean, boolean)` lines 13–25 computes the same -1, 0 or 1 directional values from those key booleans, then applies the same `(float)(value * 0.3)` arithmetic under a changed gate. A and B `Input` expose the same movement fields and `getMovement()` vector, so the sampled values feed the local player path. B's first extra argument is `LocalClientPlayerEntity.m_63723874() || Entity.m_99544176()`; B `m_63723874()` lines 596–598 depends on flying, swimming and crouch/standing pose tests, while `Entity.m_99544176()` lines 1822–1824 tests swimming pose outside water. Mapping coverage leaves some of these members unnamed, so inspect their callers and state writers rather than inferring from names.

## Coverage ledger

- Stage 1, raw four-direction keyboard sampling before sneak slowdown: `compared-no-difference` for -1, 0 and 1 values under corresponding key states. A `KeyboardInput.tick()` lines 13–45 and B `KeyboardInput.tick(boolean, boolean)` lines 13–21; caller ordering at A `LocalClientPlayerEntity.java:698-704`, B `:625-632`. This scoped conclusion does not cover crouch, item use or sprint gating.
- Stage 1, sneak/pose/spectator slowdown gate: `findings`. F001 source-confirms the 1.14 group-base pose/spectator gate and the flight-sneak interaction. F002 source-confirms an additional 1.14-to-1.14.4 swimming-state guard, absent in 1.14.1; its exact first patch remains unresolved after 1.14.1.
- Stage 1 remaining input/tick order, sprint, jump, auto-jump, flight and riding: `pending`.
- Stages 2–7: `pending`; no other movement slice has been compared with this paired Feather source yet. Existing adjacent-run findings are navigation hints, not evidence for this pair.

## Dependency queue and blockers

- `INPUT-PATCH-BOUNDARY`: inspect the `m_63723874()` body in exact 1.14.2 next; continue only across subsequent 1.14 patches needed to establish where F002's `!isSwimming()` guard first appears. Do not register the patch override until this boundary is known.
- `SOURCE-DIAGNOSTICS`: inspect any remapper/decompiler warnings that touch future movement members; existing success messages do not certify every body.
- `REMAINING-STAGES`: inventory and compare the full source-navigation stages 1–7, including block/fluid resources and external player motion inputs.

## Finding index

- [F001](findings/F001-sneak-input-slowdown-gate.md): confirmed 1.14 pose-aware and spectator-aware input slowdown gate, with the adjacent flying-sneak compensation interaction.
- [F002](findings/F002-swimming-pose-slowdown-gate.md): confirmed endpoint change to the swimming-state gate; exact first patch pending.

## Resume checkpoint

- Last completed slice: raw four-direction keyboard sampling; input slowdown gate and pose reachability compared at 1.13.2, 1.14, and 1.14.4.
- Next bounded slice: exact 1.14.2 `LocalClientPlayerEntity.m_63723874()` body for F002's release boundary; 1.14.1 is confirmed unchanged from the 1.14 group base.
- Remaining work: all other stage 1 slices and stages 2–7. Recheck hashes if regenerating either ignored source tree.

## Source audit closure

- Coverage: one bounded compared-no-difference slice, one stage-1 findings slice with an unresolved within-minor boundary, and the remaining stage-1 slices plus stages 2–7 pending. No stage is fully closed.
- Mapping alignment: resolved by same-family `feather-gen2` mappings for both exact releases. The source comparison is now partial, not blocked.
- Limits: only the documented input-slowdown slice has been source-audited. No complete movement audit, gameplay validation, or equivalence claim is made.
- Runtime validation: not performed (separate workflow).
