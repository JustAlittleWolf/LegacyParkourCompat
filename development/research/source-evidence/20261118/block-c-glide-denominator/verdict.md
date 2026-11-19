# Independent verdict: glide denominator applicability audit

**Terminal status: COMPLETED**  
**Verdict: ACCEPTED for the bounded source claim**  
**Reviewed input:** `c-glide-denominator-source/comparison.md`  
**Raw SHA-256:** `875062a74a6533bd2d85700100a0d554761cedbcbb69d78b1900212806bd34c5`

## Claim and evidence

I independently checked the exact local `ornithe-feather` sources for each release listed in the input, from 1.9 through 1.13.2, focusing on `LivingEntity.moveRelative`, the local camera pitch clamp, `Entity.getRotationVector`, and `MathHelper` sine-table construction/lookup. All 18 triples of `LivingEntity.java`, `Entity.java`, and `MathHelper.java` SHA-256 values match the input's source-identity table.

The earliest checked release, 1.9, already has the `isFallFlying()` branch in `LivingEntity.moveRelative`. The movement sequence and outer local-control/fluid gates are present in the inspected range. `Entity.updateLocalPlayerCamera` clamps ordinary local pitch to `[-90.0F, 90.0F]`; with a locally controlled, dry, already-fall-flying player, pitch `-90.0F` and yaw `0.0F` therefore reach the negative-pitch correction. This is a movement-step reachability claim, not a glide activation or startup claim.

Across the checked old releases 1.9, 1.9.1–1.9.4, 1.10–1.10.2, 1.11–1.11.2, and 1.12–1.12.2, `moveRelative` keeps the negative-pitch condition without a positive horizontal-look-length condition. At `-90.0F`, the pre-1.13 rotation-vector expression negates pitch before cosine lookup. The float lookup selects sine-table index 32768, initialized from `sin(pi)` and represented as a tiny positive float residual. At yaw zero the horizontal magnitude is consequently positive, so the old negative-pitch correction divides by a nonzero value.

In exact 1.13, the producer instead passes the negative pitch radians to cosine lookup. The lookup selects index 0, whose table entry is exactly zero. The negative-pitch correction now requires both negative pitch and positive horizontal magnitude, so it is skipped at this ordinary endpoint. I verified the same guard in 1.13.1 and 1.13.2. Thus, among the inspected exact releases, the guard first appears in 1.13 after remaining absent through 1.12.2, and is present in both checked follow-up patches.

The old code does **not** provide a reachable divide-by-zero fix for this correction. At the other ordinary endpoint, `+90.0F`, the old horizontal magnitude can be zero, but the negative-pitch gate is closed; other divisions by the magnitude are guarded by a positive-length check. The witness and comparison therefore correctly characterize a guard/trigonometric behavior change, not an old reachable zero-denominator defect.

## Exact 1.12.2 identity chain

The immutable accepted witness is `b8ca33c5:development/research/source-evidence/20261118/block-c-middle-late-player-operation-closures/c-glide-1122-113-denominator-witness/{report.md,verdict.md}`. Its report and independent verdict identify the exact 1.12.2 → 1.13 Ornithe Feather pair and the same audited source hashes. For 1.12.2, `ornithe-feather.ready.json` pins source-manifest SHA-256 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da` and raw client SHA-256 `8ada07da5ee77dad3527bd7278fbd05ee1fc8a597813b216a871a2d7d64cc64f`; the source hashes in the allowlist match the checked local files. There is no `.decompile-provenance.json` in that tree; identity is carried by the ready marker, manifests, and adjacent Ornithe provenance record. For 1.13, the adjacent provenance identifies Feather Gen2 `1.13+build.2`, raw client `d807a5ed9428b6c16b64888fd2b7fa76dcf5c55d06f96f7c0c47762c3fc3e26e`, and mapped client `4b91c330aaf6179f7384f70f91ebcdf72af2713cc3788fea1482c3529905a761`; its source hashes also match the local files. No identity mismatch was found, and no source generation was performed.

## Boundaries and remaining gate

This verdict accepts the guard, trig producer, camera-clamped pitch reachability, and version sequence only for the exact inspected releases and ordinary local dry glide state. It does not establish complete pair/tick coverage, implementation correctness, or runtime parity; those remain separate project gates. No wiki, product implementation/history, tests, build, game, TAS, Gym, server, or runtime harness was used.
