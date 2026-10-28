# Autojump input-trigonometry applicability closure

## Scope and evidence boundaries

Read-only comparison against `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft` only. The exact checked release IDs are 1.9.4, then every available release directory from 1.10 through 1.21.10. 1.21.11 was not used. No product sources, history, findings, backlog, wiki inventory, tests, build, game run, or source generation were consulted.

The feature entry point is absent from every Java file in 1.9.4 (`rg -n -i --glob '*.java' 'auto.?jump|autojump'` returned no matches). In 1.10 it first appears as `LocalClientPlayerEntity.autoJump(float,float)`, called after `move` computes the resolved position delta: `1.10/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:810-823`.

## Version coverage

A per-release source scan found an autojump method with both the float trig lookup calls and two inclusive `<= 0.001F` magnitude gates in every release below. The method is `autoJump` or `updateAutoJump`, except the Yarn-mapped 1.14.1–1.14.3 class where it is `ClientPlayerEntity.method_3148`.

- 1.10, 1.10.1, 1.10.2
- 1.11, 1.11.1, 1.11.2
- 1.12, 1.12.1, 1.12.2
- 1.13, 1.13.1, 1.13.2
- 1.14, 1.14.1, 1.14.2, 1.14.3, 1.14.4
- 1.15, 1.15.1, 1.15.2
- 1.16, 1.16.1, 1.16.2, 1.16.3, 1.16.4, 1.16.5
- 1.17, 1.17.1
- 1.18, 1.18.1, 1.18.2
- 1.19, 1.19.1, 1.19.2, 1.19.3, 1.19.4
- 1.20, 1.20.1, 1.20.2, 1.20.3, 1.20.4, 1.20.5, 1.20.6
- 1.21, 1.21.1, 1.21.2, 1.21.3, 1.21.4, 1.21.5, 1.21.6, 1.21.7, 1.21.8, 1.21.9, 1.21.10

The expression is already present in the first-feature class at 1.10: `.../1.10/ornithe-feather/net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java:833-840`. It persists in the mapped 1.14.1 Yarn form at `.../1.14.1/yarn/net/minecraft/client/network/ClientPlayerEntity.java:856-863`, the 1.14.4 Mojmap form at `.../1.14.4/mojmap/net/minecraft/client/player/LocalPlayer.java:866-873`, and the endpoint at `.../1.21.10/mojmap/net/minecraft/client/player/LocalPlayer.java:932-940`. The first/endpoint helpers are `MathHelper.sin/cos` at `.../1.10/ornithe-feather/net/minecraft/util/math/MathHelper.java:15-20` and `Mth.sin/cos` at `.../1.21.10/mojmap/net/minecraft/util/Mth.java:48-53`.

## Source facts: gates and numeric boundary

- 1.10 executes the calculation only with autojump enabled, cooldown `ticksToNextAutojump <= 0`, grounded, not sneaking, not riding, and at least one nonzero input component. 1.14-era Yarn uses the renamed `getLastAutoJump`/vehicle gates but keeps a component-wise nonzero input test. 1.14.4 still has enabled/cooldown/grounded/not-sneaking/not-passenger plus nonzero `getMoveVector`. Starting in 1.15, gate logic is expressed in extracted `canAutoJump`: it checks enabled, cooldown, grounded, not riding, and movement input, and also excludes staying on a ground surface and requires block jump factor `>= 1.0`. Compared with 1.14.4, this replaces the explicit not-sneaking check with the not-staying-on-ground-surface predicate. 1.21.10 retains the 1.15-era predicates (`LocalPlayer.java:1041-1053`).
- The vector entering the first magnitude test is the actual post-move horizontal delta, passed to the method as `float` values; its squared length is converted to `float`. The fallback fires on **inclusive** `<= 0.001F`. If it fires, the vector is replaced by speed-scaled input rotated by yaw, using `sin(yaw * (float)(Math.PI / 180.0))` and corresponding cosine. The new vector's squared length is again cast to `float`; `<= 0.001F` then returns without scanning geometry.
- The trig is a lookup, not `Math.sin/cos`: 1.10 `MathHelper.sin` indexes `SINE_TABLE[(int)(x * 10430.378F) & 65535]`, with cosine adding `16384.0F` before the cast. 1.14.1 Yarn and 1.21.10 Mojmap retain this same float scale, int cast, and 16-bit mask. The radian factor is first cast to `float`; yaw multiplication and table-index multiplication are float operations. The cast to `int` truncates toward zero, then the bit mask wraps the index. No normalization of yaw occurs in this helper.
- If measured delta squared is above the threshold, the fallback trig is not used; the measured direction is normalized and used. The forward-facing dot test still runs afterward. If fallback is entered, an input vector must already be nonzero, but the speed-scaled/rotated result can still hit the second threshold and early return.

## Reachability and geometry interpretation

`move` snapshots x/z, calls `super.move`, then passes `(float)(newX-oldX)` and `(float)(newZ-oldZ)` to autojump. Thus the first threshold is based on resolved movement, not requested travel or raw input; a collision-resolved near-zero delta can make the input/yaw fallback reachable while the player is on the ground and still pressing a direction.

The geometry that follows is predictive. In 1.10 the scan uses the current post-move position as its base, initially extends by the measured delta, normalizes either the measured or fallback vector, then extends the candidate end by `max(speed * 7, 1 / inverseLength)`; it builds an inflated collision query and tests two side segments offset by half player width. Current Mojmap retains this construction (`1.21.10 LocalPlayer.java:927-940` and following geometry block). If an eligible obstacle height is found, the source sets a one-tick autojump timer; that is a predicted trigger decision, not evidence that the player followed that projected segment or that a physical jump trajectory was measured. Any claim about an actual path outcome is inference and was not runtime-tested.

## Provenance / integrity notes

- 1.9.4 has `ornithe-feather.provenance.json`: version 1.9.4, Feather namespace/mapping, feather-gen2 1.9.4 build 2 artifacts, and the checked LocalClientPlayerEntity source hash `8aaf711948b7602c2e6c015a37e36ed06073d39727d999d80480b4910b704f5d` agrees with its `ornithe-feather.sources.sha256` entry.
- 1.10 has hidden `ornithe-feather/.decompile-provenance.json`, which binds the visible `LocalClientPlayerEntity.java` SHA-256 `a9637065f21ad67464eb5c204c74ebf228c3bb0da8a96ddf4ae73c0490fed443` to Feather Gen2 `1.10+build.2` mapping artifact, Feather metadata, Mojang client JAR, and version JSON hashes. The source hash also matches 1.10.2's verified Feather class and source manifest. This establishes artifact-bound source identity; the older provenance record does not include invocation command, batch list, or cache directory. Full pin details are in `identity-followup.md`.
- 1.12 and 1.12.1 each contain a hidden `.ornithe-feather.previous-*` tree with `.decompile-provenance.json`; both hidden player-source hashes match their visible canonical LocalClientPlayerEntity files (`d7237b8b9a68525bbfec6af1c234d2aafb1e4aa7d52059331d1d243313d6d7d1`). These hidden trees were treated as provenance only, not as the canonical source path. Current top-level provenance sidecars were absent for both releases.
- 1.14.1–1.14.3 have Yarn source trees with the method under `method_3148`; no Feather/Mojmap autojump implementation was used for these releases. 1.14.4 independently has both Feather and Mojmap source trees, and both contain the same trig fallback structure.
- 1.21.10 has Mojmap provenance for the exact decompile batch 1.21.8, 1.21.10, 1.21.11. Its LocalPlayer SHA-256 `9c1df00e2f8379b2c19577a3691fee28071d8925210be3d3df928b5352e367e1` agrees with `mojmap.sources.sha256`.

## Bounded disposition

Source establishes applicability of the tiny-measured-delta input/yaw trig fallback in every checked release from 1.10 through 1.21.10, and absence of the feature in 1.9.4. The fallback's source predicates and float lookup operations persist; its enclosing eligibility gates evolve as summarized above. Predicted geometry is separate from the actual resolved delta. The 1.10 source is artifact-bound by hidden provenance; that older record lacks the invocation command, batch list, and cache directory. See `identity-followup.md`.
