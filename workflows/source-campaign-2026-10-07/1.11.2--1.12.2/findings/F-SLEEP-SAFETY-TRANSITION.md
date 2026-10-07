# F-SLEEP-SAFETY-TRANSITION: non-angry zombie pigmen no longer block the sleep movement transition

- Older version A: 1.11.2
- Newer version B: 1.12.2
- Mechanic / coverage slice IDs: S2.2a (bed sleep eligibility and movement-state transition); S1.7 (server position/sleep packet handoff)
- Classification: changed behavior
- Confidence: source-differential observed; revised artifact snapshot verified by discovery author, ops confirmation pending
- Applicability: historical player behavior
- First changed release: unknown within (1.11.2, 1.12.2]
- Runtime validation: not performed

## Paired evidence

- A manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather`
- A source: `1.11.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `net.minecraft.entity.living.player.PlayerEntity::trySleep(BlockPos)`, lines 1194-1249, SHA-256 `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b`. On the server side it queries `MonsterEntity.class` in `Box(pos.x - 8, pos.y - 5, pos.z - 8, pos.x + 8, pos.y + 5, pos.z + 8)` without an anger predicate; any nonempty result returns `NOT_SAFE` before the mount, size, position, sleeping and velocity writes.
- B manifest artifact reference: `build/movement-campaign-2026-10-07/ready/1.12.2/ornithe-feather`
- B source: `1.12.2/ornithe-feather/net/minecraft/entity/living/player/PlayerEntity.java`, `net.minecraft.entity.living.player.PlayerEntity::trySleep(BlockPos)`, lines 1185-1243, SHA-256 `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e`. The same query box now passes `PlayerEntity.IsAngryAtPlayerPredicate`; lines 1965-1975 of the same file implement it by calling `mob.isAngryAt(target)`.
- B predicate dependency: `1.12.2/ornithe-feather/net/minecraft/entity/living/mob/monster/MonsterEntity.java`, `MonsterEntity::isAngryAt(PlayerEntity)`, lines 162-164, SHA-256 `e96f44cf14e80d3154fdd3a8e6827000bf0f13a545ee095c1ebedac7993e41b6`, defaults to true. `ZombiePigmanEntity::isAngryAt(PlayerEntity)` overrides it at lines 199-201, SHA-256 `aba397ad24f519dbdfcdd594a1e9ecd8ad26ef36ea030b54b7f2be35d06f9701`, and returns `this.isAngry()`.
- Derived artifact provenance: revision `feather-r1-2026-10-07`; A snapshot SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; B snapshot SHA-256 `fcc17537a14a423e2086f600047725ec1fcfd4c7fcf5c0d1a5bda491966c1b87`. The discovery author rechecked each snapshot against its checksum and revision record, the source-manifest hashes and every listed source file, all raw-input artifact entries, and the verification-log hash. Ops confirmation is pending. The original derived JARs are unavailable; the revised snapshots do not establish their identity or prove the hash change is metadata-only.
- Shared player state helpers: A `Entity::setSize(FF)V` lines 271-288, hash `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; B lines 274-291, hash `80f091bf32166c88cf8b8bd31caf72d84fa16224410733c7d2a0f00563f294a0a`. A/B `PlayerEntity::wakeUp(ZZZ)V` bodies are identical (A lines 1265-1287, B lines 1259-1281) and restore size to `0.6F, 1.8F`.
- Entry and authority path: A `BedBlock` invokes `player.trySleep(pos)` at line 64, source hash `84f56aab19adae4b953a27040e5f431f65c8923d6bd169995ac5a5f629a024e1`; B does so at line 86, hash `48c127b2df425cb8d7ff60a284207de10a8408e90ee18a8449522a004a0b4369`. A/B `ServerPlayerEntity::trySleep(BlockPos)` delegates to the changed superclass gate and, on `OK`, sends a sleep packet and teleports the player (A starts at line 540, file hash `c2187f10b589bbfb47bef2a1b573781fecad55a13fb80c4c5dc11260d9f12197`; B starts at line 600, file hash `ad4effc65edd98098d86af8ba725f6d6c2c94068f996739cf2734d34ecf804a9`). The client `handlePlayerSleep` applies the sleep packet through `trySleep` (A lines 766-769, B lines 775-778; handler file hashes `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` and `fce21d9902e555fd46545fb04bdb6c4a912eef6398790776ad1a122e09960c7c`).

## Source-level difference

Under the same server-side preconditions—living, awake player; natural dimension; non-sunny time; bed in range; and only a non-angry zombie pigman in the 8-by-5-by-8 query box—A includes the pigman in the monster list and returns `NOT_SAFE`. B filters the list to monsters angry at this player; `ZombiePigmanEntity.isAngryAt` returns false when `isAngry()` is false, so the list is empty and the method proceeds.

After the gate, both versions stop riding if needed, call `setSize(0.2F, 0.2F)`, move the player to the bed position, set the sleeping state and reset all three velocity components to `0.0`. Thus B performs those player movement-state writes in this condition while A returns before them. The server-player override then teleports and sends the sleep packet only when the result is `OK`.

## Reachability and dependencies

Bed interaction calls `PlayerEntity.trySleep`. On the server this dispatches to `ServerPlayerEntity.trySleep`, which delegates to the changed base eligibility check. B's filter uses the MonsterEntity default (`true`) and the ZombiePigman override (`isAngry()`); the other MonsterEntity subclasses found in the exact source scan use the default. The changed return value gates player unmounting, dimensions, position, sleeping flag, velocity reset and server teleport/sleep synchronization.

## Consequence and uncertainty

Source proves the different sleep result and the resulting server-side player size, position and velocity writes under the stated preconditions. It does not establish a measured trajectory. The client handler replays sleep locally, while the server teleport/correction path remains in S1.7 for full packet-authority closure. B also drops shoulder entities before resizing; that changes non-player entities and is outside this finding's player movement consequence.

## Handoff

This is one bed-sleep eligibility change with a player dimension/position/velocity consequence. S2.2a owns this finding. Keep the sleep-packet and correction authority trace linked to S1.7. First-changed release within the interval remains unknown. The discovery author freshly verified revision `feather-r1-2026-10-07`; do not freeze or accept this finding until independent ops confirmation closes `DEP-ARTIFACT-INTEGRITY` and a separate blind source reviewer accepts the exact finding snapshot. The original derived mapped JARs remain unavailable, and the revision does not prove identity with them.
