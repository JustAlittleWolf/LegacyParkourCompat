# Source-only boundary re-check: Powder Snow stuck piston response

## Disposition

**ACCEPT the corrected player-response claim and locate the first prepared stable-source boundary at 1.21.9 → 1.21.10.** 1.21.9 applies the stored powder-snow multiplier to piston movement. Starting in 1.21.10, `Entity.move` omits that multiplication for `MoverType.PISTON`; 1.21.11, 26.1 and 26.2 retain the exclusion. The original 1.17.1 → 26.2 endpoint claim and its corrected `lengthSqr() > 1.0E-7` / zero-limited-vector conditions remain supported. No snapshot/development cutover is claimed.

This updates only the bounded stable-boundary disposition. It does not close pair-wide source coverage or establish runtime parity.

## Rehashed inputs

- `powder-snow-postintro-audit/audit.md`: `a9c68903a2d451c34ed81954801451e05bdcbfe03798a5419db9e293b39ecdcd`
- `powder-snow-postintro-audit/powder-snow-stuck-piston-recheck.md`: `392c0dd4204de8f15176a7399af8d50a581bd57ac2e3e0e8cddb91e842f24a3d`
- `powder-stuck-boundary-followup/boundary.md`: `efb72c26f394e4a21cc012ea9e656279b3b55ab0bbacde5c14df5cc27c55990e`
- Prior independent boundary review: `f66798aa39702dc695a92044e7d1546d219e4b00c5fb53f96c3ba4ca54f5c359`

## 1.21.9 source identity and integrity

The alternate identity is Mojmap from Mojang metadata `/1.21.9.json`, with `client.txt` mappings and the client jar. The canonical hidden manifest `decompiled_minecraft/1.21.9/mojmap/.decompile-provenance.json` rehashes to the supplied SHA-256 `37d3049478585a43ffcace14fc8f2ee814b35ceedd8c9bad8b60adaed9b72c4f`. Its embedded inputs identify metadata hash `3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`, mapping-file hash `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`, and client-jar hash `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`. The manifest's exact source hashes match on disk:

- `world/entity/Entity.java`: `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c`
- `world/level/block/PowderSnowBlock.java`: `f7c23fafd5191ef56d11ba49c0dbbd33d0eb7a444b0bfcabe4e2d66a34d463a7`
- `world/level/block/state/BlockBehaviour.java`: verified against the manifest (file SHA in terminal evidence)
- `client/player/LocalPlayer.java`: `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`
- `world/entity/LivingEntity.java`: `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`

The supplied hidden manifest is sufficient exact-source identity despite the directory lacking a ready marker.

## Compared movement operation

All source method hashes below are SHA-256 of the complete extracted method text, with line endings normalized to LF and one terminal LF. Whole-file hashes are separately listed to bind the method to its exact decompiled source file.

- **1.21.9 Mojmap**: `Entity.move(MoverType, Vec3)`, lines 670–763, method hash `70f871364f242a7281a05799857a53883f4fd146a1f62c498648980e9dddc8a0`; source-file hash above. It limits piston movement first and returns for an exactly zero limited vector (680–682). If `stuckSpeedMultiplier.lengthSqr() > 1.0E-7`, it multiplies the requested vector for every mover, then clears the stored multiplier and zeros delta movement (687–690).
- **1.21.10 Mojmap**: `Entity.move`, lines 670–766, method hash `72c2ef82aaa3f804f43f8c0370fc695b4249b34c368524872259ab7feb3b05f9`; `Entity.java` SHA-256 `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18`. The same piston limiter, exact-zero early return and multiplier threshold remain (678–683, 687–694), but multiplication is now guarded by `moverType != MoverType.PISTON`; clearing the multiplier and zeroing delta movement still occur for piston moves. Ready marker says version `1.21.10`, mapping `mojmap`, source-manifest SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`.
- **1.21.11 Mojmap**: same guarded method, `Entity.move` lines 685–781, method hash `72c2ef82aaa3f804f43f8c0370fc695b4249b34c368524872259ab7feb3b05f9`; source SHA-256 `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3`. Ready identity: version `1.21.11`, Mojmap, manifest SHA-256 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.
- **26.1 unobfuscated**: same guard, `Entity.move` lines 704–800, method hash `4bcad42e745ef41130d546ba27b609798be4404eb1419f36e218784aa70290e9`; source SHA-256 `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`. Ready identity: version `26.1`, unobfuscated, manifest SHA-256 `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba`.
- **26.2 unobfuscated**: same guard, `Entity.move` lines 711–800, method hash `c869f8cc8a1e76646085b7949ecacedcb956016fddecf765ba850b7e9cf6c760`; source SHA-256 `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dfaff869bb811d5`. Ready identity: version `26.2`, unobfuscated, manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

## Provider, block-state dispatch and player path

Powder Snow's player-reachable provider is unchanged across the boundary: `PowderSnowBlock.entityInside` checks non-LivingEntity or `entity.getInBlockState().is(this)`, then calls `makeStuckInBlock(state, new Vec3(0.9F, 1.5, 0.9))` (1.21.9 `PowderSnowBlock.java:62–65`; 1.21.10 `:62–65`). `Entity.makeStuckInBlock(BlockState, Vec3)` resets fall distance and stores the multiplier (1.21.9 `Entity.java:2783–2786`; 1.21.10 `:2788–2791`). Its extracted method hash is `5a6c6395def19074fafb949dab001f733dd4324f188e0d6facce228fcadfd3c1` on both sides. Later provider and consumer lines retain these semantics.

`Entity` dispatches intersected block states through the state callback (1.21.9 `Entity.java:1194`; 1.21.10 `:1218`); `BlockBehaviour.BlockStateBase.entityInside` delegates to `getBlock().entityInside(asState(), ...)` (1.21.9 `BlockBehaviour.java:744–745`; 1.21.10 `:744–745`). The 1.21.10 callback adds a precision boolean, but Powder Snow does not use it to alter multiplier storage. `LocalPlayer.tick` calls `super.tick()` in both versions (1.21.9 `LocalPlayer.java:195–200`; 1.21.10 `:195–200`), so the inherited Entity movement and block-contact response are reachable. In the client piston sequence, the stored value reaches the piston call only when it is above the shared squared-length threshold and piston limiting returns a nonzero vector. For that bounded path, 1.21.9 scales piston movement by `(0.9F, 1.5, 0.9)`; from 1.21.10 the piston delta is left unscaled, while the pending multiplier is consumed and delta movement is cleared.

## Scope boundary

The stable source sequence inspected establishes 1.21.9 as before and 1.21.10 as after; the 1.21.11 / 26.1 / 26.2 checks confirm continuation of the changed behavior. This is an exact stable endpoint boundary only. No patch/snapshot introduction, complete pair inventory, or runtime tick parity is inferred.

The client ordering witness was also checked on the before/after pair: `Minecraft.tick` calls `level.tickEntities()` before `level.tickBlockEntities()` (1.21.9 and 1.21.10 `Minecraft.java:1782–1784`). `PistonMovingBlockEntity.tick` reaches `moveCollidedEntities` (both `PistonMovingBlockEntity.java:320–323`); its `moveEntityByPiston` calls `entity.move(MoverType.PISTON, ...)` and then `applyEffectsFromBlocks` (both `:185–190`). This places normal local-player entity ticking and block contact before the piston callback in the same client tick, when swept-volume overlap and the corrected consumer gates are satisfied.

Additional source-file integrity: 1.21.9 `BlockBehaviour.java` hash `195711b3651969f0202296bc4522b1dbc67d190ad21fa73b00e97b5040528620` and `PistonMovingBlockEntity.java` hash `c5dc0cb682442ced62a73bfe27bcbcb77a069d8e3057a80b7c62ae1f6867c845` both match the hidden provenance manifest. The 1.21.10 prepared source manifest lists `BlockBehaviour.java` as `d1bacd23db38cfdc437f3571d297bef8bd2785beceb0ec632e0ad791d5011350` and `PistonMovingBlockEntity.java` as `c5dc0cb682442ced62a73bfe27bcbcb77a069d8e3057a80b7c62ae1f6867c845`, matching both local files. Its BlockState callback wrapper adds `isPrecise` and forwards it to the block callback, while the powder-snow multiplier call remains unchanged.
