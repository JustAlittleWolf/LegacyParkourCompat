# Powder snow movement-contact response: 26.1.2 → 26.2

## Scope and bounded conclusion

This source-only slice compares the PowderSnowBlock inside-contact response that stores a movement slowdown multiplier, the inherited Entity movement consumer, and the player/local-player gates connecting them. Within those operations, no source movement-semantic difference was found between 26.1.2 and 26.2. This is not pair-wide coverage, an independent acceptance, a runtime result, or a claim that every Powder Snow state is ordinarily reachable.

## Exact source identity

Both endpoints are exact ready `unobfuscated` publications. Ready markers identify `versionId=26.1.2` / `26.2`, `mapping=unobfuscated`, and their `unobfuscated.sources.sha256` and root `artifacts.sha256` digests below. Provenance files name the matching exact version and `namespace=unobfuscated`.

| Release | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 | Artifact manifest SHA-256 |
| --- | --- | --- | --- | --- |
| 26.1.2 | `F24AF0F4D38543C2BD9C19B50F347D4E89D6027050E4B21AE070734843946BB7` | `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A` | `54AE320660CA911A6D20514C965C33EB414FBEDD1AD6727BBAA95FE906AA23C0` | `89F648229DE83B0109460695C9F1F3BED7EF36EFB84EDE9224E80CCE87118F92` |
| 26.2 | `F9406ADB6BF7CB4C1AB0792A798AB2CB90E082AD4C2EEE032C9F674D0EA8070` | `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1` | `A7AD74FC712567EB87136AFC1EAFCFD602B4088E9869F3596331FD34FAEFE894` | `BA9DC53A41BCA9744EC1C8AB2CE9363764A22333C39EF64D5105F4E3BF5C98BA` |

The source roots are `decompiled_minecraft/26.1.2/unobfuscated/` and `decompiled_minecraft/26.2/unobfuscated/`. The ready markers' source-manifest and artifact-manifest digests match the root sidecars. Each Java hash below was rechecked locally against its declared source-manifest row.

| Relative Java path | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/world/level/block/PowderSnowBlock.java` | `5733056253c3f9c7d843e8e7c29deaee67d235d830c63c0641f3a7b70604970f` | `2b693be4a4d3cf2b1895096431c16d7ba3ae51b9cf4320773d384bdd146ba417` |
| `net/minecraft/world/entity/Entity.java` | `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf` | `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5` |
| `net/minecraft/world/entity/LivingEntity.java` | `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd` | `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a` |
| `net/minecraft/world/entity/player/Player.java` | `44cf28e0c64e78d39fd13368e9991381dbebab67029070cb9ddc43f09d45d14d` | `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531` |
| `net/minecraft/client/player/LocalPlayer.java` | `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe` | `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6` |

## Local-player dispatch evidence

The local player reaches the inherited LivingEntity AI path when its client-loaded tick gate passes. `LocalPlayer.tick` calls `super.tick()` under `connection.hasClientLoaded()` (both `LocalPlayer.java:227–230`). The superclass chain continues through `AbstractClientPlayer.tick` (`:44–46`) and `Player.tick` (26.1.2 `Player.java:231–259`; 26.2 `:232–260`), each calling `super.tick()`; `Avatar` extends `LivingEntity` and declares no tick override (both `Avatar.java:13` and complete hash-bound file). `LivingEntity.tick` then calls virtual `this.aiStep()` when not removed (26.1.2 `LivingEntity.java:2738–2740`; 26.2 `:2804–2806`), dispatching to `LocalPlayer.aiStep` for this instance. `LocalPlayer.aiStep` reaches `super.aiStep()` at line 914 in both; `AbstractClientPlayer.aiStep` and `Player.aiStep` also call `super.aiStep()` (both `AbstractClientPlayer.java:76–78`; `Player.java:442–453`), reaching `LivingEntity.aiStep`, where the after-travel block-effect gate listed below occurs. These are source call-path facts only; the path does not establish that the Powder Snow state precondition is ordinarily reached.

The unchanged `Player.java` hashes already listed above bind the referenced Player methods. The following additional exact Java files were hash-checked against each endpoint manifest:

| Relative Java path | 26.1.2 SHA-256 | 26.2 SHA-256 |
| --- | --- | --- |
| `net/minecraft/client/player/AbstractClientPlayer.java` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` | `7825a8d4e8e24928cba18f68109c91479b78fb14fcd8b81f0e045b8d02a559cc` |
| `net/minecraft/world/entity/Avatar.java` | `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78` | `01a8092bc2637e7d42a261fb564bffcabadd28625cc02b120e943559e2d4fe78` |
## Ordered movement path

1. After the `LivingEntity.aiStep` travel branch, both versions call `applyEffectsFromBlocks()` under the same gate: `!level.isClientSide() || isLocalInstanceAuthoritative()` (26.1.2 `LivingEntity.java:3076–3077`; 26.2 `:3143–3144`). For a local player, `Player.isLocalClientAuthoritative()` delegates to `isLocalPlayer()` (old `Player.java:1255–1261`; new `:1276–1282`), and `LocalPlayer.isLocalPlayer()` returns `true` (both `LocalPlayer.java:378`). Therefore the local client reaches this block-effect scan when it runs the ordinary living-player AI path.
2. `Entity.applyEffectsFromBlocks()` builds/uses the recorded movement segments and calls `checkInsideBlocks` only when block effects are enabled; `isAffectedByBlocks()` is `!isRemoved() && !noPhysics` in both (old `Entity.java:839–917`; new `:903–977`). In `checkInsideBlocks`, an intersected block's `entityInside(...)` is invoked after the state entity-inside collision-shape/inside-block test passes (old `Entity.java:1225–1268`; new `:1308–1351`). The relevant caller/dispatch order is the same; this report does not compare every block-effect consumer.
3. `PowderSnowBlock.entityInside` has the same gate and statements in both releases (old/new `PowderSnowBlock.java:62–98`). It calls `entity.makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9F))` when the entity is not a `LivingEntity` or `entity.getInBlockState().is(this)`. Since a Player is a LivingEntity, the Player branch requires that in-block-state predicate. The callback's other shown work emits client snow particles and schedules freeze/extinguish effects; those are not movement claims here.
4. `Player.makeStuckInBlock` is unchanged (old `Player.java:1494–1498`; new `:1515–1519`): if `!abilities.flying`, it calls `super.makeStuckInBlock`; it then calls `tryResetCurrentImpulseContext()` regardless. The LocalPlayer inherits this implementation. `Entity.makeStuckInBlock` is unchanged (old `Entity.java:2876–2879`; new `:2943–2946`): reset fall distance, then store the provided `stuckSpeedMultiplier`.
5. `Entity.move` consumes the stored multiplier in the same order in both (old `Entity.java:704–731`; new `:711–738`). If its squared length exceeds `1.0E-7`, non-piston movement delta is multiplied component-wise by it; then the multiplier is cleared and delta movement is set to zero before edge backoff and collision. Thus the direct Powder Snow vector scales horizontal movement by `0.9` and vertical movement by `1.5` at a later non-piston `move` consuming the stored state. This is a source call-order statement, not a runtime tick measurement.

## Limits

This slice establishes the same conditional source path, multipliers, player flight gate, fall-distance reset and movement consumer in both endpoints. It does not establish datapack overrides, Powder Snow block-state availability or ordinary-world player reachability for the `getInBlockState().is(this)` precondition. It does not claim that a trajectory is affected in every tick, or compare other Powder Snow behaviors (climbing predicate, fall response, freezing/damage) beyond noting adjacent effects. Other movement paths, pair-wide dependency closure and independent coverage audit remain open.