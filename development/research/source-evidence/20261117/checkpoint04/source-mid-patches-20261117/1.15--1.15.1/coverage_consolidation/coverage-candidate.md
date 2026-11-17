# Source coverage consolidation candidate: 1.15 → 1.15.1

Status: **COMPLETE SOURCE-COVERAGE CANDIDATE; pending fresh independent full-pair coverage audit.** This consolidates the latest exact-pair Java inventory and the separately accepted resource membership packet. It is not yet a pair freeze and makes no product applicability or runtime-parity claim.

## Exact source identities

- Older: 1.15 Mojmap, root `decompiled_minecraft/1.15/mojmap/`; source manifest SHA-256 `251a3019ccc3fd067f66365f3545b69a983383a28b19a1038debf1791be29340`; provenance SHA-256 `de4a538ebbaf82d1615683b08ecae3cd76553345f3079436764eb095be343949`.
- Newer: 1.15.1 Mojmap, root `decompiled_minecraft/1.15.1/mojmap/`; source manifest SHA-256 `c8fdce2f9432b72fc13c19a48f9ab3be203cac41a617139e81bac3552245912d`; provenance SHA-256 `6a7af1f5d65d7af165c2ab9a90c1fae20169b38e70c8e92dbc5f59d94efe9461`.
- The accepted primary report states both manifests contain 3,329 Java paths with no additions/removals and 12 changed-path hashes, all individually dispositioned. Its 48-row paired operation/provider/callback/effect/tag/response ledger has zero manifest or on-disk mismatches.

## Bound accepted evidence

1. **Primary exact-pair Java inventory and movement call graph.** `ordinary_player_revision_r4/coverage-r4.md`, SHA-256 `2C1E7DE1FDE2838A4B64037E4D51207686F2D41D75D44D1DEFEE514818B9907B`; source hash ledger `ordinary_player_revision_r4/source-hashes.tsv`, SHA-256 `2D42123F13239AC2C949F7C3D5B2DD50942F708D872A285B984AB3C972CD07DF`.
   - The report covers local-player input/tick/send-position, Player overrides and direct predicates, LivingEntity tick/jump/travel/fluid/climb/fall-flying, Entity movement/position/velocity/callback kernels, collision enumeration and voxel response, selected movement-relevant block providers, and movement effects/equipment/attributes.
   - It accounts for all 12 changed Java paths. The source-derived ItemStack damage normalization leaves the checked Elytra eligibility result unchanged for the stated legal/negative damage cases; Block's changed occlusion cache is rendering-only; AbstractStateHolder identity/hash behavior does not alter the checked CrossCollisionBlock state lookup result. No player movement delta was identified.
   - Independent r4 audit `ordinary_player_r4_audit/verdict.md`, SHA-256 `72F721FCFA1E079E0055D4A007C300C32DB66C1E0ABA35C804FFA8112287462F`, accepts the corrected r4 revision reference as wording-only and reuses the unchanged technical checks for source identities, census, 48-row ledger, and Player override routing. Its bound r3 audit history records the added `Player.jumpFromGround()` and `Player.travel(Vec3)` dynamic dispatch coverage; the r4 edit changed only stale revision wording.
2. **Movement-relevant default resource membership.** `resource_gate/input-reference.json`, SHA-256 `7dfc948b8b96eac2598855b320e1f97cd0cc796c83a955aaa13c5c3820baa692`; independent audit `resource_gate/audit/verdict.md`, SHA-256 `A596C9E67F13EDC4E4419142B928B1664C1C8A7B585B175E5FBE3C0B53380B1D`.
   - The resource gate independently verifies both official pinned client JARs and all fourteen allowlisted archive entries (seven tags at each endpoint), including exact bytes, payloads, and equal membership: `minecraft:water`, `minecraft:lava`, `minecraft:fences`, `minecraft:walls`, `minecraft:leaves`, `minecraft:portals`, and `minecraft:shulker_boxes`.
   - The packet records the source-manifest-to-artifact provenance limit. No arbitrary datapack overrides or live-world tag state are claimed.

## Coverage conclusion and audit request

The accepted Java inventory covers the exact endpoint source census, movement tick/travel/collision/response call graph, relevant state providers and modifiers, and all changed Java paths. The accepted resource packet closes the previously open packaged membership gate for the movement-related tag identifiers the source report enumerated. Taken together, these exact-pair source packets provide a complete source-coverage candidate with no movement difference identified in the examined pair.

Request a fresh blind independent **full-pair source-coverage audit** of this consolidation and its bound reports, ledgers, and verdicts. The auditor should reuse the exact bounded verdicts above for unchanged claims, verify the bindings and that the source report's residual resource gate is precisely closed by the accepted packet, and return ACCEPT only if no exact-pair source operation/dependency remains unexamined. Keep pair status PARTIAL until that audit accepts the full coverage candidate.

Not claimed: source-difference onset within the interval, datapack override behavior, product applicability, implementation behavior, server parity, or runtime parity.
