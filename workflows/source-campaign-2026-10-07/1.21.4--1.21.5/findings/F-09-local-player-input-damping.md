# F-09: Controlled local-player input skips the base 0.98 damping in 1.21.5

- Older version A: 1.21.4
- Newer version B: 1.21.5
- Mechanic / coverage slice IDs: S1-INPUT-SCALE, S1-LOCAL-TICK
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.21.4, 1.21.5]
- Runtime validation: not performed

## Paired evidence

- A: `ready/1.21.4/mojmap.sources.sha256`; `LocalPlayer#aiStep()` lines 643-809 reaches `super.aiStep()` at line 809; `LivingEntity#aiStep()` reaches virtual `serverAiStep()` under its non-immobile/effective-AI branch (lines 2727-2735) and then multiplies `xxa` and `zza` by `0.98F` before travel (lines 2766-2770; `LivingEntity.java` SHA-256 `e62ce650af0a5ac97e4d0a2ba7cad4ba68a8d098525609f8b5b6976a8ab0ae30`). `LocalPlayer#serverAiStep()` calls its superclass then, when `isControlledCamera()` is true, writes the current input axes (lines 607-617; `LocalPlayer.java` SHA-256 `145686ebdc7f0d12a64070309073665eb8695b7e09911a723e86113a77d04611`); `isControlledCamera()` checks `minecraft.getCameraEntity() == this` (lines 620-622, same file hash).
- B: `ready/1.21.5/mojmap.sources.sha256`; `LocalPlayer#aiStep()` reaches `super.aiStep()` at line 830; `LivingEntity#aiStep()` calls virtual `applyInput()` before its immobile zeroing and before jump/travel (lines 2712-2717). Base `LivingEntity#applyInput()` lines 2806-2809 applies the `0.98F` multipliers (SHA-256 `a8aed863d4fdc515c751dd2878a8bbc13179228cb8dbb50edf1d19cd5404271`). `LocalPlayer#applyInput()` lines 608-621, when `isControlledCamera()` is true, writes modified input axes and does not call `super.applyInput()`; the false branch does call it (SHA-256 `f1fcfed4a938732361e7ad951f93e9b73b02320ee56f0b219e2b3c7acdbfa2ef`). The guard checks `minecraft.getCameraEntity() == this` at lines 660-662 (same file hash).
- Shared consumer: `Entity#moveRelative()` calls `getInputVector()` before adding the resulting movement vector to delta movement; the helper is at A `Entity.java` lines 1425-1441 (SHA-256 `05f18ef2ec0413fc010230407c812a11553eb5123b68d21b5d7b2c0c175698ad`) and B lines 1454-1470 (SHA-256 `fc177733e9cc4b2d5cf2562e5529d0f4e0b9c3690f081fbd58aa9119eb173dc4`).

## Snapshot source/artifact identity

- A source publication: `ready/1.21.4/mojmap`; source manifest SHA-256 `f90b61197928632e061ea877955a19055c92ae6f357c2daf1bc646172c6f51f0`; artifact manifest SHA-256 `1a0929ca8c88cfe7874f323918dfa3b044964007d3ff0d3943317bdb35caf841`; source preparation record and immutable artifact identities are in `run.md`. Client jar SHA-256 `c17c450c6e72cc51297daa57ce38f800aa01cf022b743daa21a0512d326d894e`; official mapping SHA-256 `48b502ccc5e855b49da8aa9c0c0d7c565bec54c9f5da3a3664528aff7b9fdf23`; mapped jar SHA-256 `56995548c9cb8bd7cdb9996b676daeafae02bde9d6ef4029b9eeca6bfd7dcc74`.
- B source publication: `ready/1.21.5/mojmap`; source manifest SHA-256 `365cc2d22446ceba0e36f46aa1c95ce4cc31514dbd657505680fb660f3adefd9`; artifact manifest SHA-256 `d35b1b9389d8958e33893faa63d94c86f22af30385233ca947fd410aa5c36656`; source preparation record and immutable artifact identities are in `run.md`. Client jar SHA-256 `522672ad20b460c02c2e39b6c5035ef6a849af28eb11ab2ac7eb293d395a8c11`; official mapping SHA-256 `3907657ade3e61bc8cffb4ca0a1bcba15f57986be4e613900947119364a206e5`; mapped jar SHA-256 `124561e91a61714ca5a73495784c14c03a7a927d1f715eafbcb56ae115a6712a`.

## Source-level difference

For the normal controlled-camera local-player path, A attenuates each current input axis by `0.98F` before travel. B dispatches to the `LocalPlayer` override, which sets the axes but bypasses the base method containing those multipliers. The player's item-use, sneaking, float normalization and square-movement modifiers still apply; the missing factor is specifically the shared `0.98F` attenuation. If `isControlledCamera()` is false, the override calls `super.applyInput()`, so this finding does not apply to that branch.

## Reachability and dependencies

`LocalPlayer.aiStep()` reaches `LivingEntity.aiStep()`. For a non-immobile controlled-camera local player with nonzero input surviving the other modifiers, A supplies the axes through `serverAiStep()` and then applies `0.98F`; B supplies them through `applyInput()` but bypasses the base multiplier. B's non-controlled-camera branch calls `super.applyInput()` and retains the multiplier; the immobile path zeros the axes after input application, so it is outside this finding's applicability. The resulting axes enter the shared player-relative movement-vector consumer before travel. The full travel branch and modifier inventories remain separate, open coverage.

## Consequence and uncertainty

For nonzero controlled-camera input axes, B passes values to travel without the extra `0.98F` multiplication present in A, after applying its other input transforms. The source establishes this per-tick input difference but not a measured trajectory or downstream persistence. Runtime validation was not performed.

## Handoff

Independent delta: the controlled-camera `LocalPlayer#applyInput()` override bypasses the base movement-input damping. Related finding IDs: F-03. Applicability requires the local player to be the controlled camera and input to reach the travel path. Exact first release within the pair is unknown.
