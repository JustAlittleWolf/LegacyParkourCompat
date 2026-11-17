# Independent audit r2: pair 1.12.2 → 1.13.2 provider coverage ledger

## Verdict

**ACCEPT for the narrow citation correction** in `provider_coverage_ledger/coverage-ledger.md`, SHA-256 `f5133f3574a8a4bc9c2aae01a357f9a2b4f9507c37b5cd7ebc669c4a129b44b2`. This r2 verdict supersedes the single traceability request in `verdict.md` (SHA-256 `d12bd2a483bf533edd40eac9741753e9b56f218eeca2f922967bfce24449b6d1`). It does not redo the original ledger audit.

## Correction verified

The current TorchBlock row adds the exact plain-torch report identity `torch_collision_provider/report.md`, SHA-256 `13e8d74e2625b3a72b9e1be7e02e4c0887f057ac7f6b135b418d3788dccabe67`, and independent ACCEPT identity `torch_collision_provider_audit/verdict.md`, SHA-256 `ddef09524b44601f12949a7ca8e05e5dd5ae7454bcc6f1972d11f52c3571aa9c`. Recomputed hashes over both evidence files match. The row limits this evidence to the common `minecraft:torch` and `minecraft:redstone_torch` empty-collision provider states; it does not treat those packets as coverage for all target-only wall-torch states. This directly resolves the prior missing citation.

All remainder of the original ledger audit stands unchanged: the pair remains partial, and the exact open owner/provider, state, alias, context, migration, and dynamic geometry dependencies listed in the ledger are not promoted to accepted findings by this citation update.
