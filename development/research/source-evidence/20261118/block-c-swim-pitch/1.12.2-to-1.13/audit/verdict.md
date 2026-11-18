# Independent source audit: 1.12.2 → 1.13 swim pitch vertical writer

## Verdict

**NOT ACCEPTED AS WRITTEN.** The reported source difference and its exact gate/math are confirmed. The report's key look-direction interpretation and its concrete gameplay witness reverse the vertical look sign, so the bounded finding is not independently accepted in this revision.

The correction is localized: both releases' `Entity.getRotationVector` bodies produce a vertical look component of `-sin(pitch * radians)` (1.12.2: `Entity.java:1239–1246`; 1.13: `Entity.java:1250–1258`), and `getLookVector()` returns that vector using current pitch (both: `Entity.java:1690–1692`). Thus negative pitch points upward and gives positive `g`; positive pitch points downward and gives negative `g`. The report correctly quotes the implementation condition but labels its effects backward in the paragraphs describing “upward/level” and “downward” looks.

Correct source-level witness: for a swimming, unmounted player with no jump and an empty fluid state in the block at `(x, y + 0.9, z)`, an upward look (`g > 0`) skips the writer; if the fluid state there is nonempty, it applies the writer. A downward look (`g < 0`) applies it regardless of that fluid read, using `h = 0.085` when `g < -0.2`, otherwise `h = 0.06`. The term is `velocityY += (g - velocityY) * h`, before `super.moveRelative` and the inherited move. This describes the vertical look component, not a change to contact/fluid mechanics.

## Confirmed source trace

- The exact pair is 1.12.2 → 1.13, both Ornithe Feather (`feather-gen2-1.12.2+build.2` and `feather-gen2-1.13+build.2`). The ready markers report `ready`; their declared source-manifest digests match the files. Their historical `outputPath`/`sourceRoot` locations are not present here, but each relied source file's current bytes match its entry in the corresponding canonical source manifest.
- Local tick reaches inherited movement: 1.12.2 `LocalClientPlayerEntity.tick` calls `super.tick()` at lines 165–167; 1.13 does so at 179–181. `LivingEntity` invokes `this.moveRelative(this.sidewaysSpeed, this.verticalSpeed, this.forwardSpeed)` at 1.12.2 line 1847 and 1.13 line 1907. Dispatch therefore reaches the player override on this local-player path.
- 1.12.2 `PlayerEntity.moveRelative` at lines 1386–1404 has the flight-specific save/delegate/restore path, otherwise delegates to `super`; it has no swimming/look-directed vertical writer.
- 1.13 `PlayerEntity.moveRelative` at lines 1435–1462 adds the reported swimming and not-riding writer before the flight branch and inherited call. `g`, `h`, the double constants, comparisons and operation order match the report's code block. `PlayerEntity.isSwimming()` (lines 1814–1816) excludes flying and spectator players. This is reachable through the local player path; the unchanged upstream swim-state machinery is not part of this finding.
- The above-block `getBlockState(...).getFluidState().isEmpty()` access is confirmed only as this writer's gate. No fluid mechanics or solid collision domain was assigned or audited.

## Bound identities and literal hashes

Reviewed author artifact SHA-256: `F1392FA2A961632AA5A64489882A34CDA6BB704F881469D7FCF5DA7A7821B539` (exact match).

Canonical source SHA-256 values, each checked against its source-manifest entry:

| Release | Relative source | SHA-256 |
|---|---|---|
| 1.12.2 | `net/minecraft/entity/living/player/PlayerEntity.java` | `e4e0fdbe07a7d0a0ae4a70cbb6739a287c9d045a4a12b409895c220b5d91fe1e` |
| 1.12.2 | `net/minecraft/entity/living/LivingEntity.java` | `190e9ac551538e015d9e4d6c42856e5ba32b593131cf6d93895e7b29533f1ee6` |
| 1.12.2 | `net/minecraft/entity/Entity.java` | `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a` |
| 1.12.2 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `01a58e94d8c6ff98a8e3794227cdc76a5fcbdbad795c70c9cf28854aff9823cc` |
| 1.13 | `net/minecraft/entity/living/player/PlayerEntity.java` | `4a57ce65405436aeb211d7a04a9a9f6ae1a661d033fd3ba2424909bfb42b8acd` |
| 1.13 | `net/minecraft/entity/living/LivingEntity.java` | `c38e95c2c13e088b03d77fc2c6b98d4edd7e6ca5aef12b187c8e3f3ea782e864` |
| 1.13 | `net/minecraft/entity/Entity.java` | `09514488d9055449c47e2e4bcd23b5773203ca634b7ecba2323a99d0ae016642` |
| 1.13 | `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` | `a188e1bc87831dd9da1ec1366a49acc4104c18e6979129097d02d5d3617e6a08` |

Source manifest SHA-256 values in the ready markers: 1.12.2 `b8a37ccfccd2aac5f40f5e34fec85873dbdfa93a266043c103e592e4a8c949da`; 1.13 `0509c2614b0bb0e616e2ebc6382f8eb5d031b5d86b834a4a6ed7fd96b6d68f1a`.

## Scope limit

This verdict covers only the exact 1.12.2 → 1.13 source claim and its local-player applicability for the pitch-directed swim vertical writer. It does not establish an intermediate patch boundary, complete pair coverage, implementation acceptance, runtime parity, or other fluid/collision behavior.

— Independent blind exact-source auditor, signed 2026-11-18
