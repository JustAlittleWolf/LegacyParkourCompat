# 1.21.1 → 1.21.3 tick-gates source comparison

**Terminal: COMPLETED.** Bounded caller-to-consumer comparison is complete for the assigned slices. Exact decompile publication provenance remains an open acceptance gate; see below. No product files were changed; no tests, launches, source generation, or backlog reads/writes were performed.

## Inputs and evidence identity

- Host: Windows PowerShell.
- Pair / mappings: local `decompiled_minecraft/1.21.1/mojmap/` and `decompiled_minecraft/1.21.3/mojmap/` trees.
- Exact compared sources: `net/minecraft/client/player/LocalPlayer.java`, `net/minecraft/world/entity/player/Player.java`, `net/minecraft/world/entity/LivingEntity.java`, `net/minecraft/world/entity/Entity.java`; additionally `client/player/Input.java`, `client/player/ClientInput.java`, `client/player/KeyboardInput.java`, `world/entity/player/Input.java`, `world/item/Items.java`, and `world/level/block/WebBlock.java` for reachable caller/consumer closure.
- SHA-256 fingerprints of the four primary tick files:
  - 1.21.1 LocalPlayer: `C555E68AC3C63AB9B4F9A9E31933E263B96350A2BC599D11A0DE5928BC24B583`
  - 1.21.3 LocalPlayer: `FBD40F1F47ADFA66DDA9B15188E5DCE82AF3E8E8D7C3DD0543E602A354AD3FE0`
  - 1.21.1 LivingEntity: `324A3EEE8496CAAB57CFAF5101EF576F1AE3C60C40E3857E96F35F3AF9A3A0D8`
  - 1.21.3 LivingEntity: `087390495F4FDFD14B9E12230E7AEA4FDC50913BB4D1D91119E72892881CFC52`
- Both mapping folders contain Java source trees, but neither contains `.decompile-provenance.json`; adjacent version/mapping folders provide no manifest in the inspected roots. These path names and file hashes fingerprint the material compared but do not prove exact artifact/mapping-build provenance. That acceptance dependency remains open.


Supporting source fingerprints (SHA-256):

- 1.21.1 `Player.java`: `ED32B88C3C7C8418B83DB41823520F2B6B0B49A98610306EF26E9681DC925C71`; 1.21.3 `Player.java`: `A803203E92AA4729D5F5C9B16085B6A43CE51D9907D309EB96736E9C7C1340DE`.
- 1.21.1 `Entity.java`: `B81905C7879E2CC5C5063A41D865C4164AD919F156306705BE791D1017B99850`; 1.21.3 `Entity.java`: `A93719C302A0381A972AF75EA360465E2E3551708DD07C34D4D40B7E5173C2B9`.
- 1.21.1 `client/player/Input.java`: `B302FFBC45C5F900EA18A4D4AF2DF6FA0454EA7CB7744A0D249061E5FCB97FBB`; 1.21.3 `client/player/ClientInput.java`: `6BC3C2362657C0D5CE889EB1DDDB203DDCB185A729D97CF577B6DB0FE22ABEE5`.
- 1.21.1 `client/player/KeyboardInput.java`: `A8064906872955A3520398AB5B2A326552D424F41887D1294AA6A038E2623FF0`; 1.21.3 `client/player/KeyboardInput.java`: `2A0994E8A6A2FDF9E76480227216DB4A4252734FCA8CA0AEDD25DDE77BE75BA6`; 1.21.3 `world/entity/player/Input.java`: `1C0C12AF07DA1F90651C3A7C4EB19BAB6486B6391DCBFDD2926F06E3F36C2F06`.
- 1.21.1 `ElytraItem.java`: `AAFC9E310847FCCD7FFFFCA5773D7DF8B55285A940C185E95096BACCBEE2605F`; 1.21.3 `ItemStack.java`: `46E63B700893E7D3A7E455E86BEBDD2311135CF3D822038EF3906D4D890B93A9`.
- 1.21.3 `Items.java`: `BC99499E18F7099AAB5C15F5921D583906E6CC48A7EAAD8E81D09CD26FF7B7AF`; `WebBlock.java` is byte-identical in both releases, SHA-256 `ECE9F8840481748D2D1542990B442CC9A97D670255D693B85F528FA618F48414`.
## Ordered coverage

### 1. LocalPlayer pre-travel capture and LivingEntity tick order

Compared `LocalPlayer.aiStep()` (1.21.1 lines 644–804; 1.21.3 lines 638–795), its input implementation, `Player.aiStep()`, and the `super.aiStep()` chain into `LivingEntity.aiStep()` (1.21.1 line 2586 onward; 1.21.3 line 2680 onward).

No movement delta found in the pre-travel snapshots/order: both releases capture the previous jump/shift state before `input.tick`, capture `hasEnoughImpulseToStartSprinting()` before that tick, apply item-use impulse damping before sprint admission, then apply timed auto-jump, space nudges, sprint decisions, ability-flight decisions, and finally call the inherited player/living tick. The input representation changes from mutable boolean fields (`Input.jumping`, `shiftKeyDown`) to an immutable `Input` key record (`keyPresses.jump()`, `.shift()`); `ClientInput.makeJump()` recreates that record with jump set while preserving its other key bits. Matched default inputs therefore retain the same capture and timer order.

The player-parent call order still decrements `jumpTriggerTime` before `LivingEntity.aiStep()`; the living tick still decrements `noJumpDelay`, performs the jump admission before the travel section, then performs post-travel work. The controlled-local-instance `syncPacketPositionCodec`/lerp reset at the top of `LivingEntity.aiStep` was removed in 1.21.3. This concerns the excluded external packet/rider correction path and is not treated as a historical local movement finding here.

### 2. Pending and ordinary jump admission / timers

Compared `LocalPlayer.aiStep()` jump-edge snapshots and ability-toggle checks, `Player.aiStep()`'s `jumpTriggerTime` decrement, `LivingEntity.aiStep()`'s ordinary jump gate, and the `jumpFromGround()` consumer.

The ordinary gate and timer are otherwise the same: `noJumpDelay` decrements at entry; a held jump must also pass `isAffectedByFluids`; on-ground or low-water admission requires `noJumpDelay == 0`; the admitted ordinary jump calls `jumpFromGround()` and sets the delay to 10; the non-jumping/unaffected branch clears the delay. Fluid height/threshold branches were inspected only for admission/order, not re-audited numerically.

One reachable consumer delta is drafted separately: `LivingEntity.jumpFromGround()` changes vertical-velocity assignment from `setDeltaMovement(oldX, jumpPower, oldZ)` (1.21.1 line 2060) to `setDeltaMovement(oldX, Math.max(jumpPower, oldY), oldZ)` (1.21.3 line 2142). When a grounded jump is admitted while `oldY` exceeds jump power, 1.21.3 preserves the larger upward component where 1.21.1 replaces it.

### 3. Sprint continuation/start gates and ability flight

Compared the sprint gates in `LocalPlayer.aiStep()` and its `canStartSprinting`, impulse, and food helpers (both releases lines 1023–1045). No predicate/order delta found: auto-start still requires ground or underwater, no shift and no initial sprint impulse, plus `canStartSprinting`; held-key start still has the same water gate; continuation still clears sprint for missing forward input/food, non-minor horizontal collision, or surface-water state, with the same swimming-specific branch. `isUnderWater()` remains the cached `wasUnderwater` predicate.

The apparent removal of `!abilities.flying` from the LocalPlayer fall-flying call site is compensated by the new player-owned `canGlide()` override: 1.21.3 `Player.canGlide()` returns false while abilities flight is active (Player.java lines 1471–1472), then delegates to the living gate for airborne, non-passenger, non-levitating state. The mayfly toggle's `$$11` suppression and the post-super “on ground ends flying” check remain in the same order. The start request remains suppressed on the jump-edge tick and while climbing. The generic 1.21.3 glider scan is a structural expansion, but the exact `Items.java` initializer assigns `DataComponents.GLIDER` only to Elytra in the vanilla source tree (line 963); for a vanilla chest Elytra, `canGlideUsing`'s `!nextDamageWillBreak()` is equivalent to 1.21.1 `ElytraItem.isFlyEnabled()`'s `damage < maxDamage - 1`. No vanilla fall-flying admission delta found. Modded/data-injected glider gear is outside the compared vanilla evidence.

### 4. Independent movement permission and post-travel player state

`Player.isAffectedByFluids()` remains gated by `!abilities.flying` in 1.21.3 (Player.java line 1062); this is the predicate used by the ordinary jump gate. 1.21.3 adds a Player.aiStep pre-super reset of `fallDistance` when `abilities.flying && !isPassenger()` (Player.java lines 548–555). The change affects the later fall-distance counter used for fall consequences, but does not change position/velocity or the assigned movement permission; damage/fall-consequence production is out of scope.

The LocalPlayer post-super on-ground flight shutdown is unchanged. The living tick's fall-flying maintenance is now called under `isFallFlying()`; its client-side path has no flag-writing/elytra-durability side effect, and the old client travel branch already calls `checkSlowFallDistance()` before fall-glide math. Exact fly numerical procedures were excluded.

A distinct, reachable post-travel player movement-response delta is drafted separately. In 1.21.1, `Entity.move()` invokes ground `stepOn` and `tryCheckInsideBlocks()` within each movement operation (Entity.java lines 663 onward and 760); 1.21.3 removes those per-move callbacks; `LivingEntity.aiStep()` invokes `applyEffectsFromBlocks()` after `travel()` for server entities and the locally controlled client (LivingEntity.java lines 2772–2778; Entity.java lines 736–767). For the local player, the no-argument overload checks the segment from `oldPosition()` to the final position after travel; movement segments explicitly recorded from server packet corrections are outside this assignment. The callback consumer `WebBlock.entityInside()` calls `makeStuckInBlock()`, which sets the multiplier consumed at the start of the next `Entity.move()` (WebBlock.java line 32; Entity.java lines 633–637 and 2525–2528). Thus a player entering a web during movement no longer gets that block response in a later move nested in the same travel tick; the post-travel block traversal leaves the multiplier for the next move/tick. This is a call-order change, independent of the web's unchanged multiplier.

## Exact closed and open dependencies

**Closed for this bounded queue:** input snapshot/update consumer; jump timers/admission/ground-jump consumer; sprint start/continuation predicates; mayfly/Elytra admission through LocalPlayer → Player → LivingEntity; local `isAffectedByFluids`; post-travel flight shutdown; player `aiStep` inheritance order. Two independent reachable source claims are in `findings/`.

**Open:** exact decompile artifact and mapping-build provenance for both source trees is absent locally. Also, the newly batched block-effect callback path may affect additional vanilla block consumers beyond the checked WebBlock movement response; this report establishes the player call-order delta and one concrete velocity-path consumer, but does not claim exhaustive block-callback inventory. Movement callback consumer enumeration remains a dependency for full-pair closure.

## Terminal

**COMPLETED** for the assigned bounded source comparison; source-acceptance gates are exactly the open dependencies listed above. No tests/build/gameplay verification was run or authorized.