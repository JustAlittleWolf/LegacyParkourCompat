# Implement an accepted movement change

Use this procedure for independent implementation and subsequent product integration. Require an immutable independently accepted finding, exact versions/mappings/source locations, closed dependencies and a proven applicability boundary. Resolve missing boundary evidence before implementing a range; an endpoint pair alone does not establish a patch cutover.

## Prepare the independent worker

The preparation owner must:

1. Export neutral infrastructure into a new repository on a `feat/…` or `fix/…` branch:
   `development/workflows/implementation/export_implementation_base.py --destination <absolute-new-repository> --branch <feat/... or fix/...> --finding <accepted-finding>`.
2. Inspect the manifest, included files, product base revision and retained source identities. Exclude concrete changes, mixins, dispatch hooks, implementation reports and old Git history. Preserve the existing product implementation.
3. Start a fresh worker conversation with only the export, assigned source evidence and neutral instructions. Do not reuse implementation-exposed context or inherit the coordinator's history. Repository isolation does not remove prior chat knowledge.

## Implement and return the candidate

Design the minimal delta and operation contract from the assigned evidence. Follow the exported `AGENTS.md`; do not inspect excluded product code or transplant complete movement loops. Report missing neutral capabilities to the integrator.

Document behavior and both exact source references in Change comments: release, mapping family, class and member. Omit hashes and provenance boilerplate; line ranges are optional. Commit the narrow patch on the candidate branch and return its commit, integration needs and unresolved assumptions in chat. Do not create implementation, handoff, status or completion files, or merge product history into the candidate repository.

## Integrate and verify

The product integrator must:

1. Reconcile the candidate with existing code and accepted findings on a dedicated product branch. Consolidate duplicate operations and identical neutral helpers; preserve evidence and explain replacements or no-code dispositions.
2. Enforce the root `AGENTS.md` architecture: minimal independent Changes, one hook per independently resolved operation, thin `MovementRuntime` mixins, native fallback, closest-applicable-version resolution and complete static catalog registration. Preserve source gates, numeric order, casts and rounding; do not bypass resolution with exact-profile gates.
3. Obtain independent technical review of source fidelity, applicability, hook granularity/order/descriptors, resolver direction, registration and fallback.
4. Merge the default branch before handoff, inspect semantic conflicts and report corrections. Have the designated build owner compile with every Gradle `Test` task disabled and `-x test`. Tests and runtime launches require explicit authorization; never add movement-loop unit/mock tests or claim parity from compilation.
5. Return coherent commits to the master for the central backlog association. Keep acceptance, implementation and runtime evidence separate; do not maintain a second progress record.
