# Independent entity-response closure: 26.1.2 → 26.2

## Exact source identity

Read-only source roots: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/{26.1.2,26.2}/unobfuscated`. Ready records identify the exact releases and `unobfuscated` mapping namespace; both use Vineflower 1.12.0, Java 25.0.3+9-LTS, Gradle 9.7.1, Mapping IO 0.9.1, ASM 9.10.1, Gson 2.14.0 and TinyRemapper 0.14.1. Ready-record source manifest hashes: 26.1.2 `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; 26.2 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. Recomputed source file hashes for the examined members match those already recorded in the prior census; method bodies below were independently re-read in the paired source trees.

## Author census before comparison with prior census

I searched both full `net/minecraft/world/entity` trees for entity-directed `push`, `knockback`, `setDeltaMovement`, `addDeltaMovement`, `setPos`, and `teleportTo` calls, and scanned event handler overrides for delegated/direct movement writes. Relevant routes found in the bounded external-response receiver inventory:

* ordinary entity contact: `LivingEntity.aiStep` → `pushEntities` → `Level.getPushableEntities` → `EntitySelector.pushableBy` → `LivingEntity.doPush` → dynamic `Entity.push(Entity)`; other contact pushers reach the same inherited primitive;
* Shulker opening: `Shulker.tick` → `updatePeekAmount` → `onPeekAmountChange` → overlapping entity `move(MoverType.SHULKER, ...)`;
* Shulker-box opening: `ShulkerBoxBlockEntity.tick` → `moveCollidedEntities` → overlapping entity `move(MoverType.SHULKER_BOX, ...)`;
* moving piston: block-entity tick → `moveCollidedEntities` / `moveStuckEntities` → `moveEntityByPiston`, plus slime piston direct axis-velocity assignment;
* direct client entity-event responses: `FishingHook` event 31 → `pullEntity`; `Ravager` event 69 → `applyRoarKnockbackClient` → `strongKnockback`;
* attached firework: `FireworkRocketEntity.tick` → attached fall-flying `LivingEntity.setDeltaMovement` boost.

`EntitySelector.pushableBy` explicitly admits a local-player recipient on the client. `FishingHook` tests `level.isClientSide()`, the target being a `Player`, and `isLocalPlayer()` for event 31. `Ravager.handleEntityEvent(69)` invokes its client response and its target query can return the local player. The firework attachment resolves any attached `LivingEntity` without a client-side exclusion, and its fall-flying branch directly mutates that entity's velocity. Shulker, box and piston selection likewise has no client-only recipient exclusion that would rule out LocalPlayer. These are eligible player responses irrespective of whether the entity producer's own movement simulation is in scope.

## Paired terminals

| Slice | Compared source | Paired result for the local-player terminal |
|---|---|---|
| Shared contact push | `Entity.push(Entity)` and numeric overloads; `LivingEntity.push(Entity)`, `pushEntities`, `doPush`; `Level.getPushableEntities`; `EntitySelector.pushableBy`; `Player`/`LocalPlayer` have no push override | No movement difference. `Entity.push(Entity)` retains same-vehicle and no-physics gates, normalized X/Z response and reciprocal pushability checks. Numeric push retains finite-value gate, addition, and sync flag. The client local-player selector case is unchanged. |
| Shulker body push | `Shulker.updatePeekAmount`, `onPeekAmountChange`, `getProgressDeltaAabb`, and `Entity.refreshDimensions` dependency | The peek progression, physical-peek values, candidate AABB formula, target filter and `MoverType.SHULKER` vector are unchanged. 26.2 adds `refreshDimensions()` after `reapplyPosition()` and a Shulker default-dimensions override. The queried push AABB is still calculated from scale/progress/position; `refreshDimensions` changes the Shulker's own dimensions, not the LocalPlayer's collision box or the shove vector. No local-player terminal delta identified. |
| Shulker box | `ShulkerBoxBlockEntity.moveCollidedEntities` | Same recipient scan, piston-reaction filter, movement type, and vector. Only `pos.getBottomCenter()` becomes `Vec3.atBottomCenterOf(pos)`; old helper delegates to the same bottom-center coordinates. No terminal delta. |
| Piston and slime response | `PistonMovingBlockEntity.moveCollidedEntities`, `moveStuckEntities`, `matchesStickyCritera`, `moveEntityByPiston` | These bodies and recipient/collision gates are unchanged. LocalPlayer is not a `ServerPlayer`, so the existing slime piston axis-velocity write remains applicable on client in both releases. The resulting move is also unchanged. No terminal delta. |
| Fishing hook event | `FishingHook.handleEntityEvent`, `pullEntity` | Event 31 local-client gate, owner-null gate, `0.1 * (owner position − hook position)` calculation, and target velocity addition are unchanged. No terminal delta. |
| Ravager roar event | `Ravager.handleEntityEvent`, `applyRoarKnockbackClient`, `strongKnockback` | Event 69 dispatch, client query/radius/selector, denominator floor `0.001`, and push vector are unchanged. No terminal delta. |
| Attached firework boost | `FireworkRocketEntity.tick` attached-target branch | Attached entity resolution was rewritten from an `instanceof` plus cast to a pattern binding. The fall-flying gate, look/movement reads, constants and three-component boost expression/write order are unchanged. No terminal delta. |

The compared source file hash pairs (older/newer) are:

* `Entity.java`: `8B83B1F036AABBD13D990897C540C993F7120F02955486CFCF229517D4097CCF` / `7AFB9C1294893FFE73E3B1ACFFCAD41C648F15DE8378BFF3DFFAFF869BB811D5`
* `LivingEntity.java`: `C3B64DE8DBABA8AD8A7CCD4F33255346D8206E66F12CA91260971BF5E5AC93BD` / `7FFD9C70966EDC50C9CB4D9A8FE17A518E2678FF44C8026E763D0B94AC0AE51A`
* `EntitySelector.java`: `0735F67B48018B7FC12737E06085CDF194A138EDB75F5F95B83C0A9451BE589B` / same
* `FishingHook.java`: `5C36AD0E62C7BACF0DA595A72251C1ED2941E6E15BD5C6EF04CB5042965E9A6A` / `82B6D45C5FEF7B964AE4800AD9B6FB83EA6648A4F55E296C877BB7CF153CBDD4`
* `Ravager.java`: `7D50321803FDF1B9A1B455B2C665ABF16285E747FDE78EFB0368A2C597F5B7B0` / `4963BA7F12D3946015F71CC22DDF0BC2D6F215BA64FC6DADD804A7E0AD74FA8E`
* `FireworkRocketEntity.java`: `89FF34AD8A9710F1E2049212CFF451CF8651F59A480862857CE13164D7BF6833` / `59450230E450DF84D9FB8311E4424CAC702A58609C248369F58AD1BA1ABBDA6C`
* `Shulker.java`: `618063989F44BF7E67EFEC3FFE9642A86A0913C360FE704B188014F72C6E94AA` / `B1ACA23651E14B07ED30F8AD3D01A7D0071301E3BCA28E128FCBF0689F2FB7E6`
* `ShulkerBoxBlockEntity.java`: `2673B595BFF716E9BEAFC49B8AE5CF0C2B2D3D5F1CE4D6EB7477EE91E4B58305` / `DAFF975E4FBD745112C93637AA6DC2F3F5A546B4F85BB8E1ADD121A44B3B751D`
* `PistonMovingBlockEntity.java`: `67BCEB946C172C5C022663FDA32D123A6BBB85CA4171705CA9DC2C54105AB6E6` / `706BAB1A13BA99BBD334AAD853B99F72DF58FCE5B6B67D30D41F902B63DD8E06`
* `Level.java`: `77CC835FC1A79CD970BC3F4897FE5F398958FCBC874400C297F465F0C7819F01` / `1AE4F565AD909F4E90786089B5B4F125E8A7D18E2EF7D8C376172A5787FC5639`

## Reconciliation and residuals

The same receiver set appears in the prior `2612-262-finish61/external-player-response-followup/census.md` addendum. I independently confirmed its three added routes (fishing event 31, Ravager event 69, attached firework boost) and its earlier shared-push/Shulker/box/piston routes; I found no omitted eligible writer within this assigned entity-response slice and no paired terminal difference. This closes only these enumerated entity-response terminals and their named selection/writer dependencies.

Residual: this does not independently close the packet/effect/attribute callback matrix assigned elsewhere, all block/fluid callback producers, or every other external player-response producer beyond this entity-focused census. The prior census's producer-inventory open row remains open at pair scope. No pair freeze or general movement coverage claim follows from this bounded result.
