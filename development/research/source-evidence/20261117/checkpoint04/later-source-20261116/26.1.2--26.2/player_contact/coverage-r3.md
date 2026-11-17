# 26.1.2 → 26.2 player-contact residual slice (revision 3)

Status: **NO_DIFF only for the explicitly bounded direct contact/provider/suffocation routes below; collision-time movement restitution is a confirmed source difference and remains an open compatibility candidate.** Pair status remains partial/incomplete/not frozen. This revision does not claim full-pair coverage or determine which historical profiles require restitution behavior.

## Immutable inputs and exact source identity

- Prior report preserved unchanged: `build/later-source-20261116/26.1.2--26.2/player_contact/coverage.md`, SHA-256 `3FD892BFDE6DA7B8597C025DDD04996608044E2994CEF896F5E073ACFA48B579`.
- Independent audit: `build/later-source-20261116/26.1.2--26.2/player_routes_audit/audit.md`, SHA-256 `E7F06EF6D23B5F9F68A4E651DD03C73FE801034C8255F9052FE5BDF00349597B`.
- Revision 2 report corrected here: `build/later-source-20261116/26.1.2--26.2/player_contact/coverage-r2.md`, SHA-256 `FFE7851867B19A2AAB96024CC6B0A017F4A22005D971072CBA62C7AD24B7B631`.
- Independent audit requesting this bounded correction: `build/later-source-20261116/26.1.2--26.2/player_contact_audit_r2/audit.md`, SHA-256 `090F040B990EB09B0E658B0A1DBADCC988A3E6AA56C27AD09009FB2753F2BB77`.
- 26.1.2 source is exact `26.1.2`, unobfuscated; provenance SHA-256 `8D727F880CC91F4A297E148C5B75976303D051457334BC689268FBDF631A012A`.
- 26.2 source is exact `26.2`, unobfuscated; provenance SHA-256 `A328F5333A1B27AADDE4422B23159A323AE219CCD26F11207C1A5461366F27D1`.
- Exact source file SHA-256: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java` `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`; `26.2/.../Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`; `26.2/.../LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`. The previous report/audit recorded matching exact source-ledger integrity. No source generation or source-tree writes were performed.

## Confirmed difference: collision-time player movement restitution

- Older source: `26.1.2`, unobfuscated, `net/minecraft/world/entity/Entity.java`, `Entity.move(MoverType, Vec3)`, lines 704-800. After collision clipping, horizontal collision zeroes the corresponding delta-movement components (lines 776-779). For simulated movement, a vertical clip calls `Block.updateEntityMovementAfterFallOn` (lines 781-786). No restitution dispatch or `restituteMovementAfterCollisions` method exists in this source.
- Newer source: `26.2`, unobfuscated, `net/minecraft/world/entity/Entity.java`, `Entity.move(MoverType, Vec3)`, lines 711-800. When `canSimulateMovement()` and either a moved vertical collision or horizontal collision hold, it calls `restituteMovementAfterCollisions(effectState, xCollision, zCollision, movement)` (lines 784-786). The new private method (lines 802-843) reverses/scales collided X/Z velocity by entity restitution; for vertical collision it may select the greater entity/block restitution, subject to gravity, `isSuppressingBounce()` and `BlockTags.SUPPRESSES_BOUNCE` gates, then computes Y velocity using movement/gravity compensation and air drag. A bounce also emits `GameEvent.BOUNCE` and marks position sync. `getBlockBounciness` (lines 845-852) reads `Block.getBounceRestitution()` and applies a 0.8 factor only to non-living entities. Base `Entity.getEntityBounciness()` returns 0 (lines 854-856).
- The 26.2 `LivingEntity.getEntityBounciness()` override (unobfuscated `net/minecraft/world/entity/LivingEntity.java`, lines 2201-2204) returns `getAttributeValue(Attributes.BOUNCINESS)`; 26.1.2 has no such override. The attribute is registered with a 0.0 default and 0.0–1.0 range in 26.2 `net/minecraft/world/entity/ai/attributes/Attributes.java:31`; the prior report notes the attribute is included in LivingEntity's attribute set at `LivingEntity.java:355`.

### Reachability and preconditions

`LocalPlayer` inherits through `AbstractClientPlayer → Player → LivingEntity → Entity`. In 26.2, `LivingEntity.travel` calls `this.move(MoverType.SELF, this.getDeltaMovement())` on its movement branches (for example `LivingEntity.java:2533-2535`), and the inherited virtual call reaches `Entity.move`. The restitution dispatch condition is `canSimulateMovement()` (`Entity.java:784-786`); the applicable override is `Player.canSimulateMovement()`, which returns `!level().isClientSide() || isLocalPlayer()` (`Player.java:1289-1292`), and `LocalPlayer.isLocalPlayer()` returns true (`LocalPlayer.java:377-380`). Separately, `Entity.move` uses `isLocalInstanceAuthoritative()` to gate vertical-collision/on-ground state updates when there is no vertical movement (`Entity.java:763-767`) and `checkFallDamage` (`Entity.java:777-779`). On the client, `isLocalInstanceAuthoritative()` calls `isLocalClientAuthoritative()` (`Entity.java:3589-3595`), which `Player` overrides to return `isLocalPlayer()` (`Player.java:1280-1283`), true for `LocalPlayer` as above. `Entity.move` takes its `noPhysics` early path before collision handling (`Entity.java:711-718`), so the restitution case requires normal collision resolution.

A concrete vertical case is a simulated LocalPlayer descending onto a block whose `getBounceRestitution()` is positive, with bounce not suppressed by the entity or block tag and with the method's effective-gravity/velocity condition satisfied. The resulting Y velocity can be reflected/scaled. A concrete horizontal case requires nonzero LivingEntity `BOUNCINESS`, an unsuppressed bounce and a corresponding X/Z collision; that attribute is zero by default, so a nonzero value is a necessary state precondition. Collision eligibility also requires normal collision resolution (not `noPhysics`). These are source reachability/precondition statements, not a measured trajectory or runtime-parity claim.

### Movement meaning and boundary

For eligible local-player collision simulation, 26.2 can write post-collision velocity differently from 26.1.2's horizontal zeroing/fall-on-block path. This affects later player movement ticks and is in scope for movement comparison. The exact profile applicability and intermediate introduction boundary are unknown; only the named endpoints were compared. Block-state changes are unnecessary to the source argument: the newer path reads the current block's restitution and suppression tag. This is a distinct collision-resolution candidate and is not subsumed by the no-difference results below.

## Narrow no-difference disposition retained

The earlier report's comparisons remain coverage only for their stated slices: entity push/spin contact, Shulker direct move, local escape from suffocation, pose/suffocation checks and providers, and collision target eligibility. The report’s direct contact/provider findings remain unchanged, but its broad collision/contact NO_DIFF status is superseded by this narrowed disposition. In particular, the earlier sentence leaving general `Entity.move` collision resolution untouched is now an explicit open gate, not evidence of no difference.

Still unexamined here: packet position/velocity updates, block-contact/piston/fluid impulses, item/effect/equipment responses, full input/travel/shape inventories, other movement areas and intermediate 26.x releases. No full-pair audit, implementation, test, gameplay launch or runtime parity check is claimed.



