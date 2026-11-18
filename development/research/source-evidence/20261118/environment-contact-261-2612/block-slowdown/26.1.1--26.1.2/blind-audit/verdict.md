# Independent author review verdict

**Reviewed author draft SHA-256:** `ee5594651788e111a6f0b680a3a76e673676e2dde68e833f8670b5dbe0c7388f` (verified).  
**Bound frozen audit SHA-256:** `17886d08b0cd14e4891ceb8927460c910efb41f3609ceef1f0660bfcf41703e6` (verified; preserved).  
**Scope:** the three specified ordinary-player contact operations, local tick dispatch, stored slowdown consumption, and the source/provenance and equal-input limits.

## Verdict

The bounded conclusion that these paths have no 26.1.1→26.1.2 source difference is supported by the independently frozen evidence: the compared dispatch, player override, producer and consumer classes have matching content hashes on both releases and match their local source-manifest entries. The author draft is **not acceptable verbatim yet**: one honey-velocity sentence is materially inaccurate, and its stated precondition is not sufficient to justify all producer predicates. Preserve the freeze; correct only the author draft before treating its detailed movement description and scope as accepted.

## Required corrections and residuals

1. **Honey horizontal velocity is conditional.** In `HoneyBlock.doSlideMovement`, X/Z are scaled by `-0.05 / getOldDeltaY(...)` only when reconstructed old Y is below `-0.13`; in the other branch X/Z are retained. Both branches write the same new Y and then reset fall distance. The draft's phrase that it writes the same horizontal reduction “in either branch” is false. State the branch distinction. The cited gate range should also include the initial on-ground and feet-height checks (`HoneyBlock.java:89-109`); the slide write/reset is at `:114-124`.

2. **Make the effective input explicit.** Same contact geometry, support and queried block state alone do not ensure identical branch outcomes: honey reads on-ground, feet position and current vertical velocity; the horizontal write reads current X/Z velocity; cobweb selection reads living-entity/Weaving state; and the player override reads `abilities.flying`. State equal pre-contact player movement state and relevant abilities/effects/block state alongside equal geometry and query results. For the pending cobweb/berry multiplier trace, qualify the Player as non-flying: `Player.makeStuckInBlock` delegates to `Entity.makeStuckInBlock` only when `!abilities.flying`. The two releases remain equivalent for flying players too, but those contacts do not store that slowdown vector through the Player override.

3. **Keep the provenance boundary clear.** The author draft correctly verifies local ready/source/artifact manifest pins and says shape/provider equivalence is not asserted. The 26.1.2 provenance record is less complete than 26.1.1: its local manifests pin content hashes, but the provenance object omits upstream URLs and metadata/client origin hashes. The canonical source roots are linked to ready records by matching whole-source-manifest hashes because those records point to dated staging/output paths. Keep the finding explicitly about locally pinned source content and avoid implying independent upstream-origin authentication.

4. **Limit the result to the checked slice.** Its final exclusions are appropriate: no whole-pair coverage, intermediate patch boundary, shape/provider equivalence, or runtime parity follows from identical selected source files. The unchanged-geometry and equal-query condition must remain part of the conclusion.

No source disagreement with the frozen no-difference verdict was found. No movement change should be raised for this bounded slice. This review binds to the exact author and freeze revisions above; it does not alter or renew a broader pair-coverage verdict.