# Direct block-contact movement: 26.1 to 26.1.1

## Scope and source identity

This comparison covers three ordinary, non-riding local player contact responses: honey-block sliding, cobweb slowdown, and sweet-berry-bush slowdown. It follows each block's `entityInside` callback through the exact `Entity` contact dispatcher and movement consumer. It holds block state, contact geometry and support state, entity type/effects and movement queries equal between releases. Damage handling and other block/entity behavior are outside this slice.

Both source trees are ready exact-release decompilations in the published `unobfuscated` namespace. Their ready markers identify version IDs `26.1` and `26.1.1`; their provenance records identify the matching client metadata and client JAR. The ready markers' source and artifact manifest digests match the on-disk manifests. Each of the four examined Java files matches its own source-manifest SHA-256. All four file hashes are identical between releases, so the complete compared decompiled files—not just the listed methods—are byte-identical.

| Evidence | 26.1 | 26.1.1 |
|---|---|---|
| Client JAR SHA-256 | `bc6194c61566b587f196090d730698f2191170f7580adb143e4ff839939d3840` | `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3` |
| Provenance SHA-256 | `6567d3aaf2940e0350860adf0f36b52ee65bd2b23e2ac5a6696ccd70739d3e5b` | `9df9595443d56779d505511f5441bb360cca71aafcc570bc16ea8e61a1d847bf` |
| Source-manifest SHA-256 | `b77289c135ac6e12aa35e75177cea52d968f49d49e1ec745e510b1a786c9aeba` | `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3` |
| Artifact-manifest SHA-256 | `03e2146ccc20c3f873cea447cd9a5958cf8137524d66641121d6af677a62d2a0` | `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81` |

The identical examined-file SHA-256 values in both releases are:

- `net/minecraft/world/entity/Entity.java` — `8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`
- `net/minecraft/world/level/block/HoneyBlock.java` — `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec`
- `net/minecraft/world/level/block/WebBlock.java` — `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5`
- `net/minecraft/world/level/block/SweetBerryBushBlock.java` — `ca930f1c40d72a03e55e9972a58b4cc3df645ea918625125b4255343bf893a1d`

## Contact and consumption contracts

In both releases, `Entity.applyEffectsFromBlocks(List<Entity.Movement>)` invokes `state.entityInside(...)` only for a visited block whose inside-collision shape intersects the movement path (`Entity.java:1259–1269`). Thus, with the same block state, geometry, support/contact path and entity queries, each callback receives the same inputs.

- **Honey slide** — `HoneyBlock.entityInside` calls `isSlidingDown` before slide movement (`HoneyBlock.java:64–79`). Sliding is rejected on ground, above `pos.y + 0.9375 - 1.0E-7`, or when `getOldDeltaY(deltaMovement.y) >= -0.08`, where `getOldDeltaY(y) = y / 0.98F + 0.08`. It then requires `abs(pos.x + 0.5 - entity.x) + 1.0E-7 > 0.4375 + bbWidth / 2.0F` **or** the same Z-axis test (`:81–105`). For an eligible contact, `doSlideMovement` applies the same `getOldDeltaY(y) < -0.13` gate; below it, both horizontal components are multiplied by `-0.05 / getOldDeltaY(y)`. Otherwise horizontal velocity is retained. Both branches set Y to `getNewDeltaY(-0.05)`, with `getNewDeltaY(y) = (y - 0.08) * 0.98F`, and reset fall distance (`:114–124`). Gates, expression types/order and writes are identical.
- **Cobweb** — `WebBlock.entityInside` starts with `new Vec3(0.25, 0.05F, 0.25)` and selects `new Vec3(0.5, 0.25, 0.5)` for a `LivingEntity` with `MobEffects.WEAVING`, then calls `makeStuckInBlock` (`WebBlock.java:27–41`). The shared `Entity.makeStuckInBlock` resets fall distance and stores that multiplier (`Entity.java:2876–2879`). On the next ordinary `Entity.move` call, if its squared length exceeds `1.0E-7`, the requested delta is multiplied componentwise by the stored vector; the multiplier is then cleared and delta movement is set to zero (`Entity.java:704–728`). Both multiplier selection and consumption are identical.
- **Sweet berry bush** — `SweetBerryBushBlock.entityInside` writes `new Vec3(0.8F, 0.75, 0.8)` only for a `LivingEntity` that is neither a fox nor a bee (`SweetBerryBushBlock.java:82–91`). The shared `makeStuckInBlock` and next-`move` consumption above apply unchanged. The eligibility gate, vector and writes are identical.

## Result

No source-confirmed movement difference was found in these three bounded contact contracts. For any eligible same-input witness with equal contact geometry/support and entity queries, each contract produces the same velocity/fall-distance changes and the same next-move multiplier consumption in both releases. This bounded comparison does not claim full-pair source coverage or establish an implementation cutover; no per-operation finding is warranted here.