# 1.16.2 -> 1.16.3: active Elytra glide at climbable feet

Result: no source delta was found for this bounded player-movement operation. The glide-before-climbable gate is present identically in both exact Mojmap source trees. This is a checked no-difference slice, not an independent version-boundary finding, and does not establish complete pair coverage.

## Exact local evidence

- 1.16.2, Mojmap: `decompiled_minecraft/1.16.2/mojmap/`; canonical adjacent metadata `mojmap.ready.json`, `mojmap.provenance.json`, and `mojmap.sources.sha256`. Ready metadata says `versionId=1.16.2`, `mapping=mojmap`, and its `sourceManifestSha256` is `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`, matching the manifest bytes. Ready marker SHA-256: `e5f69bab646b6143b0660a97fade59595ff2e924d36567b9cd285c8babd31214`; provenance SHA-256: `a9b166406064e877751542fe7df27acc32be3843f6fdc2280dac32ce0b54207c`; manifest SHA-256: `10d312ca29ee48e25f4727adafb9fcfbd741dcebe81b343d3dcffa071952a7a7`. The ready marker pins client SHA-256 `19960f4e3c60f2723f361844d2b4020b8bf2160bd91a1e1efde8ce5aea4ff73d`.
- 1.16.3, Mojmap: `decompiled_minecraft/1.16.3/mojmap/`. Its source-root `.decompile-provenance.json` is hidden and is the local provenance record; there is no adjacent `mojmap.ready.json` or `mojmap.sources.sha256`. Hidden provenance SHA-256: `513271804ead6757345d305717e7e0bfc1641434c35a3b254f287bd8fd281498`. Its pinned exact metadata input is `.../1.16.3.json` SHA-256 `193c5226095ce9e8a317f1e35ddbe4d976f176f6df7ac649ecfa669e17160ead`; client JAR SHA-256 `da85197babd989a7de318774cf5d97bcef95e89a70c9fa9a4c8180463e9589fa`; official `client.txt` mapping SHA-256 `c41c58faaf3a3b651d8883b9b1df355cb6ad59c4d6296b2e1ffc3dfe2bf9be3e`.
- Both trees are labeled `mojmap`. The 1.16.3 hidden provenance's recorded hashes for the four inspected source files agree with their local bytes and with 1.16.2's canonical source manifest.

## Compared source slices

All paths below are relative to each version's `mojmap/` source root. Line ranges are the same in both source files.

- `net/minecraft/world/entity/LivingEntity.java:1363-1373`: `onClimbable()` checks the feet block state and `BlockTags.CLIMBABLE` (and then the trapdoor-as-ladder case), confirming the ladder/vine feet precondition.
- `net/minecraft/world/entity/LivingEntity.java:1914-2022`: `travel(Vec3)` evaluates water, lava, then `else if (this.isFallFlying())`, before entering the generic friction branch. In the glide branch, the strict test `vec36.y > -0.5` assigns `fallDistance = 1.0F` before calculating the glide movement and calling `move(MoverType.SELF, ...)`. Generic climbable handling is bypassed by that branch. The later glide branch's shared-flag clear is guarded by `this.onGround && !this.level.isClientSide`.
- `net/minecraft/world/entity/LivingEntity.java:2062-2071,2089-2104`: only the generic `handleRelativeFrictionAndCalculateMovement` path invokes `handleOnClimbable`; it zeroes fall distance and clamps horizontal velocity and downward velocity (minimum -0.15) before movement. That cleanup is not reached by the glide branch.
- `net/minecraft/world/entity/LivingEntity.java:2454-2460,2478-2500,2891-2893`: `aiStep` runs `updateFallFlying()` immediately before `travel`; the travel body is gated by `isEffectiveAi() || isControlledByLocalInstance()`. The updater reads shared flag 7, retains local `bl=true` when the player is airborne, not a passenger, has no levitation, and wears an enabled chest Elytra. If a gate fails it sets only its local `bl=false`; its write-back is server-only, so this method does not itself clear the client shared bit. `isFallFlying()` reads shared flag 7.
- `net/minecraft/world/entity/player/Player.java:1387-1415,1475-1527`: player travel delegates to `LivingEntity.travel` unless creative flight applies; `tryToStartFallFlying()` requires airborne, not already fall-flying, dry, and no levitation.
- `net/minecraft/client/player/LocalPlayer.java:725-733`: local jump initiation additionally requires `!this.onClimbable()`. Thus this report concerns an already-active ordinary Elytra state carried into ladder/vine contact, not starting flight while climbing.
- `net/minecraft/world/entity/Entity.java:485-518,894-902` and `net/minecraft/world/entity/LivingEntity.java:257-278`: `move` invokes `checkFallDamage` immediately after collision resolution. On a downward landing, the inherited handler first passes the current fall distance to `block.fallOn(...)` and then sets the field to zero; off-ground descent updates it by subtracting the actual negative movement. This can overwrite or advance the pre-move `1.0F` value in the same call. The gameplay damage consequences of `fallOn`/fall-damage resolution are excluded. Entity movement ownership/source identity and these collision-state writes are identical between the pair.

No gameplay or damage trajectory is inferred. The server-only flag clear and later fall-damage calculation are outside this client-side gate claim; the immediate `fallDistance` writes during movement are included as state ordering.

## Relevant full source SHA-256 values

| Relative source path | 1.16.2 = 1.16.3 SHA-256 |
| --- | --- |
| `net/minecraft/world/entity/LivingEntity.java` | `b5d8a1a3c80f85d5d545b5a777e9e2a915dc002a7e31b5f5ad12bf7e285d7a88` |
| `net/minecraft/world/entity/player/Player.java` | `d2e26589bdb6a20dc914266db06aa48f50811efc792d6e63b3008c9199914960` |
| `net/minecraft/client/player/LocalPlayer.java` | `6011569e766bb1568609147be9aa14e9c08c51948e3d3a60fd066e848f6a8c2b` |
| `net/minecraft/world/entity/Entity.java` | `f9a9a073fe3105a0aa53d0f21ec72e59084e8d21a14c1cd3be75703865ee2666` |


