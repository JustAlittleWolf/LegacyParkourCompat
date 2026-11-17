# Independent canonical finding audit — creative-flight fall-distance edge backoff

**Verdict: ACCEPT**

Audited immutable canonical finding `development/research/findings/1.20.6--1.21.11/creative-flight-fall-distance-edge-backoff.md`, SHA-256 `EA7B6DDE0009DE86F7AB8BFE6DB55F7A06AE0C5595AB3707D9B89E8DFD1BB616`.

## Source identity and independent retrace

The exact sources are the canonical Mojmap trees. The 1.21.1 ready marker identifies version `1.21.1` / mapping `mojmap`; its source manifest SHA-256 is `900f956e00f6fc1300bb3d689ea49df2b1a57bcaa54617344ef456d95d47cb48`, and the manifest entries bind `Player.java` to `ed32b88c3c7c8418b83db41823520f2b6b0b49a98610306ef26e9681dc925c71` and `Entity.java` to `b81905c7879e2cc5c5063a41d865c4164ad919f156306705be791d1017b99850`. The 1.21.2 mapping-local provenance file SHA-256 is `00f399407dc447737ea83e3e46562418f8c712f66e3f51cb85d46afa2ecac575`; its source entries bind `Player.java` to `a803203e92aa4729d5f5c9b16085b6a43ce51d9907d309eb96736e9c7c1340de` and `Entity.java` to `a93719c302a0381a972af75ea360465e2e3551708dd07c34d4d40b7e5173c2b9`.

## Claim audit

The 1.21.1 `Player.aiStep` reaches `super.aiStep()` at line 534 without a creative-flight fall reset. In `Player.travel` lines 1468–1474, its nonpassenger flying branch calls inherited travel, restores damped vertical velocity, then calls `resetFallDistance()`. The 1.21.2 `Player.aiStep` lines 551–555 instead resets fall distance for a flying nonpassenger before inherited movement; `Player.travel` lines 1460–1465 has no corresponding post-travel reset. The finding's adjacent reset-order change and source anchors are accurate.

`Entity.checkFallDamage` adds the magnitude of negative vertical movement to `fallDistance` when the move is not on ground (`Entity.java:1163–1178` in 1.21.1; `1216–1231` in 1.21.2); `Entity.move` calls this method in both versions. Thus, following the pre-reset on 1.21.2, an unobstructed `-0.15` vertical move leaves `0.15` fall distance, while the 1.21.1 post-travel reset leaves zero. The finding's conditioned 0.15 descent is supported.

The predicate anchors are accurate: `Player.isAboveGround` uses `fallDistance < maxUpStep` and calls `canFallAtLeast(maxUpStep - fallDistance)`; `canFallAtLeast` constructs the downward AABB with the `1.0E-5F` offset (`1.21.1 Player.java:1121–1127`; `1.21.2 Player.java:1117–1124`). For the stated feet height `0.525` and `maxUpStep=0.6`, the old zero-distance probe reaches `-0.07501`; with retained `0.15` distance the new `0.45` probe reaches `+0.07499`. With the described support edge and collision assumptions, the old query intersects the support and the new query does not. `maybeBackOffFromEdge` in both versions gates the behavior on non-flying, non-positive-Y SELF/PLAYER movement, staying-on-ground-surface state, and `isAboveGround`; its horizontal reduction loop zeros a residual request at or below `0.05`. The stated consequence for a sufficiently small eligible horizontal SELF move follows from these source facts.

The line ranges in the canonical finding cover the cited methods and relevant operations. No unsupported change to the edge predicate itself is asserted; the difference is its fall-distance input after the preceding creative-flight tick. I found no material source or movement-meaning error in the narrowed claim.

## Binding and limits

This verdict is bound only to the exact canonical finding SHA above and the source identities recorded here. For source-report lineage, the prior accepted evidence packet is `build/later-source-20261116/1.20.6--1.21.11/fall_distance_boundary_revision_r6/coverage-r6.md`, SHA-256 `6B27F6AC61A78949DE98D2C0D7E7626B7F0E559994D2D27C47CF69CCB9B56BD4`; its prior independent audit is `build/later-source-20261116/1.20.6--1.21.11/fall_distance_boundary_r6_audit/audit-r6.md`, SHA-256 `57C1268D3E7BE95BA02B4A693B944F4018753738169C02316CBC86E548312173`. This is an independent retrace of the canonical text, not a pair-freeze or full `1.20.6→1.21.11` coverage claim, runtime result, or verification of other release boundaries.
