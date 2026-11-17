# Pane and piston onset audit: source identity addendum

Verdict: **ACCEPT** for the bounded onset/source facts in the prior audit, when this addendum is read with that audit.

## Immutable bindings

- Onset packet: `build/mcpk-20261116/pipeline-early-rules/pane-piston-source/pane-piston-onset.md`, SHA-256 `734ce421d5ac7c388ba21d7ee7e581e38dab5b5a12079aabb5f3337c86f2a6ac`.
- Prior independent audit: `build/mcpk-20261116/pipeline-early-rules/pane-piston-audit/verdict.md`, SHA-256 `b7af843aea71e1f4e8ead7250dadbbc71ac62b632a2bd4fa5f5c24f3821bf4c5`.
- Identity resolution packet: `build/mcpk-20261116/pipeline-early-rules/source-identity-resolution/packet.md`, SHA-256 `5f471e4f8e127ff0006f3b574244de3c38584d7360636a5234a586eec579d9d9`.

## Decision

The 1.9–1.9.3 source rows are authenticated for the specific audited source facts under the repository's source-publication contract. `buildSrc/README.md` defines the published mapping-local `.decompile-provenance.json` as the manifest that binds complete input URLs and hashes, tool/remapper and runtime identity, pipeline options, and per-source hashes. It says published trees carrying this manifest are checked against current inputs and source hashes; campaign readiness markers are a separate evidence/coverage decision. The identity packet verifies those manifest hashes and exact version metadata/client pins, verifies Feather metadata pins and the release-specific stable `+build.2` coordinates, and reports every one of the 1,819 per-source hashes matching for each row. The exact rows have the hidden manifests at the canonical `decompiled_minecraft/<release>/ornithe-feather/` publication paths. Thus no extra root registry row or campaign ready marker is needed to authenticate these specific sources.

The Feather mapping JAR bytes were unavailable for a fresh raw-byte rehash. This limits independent confirmation of the artifact digest against the archive bytes now, but does not make the recorded mapping version/build ambiguous: each publication manifest binds its precise Feather Gen2 `+build.2` Maven URL and SHA-256, and the identity packet binds the coordinate to the locally hash-verified Feather metadata. The decompile task's provenance manifest is the source publication identity. I treat that project-defined provenance chain as sufficient for this bounded source audit; I do not claim an independent re-download/re-hash of the raw archive or broader campaign readiness.

Combining this closed identity gate with the unchanged source/hash/call-path observations in the prior audit, **1.9 is accepted as the earliest checked release** among 1.8.9, 1.9, 1.9.1, 1.9.2, 1.9.3 and 1.9.4 for both the pane connection-provider form and changed piston-head collision-shape/provider. Pane acceptance remains limited to onset of the provider form; it does not accept or restate the separate general pane-behavior claim. The piston order/selectors and ordinary player movement reachability remain as bounded in the prior audit. This is source acceptance only, not trajectory prediction, runtime parity, pair coverage, implementation or campaign readiness.

No source generation, registry write, network operation, build, test or runtime launch was performed. This addendum supersedes the prior audit's bounded-inconclusive outcome only for the identity gate and the same bound onset/source facts; the prior report remains unchanged as historical evidence.
