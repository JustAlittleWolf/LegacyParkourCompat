# 1.13 to 26.2 swimmer-state destination witness

## Result

**The early Player-level requirement remains in 26.2, but only for flight:** `Player.updateSwimming()` still clears swimming when `abilities.flying` and otherwise calls `super.updateSwimming()`. The additional old-player guard for an already-swimming player's feet/head clearance is gone. In 1.13, a non-flying player already swimming calls the inherited update only when both clearance predicates pass; when either fails, the old swimming bit is retained for that tick.

## Exact source evidence

- 1.13 (release 1.13.0), Ornithe Feather `feather-gen2:1.13+build.2`: `net/minecraft/entity/living/player/PlayerEntity.java:1464-1471` constructs `blockPos = new BlockPos(this)` and `blockPos2 = blockPos.up()`. Flying clears swimming. Otherwise, the inherited call is gated by `!isSwimming() || (doesNotSuffocate(blockPos) && !state(blockPos2).getMaterial().blocksMovement())`. `doesNotSuffocate` at `:1474-1479` is `shouldSuffocate(pos) && !state(pos.up()).isSolid()`, and `shouldSuffocate(pos)` is `!state(pos).isSolid()`. Thus for an already-swimming player, a solid feet state, a solid upper state, or an upper material that blocks movement can skip recomputation. The caller's exact position is one block position derived from player coordinates and its `up()` position; these checks are retained as source gates here, without inferring collision or fluid behavior.
- 26.2, unobfuscated: `net/minecraft/world/entity/player/Player.java:1433-1439` has the same Player-level flying branch, but its non-flying arm unconditionally calls `super.updateSwimming()`. There is no already-swimming/feet/head clearance condition in this override.
- Inherited predicates: 1.13 `Entity.updateSwimming()` at `net/minecraft/entity/Entity.java:952-957` uses `isInWater()` for an already-swimming entity and `isSubmergedInWater()` when not swimming; both arms also require sprinting and not riding. 26.2 `Entity.updateSwimming()` at `net/minecraft/world/entity/Entity.java:1635-1642` uses sprinting + `isInWater()` + not passenger to retain an existing swimming state, and for a non-swimmer uses sprinting + `isUnderWater()` + not passenger + water at `blockPosition`. These are only the inherited source gates; this witness does not cover fluid classification/interaction.
- Both call chains dispatch polymorphically before local movement: in 1.13, `Entity.tick()` calls `baseTick()` (`Entity.java:332-340`), whose water-state helper invokes `updateSwimming()` (`Entity.java:946-950`); `LivingEntity.baseTick()` calls `super.baseTick()` first (`LivingEntity.java:241-244`). In 26.2, `Entity.tick()` calls `baseTick()` (`Entity.java:506-508`) and `Entity.baseTick()` calls `updateSwimming()` (`Entity.java:510-533`). `LocalClientPlayerEntity.tick()` / `LocalPlayer.tick()` then delegate through `super.tick()` (`:179-182` / `:227-233`); living tick reaches `aiStep()` after that base tick (`1.13 LivingEntity.java:1670-1673`; 26.2 `LivingEntity.java:2765-2806`). So the override-selected swimming bit is available to the local player's later tick movement.

## Reachable local state and movement consequence

A concrete source-level path is an ordinary, non-flying, non-riding player already swimming in water, with the head/upper `blockPos.up()` material blocking movement while the swimming pose fits the low-clearance space. On one tick the player is still sprinting, so the existing swimming state is maintained. In local `aiStep`, releasing forward while airborne stops swim-sprinting in 1.13 (`LocalClientPlayerEntity.java:744-750`) and 26.2 (`LocalPlayer.java:820-828`, with `shouldStopSwimSprinting()` at `:927-929`). On the following tick, base-tick dispatch happens before `aiStep` and travel: 1.13's upper-material gate skips inherited recomputation and retains `isSwimming`; 26.2's Player override delegates and inherited `Entity.updateSwimming()` clears it because sprinting is now false. This uses the ordinary sprint-stop path, not an injected state.

The observable destination movement predicate is `Player.travel()` at 26.2 `Player.java:1402-1423`: before `super.travel(input)` performs travel movement, a non-passenger with `isSwimming()` may alter Y delta by `(lookAngleY - movement.y) * multiplier` when look Y is nonpositive, jumping, or the sampled upper fluid state is nonempty. For example, looking slightly downward makes the retained old swimming bit enter this vertical-adjustment branch on the next tick; the destination's cleared bit bypasses it. The 1.13 side has an observable player-motion consumer at `PlayerEntity.moveRelative()` `:1435-1445`, gated by `isSwimming() && !isRiding()` before the superclass movement-relative call. This establishes a state-to-movement witness, not the separate fluid/collision result or a full trajectory.

## Immutable source identity and byte checks

Read-only canonical roots: `decompiled_minecraft/1.13/ornithe-feather/` and `decompiled_minecraft/26.2/unobfuscated/`. Each ready marker's `sourceManifestSha256` matches the local manifest bytes; each relied source file's SHA-256 matches its manifest entry.

| Side | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 |
|---|---|---|---|
| 1.13 / Ornithe Feather | `82a4a8f6a03fbd99a7b8577b1ae5980d95af07c7ded0da284106d16eb1e01a99` | `34652aeef6914b5cbff6619ae60a855aa676b2ae1f607eccdaa623ad09dc5e8d` | `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a` |
| 26.2 / unobfuscated | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` |

Relied source-file SHA-256 values (all matched against the relevant `*.sources.sha256` entry):

- 1.13 `net/minecraft/entity/Entity.java`: `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642`
- 1.13 `net/minecraft/entity/living/player/PlayerEntity.java`: `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd`
- 1.13 `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08`
- 1.13 `net/minecraft/entity/living/LivingEntity.java`: `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864`
- 26.2 `net/minecraft/world/entity/Entity.java`: `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`
- 26.2 `net/minecraft/world/entity/player/Player.java`: `8decc71b9c780664578ddb14591db2a2f207c72c05b676edded6f8e964576531`
- 26.2 `net/minecraft/client/player/LocalPlayer.java`: `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`
- 26.2 `net/minecraft/world/entity/LivingEntity.java`: `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`