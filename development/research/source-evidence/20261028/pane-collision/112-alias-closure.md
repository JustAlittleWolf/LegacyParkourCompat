# 1.12 pane witness alias closure

## Result

The accepted 1.12.2 pane-center witness applies without a source-behavior gap to 1.12 and 1.12.1. For the bounded input below, all three aliases collect the pane center post and clip positive X movement to the same `9.98807907104382e-6` gap. This conclusion is limited to the reviewed pane and movement slices; it does not establish equivalence across the complete releases or a patch introduction boundary.

## Bounded input and equal outcome

Use an isolated pane at `(0,64,0)`, with its four neighbors clear, and a standing player at `(0.13749,64.2,0.13750005)` whose supplied positive X movement is `0.003125` (`25/8000`). The false connection properties select only the post. Its world bounds are X/Z `[0.4375,0.5625]`, Y `[64,65]`.

The player dimensions are `0.6F × 1.8F` in the standing state. `Entity.setPosition` stores `width / 2.0F` in a float before doing the box arithmetic, yielding half-width `0.30000001192092896`. The box therefore has `maxX=0.43749001192092896` and `maxZ=0.43750006192092894`: a positive X gap of `9.98807907104382e-6` and positive Z overlap of `6.192092893986256e-8`; Y overlaps the pane. These same operations and source bytes are present in each alias.

For each version, `Entity.move` requests collisions for the box expanded toward the supplied movement. `World.getCollisions` enumerates the swept area and passes that swept box to block collection. `PaneBlock.addCollisions` resolves virtual connections when `forceShape` is false, always submits `SHAPES[0]`, and submits arms only for true connection properties. With clear neighboring faces, all four properties resolve false. `Block.addCollision` translates the center post and retains it when the swept query intersects it; the overlap is strict-positive on X, Z, and Y.

`Entity.move` then clips Y, X, and Z in that order. For positive X, `Box.intersectX` requires strict Y and Z overlap, expressed as `!(box.maxY <= this.minY)`, `!(box.minY >= this.maxY)`, `!(box.maxZ <= this.minZ)`, and `!(box.minZ >= this.maxZ)`. The tiny positive Z overlap passes; the candidate gap `pane.minX - player.maxX` is smaller than `0.003125`, so the returned X displacement is `9.98807907104382e-6`. No epsilon is applied in these orthogonal gates.

The player path reaches this shared `Entity.move`: `PlayerEntity` extends `LivingEntity`, client player classes extend `PlayerEntity`, and `LivingEntity` calls `this.move(MoverType.SELF, velocityX, velocityY, velocityZ)` in its travel branches. This checks the consumer path, not whether a full input/tick trajectory produces the supplied displacement.

## Exact source slices

All paths below are relative to `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\ornithe-feather\` and use Feather names.

| Slice | 1.12 | 1.12.1 | 1.12.2 |
|---|---:|---:|---:|
| `net/minecraft/block/PaneBlock.java`: shape table, defaults, collector, virtual property resolution | 26–79, 108–113 | 26–79, 108–113 | 26–79, 108–113 |
| `net/minecraft/block/Block.java`: default collector and translated shape intersection | 372–381 | 372–381 | 372–381 |
| `net/minecraft/world/World.java`: block enumeration and collision dispatch; public list wrapper | 964–1019 | 964–1019 | 964–1019 |
| `net/minecraft/entity/Entity.java`: default dimensions, position-to-box, movement query and axis order | 178–191, 298–305, 462–589 | 178–191, 298–305, 462–589 | 178–191, 298–305, 462–589 |
| `net/minecraft/util/math/Box.java`: strict X clip and strict intersection predicate | 185–203, 245–251 | 185–203, 245–251 | 185–203, 245–251 |
| Player dispatch: `living/player/PlayerEntity.java` superclass; `LivingEntity.java` move callers; client player superclass | 94; 1464, 1514, 1543, 1574; client `ClientPlayerEntity.java:21` | 94; 1464, 1514, 1543, 1574; client `ClientPlayerEntity.java:21` | 94; 1464, 1514, 1543, 1574; client `ClientPlayerEntity.java:21` |

The `PaneBlock.java`, `Block.java`, `Entity.java`, and `Box.java` files have identical SHA-256 hashes across all three aliases. `LivingEntity.java` and the client `ClientPlayerEntity.java` are also byte-identical across all three. The `PlayerEntity.java` files differ in unrelated content, but the class declaration (`extends LivingEntity`) and `updatePlayerPose` dimension-selection slice at lines 291–312 are the same in all three; its standing branch selects `0.6F × 1.8F`. `World.java` differs as a whole, so I compared the complete private `getCollisions(Entity, Box, boolean, List<Box>)` method through the next public overload; its LF-normalized UTF-8 body hash is identical in all three: `C288BE3D2104B7882459511CF11AAE40D36FFA6160C8E524D23D18687A2B15F6`.

## Source identity and integrity

Canonical source root: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. All three version directories and `ornithe-feather` source trees are present and read-only.

- **1.12 / ornithe-feather:** `.decompile-provenance.json` SHA-256 `27515E5F4C71E7DB2F035A81716507128D77466C75380AD1EF7D140C06401467`. Its pinned inputs name exact Mojang metadata `.../1.12.json` and Feather mapping metadata `.../feather/1.12`; the mapping JAR input is `feather-gen2-1.12+build.2-mergedv2.jar` (recorded SHA-256 `73ce7d6d0a281db15fa030ae09e836387dae1172fdffcaf3e2d1a4840830b719`). The provenance `sources` entries match the current hashes for all source files above.
- **1.12.1 / ornithe-feather:** `.decompile-provenance.json` SHA-256 `0FF5246E8D29C04554CC9EC93AE9B4ABEF539652325B6C0AC822851D7186A9C8`. Its pinned inputs name exact Mojang metadata `.../1.12.1.json` and Feather mapping metadata `.../feather/1.12.1`; the mapping JAR input is `feather-gen2-1.12.1+build.2-mergedv2.jar` (recorded SHA-256 `f94febd2167d6091b4c157aef0a85c8791e6f8fb27b883e27a82a68c7b02b9c7`). The provenance `sources` entries match the current hashes for all source files above.
- **1.12.2 / ornithe-feather:** accepted evidence identifies Feather build 2 mergedv2 JAR and Tiny mapping artifacts. Its ready record identifies version `1.12.2`, mapping `ornithe-feather`, and source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; the targeted sources align with the accepted finding and audit.

The 1.12 and 1.12.1 exports have per-file source hashes in their provenance JSON, but no published aggregate ready marker, aggregate source manifest, or artifact manifest beside these trees. For 1.12.2, the accepted evidence reports that mapping artifacts could not be reread from the provenance-named artifact directory, so the Feather artifact identity remains metadata-backed and was not revalidated against the JAR/Tiny bytes in this closure. The `.minecraft-decompile-cache` and provenance-named campaign artifact directory were not used to infer anything about behavior. No decompilation, build, test, game, TAS, Gym, or runtime execution was performed.
