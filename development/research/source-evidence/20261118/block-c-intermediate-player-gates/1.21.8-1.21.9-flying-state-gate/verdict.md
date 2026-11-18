# Independent audit: 1.21.8–1.21.9 flight-state gate report

## Immutable input

Audited report: `c-queue-1218-1219-flying-state-gate/report.md`  
Input SHA-256: `0bed8bbd1c81382b7de02aa71de56b8ce5ccfac0515b0108d992e3e336d46b95`

## Source identity and readiness

The older source is `decompiled_minecraft/1.21.8/mojmap`, with `mojmap.ready.json` declaring version `1.21.8`, mapping `mojmap`, ready status, 6,105 source files, and source-manifest SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`. Its wrapper provenance records the exact 1.21.8/1.21.10/1.21.11 Mojmap run.

The newer source is `decompiled_minecraft/1.21.9/mojmap`. It has no adjacent `mojmap.ready.json`, but has `.decompile-provenance.json`. That manifest records the 1.21.9 metadata, client JAR, and Mojang client mappings inputs by SHA-256 (`3cca801f41f83059092c88f7de3ea74247da4c394f3e672b7a41451151c293d5`, `40c0a32c46601f71f6a61dae9f126202ded5cc453cfb8da6e42ae73f2a3f5c1b`, and `f223e9775abb66b6712c505cb1a73b48d615efebaf43a9f81dd58df9a9b1c7b3`). The manifest's recorded SHA-256 values match the actual bytes for all three reviewed source files: `LocalPlayer.java` `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1`, `Player.java` `af857617b66a5776e63830771360b96f75e21d47d20db08f164a2dbeee801d82`, and `LivingEntity.java` `b8b49d60769203f7bd5afe4a1bffcdcdbec30be28960324cdc43a2df85a6eb66`. The 1.21.8 file hashes also match the report.

The discovery procedure requires exact releases, mapping identities, relevant hashes, and intact method bodies; it does not require a filename or adjacent `*.ready.json` marker. `buildSrc/README.md` says provenance manifests validate inputs, tools, runtime, and source hashes, while campaign preparation markers are a separate evidence/coverage decision. Therefore the missing 1.21.9 marker does not by itself invalidate the exact source candidate or make the reviewed methods unusable. It does mean this source-only audit cannot claim a separate campaign-ready publication wrapper or complete pair readiness. The report's provisional/pair-freeze limitation is appropriate; treating the marker's absence as a failed source identity would not be.

## Verdict

**Bounded flight-transition claim: ACCEPTED (no difference).** The compared exact source bodies support the report's no-difference result for the named local-player ability and fall-flight gates:

- `LocalPlayer.aiStep`: `1.21.8/mojmap/net/minecraft/client/player/LocalPlayer.java:746–768, 770–774, 787–800, 830–834`; `1.21.9/.../LocalPlayer.java:750–772, 774–778, 791–804, 834–838`. May-fly/always-flying conditions, jump-toggle timer and swimming veto, ability update, grounded jump-on-enable, fall-flight request veto/order, `wasFallFlying` sampling, controlled-camera vertical input, and grounded flying reset retain the same operations and order. The direct ability writes in this slice remain `flying=true`, toggle, and grounded reset to false in both releases.
- `Player.tryToStartFallFlying`, `startFallFlying`, and `canGlide`: `1.21.8/mojmap/net/minecraft/world/entity/player/Player.java:1464–1466, 1519–1530`; `1.21.9/.../Player.java:1321–1323, 1376–1387`. The `!alreadyFallFlying && canGlide() && !isInWater()` gate, `!abilities.flying && super.canGlide()` veto, and shared-flag start write are unchanged.
- `LivingEntity.travelFallFlying` and `stopFallFlying`: `1.21.8/mojmap/net/minecraft/world/entity/LivingEntity.java:2353–2372`; `1.21.9/.../LivingEntity.java:2383–2402`. Climbable fallback and the shared-flag true-then-false reset are unchanged. This does not reopen climbable geometry.
- The `wasFallFlying` field/sample and shared-flag edge callback remain equivalent in `LocalPlayer.java:142, 512` (1.21.8) and `:140, 516` (1.21.9). `Player.isAffectedByFluids()` remains `!abilities.flying` (`1.21.8 Player.java:1033–1035`; `1.21.9 Player.java:886–888`).

**Additional C-owned actual delta: sprint eligibility uses flight state at the shallow-water gate.** This is a player sprint-state decision in C's remaining locomotion domain, not a no-difference result for C. In 1.21.8, `LocalPlayer.canStartSprinting()` (`LocalPlayer.java:1065–1075`) directly checks `!hasBlindness()`, food, passenger permission, fall-flight/slow-movement constraints, and `!isInWater() || isUnderWater()`. In 1.21.9, `isSprintingPossible(boolean)` (`LocalPlayer.java:1055–1060`) consolidates the blindness, food, and passenger checks and adds `($$0 || !isInShallowWater())`; `canStartSprinting()` and run-sprint stopping pass `getAbilities().flying` (`:1062–1069`, `:841–844`), while swim-sprint stopping passes `true` (`:847–848`). For ordinary non-underwater water, the new flying argument can bypass the shallow-water restriction when starting or retaining run sprint. The `isInShallowWater()` predicate is defined in `Entity.java:1449–1451` as `isInWater() && !isUnderWater()` and is the bounded fluid-domain dependency for the fluid owner. Preserve this as a distinct C sprint-gate delta with a cross-domain fluid input; do not assign the whole sprint decision to the fluid lane.

The report already flags the shallow-water dependency, but the audit requires its classification as a C-owned sprint-gate operation and a concrete consequence as above. This does not falsify the accepted no-difference result for the specifically named flight activation/fall-flight slices.

## Scope

This verdict accepts only these bounded claims against the referenced exact sources. It does not establish complete 1.21.8–1.21.9 tick/inventory coverage, close all dependencies, freeze the pair, prove an exact intermediate patch boundary, or establish runtime parity. No product files, research index, Git state, tests, launches, or source generation were accessed or changed.