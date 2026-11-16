# Implement an accepted movement change

Use this procedure for independent implementation and subsequent product integration. Require an immutable independently accepted finding, exact versions/mappings/source locations, closed dependencies and a proven applicability boundary. Resolve missing boundary evidence before implementing a range; an endpoint pair alone does not establish a patch cutover.

## Prepare the independent worker

The preparation owner must:

1. Start from the current `main` checkout and export its neutral infrastructure into a new, history-free repository on a `feat/…` or `fix/…` candidate branch:
   `development/workflows/implementation/export_implementation_base.py --destination <absolute-new-repository> --branch <feat/... or fix/...> --finding <accepted-finding>`.
2. Inspect the manifest, included files, product base revision and retained source identities. The sanitized export is the candidate branch's base snapshot; it excludes concrete changes, mixins, dispatch hooks, implementation reports and old Git history. Preserve the existing product implementation in the product checkout.
3. Start a fresh worker conversation with only the export, assigned source evidence and neutral instructions. Do not reuse implementation-exposed context or inherit the coordinator's history. Repository isolation does not remove prior chat knowledge.

## Implement and return the candidate

The assignment must provide enough evidence, scope, authorization, owned paths, output and stop condition for the worker to finish independently. Design the minimal delta and operation contract from the assigned evidence. Follow the exported `AGENTS.md`; do not inspect excluded product code or transplant complete movement loops. Resolve technical questions within the assignment using its evidence and project rules; contact the coordinator only for an organizational blocker that cannot be resolved from those inputs. Do not wait for progress checks or additional permission for already authorized work.

Document behavior and both exact source references in Change comments: release, mapping family, class and member. Omit hashes and provenance boilerplate; line ranges are optional. Commit the narrow patch on the candidate branch and return its commit, integration needs and unresolved assumptions in chat. End with one terminal result, `COMPLETED` or `FAILED`, identifying the candidate commit/output and any remaining blocker. The coordinator may refill the released slot without repeating completed technical review; source acceptance, applicability and other explicit independent gates remain separate and must still be satisfied before integration. Do not create implementation, handoff, status or completion files, or merge product history into the candidate repository.

## Integrate and verify

The product integrator must:

1. Create a dedicated product integration branch from the current `main`. Reconcile the candidate with existing code and accepted findings, consolidating duplicate operations and identical neutral helpers into the established product structure. Preserve evidence and explain replacements or no-code dispositions.
2. Enforce the root `AGENTS.md` architecture: minimal annotated Changes in version subpackages, one hook per independently resolved operation, thin `MovementRuntime` mixins, native fallback, closest-applicable-version resolution and complete automatic hook discovery. The change annotation alone supplies the registration version; keep keyed variant factories on their change classes. Preserve source gates, numeric order, casts and rounding; do not bypass resolution with exact-profile gates.
3. Integrate coherent accepted changes as they are ready, keeping discovery moving. As the integration self-check, compare the consolidated code with accepted source evidence and the required architecture, including fidelity, applicability, hook granularity/order/descriptors, resolver direction, registration and fallback. Source acceptance and blind source audits remain separate gates; code integration does not replace them.
4. If `main` advanced since the integration branch was created, merge the current `main` into the integration branch and inspect semantic conflicts; report any corrections. Honor explicit user check constraints. After code changes, have the designated build owner run the required build with every Gradle `Test` task disabled and `-x test`, unless the user explicitly defers it. Tests and runtime launches require authorization; do not add optional checks. Documentation-only changes need static checks. Never add movement-loop unit/mock tests or claim parity from compilation.
5. After consolidation and authorized checks, merge the integration branch into `main` and return the resulting `main` commit to the master for the central backlog association. Keep acceptance, implementation and runtime evidence separate; do not maintain a second progress record. Do not push unless the user requests it.
