# 1.13.1–1.13.2 Feather: local pitch setter / dry Elytra audit

## Frozen scope and verdict

**Lane:** independent blind exact-source audit, Ornithe Feather mappings. **Pair:** Minecraft 1.13.1 → 1.13.2. **Bounded operation:** local mouse-look pitch mutation and its direct active dry Elytra movement path. This is not a full-pair coverage audit.

**Verdict:** No source difference in the bounded pitch setter, its mouse caller, or the immediate dry Elytra movement path. No finding is supported for this operation over this pair. Both releases use the same double-valued turn delta and float narrowing; both clamp current and previous pitch in the same order; active local dry Elytra movement consumes the clamped current pitch through both `pitch` itself and the look vector. The broader `PlayerEntity.java` files differ, but the audited `moveRelative` method is byte-identical.

## Exact source identity and pins

Paths below are relative to `decompiled_minecraft/<version>/ornithe-feather/`.

- **1.13.1 ready marker:** `ornithe-feather.ready.json`, SHA-256 `f098b3a08b37276cc1a465a8a122df8de76d68786c854b92bac1ca51a79670de`. It names release `1.13.1`, namespace `ornithe-feather`, Feather `1.13.1+build.2`, Java 25 / Vineflower, 2,710 source files / 14,768,136 bytes. Its source-manifest SHA is `21770b01ff3bf443db2e9e1b6df7db25d2fd6b7a72a86228259269c932d3abba`; artifact-manifest SHA is `471177ba7bcff332e93ab5bf0127d5c689c3d34fe4f826976207ccfa3bf7b51c`; diagnostics SHA is `ecf4b21c46745daecada59027db3e365df2a289d5405468dc96f81ad739c5d69`; provenance SHA is `c28ed59f2c507b6133f7677742d316d38e9993eaf1fbb6c2054bd68d1085e904`; raw client SHA-256 is `f0b4cf6d1a87ae150f87a535651138ca7b82bdad8853f76e7ddbff1dd610ac87`.
- **1.13.2 ready marker:** `ornithe-feather.ready.json`, SHA-256 `1d1c644c1deb05c02d11dc575ad834f4f72489124701d9b2b90357f43dc37b38`. It names release `1.13.2`, namespace `ornithe-feather`, Feather `1.13.2+build.2`, Java 25 / Vineflower, 2,711 source files / 14,770,525 bytes. Its source-manifest SHA is `2c8cfb646bf622fb26ac0ca0cb5e02a354fb512e29f8b1010f1aaacd509e1211`; artifact-manifest SHA is `fdcacd9150f98ea70acafc5cab754027ab1a09828ff0eaa8e542dd7890d9ce1e`; diagnostics SHA is `a89e4e3cefe704b58339a1e342e4d8a0fffe7e49dba3ca18402647b69c398a45`; raw client SHA-256 is `3410887ba652f25792c7675bfaf9140e73b60e93cfbf113a803f8a98cb05c0f9`.
- Recomputed hashes of both ready-pinned source manifests match. Recomputed every listed source file against its manifest: 2,710/2,710 and 2,711/2,711 entries match, with source byte counts matching the ready markers and no extra or omitted `.java` files.
- **Provenance boundary:** 1.13.1 ready pins the provenance file hash. 1.13.2 has `ornithe-feather.provenance.json` (SHA-256 `e25950b7855fc4f2aee1a662f0be6b0af9f28788d9e27293075cfc5a3aa5d383`), but its ready marker has no provenance-hash field, so that sidecar is not bound by the ready pin. The accompanying 1.13.2 `verification.json` reports 2,711 source files verified, zero source-file differences/missing files, and no raw-input-artifact differences; its one difference is the regenerated intermediate remapped client JAR, explicitly explained there. I use the 1.13.2 ready-pinned source manifest and the successful complete source-tree hash verification as the direct source identity for this audit; the provenance-sidecar linkage is weaker than 1.13.1.

Relevant full-file hashes (each listed source-file hash was also checked against its source manifest):

| Source file | 1.13.1 SHA-256 | 1.13.2 SHA-256 | Pair result |
| --- | --- | --- | --- |
| `net/minecraft/entity/Entity.java` | `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269` | same | byte-identical |
| `net/minecraft/entity/living/LivingEntity.java` | `bb691358c9a43c9f46e85575bf4d0a4ad671d0eb912502acc3a6a3f625e42f1c` | same | byte-identical |
| `net/minecraft/entity/living/player/PlayerEntity.java` | `4c1b14b4727116071b1ba9e3eeae84b8077f3d767e10e49b6a68a2556da32272` | `4ed22f6c5a3c55d67eed782070ac722201df4d624adbc90779f1fd29c2876633` | file differs; audited method identical |
| `net/minecraft/client/MouseHandler.java` | `b045dc9eaf5b5ab1d9f13dd795465a106f52571dda9e5c837f5c2bbbdfddf4ce` | same | byte-identical |
| `net/minecraft/client/entity/living/player/ClientPlayerEntity.java` | `99b57acea68323125d859313ef5dd2c55d0703c08c5cce475c0227da0d12d1ee` | same | byte-identical |
| `net/minecraft/util/math/MathHelper.java` | `2211b0cb3da7d8789a4a4df96509b95e6b1be8b222be2f530e7bd0695573bd69` | same | byte-identical |

## Source trace

- `Entity.java:318–330`, `updateLocalPlayerCamera(double cursorDeltaX, double cursorDeltaY)`, is identical at both endpoints. It computes `d = cursorDeltaY * 0.15` and `e = cursorDeltaX * 0.15` as `double`; then writes `pitch = (float)(pitch + d)`, writes yaw, clamps the newly narrowed pitch to `[-90.0F, 90.0F]`, advances `lastPitch` independently with the same double delta and narrows it to float, advances `lastYaw`, then clamps `lastPitch`. Thus there is an initial float write before the final clamp, but no intervening callback; current pitch is clamped before the previous/interpolation pitch is advanced and clamped. It does not copy the clamped current pitch into `lastPitch`.
- `MathHelper.java:89–95` confirms this call selects the float overload. Its comparisons clamp finite overflow narrowed to positive/negative infinity to the corresponding endpoint; NaN passes through because both comparisons are false. This behavior is identical at both endpoints.
- `MouseHandler.java:211` calls `minecraft.player.updateLocalPlayerCamera(j, k * n)` after applying mouse scaling/smoothing and invert-Y handling. The full caller file is identical at the two versions. `ClientPlayerEntity` inherits the method from `Entity`.
- `PlayerEntity.java:1442–1468`, `moveRelative`, is byte-identical between versions (method SHA-256 `094fddf8dbae3610055e9e910399d8048f039c5a083a98c19c2de29ee7bf7a5b`). It delegates to `super.moveRelative(...)` on the ordinary non-creative-flight path.
- `LivingEntity.java:1478–1669`, `moveRelative`, is byte-identical between versions (method SHA-256 `40fa1adcacc0b1135aadad2d3b0b1b0015dc6f457810cc4721b1c89d9c132659`). It admits the local/logical movement side, skips the water/lava alternatives when dry, and enters its `isFallFlying()` branch for an active Elytra player. That branch reads `this.pitch` into the radian angle `j`; it also calls `getLookVector()`, which resolves from `this.pitch` and `this.yaw` (`Entity.java:1251–1260, 1691–1693`). The pitch affects the Elytra velocity equations before the self move (`LivingEntity.java:1488–1523`). The normal tick-side caller invokes `moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` at `LivingEntity.java:1920–1926`.

## Audit limit

This verdict closes only the assigned setter-to-dry-Elytra path for these exact releases and the cited Feather namespace. It does not establish complete 1.13.1 → 1.13.2 movement coverage, a patch introduction boundary beyond this pair, or runtime parity. No source was generated or modified; no product files, author reports, wiki data, Git history, tests, builds, or runtime were used.

## Phase-two comparison with frozen author report

The author-lab report bytes were verified against the supplied SHA-256 `6fc626e980e9e0ae29cd827a041ccae540e86fb6cb71d2f495441644d16a1fe4` before comparison. Its verdict agrees with this audit: the bounded 1.13.1 → 1.13.2 pitch update and active Elytra pitch read are unchanged; the turn increment is double-valued before float narrowing; current pitch is clamped before independently advancing/clamping `lastPitch`; and glide reads live `pitch` rather than previous/interpolated pitch. The author report adds a concrete finite `accumulatedDY = 1.0e40` source-level witness for narrowing to infinity and subsequent clamp to `90.0F`, while explicitly limiting that witness to source semantics rather than hardware mouse capability. This is consistent with the float-clamp behavior documented above, not a version difference.

The author report verifies the relevant source files and ready-pinned source-manifest identities. This audit additionally records the whole-tree checks and the weaker 1.13.2 ready-to-provenance-sidecar linkage; that qualification does not contradict the shared source-file and operation result. No incompatible factual claim was identified. The original phase-one verdict prefix is preserved verbatim; this comparison note is the only appended text.
