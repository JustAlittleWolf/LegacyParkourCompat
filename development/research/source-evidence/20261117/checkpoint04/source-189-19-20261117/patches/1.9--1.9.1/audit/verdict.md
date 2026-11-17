# Independent source coverage audit: 1.9 → 1.9.1

**Verdict: ACCEPT**

## Immutable claim and evidence identity

- Audited report: `build/source-189-19-20261117/patches/1.9--1.9.1/coverage.md`
- Audited report SHA-256: `0C90533ADCEDAA515C6702FE9203A7BBCD455F13ED92BE4F65242E61C7F2A242`
- 1.9 source root: `decompiled_minecraft/1.9/ornithe-feather`; provenance SHA-256 `8daacb7af8f210e716ee4b34c43b8a5d6f6b4b19831d2fd725cbe16ecf330a1a`.
- 1.9.1 source root: `decompiled_minecraft/1.9.1/ornithe-feather`; provenance SHA-256 `e9fb0da6511597e2de953a143f9570b82243e5b9504603686e557f06271da64a`.

## Audit evidence

- Independently recomputed both provenance hashes and the coverage-report hash; all match the supplied identities.
- Parsed each provenance inventory and recursively enumerated Java files: both sides have 1,819 declared and 1,819 present files, with zero missing paths, extra Java paths, or SHA-256 mismatches.
- Compared the paired inventories: 1 old-only path, 1 new-only path, and 56 common paths with changed hashes, for a 58-path union of differences. The only changed block implementation paths are `ChestBlock.java` and `PistonBaseBlock.java`, the explicitly frozen/excluded provider claims; I did not open those files. `Entity.java` and `ClientPlayerEntity.java` are byte-identical across the pair.
- Retraced local player tick and input through `LocalClientPlayerEntity.tick`/`mobTick`, `PlayerEntity.tick`/`mobTick`, `LivingEntity.tick`/`mobTick`, movement branches, and shared entity movement. Extracted paired method bodies for `LivingEntity.jump`, `moveRelative`, `tick`, `mobTick`, `jumpInWater`, and `jumpInLava`; and for player tick/mobTick/rideTick/jump/moveRelative. All paired bodies are identical except local player `mobTick`, which differs only by the new Elytra sound call after the existing `START_FALL_FLYING` packet. The complete new sound tick reads player position, velocity, fall-flight state and removed state to update the sound instance; it writes no player movement state.
- Reviewed the remaining changed ancestry hunks: armor toughness registration/calculation, world-border damage, and shield damage threshold are damage-only. `World` has only a path-navigation listener accessor rename; its collision queries/tick path are unchanged. Changed inventory block-entity handling, AI/path-navigation, client UI/protocol/version text, persistence/datafix, server, rendering/audio, and vehicle hunks do not alter client player movement. Search of changed non-excluded sources for movement-state and collision operations found no additional changed player movement writer, consumer, predicate, callback, or provider.
- No source-backed counterexample to the report's no-finding conclusion was found. The ordered tick inventory and terminal movement/provider/callback/inventory slices are sufficiently supported by exact source hashes and paired review for this bounded pair.

## Residual gates

- This accepts the audited source coverage claim; the coordinator retains the formal pair-freeze decision and must preserve the already accepted pane-geometry and piston-head-provider exclusions without reopening their packets.
- No implementation, exact intermediate patch introduction boundary, or runtime parity is established by this verdict.

