> Second corrected source-only revision for root reconciliation. Immutable original draft SHA-256: `15553598c102027638241e8a299ddca1d97c5765c733ce1e6b092224f699c082`; prior candidate SHA-256 `2E63B5E7CFC08C2DEBC2F0BC868D46EC7114645F6F5726F9853678D2A2380FEB`. First cold source audit: `.native-source/20261028/ordinary-closure/autojump-cold-audit/autojump-fallback-direction-cold-audit.md` (SHA-256 `0f5b0634dfa8728227e9a1ec7c47e76d9144e6354b9e8bdfcc30e1271d7f20b5`). This copy makes the audited bucket-difference example unambiguous by naming the exact binary32 yaw value; it retains the “nonzero” input correction. The prior candidate audit is `.native-source/20261028/ordinary-closure/audit-corrections/autojump-fallback-direction-corrected-audit.md` (SHA-256 `982200BAEE7B28BBC1877EBEC717486D4F8B014ECC8C2109D4658F2978CC88E7`); it noted the rounded display value is ambiguous if parsed as an exact decimal. Original draft remains unchanged; no permanent finding/backlog edited.
# Auto-jump fallback input direction

Immutable source finding draft: `autojump-fallback-direction`, revision is the SHA-256 of this Markdown file after writing. Scope is the reachable local-player direction fallback only; collision enumeration order is outside this finding.

## Evidence

The canonical local source is `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft`. The per-release mapping and source identities are in `identities.json`; each item binds the auto-jump owner class and trig helper source hashes to its mapping-specific ready marker or hidden provenance file. `1.10` and `1.21.9` have hidden `.decompile-provenance.json` identities and no ready marker. Other listed snapshots have mapping-specific `*.ready.json` markers and source manifests. The frozen `1.10` source is present under `ornithe-feather`; 1.9.4's inspected mapped source has no AutoJump symbols.

- 1.9.4, Ornithe Feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java` (AutoJump absent in the mapped source search).
- 1.10 and 1.10.2, Ornithe Feather: `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `move` lines 812–817 and `autoJump` lines 823–843; `net/minecraft/util/math/MathHelper.java`, `sin`/`cos` lines 15–20.
- 1.12.2 and 1.13.2, Ornithe Feather: same owner, `move`/`autoJump` around lines 845–851 / 857–879 and 882–902; `MathHelper.java`, `sin`/`cos` lines 15–20.
- 1.14.4, Mojmap: `net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump` lines 856–882; `Mth.java`, `sin`/`cos` float overloads.
- 1.16.5 and 1.19.4, Mojmap: same owner, `updateAutoJump` beginning at lines 874 / 891 and `canAutoJump` at 974 / 1007; `Mth.java`, float `sin`/`cos` overloads.
- 1.21.8, 1.21.9, and 1.21.10, Mojmap: same owner, `updateAutoJump` lines 935–956 (1.21.8) and 925–950 (1.21.9/1.21.10) and `canAutoJump` lines 1051/1041–1053; `Mth.java`, float `sin`/`cos` overloads at lines 48–53.
- 1.21.11, Mojmap: `LocalPlayer.java`, `updateAutoJump` lines 966–984 and `canAutoJump` lines 1082–1094; `Mth.java`, double `sin`/`cos` overloads at lines 50–55.
- 26.2, unobfuscated: `net/minecraft/client/player/LocalPlayer.java`, `updateAutoJump` lines 1005–1023 and `canAutoJump` lines 1121–1132; `net/minecraft/util/Mth.java`, double `sin`/`cos` overloads at lines 50–55.

## Finding

Auto-jump is present in 1.10 and absent in the inspected 1.9.4 local-player source, establishing its presence by 1.10.0 in this source set. In 1.10 through 1.21.10, the owner method first measures the actual post-move horizontal delta as a `float` squared length. If that value is `<= 0.001F`, it reconstructs a horizontal direction from the current input vector and speed. It computes yaw radians as a `float`, then obtains `sin` and `cos` through the 65,536-entry float lookup table. The same `<= 0.001F` test is applied to the reconstructed vector; if still below threshold, the method returns. Otherwise it normalizes this vector and uses it for both the facing dot product (`< -0.15F` rejects) and the side-segment/look-ahead geometry.

At 1.21.11, this fallback still forms yaw radians as a `float`, but `Mth.sin`/`cos` now accept `double`, so the float angle is widened at the call. The helper indexes its float sine table with double multiplication by `10430.378350470453` (cast to `long` and masked); through 1.21.10 the float overload multiplies by float `10430.378F` before casting to `int`. Table construction also changes from the earlier `i * 2π / 65536` form to `i / 10430.378350470453`. 26.2 retains the 1.21.11 double-argument table lookup. This changes raw fallback direction at some yaw values, though the vector remains float-derived and both forms return float table values.

## Reachability and movement meaning

The fallback is reachable for an ordinary local player with auto-jump enabled, eligible grounded state, non-sneaking/non-passenger status, nonzero movement input, and actual horizontal movement squared `<= 0.001F`—for example, when a collision leaves only a tiny post-move horizontal delta. The `1.21.11` lookup change can shift the reconstructed direction by one table bucket at some yaw angles. For the actual binary32 yaw value `-179.791259765625°` (display-rounded to `-179.7913°`; the resulting float yaw radians are `-3.137949466705`), the earlier float multiplication/cast yields table index `32807` while the newer double multiplication/long-cast yields `32806`; the sampled sine values are approximately `-0.003739069` and `-0.003643196`. With forward input, this changes the fallback vector's X component by about `0.00009587 × speed` before normalization. This illustrates only a raw math consequence, not a measured trajectory.

Because the normalized fallback direction drives both the `-0.15F` facing gate and the candidate side-segment/look-ahead geometry, a yaw/input/obstacle setup close to either boundary can change whether the method arms auto-jump. If armed, the existing auto-jump timer drives a synthesized jump input on a subsequent local-player tick. No claim is made that the small lookup delta changes every ordinary obstacle traversal or that a tested full tick diverges.

## Gate evolution observed

The pre-1.14 method gates the fallback behind enabled auto-jump, cooldown `<= 0`, grounded, not sneaking, not riding, and nonzero movement input. The 1.14.4 gate is materially equivalent, with passenger terminology. By 1.16.5, the `canAutoJump` predicate additionally excludes `isStayingOnGroundSurface()`, requires `isMoving()`, and requires `getBlockJumpFactor() >= 1.0`; the same gate is present in 1.19.4 through 26.2. The fallback threshold and facing threshold remain `0.001F` and `< -0.15F` in inspected snapshots.

## Freeze limits

This draft compares only the assigned release snapshots. It does not establish an exact intermediate patch boundary for the 1.21.11 helper signature/math change, nor independently close any full release-pair movement inventory. The observable consequence is a reachable decision/geometry perturbation; a game/TAS run was not performed.

