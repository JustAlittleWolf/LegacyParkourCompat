# Discovery: 26.1.2 to 26.2

- Status: complete (focused movement delta and group-base boundary)
- Scope: client player entity collision restitution and post-collision block landing; older A = 26.1.2; newer B = 26.2
- Repository revision and start date: `ebe56a21d17d120f11f8ede5b6a3d49bea7c7e41`; 2026-10-01
- Selected naming namespace, CLI mode per side and alignment evidence: Mojang official names; both releases are published unobfuscated and were decompiled directly with `unobfuscated`.
- Source preparation command and log: canonical marker `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/ready/<version>--unobfuscated.json`; endpoint logs are in `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/logs/`. Shared source trees were read-only.
- Toolchain/decompiler/remapper versions and options: Gradle 9.7.1; JDK 25.0.3+9-LTS; Vineflower 1.12.0; Tiny Remapper 0.14.1; Mapping IO 0.9.1; 4G decompiler heap.

## Artifact manifest

Source-file hashes are SHA-256. Evidence paths below are relative to each source root. Mapping and remapped-jar fields are not applicable because both clients are published unobfuscated.

### A — 26.1.2

- Exact requested/resolved release: `26.1.2` / `26.1.2`.
- Source root: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/sources/26.1.2/unobfuscated/`.
- CLI mode / namespace: `unobfuscated` / Mojang official names.
- Client jar: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/cache/26.1.2/client.jar`; SHA-256 `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; publisher SHA-1 `4e618f09a0c649dde3fdf829df443ce0b8831e65`.
- Successful preparation marker: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/ready/26.1.2--unobfuscated.json`; marker SHA-256 `f005e0ee398c297a49576526c50714d4b6e78c438e836f080f276d46e470fe49`; successful log `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/logs/endpoint-26.1.2-unobfuscated.log`.
- Mapping / remapped jar: not applicable: published unobfuscated.
- Cited source hashes:
  - `net/minecraft/client/player/LocalPlayer.java` — `433fd995ad317af0f6ef0e50c1e8e3483cb8f00e0e327d4edf27a4dd99666ebe`
  - `net/minecraft/world/entity/Entity.java` — `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
  - `net/minecraft/world/entity/LivingEntity.java` — `c3b64de8dbaba8ad8a7ccd4f33255346d8206e66f12ca91260971bf5e5ac93bd`
  - `net/minecraft/world/level/block/Block.java` — `1693cfb7b84190a2fe664470a56d59e78ed5bd7d722a2bd16888b036d4d8e977`
  - `net/minecraft/world/level/block/Blocks.java` — `ba8a258b33f73fe03f93e7b02f9c25d4f66cf3aaab04c4580d0863cc71bc866f`
  - `net/minecraft/world/level/block/BedBlock.java` — `996e4ca63f4bfaea14b215648f2318d52c49f6c802317381a670d87c0a6d0b03`
  - `net/minecraft/world/level/block/SlimeBlock.java` — `84d22cf526d6bf1b4ec4b0b642a76fc2c90380a71f05ab92a7de9935f0d1c38e`
  - `net/minecraft/world/level/block/state/BlockBehaviour.java` — `9db85de84e502903e6fe497f043b58620b92089236ffb213f0b93db979428d13`

### B — 26.2

- Exact requested/resolved release: `26.2` / `26.2`.
- Source root: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/sources/26.2/unobfuscated/`.
- CLI mode / namespace: `unobfuscated` / Mojang official names.
- Client jar: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/cache/26.2/client.jar`; SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`; publisher SHA-1 `2dc72797acbc1b63fc16a11c4ac393605f453754`.
- Successful preparation marker: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/ready/26.2--unobfuscated.json`; marker SHA-256 `b7f4f560cb96b8264d558ce976326f6ac7017d2c244710b9394089a8746e674c`; successful log `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/logs/26.2-unobfuscated.log`.
- Mapping / remapped jar: not applicable: published unobfuscated.
- Cited source hashes:
  - `net/minecraft/client/player/LocalPlayer.java` — `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`
  - `net/minecraft/world/entity/Entity.java` — `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`
  - `net/minecraft/world/entity/LivingEntity.java` — `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`
  - `net/minecraft/world/entity/ai/attributes/Attributes.java` — `4a7c33552f256b5d35c6d46fd5810405f4e98182e2b26a9a3009ef4f1d3fdd5c`
  - `net/minecraft/world/level/block/Block.java` — `cec6a05e644e4a7feb8253cc4ca772a98f0e116fb098a1b7ee7302984ac7ecab`
  - `net/minecraft/world/level/block/Blocks.java` — `f3f2faeed23e9697407069a1d523107491590b8710175523ea05294d5bd00435`
  - `net/minecraft/world/level/block/BedBlock.java` — `22f515c272d52eebd75456e4a78eb7f38708d682f8d2cb5fdc170e7c3105af2b`
  - `net/minecraft/world/level/block/SlimeBlock.java` — `e0fc3087b66777a2800676aaeee47a6e98395ea4c964d5a301f3a73b3577c754`
  - `net/minecraft/world/level/block/state/BlockBehaviour.java` — `9c7a103492d0714c90397da88eb696912ff6a9ca1c005d984c4746d52637fd1e`

### Group-base boundary — 26.1

- Exact requested/resolved release: `26.1` / `26.1`; native `unobfuscated` source. Canonical marker: `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/ready/26.1--unobfuscated.json`; marker SHA-256 `6334242629252fae3aa850bae081bdc6239d1665c30bb46c4adbc5376bcadb13`; successful log `C:/Users/Wolfi/.codex/worktrees/df7f/LegacyParkourCompat/build/stable-shared-minecraft/logs/boundary-26.1-unobfuscated.log`.
- Client SHA-256: `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840`; publisher SHA-1 `191771837687b766537a8c4607cb6fad79c533a1`.
- Every marker-listed movement source was rehashed and matched. `Entity.java`, `LivingEntity.java`, `Player.java`, `LocalPlayer.java`, `Block.java`, `Blocks.java`, `BedBlock.java`, `SlimeBlock.java`, and `BlockBehaviour.java` match their 26.1.2 counterparts exactly. This confirms the `V26_1` group-base behavior; it is a targeted boundary check, not a broader 26.1 audit.

## Correspondence and call order

- Client movement reaches the common entity collision method: `LocalPlayer.aiStep()` delegates to its superclass (`LocalPlayer.java:914` on both sides); `LivingEntity.aiStep()` dispatches `travel(input)` (`LivingEntity.java:3073` in A, `3140` in B); travel calls `Entity.move(MoverType.SELF, getDeltaMovement())` across its movement branches. The latter exact `move` call site is the shared collision hook.
- A `Entity.move(MoverType, Vec3)` (`Entity.java:704`) clips movement, updates collision flags, calls `checkFallDamage`, zeroes collided horizontal velocity components (`Entity.java:779-780`), then, when `canSimulateMovement()` and `delta.y != movement.y`, calls `Block.updateEntityMovementAfterFallOn(level, entity)` (`Entity.java:781-785`). The virtual callback reaches the base implementation (`Block.java:498-500`) or old `BedBlock` / `SlimeBlock` overrides.
- B has the same `Entity.move` role and collision-state update. After `checkFallDamage`, it instead invokes private `restituteMovementAfterCollisions(BlockState, boolean, boolean, Vec3)` for simulated horizontal or vertical collisions (`Entity.java:785, 802-842`). The old `updateEntityMovementAfterFallOn` method is absent from the target `Block` API and is no longer called by `Entity.move`.
- Target ownership was checked against the published 26.2 client jar (SHA-256 matches the canonical marker) with `javap -p -s -c`: `Entity.move(MoverType, Vec3)` descriptor `(Lnet/minecraft/world/entity/MoverType;Lnet/minecraft/world/phys/Vec3;)V` invokes private `restituteMovementAfterCollisions(BlockState, boolean, boolean, Vec3)` with descriptor `(Lnet/minecraft/world/level/block/state/BlockState;ZZLnet/minecraft/world/phys/Vec3;)V` at bytecode offset 598. `Block.getBounceRestitution()` has descriptor `()F`. The mixin target owner is `net.minecraft.world.entity.Entity`, matching that call.
- Boundary evidence: 26.1 and 26.1.2 share identical relevant movement source hashes. 26.2 adds the restitution call and data, establishing `ParkourVersion.V26_1` as the old behavior boundary.

## Coverage ledger

- Slice C1 / stage 4 / player collision-axis response and vertical landing callback:
  - Status: findings.
  - A: `26.1.2/unobfuscated/net/minecraft/world/entity/Entity.java:757-785`; B: `26.2/unobfuscated/net/minecraft/world/entity/Entity.java:764-785,802-851`.
  - Dependencies: collision flags, current delta movement, `canSimulateMovement`, post-collision effect block, and landing callback.
  - Conclusion: A zeros velocity on collided X/Z axes and dispatches the legacy block callback on vertical collision. B runs restitution for horizontal and vertical collisions.
- Slice C2 / stage 5 / landing blocks and restitution values:
  - Status: findings.
  - A: `Block.java:498-500`, `BedBlock.java:138-150`, `SlimeBlock.java:33-45`; targeted searches of the hashed A `BlockBehaviour.java` and `Blocks.java` found no `bounceRestitution` field, property, or registration.
  - B: `Block.java:494-496`; `Blocks.java:696-704` sets beds to `0.75F` and `Blocks.java:2976-2979` sets slime to `1.0F`. Bed and slime no longer override the landing callback.
  - Conclusion: the old generic callback multiplies Y velocity by `0.0`; old bed bounce multiplies descending Y by `0.66F` and old slime bounce reflects descending Y directly, with the existing living/nonliving factor and sneak suppression. B expresses these through block restitution. Relevant `BedBlock`, `SlimeBlock`, `Blocks`, and `BlockBehaviour` source hashes were recorded from all validated comparison trees.
- Slice C3 / stage 6 / living-entity bounciness input:
  - Status: findings.
  - A: targeted entity/attribute search found no bounciness attribute or restitution consumer.
  - B: `Attributes.java:31` registers syncable `BOUNCINESS` with default `0.0`; `LivingEntity.java:2202-2204` returns that attribute for entity restitution. This is a server-synchronized input that may affect player collision velocity in addition to block restitution.
- All other navigation stages and resource/tag contents were not audited. The 26.2 `SUPPRESSES_BOUNCE` tag gates only the newer restitution method; the emulation intercepts that method and uses the 26.1 landing callbacks instead. This focused run makes no whole-version equivalence claim.

## Dependency queue and blockers

- DEP-COLLISION-DATA: full resource/tag closure for the new 26.2 suppression tag was not inventoried. It is not consumed by the historical 26.1 emulation, which restores the old callback path; no source blocker remains for this delta.
- No unresolved dependency prevents implementing this movement delta.

## Finding index

- [F-01: Entity collision restitution and block landing](findings/F-01-entity-collision-restitution.md) — source-confirmed.

## Resume checkpoint

- Last completed slice: the focused endpoint comparison, bed/slime landing behavior, and exact 26.1 group-base boundary.
- Next: integration with older versioned block landing changes through `BlockLandingBehavior`; runtime parity belongs to the TAS workflow.
- Outstanding assumptions: no other navigation stage is covered by this focused run.

## Source audit closure

- Coverage counts: 3 focused slices with findings; other movement stages out of this run's deliberately bounded scope.
- Unresolved gaps and limits: not an exhaustive movement audit; 26.2 tag contents and gameplay trajectories were not inspected or tested.
- Evidence/hash/correspondence audit: exact `26.1`, `26.1.2`, and `26.2` `ready` markers were read; every marker-listed movement source was rehashed and matched, and additional cited block/attribute sources were hashed from each validated tree. Shared generated sources remained read-only and may be removed after serialized preparation; marker hashes and successful log paths are retained above.
- Runtime validation: not performed (separate workflow).
