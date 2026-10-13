# Independent source review: corrected bed ordinary-fall witness

## Decision

**ACCEPT — source-confirmed for the bounded fresh-entity witness.** The corrected ordinary-fall sequence has a reachable source-supported initial velocity, exact bed-top contact, and the claimed A/B response difference. This does not establish that every walk-off from a settled standing state begins at zero vertical velocity, or close the broader restitution slice.

The pair remains **PARTIAL**. No implementation, runtime validation, or broader discovery disposition is made here.

## Reviewed snapshot identity

- Snapshot commit: `d90a4c13d7ac63984a758aa3f3a6ce320cecac69`
- Path: `workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-BED-LANDING-RESTITUTION.md`
- Git blob: `7762fc6328e89c66afe6c28c563c6a4bd7dd1d47`
- Raw SHA-256: `c776cc145aa9e6373d83b24c209fb1c025d914dc63c64041b4aaf7d515fc227d`

The target file bytes were checked against both supplied identities. The canonical ready markers on the primary checkout point to the exact `26.1.2/unobfuscated` and `26.2/unobfuscated` source roots. Each ready marker's source-manifest and artifact-manifest SHA-256 matched the marker values. The cited Java source files inspected for this decision match their respective source-manifest entries. No decompile, build, test, game, server, or runtime execution was performed.

## Reachability and source checks

The initial zero is source-reachable for the bounded witness as a **fresh entity's first travel tick**: A `Entity.java:222` and B `Entity.java:223` initialize `deltaMovement` to `Vec3.ZERO`. Their entity-data builders also define `DATA_NO_GRAVITY` as `false` (A `Entity.java:316`; B's corresponding builder range). This supports beginning at feet Y `1.0` above a full-cube ledge with vertical `+0.0`, provided the local player is at that position before its first travel tick and no intervening writer changes velocity. It does not support treating zero as the steady-state vertical velocity of a player that has already spent travel ticks standing on the ledge; after ordinary grounded travel, the gravity/friction tail can leave a negative stored vertical velocity. The finding's result is therefore conditional on the stated fresh-entity setup.

The paired tick path supports the rest of that setup:

- A/B `LocalPlayer.aiStep` updates input with `input.tick()` before calling `super.aiStep()`; `LivingEntity.aiStep` reaches `travel(input)`, and `Player.travel` delegates to ordinary living travel for the stated non-passenger, non-swimming, non-flying player. `Player.canSimulateMovement()` permits the local client player to simulate movement.
- A/B ordinary air travel calls movement (and thus `Entity.move`) before applying gravity and vertical drag. With default gravity `0.08` and widened `0.98F` drag, departure from `+0.0` produces `v1 = -0.07840000152587891`, then the next tails produce `v2 = -0.1552320045166016` and `v3 = -0.230527368912964` in Java double operation order.
- A/B bed collision shapes have a flat top at `9/16`, so a bed at Y `0` has top Y `0.5625`; the neighboring full cube's top at Y `1.0` is `7/16` higher. The stated fall requests move the feet to `0.9215999984741211`, then `0.7663679939575195`. On the third falling tick the bed clips the request to `-0.20386799395751953`, reaching the top exactly.
- For the setup contact, current input is shift-held. A's `Entity.move` retains the requested vertical delta for its bed callback; the bed delegates to the base block response, multiplying negative Y by zero. B's collision response selects zero restitution when suppression is active. The subsequent ordinary gravity/friction tail stores `v1` for the next tick on both endpoints. `LocalPlayer.isShiftKeyDown()` reads the current `keyPresses.shift()` value, and input is refreshed before that tick's travel, so release before the next tick is reachable.
- On that next tick the player starts exactly on the bed top and requests `v1`. Collision clips movement to `+0.0` while the response sees the original requested `-0.07840000152587891`. A's bed callback computes `-v1 * 0.66F * 1.0 = +0.05174400306320195`. B compares `-v1 = 0.07840000152587891` against effective gravity `0.08`; the `< gravity` branch sets restitution to zero. It skips the positive-restitution portion/lerp path and writes `(0.0 - v1) * 1.0 * 0.0 = +0.0`.
- The next travel tail gives A `-0.027690877537002466` and B `-0.07840000152587891`. The default no-effect case has neither Slow Falling nor Levitation; no-gravity is false, default gravity is `0.08`, and default air drag is `1.0`. The finding appropriately limits the recurrence to those defaults and ordinary friction.

The setup sign-zero distinction is consistent with source operation order: A's base callback can write `-0.0` when its input is negative, while B's zero-restitution multiplication yields `+0.0`. Subtracting gravity and applying drag stores the same negative `v1` either way. The later unsuppressed difference is therefore not an artifact of an arbitrary packet vector or a signed-zero discrepancy.

## Scope and history

This decision applies only to the cited corrected snapshot and the bounded fresh-entity witness. It does not inspect or revise the prior feedback report, does not broaden the result to settled ledge departures, and does not claim the first changed release within `(26.1.2, 26.2]`. Preserve the prior review history and its rejection of the earlier packet-input witness. Other restitution blocks/entities, independent pair audit, implementation disposition, release boundary, and runtime validation remain open.
