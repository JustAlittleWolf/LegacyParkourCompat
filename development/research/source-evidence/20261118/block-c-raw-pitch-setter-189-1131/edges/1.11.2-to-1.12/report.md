# C — 1.11.2 to 1.12 pitch camera and Elytra source slice

## Scope and disposition

Blind source-only comparison of ordinary local camera pitch updates, current/previous pitch sequencing, a finite overrange input witness, and the first stored-pitch read on the active local dry Elytra movement branch. The camera pitch update is unchanged; it is not a pitch-setter difference. The Elytra first-look-vector dependency changes from head yaw to yaw, so retain that as the bounded movement difference for this slice. This is source evidence only; it does not establish runtime parity or an exact intermediate introduction boundary.

## Exact source identity and integrity

- **Older:** Minecraft ID `1.11.2`, `ornithe-feather` (Feather gen2 1.11.2 build 2). `decompiled_minecraft/1.11.2/ornithe-feather.ready.json` says `status=ready`, exact `versionId=1.11.2`, and mapping `ornithe-feather`. Its source manifest SHA-256 is `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`, matching the actual `ornithe-feather.sources.sha256`; the artifact manifest SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` also matches. The provenance JSON SHA-256 is `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6`; ready JSON SHA-256 is `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40`.
- **Newer:** Minecraft release ID `1.12` (the initial 1.12 release), mapping output `decompiled_minecraft/1.12/ornithe-feather`. The in-tree `.decompile-provenance.json` SHA-256 is `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467`. Its inputs identify Mojang `1.12.json` metadata and client artifact (`client.jar` SHA-256 `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca`), Feather metadata for `1.12` and `feather-gen2-1.12+build.2-mergedv2.jar` (SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`). The provenance source-hash map matches the actual bytes for each cited Entity, LivingEntity, PlayerEntity, LocalClientPlayerEntity, and GameRenderer source file listed below.
- **Missing readiness anchor:** there is no `decompiled_minecraft/1.12/ornithe-feather.ready.json` and no separate `ornithe-feather.sources.sha256`; the 1.12 mapping-directory provenance is present and hashes of cited files match it. Treat this as source provenance, not a campaign-ready marker.

## Direct local camera pitch update — no difference

- `1.11.2`, `net/minecraft/entity/Entity.java`, `Entity.updateLocalPlayerCamera(float yaw, float pitch)`, lines 304–314.
- `1.12`, same file/member, lines 307–317.

Both signatures accept `float` yaw and pitch. The method snapshots current pitch into `float f`, applies yaw first, then assigns `(float)(this.pitch - pitch * 0.15)`, clamps current pitch with `MathHelper.clamp(this.pitch, -90.0F, 90.0F)`, and only then adjusts `lastPitch` by `(this.pitch - f)`. The expression, cast, clamp limits, and order are identical. `lastPitch` receives the post-clamp current-pitch delta; it is not separately clamped here.

The ordinary `GameRenderer` caller also uses float mouse sensitivity/scaled deltas and passes `i * j` as the pitch argument in both versions (`1.11.2` lines 846–866; `1.12` lines 850–867). This slice does not show a pitch parameter widening or different mouse-to-camera scaling at the call.

**Finite witness:** with current `pitch=89.0F`, `lastPitch=88.0F`, and finite pitch argument `-100.0F`, the assignment's unclamped value is 104 degrees; current pitch clamps to `90.0F`; then `lastPitch` becomes `88.0F + (90.0F - 89.0F) = 89.0F`. The same result follows from both source bodies. This demonstrates the current/previous sequencing, not a cross-version difference.

## First stored pitch read on active local dry Elytra movement

The `LivingEntity.moveRelative` branch is gated by local/server movement control, dry conditions (water/lava predicates permit the path), and `isFallFlying()`:

- `1.11.2`, `LivingEntity.java` lines 1388–1399. Its first look operation is `this.getLookVector()` at line 1397; the direct radians read `float i = this.pitch * (float)(Math.PI / 180.0)` follows at line 1398.
- `1.12`, `LivingEntity.java` lines 1425–1436. The corresponding order is `this.getLookVector()` at line 1434, followed by that same direct pitch-to-radians read at line 1435.
- The inherited player override delegates to this superclass path outside ability flight: `PlayerEntity.moveRelative` at 1.11.2 lines 1386–1402 and 1.12 lines 1385–1404. The travel caller passes movement speeds at `LivingEntity.java` lines 1798–1804 (1.11.2) and 1842–1848 (1.12). The 1.12 movement signature adds an upwards component; it does not change the pitch expression in the Elytra branch.

The **first stored-pitch read** in this branch is inside `getLookVector`, before the explicit `this.pitch` radians read:

- In 1.11.2, `LivingEntity.getLookVector()` calls `this.getRotationVector(1.0F)` (`LivingEntity.java` lines 1915–1918). Its override of `getRotationVector(float)` uses `this.pitch` with `this.headYaw` on the `tickDelta == 1.0F` branch (lines 1921–1928).
- In 1.12, the inherited `Entity.getLookVector()` calls the final helper with `this.pitch` and `this.yaw` directly (`Entity.java` lines 1690–1692). `LivingEntity` still has an interpolated `getRotationVector(float)` override using head yaw, but this `getLookVector()` call bypasses that override and directly invokes the `(float pitch, float yaw)` helper.

Thus the local dry Elytra calculation's first look vector changes its horizontal facing source from `headYaw` in 1.11.2 to `yaw` in 1.12, while both paths read the same stored current `pitch` first and the subsequent radians expression is unchanged. When these yaw fields differ, the look vector's horizontal components differ and feed the Elytra acceleration terms at `LivingEntity.java` lines 1405–1421 (1.11.2) / 1442–1458 (1.12); this is the movement consequence supported by the source. The camera method changes `yaw` but not `headYaw` (`Entity.updateLocalPlayerCamera` above), so the code permits divergence after camera input. This report does not assert how frequently that precondition occurs in a live client.

## Cited source hashes

SHA-256 values below were computed from current source bytes. Older values are represented in the verified source manifest; newer values were checked against `.decompile-provenance.json`.

| Release | Relative source | SHA-256 |
|---|---|---|
| 1.11.2 | `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` |
| 1.11.2 | `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` |
| 1.11.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` |
| 1.11.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` |
| 1.11.2 | `net/minecraft/client/render/GameRenderer.java` | `d8f54f58020e52b117c74cf248a1bac4fc541c4244340f6a0b43f5dbce245db5` |
| 1.12 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12 | `net/minecraft/entity/living/player/PlayerEntity.java` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` |
| 1.12 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| 1.12 | `net/minecraft/client/render/GameRenderer.java` | `c6634f86b93421a4f1d05ab3df5bfdf8edef3a295190a3a06eba48a4cac874bb` |
