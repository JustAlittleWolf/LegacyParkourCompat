# Independent review — remaining LocalPlayer member inventory, 1.15.2 → 1.16.5 Mojmap

## Bound claim and evidence

Reviewed `.native-source/20261030/1152-1165-finish43/remaining-localplayer-member-inventory/report.md`, SHA-256 `5278c1ba1283bf289c46751a192c975ffcbfc6e2129eeb044ac6c9a7f1361545`, including its input pins and named closure reports. Read the exact canonical Mojmap sources at `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.15.2,1.16.5}\mojmap`. The current files' SHA-256 values matched the cited source hashes and the corresponding entries in each release-root `mojmap.sources.sha256` manifest. The parent source-manifest hashes remain 1.15.2 `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7` and 1.16.5 `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b`.

## Dispositions

### Open Shulker LocalPlayer escape — ACCEPT

The claimed source-level response delta is supported with the specified witness. With LocalPlayer at `(0.5,64,0.5)`, width `0.6`, `noPhysics == false`, initial X velocity zero, an EAST-facing ShulkerBox at `(0,64,0)` and a matching opened BE at progress 1, all four old call probes have X/Z offsets `±0.21`, floor to block column `(0,0)`, and pass Y `64.5` to `checkInBlock`. `blocked` scans from `floor(bb.minY)` to `ceil(bb.maxY)` exclusive, including block Y 64 for this standing box. The old Shulker override reports suffocating unconditionally, and the adjacent sampled columns are clear. The four calls execute in the stated order. On the final probe `(0.71,64.5,0.71)`, EAST and SOUTH have equal `0.29` distances; strict `<` plus WEST/EAST/NORTH/SOUTH order retains EAST. Earlier calls can write X `-0.1`, but the final call writes `+0.1`, so the source-level final pre-travel X response is `+0.1` from a zero initial value.

In 1.16.5 the same four call sites are guarded only by `!noPhysics`, but use `getY()` for the queried position. `suffocatesAt` tests the player's vertical box clipped to that column. The opened Shulker collision shape may intersect this box, but the registration's predicate (`BE absent || isClosed`) is false for the matching opened BE. The collision spliterator applies that predicate before collision-shape acquisition, so the initial query returns non-suffocating; no direction is chosen and no X escape write occurs. This is limited to the stated loaded block/block-entity state and source-level response; it is not a runtime trajectory claim.

### Fall-flying request ladder/tag gate — ACCEPT, conditional only

The 1.15.2 LocalPlayer request at `aiStep:715–720` and 1.16.5 request at `:725–730` have identical surrounding jump/flight and Elytra gates except `!onLadder()` became `!onClimbable()`. At the start of `aiStep`, `bl` snapshots `input.jumping` before `input.tick`; `bl8` records an ability-flight change in the intervening ability-toggle block. Thus the request requires current `input.jumping`, `!bl8`, `!bl` (no jump held in the pre-tick snapshot), not ability-flying, and not a passenger; then chest-slot Elytra identity and enabled state are checked before `tryToStartFallFlying`, and a successful result sends `START_FALL_FLYING`.

The effective climbable test is source-defined but its full state membership is not: old `LivingEntity.onLadder` explicitly checks ladder, vine, scaffolding, then the aligned open-trapdoor ladder case; new `onClimbable` checks the data-driven `BlockTags.CLIMBABLE` membership first and then the same trapdoor case, recording `lastClimbablePos` on success. `BlockTags.java` only binds the tag name here; these Java source roots do not establish the effective membership of every vanilla or dynamically reloaded state. Therefore the possible request difference is conditional on effective tag membership differing from the old explicit set (or the converse). This review does not assert that arbitrary tag-only states occur by default. The accepted flying-ladder descent/trapdoor witness is a separate operation and remains closed under its prior review.

`Player.tryToStartFallFlying` itself is not called from elsewhere on the client source route: LocalPlayer is its sole client caller in both releases. It rechecks airborne, not already fall-flying, not in water, and enabled Elytra. The 1.16.5 method adds `!hasEffect(LEVITATION)`; that is a separately accepted Player operation and must not be attributed to the LocalPlayer climbable-gate residual. The server packet listener separately invokes the same method on `START_FALL_FLYING` (old `ServerGamePacketListenerImpl:1137–1140`, new `:1270–1273`); this is server command handling, not evidence for historical client-only movement. No server emulation claim follows.

### `updateAutoJump` residual boundary — ACCEPT as unresolved, no separate finding

The LocalPlayer `move` override invokes `updateAutoJump` after `super.move` in both versions. The `canAutoJump` conditions are unchanged: enabled, timer nonpositive, grounded, not staying on a ground surface, not riding, nonzero input movement, and block jump factor at least one. The collector API migration inside `updateAutoJump` is equivalent for the entity filter at equal state: old empty exclusion set admits all candidates through the remaining vanilla filters; new `entity -> true` likewise admits them. The block-shape collector has a separate narrowly reachable rounding residual in the supplied collision-collection report, but its multi-axis `Entity.move` witness does not establish an auto-jump-specific candidate trajectory or coordinate witness. Keep this route dependency open as bounded; do not reuse the Entity.move witness as proof of an auto-jump delta.

## Reviewed exact members and source hashes

| Release | File/member slice | SHA-256 of complete source file |
|---|---|---|
| 1.15.2 | `client/player/LocalPlayer.java`: `checkInBlock` 385–429, `blocked` 431–443, `aiStep` calls 650–655, fall-flying request 715–720, `move` caller and `updateAutoJump`/`canAutoJump` 853–976 | `3a9019bd7b860e251c23fd8d0cd70b7f5b38566d34470c4e29b1014ef689ccbd` |
| 1.16.5 | `client/player/LocalPlayer.java`: `moveTowardsClosestSpace` 414–441, `suffocatesAt` 443–447, `aiStep` calls 656–661, fall-flying request 725–730, `move` caller and `updateAutoJump`/`canAutoJump` 865–988 | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| 1.15.2 | `world/entity/LivingEntity.java`: `onLadder` 1226–1251 | `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54` |
| 1.16.5 | `world/entity/LivingEntity.java`: `onClimbable` 1363–1395 | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.15.2 | `world/entity/player/Player.java`: `tryToStartFallFlying` 1578–1588 | `1ba2724c22163862b8f7fdfdea5a04a66e4db26a119d7e5360ba024724a34793` |
| 1.16.5 | `world/entity/player/Player.java`: `tryToStartFallFlying` 1520–1530 | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.16.5 | `tags/BlockTags.java`: `CLIMBABLE` binding 79 | `99f23e86191ba49033800be965341c4408b002404e9f403e3f4d42f962f1e88b` |
| 1.16.5 | `world/level/block/Blocks.java`: Shulker suffocation registration 3224–3236 | `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12` |
| 1.16.5 | `world/level/CollisionSpliterator.java`: predicate-before-shape query 72–86 | `19ab959c469b8edb5c371ff737a59a61b8a80bf397eff1db280064342094d297` |

The Shulker finding's additional provider identity is bound to `.native-source/20261030/1152-1165-finish43/suffocation-provider-witness/report.md` SHA-256 `cf6e6bd53282b51a1d7e215ce99351cca88199776ba76cac93501ab9d111beb5` and its coordinate/tie review to `suffocation-provider-witness-independent-audit/report.md` SHA-256 `214e87591315d984782e035e51261666d7ca96f6e02648e14d18baaa5906547d`. The inventory input revision and the reused auto-jump collision residual are those pinned in the inventory's `input-hashes.tsv`.

No product, wiki, backlog, source-generation, test, build, launch, or runtime action was performed.
