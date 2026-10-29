# Local-player crouch cache and slow input: 1.16.1–1.16.3

## Phase and release coverage

Source discovery closure for the exact interval **1.16.1 → 1.16.3**. I also inspected exact 1.16.2 to check the interior patch. The crouching cache, slow-input decision, input-provider sampling, and local-player replacement logic below are present in all three inspected releases. No change in this operation was found across this interval. This does not narrow the cache's introduction earlier than the separately bounded 1.15.2 → 1.16.1 provider-closure evidence, and it makes no product, implementation, or runtime-parity claim.

## Source identity and integrity

Canonical read-only trees are under `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap`.

- **1.16.1 Mojmap:** parent `mojmap.ready.json` reports ready, exact `versionId=1.16.1`, `mapping=mojmap`, 3,572 files, and client SHA-256 `b4831e7b63b10588ff06ea86322108d916c536446f3aea3ae988b7fe3e1f66ef`. Its `mojmap.sources.sha256` SHA-256 is `76fdd121070c2012f48cbdf5cf0c78490116afc94117c30b0858eedcd6ab9754`, matching the ready marker. The parent provenance identifies version 1.16.1 and Vineflower 1.12.0.
- **1.16.2 Mojmap:** parent ready marker reports ready, exact `versionId=1.16.2`, `mapping=mojmap`, 3,516 files, and client SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`. Its source-manifest SHA-256 is `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`, matching the ready marker.
- **1.16.3 Mojmap:** no parent `mojmap.ready.json`, `mojmap.provenance.json`, or `mojmap.sources.sha256` is present. I used the mapping-root `.decompile-provenance.json` directly: SHA-256 `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`; it embeds 3,516 source-path hashes, matching the observed count of 3,516 Java sources. Its inputs identify the exact 1.16.3 client JAR (`da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`), Mojang `client.txt` mappings (`c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`), and 1.16.3 version metadata (`193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`). The embedded per-source hashes for every file cited below were recomputed and matched. This is sufficient for these inspected files; the absent parent marker is recorded as a provenance limitation, not as blanket source unavailability.

Relevant source-file hashes (1.16.1 / 1.16.2 / 1.16.3); each was checked against the parent source manifest or 1.16.3 embedded map:

| Relative source file | 1.16.1 | 1.16.2 | 1.16.3 |
|---|---|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `ee20523adfdb82f3753fa43312af170f34c4f4aa78570631c61bc8c0820dfb8b` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` | same as 1.16.2 |
| `net/minecraft/client/player/Input.java` | `367c3a9b0b21d8f106a21fd2c73a3018685dbf07d9c8a9340e2d4c9d73359201` | same | same |
| `net/minecraft/client/player/KeyboardInput.java` | `746ea654cf4f46a5f4b94a237c4307652252a44807a488b606dc993e088396f7` | same | same |
| `net/minecraft/client/multiplayer/ClientPacketListener.java` | `466d11a2b628c80e17885a0e85f25fee2ee6a05de33d9e71f309b8bc233a549d` | `4d043b866c970910ef079334b0d506f7bc0f78f679e5225f11a0418bab7a72f9` | same as 1.16.2 |
| `net/minecraft/client/multiplayer/MultiPlayerGameMode.java` | `6be55796cb04dc2a1c33ddd1aca2c0004070a80555ea2115b73306b83990fec7` | `80d1b28891b33de967cbc26e13acec097fb94091bbc226f92ad5ba2750015b66` | same as 1.16.2 |
| `net/minecraft/world/entity/Entity.java` | `27e3bb01b5d03c09161891e717c16477899768333082e81ebfaac4e525616576` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` | same as 1.16.2 |
| `net/minecraft/world/entity/player/Player.java` | `a849960d47bf00a2cddfb6636c78c518532a11967ba08829f19c5ebced08681e` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` | same as 1.16.2 |

## Trace

### Key polling and input-object capture

In each version, `Options.java:107–112` declares the same forward, left, back, right, jump, and sneak key mappings; sneak is a `ToggleKeyMapping` using `toggleCrouch`. `Input.java` and `KeyboardInput.java` are byte-identical across all three inspected releases. `KeyboardInput.tick(boolean)` (`KeyboardInput.java:13–25`) reads the four direction `isDown()` states, makes signed `0.0F`/`±1.0F` forward and side impulses, then samples jump and shift. When its argument is true, only the two directional impulses are multiplied by `0.3` and cast back to `float`; jump and shift are not scaled. `KeyMapping.java` and `ToggleKeyMapping.java` also have identical hashes in these three trees.

`ClientPacketListener.handleLogin` installs `new KeyboardInput(this.minecraft.options)` on the local player (1.16.1 `ClientPacketListener.java:397`; 1.16.3 `:392`). The respawn handler installs the same provider on the replacement (`1.16.1 :1135`; 1.16.3 `:1106`). Thus these normal login/respawn paths poll through `KeyboardInput`, not the base no-op `Input.tick`.

### Cache gates and tick order

In all three versions, `LocalPlayer.isShiftKeyDown()` returns `input != null && input.shiftKeyDown`; `isCrouching()` returns the private `crouching` field; and `isMovingSlowly()` returns `isCrouching() || isVisuallyCrawling()` (1.16.1 `LocalPlayer.java:620–630`; 1.16.2/1.16.3 `:595–605`).

At the start of `LocalPlayer.aiStep`, after the portal handler, the method copies the *previously stored* `input.jumping` and `input.shiftKeyDown` into local `bl` and `bl2`, then computes `bl3 = hasEnoughImpulseToStartSprinting()`. It writes `crouching` before polling keys:

```java
this.crouching = !this.abilities.flying
    && !this.isSwimming()
    && this.canEnterPose(Pose.CROUCHING)
    && (this.isShiftKeyDown() || !this.isSleeping() && !this.canEnterPose(Pose.STANDING));
this.input.tick(this.isMovingSlowly());
```

This order is present in 1.16.1 `LocalPlayer.java:659–666` and 1.16.2/1.16.3 `:634–641`. Therefore the cache and the slow-input argument use the stored shift state from before this tick's `KeyboardInput.tick` samples current key state. The conjunction prevents crouching while flying or swimming, requires crouch-pose collision admission, and then requires stored shift or the non-sleeping/no-standing-pose-fit condition. `Entity.canEnterPose` is `level.noCollision(this, getBoundingBoxForPose(pose).deflate(1.0E-7))` (1.16.1 `Entity.java:1620–1622`; 1.16.2/1.16.3 `:1640–1642`). `Player.isSwimming()` additionally returns false while flying or spectating before consulting `super.isSwimming()` (1.16.1 `Player.java:1775–1777`; 1.16.2/1.16.3 `:1782–1784`).

The separate visual-crawl fallback is also stable: `Entity.isVisuallySwimming()` checks `getPose() == Pose.SWIMMING`, and `isVisuallyCrawling()` checks that predicate plus `!isInWater()` (1.16.1 `Entity.java:1852–1858`; 1.16.2/1.16.3 `:1845–1851`). Thus the directional `0.3` path can be enabled by cached crouching or by the visual-swimming-outside-water predicate.

The local-player `tick()` calls `super.tick()` when its current chunk is present (1.16.1 `LocalPlayer.java:183–185`; 1.16.2/1.16.3 `:184–186`). `LivingEntity.tick()` invokes virtual `this.aiStep()` (1.16.1 `LivingEntity.java:2168`; 1.16.2/1.16.3 `:2170`). `LocalPlayer.aiStep()` reaches `super.aiStep()` after its own input/sprint handling (1.16.1 line 813; 1.16.2/1.16.3 line 788). In the inherited `LivingEntity.aiStep`, effective AI and non-immobility gate `serverAiStep()`; subsequent code sends `new Vec3(xxa, yya, zza)` to `travel` (1.16.1 `LivingEntity.java:2413–2421, 2452–2458`; 1.16.2/1.16.3 `:2415–2423, 2454–2460`). Under `isControlledCamera()`, `LocalPlayer.serverAiStep()` copies input side/forward impulses and jump into `xxa`, `zza`, and `jumping` (1.16.1 `LocalPlayer.java:634–640`; 1.16.2/1.16.3 `:609–615`).

### Sprint-related state and replacement

`wasSprinting` is a packet-transition latch initialized from the constructor argument and updated in `sendPosition` when `isSprinting()` changes; `wasShiftKeyDown` similarly tracks shift packet transitions (1.16.1 `LocalPlayer.java:92, 131–132, 213–230`; 1.16.2/1.16.3 `:93, 132–133, 214–231`). These latches are distinct from the `crouching` cache and `Input.shiftKeyDown`. At the start of `aiStep`, `sprintTime` increments and positive `sprintTriggerTime` decrements before the input snapshot; the sprint trigger logic consumes pre-poll `bl2` to reset its trigger and gate start-sprint checks. The `hasEnoughImpulseToStartSprinting()` threshold is unchanged: underwater it uses `input.hasForwardImpulse()`, otherwise `input.forwardImpulse >= 0.8` (1.16.1 `LocalPlayer.java:1014–1017`; 1.16.2/1.16.3 `:989–992`). The `LocalPlayer` source hash differs between 1.16.1 and 1.16.2, but the checked cache, input-order, server-AI and sprint snippets retain the same order and expressions; the 1.16.2 and 1.16.3 `LocalPlayer.java` files are byte-identical.

On respawn, `ClientPacketListener` creates a new `LocalPlayer` while passing the previous player's `isShiftKeyDown()` and `isSprinting()` to `MultiPlayerGameMode.createPlayer` (1.16.1 `ClientPacketListener.java:1116–1118`; 1.16.3 `:1087–1089`; factory at `MultiPlayerGameMode.java:330–331` (1.16.1) / `:329–330` (1.16.3)). The constructor stores those booleans only in `wasShiftKeyDown` and `wasSprinting` (1.16.1 `LocalPlayer.java:131–132`; 1.16.2/1.16.3 `:132–133`). `crouching` has no constructor assignment, and the new `KeyboardInput` is installed after replacement construction, so its `Input` fields begin at Java defaults and are populated when `tick` polls. This replacement path does not copy the prior crouching cache or the prior `Input` object.

## Result

The crouch cache and its slow-input consume order are unchanged in the inspected 1.16.1, 1.16.2, and 1.16.3 exact Mojmap sources. The prior raw-provider closure remains bounded dependency evidence; this report independently verifies the 1.16.1–1.16.3 cache, gates, tick ordering, sprint latches, provider installation and replacement path. The relevant source hashes and method bodies support a **no-difference** result for this operation in the assigned interval.
