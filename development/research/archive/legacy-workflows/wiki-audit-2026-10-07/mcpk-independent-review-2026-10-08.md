# MCPK wiki snapshot review — 2026-10-08

Review base: `8c83d931dab12a02f28d4aa1d8fe78070cf6aec6` (`feat/mcpk-wiki-audit`). The exact-source root was the supplied read-only ready tree at `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`. No implementation files, Minecraft Wiki pages, live wiki pages, game runtime, tests, or builds were used.

## Review isolation and source provenance

The preserved MCPK catalog records an original fetch failure (HTTP 403). This review therefore treats the wiki wording, page revision, and claim attribution as preserved-catalog provenance only; it does **not** claim live-page verification. Conclusions below independently compare the assigned bounded claims with the exact vanilla endpoints.

Process note: one preliminary, overly broad repository search returned excerpts from unrelated workflow/source reports. I discarded those excerpts and made no use of them in the adjudication; after noticing the scope issue, I restricted all further reads to the four assigned MCPK snapshots and their exact vanilla sources. Because the lane instructions require strict separation from other audit/source reports, the technical assessments below are useful, but **formal independent-lane acceptance is blocked** pending a clean reviewer pass.

## Snapshot identities and disposition

Git blob IDs below are the repository's SHA-1 object IDs. Content SHA-256 values were calculated from the committed snapshot files.

| Bounded finding snapshot | Immutable snapshot identity | Technical assessment | Formal lane status |
|---|---|---|---|
| `1.16.2 sneak edge backoff predicate` — `workflows/wiki-audit-2026-10-07/mcpk-1.16.2-step-down-snapshot.md` | `8c83d93`; blob `ca614da15f99108b83cdda8b6cd392a301a58c39`; SHA-256 `96c79dffd5a81a93d1ebbbcd068d5614b24f7a004166c656a6eeabf68a2bfe909` | ACCEPT | BLOCKED by the process note above |
| `1.17 Big Dripleaf collision timing` — `workflows/wiki-audit-2026-10-07/mcpk-1.17-big-dripleaf-snapshot.md` | `f4d5dcf`; blob `3760d8ab5316a6e5ae9ccdb112af1bcf56820236`; SHA-256 `ed0f578f848d7d7111db615680f58f66183bfa4db757356182f085d9c2c5ae4c` | ACCEPT | BLOCKED by the process note above |
| `1.17 swimming entry gate` — `workflows/wiki-audit-2026-10-07/mcpk-1.17-swimming-snapshot.md` | `3de1093`; blob `79c325aa090b49eb272115ba768b1fa790333cfe`; SHA-256 `1ae1e0824ff433ec26651fdb7cd9c638bd45cb5ad1f858f4cd4706ed6f528884` | REQUEST CHANGES | BLOCKED by the process note above |
| `1.15 slipperiness sample examples` — `workflows/wiki-audit-2026-10-07/mcpk-1.15-slipperiness-examples-snapshot.md` | `bb05061`; blob `65a2936fb581f40a2c0ce1c4f86e0b682c9b39de`; SHA-256 `6714aaef4e086592c04f062ee78fdf0ee0eea4678e432c93028f456051287883` | REQUEST CHANGES | BLOCKED by the process note above |

The four snapshot files in the review checkout are byte-identical to the corresponding commit:path objects above. No claim here freezes a release pair or establishes implementation coverage or runtime parity.

## Bounded adjudications

### 1.16.1 → 1.16.2 sneak edge backoff — ACCEPT

The cited `Player.maybeBackOffFromEdge` comparison is accurate. At 1.16.1, the predicate requires `(SELF || PLAYER)`, `onGround`, and `isStayingOnGroundSurface()`. At 1.16.2 it requires `!abilities.flying`, the same mover types, `isStayingOnGroundSurface()`, and `isAboveGround()`. For `Player`, `isStayingOnGroundSurface()` returns the sneak-key state. `isAboveGround()` is true on ground, or when `fallDistance < maxUpStep` and a vertical collision probe at `fallDistance - maxUpStep` has no collision. This does not admit arbitrary airborne movement.

Caller and probe closure were checked: `Entity.move` invokes the virtual `maybeBackOffFromEdge` before collision resolution at 1.16.1 `Entity.java:488–489` and 1.16.2 `Entity.java:504–505`. Both `Player` bodies retain the X-only, Z-only, then combined support probes and decrement each horizontal component by `0.05` (with the same threshold branch). `CollisionGetter.noCollision(entity, AABB)` delegates to the collision collection and requires every returned shape to be empty in both endpoints. The changed scope is the entry predicate; the probe loops are not a separate delta.

External vanilla inputs remain `abilities.flying`, mover type, sneak input, `onGround`/`fallDistance`, `maxUpStep`, the player box, and world collision shapes. This is a player movement predicate and is eligible historical behavior; its state producers and collision geometry are not part of this bounded finding.

Exact ready endpoints (both `mojmap`; ready JSON and manifest hashes matched):

- `1.16.1`: source manifest `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; artifact manifest `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`.
- `1.16.2`: source manifest `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7`; artifact manifest `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`.
- Exact source rows checked: `Player.java` SHA-256 `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` → `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960`; caller `Entity.java` SHA-256 `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` → `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666`; `CollisionGetter.java` SHA-256 `fc7f175b10c2c6683913a903a366bd775facecf3c58f04cc6e9cfc95ddceed98` → `b507d6be11e5985a62cfeb249a99dcb5f8edaf346f12cb2487797d9e01763eac`.

The snapshot is correctly bounded: it does not pin the first patch release or claim full-pair coverage.

### 1.17 Big Dripleaf player-contact collision timing — ACCEPT

At 1.16.5, `Blocks.java` has no `BIG_DRIPLEAF` registration; 1.17.1 registers it. This bounds the feature to after 1.16.5 and by 1.17.1, without proving the first 1.17 patch. In 1.17.1, `entityInside` changes `NONE` to `UNSTABLE` only server-side, when the entity is grounded, its position Y is strictly above `blockY + 0.6875F`, and there is no neighbor signal. `UNSTABLE` is scheduled after 10 block ticks. `NONE` and `UNSTABLE` both use the 11/16-to-15/16 leaf collision box; the next scheduled transition to `PARTIAL` lowers its top to 13/16. `PARTIAL` schedules `FULL` after a further 10 block ticks, and the `FULL` leaf collision shape is empty. The collision override returns the leaf shape, so the outline stem does not change this player-collision timing claim.

`entityInside` does not filter its `Entity` argument to players, so the recorded player path is relevant but not an exclusive trigger inventory. Non-player movement and other triggers do not broaden this finding. Historical collision-shape handling is eligible only for selected versions where Big Dripleaf exists (1.17.1 and later); the feature must not be invented for earlier profiles. Block states and scheduled world ticks remain vanilla inputs.

Exact ready endpoints (both `mojmap`; ready JSON and manifest hashes matched):

- `1.16.5`: source manifest `9499f2611d0e6dde37cbdf5f1a28a591416382d6bc188b37d5f99e2b2a20823b`; artifact manifest `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`.
- `1.17.1`: source manifest `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; artifact manifest `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`.
- Exact source rows checked: `1.16.5/Blocks.java` SHA-256 `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`; `1.17.1/Blocks.java` `87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8`; `BigDripleafBlock.java` `1e1315b0a53eb85a3365335131e7819c1b667a55e14343d839c5211c671fd59f`.

The 10-tick values are source block-tick delays, not wall-clock durations under all tick conditions. The timing discrepancy in the snapshot is source-confirmed.

### 1.16.5 → 1.17.1 swimming entry gate — REQUEST CHANGES

The `Entity.updateSwimming` endpoint comparison is accurate and the snapshot correctly separates continuation from entry: the `isSwimming()` branch is unchanged, while the not-yet-swimming branch adds `level.getFluidState(blockPosition).is(FluidTags.WATER)` after the existing sprinting, underwater, and non-passenger checks.

The snapshot needs one player-specific guard before its claim is ready for implementation handoff. `Entity.tick` calls `updateSwimming()` after updating fluid state and eye-fluid state, using virtual dispatch. `Player.updateSwimming()` in both endpoints first checks `abilities.flying`; flying players are set to not swimming, while only non-flying players delegate to `Entity.updateSwimming()`. Amend the snapshot to state that the new entry gate applies to the non-flying player path and record the unchanged player override/call order. This preserves the valid bounded claim while closing its player consumer path.

Vanilla external inputs are sprint state, eye/submersion state, passenger state, the player block position, the water fluid tag/state, and the flying ability. For the player movement campaign this is an eligible state/pose entry predicate; fluid-state production and non-player swimming behavior remain outside the finding.

Exact ready endpoints (both `mojmap`; ready JSON and manifest hashes matched):

- `1.16.5`: source manifest `9499f2611d0e6dde37cbdf5f1a28a591416382d6bc188b37d5f99e2b2a20823b`; artifact manifest `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c`.
- `1.17.1`: source manifest `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b`; artifact manifest `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa`.
- Exact source rows checked: `Entity.java` SHA-256 `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` → `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; `Player.java` `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` → `724bb298499dabe489dffd5ce7ec81c9773619a8d2c70044911eae4c9e8ff481`.

No exact first 1.17 patch, pose dimensions, fluid-height boundary, or subsequent swimming math is inferred.

### 1.14.4 → 1.15.2 slipperiness sample examples — REQUEST CHANGES

The sampling arithmetic and listed examples are source-confirmed. 1.14.4 reads a block at `boundingBox.minY - 1.0`; 1.15.2 calls `getBlockPosBelowThatAffectsMyMovement()`, which constructs a `BlockPos` at `minY - 0.5000001`. `BlockPos(double, double, double)` delegates to `Vec3i(double, double, double)`, which floors all components. A bottom slab top at `Y+8/16` therefore samples the block below; the bed top at `Y+9/16` and Soul Sand top at `Y+14/16` sample their own block positions under the new offset. A top slab is a different case and remains an explicit limitation.

Please correct the exact cited Soul Sand source path. The snapshot names `net/minecraft/world/level/block/SoulSandBlock.java`, but the ready source manifest and directory contain `net/minecraft/world/level/block/SoulsandBlock.java`. The cited SHA-256 (`355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`) matches the file contents, but the path string does not match the exact manifest identity. The Windows filesystem is case-insensitive; that does not make the spelling exact for a reproducible source reference.

Vanilla external inputs are player bounding-box position, `BlockPos` floor conversion, the sampled block, and its friction value. The sampling operation is player movement behavior; the block states and friction registry stay vanilla. The examples are shape/sample-position deductions, not measured full trajectories.

Exact ready endpoints (both `mojmap`; ready JSON and manifest hashes matched):

- `1.14.4`: source manifest `af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`; artifact manifest `308cc33ef6dffc047432ade6affc88eccc9de94a736ee9a98571fd92915c2970`.
- `1.15.2`: source manifest `cb7fd93f8730d8b2813857744f8572060535c7de44fcee86222acd1ceb6f43e7`; artifact manifest `208ab867640097a0c188e452de4876934deb217d75ac726d358cfa6730260406`.
- Exact source rows checked: `1.14.4/LivingEntity.java` SHA-256 `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681`; `1.15.2/LivingEntity.java` `46d243bb7e51f7b54404aa1d7d6e6b827682d0f5925d02847c4193306c4d5e54`; `Entity.java` `191b3ad3e7348c9bac1e703fff896706d23a751bf15162aacf676f5f97c0a10e`; `BedBlock.java` `e2496c30e0ff922f5bb492f951e2389c1e1fbbbdcd7aed2d4d308574fa7a4b0f`; `SlabBlock.java` `5a57ae37e77fc93074100d7e3eff1c5e23b43d8a23e0289b68ad8c9914df0d86`; `SoulsandBlock.java` `355f0cc25e78dce76cbc9c89d8bd8ff1956d4b53780cba8e3fb5c23f485802cf`; `Vec3i.java` `dd4ad167ad9b4be90e9b13216e8b450b7bc401ca700732c2dd78970739942c7d`.

## Execution record

This is a bounded wiki/source review only. No mechanics were changed, no tests/builds/game clients/TAS/Gym/server/Docker actions were run, and no full-pair or runtime-parity claim is made. A clean, isolated reviewer should repeat the four disposition checks before treating any snapshot as independently accepted.
