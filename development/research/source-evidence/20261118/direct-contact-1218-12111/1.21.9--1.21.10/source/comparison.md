# Ordinary block-contact movement: 1.21.9 to 1.21.10

## Evidence identity

- Older source: `decompiled_minecraft/1.21.9/mojmap`. Its embedded `.decompile-provenance.json` identifies Mojmap client mappings by `client.txt` SHA-256 `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`, and the version-specific metadata URL ends in `/1.21.9.json` (metadata package hash `91f383c21d8cd06abc9a569f2976b58da192a642`). The client JAR input SHA-256 is `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`.
- Newer source: `decompiled_minecraft/1.21.10/mojmap`. `mojmap.ready.json` reports `versionMetadataId: 1.21.10`, `mapping: mojmap`, 6,386 source files, and client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`. `mojmap.provenance.json` records a Mojmap exact run whose batch includes 1.21.10. The ready marker's source-manifest SHA-256 is `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`.
- The compared source bodies were checked against the endpoint evidence. The 1.21.9 embedded source hashes and 1.21.10 `mojmap.sources.sha256` entries agree with the files on disk:

| Source | 1.21.9 SHA-256 | 1.21.10 SHA-256 |
|---|---|---|
| `net/minecraft/world/level/block/HoneyBlock.java` | `df26bbd9b72ac98b93b97c21c656d76d33c1968ccf136884b177a0b95dff4f61` | `7f8a0dca359d623f5db71e206679f20de8cd7c42f244d0f0230b876f5131f898` |
| `net/minecraft/world/level/block/WebBlock.java` | `ba24400cf87b00c88c5bd441c781a128b6973a46bff3df62c6144e9bfc5abb43` | `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` |
| `net/minecraft/world/level/block/SweetBerryBushBlock.java` | `0f3629034c12f654701ca7dfb24f1db83a57024e8552247198b11e21dfcf57af` | `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` |
| `net/minecraft/world/entity/Entity.java` | `bd6074b108212bde667877700af1a52649472630c906c57c2a36c96a6fcee74c` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` |
| `net/minecraft/world/entity/LivingEntity.java` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` | `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |
| `net/minecraft/client/player/LocalPlayer.java` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` | `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` |

## Scoped comparison

- Honey slide producer: `HoneyBlock.entityInside` and `doSlideMovement` (1.21.9 lines 63–70, 106–116; 1.21.10 lines 63–70, 106–116). Both releases use the same `isSlidingDown` gate, the same inverse vertical-velocity calculations and `-0.05` slide target, the same horizontal scaling condition, and the same fall-distance reset. The 1.21.10 override adds a boolean parameter and forwards it to `super`; it does not read the parameter or alter the honey movement writes.
- Web slowdown producer: `WebBlock.entityInside` (lines 27–34 on both sides). Both choose `(0.25, 0.05F, 0.25)`, or `(0.5, 0.25, 0.5)` for a living entity with Weaving, and call `makeStuckInBlock` identically. The added 1.21.10 boolean is unused.
- Berry-bush slowdown producer: `SweetBerryBushBlock.entityInside` (1.21.9 lines 82–93; 1.21.10 lines 82–93). Both apply `(0.8F, 0.75, 0.8)` to living entities except foxes and bees. The server-only berry damage branch is outside this movement comparison; it is unchanged in the inspected conditions.
- Effect/contact order: `LivingEntity` travels before `applyEffectsFromBlocks` (lines 2845–2853 on both sides). That processing uses recorded movement segments and invokes block `entityInside` for intersecting block shapes (`Entity.checkInsideBlocks`; 1.21.9 lines 1134–1209, 1.21.10 lines 1137–1214). Thus block contact stages effects after the current ordinary travel. Honey's direct velocity edit is available to the next travel; web/berry `makeStuckInBlock` stores the multiplier and resets fall distance.
- First ordinary consumer: `LivingEntity.handleRelativeFrictionAndCalculateMovement` calls `move(MoverType.SELF, ...)` (lines 2476–2485 on both sides); `LocalPlayer.move` delegates to `super.move` (lines 906–909 on both sides). `Entity.move` consumes a stored multiplier by multiplying the requested displacement, clearing the stored value, and zeroing velocity (1.21.9 lines 687–690; 1.21.10 lines 687–694). The SELF behavior is the same. The separately guarded PISTON behavior in 1.21.10 is excluded by the assignment and is not claimed here. `Entity.makeStuckInBlock` itself is identical (1.21.9 lines 2783–2786; 1.21.10 lines 2788–2791).

## Verdict and limits

No source-confirmed difference was found in the assigned ordinary, non-riding, non-flying player path for Honey Block slide velocity/fall reset, Web slowdown, Sweet Berry Bush slowdown, their block-contact staging, or the first ordinary SELF consumer. No positive finding is warranted for this slice. This is a bounded result, not full-pair coverage or runtime parity. It excludes the PISTON-only stored-multiplier path, damage, fluids, portals, attributes, sprint, geometry, support behavior, other full-pair movement slices, and runtime verification.
