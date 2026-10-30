# Eye-fluid tracker and public-query path closure — bounded disposition

## Scope and verdict

Compared the authorized exact source trees `1.13.2`, `1.14`, `1.14.1`, `1.14.2`, `1.14.3`, `1.14.4`, and `26.2`, limited to ordinary local-player movement consumers and direct eye-fluid query callsites beyond the already reviewed cached-water producer/refresh. No additional independent historical movement difference is established in this slice. The historical point query's movement-visible use flows through the player/entity water-submersion cache and swimming update already closed in the eye-query review. Direct local-player query calls found in this bounded inventory are water-vision consumers. This is an operation-scope disposition, not full-pair coverage.

## Exact source identity

Read-only canonical root: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/`.

| Release / mapping family | Relevant source | SHA-256 |
| --- | --- | --- |
| 1.13.2 / Ornithe Feather Gen2 build 2 | `entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` |
| 1.13.2 / Ornithe Feather Gen2 build 2 | `client/entity/living/player/LocalClientPlayerEntity.java` | `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf` |
| 26.2 / unobfuscated | `world/entity/Entity.java` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| 26.2 / unobfuscated | `client/player/LocalPlayer.java` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |

Hashes were recomputed from the canonical trees and agree with the source identities stated in the permitted `source-review.md`. This bounded disposition reuses that review's paired-source closure for 1.14 and 26.2; it does not claim separate revalidation of all metadata artifacts.

## Call graph and timing

- In 1.13.2, `Entity.m_03231680` performs `checkWaterState`, then `m_61708540`, then `updateSwimming` (`entity/Entity.java:947-950`). `m_61708540` assigns `submergedInWater = isSubmergedIn(WATER)` (`968-969`), and `updateSwimming` consumes that cached field through `isSubmergedInWater()` (`955-965`). The player tick refreshes its own cached value before `super.tick` (paired `PlayerEntity.java:206-208,257-260` in the permitted review).
- In inspected 1.14-family sources, player tick similarly refreshes the player water cache before `super.tick`; entity water-state logic refreshes the entity-side cache before `updateSwimming` (the family aliases/locators are recorded in permitted `source-review.md`). Local-player sprint/swim checks consume `isSubmergedInWater()` and `isInWater()` (`1.13.2 LocalClientPlayerEntity.java:723-752`; `1.14.4 LocalClientPlayerEntity.java:654-684`). These decision sites therefore depend on the per-tick cached water result, not an independent direct public-query algorithm.
- In 26.2, `Player.tick` calls `updateIsUnderwater` before `super.tick` (`Player.java:259-260`); `updateIsUnderwater` reads the existing `isEyeInFluid(WATER)` tracker (`304-307`). Later in `Entity.baseTick`, `wasEyeInWater` captures the pre-refresh tracker, `updateFluidInteraction()` refreshes it, and `updateSwimming()` consumes `isUnderWater()` (`Entity.java:530-532,1613-1615,1635-1641`). Thus the local-player/ordinary-player decision cache retains a previous-sample ordering. The direct `LocalPlayer.isEyeInFluid(WATER)` calls at `863-868` and `getWaterVision` at `1154-1158` serve water-vision behavior; sprint checks call `isUnderWater()` (`1143-1148`), which returns cached `wasUnderwater` (`1176-1179`).

## Direct public-query inventory and disposition

- 1.13.2 local player direct `isSubmergedIn(WATER)` at `LocalClientPlayerEntity.java:789-794,982-986` updates/reads underwater-vision state. The movement decision sites at `723-752` use the `isSubmergedInWater()` cache.
- 1.14.0 through 1.14.4 direct local-player water query calls appear in the water-vision tick/accessor and render paths. The paired review records exact aliases and confirms client `isChunkLoaded` overrides return true; the `checkLoaded` refresh argument therefore adds no actual client residency rejection. Movement sprint/swim decisions use the cached player water-submersion value.
- 26.2 local-player direct `isEyeInFluid(WATER)` at `863-868,1154-1158` affects water vision. The sprint/underwater predicate is cached as `wasUnderwater`; movement swimming uses `wasEyeInWater` and current body water. No separate direct eye-query movement caller was found in this bounded ordinary-player callsite search.
- Other search hits are rendering/HUD/audio, mining/turtle helmet behavior, non-player entity AI, boats, or server-side air/drowning paths; they do not create an additional ordinary client player movement operation in the assigned slice.

Accordingly, the eye-fluid API evolution from point sample to tracker is already represented by the accepted producer/query operation and its cached movement consumers. No separate finding should be created for a public-query path in this scope. The historical direct water-vision reads are eligible direct player predicates only if their visual output is later included, but rendering is excluded by the project invariant.

## Remaining gates

- This disposition does not establish full-pair movement inventory or independent full-pair audit.
- It does not establish an exact first-patch boundary between 1.13.2 and inspected 1.14 releases, or a broad cutoff to 26.2.
- No runtime parity or measured trajectory claim is made.
- Existing source-review limits remain: 1.13.3–1.13.9 and releases after 26.2 were not inspected; 1.14.0–1.14.3 rely on provenance-contained per-file identities rather than separate ready-marker/source-manifest layout.
