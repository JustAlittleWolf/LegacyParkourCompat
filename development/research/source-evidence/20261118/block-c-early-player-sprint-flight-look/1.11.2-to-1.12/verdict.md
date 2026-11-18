# Independent source-audit verdict: 1.11.2 → 1.12

## Reviewed identity

Frozen input: `c-early-1112-112-glide-look-dismount/report.md`, SHA-256 `9ac7e99640f22c7c7ad9ff2b0ee3457b5114e8812dadb808e468d6329137a694` (matches the assigned digest).

The paired sources are the exact `1.11.2` and `1.12` Feather-mapped trees, not 1.12.2. I checked the 1.11.2 ready marker and provenance (`1.11.2`, `ornithe-feather`, Feather Gen2 `1.11.2+build.2`) and the 1.12 hidden provenance inputs, which name the 1.12 version metadata/client and Feather `1.12+build.2` artifacts. The hashes below were recomputed from the cited source files and match their local source manifests/provenance maps.

| Relative source | 1.11.2 SHA-256 | 1.12 SHA-256 |
| --- | --- | --- |
| `net/minecraft/entity/living/LivingEntity.java` | `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| `net/minecraft/entity/Entity.java` | `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `65c2747bd8c70def6be7f41f624d4c9493342b39ae7bed7967f9ff63608f59ed` | `d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1` |
| `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java` | `65d362c00de1adfd55fae5f858b91bf9b227525b987c09a2d223279376a66175` | `633c59b54bdbe79a2430b59215033206c886249b154044ca95965b375ba3f8e6` |

## A. Local dry Elytra look input — confirmed

In 1.11.2, `LivingEntity.moveRelative` reaches `getLookVector()` under the locally controlled/logical-update, non-water/non-lava (unless ability-flying), already-fall-flying gates (`LivingEntity.java:1388–1398`). `LivingEntity.getLookVector()` calls `this.getRotationVector(1.0F)` (`:1915–1918`); virtual dispatch to `LocalClientPlayerEntity.getRotationVector(float)` returns `this.getRotationVector(this.pitch, this.yaw)` (`LocalClientPlayerEntity.java:153–156`). Thus the call uses current body yaw and pitch, with neither head yaw nor interpolated rotations.

In 1.12 the same movement branch calls `getLookVector()` (`LivingEntity.java:1425–1435`), and inherited `Entity.getLookVector()` directly supplies `this.pitch` and `this.yaw` (`Entity.java:1690–1692`). The local player's float-delta override (`LocalClientPlayerEntity.java:158–161`) is bypassed by that two-angle call. The route changed, but the selected local look angles did not. The report correctly limits this conclusion to the selected look input; it does not establish downstream trajectory parity.

## B. Normal non-Boat/non-HorseBase dismount writer and eligibility — confirmed within the assigned bound

On the client, `ClientPlayNetworkHandler.handleEntityPassengers` processes a passenger update and calls `entity.yeetPassengers()` in both versions (1.11.2 `:824–845`; 1.12 `:831–852`). `Entity.yeetPassengers()` invokes each passenger's `stopRiding()` (1.11.2 `Entity.java:1616–1619`; 1.12 `:1642–1645`). Base `Entity.stopRiding()` clears the mount and removes the passenger (1.11.2 `:1622–1627`; 1.12 `:1648–1653`). The local-player override only resets rowing (1.11.2 `LocalClientPlayerEntity.java:147–151`; 1.12 `:152–156`). Crucially, `LivingEntity.stopRiding()` calls `dismountRider(previousMount)` only after a real detach and when `!world.isClient` (1.11.2 `:1863–1869`; 1.12 `:1908–1913`). The reported normal dismount position callback is therefore not run locally by this detach path.

On the server, that call is synchronous on the rider (`this`); for the ordinary branch, its `entity` argument is the previous mount. The callback begins in 1.11.2 `LivingEntity.java:1282–1287` and 1.12 `:1321–1327`. Its selected direct position writer is `this.teleport(...)`: either an early candidate return or the final saved coordinate (`1.11.2 :1304–1308, :1327`; `1.12 :1341–1345, :1364`). `Entity.teleport` sets the teleported flag, sets position/angles while preserving current yaw/pitch, and ticks the entity (`1.11.2 Entity.java:2155–2159`; `1.12 :2181–2185`). This confirms the server-side callback writer and the local-client eligibility gate. It does not establish a client-side historical dismount movement delta or the behavior of the later server-to-client position correction.

The report's changed collision-box inputs and candidate geometry are outside this audit's assigned geometry boundary; no geometry verdict is made. This is a bounded audit only, not full-pair coverage or runtime parity.

## Verdict

Both independently checked claims are supported by the exact paired sources within their stated bounds. No material correction to the frozen report is required for these claims.
