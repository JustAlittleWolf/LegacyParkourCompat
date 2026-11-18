# Dry crouch horizontal input: 1.14.3 → 1.14.4

Disposition: no independent source difference in the assigned ordinary dry, unmounted, local, nonsprinting CROUCHING input slice. No canonical finding draft is warranted. This is bounded input-production coverage; it does not claim parity for travel-body behavior or the excluded pose/collision providers.

## Source identity and integrity

- **1.14.3, Yarn:** `decompiled_minecraft/1.14.3/yarn`. The nested `.decompile-provenance.json` SHA-256 is `81EA0021EB122D4E3340E20D47D6ECB7079365BA767A038D26A8264CDDACC28A`. Its pinned inputs identify the 1.14.3 client (`4063062677433988e012fba53e42e4c21adb6919ac7f061f19f3c6e75b334e07`) and Yarn `1.14.3+build.13` (`ad9e77f907cfc6780fb348ad4b3a948ffc8cea3bfe50abcc792428645e49e06e`). The four relevant source files' actual SHA-256 values match their entries in the nested `sources` manifest.
- **1.14.4, Mojmap:** `decompiled_minecraft/1.14.4/mojmap`. `mojmap.ready.json` SHA-256 is `EC7B6A6D9BA72F8A19908527977C970A8E2B3007B0E0B4323A725FB5943D3043`; `mojmap.provenance.json` SHA-256 is `9D1AD405E1E787D2FCA5ED1897E6797B1F3CD79527DA50D713232F46E2F30D97`. The ready marker identifies exact version `1.14.4`, mapping `mojmap`, and a 3,250-file source manifest; its recorded manifest digest (`af98406f3d4ed31494fbdce1078d1f3df3f712501878f9a0e9ad5879bdf23c3b`) matches the actual `mojmap.sources.sha256` bytes. The four relevant source files match their individual entries in that manifest.

## Compared behavior

- `net/minecraft/client/input/KeyboardInput.java`, 1.14.3 lines 13–24, reads opposing key states into signed `0.0F`/`+1`/`-1` movement axes, then applies slowdown iff `!bl2 && (sneaking || bl)`. Each axis is assigned `(float)(axis * 0.3)`. 1.14.4 Mojmap `net/minecraft/client/player/KeyboardInput.java`, lines 13–24, performs the same boolean-to-axis mapping, condition, double-literal multiplication, and float cast. The field/key names changed only with mapping (`movementForward`/`movementSideways`, `pressing*` → `forwardImpulse`/`leftImpulse`, `up`/`down`/`left`/`right`).
- The local callers preserve the bounded ordinary-crouch and spectator inputs: 1.14.3 `ClientPlayerEntity.tickMovement`, lines 617–618, passes `isInSneakingPose() || shouldLeaveSwimmingPose()` and `isSpectator()`; 1.14.4 `LocalPlayer.aiStep`, lines 628–629, passes `isVisuallySneaking() || isVisuallyCrawling()` and `isSpectator()`. With the supplied ordinary dry crouching pose, the crouch term is true on each side; swimming-exit and crawling additions are outside this slice. Thus for a nonspectator the slowdown is applied whether crouch comes from the sneak key or the supplied crouch-pose term; for spectator the condition suppresses it. No difference in eligibility or multiplier is established for this bounded input.
- The resulting local input axes are copied to the local player's horizontal movement fields only when it is the controlled camera: old `ClientPlayerEntity.tickNewAi`, lines 589–594; new `LocalPlayer.serverAiStep`, lines 600–605. Both fields then receive the same horizontal `0.98F` multiplication before the travel vector is constructed: old `LivingEntity.tickMovement`, lines 2215–2220, creates `Vec3d(sidewaysSpeed, upwardSpeed, forwardSpeed)`; new `LivingEntity.aiStep`, lines 2218–2223, creates `Vec3(xxa, yya, zza)`. In this path the axis order and float operation are preserved. Travel-body math is not assessed.

## Verified source hashes

| Version | Source file | SHA-256 |
| --- | --- | --- |
| 1.14.3 Yarn | `net/minecraft/client/input/KeyboardInput.java` | `ade8c1b46b4b25888d326a963f645dd4fe82f53ef0797c2e499fe37e9bb47aa0` |
| 1.14.3 Yarn | `net/minecraft/client/input/Input.java` | `0a14d641cea95ecd6a836a2aecf0a7627dbf236460160a263a85b574a0216e4e` |
| 1.14.3 Yarn | `net/minecraft/client/network/ClientPlayerEntity.java` | `28b6b4031e02afd4a2e6b4a80e5a7ca06f16dac42ca0f92cab25e2c2dc9e14ae` |
| 1.14.3 Yarn | `net/minecraft/entity/LivingEntity.java` | `e8aed9c63c0dfbe88e4b9e11c58881bc6e04b9cbaecbde5e3606e3afa406d72b` |
| 1.14.4 Mojmap | `net/minecraft/client/player/KeyboardInput.java` | `33daa0833a95e09e728c1b8f509020dd13b70e38aba922e2b1e10ec5c9b7739a` |
| 1.14.4 Mojmap | `net/minecraft/client/player/Input.java` | `8adaf34fa0bcef319d8b3dd678f53ddf68b0293a17e2c1620a6528fa0d9ad1ef` |
| 1.14.4 Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `0795c1223198ce5acf5d2ed9e5db8435bbec4cd52b96f96b1ddf2865baaae85f` |
| 1.14.4 Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `428762178a876efd4069086e6b7d51f571ea0401f44e7eff0927b7d26d9ef681` |

## Boundaries

Compared: raw keyboard axes, crouch/spectator slowdown gate and factor, local-player field writer, horizontal `0.98F` scaling and travel-vector input order. Excluded: pose selection and collision-clearance providers; crawling/swimming; item-use slowdown; travel body and all other assigned exclusions. No permanent difference finding was created.
