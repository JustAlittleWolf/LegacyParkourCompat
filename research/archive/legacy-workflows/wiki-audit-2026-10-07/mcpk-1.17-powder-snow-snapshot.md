# MCPK finding snapshot: 1.17 Powder Snow movement and collision

Snapshot date: 2026-10-08. Lane: MCPK wiki audit. Status: **source-confirmed bounded finding; independent wiki-lane review pending**. This snapshot does not freeze the full release pair.

## MCPK claim

The MCPK [Version Differences page](https://www.mcpk.wiki/wiki/Version_Differences), last edited 2026-04-17 12:51, lists Powder Snow in 1.17. It says fall distance above 2.5 produces a 0.9-high hitbox; leather boots allow a full hitbox and climbing; otherwise there is no hitbox; and entities in the snow get a `(0.9, 1.5, 0.9)` stuck-speed multiplier. This snapshot checks player collision, climb, and movement response. Freeze-attribute timing and fall damage remain outside this bounded finding.

## Source adjudication

The 1.16.5 Mojmap endpoint has no `POWDER_SNOW` registration in `Blocks.java`; 1.17.1 registers Powder Snow and contains the movement methods below. These endpoints confirm the mechanic exists by 1.17.1; the exact first 1.17 patch is not pinned here.

- **Falling collision shape:** for an entity collision context, `getCollisionShape` returns `FALLING_COLLISION_SHAPE` when `fallDistance > 2.5F` (`PowderSnowBlock.java:87–94`). That shape is a full X/Z footprint with height `0.9F` (`:33–37`). The comparison is strict: exactly `2.5F` does not select this falling shape.
- **Walkable collision shape:** below the falling threshold, a living player qualifies through leather boots (`canEntityWalkOnPowderSnow`, `:111–116`). The shape is returned only when the context is above the full block shape and is not descending (`:96–99`); otherwise the method returns empty (`:100–104`). The wiki’s simplified “above the top” condition omits the non-descending guard. This block therefore has a context-dependent collision shape rather than a player-independent solid cube.
- **One-shot stuck movement:** `PowderSnowBlock.entityInside` calls `makeStuckInBlock` with `(0.9F, 1.5, 0.9F)` for entities that are not living, or for living entities whose feet block is Powder Snow (`PowderSnowBlock.java:53–57`). A player meets that gate while their feet block is Powder Snow. `makeStuckInBlock` stores the vector (`Entity.java:2150–2153`). On `Entity.move`, when its squared length is above `1.0E-7`, the requested movement vector is multiplied componentwise, the stored multiplier is cleared, and stored delta movement is set to zero before collision resolution (`Entity.java:534–555`). Thus the wiki’s vector affects an individual movement operation once; it is not a persistent multiplier on every stored velocity tick. It scales upward Y movement by `1.5` when applied, which is the source basis for the higher-jump effect.
- **Climbing:** after `LivingEntity` performs movement, its relative-friction movement method returns a vector with Y=`0.2` when `(horizontalCollision || jumping)` and either the entity is on a normal climbable or its feet block is Powder Snow and it can walk on Powder Snow (`LivingEntity.java:2155–2165`). With leather boots, jump input alone satisfies the first predicate and activates the Powder Snow climb impulse.

The client/server frozen-speed attribute timing remains unadjudicated here. The source evidence above confirms only the block collision, climb condition, and stuck-movement operation.

## Exact source identities

Canonical ready root: `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\ready\`.

Both endpoints are ready Mojmap trees. The ready source and artifact manifest hashes were checked against their markers; each cited Java file hash matches its row in the source manifest.

| Endpoint | Source manifest SHA-256 | Artifact manifest SHA-256 |
|---|---|---|
| `1.16.5/mojmap` | `9499f2611d0e6dde37cb635f1a28a591416382d6bc188b37d5f99e2b2a20823b` | `f9b9812d6995012cefcc1201a63855e5931e8551b508753be6c46d442d8b370c` |
| `1.17.1/mojmap` | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `e52c5dbae7d9663190ccc55a4f9b44a8e0615fb1fbbd8280811df43f70ec8aaa` |

Cited Java file SHA-256 values, relative to each endpoint’s `mojmap/` directory:

- `1.16.5`: `net/minecraft/world/level/block/Blocks.java` — `3b39d5cc4cd22f146ed3195aa30cbb9fdfca49f63783fabf9924a6ce7795fa12`.
- `1.17.1`: `net/minecraft/world/level/block/PowderSnowBlock.java` — `4069b3ef70e2d0ce146bbff79be54b2be99b7c7b32818c1eb45f11fd66f99aa3`; `net/minecraft/world/entity/Entity.java` — `ab28e1fba924771ec048140dfd293ee5a46a7dfe81f71a1a0b1aecc1927232de`; `net/minecraft/world/entity/LivingEntity.java` — `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`; `net/minecraft/world/level/block/Blocks.java` — `87d72a113a3f8937a6a585ef917a4fd29cc5b335c00a858f6800e38f3c1bf7a8`.

This is bounded wiki corroboration, independent of other audit lanes. Full powder-snow tick timing, broad pair coverage, and independent wiki-lane acceptance remain open.
