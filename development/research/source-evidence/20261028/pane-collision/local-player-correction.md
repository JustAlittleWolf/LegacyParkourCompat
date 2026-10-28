# Correction: LocalPlayer pane witness helper binding

## Disposition

**REVISE** the local-player reachability conclusion in `supplement.md` (SHA-256 `DCA55B263A18FB396B2F4E4349FA2157A81EC60E122BF54C4ABB33C4B62B73AC`). The accepted pane finding and its collector analysis remain unchanged. The prior supplement bound the four `LocalPlayer.aiStep` calls to the wrong overload: they invoke `LocalPlayer.moveTowardsClosestSpace(double x, double z)`, not `Entity.moveTowardsClosestSpace(double x, double y, double z)`. Under the isolated pane witness, the correct helper does not rewrite X velocity.

## Exact overload and suffocation gate

Canonical read-only evidence is still `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\26.2\unobfuscated`, exact ID `26.2 / unobfuscated`. The 26.2 export SHA identities remain those recorded in `supplement.md`; this correction verifies the following direct source file hashes against `unobfuscated.sources.sha256`:

| Source member/range | SHA-256 |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java`: four two-argument calls in `aiStep` 795-799; private overload and predicate 451-484 | `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6` |
| `net/minecraft/world/level/CollisionGetter.java`: `getBlockCollisionsFromContext` 104-105; `collidesWithSuffocatingBlock` 126-136 | `8E7D1A54D27E0F187667439539C59B8892F966F1B9C8107EB162C6D43F830F6C` |
| `net/minecraft/world/level/BlockCollisions.java`: state filter and shape collection 78-103 | `EB8A8F6F07B1D6384F17987818F056D41ADFD6B2AD120D26774BFA550F4FC51E` |
| `net/minecraft/world/level/block/state/BlockBehaviour.java`: `isSuffocating` 808-810; default predicate 1018; full-collision-shape query 900-902 | `9C7A103492D0714C90397DA88EB696912FF6A9CA1C005D984C4746D52637FD1E` |
| `net/minecraft/world/level/block/Block.java`: centered column shape 177-184; `isShapeFullBlock` 351-353 | `CEC6A05E644E4A7FEB8253CC4CA772A98F0E116FB098A1B7EE7302984AC7ECAB` |
| `net/minecraft/world/level/block/Blocks.java`: `IRON_BARS` and `GLASS_PANE` registration 2336-2364 | `F3F2FAEED23E9697407069A1D523107491590B8710175523EA05294D5BD00435` |

`LocalPlayer.aiStep` calls the private two-coordinate overload at the four corners. That overload first checks `suffocatesAt(pos)`; it enters the direction loop and mutates movement only if this returns true. `suffocatesAt` constructs a one-block-column test area and delegates to `CollisionGetter.collidesWithSuffocatingBlock`. That method creates `BlockCollisions` with `onlySuffocatingBlocks=true`; `BlockCollisions.computeNext` therefore requires `blockState.isSuffocating(...)` before it asks for or collects a collision shape.

The `IRON_BARS` and `GLASS_PANE` registrations use `IronBarsBlock` with `noOcclusion()`; that property only sets `canOcclude=false` and does not replace `isSuffocating`. The default `BlockBehaviour.Properties.isSuffocating` predicate is `state.blocksMotion() && state.isCollisionShapeFullBlock(level,pos)`. The unconnected pane state in the accepted witness uses only the centered post shape `[7/16,9/16] × [0,1] × [7/16,9/16]`. Its collision shape is not a full-block shape, so `state.isCollisionShapeFullBlock` is false and the state is not suffocating under the default predicate. Whether `blocksMotion()` is true does not change that false conjunction. Air corner cells are likewise not suffocating.

With the accepted player center and `getBbWidth()==0.6F`, the four `x/z` sample cells are `(-1,64,0)`, `(-1,64,-1)`, `(0,64,-1)`, and `(0,64,0)`. In the isolated pane neighborhood, the first three are air and the last is the pane. Thus `suffocatesAt(pos)` is false at every call; the private helper returns without evaluating candidate directions or changing the packet-supplied movement. The prior supplement's WEST choice, randomized negative X assignment, and consequent failure to reach the positive-X movement witness are **not applicable**: those came from the unrelated three-coordinate `Entity` helper, which these call sites do not invoke.

## Bounded consequence

The normal clientbound `ClientboundPlayerPositionPacket` source route previously checked can seed the exact double center and delta movement with no relative flags. Given the accepted ordinary movement preconditions (loaded LocalPlayer tick, local player, non-passenger, non-spectator/no-physics false, airborne, dry and not swimming, not fall-flying, abilities not flying, zero movement input, and isolated pane/clear surroundings), the corrected call binding means the corner helper leaves `(0.003125,0,0)` intact. The player-specific cutoff at `LivingEntity.aiStep` also retains it because `0.003125² = 9.765625E-6`, which is not below `9.0E-6`. The ordinary air travel path then passes current delta movement to `move(MoverType.SELF, ...)` before applying gravity and air drag, so the exact positive-X movement can reach the conditional collision collector. The packet sets position and delta movement; this correction does not prove an input-only construction or claim runtime execution.

The false implication withdrawn here is solely that 26.2's ordinary local-player helper replaces the witness X velocity. The exact source aliases and `BlockCollisions` AND/merge result recorded in the accepted finding and prior supplement remain valid. No build, test, gameplay/runtime launch, server launch, or source generation was performed.
