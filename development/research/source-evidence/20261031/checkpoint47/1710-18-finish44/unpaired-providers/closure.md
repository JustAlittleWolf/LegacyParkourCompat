# 1.7.10–1.8 unpaired collision-provider closure

## Bounded scope and evidence identity

This is a source-only follow-up to the permitted provider roster at `.native-source/20261030/1710-18-pool42/provider_registration/`. It audits only declaration membership changes and inherited/replacement paths among block collision providers; it does not compare all provider state branches or claim full-pair freeze.

- 1.7.10: Ornithe Feather `1.7.10+build.2`, canonical read-only root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.7.10\ornithe-feather`; provenance SHA-256 `11BADA441EBE07661402D1C40E1C490B78B279ECAA084F9A4DC3AE2DD54CAF6F`; raw client SHA-256 `a4fc2284657544e0f4bcc964f927c2fda3e3a205178ed1d5d58883aaf9780cce`.
- 1.8: Ornithe Feather `1.8+build.2`, canonical read-only root `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.8\ornithe-feather`; provenance SHA-256 `52C8312C34A1A491D4A200C84E90F5D8D95C7E5CAA9571F864D8E70F9737DB05`; raw client SHA-256 `ccccd0a92f76fbb24279691daad9b67f85d80471a37e59e0da76d7754982dfe`.
- Mapping family: Feather on both sides.
- Prior permitted roster inventory has 45 class rows: 44 classes with direct provider declarations per side, 43 classes with declarations on both sides, and no direct declaration member-name/count mismatch among those 43. The sole old-only direct declaration is `CarpetBlock.getCollisionShape`; the sole new-only provider declaration is `BannerBlock.getCollisionShape`.

## Historical Carpet provider closes through inheritance

1.7.10 `net/minecraft/block/Block.java:662` registers a `CarpetBlock` at numeric ID 171/key `carpet`. `CarpetBlock.java:14-20` extends `Block` and sets its ordinary shape to height `1/16`. Its direct collision override at lines 29-33 instead constructs a box whose `maxY` is `y + 0 * 0.0625F`, while `minY` is `y + this.minY` (zero after the constructor). Thus this provider's returned collision box has zero vertical extent, despite the stored block shape being 1/16 high. The paired 1.7.10 `Block.addCollisions` at `Block.java:836-841` calls the virtual provider and adds only a non-null shape intersecting the query box. `Box.intersects` at `util/math/Box.java:172-177` compares the candidate interval against the query interval but does not independently reject a zero-height candidate; the degenerate box can be added if its Y point is strictly inside the query and X/Z overlap.

The carpet instance remains registered at ID 171/key `carpet` in 1.8 (`Block.java:1089`). `CarpetBlock.java:17-27,49-53` still sets/updates its stored shape to height `1/16`, but the class no longer declares `getCollisionShape`. It extends `Block` at line 17, so lookup resolves to the paired base provider `Block.getCollisionShape(World, BlockPos, BlockState)` (`Block.java:299-303`), which constructs a box from `minY` through `maxY`. `Block.addCollisions` (`Block.java:292-297`) retains the non-null/intersection gate. The 1.8 `Box.intersects` comparisons (`Box.java:153-158`) are the same strict interval test. Consequently registered carpet now contributes its non-degenerate 1/16 collision box when the entity query intersects it; this is a historical-player movement-relevant membership/replacement difference, not a removed block or a class-renaming artifact.

The query is reached from player/entity movement: 1.7.10 `Entity.move` calls `World.getCollisions` at `Entity.java:457,503`, and sneak-edge support probes call it at `:413,425,435`; 1.8 equivalents are `Entity.java:438,468` and `:396,408,418`. Each version's `World.getCollisions` scans blocks and dispatches virtually through the block instance's `addCollisions` (`World.java:972-993` in 1.7.10; `:873-903` in 1.8). In 1.7.10 the base `addCollisions` then invokes the carpet override; in 1.8 the same base collector invokes the inherited `Block.getCollisionShape` replacement.

## 1.8-only Banner provider is modern-only

1.8 `Block.java:1103-1104` registers standing and wall banner instances, both subclasses of `BannerBlock`. `BannerBlock.java:21-35` is a new provider class in this roster and its direct `getCollisionShape` returns `null`; the base collision collector consequently adds no collision box. A case-sensitive scan of the 1.7.10 `net/minecraft/block/` sources found no BannerBlock or banner registration. This is an 1.8 feature addition with no old-era block or old-profile provider behavior to emulate; its provider is classified modern-only and excluded from the 1.7.10 historical provider set.

## Declaration membership closure

All other 43 old-era classes with a direct collision declaration, including base `Block`, have the same provider method name(s) and declaration counts in the same class in 1.8. No additional old-only declaration or old-class-to-differently-named-provider replacement path appeared in the complete permitted roster. The Carpet path above is the only inherited replacement found; the Banner path is the only 1.8-only provider addition. This closes declaration membership and replacement tracing for this bounded slice, not every branch/formula in the paired providers.

## Relevant exact file hashes

| File | 1.7.10 SHA-256 | 1.8 SHA-256 |
|---|---|---|
| `net/minecraft/block/CarpetBlock.java` | `BBDD4B80D0CFD7B5AA69313D05D2C70154D5D37545F1DE6DF776CEBA14D7A6F2` | `75016CB9C1C7E237B2A09833D9AB9169C0A542F849D8F22ADAA0E804B820B9CF` |
| `net/minecraft/block/Block.java` | `462EA8943470DE83F2D923D1D267BEBC6B9AF05EECF3C60C5E90EAE870394D0B` | `4A4E6DFDE24A135ECF87C359FD98F0380664E4BACAF679815C9016CB1BA0D814` |
| `net/minecraft/world/World.java` | `2D648EDFE7FDC953825A1DF94709E889EA21354494D85762BCA49B4A109D01F9` | `AFBC701EF8B66020BDBB2831FD80683705D20237C68FBF23A4001C020876FC6D` |
| `net/minecraft/entity/Entity.java` | `AF4EF7B11BC709D96BD3EEA1260A61D38A1A04996BDF87F2A77C459DDF070A69` | `2BE081ABE1720F89D7AB90F782B8411E71BC5B8A6B53966B49A2380E3C47FFD9` |
| `net/minecraft/util/math/Box.java` | `0EA7FB1600F9E5D7566593310830D509FEC44AAB792DB6D2D7CFA4CEBEFC6F0A` | `2D270470A11082E1CF9F772C94ECFF9ECBF5430BE78947DD152E2E72C20BBC52` |
| `net/minecraft/block/BannerBlock.java` | absent | `A510F71E59E53900D942A566C35FE59A321E7711587103D77B803B4630971035` |
