# Artifact path correction and reassessment

The original audit report incorrectly stated that `order-analysis.md` was absent because it checked under `development/research/.native-source`. The assignment-permitted artifact does exist at the repository-root path:

`.native-source/20261030/early18-pool34/1.8.1--1.8.2-entity-query-order/order-analysis.md`

I have now read that artifact. It hash-verifies the paired `TypeInstanceMultiMap`, `WorldChunk`, `World`, `Entity`, and `LivingEntity` sources against their provenance manifests and traces candidate order through the local-player collision query into `Entity.move`. It closes the ordinary entity-box **enumeration-order** question: for ordinary finite boxes, the paired axis clipping reduces independent constraints by min/max; the empty-section guard skips only an empty iteration; and `LivingEntity.pushAwayCollidingEntities` is behind `!world.isClient`.

This removes the named missing-artifact objection. It does not close the separate membership case where a removed old and new entity share an ID but have different collision shapes; the conditional wrap/replacement path remains as assessed in `report.md`. Nor does this bounded order analysis supply the pair-wide terminal role/method crosswalk or close the other open tick, state-update, custom modifier, and resource/tag dependencies listed there.

**Reassessed verdict: full-pair freeze remains unsupported.** The reason is the remaining pair-wide coverage and dependency gaps, not the location or absence of this order-analysis artifact. The original audit text is preserved unchanged; this note corrects its path statement and narrows that specific gap.
