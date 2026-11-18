# Contact slowdown comparison: 26.1.1 to 26.1.2

## Verdict

No difference was found in the three assigned direct player movement contact contracts. For a non-riding local player, with the same contact geometry, support state and queried state on both releases, honey slide velocity/fall reset, cobweb stuck-speed production and next-move consumption, and sweet-berry-bush stuck-speed production and next-move consumption are source-equivalent. No canonical difference finding is warranted for this bounded comparison.

## Exact source identities

Both sides use the published `unobfuscated` source mapping and exact metadata IDs 26.1.1 and 26.1.2. Their ready markers report complete Vineflower 1.12.0 decompilations under Java 25. Source-manifest SHA-256 values match the ready markers:

- 26.1.1: client JAR `8c109be6177f40d10d0afd820b06542f9609597874242b96e16c5b0dc9e1fef3`; `unobfuscated.sources.sha256` `88afdfb8264842713f8cce86c0bf88e95c2c991ee706349d6ed8b6c22d52fcb3`; `artifacts.sha256` `6ee17e11c2cf02a4ba93d66852ea172d5ba857028654fb5df3c1d9c87f0faa81`.
- 26.1.2: client JAR `b1b3158572666445eff01e82fad8c7de2e4953db6d354f311730d77a8359d0b0`; `unobfuscated.sources.sha256` `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0`; `artifacts.sha256` `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92`.

The three producer source files are byte-identical between releases; their SHA-256 values match each side's source manifest: `HoneyBlock.java` `998aff733f7bb654fcd837680375ba740574d3371d39bd708dbdae352d4782ec`, `WebBlock.java` `8afb61bea4d6fc8502971d0729c6a2f87f4f55d618ae6ed3251b95141f524af5`, and `SweetBerryBushBlock.java` `ca930f1c40d72a03e55e9972a58b4cc3df645ea918625125b4255343bf893a1d`. `Entity.java`, containing the shared contact dispatch and movement consumer, is also byte-identical in both sources (`8b83b1f036aabbd13d990897c540c993f7120f02955486cfcf229517d4097ccf`).

## Contact production and ordered consumption

- **Honey slide:** `HoneyBlock.entityInside` calls `isSlidingDown`, then `doSlideMovement` when the same gates pass (`HoneyBlock.java:64-80, 96-120`). The movement writes the same horizontal reduction (`-0.05 / getOldDeltaY(...)`) and vertical velocity (`getNewDeltaY(-0.05)`) in either branch, then calls `resetFallDistance()` (`:111-122`). The operation and order are identical.
- **Cobweb:** `WebBlock.entityInside` chooses `(0.25, 0.05F, 0.25)`, or `(0.5, 0.25, 0.5)` for a living entity with WEAVING, and calls `makeStuckInBlock` (`WebBlock.java:27-41`). The producer is identical.
- **Sweet berry bush:** for living entities other than foxes and bees, `SweetBerryBushBlock.entityInside` calls `makeStuckInBlock(state, new Vec3(0.8F, 0.75, 0.8))` before the separate server-side movement/damage branch (`SweetBerryBushBlock.java:82-99`). Player contact reaches this producer under the stated contact precondition; damage is outside this comparison. The movement producer and its ordering are identical.
- **Local ordering and next move:** ordinary `LivingEntity.aiStep` takes its non-riding `travel(input)` branch when movement is locally simulatable, then calls `applyEffectsFromBlocks()` for the authoritative local instance (`LivingEntity.java:3059-3078`; the riding branch is at `:3070-3074`). Block effects are checked along recorded movements and dispatch `state.entityInside(...)` (`Entity.java:839-850, 885-897, 1195-1210, 1259-1277`). Thus cobweb/berry contact stores the multiplier after that travel. On the next ordinary `Entity.move`, when its squared length exceeds `1.0E-7`, the multiplier scales requested delta except for piston movement; then the multiplier is cleared and delta movement is zeroed (`Entity.java:704-730`). This consumer, gate, order and operation are identical in both releases.

Under the bounded equivalence condition, the same block contact is visited and branch predicates resolve the same way. Since the relevant producer bodies and ordered local consumer are identical, these three contracts yield no movement delta from 26.1.1 to 26.1.2. This does not assert whole-pair coverage, intermediate patch boundaries, provider/shape equivalence, or runtime parity.