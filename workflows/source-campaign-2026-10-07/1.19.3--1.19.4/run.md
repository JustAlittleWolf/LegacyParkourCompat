# Discovery: 1.19.3 to 1.19.4

- Status: active
- Scope: client player movement; older A = 1.19.3; newer B = 1.19.4. Health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations, and non-player movement are excluded; inspect direct vanilla-state consumers only when they gate player movement.
- Repository revision and start date: base `002137b227676caea77f6832b9f4c8d0b6200bff`; task branch `feat/source-discovery-movement-source-1-19-3-1-19-4`; started 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: Mojmap / Mojmap; both exact readiness records and source/artifact hashes verified. Member correspondence and comparison are pending.
- Source preparation owner / command / log / readiness marker: shared source owner. A `1.19.3` and B `1.19.4` Mojmap publications are validated below; both came from the same successful exact batch command and full log recorded at relative paths. This worker is read-only on shared generated sources and will not run decompilation.
- Toolchain/decompiler/remapper versions and options: verified Java 25.0.3+9-LTS; Gradle 9.7.1; Vineflower 1.12.0; TinyRemapper 0.14.1; ASM 9.10.1; Gson 2.14.0; Mapping IO 0.9.1; decompiler heap 4G.
- Discovery author(s): source-only worker for exact pair 1.19.3 to 1.19.4.
- Independent reviewer (must differ from discovery authors): not yet assigned.

## Artifact manifest

Shared artifact root, relative to the repository: `build/movement-campaign-2026-10-07/`. A: requested/resolved `1.19.3`, namespace/CLI `mojmap`; validated marker `ready/1.19.3/mojmap.ready.json` (`status=ready`, version metadata ID `1.19.3`, 4,618 source files, 22,430,993 source bytes). Source root `ready/1.19.3/mojmap/`; source manifest `ready/1.19.3/mojmap.sources.sha256`, SHA-256 `6f1cc6d07ab3902a7ea25a3f8817504723be70d82e97f541cffda95ea2c1c843`; all 4,618 listed source-file hashes checked, with zero missing or mismatched files. Artifact manifest `ready/1.19.3/artifacts.sha256`, SHA-256 `13e9e1a1458d0c6352207dac6edb36745427b012d30408e7cb42035b2f042652`; relevant `client.jar` SHA-256 `b7228c23dbc8988129561af3918dd469577de842d2eb3c7dabe00316bf9a44d6`, `client-mojmap.jar` `3be15cd54092cbac9853a67445a42b38d5c3b0d61503bd79b0d76589357d871e`, and `client_mappings.txt` `f72a4675ae77fa16966a0338567e30ee3a64bb7476932e264e8b25937f664a1b`; these five relevant artifact entries exist and match their manifest hashes. Readiness diagnostics `ready/1.19.3/movement-diagnostics.txt`, SHA-256 `95e8189acb6b384b21a584f28d5e8d23922996ea9f446e17e54734ccba840e60`; official metadata and required Entity/LivingEntity/Player/LocalPlayer source presence verified; diagnostics enumerate 21 movement method/caller locations. Provenance `ready/1.19.3/mojmap.provenance.json` gives exact batch command for 1.19.2, 1.19.3, 1.19.4; normalized command: `gradlew.bat decompileMinecraft --versions=1.19.2,1.19.3,1.19.4 --mappings=mojmap --decompiler-heap=4G`, with shared staging output and cache under this relative artifact root. Successful full log at `staging/mojmap-1.19.2-to-1.19.4-45d0418942a144298b19fb4ac74ff06a/gradle.full.log`; log reports requested/resolved 1.19.3 and BUILD SUCCESSFUL. Logged remapper access warnings are for GUI option classes; no player movement body damage is reported. Toolchain: Java 25.0.3+9-LTS, Gradle 9.7.1, Vineflower 1.12.0, TinyRemapper 0.14.1, ASM 9.10.1, Gson 2.14.0, Mapping IO 0.9.1; 4G decompiler heap. B: requested/resolved `1.19.4`, namespace/CLI `mojmap`; validated marker `ready/1.19.4/mojmap.ready.json` (`status=ready`, version metadata ID `1.19.4`, 4,740 source files, 23,021,785 source bytes). Source root `ready/1.19.4/mojmap/`; source manifest `ready/1.19.4/mojmap.sources.sha256`, SHA-256 `6286e325371e085dfee1ab7987e66d4b6d1a984a49b6ec8ea730045cd24cecb3`; all 4,740 listed source-file hashes checked, with zero missing or mismatched files. Artifact manifest `ready/1.19.4/artifacts.sha256`, SHA-256 `e84b8441615fd386ccdd3bfde71b7b1d658061e9383daffe41842e9acb7afb72`; relevant `client.jar` SHA-256 `0e79cf7f07c107e9a1fe22ed703e472372b44149e64114944f0811abbd25f3ec`, `client-mojmap.jar` `efbf38c89b396faae60cfe3bdb91671cb8d1ec3ccf2e27d3ffab4d261acd016d`, `client_mappings.txt` `5ef270b938f89cfc77371e0deee9f9044c41d9a373fa11dcb1fd1923272c4606`, and `version.json` `31c80f5545445fe8e09aae83cda8983988517b75a4ea9e9f359f8a2a17850c75`; shared `version_manifest_v2.json` SHA-256 `845dfb7f8b28ce06bf0752b597ef2d4d65df973e4b59d8d6ff429ad2d3adde04`. All five entries exist and match the artifact manifest. Readiness diagnostics `ready/1.19.4/movement-diagnostics.txt`, SHA-256 `61b7a69e1804684d0fc4414d5482d7233de5a7bfe64a19fb5a92571a7a69bcef`; official metadata and required Entity/LivingEntity/Player/LocalPlayer source presence verified; diagnostics enumerate 22 movement method/caller locations. The same successful batch log reports requested/resolved 1.19.4 and `BUILD SUCCESSFUL`, with no reported player-movement body damage. Exact A/B pair is now ready for comparison, but no behavioral comparison has yet been completed. Readiness JSON and source paths below are relative to this manifest through `../../../build/movement-campaign-2026-10-07/`. Do not commit generated sources, jars, raw diffs, caches or logs.

## Blind-discovery freeze

- Status: pending
- Freeze commit/checkpoint and timestamp: pending; freeze only after source-only coverage and findings are complete and audited for provenance.
- Evidence inventory and finding IDs included at freeze: none yet; no comparison has started.
- Confirmation that old mod implementation/code and isolated wiki-audit results were not opened before freeze (prior source-discovery reports may be used as navigation): confirmed; no mod implementation or wiki-audit outputs opened and no wiki browsing performed.
- Source/mapping hashes covered by freeze: pending readiness verification.

## Correspondence and call order

Pending exact source readiness. Resolve each logical role to fully qualified A/B class and member descriptor; record inheritance, callers, replacements/splits, body ranges and per-side hashes. Index complete local tick order (input sampling -> client/player tick -> pre-travel -> travel dispatch and every reachable branch -> post-travel) and each movement-state read/write edge. Include exact pose/dimension, velocity/box, collision/support/fluid, timers, equipment/effect and external-input producers and consumers. No guessed class/member correspondence.

## Required source inventories

Each inventory will map to bounded method/body-range slices with closed dependency links; stage labels alone are not evidence.

- `INV-TICK` input sampling, player tick/call graph, pre-travel, travel branches, post-travel: status=pending; slice_ids=I-TICK-1; evidence=source readiness pending
- `INV-STATE` movement state writers/readers including pose, dimensions, eye height, position, velocity, collision/ground/fluid flags, timers and direct predicates: status=pending; slice_ids=I-STATE-1; evidence=source readiness pending
- `INV-COLLISION` player collision/query path, shape providers, registrations, callbacks and neighboring-block dependencies: status=pending; slice_ids=I-COLLISION-1; evidence=source readiness pending
- `INV-WORLD-MOVEMENT` block/fluid movement properties, subclasses, registries, data/tags and resource-backed defaults: status=pending; slice_ids=I-WORLD-1; evidence=source readiness pending
- `INV-MODIFIERS` movement attributes, effects, enchantments, equipment and their applications/removals/conditions: status=pending; slice_ids=I-MODIFIER-1; evidence=source readiness pending
- `INV-EXTERNAL` player-only externally supplied movement inputs and client consumers, such as corrections, pushes, pistons and mounts: status=pending; slice_ids=I-EXTERNAL-1; evidence=source readiness pending
- `INV-EXCLUSIONS` explicit scope audit for health, regeneration, hunger, food, saturation, exhaustion, damage/combat simulations and non-player movement: status=pending; evidence=scope boundary recorded; direct vanilla-state consumers remain part of movement predicate inventory, excluded producer systems do not

## Coverage ledger

These initial rows are open inventory-discovery placeholders, not claimed bounded comparisons. Replace them with method/body-range units, parent/dependency edges, producers/consumers and explicit dispositions after both exact sources pass readiness verification.

### Slice I-TICK-1: input, complete local tick order and pre/travel/post movement inventory

- Inventory ID(s): INV-TICK
- Exact behavior boundary and enclosing guards/order checked: pending source correspondence and full call graph.
- A evidence: pending exact A readiness verification; no paired method/body compared.
- B evidence: pending exact B readiness verification; no paired method/body compared.
- State producers/writers -> consumers/readers: pending inventory of input, timers, movement flags, position and velocity.
- Parent slices / dependencies / closure evidence: none; both exact source pairs are verified.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started because exact source trees and body diagnostics are not yet verified.
- Finding IDs or checked absence/replacement path: none; not evidence of equivalence.

### Slice I-STATE-1: pose/dimensions and movement state-writer inventory

- Inventory ID(s): INV-STATE
- Exact behavior boundary and enclosing guards/order checked: pending constructor/default, update, reset and transition paths.
- A evidence: pending exact A readiness verification; no paired method/body compared.
- B evidence: pending exact B readiness verification; no paired method/body compared.
- State producers/writers -> consumers/readers: pending pose, dimensions, eye height, position, velocity, box, flags, support and timer inventory.
- Parent slices / dependencies / closure evidence: connect into I-TICK-1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started because exact source trees and body diagnostics are not yet verified.
- Finding IDs or checked absence/replacement path: none; not evidence of equivalence.

### Slice I-COLLISION-1: player collision/query and shape-provider inventory

- Inventory ID(s): INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pending query path, axis/step/support logic, callbacks and shape inputs.
- A evidence: pending exact A readiness verification; no paired method/body compared.
- B evidence: pending exact B readiness verification; no paired method/body compared.
- State producers/writers -> consumers/readers: pending dimensions/pose/support -> query bounds/context -> selected movement/callback state.
- Parent slices / dependencies / closure evidence: connect into I-STATE-1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started because exact source trees and body diagnostics are not yet verified.
- Finding IDs or checked absence/replacement path: none; not evidence of equivalence.

### Slice I-WORLD-1: movement-relevant block/fluid properties, registrations and resource inventory

- Inventory ID(s): INV-WORLD-MOVEMENT, INV-COLLISION
- Exact behavior boundary and enclosing guards/order checked: pending enumerating reachable shape/callback overrides, friction/speed/jump factors, fluids, registrations, neighboring-state dependencies and resource data.
- A evidence: pending exact A readiness and artifact/resource verification; no members compared.
- B evidence: pending exact B readiness and artifact/resource verification; no members compared.
- State producers/writers -> consumers/readers: pending registrations/properties/tags/neighbor state -> player queries, travel, callbacks and fluid push.
- Parent slices / dependencies / closure evidence: connect into I-COLLISION-1 and I-TICK-1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; modern-only behavior applicability will be checked only after exact registry evidence exists.
- Finding IDs or checked absence/replacement path: none; not evidence of equivalence.

### Slice I-MODIFIER-1: direct movement modifier consumer-to-producer inventory

- Inventory ID(s): INV-MODIFIERS
- Exact behavior boundary and enclosing guards/order checked: pending movement consumers, attribute aggregation, effects/enchantments, equipment/item-use predicates and conditions.
- A evidence: pending exact A readiness and relevant resource verification; no members compared.
- B evidence: pending exact B readiness and relevant resource verification; no members compared.
- State producers/writers -> consumers/readers: pending vanilla/default/server-synchronized movement values and item/effect applicability -> movement predicates and arithmetic consumers.
- Parent slices / dependencies / closure evidence: connect into I-TICK-1 and I-WORLD-1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; food/health producer systems are excluded even if vanilla values gate sprint.
- Finding IDs or checked absence/replacement path: none; not evidence of equivalence.

### Slice I-EXTERNAL-1: player-facing external input consumers

- Inventory ID(s): INV-EXTERNAL
- Exact behavior boundary and enclosing guards/order checked: pending client consumers of corrections, pushes, piston displacement, mounts/dismounts and launch items.
- A evidence: pending exact A readiness verification; no paired method/body compared.
- B evidence: pending exact B readiness verification; no paired method/body compared.
- State producers/writers -> consumers/readers: pending local client handling vs externally supplied velocity/position, player collision/push and mount state.
- Parent slices / dependencies / closure evidence: connect into I-TICK-1 and I-STATE-1.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): comparison not started; non-player independent simulation and damage/combat remain excluded.
- Finding IDs or checked absence/replacement path: none; not evidence of equivalence.

### Slice I-EXCLUSIONS-1: explicit excluded-system and player-only scope audit

- Inventory ID(s): INV-EXCLUSIONS
- Exact behavior boundary and enclosing guards/order checked: direct vanilla-state reads may be recorded only where a movement predicate consumes them; excluded producer behavior is not emulated.
- A evidence: scope applied; source consumer ranges await exact A source.
- B evidence: scope applied; source consumer ranges await exact B source.
- State producers/writers -> consumers/readers: separate excluded health/food/damage producers from allowed direct player-movement consumers; non-player movement excluded.
- Parent slices / dependencies / closure evidence: dependent direct consumers are listed in the corresponding tick/state/modifier slice.
- Status: pending
- Disposition and rationale (including concrete reachability/preconditions): scope is fixed by coordinator instructions; source-level direct-read inventory remains pending.
- Finding IDs or checked absence/replacement path: excluded producer systems themselves are not finding candidates; direct movement predicates still require source evidence.

## Dependency queue and blockers

- Open dependencies: D-3 (workflow tooling owner; completion checker parses every `- Status:` bullet, including required slice statuses, as top-level status and fails this report with `expected exactly one top-level status, found 10`; repair checker scope before final static gate). D-3 is a completion-checker tooling dependency being handled by the workflow owner; it does not prevent source comparison.

## Finding index

No comparison has started; zero confirmed findings. This does not imply no difference.

## Resume checkpoint

- Last completed slice: none; initial workflow, updated hardening docs, template, source navigation and buildSrc README read.
- Next bounded slice and exact files/members/body ranges to open: build the full paired class/member correspondence and reachable tick call graph from the two verified source trees; then compare bounded member/body slices beginning at local input and tick order.
- Outstanding dependencies and owners: D-3, workflow tooling owner.
- Current assumptions requiring verification: actual player class/member correspondence; complete per-stage writer/provider/registration/dependency inventories; cited body hashes; and resource/datapack provenance.

## Implementation reconciliation

- Reconciliation status: pending
- Repository revision inspected: deferred until source-only freeze; blind source worker has not read mod implementation.
- Finding -> implementation disposition/evidence: pending; implementation inspection is deferred until after source-only freeze and coordinator reassignment/authorization.
- Existing implementation without a frozen source finding: not inspected; preserve blind-discovery constraint.
- Coverage gaps routed back to discovery slices: source readiness dependency none; both exact source pairs are verified.

## Independent source audit

- Reviewer: not yet assigned; must not be a discovery author.
- Status: pending
- Inventories and call-chain ranges re-walked: none; comparison has not started.
- Concrete missed-slice routes (or `none found`): pending independent review.
- Misses routed to slice/finding IDs and owners: pending independent review.
- Reviewer evidence / date: pending.

## Source audit closure

- Coverage counts by status: pending 7; in-progress 0; compared-no-difference 0; findings 0; not-applicable 0; blocked 0. Explicit pending count: 7.
- Required inventory status and evidence: seven inventories are pending; no method/body evidence yet.
- Open dependencies: D-3 (workflow tooling owner).
- Unresolved gaps and limits: exact sources are not yet published at the canonical ready paths; no comparison has started.
- Evidence/hash/correspondence audit: pending readiness verification. Static completion checker was invoked and failed before source closure: `expected exactly one top-level status, found 10`; the checker counts slice `- Status:` fields as top-level. Source worker does not modify shared checker; see D-3.
- Blind freeze: pending
- Implementation reconciliation: pending
- Independent audit: pending
- Runtime validation: NOT AUTHORIZED; not performed, no tests/builds, clients, TAS, Gym, server or Docker launched.




