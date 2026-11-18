# Independent author review: 26.1 → 26.1.1

## Verdict

**Pass for the three bounded same-input movement contracts.** The author comparison's conclusion agrees with the frozen independent audit: honey slide eligibility and velocity/fall-distance writes, cobweb multiplier selection and next-move consumption, and sweet-berry-bush multiplier selection and next-move consumption have no source difference between these releases, conditional on equal contact/query inputs.

The contact and consumer sequence is consistent: block effects are applied after local living-entity travel; movement records are dispatched through the inside-block scan; an intersecting block callback runs before `onInsideBlock`; and a stored stuck multiplier is consumed by the next applicable non-piston `Entity.move`, after which the multiplier is cleared and delta movement is zeroed. Honey's callback writes its slide velocity and resets fall distance at contact. The cobweb weaving gate and berry living-entity/fox/bee gate, vectors, and common `makeStuckInBlock` writes match the frozen review.

## Scope limits

The equality assumptions for block states, geometry/support, contact path/order, and entity/effect/movement queries are properly treated as dependencies. This review does not validate collision geometry, query production, or every possible contact's spatial reachability. Berry server-side damage remains excluded. The source verdict establishes neither full-pair coverage nor implementation acceptance or runtime parity.

One minor attribution refinement: the cited `Entity.java:1259–1269` lines are inside `checkInsideBlocks`; `applyEffectsFromBlocks(List<Entity.Movement>)` reaches that dispatcher by delegation. The stated callback gate and ordering are accurate, and this wording does not affect the motion verdict.

## Reviewed revisions

- Author comparison SHA-256: `ae5993f3ec0fbb71b565536e7ac26c01a0ec71ab8c6775da19b41b05172c730b`.
- Frozen blind audit SHA-256: `548817dfecdf0446f5210085a6e629f4a58c7e3918b4c2bbce4c33f467fc9c43`.