# Core and resource residual closure

Scope: changed paths with role `other` under `net/minecraft/core/**` and `net/minecraft/resources/**` across 1.18.2→1.19→1.19.1→1.19.2→1.19.3→1.19.4. Exact path roster input `.native-source/20261030/1182-1194-inventory44/changed-paths.tsv` SHA-256 `FC00141CB9C9ED40AA24B59429DFBB4DB7AEDE3C9657AF82F55E429C59511606`; role roster `changed-path-roles.csv` SHA-256 `8A3F647F94F5B9A556537C0592A32DB9FAB34AAB87F35E71D85F16E2BFE041EF`.

## Coverage and source identity

There are 72 changed edge/path rows across 41 unique `net/minecraft/core` paths in role `other`; 63 rows across 36 paths remain after excluding the already closed math/geometry helpers `BlockPos`, `Direction`, `Direction8`, `SectionPos`, and `Vec3i`. There are no `net/minecraft/resources/**` rows with role `other`; resource paths are classed under the separate `registry/resources` role and their movement-relevant tag/registry dependencies were covered by the earlier source report.

All six `mojmap.ready.json` markers report `mapping=mojmap`, `status=ready`; each manifest’s line count and SHA-256 matches its ready marker. The source path hashes for all 63 selected edge/path rows were recomputed against the corresponding old/new source files and match the frozen roster (zero missing or hash mismatches). Exact version identities are in `source-identities.tsv`; row-level path, endpoint source hashes, and dispositions are in `path-dispositions.tsv`.

## Changed-member route disposition

- `BlockMath`: UV/model transform math used by the block-model renderer. No ordinary player movement call.
- `CauldronInteraction`: cauldron item/block interaction path; changes concern interaction and world/item state production. No direct movement velocity, position, input, or movement predicate consumer.
- `core/dispenser/**`: automated block dispenser behavior (including boat and shears providers); affects dispenser-produced block/item/entity state and non-player entity behavior, not a local player's movement tick.
- `core/particles/**`: particle option codecs and registrations; consumers create/render particles, so any changed member is visual metadata/output.
- `SerializableUUID` and `UUIDUtil`: UUID serialization/parsing helpers. Search of the exact player movement and attribute/effect source roots found no use from movement modifier application or direct player movement reads/writes; the changed UUID consumers are entity/AI/data persistence and identity paths.
- Remaining `Holder*`, `Registry*`, and defaulted/mapped registry classes: holder lookup, bootstrap, codec, default-value, and synchronization infrastructure. The ordinary movement consumers query already resolved blocks, fluids, tags, attributes, and effects. Under the assigned equal resolved vanilla states/memberships assumption, these infrastructure changes do not change the movement operation or its operands. The exact tag IDs and vanilla generated movement-tag membership were separately closed in `vanilla-movement-tag-membership.md`.

No movement candidate was found in this bounded core/resource slice. This is not a full-pair freeze and does not classify other changed roles outside this directory/role intersection.

## Artifact hashes

- `path-dispositions.tsv`: SHA-256 `899022DD327311F15080C16D01642026A60F123F34B6CBE69E51672B5E3339D2`
- `source-identities.tsv`: SHA-256 `FC0B8E64F0EB7C56D8F40C9068264E0B49B0F4CBE814CEEB5B2E50DAEF3989A1`

## Changed-member inspection

The earlier path dispositions are now backed by exact version-pair source diffs. `changed-lines-review.txt` contains the `git diff --no-index --function-context` hunk context and all added/removed source lines for each of the 63 selected edge/path rows (including rows with no behavior delta beyond declarations/imports). Its headings bind each hunk group to its exact adjacent version edge and Mojmap source path; `path-dispositions.tsv` binds the same rows to old/new source SHA-256 values. This captures changed member signatures and changed statements without copying unchanged file bodies. Relevant changed members are registry/holder API, registration, lookup, freeze, and lifecycle methods; dispenser behavior registration/execution methods; particle option codec/serializer/registration methods; `BlockMath` transforms; cauldron bucket interaction overload calls; and UUID conversion helpers. Review confirms no changed member in this slice is a direct ordinary local-player movement response, predicate, input, position, or velocity sink. Registry/holder and particle/block-state codec changes are classified only under the report's equal-resolved-vanilla-state/tag-membership boundary; dispenser and cauldron changes are producer/interaction paths; UUID helpers are serialization/identity paths.

The exact Mojmap source root is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\<version>\mojmap\`; all six source identities are pinned in `source-identities.tsv`. No `net/minecraft/resources/**` path has role `other`.
