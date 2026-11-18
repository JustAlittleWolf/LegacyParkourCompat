# Frozen audit and author report binding

## Bound identities

- **Independent verdict:** `c-swim-pitch-vertical-121-1211-audit/verdict.md` in the 2026-11-17 Codex visualization root; SHA-256 `094c091486e86abfc77edf468947d52a1626bd00460a1c3be22d1f7baf5f6830`.
- **Author report:** `c-swim-pitch-vertical-121-1211-source/report.md` in that same visualization root; SHA-256 `c0d2c03d515de8461d9c4a1e411d4965a55efdfd727dc1512c5ec5b1fde48c64`.
- **Compared evidence:** exact local Mojmap decompiled Minecraft `1.21` to `1.21.1` sources. The 1.21 side is identified by `decompiled_minecraft/1.21/mojmap/.decompile-provenance.json`; the 1.21.1 side by `decompiled_minecraft/1.21.1/mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, and `artifacts.sha256`. Per-file pins and paired hashes are recorded in the independent verdict.

## Scope and acceptance

This binding covers the assigned ordinary active nonpassenger `Player.travel(Vec3)` direct swim-pitch Y correction: the local swim and passenger gates; Entity look-vector pitch source; float operations, Mth sine-table initialization and index/cast/mask behavior, and widening to Vec3/double; the coefficient and correction gates; the above-fluid sample coordinates and evaluation order; and the following inherited `LivingEntity.travel(Vec3)` water path.

**Independent operation verdict: accepted as no source difference found** between the exact 1.21 and 1.21.1 Mojmap endpoints. This does not claim complete pair coverage, pair freeze, an exact intermediate boundary, or runtime parity.

## Freeze sequence

The exact paired source files, source identities, and complete bounded trace were examined and written to the independent verdict first. The verdict file was hashed as `094c091486e86abfc77edf468947d52a1626bd00460a1c3be22d1f7baf5f6830` before opening the author report. The author report was then independently hash-checked against its supplied expected identity and matched `c0d2c03d515de8461d9c4a1e411d4965a55efdfd727dc1512c5ec5b1fde48c64`; only then was its content read and compared to the already frozen verdict. The two reports' bounded operation conclusions align.

**Signed:** `c-swim-pitch-vertical-121-1211-audit` independent source auditor, 2026-11-18.

