# Independent source audit: Elytra glide with climbable contact, 1.16.1 → 1.16.2

Verdict: **ACCEPTED, bounded no-difference finding.** I independently retraced the frozen claim against the exact adjacent Mojmap sources. Within the stated path, the local climbable gate prevents the jump-triggered start attempt, while an already-active ordinary chest-Elytra glide is retained by the client-side update predicate and reaches the same active `travel` branch in both versions. The strict pre-move `double` Y comparison and `1.0F` assignment match exactly. The result says nothing about other Elytra behavior or other 1.16 patch boundaries; it is source acceptance, not runtime parity.

## Immutable claim and source identity

- Frozen claim bytes: `C:/Users/Wolfi/.codex/visualizations/2026/11/17/01a1eb7b-b9ff-7a20-8686-0295dde20f26/c-glide-climb-1161-1162-alias-gate/report.md`, SHA-256 `49c75b5343458590181c6c30d0c26b7fd4d687579266c607dd193f7e1d6fd753` (5,224 bytes at audit).
- Exact adjacent releases: `1.16.1` and `1.16.2`; both provenance JSONs specify namespace `mojmap`, batch IDs/commands for the same `1.16.1,1.16.2` run (`mojmap-1.16.1-to-1.16.2-e70b9be363894d4898ba9ec4c224103a`), and exact release IDs. Canonical source paths are `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`; these are ordinary directories/files, not symlinks or aliases.
- Ready markers identify exact `versionId`/`mapping` and `status: ready`. Their source-manifest SHA-256 values match the manifests on disk: 1.16.1 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; 1.16.2 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`.
- Their artifact-manifest SHA-256 values also match disk: 1.16.1 `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`; 1.16.2 `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`. The manifests pin the distinct release client jars (`b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`; `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`) and Mojmap artifacts (`f92d3ad192db28f84522436cc12bb92498ef3b8090f88e906d56d1fb8744490d`; `559df3579e366e11884d733f7a06530ee3f174b1eadeb8051f8492f6103b6d90`).
- Relevant source-file hashes below were independently recalculated and match each release's own `mojmap.sources.sha256` entry:

| Release | Source file | SHA-256 |
|---|---|---|
| 1.16.1 | `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` |
| 1.16.2 | `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| 1.16.1 | `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` |
| 1.16.2 | `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| 1.16.1 | `net/minecraft/world/entity/LivingEntity.java` | `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26` |
| 1.16.2 | `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| 1.16.1 | `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` |
| 1.16.2 | `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |

## Independent trace and paired references

1. **Starting versus already-active glide.** `LocalPlayer.aiStep` attempts jump-triggered Elytra start only under `input.jumping && !bl8 && !bl && !abilities.flying && !isPassenger() && !onClimbable()`; the chest stack must also be an enabled Elytra. This climbable gate is present at 1.16.1 `LocalPlayer.java:750–754` and 1.16.2 `:725–729`. `Player.tryToStartFallFlying` itself checks off-ground, not already fall-flying, not in water, no Levitation, and enabled chest Elytra, then sets shared flag 7; it has no climbable condition (1.16.1 `Player.java:1513–1527`; 1.16.2 `:1520–1534`). Thus the report correctly distinguishes inability to start through that local jump path from behavior when the flag was already active.
2. **Active-state update.** In `LivingEntity.aiStep`, `updateFallFlying()` runs after horizontal input damping and immediately before bounding-box capture and `travel` (1.16.1 `LivingEntity.java:2452–2458`; 1.16.2 `:2454–2460`). Its active condition is shared flag 7, `!onGround`, `!isPassenger()`, no Levitation, and an enabled chest Elytra (1.16.1 `:2476–2495`; 1.16.2 `:2478–2497`). There is no climbable predicate. On the logical client it does not rewrite the shared flag; the ordinary equipped Elytra remains valid under the stipulated path.
3. **Dry active-glide branch and movement order.** `travel` dispatches water/lava before `else if (isFallFlying())`; the active branch contains no climbable test or `handleOnClimbable` dispatch and reaches `move` after applying the same glide math (1.16.1 `LivingEntity.java:1912–2006`; 1.16.2 `:1914–2008`). It multiplies movement by `0.99F, 0.98F, 0.99F` and calls `move(MoverType.SELF, ...)` in both. Climbable blocks may still affect ordinary collision through `move`, which does not change branch selection.
4. **Threshold and pre/post-move limits.** At the start of that branch, `Vec3 vec36 = getDeltaMovement()` captures the current velocity and `if (vec36.y > -0.5) fallDistance = 1.0F` executes before glide look-angle calculations and before `move` (1.16.1 `LivingEntity.java:1977–1986`; 1.16.2 `:1979–1988`). `y` is `double`, comparison is strict, and exactly `-0.5` does not assign. The glide branch itself has no later fall-distance assignment. However movement may subsequently call `Entity.checkFallDamage`: on ground, positive distance can be reported to `fallOn`, then reset to `0.0F`; otherwise a negative moved Y adds downward distance using `(float)(fallDistance - d)` (1.16.1 `Entity.java:502,879–888`; 1.16.2 `:518,894–903`). Consequently `1.0F` is the pre-move assignment, not a guaranteed post-tick value.
5. **Paired behavior.** The compared statements and gate order above are equivalent across the exact pair; only line offsets differ. I found no adjacent-version difference for this bounded active Elytra path.

## Provenance limitation

The provenance JSONs record Vineflower `1.12.0`, but do not bind its jar digest. The local cached `vineflower-1.12.0.jar` inspected for this audit has SHA-256 `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`. This is an identity for the local artifact inspected now, not proof of the jar's historical origin or a hash authenticated by the source provenance. The exact release, Mojmap, manifests, mapped-source file hashes, and pair comparison above are separately bound by the ready/source manifests.

No product files, research records, Git metadata, wiki sources, builds, tests, decompilation or runtime launches were accessed or changed.
