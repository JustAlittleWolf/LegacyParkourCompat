# Independent source verdict: sneaking WATER eye sample and sprint cache

## Immutable claim inputs

Verified source-only handoff bytes before review:

- `lane-fluid-eye-cache/correction-comparison.md`: SHA-256 `cab45c05b494c2437f3e753c0e7df110180b92ee039e87ba9d8bb22933e13943`.
- `lane-fluid-eye-cache/development/research/findings/1.13.2--1.14/sneaking-water-eye-height.md`: SHA-256 `6c13fb5a17c040449bf2fe2f2842eb648d0dea23f1fdb0a9e5162f7541a4f62e`.

Both match the parent handoff. I reviewed only the frozen exact-source roster below and these two claim files; no implementation, wiki inventory, product diff, Git history, build, runtime or network source was consulted.

## Frozen source roster and pin verification

Source-tree SHA-256 digests below were computed over sorted relative file paths and each file's SHA-256, UTF-8 encoded with LF separators and path/hash NUL framing. The per-release provenance sidecars and source counts are recorded to make this audit reproducible.

| Exact source | Files | Tree digest | Provenance sidecar SHA-256 |
|---|---:|---|---|
| `1.13.2/ornithe-feather` | 2711 | `3551d72357766c0c00d78f888e703afbc30a6670e98d907888bbb7ec87adbfef` | `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383` |
| `1.14/ornithe-feather` | 3152 | `d35c727481ef73347eb911bfb68ddabbbcd9d55c37eacaf9db686470d1812af8` | `bfb011801a64e9afaa4aca43e8a8ed40bf2a02f136ffc02e65b32e79315764fa` |
| `1.14.1/yarn` | 3156 | `ab8c2a3da01fd91375d0019c16becbdd328df36471f3ec09a321d300c6c9f8bd` | `308537c5adec3104c5ca243eb5010554f00978635ed74f0d7801c804dd4d4907` |
| `1.14.2/yarn` | 3156 | `82e2d7d9e7e9d4aee5c62d5c2f7c838f70b8d31c5c1f8af7784a7afdc9830930` | `786546f99684423cc2563be45c9c3334dfcc2c7db58ddb74ba39ce403bd0869a` |
| `1.14.3/yarn` | 3160 | `bdf2ee94bfa6e19bf8ae24cdc83bd4262d69a980d8a675e556c83a1fa9e9814f` | `81ea0021eb122d4e3340e20d47d6ecb7079365ba767a038d26a8264cddacc28a` |
| `1.14.4/mojmap` | 3250 | `b5f2ae5c0509d4f677fd2204bced096e982758e587fe448c8e0c0de9279aee89` | `9d1ad405e1e787d2fca5ed1897e6797b1f3cd79527da50d713232f46e2f30d97` |

The endpoint sidecars identify Ornithe Feather Gen2 `1.13.2+build.2` and `1.14+build.2`; the 1.14.1/.2/.3 source trees identify Yarn and 1.14.4 identifies Mojmap. Each of the six 1.13.2/1.14 `Entity.java`, `PlayerEntity.java`, and `LocalClientPlayerEntity.java` file hashes matches its corresponding `*.sources.sha256` published manifest entry. Later controls were examined from their exact pinned source trees; 1.14.1/.2/.3 and 1.14.4 preserve the sneaking eye value `1.27F` and refresh active eye height from the current pose. They do not support an additional 1.14-to-1.14.1 or 1.14.3-to-1.14.4 cutover claim.

## Claim verdicts

### Eye-height and cache operation: ACCEPTED

1.13.2 `Entity#isSubmergedIn(Tag<Fluid>)` samples `d = y + getEyeHeight()`, creates `BlockPos(x,d,z)`, reads the fluid state there and accepts it only when the tag matches and the strict test `d < blockPos.getY() + (fluidState.getHeight() + 0.11111111F)` passes (`Entity.java:1042–1050`). `PlayerEntity#updateWaterSubmersion()` stores that WATER result before `super.tick()`; `PlayerEntity#updatePlayerPose()` runs after `super.tick()` (`PlayerEntity.java:207–208, 257–260, 334–354`). For a local player with sneak held in the sampled prior input state, `PlayerEntity#getEyeHeight()` returns `1.62F - 0.08F`, or `1.54F` (`PlayerEntity.java:1859–1875`).

1.14 retains the same sample point, tag and strict comparison in `Entity#m_69330161(Tag<Fluid>, boolean)`; the optional chunk gate runs before fluid lookup (`Entity.java:987–1005`). `PlayerEntity#updateWaterSubmersion()` passes `true`, and the cache is still written before `super.tick()` with player pose refreshed afterwards (`PlayerEntity.java:215–216, 262, 265–267, 324–350`). `Entity#m_84943753()` refreshes stored eye height from the currently selected pose; `PlayerEntity#m_86089714(Pose, EntityDimensions)` supplies `1.27F` for `Pose.SNEAKING` (`Entity.java:2178–2189, 2259–2261`; `PlayerEntity.java:1780–1793`). Thus at the start of a tick, the cache sample uses the pose eye height installed on the preceding tick; a pose/input change later in that tick does not retroactively change the sample.

### Source-fluid threshold and numeric witness: ACCEPTED

For the isolated still WATER source in the proposed witness (block y=1 and air at y=2), the source fluid returns level 8 (`WaterFluid.java:136–145`) and `FlowingFluid#getHeight` returns `state.getLevel() / 9.0F` in 1.13.2 (`FlowingFluid.java:448–450`). In 1.14, `getHeight(world,pos)` returns 8/9 for a source with no same-fluid block above (`FlowingFluid.java:447–451`). Both paths add the float literal `0.11111111F`; IEEE-754 float addition rounds `0.8888889F + 0.11111111F` to `1.0F`, so the strict boundary for block y=1 is double 2.0. With feet y=0.70, `0.70 + 1.54F` is about 2.24 and samples air at y=2; `0.70 + 1.27F` is about 1.97 and samples WATER at y=1, below 2.0. This part of both immutable claims is correct.

### Chunk admission gate: NO LOCAL DIFFERENCE

The 1.14 WATER-cache query returns false if its optional loaded-chunk check fails. An ordinary `LocalClientPlayerEntity#tick()` first checks `world.isChunkLoaded(new BlockPos(x,0,z))` before calling the superclass/player tick (`LocalClientPlayerEntity.java:178–181`). The later eye query uses the same unchanged x/z before movement; both checks floor the same x/z coordinates and ask about the same chunk. On this synchronous local tick path, the 1.14 query cannot see a different same-x/z chunk-load result than the outer admission check. This guard does not create the proposed local witness or a local movement delta. This conclusion is limited to the ordinary local-player path; it does not generalize to other callers.

### Body-water-independent local cache read: ACCEPTED

Both local player `isSubmergedInWater()` accessors return their cache field directly (1.13.2 `LocalClientPlayerEntity.java:999–1004`; 1.14 `LocalClientPlayerEntity.java:979–984`), without conjoining `isInWater()`. The water cache is eye-position based. The separate local sprint branches then read body-water state independently: auto-start requires `(onGround || isSubmergedInWater())` plus its other gates, while held-key start requires `(!isInWater() || isSubmergedInWater())`, adequate forward input, sufficient food/equivalent flight ability, no item use/blindness and `!isSprinting()` (`LocalClientPlayerEntity.java:723–756` for 1.13.2, `655–689` for 1.14). At y=0.70 with a water source occupying y=1, the player bounding box intersects that source, so body-water is true for both versions. The eye-cache difference remains behaviorally visible through the held-sprint start/use condition despite body-water being unchanged.

### Concrete sprint narrative as submitted: REJECTED AS WRITTEN; A RELEASE-EDGE WITNESS IS REACHABLE

The immutable narrative describes high forward input and a held sprint key (or active double-tap timer) while the player is in a steady held-sneak input state, then attributes the result to the first auto-start branch's `(onGround || cache)` condition. That state cannot start sprinting through that branch. Both local implementations capture `bl2 = input.sneaking` before refreshing keyboard input, and the branch requires `!bl2`; a held sneak therefore closes it. The current input tick also scales forward input to 0.3 while sneak is held (`1.13.2 KeyboardInput#tick`; 1.14 `KeyboardInput#tick` unless spectator), which independently fails the later `movementForward >= 0.8F` requirement. A pre-existing double-tap timer does not remove either gate. So the supplied steady-held-sneak/first-branch explanation is not a feasible ordinary LocalPlayer witness.

There is a concrete one-tick sneak-release witness for the same eye-cache delta, but it must be stated through the second held-sprint branch. Establish prior-tick crouch while falling at the stated y; on the sample tick release sneak, hold forward and sprint, and start unsprinting. Before `super.tick()`, 1.13.2 samples `1.54F` and caches false while 1.14 still has the preceding tick's `Pose.SNEAKING` eye value `1.27F` and caches true. In LocalPlayer's input processing for that sample tick, the pre-input `bl2` is still true, so the first branch remains blocked on both versions. The keyboard refresh sees sneak released and forward=1.0, however, allowing the second held-sprint branch to reach its cache gate. Since body-water is true, its `(!isInWater() || cache)` gate is false in 1.13.2 and true in 1.14; `!isSprinting()` and the remaining gates can all be satisfied. This begins sprinting in 1.14 only on the release edge, while the prior sneaking eye sample is still cached. This is a transient cache/pose ordering witness, not steady simultaneous sneak-key plus sprint input. A source-only predicate witness is supported; no trajectory result is asserted.

The finding should not claim that `(onGround || cache)` alone makes the old/new auto-start branch differ with sneak and forward simultaneously enabled. Preserve the accepted eye-sample change separately from that rejected witness phrasing. The correction-comparison's rejected body-conjunct draft is correctly rejected, and its broader no-difference verdict for the 1.14.1/.2/.3/.4 active eye-height refresh controls is supported.

## Key exact source locations

- 1.13.2 Feather: `Entity.java:1042–1050`; `PlayerEntity.java:207–208, 257–260, 334–354, 1859–1875`; `LocalClientPlayerEntity.java:181–182, 699–756, 629–632, 999–1004`; `KeyboardInput.java:13–48`; `FlowingFluid.java:448–450`; `WaterFluid.java:136–145`.
- 1.14 Feather: `Entity.java:987–1005, 2178–2189, 2259–2261`; `PlayerEntity.java:215–216, 262, 265–267, 324–350, 1780–1793`; `LocalClientPlayerEntity.java:178–181, 625–689, 587–593, 979–984`; `KeyboardInput.java:13–27`; `FlowingFluid.java:447–451`; `WaterFluid.java:140–150`.
- Later controls: Yarn `1.14.1/.2/.3` `entity/player/PlayerEntity.java#getActiveEyeHeight` (1.27F for sneaking) and `entity/Entity.java` pose-driven active-eye-height refresh; Mojmap `1.14.4` `world/entity/player/Player.java:1805–1811` and `world/entity/Entity.java` active-eye-height refresh.