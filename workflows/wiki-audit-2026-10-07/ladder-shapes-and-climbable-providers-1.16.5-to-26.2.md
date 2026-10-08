# Bounded ladder shape and climbable-provider comparison: 1.16.5 to 26.2

Snapshot ID: `wiki-ladder-shapes-providers-1.16.5-26.2`

Status: sampled source comparison recorded; independent Wiki-lane review pending. This extends the previous 1.14.4→1.16.5 comparison with selected exact endpoints and checks ladder shape construction, climbability/tag predicate, and one separate powder-snow climb-assist branch. It is not a release-by-release census, does not establish generated runtime tag contents for 26.2, and does not close attachment/support rules or all movement consumers. No runtime validation was performed.

## Exact ready-source identities

All roots below are under `build/movement-campaign-2026-10-07/ready/` and their source-manifest entries were checked.

| Endpoint | Manifest SHA-256 | Inspected source files and SHA-256 |
|---|---|---|
| 1.17.1 | `93270d229acfb751bf56daf1e7be26ce3dcff26b29e94aa157de621405a3463b` | `mojmap/net/minecraft/world/level/block/LadderBlock.java` `866aaeef33df4aa2b2a4e581b6d7176dcbaaa47ac5349eda60b6d86d0e6da842`; `mojmap/net/minecraft/world/entity/LivingEntity.java` `33fd081aadb2b6fdc9ebf487db6da5b38c54f4b8676572790ee2203690d15e6f`; `mojmap/net/minecraft/data/tags/BlockTagsProvider.java` `558092d5e3f5e309438f1865a58ec474a0c0a00f13d31b5458560143d8550243` |
| 1.20.6 | `56aae10684471d7abb1c366bd5dd431ab976112a87e6cc5c687a68f1eff06311` | `mojmap/net/minecraft/world/level/block/LadderBlock.java` `ab51bd41e6fc33e605177f7131de91ec7e383f80c11c279c72ae146e648d4167`; `mojmap/net/minecraft/world/entity/LivingEntity.java` `c66ec8dc3b1856e490e5834a46185589030d9fbc411e3e2ce64c73203cd753b2` |
| 1.21.10 | `mojmap.sources.sha256` raw SHA-256 `4be26049350c1b314a0b198022cb1e7ab1e745e104047de7a5c7d099a9d7b7e1` | `mojmap/net/minecraft/world/entity/LivingEntity.java` `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66` |
| 1.21.11 | `0c4d83fef84c101d9db88f331acec7960f3c8346228c28995e6cf57bbfcae555` | `mojmap/net/minecraft/world/level/block/LadderBlock.java` `2ecda32f482b2a4eb9293f7b289758ad75a2a464e77a46f41df23282aef2c574`; `mojmap/net/minecraft/world/entity/LivingEntity.java` `19ed2d565858c401c69a06750b054a633a20ea864cab3a753b5c767f0bdd60e8`; `mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java` `8c17f2a6c6654721019e575377523edb0ac68c71b8325188a4e6412962c83474`; `mojmap/net/minecraft/world/level/block/Block.java` `bd7f69ffe617fa1008c9311ad7c731b23f242acb5ff3bab8c1a81e41e7d15a73`; `mojmap/net/minecraft/world/phys/shapes/Shapes.java` `fbe1aa9c92b4806128325ed82a5255aba5158de68ba62eb1afd53e7811b6b40b` |
| 26.1.2 | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `mojmap/net/minecraft/world/level/block/LadderBlock.java` `8603bfb9a55aebab196d245c9bb8527e1fa40385d125c5df64540c4f6652b0c0` |
| 26.2 | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `mojmap/net/minecraft/world/level/block/LadderBlock.java` `8603bfb9a55aebab196d245c9bb8527e1fa40385d125c5df64540c4f6652b0c0`; `mojmap/net/minecraft/world/entity/LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `mojmap/net/minecraft/data/tags/VanillaBlockTagsProvider.java` `3f6060116130ea23ff1e16b68b5baab1ee7a1ee8d00f7f69d11f7bb38b389e4b` |

The generated `data/minecraft/tags/block/climbable.json` resource is absent from the 26.2 ready tree; only the provider source is covered here.

## Bounded findings

1. **Directional ladder geometry remains equivalent in the sampled endpoints.** The four 3/16-thick facing planes recorded through 1.16.5 remain represented directly by directional boxes in the inspected 1.17.1 and 1.20.6 sources. In 1.21.11 the implementation is refactored: `Block.boxZ(16, 13, 16)` plus `Shapes.rotateHorizontal` and a facing lookup produce the same full-height plane with a 3/16-thick backing. The same `LadderBlock.java` bytes occur at 26.1.2 and 26.2. This is an endpoint/sample result, not proof that every intervening release or all collision consumers are unchanged.

2. **The tag predicate continues to broaden its named provider set.** The 1.17.1 provider's `CLIMBABLE` list contains ladders, vines, scaffolding, weeping/twisting vines and plants, and cave vines and plants. The inspected 1.21.11 provider retains these categories. The 26.2 provider lists ladder, vine, scaffolding, weeping/twisting vine forms, and glow-berry/cave-vine forms under mapped identifiers. Since the 26.2 generated tag resource is absent, this does not establish final runtime membership, data-pack overrides, or a complete provider census.

3. **Separate powder-snow assistance is not tag membership.** In 1.17.1, `LivingEntity.onClimbable()` checks the climbable tag and trapdoor case. A separate movement branch in `handleRelativeFrictionAndCalculateMovement()` sets vertical motion to `0.2` when horizontal collision or jump input is present and the feet block is powder snow walkable by the entity. Keep this separate from ladder/climbable predicate results.

4. **A sampled fall-flying gate changes between 1.21.10 and 1.21.11.** The inspected 1.21.10 `onClimbable()` lacks the gate; 1.21.11 and 26.2 return false when the entity is fall-flying and its current state is in `CAN_GLIDE_THROUGH`, before evaluating `CLIMBABLE`. This bounds an observed source transition to the interval `(1.21.10, 1.21.11]`; it does not identify a snapshot introduction or establish all contexts and consumers affected.

## Open dependencies

Independent review is pending. Release-by-release source coverage, the 26.2 generated climbable tag, data-pack effects, ladder attachment/support predicates, neighboring-block providers, the complete climb movement call graph, and the historical Wiki claims about sneak-hold and half-block slopes remain open. The earlier [1.14.4 to 1.16.5 report](ladder-shapes-and-climbable-providers-1.14.4-to-1.16.5.md) remains immutable and separately bounded.
