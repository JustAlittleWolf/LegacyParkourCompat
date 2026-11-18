# Independent audit verdict: 1.21.7 → 1.21.8 player gates

## Verdict

**PASS for the bounded no-difference claim, with a correction to the frozen report’s provenance note.** The claim that the local 1.21.8 source manifest is absent is incorrect for the audited checkout. Its adjacent ready marker names an older absolute output path, but the locally available manifest and readiness identity bind the current source files to that ready set. The six paired Java files are byte-identical across the releases. This verdict covers only the report’s stated player input/sprint/pose and direct effect-reader slices; it is not a full-pair inventory audit or runtime parity result.

## Frozen input identity

- Input: `C:\Users\Wolfi\.codex\visualizations\2026\11\17\01a1eb7b-b9ff-7a20-8686-0295dde20f26\c-queue-1217-1218-player-gates\report.md`
- Expected and observed SHA-256: `d2d86afd60d7cc7c5f8670fa29a84d62abcb632aa16b8ed927cdf0ed7c0132c1`
- Source roots: `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.7\mojmap\` and `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.8\mojmap\` (Mojmap).

## Provenance checks

- 1.21.8 `mojmap.ready.json` says version `1.21.8`, mapping `mojmap`, status `ready`, 6,105 source files / 27,900,784 bytes, and names `mojmap.sources.sha256` with SHA-256 `8ffb76cea647a2ba4fe58e000678f751bea2c40ea6358bae492e962ed1d9d008`.
- Current file `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\1.21.8\mojmap.sources.sha256` has exactly that SHA-256. I checked all 6,105 manifest entries against files under the current `1.21.8\mojmap\` root: 6,105 verified, 0 missing or hash mismatches, total 27,900,784 bytes.
- The adjacent `mojmap.provenance.json` identifies version `1.21.8`, namespace/mapping option `mojmap`, and an exact run for `1.21.8,1.21.10,1.21.11`. Its run output is the legacy `D:\Javastuff\LegacyParkourCompat\build\movement-campaign-2026-10-07\...` location recorded by the ready marker. That path mismatch alone does not invalidate the local tree because the ready marker’s manifest digest matches the manifest that verifies the current tree.
- 1.21.7 `.decompile-provenance.json` has SHA-256 `45bf296d921b2239fd0aedb2684e2c1cb9b7d7532b4106b8a2fd9b09309ca82d`, matching the report’s recorded provenance identity.

## Independent paired-source observations

Each listed SHA-256 is the observed digest in both version directories; the 1.21.8 value also occurs at the corresponding path in its verified source manifest:

| Source file | SHA-256 in both releases |
|---|---|
| `net/minecraft/client/player/LocalPlayer.java` | `53f2a71a886b9c71853afe36f2df857f80a9bf1cd6ec4ca8604ccd7e238d89ee` |
| `net/minecraft/client/player/ClientInput.java` | `597a44339a99f1bce1b081614c7c2984ca03e255b40e9d247675a31b6f810d78` |
| `net/minecraft/client/player/KeyboardInput.java` | `d5cb0e93df7f66755172d74e028225012ee33c6e4f0d510a1d8e25ba5497c0a3` |
| `net/minecraft/client/KeyMapping.java` | `d5475012d2849e7547e23d1584d5b099661a2cb1e60daebe6ec19cb28b779111` |
| `net/minecraft/world/entity/player/Player.java` | `8dc5514fe44311f39268692f5188840b9f65cb71143228fa8b84242585ea7987` |
| `net/minecraft/world/entity/LivingEntity.java` | `609f0197a0b4551ab42279e452c11cdd256135b1d467c2a95950b0e9fd7ddef8` |

Direct source review confirmed the frozen report’s bounded claims:

- `LocalPlayer.java:197–222,234–239`: `super.tick()` precedes changed input and movement/sprint packet handling.
- `LocalPlayer.java:683–744,837–856,1061–1083`: input snapshots precede `input.tick()`; refreshed movement is consumed afterward; auto-jump sets jump; sprint-trigger reset and double-tap timing, sprint-key start, run/swim continuation and blindness/food/forward/collision/water/vehicle gates match.
- `ClientInput.java:6–31`, `KeyboardInput.java:7–36`, `KeyMapping.java:99`: empty base tick, key sampling, impulse/vector normalization and forward threshold are unchanged.
- `LocalPlayer.java:473–510,593–639`: local use-item state and stop behavior, `0.98F` then `0.2F` item-use input scaling, crouch/sneak handling, and sprint gating are unchanged.
- `Player.java:354,459–491`, `LocalPlayer.java:664–680`: pose update invocation, fit guard, desired pose/fallback selection, and standing reset match.
- `LocalPlayer.java:854–856,1065–1083`, `LivingEntity.java:2192–2217,2777–2813`: blindness reader, jump boost amplifier math and jump gate/order, and slow-falling/levitation fall-distance reset match.

Because the six entire files match byte-for-byte, the bounded slices have no source difference. The report’s exclusions and partial full-pair status remain appropriate; the provenance paragraph should be corrected before being reused as current evidence.

## Audit limits

I did not inspect product implementations or Git history, generate or rewrite sources/provenance, run tests, or launch the game. The six-file comparison does not close collision, geometry, fluid, external writer, or other full-pair dependencies.
