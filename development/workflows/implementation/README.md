# Implement an independently accepted change

Receive one immutable, independently accepted source finding, the exact older/newer versions, mappings and locations, dependency closure and proven applicability boundary. An endpoint pair alone cannot establish an intervening patch cutover. Resolve missing boundary evidence before implementing a version range.

## Independent implementation environment

The preparation owner exports a clean infrastructure snapshot into a new repository with its own `feat/…` or `fix/…` branch. Include only neutral build/runtime/version-resolution infrastructure and necessary API contracts. Exclude existing concrete movement changes, mixins, movement dispatch hooks, implementation reports and old Git history. Inspect the export before handing it over; a new branch in the existing product repository does not remove its history. The export helper and infrastructure manifest must identify included files and base revision. Never erase existing implementations from the product repository to create blindness.

Use `development/workflows/implementation/export_implementation_base.py --destination <absolute-new-repository> --branch <feat/... or fix/...> --finding <accepted-finding>`. Its base-identity manifest identifies the product revision, export policy and copied infrastructure. Verify the manifest and retained finding's source/mapping identities before independent implementation.

An implementer receives that snapshot plus accepted source evidence, and designs the delta without consulting existing concrete implementations. Add the minimal operation contract and generic integration proposal needed by the finding. Do not transplant a complete movement loop. Deliver the narrow patch on your own branch in a coherent commit. Put behavior and both exact source references in Change comments; communicate the commit reference, integration needs and unresolved assumptions in chat. Do not create implementation, handoff, status or completion report files. If neutral infrastructure lacks a required capability, report it to the integrator; do not inspect excluded product code to fill the gap.

## Product integration

The integrator works in the product repository on its dedicated branch, reconciles the independent patch with existing code and accepted findings, consolidates shared neutral operations and removes redundant implementation. Preserve existing evidence and explain replacements or no-code dispositions.

- Put independent minimal deltas in `change/`; do not inherit behavior across releases.
- One hook represents one independently resolved technical operation. Keep mixins thin and general, dispatch through `MovementRuntime`, and preserve native behavior when emulation is inactive.
- `ChangeResolver` selects the closest applicable change from the selected version or later. Do not add exact-profile gates that defeat resolution.
- Register every implemented interface in the single static `MovementChangeCatalog`. No per-version providers or duplicated movement loops.
- Preserve source gates, evaluation order, arithmetic, casts and rounding. Share helpers/accessors only when behavior is exactly identical. Broken movement invariants must fail visibly; expected failures use `LegacyParkourCompat.LOGGER` and are handled.

Have an independent technical reviewer verify source fidelity, applicability, hook granularity/order/descriptors, resolver direction, complete catalog registration and native fallback. Compile through the designated build owner with every Gradle `Test` task disabled across all projects and `-x test`; compilation is not runtime parity. Tests and game/TAS/Gym/server launches require explicit authorization. Do not add movement-loop unit/mock tests.

Commit coherent checkpoints. Before product handoff, merge the repository default branch, inspect incoming commits for semantic conflicts and report corrections. The master orchestrator alone updates the central [backlog.json](../../research/backlog.json), associating findings with integrated commits and preserving separate source-acceptance, implementation and runtime evidence. Implementers do not maintain a second progress record.
