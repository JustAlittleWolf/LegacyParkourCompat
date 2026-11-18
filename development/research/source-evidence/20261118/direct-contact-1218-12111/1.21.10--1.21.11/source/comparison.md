# Direct block-contact comparison: 1.21.10 to 1.21.11

## Evidence identity

Both canonical source roots report ready Mojmap outputs for exact IDs 1.21.10 and 1.21.11. Their provenance records the same exact decompile run (`decompileMinecraft --versions=1.21.8,1.21.10,1.21.11 --mappings=mojmap`), with Vineflower 1.12.0 and Java 25. The ready markers bind 1.21.10 to version metadata ID 1.21.10 and client SHA-256 `e65ca028bc58da12bf8413066c90ecd4d48c94fb351d9bbd22ecdff5c87f9482`; 1.21.11 binds ID 1.21.11 and client SHA-256 `1473c9489ac50fda3c435049a76a70d61a10b8610db27f5ba9d8756b686cd3bd`. Both hashes are present as `client.jar` entries in the batch artifact manifest. The canonical source-manifest file SHA-256 values match their ready markers: 1.21.10 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1`, 1.21.11 `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555`.

## Compared behavior

No player-movement difference was found in the bounded direct-contact slice. For ordinary dry, non-riding, non-flying player contact:

- `HoneyBlock.entityInside` and its slide predicate/writer (`HoneyBlock.java`, 1.21.10 lines 62–116; 1.21.11 lines 62–116) are textually identical. The gate, old/new vertical-speed conversions, `-0.13` horizontal throttle condition, x/z scaling, assigned y, and `resetFallDistance()` order are unchanged. Honey source-file differences are only renamed boat/minecart imports; collision geometry, sounds, achievements, and damage are outside this scope.
- `WebBlock.entityInside` (`WebBlock.java` lines 27–34 on both sides) writes `(0.25, 0.05F, 0.25)` to `makeStuckInBlock`; its weaving branch writes `(0.5, 0.25, 0.5)` on both sides. `SweetBerryBushBlock.entityInside` (`SweetBerryBushBlock.java` lines 82–95 on both sides) retains the living-entity and Fox/Bee gates, then writes `(0.8F, 0.75, 0.8)` in the same order. Both block source files have identical hashes across releases. Berry damage is not evaluated.
- `Entity.checkInsideBlocks` (1.21.10 lines 1137–1225; 1.21.11 lines 1156–1244) retains ordered movement/axis traversal and invokes `entityInside` before `onInsideBlock` for each intersecting block. `applyEffectsFromBlocks` applies and clears the collector after traversal (1.21.10 lines 839–864; 1.21.11 lines 858–883). This scoped callback ordering is unchanged.
- `Player.makeStuckInBlock` (1.21.10 lines 1428–1434; 1.21.11 lines 1493–1499) has the same flying guard, superclass call, then impulse-context reset. On the first ordinary `Entity.move(MoverType.SELF, ...)` (1.21.10 lines 670–714; 1.21.11 lines 685–729), the same nonzero-multiplier gate componentwise-multiplies requested movement, then clears `stuckSpeedMultiplier` and zeros delta movement before edge backoff and collision. The local multiplier consumer and its operation order are equivalent.

This closes only these local writers, their block-contact ordering, and the first ordinary SELF-move multiplier consumer. No general movement equivalence is claimed.

## Source file integrity

SHA-256 values below match each file entry in the corresponding `mojmap.sources.sha256` manifest.

| Relative source file | 1.21.10 SHA-256 | 1.21.11 SHA-256 |
| --- | --- | --- |
| `HoneyBlock.java` | `7f8a0dca359d623f5db71e206679f20de8cd7c42f244d0f0230b876f5131f898` | `d6f11b868160cba12b45b1818265ab870fcb96bbb6f28fdc13952013bdf2ab32` |
| `WebBlock.java` | `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` | `90c0b5274ca4cf037605019cd906ff121dca108dbfc0fc2060a038ea966218fc` |
| `SweetBerryBushBlock.java` | `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` | `9797bf5172b1aec00752e06d8203e97a8cacac3f32938be2da7ad455dd07fb99` |
| `Entity.java` | `8361dbb86fe6c975d21f69d008377b6f191669be751150c842517e9d0346fa18` | `32314478c6036fa9f3f3cc409c61c622eefc5a282d60e1011a1a33cc29cf18a3` |
| `Player.java` | `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82` | `8e97167350a91741d0aa10d3b0d92a33150ed6dccdc94cd5b37d9c7ca22bcc81` |

## Limits

PISTON-only movement is excluded, including the PISTON multiplier bypass in `Entity.move`. Also excluded: damage, collision/support geometry, fluids, portals, sprint/attributes, broad tick-path coverage, implementation, and runtime parity.
