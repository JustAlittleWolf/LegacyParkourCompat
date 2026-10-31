# Independent review: local-player look-at precision

## Verdict

**ACCEPT as a source-level local-player movement candidate**, introduced at 1.17 and retained in 1.17.1. The accepted claim is narrowly conditioned on receiving the same finite coordinate-target look packet and having identical local player state/input at the next movement call. It establishes a changed movement velocity write through the swimming travel branch. It does not establish server-side target production, an executed game tick, or a full movement trajectory.

## Frozen roster and evidence identity

I froze `roster.md` before inspecting the proposing worker’s report, then compared the enumerated exact Mojmap sources directly. The selected source hash ledger is `source-hashes.tsv`; each of its 27 source-file/version cells matched the corresponding 1.16.5 and 1.17.1 parent source-manifest entry or 1.17 embedded provenance entry. The checked source roots are `D:\Javastuff\LegacyParkourCompat\decompiled_minecraft\{1.16.5,1.17,1.17.1}\mojmap`. The independently rehashed identity records are the 1.16.5 parent `mojmap.provenance.json`, `mojmap.ready.json`, `mojmap.sources.sha256`, and `artifacts.sha256`; 1.17 `mojmap/.decompile-provenance.json`; and corresponding 1.17.1 parent sidecars. Source hashes and manifest comparisons are saved mechanically in the ledger; this review did not reopen the raw client archives or mapping bytes.

## Paired call path and operation

`ClientPacketListener.handleLookAt` is equivalent in all three versions: it queues packet work on the client thread, obtains the packet position, and when non-null calls `minecraft.player.lookAt(fromAnchor, position)`. A coordinate-target `ClientboundPlayerLookAtPacket` contains three raw doubles and a `fromAnchor`; its client `getPosition` returns a `Vec3` from those coordinates without an angle or range clamp. With `Anchor.FEET`, the anchor is the player’s current position. Thus the fixed finite packet target and equal current position are sufficient to compare the same client input on both releases. The server-side choice or production of that target is outside this review.

The runtime object is `LocalPlayer` → `AbstractClientPlayer` → `Player` → `LivingEntity` → `Entity`. `LivingEntity.lookAt` calls `super.lookAt` and then copies the resulting head/body yaw state; it does not replace the pitch calculation. The relevant `Entity.lookAt` operation is:

- **1.16.5**, `Entity.java:2628–2639`: compute horizontal distance `g = Mth.sqrt(d*d + f*f)`. The exact `Mth.sqrt(double)` overload returns `float`, so the local `double g` receives a float-rounded value. Pitch is then written from `Mth.atan2(e,g)` and converted to float.
- **1.17 and 1.17.1**, `Entity.java:2758–2771` / `:2760–2773`: use `g = Math.sqrt(d*d + f*f)`, retaining binary64 precision. For finite computed angles, the new `setXRot`/`setYRot` setters keep the same values that the old direct field writes kept; their finite-value guard does not change this witness. The yaw expression does not use `g`, so the candidate difference is pitch only.

`Mth.atan2`, its lookup tables, `Mth.sin`/`cos`, and `Vec3.directionFromRotation` use the same relevant formulas across the triple. `Entity.getLookAngle()` derives its vector from current pitch/yaw. The `Mth.sin` table index includes truncation of the float angle product, so a one-ULP pitch change can select a different entry.

## Independent static arithmetic and movement consequence

I independently reproduced the formulas from the exact sources for player position `(0,0,0)`, FEET anchor, and the same finite packet target `(-411973,-745099,283936)`:

| Value | 1.16.5 | 1.17 / 1.17.1 |
| --- | ---: | ---: |
| horizontal distance written to `g` | `500341.28125` | `500341.288347264` |
| pitch written | `56.118168` (`0x42607901`) | `56.118164` (`0x42607900`) |
| vertical look-vector sine-table index | `55320` | `55321` |
| `getLookAngle().y` | `-0.83018905` | `-0.8301356` |

This moves an operation downstream of the rotation write. In the equal-state static case where the local player is swimming, non-flying, non-spectating, on foot, and has Y delta movement `0`, `Player.travel(Vec3)` reads `g = getLookAngle().y`. Both values satisfy `g < -0.2`, so `h` is the same `0.085`; both satisfy `g <= 0`, so the vertical-update branch is taken. It writes `(g - 0) * h` to Y delta movement: `-0.0705660691857338` in 1.16.5 and `-0.0705615246295929` in 1.17/1.17.1. This is a source-derived arithmetic witness with an identical packet and movement state, not runtime execution.

The local-player route is reachable: `LocalPlayer.aiStep` delegates to `super.aiStep`; `LivingEntity.aiStep` calls virtual `travel`; `Player.travel` performs the swim adjustment before delegating to living travel. The swimming and not-passenger gates are explicit. A zero mouse/input turn preserves the response-set pitch until that movement call; no local-player pre-travel code resets it on this route. The result is therefore a direct movement-velocity write distinction once the enumerated state gates hold.

## Boundary and limits

The `Mth.sqrt(double)` → `Math.sqrt(double)` substitution first appears in `Entity.lookAt` at the 1.16.5→1.17 boundary; 1.17.1 retains the latter body. The handler and packet coordinate path themselves have no behavior change in this interval. This finding does not include server-side target-generation differences, input-device production, or any claim that every target vector or every swimming state differs. No tests, build, game launch, server launch, source generation, or product/history/wiki/backlog access was used.
