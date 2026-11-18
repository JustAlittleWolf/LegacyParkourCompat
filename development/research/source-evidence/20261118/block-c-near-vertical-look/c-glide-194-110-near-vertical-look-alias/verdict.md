# Independent source audit: 1.9.4 → 1.10 near-vertical Elytra look path

## Verdict

**ACCEPT** the frozen report’s bounded no-difference claim for an already-active local player in ordinary dry movement at a finite negative near-limit pitch such as `-89.0F`, provided the look vector has a nonzero horizontal component. The Elytra glide math, float lookup procedure, branch order, and first movement call are identical in the compared local Feather sources. This does not audit startup/activation, malformed or exactly aliased angles, later movement, server paths, or intermediate patches. It is not a full-pair coverage freeze.

## Immutable input

- Frozen report: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-194-110-near-vertical-look-alias/report.md`
- SHA-256 of the full report bytes at audit: `3f9ef667c4d326a3adaf1d5e7bf12edc920b630695651d8b4c7b15c701bb7651`.

## Source identity and byte checks

The exact local inputs are `decompiled_minecraft/1.9.4/ornithe-feather/` and `decompiled_minecraft/1.10/ornithe-feather/`.

- 1.9.4 `ornithe-feather.ready.json` identifies release `1.9.4`, mapping `ornithe-feather`, and client SHA-256 `23e90103a1ca2ac71100004c6d5846de09f85695f579843ef8da41571e60c908`. Its declared source-manifest digest `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` matches the local `ornithe-feather.sources.sha256`. `artifacts.sha256` pins the Feather build.2 merged jar (`49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125`) and Tiny (`9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027`).
- The local 1.9.4 ready marker, generation provenance, artifact manifest, and source manifest SHA-256 values are respectively `3d0a810d9f3a93233d880ca07ffc806ea232230d33197197ad50ba69181171c6`, `5c913715bed763369ceb5fd171cab3e63c43298023078c3683f96c7ea8fed3cd`, `9527dca544694daa3b4a7741be1a5b4802d8b6665c8a152a408a37a27a1b4d77`, and `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19`; the latter two match the ready marker declarations.
- 1.10 `.decompile-provenance.json` itself hashes to `df266e2c3009dd1a873c1665c30fe388078ed1c6227668e10cde87409a18c6da`. It pins the Feather metadata input (`a1eb1ccff25e2b76fa1d08e9b515508e97ac4837fbcacbd7f77b3a01026d3950`), build.2 merged mapping jar (`ad7cf6744d81d0260c9d97ddfe8250b2fdbd54fe332d4d00266b3545d8ddf8dd`), and client (`c00f8170ebf4fd834f552246743a64885a6dffb3dd1e3a3f3dba9676b7a0af35`).
- I recomputed SHA-256 from each present source file and checked against its local source manifest (1.9.4) or provenance `sources` entry (1.10). All five source pairs match both local declarations and the frozen report:

| Source | 1.9.4 SHA-256 | 1.10 SHA-256 |
|---|---|---|
| `net/minecraft/entity/living/LivingEntity.java` | `bbb7703f18fd5da05c4e4a43a77ea644b388e63c01d34166d308ea52054be4e5` | `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82` |
| `net/minecraft/entity/Entity.java` | `bcd7fa2206bf8d7271102f6da7fe2771f96dbe22ec79dab9f2101a3e29c17ef0` | `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `d658a0d95452d12bb7e347bfd802240eeecaf7f938e10dcd43e2640434387f85` | `db7bbbc15e2bcb0de18476fec1b88e73eccf8ae63b170276b1d8327dd0be796b` |
| `net/minecraft/util/math/MathHelper.java` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` | `43b2a4e3931733dbc3538024b7182b024d874a5f22a269a6199c6b4ca8c59151` |
| `net/minecraft/util/math/Vec3d.java` | `644edb1d25ba496f9a0bb74f8148554163975bd42ee5de0d0cac295307f64aa5` | `059dd21505c13b6febbbe880889f3aa6b00f7648ba1b770268eb8841674e90e8` |

## Independent trace

- In both versions, `LivingEntity.moveRelative` first requires `isLocallyControlled() || isLogicalSideForUpdatingMovement()`, then admits the dry path through the water/lava gates, then dispatches on `isFallFlying()`. `isFallFlying()` reads flag 7 in both versions. A `PlayerEntity` whose `abilities.flying` is false takes the ordinary `super.moveRelative` delegation. The local pre-first-move `moveRelative` source prefix, through `this.move(this.velocityX, this.velocityY, this.velocityZ);`, is identical text across versions (2,331 characters). This check stops at the first move; the rest of the method is outside this verdict.
- `LivingEntity.getLookVector()` calls `getRotationVector(1.0F)`, which supplies `pitch` and `headYaw` to the identical `Entity.getRotationVector(float,float)` body in both releases. That body computes the pitch and yaw trig arguments in `float`, looks them up through `MathHelper.sin/cos`, multiplies the float components, and passes those products into `Vec3d`. `Vec3d.fromRotation` exists in 1.10, but the inherited entity path is not used by this living-player call.
- `MathHelper.sin` and `cos` use, in both releases, `(int)(x * 10430.378F) & 65535` and `(int)(x * 10430.378F + 16384.0F) & 65535`. For `pitch = -89.0F`, applying Java float32 rounding at each expression step gives the look-vector pitch argument `+1.5533430576324463F`, with sine index `16201` and cosine index `32585`. The glide branch separately computes `i = -1.5533430576324463F`; `MathHelper.sin(i)` uses index `49335`, and `MathHelper.cos(i)` uses index `182`. Head-yaw indices depend on the particular yaw and are intentionally not fixed here.
- Before the first move, both sources compute `j = Math.sqrt(vec3d.x * vec3d.x + vec3d.z * vec3d.z)` as `double`. The descent correction is gated by `velocityY < 0.0 && j > 0.0`. The negative-pitch correction then runs under `i < 0.0F` and divides its horizontal terms by `j` without its own `j` guard. The later horizontal speed-alignment correction is guarded by `j > 0.0`. Drag is applied next, then the first `move` call. These guards and their order match exactly in the bounded source prefix. For the stated non-aliased look, `j > 0`; the report does not claim a guard protects the negative-pitch division at exact vertical/aliased states.

## Source references

All paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- `LivingEntity.java`: 1.9.4 `1302–1341`, 1.10 `1332–1371` (`moveRelative` gates, lookup-derived math, correction gates, drag and first `move`); 1.9.4 `1817–1829`, 1.10 `1853–1865` (`getLookVector` and pitch/headYaw delegation); 1.9.4 `2052–2054`, 1.10 `2088–2090` (`isFallFlying`).
- `PlayerEntity.java`: 1.9.4 `1372–1390`, 1.10 `1391–1409` (ordinary non-creative-flying delegation).
- `Entity.java`: 1.9.4 `1124–1129`, 1.10 `1148–1153` (rotation-vector expressions).
- `MathHelper.java`: both `15–21` (sine table lookup expressions).
- `Vec3d.java`: 1.10 `184–190` (`fromRotation` entry); it is not called by the traced living-player look path.


