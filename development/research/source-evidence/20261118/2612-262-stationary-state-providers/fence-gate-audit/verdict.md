# Independent verdict: FenceGateBlock stationary collision, 26.1.2 → 26.2

Verdict: **AGREE**, bounded to the registered vanilla `FenceGateBlock` family and dry stationary collision shape over legal block states. No source-confirmed collision-shape delta is present in this pair. This is an operation/family verdict only; it does not assess a generic collision consumer or establish complete pair coverage/freeze.

## Immutable claim and audit identities

SHA-256 values were computed from raw file bytes. Author hashes match the exact handoff. The original independent audit files remain unchanged.

| Bound artifact | SHA-256 |
|---|---|
| Author `fence-gate-author/comparison.md` | `52f3199170b5b105f2d0a4185350e4daf6d6ef67f2d58e403221eae6ef1ec49e` |
| Author `fence-gate-author/source-roster.md` | `847cb53cadd58b0c8c4f437d2f324de1d2630fb5a837e5fe1b71274ecc1063ee` |
| Author `fence-gate-author/provenance.md` | `1e761b63ae439a2d5bb0f3c4ab385ed6ba77988578ee4b13c44edf3a53f76caa` |
| Frozen independent `fence-gate-audit/audit.md` | `d6d42e53a981d169a131799dcea4a1b718f8bde7b5b4f57ab57ff1efd29fa671` |
| Frozen independent `fence-gate-audit/source-roster.csv` | `77ca61575020ddbba4a16598b6278030a838391e230f0373158a005aa4265c526` |

The exact source roots are the canonical `decompiled_minecraft/26.1.2/unobfuscated` and `decompiled_minecraft/26.2/unobfuscated` trees. The author’s pinned source-manifest hashes were independently confirmed against raw bytes: `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` and `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`. All 11 Java paths in the author’s roster were re-hashed on both sides and each digest matched its source-manifest row. The author’s source roster and provenance identities agree with the frozen audit’s pinned pair.

## Scope and witness

The author’s family boundary matches the frozen audit: built-in fence-gate variants, legal static states, and the direct `FenceGateBlock` collision provider. The 12 registrations on both sides are oak, spruce, birch, jungle, acacia, cherry, dark oak, pale oak, mangrove, bamboo, crimson, and warped. Each uses the same movement-relevant property recipe: wood-plank map color, `forceSolidOn`, bass instrument, strength `(2.0F, 3.0F)`, and `ignitedByLava`; none sets `noCollision`, `noOcclusion`, or `dynamicShape`. Defaults therefore retain `hasCollision=true`, `canOcclude=true`, and `dynamicShape=false`. The subclass collision override directly selects the collision result and does not gate it through the base `hasCollision` flag. Its class-level collision-axis map is static/shared, while ordinary state caching is per state because dynamic shape is false.

`FACING` has four horizontal values (N/S on Z, E/W on X); `OPEN`, `POWERED`, and `IN_WALL` are Boolean. `StateDefinition` forms the Cartesian product, yielding 32 legal selector states per gate. For every state, `OPEN=true` returns empty. For `OPEN=false`, north/south returns local box `[0,1] × [0,1.5] × [3/8,5/8]`; east/west returns `[3/8,5/8] × [0,1.5] × [0,1]`. `POWERED` and `IN_WALL` do not affect collision. The outline, occlusion, support, placement, and neighbor-update providers remain distinct and outside this collision claim. The only `FenceGateBlock.java` source-text delta is equivalent switch syntax in pathfinding, outside this scope.

## Limits

This verdict does not accept or audit the generic `BlockCollisions`/`CollisionGetter` consumer changes mentioned in the author comparison, other gate families, water/pathfinding behavior, dynamic state production, surface/step effects, other versions, runtime parity, or full-pair coverage/freeze.