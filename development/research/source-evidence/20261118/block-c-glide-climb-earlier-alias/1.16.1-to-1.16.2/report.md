# Elytra while on climbables: 1.16.1 → 1.16.2

## Result

No adjacent-version difference was found in the assigned active Elytra path. Climbable contact blocks starting a glide through the local jump trigger, but it does not deactivate an already active glide or select a different active-glide travel formula. In that branch, the strict `velocityY > -0.5` check assigns `fallDistance = 1.0F` before movement in both releases.

## Exact source identity

- Pair: Minecraft **1.16.1 → 1.16.2**, Mojmap; canonical files are `decompiled_minecraft/1.16.1/mojmap/` and `decompiled_minecraft/1.16.2/mojmap/`.
- Each `mojmap.provenance.json` records the same exact batch command, `decompileMinecraft --versions=1.16.1,1.16.2 --mappings=mojmap --decompiler-heap=4G`, and the same batch ID `mojmap-1.16.1-to-1.16.2-e70b9be363894d4898ba9ec4c224103a`; the ready markers identify the respective exact releases and report success.
- The ready markers' source-manifest SHA-256 values match the files on disk: 1.16.1 `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`; 1.16.2 `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. Both manifests' `LivingEntity.java` entries match the files on disk: 1.16.1 `b6ea2b9b037b79fc3ea6e1f6bcd2c4c1d89d4a96315968a8f247641f6c335f26`; 1.16.2 `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88`.
- The provenance records Vineflower **1.12.0**. The locally cached `vineflower-1.12.0.jar` SHA-256 is `1dfcfe974395734fa467ce620661c7623d05ba83670de0529b1fbd63ff548b9d`. The source provenance marker records the tool version but does not itself bind this jar digest; the digest identifies the local cached artifact inspected here.
- Ready-marker artifact-manifest hashes also match disk: 1.16.1 `a113f9d59fa4582effb2ebf548c4388a54c9c5654fbf57fae7974dce896d1f98`; 1.16.2 `7b8551bdc108a1584039ac22e35b6b75c3584ed85aba24561c7d340f5cbeb108`. The manifests pin each release's client and client Mojmap artifacts.

## Gates and order

1. `LocalPlayer.aiStep` checks `input.jumping && !bl8 && !bl && !abilities.flying && !isPassenger() && !onClimbable()` before reading the chest Elytra and calling `tryToStartFallFlying` (1.16.1 `LocalPlayer.java:750–754`; 1.16.2 `LocalPlayer.java:725–729`). Thus a player currently on a climbable cannot initiate the local jump-triggered glide on that pass.
2. `Player.tryToStartFallFlying` separately requires `!onGround`, `!isFallFlying()`, `!isInWater()` and no Levitation, then an enabled chest `Items.ELYTRA`; success calls `startFallFlying`, which sets shared flag 7 (1.16.1 `Player.java:1513–1527`; 1.16.2 `Player.java:1520–1534`). This predicate itself has no climbable test. The requested path assumes an ordinary enabled chest Elytra.
3. In `LivingEntity.aiStep`, after input travel components are damped by `0.98F`, `updateFallFlying()` runs immediately before bounding-box capture and `travel(...)` (1.16.1 `LivingEntity.java:2455–2458`; 1.16.2 `:2457–2460`). It retains the active flag only off-ground, not riding, without Levitation, and with an enabled chest Elytra; otherwise its local `bl` becomes false. Only the server writes that recomputed flag (`!level.isClientSide`); Elytra wear is likewise server-only and gated by `(fallFlyTicks + 1) % 20 == 0` (1.16.1 `:2476–2494`; 1.16.2 `:2478–2496`). This update has no climbable gate.
4. In `travel`, water and lava checks precede `else if (isFallFlying())` (1.16.1 `LivingEntity.java:1912–1977`; 1.16.2 `:1914–1979`). Once that branch is reached, it contains no `onClimbable` check and does not call the ordinary movement helper `handleOnClimbable`; the glide velocity math, drag multipliers `0.99F`/`0.98F`, and move call are the same in both files (1.16.1 `:1977–2006`; 1.16.2 `:1979–2008`). Climbable blocks still participate in normal collision through `move`; a collision can affect the resulting movement/ground state, but climbable status does not redirect the glide calculation.
5. At branch entry, `if (vec36.y > -0.5) this.fallDistance = 1.0F;` occurs before look-angle calculations and before `move` (1.16.1 `LivingEntity.java:1978–1980`; 1.16.2 `:1980–1982`). The comparison is strict: exactly `-0.5` does not assign `1.0F`.

## Reset bound

The glide branch itself has no later `fallDistance` write. Its `move` call can invoke `Entity.checkFallDamage`: if movement establishes `onGround`, the common handler calls `fallOn` for positive distance and resets it to `0.0F`; otherwise, a negative moved Y adds the downward displacement to `fallDistance` via `(float)(fallDistance - d)` (1.16.1 `Entity.java:502,879–888`; 1.16.2 `:518,894–903`). Therefore the pre-move `1.0F` is an assignment at that point in the tick, not a guarantee of the post-move value. The grounded fall-distance reset is not climbable-specific. The glide branch's explicit flag clear on landing is server-only (`LivingEntity.java:2027–2028` / `:2029–2030`); it does not clear the local client's active flag there.

All cited paired methods and gates were read in both exact sources. The scoped behavior and numeric expressions are unchanged across this adjacent pair; this report makes no claim about other Elytra behavior or other 1.16 patches.
