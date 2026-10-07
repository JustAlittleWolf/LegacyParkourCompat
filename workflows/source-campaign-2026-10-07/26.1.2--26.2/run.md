# Discovery: 26.1.2 to 26.2

- Status: active
- Scope: source-only client player movement; older A = 26.1.2; newer B = 26.2. No runtime Java implementation, wiki browsing, release-notes mechanics, gameplay validation, tests, client/TAS/Gym/server/Docker launches.
- Repository revision and start date: task worktree created at `002137b227676caea77f6832b9f4c8d0b6200bff` (main); discovery branch `feat/source-discovery-movement-source-26-1-2-26-2`; 2026-10-07.
- Selected naming namespace, CLI mode per side and alignment evidence: intended official names; A 26.1.2 native `unobfuscated` request pending source-owner publication; B 26.2 native `unobfuscated`, verified `versionId=26.2` and ready marker. Both are expected in Mojang official names; direct pair alignment still awaits A marker verification, and comparison has not begun.
- Source preparation command and log: B command/log not included in ready JSON; movement method diagnostics at `build/movement-campaign-2026-10-07/ready/26.2/movement-diagnostics.txt`. A source owner was asked to publish exact 26.1.2 Mojmap and provide the readiness JSON. No decompiler was run by this worker.
- Toolchain/decompiler/remapper versions and options: B readiness record says Vineflower, Java runtime 25; exact decompiler build/options not recorded in marker and pending source-owner provenance. Native unobfuscated B uses original client jar; mappings/remapped jar are not applicable. A pending.

## Artifact manifest

### A — 26.1.2

- Source root, exact jar identity/hash, manifests and diagnostics: pending readiness publication. Earlier pair report indicates native `unobfuscated`; this is only a namespace navigation aid, not current artifact evidence. Mapping/remapped jar should be not applicable only after the current marker confirms published unobfuscated. Do not use unproven historical source trees as comparison evidence.

### B — 26.2

- Exact release: readiness JSON `ready/26.2/unobfuscated.ready.json`, `versionId=26.2`, status `ready`; source root `build/movement-campaign-2026-10-07/ready/26.2/unobfuscated/`; native `unobfuscated` namespace; Java runtime 25; Vineflower.
- Original client jar: `build/movement-campaign-2026-10-07/artifacts/26.2/client.jar`, SHA-256 `40896ee9f1e2bec3c934daac7e93d41e9e3d9c2f8ae0ca366d52ffbfd1afa290`; version metadata id 26.2. Mappings and remapped jar: not applicable, published unobfuscated release, original client jar is decompiler input.
- Source manifest: `ready/26.2/unobfuscated.sources.sha256`, SHA-256 `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894`; 7,055 source files, 33,813,717 bytes per ready JSON.
- Artifact manifest: `ready/26.2/artifacts.sha256`, SHA-256 `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba`.
- Movement diagnostics: `ready/26.2/movement-diagnostics.txt`, SHA-256 `ba6fd6c5b1c77b3ee2988f0c54968cc5b915680670fdb66699dbcbf8b9fe21d3`; contains decompiled line anchors for Entity.move/moveRelative, LivingEntity travel/jump paths, Player.travel and LocalPlayer.aiStep. This confirms anchor presence, not pair coverage or absence of other damaged methods.
- Readiness JSON cites the source/artifact/diagnostic manifest hashes above; each was recomputed and matched. Direct B source hashes also matched the source manifest: `net/minecraft/client/player/LocalPlayer.java` `8d089aa09217e3607b38590f7c1623385562800943ac6dfd3d17804e041da6d6`; `KeyboardInput.java` `b4bbb410650444c30d2a62d70fd3c6cd1aefa104b8a12e474e2a478a62089c39`; `world/entity/LivingEntity.java` `7ffd9c70966edc50c9cb4d9a8fe17a518e2678ff44c8026e763d0b94ac0ae51a`; `world/entity/Entity.java` `7afb9c1294893ffe73e3b1acffcad41c648f15de8378bff3dffaff869bb811d5`. Additional source/resource hashes must be recorded when cited.

## Correspondence and call order

Pair-specific correspondence has not started because the A source tree is not yet published. B-only navigation seeds, not resolved correspondence: `KeyboardInput`; `LocalPlayer.aiStep`; inherited `LivingEntity.aiStep`, `travel`, `jumpFromGround`; `Entity.move`, collision helpers and bounding-box/shape/fluid code. Re-resolve A and B descriptors, inheritance, callers and full method bodies after A is ready. No guessed cross-version names are recorded as correspondence.

## Coverage ledger

All comparison slices remain pending. These stage rows are navigation queues, not claims of exhaustive method inventory. Expand each into bounded, evidence-backed units and dependencies as the exact A/B trees are indexed.

- S1 / local input and tick ordering: `pending`; input sampling, keyboard conversion, tick/super-tick/travel order, sprint/jump/flight/riding/auto-jump gates and their timing; A evidence pending, B anchors present but not compared; dependencies unindexed.
- S2 / player state and gates: `pending`; pose/dimensions/eye height, swimming/crawling, abilities, sprint gates, item use and stored air speed; pair inventory pending.
- S3 / living movement integration: `pending`; ground/air/fluid/glide travel, pre/post-travel updates, jump impulses, climbing, velocity thresholds and exact arithmetic; pair inventory pending.
- S4 / entity movement and collision: `pending`; move/collision, axis/step candidates and ties, edge/support probes, callbacks, shape/AABB queries, fluid contact and velocity cancellation; pair inventory pending.
- S5 / blocks and fluids: `pending`; all registered movement-relevant shapes/properties/callbacks/providers/neighbors, fluids and resource-backed inputs; registries/resources not indexed.
- S6 / effects, attributes, enchantments and equipment: `pending`; consumer-to-registration/application/removal/data chains; no health, regeneration, hunger/food, exhaustion, damage or combat emulation (vanilla state consumers only).
- S7 / external influences and dependency closure: `pending`; client velocity/position packet consumers, pushes/explosions/pistons/launch items and remaining state writers; pair inventory pending.

## Dependency queue and blockers

- D-A-SOURCE: exact 26.1.2 `unobfuscated` ready publication and readiness JSON from source owner. Required to establish direct official-name alignment, exact release, artifact provenance and begin every paired slice. Status pending; request sent in this chat commentary; no substitute source used.
- D-B-PROVENANCE: resolved decompiler/remapper/options/log provenance for 26.2 if required beyond the validated marker; anchor file hashes and readiness manifests have been verified. Request only if later source review identifies relevant body-diagnostic ambiguity.
- Resource-backed registrations/tags/defaults and jar resource provenance on both versions: not inventoried yet; queue during stages 5-6.

## Finding index

No pairwise candidates reviewed and no finding files yet. Zero findings at this checkpoint does not mean equivalence. Findings/exclusions will be added only after paired source evidence and dependency closure.

## Resume checkpoint

- Last completed slice: none; verified B readiness/provenance only.
- Next bounded slice: validate A readiness JSON and exact IDs/hashes; establish correspondence and input/tick ordering for `KeyboardInput` -> local-player tick/aiStep -> inherited travel, including full caller/callee bodies on both sides.
- Outstanding dependencies: D-A-SOURCE; decompiler/body diagnostics; all paired coverage units.
- Current assumptions requiring verification: 26.1.2 current ready marker confirms native unobfuscated; native-unobfuscated 26.2 source bodies used in final claims remain intact. No release introduction point can be inferred from this endpoint pair.

## Source audit closure

- Coverage counts by status: 7 coarse stage rows pending; 0 in-progress, compared-no-difference, findings, not-applicable, or resolved blocked rows. Granular method-level coverage is not yet inventoried; explicit pending count will be updated before closure.
- Unresolved gaps and limits: comparison has not begun; A source absent from ready tree at checkpoint; B-only inventory is navigation preparation, not evidence of equivalence. Health/regeneration/hunger/food/saturation/exhaustion/damage/combat are excluded even when indirectly touching sprint gates; check vanilla-state consumers only. Modern-only blocks/features do not gain historical behavior. Non-player physics is out of scope.
- Evidence/hash/correspondence audit: B ready JSON and cited source/artifact/diagnostic manifest hashes recomputed; four anchor source hashes match the B source manifest. No pairwise source conclusion yet.
- Runtime validation: not performed (separate workflow).

