# Full-pair source closure audit: 1.8.4 → 1.8.5

## Verdict

**Recommend conditional full-pair source freeze for the fixed-profile client movement inventory**, with equal received status-effect state, equal received movement-attribute state, and equal movement-relevant world/player state as explicit input conditions. The supplied whole-tree audit already established complete paired Java path/hash inventory and unchanged movement-root coverage; this audit retraced its changed-edge dispositions and used the newer closure evidence to close the Beacon, received-state, and default packaged-resource edges. Under those equal-input conditions, no additional 1.8.4 → 1.8.5 client movement difference remains outside the separately accepted `EntityAttributes.java` operation.

Preserve `EntityAttributes.java` as its own accepted movement-speed attribute-cap finding. Its exact changed bound is `Double.MAX_VALUE` in 1.8.4 to `1024.0` in 1.8.5 for `generic.movementSpeed`, with base `0.7F` and lower bound `0.0` unchanged. It is not part of the Beacon edge, and equal received movement-attribute state here does not revise, absorb, or reject that accepted finding. Freeze records the complete pair inventory including that distinct operation; it does not assert parity when the effective movement attributes differ.

This is a source/effective-input closure only. It does not claim equal outcomes when Beacon behavior produces different server-authoritative received state, runtime parity, or an exact intermediate introduction boundary.

## Inputs and immutable identities

Exact read-only sources: `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/1.8.4/ornithe-feather/` and `.../1.8.5/ornithe-feather/`, both Ornithe Feather Gen2 build 2. Current provenance file SHA-256s match the reported identities: 1.8.4 `b2207bf9ab2a7561bf73ac92a63cc67760ccc6fd9e2378ff1e10710ecbb03a00`; 1.8.5 `986d25dfd11927abed51c92643e5beda48237da875fbcdf1a48a930456ea99ce`.

The assigned source report's complete maps contain 1,612 Java paths per release, matching path sets with 1,595 equal and 17 differing source hashes. I independently reconfirmed these current manifest counts and pairwise hash-map totals. The earlier independent ordinary-movement audit reports zero disk/map omissions, extras, or content mismatches and verifies all 17 changed paths against the CSV. The Beacon closure and resource reports independently bind their cached JARs to those manifests/pins and report the full packaged-entry comparison.

Assigned report/input SHA-256s:

| Input | SHA-256 |
|---|---|
| `184-185-ordinary-movement/report.md` | `56103dee13e9bc5e6e56775beec20d9d7841bbf76bf9699414fb54338502bbcc` |
| `184-185-ordinary-movement/changed-source-paths.csv` | `2408d2f8ee64b0c4063eff54ccaa1fdcb4ddb9ec1907a8ec1f9026fd19c8cabb` |
| `184-185-ordinary-movement/SHA256SUMS.txt` | `3db1b3d231d57f014b829f463f95f17878afcc2f608e1ce63d6f1e090b88a1c0` |
| `184-185-ordinary-movement-audit/audit.md` | `fb88518c29a91ed0c203b75db9a35b8ee25ce66fea36e826596f421e1836014c` |
| `184-185-same-input-closure/report.md` | `9cce308ea50f348e52a720b3612bb9c2a2a47c771497c2bed2feca1a4d2d1f18` |
| `184-185-same-input-closure-review/review.md` | `826d6c91965e8ba65bcdd803a21d1e7b09414aadfea3c76411f325663d3510cf` |
| `184-185-same-input-closure-review/correction-supplement.md` | `51163140e095cb583b94d9e0991d8bf5c0f5f232a8db9a3b00dcc7c1aaae16d6` |
| `worker-184-185-beacon-status-resources/report.md` | `3c4171bcebc834451e7b86b7629a2746729b8b4e0c75a4d4c5396eb910f806c5` |

## Reconstructed movement inventory and terminal coverage

- **Tick roots and ordering:** `Minecraft` has no tick hunk; `LocalClientPlayerEntity`, `ClientPlayerEntity`, `Input`, and `KeyboardInput` are hash-identical. `Entity`, `LivingEntity`, `PlayerEntity`, abilities/inventory, `World`, and `ClientWorld` are identical. The accepted original inventory covers local input snapshots, sprint/flight/jump predicates, tick dispatch, travel/jump, post-travel state and position/velocity/collision-flag/dimension writers. Reused terminal slices therefore have no source delta in this pair.
- **Collision and movement environment:** all block provider sources are identical. `Block`, `Blocks`, block states, box/vector math, liquids, climbables, slime, ice, soul sand and cobweb providers are unchanged; changed `BlockItem` and block-entity paths do not alter collision shape providers. Reused inventory covers collision queries, callbacks, fluids and world/chunk/border dependencies. No changed collision or shape terminal remains.
- **Status/effect dependency:** `StatusEffect`, `StatusEffectInstance`, living/player consumers, client packet handler and status packet are unchanged. Changed `BeaconBlockEntity` filters unsupported IDs in 1.8.5 `readNbt` and `setData`; 1.8.4 stores them raw. The normal six-effect UI cannot choose Slowness ID 2, but the unchanged `MC|Beacon` server custom-payload handler forwards raw IDs to `setData` under menu/payment gates. Thus this is a concrete server-side producer difference for a nonconforming payload, not client movement-loop math. Slowness's unchanged ID 2 modifier is `-0.15F` to `MOVEMENT_SPEED`. The client status callback does not install the modifier client-side; authoritative attributes arrive separately. With equal received status and movement-attribute state and equal movement-relevant world/player state, no Beacon-specific client movement difference remains. Unequal server-produced/received state remains outside this same-input closure.
- **Separate attribute operation:** `EntityAttributes.java` is one of the 17 changed paths. Its only movement-related behavior here is the movement-speed upper cap change from `Double.MAX_VALUE` to `1024.0`; preserve its separately accepted finding and status. It is not re-audited or merged with Beacon. The other changed paths are fully listed in the assigned inventory and concern version/UI/telemetry metadata, sign and command-block NBT/permissions, server packet validation/inventory sanitation, or scoreboard names; no additional player movement terminal was found.
- **Packaged resources:** the closure review reports 5,597 non-directory entries per provenance-matched client JAR, equal entry paths, and 43 content differences. The differing non-class entries are manifest/signatures and `assets/minecraft/texts/splashes.txt`; no packaged movement-default, data/registry JSON, or movement resource differs. This closes default packaged-resource inputs only. User packs, world data, and server-produced state are outside it.

## Gaps and queue

No remaining pair-local source edge is identified for the stated equal-input domain. Keep these boundaries visible in the frozen record: server-produced unequal Beacon/status/attribute state is not equal input; user-installed resources and world data were not compared; runtime parity is untested; and the separate movement-speed cap finding retains its existing acceptance and applicability evidence without being decided by this Beacon audit. The earlier audit's outside-pair 1.8.3 → 1.8.4 boundary questions are not reopened or decided here.

**Bounded queue:** freeze this pair's source inventory conditionally on equal received status, equal received movement attributes, and equal movement-relevant world/player state; preserve the distinct accepted `EntityAttributes.java` cap operation and retain unequal-input/runtime claims as open. No further 1.8.4 → 1.8.5 source sweep is indicated by the supplied evidence.

## Audit bounds

This review used only the assigned reports and exact 1.8.4/1.8.5 source roots. It did not inspect product sources/history, wiki material, backlog, or other lanes; it did not decompile, download, build, test, or launch. Full path-to-disk hashing and packaged archive enumeration are evidenced by the assigned independent reports and their cited source/resource checks; this audit independently checked current manifest provenance hashes, paired source-map counts/hashes, the two `EntityAttributes.java` bodies/hashes, and all assigned report digests.
