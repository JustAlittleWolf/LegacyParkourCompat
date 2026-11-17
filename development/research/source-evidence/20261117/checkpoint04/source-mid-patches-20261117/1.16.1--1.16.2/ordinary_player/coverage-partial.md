# 1.16.1 → 1.16.2 source-only movement coverage (partial)

## Immutable source identity

- Older: Minecraft 1.16.1, Mojmap; canonical root `decompiled_minecraft/1.16.1/mojmap/`; 3,572 Java files. Source manifest `decompiled_minecraft/1.16.1/mojmap.sources.sha256`, SHA-256 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; provenance `decompiled_minecraft/1.16.1/mojmap.provenance.json`, SHA-256 `a6ff065188a400ec891980cdf2e6c0c238bf5e05168a2f69b29c6cecc2214b98`; ready sidecar `decompiled_minecraft/1.16.1/mojmap.ready.json`, SHA-256 `c19d707f7d6622733b5189599ff77aa7999cc11a3d900f29d150f9fab203ffcb`.
- Newer: Minecraft 1.16.2, Mojmap; canonical root `decompiled_minecraft/1.16.2/mojmap/`; 3,516 Java files. Source manifest `decompiled_minecraft/1.16.2/mojmap.sources.sha256`, SHA-256 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`; provenance `decompiled_minecraft/1.16.2/mojmap.provenance.json`, SHA-256 `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; ready sidecar `decompiled_minecraft/1.16.2/mojmap.ready.json`, SHA-256 `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`.
- Input locator `build/source-inputs-115-116-20261117/README.md` binds these roots to exact release IDs, Mojmap, source manifest/provenance and official mappings. It states full 3,572/3,516 file inventories and source hashes were verified, with zero inventory/hash mismatches. No source files were changed.
- Pair inventory comparison: 899 same-path hash changes, 96 additions and 152 removals across complete source manifests. The count is only a navigation aid, not a movement conclusion.

## Checked movement inventory

### Examined paired slices (no pair-wide closure claimed)

- `client/player/LocalPlayer.java`: checked `aiStep` ordering around input tick, no-physics branch and its four player-embedding response calls; the four calls remain in order, but the callee/escape test and velocity choice change. Also checked other displayed tick/input code around those calls. Input class and keyboard input hashes are unchanged.
- `world/entity/player/Player.java`: compared `maybeBackOffFromEdge`, its unchanged 0.05 decrement loops and the added `isAboveGround` predicate. Identified a separate reachable airborne edge-backoff change when crouch is held, player is non-flying and the shifted-below-box collision gate passes.
- `world/entity/LivingEntity.java`: checked jump/travel method diff regions and the Soul Speed caller `onSoulSpeedBlock` → `getBlockSpeedFactor` / `tryAddSoulSpeed`. Travel's movement branches show no other paired textual body delta in the compared hunks. Soul Speed block-position selector changed; membership of `BlockTags.SOUL_SPEED_BLOCKS` remains open pending permitted resource evidence.
- `world/entity/Entity.java`: inspected movement response and collision-related diff regions, `getOnPos`, `getBlockPosBelowThatAffectsMyMovement`, fluid tracking entry points and position/velocity setters. The `move` collision clipping/order body did not show a movement response change in the compared hunk. Fluid tracking did change: `updateInWaterStateAndDoFluidPushing` now evaluates lava even when water is present, and `isInLava` now reads tracked lava height behind a first-tick guard. This needs a dedicated source/caller audit before it can be closed.
- Key support files and identities checked: `world/level/CollisionGetter.java`, `world/level/block/state/BlockBehaviour.java`, `world/level/block/Blocks.java`, `world/phys/AABB.java`, `world/phys/shapes/Shapes.java`, `world/entity/ai/attributes/Attributes.java`, `world/effect/MobEffects.java`, `world/item/enchantment/Enchantments.java`, `tags/BlockTags.java`, `tags/FluidTags.java`. This was a focused support-file check, not a full shape/provider/registration inventory.

### Candidate movement differences drafted for independent review

- `player-airborne-edge-backoff.md`: new `isAboveGround` gate lets crouch backoff run for some near-ground airborne intervals; it is bound to the player’s direct movement vector adjustment in `Entity.move`.
- `local-player-suffocation-escape.md`: corner embedding response changed from cell-by-cell `isSuffocating` tests and nearest clear horizontal neighbor to bounding-box `noBlockCollision` filtering plus axis/step-based direction selection; response still writes a 0.1 horizontal velocity component.
- `soul-speed-block-position.md`: the enchantment speed-factor predicate now checks `getBlockPosBelowThatAffectsMyMovement()` instead of `getBlockStateOn()`. The tag/resource membership and exact reachable ordinary-state case are open.

No source finding has independent acceptance yet. Candidate drafts remain only in this isolated scratch lane.

## Partial, unexamined, and dependencies

- **Partial:** full LocalPlayer tick graph and all inherited `LivingEntity`/`Entity` callbacks; move and post-move collision contacts; changed fluid state/pushing and water-vs-lava branch interactions; direct server corrections/teleports/knockback and their local consumers; climb/fall-flying and fluid-jump thresholds; resource/tag-driven Soul Speed and fluid behavior; all changed block shape/provider/state callbacks and their block/tag registrations; movement effect, equipment and attribute dependency closure.
- **Unexamined inventory:** complete tick graph from input acquisition through input normalization, sprint/jump/crouch decisions, travel, landing and post-travel; every direct position/velocity/box write by callbacks and network responses; all 120 changed block classes plus additions/removals for historical movement shapes, friction, jump factors, climbability and collision callbacks; tag JSON membership and fluid resources; neighboring-block and state-dependent branches; remaining movement-affecting item/effect/attribute paths.
- **Dependencies open:** `BlockTags.SOUL_SPEED_BLOCKS` JSON membership; `FluidTags.WATER` / `FluidTags.LAVA` membership and registered fluid states; block collision shapes and `isSuffocating` semantics for LocalPlayer response; call ordering from local input to entity movement; exact vanilla reachability for candidate cases. Resource extraction was not authorized, so no membership claim is made.
- **Excluded/unchanged scope checked:** attack/damage, vehicle physics, display/rope-hold additions, bed/respawn changes, portal transfer/rework and non-player entity behavior are not findings for ordinary player walking/sprinting/jumping. Their callers were not comprehensively audited.

## Next bounded action

Continue this pair’s source-only closure at the water/lava update and `LivingEntity.travel` call graph, then enumerate the changed block collision/shape/provider and movement effect/tag dependencies. Send the exact report and candidate hashes to an independent blind reviewer. This pair remains PARTIAL; no release onset, full-pair freeze, product applicability or runtime claim is made.
