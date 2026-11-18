# Blind source audit: powder snow frost aliases

Scope: named Mojmap anchors 1.17, 1.17.1, 1.18, 1.18.2, 1.19, 1.19.3, 1.19.4, 1.20, and 1.20.2 only. Read only the canonical `decompiled_minecraft/<version>/mojmap/` trees. No claim is made about an uninspected intermediate release or the exact introduction boundary of any change.

## Source identity and pins

Host and shell: Microsoft Windows 10.0.26200, PowerShell 7.6.9. All nine named source roots exist. Mapping family is Mojmap, under the canonical `mojmap` directories.

For 1.17 and 1.20, `mojmap/.decompile-provenance.json` embeds input and per-source SHA-256 pins. The input records identify the exact release metadata and official client/client-mapping artifacts; hashes for `LivingEntity.java`, `Entity.java`, `Player.java`, and `client/player/LocalPlayer.java` were checked against the embedded per-source hashes and all match. These legacy roots have no adjacent `.ready.json`, `.provenance.json`, or source-manifest file, so their source identity rests on the embedded provenance rather than a ready marker.

For the other seven anchors, the adjacent Mojmap ready marker's `versionId` and `mapping` match the named exact ID and `mojmap`; the source-manifest SHA-256 matches the marker's `sourceManifestSha256`; and the provenance record's `versionId` and `namespace` match. The four source files named above match their entries in the source manifest at each anchor.

| Exact anchor | Provenance form | Source-manifest SHA-256 or embedded source pins |
|---|---|---|
| 1.17 | Embedded | Source hashes verified for all four files above; client `14122287d4e4256c2d65ec8c0ccfa3b56448495fa3ccabc87d0f0eeb4fa1c30f`; client mappings `191b957aaa40b294402feef29651ae7a9f1263ec22c5382f3b8fa0a4918b0471` |
| 1.17.1 | Ready + provenance | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` |
| 1.18 | Ready + provenance | `a43c61223ddedbdd8ede4d2daf6a750a58cbba324b30c025429ee1030cfc0d78` |
| 1.18.2 | Ready + provenance | `aea0cb9c6fc8f7a46f0eb82b0388ad58a4659f513be6c0a2be06c0df0c1eb07a` |
| 1.19 | Ready + provenance | `78bf3f9d5b4b5437240dd0707fa7a9ff56e6d1e267bd8edc13adc8313b535e26` |
| 1.19.3 | Ready + provenance | `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843` |
| 1.19.4 | Ready + provenance | `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3` |
| 1.20 | Embedded | Source hashes verified for all four files above; client `5ac9ead135746f5f27048f2e01d8d5bdc2dabc4d91422f73ef2435f4c3d24aff`; client mappings `fdd921d6bcc7657850f78514d36bb57af0b8a340786d12c46ec18d9c46c039de` |
| 1.20.2 | Ready + provenance | `e439ff7e1d3f1d31e01be1edd141f94d917a9dc7fa0de5f69904544cbbe62887` |

The seven source-manifest digests above were also confirmed to equal the ready markers' recorded source-manifest digest. All source hash checks described here were local checks against the published pins; no source tree was changed.

## Frozen comparisons

### Post-travel frost cleanup and recomputation

Across these nine named anchors, `net/minecraft/world/entity/LivingEntity.java` has the same relevant order in `aiStep`: `travel(...)`; if the freeze tick condition is reached, advance frozen ticks by one up to the required maximum when `isInPowderSnow && canFreeze()`, otherwise reduce them by two down to zero; then call `removeFrost()`; then call `tryAddFrost()`. The damage branch follows those calls. Local frozen ticks therefore feed the recomputation after travel on each inspected anchor. The local sequence and gates are equivalent at the named anchors.

The `tryAddFrost()` block-state lookup changes spelling at the 1.19 anchor: through 1.18.2 it tests `!getBlockStateOn().isAir()`, and from 1.19 through 1.20.2 it tests `!getBlockStateOnLegacy().isAir()`. In 1.18.2, `getBlockStateOn()` reads `getOnPos()`, which floors `position.y - 0.2F`. In 1.19 onward, `getBlockStateOnLegacy()` reads `getOnPosLegacy()`, which calls the shared helper with `0.2F`. Thus the changed name retains the same offset/predicate in these anchors; it does not use the newer `getOnPos()` offset of `1.0E-5F`.

### Frost modifier identity, gates, and formula

At each named anchor, `LivingEntity` declares `SPEED_MODIFIER_POWDER_SNOW_UUID` as `1eaf83ff-7207-4596-b37a-d7a07b3ec4ce`. `removeFrost()` gets `Attributes.MOVEMENT_SPEED` and, when the attribute and UUID modifier exist, removes that modifier.

`tryAddFrost()` adds a transient `AttributeModifier` only when the block state on the entity's floor is non-air, `getTicksFrozen() > 0`, and the movement-speed attribute exists. It uses the same UUID, name `Powder snow slow`, amount `-0.05F * getPercentFrozen()`, and `AttributeModifier.Operation.ADDITION` at all nine anchors. The null attribute path returns without adding. There is no direct powder-snow or `canFreeze()` predicate in this modifier-add method; those appear in the preceding frozen-tick update.

In `net/minecraft/world/entity/Entity.java`, all nine anchors have `getPercentFrozen()` equal to `(float) Math.min(getTicksFrozen(), getTicksRequiredToFreeze()) / getTicksRequiredToFreeze()`, and the base `getTicksRequiredToFreeze()` returns `140`. These cap the fractional input to the additive modifier at 1.0 for the base entity implementation.

### Local speed read and when the modifier is received

At every named anchor, `LocalPlayer.move(...)` calls `super.move(...)` and then `updateAutoJump(...)`; `updateAutoJump(...)` reads `this.getSpeed()` when its low-displacement branch needs an input-derived direction. In the `LivingEntity.aiStep()` path, `travel(...)` reaches this local move override before the post-travel `removeFrost()` / `tryAddFrost()` pair. That local cached-speed read is therefore before the frost modifier is recomputed for the current completed travel.

The ordinary attribute-to-player-speed receipt is visible in `net/minecraft/world/entity/player/Player.java`: `Player.aiStep()` calls `super.aiStep()` and, after it returns, calls `setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED))`. Since the `LivingEntity` post-travel frost update completes inside `super.aiStep()`, this later read sees the recomputed modifier and stores the movement speed for subsequent local movement. This boundary and ordering are equivalent in all nine named anchors.

## Source locations

For every exact ID in scope, the verified files are:

- `net/minecraft/world/entity/LivingEntity.java`: UUID declaration; `removeFrost`, `tryAddFrost`, and the `aiStep` travel/frozen-tick/post-travel sequence.
- `net/minecraft/world/entity/Entity.java`: `getPercentFrozen`, `getTicksRequiredToFreeze`; for 1.18.2 onward, the old/new block-position helper definitions used to audit the guard.
- `net/minecraft/client/player/LocalPlayer.java`: `move` / `updateAutoJump` ordering and its cached `getSpeed()` read.
- `net/minecraft/world/entity/player/Player.java`: `aiStep` and the post-`super.aiStep()` movement-attribute-to-speed update.



The exact source locations below were inspected at each anchor (`LivingEntity` fields identify method starts; the `tail` line is the `removeFrost()` call immediately followed by `tryAddFrost()`). All paths are relative to that anchor's `mojmap/` root.

| Anchor | LivingEntity `removeFrost` / `tryAddFrost` / post-travel call | Entity `getPercentFrozen` | LocalPlayer `move` / `getSpeed` read | Player attribute speed receipt |
|---|---:|---:|---:|---:|
| 1.17 | 500 / 509 / 2555 | 2067 | 895 / 911 | 512 |
| 1.17.1 | 500 / 509 / 2559 | 2069 | 893 / 909 | 512 |
| 1.18 | 500 / 509 / 2561 | 2051 | 891 / 907 | 512 |
| 1.18.2 | 504 / 513 / 2565 | 2064 | 889 / 905 | 514 |
| 1.19 | 506 / 515 / 2598 | 2087 | 973 / 989 | 522 |
| 1.19.3 | 505 / 514 / 2612 | 2096 | 900 / 916 | 519 |
| 1.19.4 | 500 / 509 / 2564 | 2116 | 880 / 896 | 517 |
| 1.20 | 515 / 524 / 2586 | 2223 | 872 / 888 | 518 |
| 1.20.2 | 516 / 525 / 2615 | 2261 | 871 / 887 | 523 |

Equivalence verdict: these three requested operations are semantically equivalent across the inspected named anchors. This verdict is bounded to those anchors and their cited source methods; it does not establish patch-by-patch continuity, an uninspected release's behavior, or a precise version cutover.

