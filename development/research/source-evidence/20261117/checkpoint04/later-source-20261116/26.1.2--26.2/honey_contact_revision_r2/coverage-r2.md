# HoneyBlock player slide contact: 26.1.2 → 26.2

## Scope and result

This is a bounded source-only comparison of HoneyBlock's direct movement response when a player intersects the block. It covers the side-slide predicate, vertical reconstruction formulas, velocity write, and the ordinary local-player route that reaches `entityInside`. It excludes collision-shape construction (covered in a separate provider slice), fall-damage calculation, other entities and vehicles, resource membership, and pair-wide closure.

**Bounded NO_DIFF.** The HoneyBlock movement response and its gates match between 26.1.2 and 26.2. The nearby ServerPlayer achievement branch changes from an `instanceof` plus cast to pattern binding, with the same class and tick gates; it does not change movement state.

## Exact source identities

Both source roots are canonical read-only `unobfuscated` decompilations. At each version root, the ready marker's release/mapping and source-manifest digest match the provenance and on-disk source manifest; the provenance declares the exact release and `namespace=unobfuscated`.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Bound source-manifest SHA-256 | Artifact-manifest SHA-256 | Ready identity |
|---|---|---|---|---|---|
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` | `versionId=26.1.2`, `mapping=unobfuscated`, `status=ready` |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` | `versionId=26.2`, `mapping=unobfuscated`, `status=ready` |

The identity files are at `decompiled_minecraft/<version>/unobfuscated.ready.json`, `unobfuscated.provenance.json`, `unobfuscated.sources.sha256`, and `artifacts.sha256`; Java sources are under `decompiled_minecraft/<version>/unobfuscated/`.

## Player call path and movement response

1. On the ordinary local-player tick, `LocalPlayer.tick` calls `super.tick` (both releases, `LocalPlayer.java:227–230`); `Player.tick` calls `super.tick` (`Player.java:259–260`); `LivingEntity.tick` dispatches virtual `this.aiStep()` (`LivingEntity.java:2739` in 26.1.2; `:2805` in 26.2). `LocalPlayer.aiStep` calls `super.aiStep` at line 914 in both, and `Player.aiStep` calls its superclass at line 453 in both. The superclass path reaches `LivingEntity.aiStep`.
2. `LivingEntity.aiStep` performs ordinary travel, then applies block effects when the level is server-side or the instance is locally authoritative (`26.1.2 LivingEntity.java:3073–3077`; `26.2 :3140–3144`). On the client, `Entity.isLocalInstanceAuthoritative()` delegates to `isLocalClientAuthoritative()` (26.1.2 `Entity.java:3516–3518`; 26.2 `Entity.java:3589–3591`). `Player.isLocalClientAuthoritative()` delegates to `isLocalPlayer()` (26.1.2 `Player.java:1260–1262`; 26.2 `Player.java:1281–1283`), and `LocalPlayer.isLocalPlayer()` returns true at both endpoints (26.1.2 and 26.2 `LocalPlayer.java:378–380`). This closes the local-player authority bridge for the client-side block-effects path. The inherited `Entity.isAffectedByBlocks()` permits this path when the entity is not removed and not `noPhysics` (`Entity.java:913` / `:977`).
3. `Entity.applyEffectsFromBlocks` calls `checkInsideBlocks`; when a non-air block's entity-inside collision shape intersects the movement path and the block has not already been visited, `Entity.checkInsideBlocks` invokes `state.entityInside(...)` (26.1.2 `Entity.java:885–913, 1195–1269`; 26.2 `:949–977, 1278–1352`). `BlockBehaviour.BlockStateBase.entityInside` dispatches to `this.getBlock().entityInside(...)` (26.1.2 `BlockBehaviour.java:774–777`; 26.2 `:780–783`). A HoneyBlock intersection therefore reaches the override after local-player travel. Exact collision-shape equality is outside this report's scope.
4. `HoneyBlock.entityInside` first evaluates `isSlidingDown`. If true, it checks the slide achievement, calls `doSlideMovement`, then emits optional slide effects; it finally calls `super.entityInside`. The movement write occurs before the superclass callback.

The source-equal slide gate is ordered as follows (`HoneyBlock.java:89–107` at both endpoints): reject if `entity.onGround()`; reject if `entity.getY() > pos.getY() + 0.9375 - 1.0E-7`; reject if `getOldDeltaY(entity.getDeltaMovement().y) >= -0.08`; then compute absolute X/Z center offsets and require `dx + 1.0E-7 > 0.4375 + bbWidth/2` **or** `dz + 1.0E-7 > 0.4375 + bbWidth/2`. Equality at those strict side-overlap comparisons does not pass.

The two reconstruction helpers are identical (`HoneyBlock.java:81–86`):

- `getOldDeltaY(deltaY) = deltaY / 0.98F + 0.08`.
- `getNewDeltaY(deltaY) = (deltaY - 0.08) * 0.98F`.

When sliding, `doSlideMovement` reads the current vector. If `getOldDeltaY(currentY) < -0.13`, it multiplies X and Z by `-0.05 / getOldDeltaY(currentY)`; otherwise it preserves X and Z. Both branches write Y as `getNewDeltaY(-0.05)` and then reset fall distance (`HoneyBlock.java:114–123`). The float suffix on `0.98F`, double literals, comparisons, expression order and `Vec3` construction are the same at both endpoints. No altered player velocity formula or gate was found in this slice.

`maybeDoSlideEffects` is also source-equal but only plays sound or sends server-side entity events; it does not write position or velocity. `fallOn` is outside the movement comparison: its same source body handles sound/event plus `causeFallDamage`, and damage resolution is excluded.

## Hash-bound Java inputs

Each local SHA-256 below was checked against its endpoint's `unobfuscated.sources.sha256` entry.

| Relative Java path | 26.1.2 SHA-256 | 26.2 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/HoneyBlock.java` | `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec` | `ad1626f8b2c7ac39bdae01de7f8d5dbec0fb4193057dea33e39e0c7cc3c1b6aa` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java` | `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13` | `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e` |

## Limits

This result covers only the HoneyBlock direct slide response and the local-player caller path that reaches its block callback. It does not establish ordinary map placement or a trajectory that satisfies the slide conditions, runtime parity, data/resource membership, other block contacts, falling damage, or the complete 26.1.2→26.2 movement pair.
