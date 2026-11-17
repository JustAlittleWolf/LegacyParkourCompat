# Independent bounded audit: collision-list clipping caller dependency

**Verdict: ACCEPT** for the added caller-order dependency only, by reuse of the unchanged prior accepted stair geometry/order verdict.

## Immutable inputs and evidence

- Input: `build/source-189-19-20261117/input-travel/remaining-source-collision-review.md`, SHA-256 `0B12CD86FAEEF3B43D77071BC02A64896496D091839061C94D03E545BDEDCA47`.
- Reused claim: `build/source-189-19-20261117/provider-residual/stairs-clipping-order.md`, SHA-256 `459D7278DE596837FB96F5E447517B6C21CE754172943679424DCBDD1C4A92D6`.
- Reused independent verdict: `build/source-189-19-20261117/provider-residual/stairs-clipping-order-audit/verdict.md`, SHA-256 `8A072DA1124F30F6301E3EA07CF68DA4F19C88071BB6C744F5F1514AE7DC7FDD`.
- Exact source identity is unchanged: 1.8.9 Ornithe Feather manifest SHA-256 `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004`, provenance SHA-256 `ba066fc8f1c7ceb9819d2106338d9e9f2c2b96eb5a8329eb40e171b1f9889185`; 1.9 Ornithe Feather provenance SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- Rechecked caller files: 1.8.9 `World.java` SHA-256 `3C04F5B874FBB6692039164C882A3C47FE28C7ECA864E91C9AC57798153888EE`; 1.9 `World.java` SHA-256 `D1E23DB2E20C86EB37B964A0DF6838F1422ADE7B0C2FFF9C869FF8345979AA98`; 1.8.9 `Entity.java` SHA-256 `D4C10932CB5BB1067A5A58BE4E1BB1B42BDDE3B6FC893D88178CC07435A1696B`; 1.9 `Entity.java` SHA-256 `74D3AC34D7E6355C8FDCDD777DCD95BA9EF4626718C8772C1B58D0E28419E42A`.

## Audited dependency

In both versions, ordinary `Entity.move` obtains its collision list from `this.world.getCollisions(this, expandedShape)`. `World.getCollisions` appends block-provider collisions to that same list as the scan proceeds (`1.8.9 World.java:891–922`; `1.9 World.java:893–928`). The previously accepted stair audit establishes the bounded provider-local box sequence and that intersecting boxes are appended in that order.

The ordinary movement path iterates the returned list in sequence for Y clipping, moves the box, then iterates the same list for X and Z (`1.8.9 Entity.java:439–459`; `1.9 Entity.java:506–529`). If step-up is eligible, each release independently calls the same `this.world.getCollisions` path for its step query and consumes that new list sequentially in Y-X-Z order for both alternatives; it then clips downward and selects the result (`1.8.9 Entity.java:460–533`; `1.9 Entity.java:530–611`). This confirms the claimed consumer order for provider-appended stair pieces on each query.

## Limits

The caller-order dependency duplicates the previously accepted verdict and needs no changed stair-order claim. This does not assert equal complete collision-list membership or global order: scan bounds and pruning differ between releases. No trajectory, runtime, getter-context, stale-cache, pane/piston, or full-pair coverage claim is made. The separate state-getter checks and Anvil/Chest/Stem mutable-cache lifecycle are outside this narrowed audit.
