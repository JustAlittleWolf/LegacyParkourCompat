# 1.11.2 → 1.12: glide look and normal dismount

## Bound and evidence identity

This report covers only (A) the look-vector yaw selected by the first already-active dry Elytra move for the local player, and (B) the normal dismount callback/position writer for a mount that is neither a Boat nor a HorseBaseEntity. It does not establish full-pair coverage. No trajectory or collision-geometry outcome is inferred.

Both exact canonical sources are Feather-mapped. The older source is `decompiled_minecraft/1.11.2/ornithe-feather/`, with sidecars at `decompiled_minecraft/1.11.2/ornithe-feather.{provenance,ready,sources.sha256}` and `decompiled_minecraft/1.11.2/artifacts.sha256`. The ready marker identifies exact version `1.11.2`, Feather Gen2 `1.11.2+build.2`, 1,921 source files, and the source-manifest SHA-256 below. Its verification sidecar reports all 1,921 published source files identical and no raw-input differences; it separately records a regenerated intermediate mapped-JAR hash, which is not a source-tree difference.

The newer source is `decompiled_minecraft/1.12/ornithe-feather/`. Its exact version is recorded in the hidden `decompiled_minecraft/1.12/ornithe-feather/.decompile-provenance.json` manifest (`inputs` include the 1.12 Feather metadata, mapping artifact, and client/version metadata). This report found and used that hidden file; it did not substitute 1.12.2.

| Evidence | SHA-256 |
| --- | --- |
| 1.11.2 source manifest `decompiled_minecraft/1.11.2/ornithe-feather.sources.sha256` | `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0` |
| 1.11.2 artifact manifest `decompiled_minecraft/1.11.2/artifacts.sha256` | `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f` |
| 1.11.2 provenance sidecar | `dcdb65e1361297a584c5b93f581b1b539911911c6cec1a203740baa5379732c6` |
| 1.11.2 ready marker | `ceabfb266993567aab8c867525365e18aa0a0d7ba5a2cb7b6cfcc83d21318c40` |
| 1.11.2 Feather merged-v2 artifact, recorded in artifact manifest | `d14500101ac23c874b0fe394eae21a382c410ec4f3bbc2e58042e5234a236757` |
| 1.11.2 Feather Tiny artifact, recorded in artifact manifest | `4fa160c09d83bf61ae21bb74ab1e33b6aabe9b8ec89904b266ad53cecc9c36e6` |
| 1.11.2 client artifact | `be3fff4f2cc005a1310a96389efdeb983d2bcb4b8e747c402acd616ae73d0ba2` |
| 1.12 hidden provenance manifest | `27515e5f4c71e7db2f035a81716507128d77466c75380ad1ef7d140c06401467` |
| 1.12 Feather metadata `.../versions/feather/1.12` | `5b4555cb0473f14b4bde13b7f60f7f979dc4f8e149187f5696ae8283e437fac4` |
| 1.12 Feather Gen2 `1.12+build.2` merged-v2 artifact | `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719` |
| 1.12 client artifact | `59e84cf6acc3912e1705ab05c722485afb3ec33343eef4756d8c16edfe6411ca` |
| 1.12 exact version metadata | `33cc40a05466b4e34e04e65def732094388e9149eb6bd37e5daa227c06cbde4b` |

The following complete source-file hashes were recomputed and match their canonical source manifests (the 1.11.2 sidecar manifest and the 1.12 hidden provenance `sources` map):

| Source file, relative to its version's `ornithe-feather/` | 1.11.2 SHA-256 | 1.12 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `87fe94fa6cbf7aba18b9a5e3401664439eb8eba9958173da8fbcd05cc7ad948b` | `6a9d5f420b13625195b149c0d9468cf1284d5eb7dc1b2a46cf01b952a27ee063` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | `633c59b54bdbe79a2430b59215033206c886249b154044ca95965b375ba3f8e6` |
| `net/minecraft/network/packet/s2c/play/EntityPassengersS2CPacket.java` | `c49ec4c182b096f73033bd12ea25d727fe9d68481beb219ea4387457ebaccb80` | `c49ec4c182b096f73033bd12ea25d727fe9d68481beb219ea4387457ebaccb80` |

## A. Local dry Elytra glide look vector

**Conclusion: no difference in the yaw or pitch supplied to the local player's `getLookVector()` at this bounded call site.** It reads current body yaw (`yaw`) and current pitch; it does not read head yaw, previous rotations, or an interpolated rotation. This establishes only the selected look input, not any resulting trajectory.

- **1.11.2:** `LivingEntity.moveRelative` reaches the `isFallFlying()` branch after the local/logical-update and dry water/lava gates and calls `this.getLookVector()` (`LivingEntity.java:1388–1398`). The inherited `LivingEntity.getLookVector()` calls virtual `this.getRotationVector(1.0F)` (`LivingEntity.java:1915–1918`). For the local player, `LocalClientPlayerEntity.getRotationVector(float)` overrides that method and returns `this.getRotationVector(this.pitch, this.yaw)` (`LocalClientPlayerEntity.java:153–156`). Thus the virtual call uses current yaw, bypassing the base LivingEntity head-yaw/previous-interpolation implementation at `LivingEntity.java:1921–1928`.
- **1.12:** the same dry, local movement branch checks `isFallFlying()` and obtains the vector from `this.getLookVector()` (`LivingEntity.java:1425–1435`). The inherited `Entity.getLookVector()` directly calls `this.getRotationVector(this.pitch, this.yaw)` (`Entity.java:1690–1692`). The local player's float-tick-delta override at `LocalClientPlayerEntity.java:158–161` is not involved in that two-angle call.

The selected values are therefore current body yaw and pitch on both sides, although the dispatch route changed from a virtual float-tick-delta call in 1.11.2 to a direct current-angle call in 1.12. No first-tick vector arithmetic or downstream gliding trajectory was compared.

## B. Normal dismount position callback

**Local-client gate:** a passenger-list update reaches `ClientPlayNetworkHandler.handleEntityPassengers`, which calls `entity.yeetPassengers()` and rebuilds the mount relationship (`ClientPlayNetworkHandler.java:824–845` in 1.11.2; `:831–852` in 1.12). A local player is detached through its `stopRiding` chain. `Entity.stopRiding()` only clears `mount` and removes the passenger (`Entity.java:1622–1627` old; `:1648–1653` new); the local-player override only adds its rowing reset (`LocalClientPlayerEntity.java:147–151` old; `:152–156` new), and `PlayerEntity.stopRiding()` only clears boarding cooldown (`PlayerEntity.java:952–956` old; `:947–951` new). `LivingEntity.stopRiding()` invokes `dismountRider` only after a real mount change **and** when `!world.isClient` (`LivingEntity.java:1863–1869` old; `:1907–1913` new). Therefore the normal dismount position callback does not execute on the local logical client.

**Server-side selected-player writer and timing:** when `LivingEntity.stopRiding()` runs on the server for a player rider, it captures the previous mount, performs the base detach, then synchronously calls that rider's `dismountRider(previousMount)` before `stopRiding()` returns. In the ordinary branch (`entity` is neither `BoatEntity` nor `HorseBaseEntity`), the method is on the **rider** (`this` is the player) and its argument is the mount. It initializes `(l,m,n)` to `(entity.x, entity.getShape().minY + entity.height, entity.z)`, then, only if mount movement direction is non-null, tests the fixed nine offset pairs around `o=floor(this.x)+0.5`, `p=floor(this.z)+0.5`. Offsets are `s=direction.offsetX*a + clockwiseY.offsetX*b`, `t=direction.offsetZ*a + clockwiseY.offsetZ*b`, producing `(u,v)=(o+s,p+t)`. It immediately calls `this.teleport(u,this.y+1,v)` and returns on the first no-collision candidate whose block at `(u,this.y,v)` is full; otherwise it records candidates at `y+1` for the full-block/water support check at `y-1`, or at `y+2` for the raised-clearance/full-block check. After the loop (or directly if direction is null), it calls `this.teleport(l,m,n)`. See old `LivingEntity.java:1282–1327`, new `:1321–1364`.

The bounded source difference is in the collision box used to test those candidates: 1.11.2 constructs it from the rider's shape min/max Y shifted by `entity.height` and tests `box.moved(s,1.0,t)` (`LivingEntity.java:1293–1305`); 1.12 constructs it from the mount's shape minY and `floor(mount.minY)+rider.height` and tests `box.moved(s,0.0,t)` (`:1332–1342`). The offset list, saved-coordinate pattern, block predicates and final/early `teleport` callbacks remain structurally the same. The external collision-box/position feasibility dependency is not resolved here; no candidate reachability or geometry outcome is claimed.

On either server path the selected `teleport` is synchronous: `Entity.teleport` marks `teleported`, calls `setPositionAndAngles(x,y,z,currentYaw,currentPitch)`, then calls `world.tickEntity(this,false)` (`Entity.java:2155–2159` old; `:2181–2185` new). This identifies the server writer but is not evidence for a local historical client movement delta. Per project invariants, server-side historical physics is outside the client replay path.

## Bounded coverage / open dependency

- Glide look-vector selection: both paired call paths traced; no local-player yaw/pitch difference found.
- Normal non-Boat/non-HorseBase dismount: client detach and callback gate traced; server writer and changed collision-box inputs recorded. Collision-box/position feasibility remains the supplied external dependency; no geometry conclusion is made.
- No other tick slices, mount subclasses, exact intermediate patches, full-pair inventory, acceptance verdict, implementation or runtime parity were assessed.
