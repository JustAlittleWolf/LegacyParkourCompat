# Independent audit: grounded east-border AutoJump producer

Status: **COMPLETED**. The frozen bounded source claim is independently supported for ordinary first-forward grounded production and for the stated conditional east-border query. No blocker remains for this bounded audit.

## Immutable input and source identity

- Audited only `domain-b-autojump/early-border-ground-source/source-closure.md`, SHA-256 `5A368468C4246330CFC8AC404A370EEE8B131BF4BE330A48B7B728E65645F012`.
- Read project `README.md`, `buildSrc/README.md`, orchestration `WORKFLOW.md` and `OVERNIGHT.md`, shared discovery `WORKFLOW.md`, and source-lane `WORKFLOW.md` as required. No product implementation, Git history, backlog, wiki inventory, or other source report was used.
- Inspected the canonical read-only `decompiled_minecraft/<version>/ornithe-feather/` trees for `1.10`, `1.10.1`, `1.10.2`, `1.11`, `1.11.1`, `1.11.2`, and `1.12.2`. All are exact Feather Gen2 build.2 mappings. The mapping JAR SHA-256 pins checked from the embedded provenance or artifact manifests are:

| Release | Feather Gen2 build.2 mapped JAR SHA-256 |
| --- | --- |
| 1.10 | `ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd` |
| 1.10.1 | `6e8ba44832b7b77d913962462bde55084005d5de6389232453afc0bc0339ea37` |
| 1.10.2 | `18cffc56c2d8de89b50cb0328b174566236553c4aafb62afdc58a1e0ff0cadb0` |
| 1.11 | `658ad8c5a5d661eb5c2f97c00a47f6e02c02d5eee1eb1005f8e2926eb95dec44` |
| 1.11.1 | `83c2f49749b8eff663bf67664204e9cf02c75174e264d4cd57a6ba9f01bd2928` |
| 1.11.2 | `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757` |
| 1.12.2 | `e48244030c53979793bdfbe48d7f1f3536f7e4f678ee5890a416198037cd46ca` |

- Recomputed hashes for `LocalClientPlayerEntity.java`, `LivingEntity.java`, `PlayerEntity.java`, `Entity.java`, `World.java`, `Blocks.java`, and `Block.java`. All seven files match the embedded source map for 1.10/1.10.1 or the `.sources.sha256` manifest for each other release. For the five sidecar-based trees, the actual source-manifest and artifact-manifest hashes match the `.ready.json` pins.
- Independently extracted and hashed the full `LocalClientPlayerEntity.autoJump(float,float)` body in each release. Each is SHA-256 `2ef3050e68d1ed7f2ce5e69d8899abb4d12a0a57d895e4e452a73c7682247184`; offsets are 823, 823, 823, 835, 835, 835, and 857 respectively. The same strict clearance and timer logic therefore applies throughout this exact seven-version set.

## Ordinary grounded producer

In 1.10, `LocalClientPlayerEntity.serverTickAi` copies forward input into `forwardSpeed` (602–607); `LivingEntity.mobTick` damps it by `0.98F` before `moveRelative` (1743–1747). A forward-only input of 1 therefore reaches the grounded movement calculation as `0.98F`.

The player base movement-speed attribute is `0.1F` (`PlayerEntity.java:160`); Speed II's modifier is `+0.2F` per amplifier level with operation 2 (`StatusEffect.java:216–221`) and scales that modifier by `amplifier+1` (`StatusEffect.java:182–201`), making `getSpeed()` approximately `0.14F`. The grounded `moveRelative` branch samples the support block slipperiness, multiplies by `0.91F`, then uses `0.16277136F / (s*s*s)` as the ground scale (`LivingEntity.java:1386–1400`). For Stone slipperiness `0.6F`, that ground multiplier is approximately 1. The normalized forward input remains 0.98, yielding horizontal displacement approximately `0.1372` from rest. At yaw `-90°`, the forward term maps to positive X (`Entity.java:960–975`).

With center X `8.85`, the post-move center is approximately `8.9872`. The player half-width is `0.3`, so its X extent ends around `9.2872`, still supported by the stated Stone cells at X=8 and X=9 and short of the X=10 obstacle. From feet Y=64 over Stone at Y=63, a prior downward velocity is clipped to zero by the floor collision (`Entity.java:514–530`); the downward clip satisfies the grounded predicate (`Entity.java:522–523`). `LocalClientPlayerEntity.move` invokes AutoJump after collision using actual post-collision horizontal displacement (`LocalClientPlayerEntity.java:811–817`). Thus the grounded state and nonzero eastward delta are reachable through the ordinary first-forward producer, rather than existing only as a supplied hypothetical input.

## Conditional east-border query and first high timer

For the actual `dx≈0.1372`, `autoJump` uses the supplied movement vector because its squared length exceeds `0.001`; the direct vector points east. The endpoint is built from post-move X plus `dx` and then the normalized direction times `p=max(getSpeed()*7,1/l)`. With `getSpeed≈0.14`, `p≈0.98`, so the ray endpoint is about `8.9872 + 0.1372 + 0.98 = 10.1044`. It crosses the X=10 wall face by about 0.1044 blocks. The grown query box spans feet Y=64 through head Y≈65.8; the side rays are raised to Y=64.51.

The ordinary arming gates require AutoJump enabled, `ticksToNextAutojump<=0`, grounded, not sneaking/riding, and a nonzero input vector (`autoJump` lines 823–849). The fixture satisfies these. A full-collision candidate at X=10, Y=64 has top Y=65 and intersects the raised ray. For an out-of-border candidate, `World.getCollisions` may supply a synthetic Stone collision shape (1.10 `World.java:911–937`; 1.12.2 `World.java:973–1001`). This collision-list shape determines the ray hit and its top; the clearance loop instead directly calls `world.getBlockState(blockPos3)` (`autoJump` 1.10 lines 888–895; 1.12.2 lines 922–929). With loaded AIR at X=10, Y=65, that exact cell read sees AIR. The integer loop `t=1; t<1.2F; t++` visits only `t=1`; the resulting rise is `u=65-64=1`, accepted by the source's equivalent strict bounds `u>0.5F && u<=1.2F`, which assigns `ticksToNextAutojump=1`. No Jump Boost is necessary.

This separates two verified points: source conditions alone establish that the supplied grounded eastward motion arms the first high timer; tracing the ordinary input, ground movement scale, floor clip, and post-collision AutoJump call establishes that this motion is produced by the stated first-forward setup.

## Limits

The verdict covers only the described loaded east-side fixture, first-forward grounded producer, AutoJump query, and the seven exact releases listed above. It does not establish another direction, arbitrary border/headroom layouts, unknown block states, patch introduction boundaries, or runtime parity.

No code, source tree, tests, build, game launch, or central record was changed or run.

