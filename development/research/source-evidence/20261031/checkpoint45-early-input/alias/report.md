# Early input-flight alias audit (44)

## Scope and result

Blind source-only audit of three already drafted operations comparing Ornithe Feather 1.7.10 with every stable 1.8 patch, 1.8, 1.8.1–1.8.9. Exact source trees were read from `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`; no product, wiki, or backlog material was consulted. The method-level evidence establishes that all three 1.7.10→1.8 behavioral differences persist at 1.8.9. No later 1.8 patch restores the 1.7.10 behavior or introduces another change in these bounded method bodies.

Source is exact decompiled Ornithe Feather. 1.7.10 and 1.8–1.8.8 have embedded `.decompile-provenance.json` files. 1.8.9 uses the sidecar `ornithe-feather.provenance.json` and `ornithe-feather.sources.sha256`; its ready marker binds the source manifest and artifact manifest. The 1.8.9 source manifest was checked across the full tree: all 1,612 listed Java files exist and match, with zero missing, mismatching, or extra source files. Every relevant file hash listed in `evidence.tsv` matches its embedded or sidecar source manifest. The 1.8.9 ready marker SHA-256 is `fe0ec792349ae43c91d0a30f23660c94b2f5c8d31a29643f73ed0de367110b95`; its declared source-manifest and artifact-manifest SHA-256 values match the actual files (`9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` and `da003358256d1c4402ebb20614651e5410310e871ee913de2b9c1295a64e1446`). Per-release provenance and source identities, including raw client and Feather mapping artifact hashes, are in that ledger.

The earlier independent review text transcribes the 1.8 Feather merged-artifact SHA-256 as `3c020d1588b4aab403b5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`. The actual 1.8 `.decompile-provenance.json` identifies `3c020d1588b4aab403f5df077ca91b6e3776da2823d3b55202f4616f0d5ae5fe`; `evidence.tsv` records the manifest-derived value. This is a transcription mismatch in the older review, not a source-tree hash mismatch.

## Operation verdicts and patch coverage

### Flying vertical input uses configured fly speed

Confirmed through 1.8.9. 1.7.10 `InputClientPlayerEntity.mobTick`, lines 197–204, applies `velocityY -= 0.15` for sneak and `velocityY += 0.15` for jump under `abilities.flying`. 1.8 `LocalClientPlayerEntity.mobTick`, lines 604–611, gates on `abilities.flying && isCamera()` and uses `getFlySpeed() * 3.0F` in each vertical write. The equivalent later gates/writes are at 1.8.2 and 1.8.9 lines 596–603. The 1.8–1.8.9 `PlayerAbilities` source has `flySpeed = 0.05F` at line 11 and returns the float at lines 44–49; these match the 1.7.10 field/default/accessor. Thus 1.8’s default product is float `0.15000000596046448`, promoted into the velocity operation; this differs from double literal `0.15`, and non-default configured values scale it. The added camera guard remains part of the 1.8 gate; the ordinary active-camera path is eligible.

### Sprinting doubles airborne flight acceleration

Confirmed through 1.8.9. The 1.7.10 `PlayerEntity.moveRelative`, lines 1241–1249, saves the previous `speedInAir`, sets it to `abilities.getFlySpeed()` only when flying without a vehicle, calls `super.moveRelative`, then restores `velocityY` and `speedInAir`; otherwise it delegates directly. 1.8 changes only the assigned temporary scale to `getFlySpeed() * (isSprinting() ? 2 : 1)` at lines 1281–1289. At 1.8.2–1.8.9 the same factor/call/restore sequence appears at lines 1283–1291. `LivingEntity.moveRelative` selects `speedInAir` in the airborne branch and passes it to `updateVelocity` (1.7.10 lines 1105–1111; 1.8.9 lines 1132–1140). The later `isLocallyControlled()` outer guard still includes the described local-player path; the input and flight conditions are unchanged across the audited 1.8 patches.

### Sneak-held input suppresses one sprint double-tap window

Confirmed through 1.8.9. In 1.7.10 `InputClientPlayerEntity.mobTick`, lines 141–144 save prior jump/forward-threshold state then sample input. Its grounded double-tap branch at lines 160–172 requires prior forward below `0.8F`, current forward at least `0.8F`, and the existing sprint/hunger/item/blindness gates. In 1.8, `LocalClientPlayerEntity.mobTick` also saves prior `input.sneaking` before `input.tick`; the grounded branch adds `!priorSneaking` at lines 543–566. The corresponding 1.8.9 sequence is at lines 535–558. The held-sprint branch and sprint stop branch remain separate and unchanged in those method bodies. `KeyboardInput.tick()` reads forward before sneak and, while sneaking, multiplies each float movement value by the double literal `0.3` before casting the result back to float (1.7.10 and 1.8.9 lines 13–37). This sampler body is textually identical across all audited 1.8 patches and the same in 1.7.10, supporting the prior-sneak/forward-threshold transition described by the candidate.

## Intermediate aliases and fingerprints

The extracted method-body SHA-256s below are lexical fingerprints over the source method text (including its line breaks). Exact equality within a group demonstrates method-body aliasing; different local names/line offsets across the 1.8.1→1.8.2 decompiler output do not by themselves indicate behavior changes. The operation conditions and order were compared at those versions and at the 1.8.9 endpoint.

| Operation body | 1.7.10 fingerprint | 1.8 / 1.8.1 | 1.8.2–1.8.9 | Source ranges |
| --- | --- | --- | --- | --- |
| local input `mobTick` | `0695bd17a10ce7c325bb09f3b22d71498a05f9626300768bc290bd2564782be3` | `40712267ae8cbc9783e6b941ecc29308370aeae8b6c9a255d55a76c9b5fcfb32` | `5020da8f88b411b2f146fe8b9ef3402dddb2a4600180c538a379b7418935c807` | 1.7.10 `InputClientPlayerEntity.java:86–239`; 1.8/.1 `LocalClientPlayerEntity.java:496–645`; .2–.9 `LocalClientPlayerEntity.java:488–637` |
| `PlayerEntity.moveRelative` | `c3833ed9b9d3d0f8c0f61efc8118ecf0800e20e204368b8d3473223a210d9206` | `436962b30a9267d79ae3b2dc70a759b776c92a9a1b36603b52f5aee7b3ef21f4` | `e49e02b0c1c173b167851f5b4faf1aa45f5a995e4a6e7932fb423a903a916b8a` | 1.7.10 `PlayerEntity.java:1237–1253`; 1.8/.1 `:1277–1293`; .2–.9 `:1279–1295` |
| `LivingEntity.moveRelative` | `dbabf283717b0f9e950bb9f78ee31d8f50a51e5be7c9b01384b8d64083ec4764` | `308656fb103f23bdc6dc076c23671e2cbc1a7de22bc612f86b92ad9aed8d2aee` | `24f1145ddee4e9e2826728a09bf20f1ac82ae4bc902a454ea17b8007c3094853` | 1.7.10 `LivingEntity.java:1095–1200`; 1.8 `:1115–1235`; 1.8.1 `:1121–1241`; .2–.9 `:1119–1239` |

Every 1.8 stable patch is explicitly represented in the source-hash ledger. The `mobTick`, player `moveRelative`, and living `moveRelative` method fingerprints are equal between 1.8 and 1.8.1, although PlayerEntity and LivingEntity full-file hashes differ due to other source edits. From 1.8.2 through 1.8.9, the audited LocalClientPlayerEntity, PlayerEntity, LivingEntity, KeyboardInput and PlayerAbilities files are byte-identical within each class group. The .1→.2 method text changes local names/formatting without changing the compared operations; unrelated class edits also exist. Per-version source manifest verification makes the patch coverage explicit. This closes the bounded 1.7.10→1.8.9 alias check only; it does not assert full pair coverage or source-finding acceptance.

## Exact source locations used

- `1.7.10/ornithe-feather/net/minecraft/client/entity/living/player/InputClientPlayerEntity.java`: `mobTick` at lines 86–239.
- `1.8/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick` at lines 496–645.
- `1.8.1/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: same `mobTick` range.
- `1.8.2` through `1.8.9/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`: `mobTick` at lines 488–637.
- 1.7.10 `PlayerEntity.moveRelative`: lines 1237–1253; 1.8/.1 lines 1277–1293; 1.8.2–.9 lines 1279–1295.
- `LivingEntity.moveRelative`: 1.7.10 lines 1095–1200; 1.8 lines 1115–1235; 1.8.1 lines 1121–1241; 1.8.2–.9 lines 1119–1239.
- `KeyboardInput.tick`: all audited versions lines 13–38; fly speed field/accessor: all audited versions lines 11 and 44–49.

No code, tests, source generation, implementation, backlog, wiki or product records were changed. Full-pair coverage remains outside this bounded audit.
