# Keyboard diagonal: non-camera source comparison

## Immutable source identity

Primary source publication: `f4e54477501fd012c44b975f64f16264d90a18f2` (published exact 1.21.4→1.21.5 source verdict). Canonical generated sources are in `decompiled_minecraft/`; the following source-file SHA-256 values were computed from those published files.

| Release / mappings | Source file | SHA-256 |
|---|---|---|
| 1.21.4 / Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `145686EBDC7F0D12A64070309073665EB8695B7E09911A723E86113A77D04611` |
| 1.21.4 / Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `E62CE650AF0A5AC97E4D0A2BA7CAD4BA68A8D098525609F8B5B6976A8AB0AE30` |
| 1.21.4 / Mojmap | `net/minecraft/client/Minecraft.java` | `F4C9BA42F66A267FF01FF23F2BC9A8472D6F168EFFB1FC84314A613F730F94B1` |
| 1.21.5 / Mojmap | `net/minecraft/client/player/LocalPlayer.java` | `F1FCFED4A938732361E7AD951F93E9B73B02320EE56F0B219E2B3C7ACDBFA2EF` |
| 1.21.5 / Mojmap | `net/minecraft/world/entity/LivingEntity.java` | `A8AED863D4FDC515C751DD2878A8BB9C13179228CB8DBB50EDF1D19CD5404271` |
| 1.21.5 / Mojmap | `net/minecraft/client/Minecraft.java` | `95E4B37501D302CC1053951CBF6FDEBE60D7AC4FDCBF6205CB51192D760E0E3E` |
| 1.21.5 / Mojmap | `net/minecraft/server/level/ServerPlayer.java` | `81EC90DE6BF1521F45F1552C60AF1EBB1A83FBCFA2EEFB41B99E25B99D2F0D1B` |
| 1.21.5 / Mojmap | `net/minecraft/client/multiplayer/ClientPacketListener.java` | `284F34159A1BAF30B6B540751AD2FA62344C179D5ED6A7BBCB5C888665779617` |
| 1.21.5 / Mojmap | `net/minecraft/client/multiplayer/ClientLevel.java` | `75BEC470A1D8FAE229DE6FD2B0279223F15E1FA7FADA848070A72C7A008621D4` |
| 1.21.5 / Mojmap | `net/minecraft/client/renderer/GameRenderer.java` | `A443063FE95E3FBE219C9B43389BA5730D341F57FFFC62AE9A134F54E173A0D3` |
| 26.2 / unobfuscated | `net/minecraft/client/player/LocalPlayer.java` | `8D089AA09217E3607B38590F7C1623385562800943AC6DFD3D17804E041DA6D6` |
| 26.2 / unobfuscated | `net/minecraft/world/entity/LivingEntity.java` | `7FFD9C70966EDC50C9CB4D9A8FE17A518E2678FF44C8026E763D0B94AC0AE51A` |
| 26.2 / unobfuscated | `net/minecraft/client/Minecraft.java` | `C79D698D2323CB97A7E7570FA736CC7A0B88EAB7ADAB8577E1BC66BA021CCDF0` |

The 1.21.4 and 1.21.5 `mojmap.provenance.json` manifests identify exact releases/mappings, Mojmap generation, Vineflower 1.12.0, and Java 25. Their ready markers bind client SHA-256 values `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e` (1.21.4) and `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11` (1.21.5), plus source-manifest SHA-256 values `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0` and `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`. The 26.2 `unobfuscated.provenance.json` identifies exact release 26.2, unobfuscated mapping, Vineflower 1.12.0 and Java 25; its ready marker binds source-manifest SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`.

## Source facts and movement path

- **1.21.4 axis input and camera gate:** `LocalPlayer.serverAiStep` calls `super.serverAiStep()` and copies `input.leftImpulse` / `input.forwardImpulse` into `xxa` / `zza` only under `isControlledCamera()` (`LocalPlayer.java:607–622`). The predicate is exactly `minecraft.getCameraEntity() == this`. `LocalPlayer.aiStep` independently polls `input.tick()` (`LocalPlayer.java:643 onward`). In `LivingEntity.travel`, the horizontal fields are multiplied by `0.98F` (`LivingEntity.java:2767–2768`) before the vector passed into travel is constructed (`:2774`). Thus that historical damping remains on the movement-consumer path independently of the camera predicate; when the input-copy gate is false, newly polled input is not copied by `serverAiStep`.
- **1.21.5 fallback:** `LivingEntity.aiStep` calls virtual `applyInput()` (`LivingEntity.java:2712`). `LocalPlayer.applyInput` copies the modified keyboard move vector into `xxa` / `zza` only when `isControlledCamera()` is true; otherwise it calls `super.applyInput()` (`LocalPlayer.java:608–620`). Base `LivingEntity.applyInput()` multiplies existing `xxa` and `zza` by `0.98F` (`LivingEntity.java:2806–2809`). The local player's subsequent normal travel consumes those fields. This is the native false-camera movement fallback; it does not run the controlled-camera `modifyInput` path.
- **1.21.5 controlled-camera order:** `modifyInput` returns zero input unchanged; for nonzero input it scales the vector by `0.98F`, then applies item-use scaling, then sneak-speed scaling, and finally calls `modifyInputSpeedForSquareMovement` (`LocalPlayer.java:623–656`). The square correction computes float length; if positive, scales by reciprocal length, computes distance to unit square, clamps the corrected length to `1.0F`, and scales the direction to that length (`:641–656`). These steps are confined to the controlled-camera path above.
- **Camera target changes:** `isControlledCamera` compares the player with `Minecraft.getCameraEntity()` (`1.21.4 LocalPlayer.java:620–622`; `1.21.5 LocalPlayer.java:660–662`; 26.2 `LocalPlayer.java:744–746`). `Minecraft.setCameraEntity` writes/sets the camera entity (`1.21.4 Minecraft.java:2428–2431`; `1.21.5 Minecraft.java:2456–2459`; 26.2 `Minecraft.java:2664–2667`). In 1.21.5, a concrete spectator target-change path is visible in `ServerPlayer.attack`: if the server-side player is a spectator, it calls `setCamera(target)` rather than attacking (`ServerPlayer.java:1775–1779`). `setCamera` sends `ClientboundSetCameraPacket` (`:1749–1762`); client `ClientPacketListener.handleSetCamera` resolves that entity and calls `Minecraft.setCameraEntity` (`ClientPacketListener.java:1688–1695`). `Minecraft.setCameraEntity` updates the target (`Minecraft.java:2456–2459`), so the LocalPlayer equality gate becomes false while its `applyInput` fallback remains the superclass call. `ClientLevel.tickNonPassenger` is the normal entity tick consumer (`ClientLevel.java:261, 277`); camera selection does not remove the player entity. `GameRenderer.renderLevel` restores a null camera target to the local player (`GameRenderer.java:631–635`). These are camera-selection transitions. The false-camera path bypasses square correction.
- **26.2 continuation:** `LocalPlayer.applyInput` preserves the same controlled-camera test and `super.applyInput()` fallback (`LocalPlayer.java:692–705`), and its controlled `modifyInput` still starts with `input.scale(0.98F)` before later scales and square correction (`:707–738`). Base `LivingEntity.applyInput` retains `xxa *= 0.98F; zza *= 0.98F` (`LivingEntity.java:3180–3183`).

No source claim here relies on camera rendering as a movement operation. The direct movement evidence is the input-copy/fallback gates and the later use/damping of the player-axis fields.


