# Early direct player pitch applicability trace

## Result

**COMPLETED** for direct finite pitch handling in exact sampled stable releases 1.7.10 and every 1.8 release from 1.8.0 through 1.8.9, against the already accepted 1.8.9/1.9.0–1.9.4 comparison. In 1.8.0–1.8.9, the player correction path does not clamp finite pitch after resolving relative flags. The sampled 1.7.10 correction has the same no-clamp result but uses an absolute angle packet, so it shows the behavior predates the 1.8 line without establishing the lower introduction boundary. No claim about unsampled versions/snapshots.

## Exact source coverage

All source paths are relative to `D:/Javastuff/LegacyParkourCompat/decompiled_minecraft/<release>/ornithe-feather/`.

- 1.8.0: `net/minecraft/client/network/handler/ClientPlayNetworkHandler.java`, `handlePlayerMove` (lines 525–567); `net/minecraft/entity/Entity.java`, `setRotation` (220–223), `updatePositionAndAngles` (887–904).
- 1.8.1: same method line ranges and results as 1.8.0.
- 1.8.2–1.8.9: `ClientPlayNetworkHandler.handlePlayerMove` (1.8.2 line 517 onward; 1.8.9 lines 517–568), `Entity.setRotation` (1.8.9 lines 221–223), `Entity.updatePositionAndAngles` (1.8.9 lines 876–893).
- 1.7.10: `ClientPlayNetworkHandler.handlePlayerMove` (480–499), `Entity.setRotation` (210–213), `Entity.updatePositionAndAngles` (858–876).
- Reused accepted exact comparison/audit: `development/research/source-evidence/20261118/block-c-grouped-source-closures/early-player-packet-authority/{comparison.md,verdict.md}`. That verdict accepts 1.8.9 vs 1.9.0–1.9.4 direct correction claims and establishes that the 1.9.x setter clamps pitch to ±90 before its rotation wrapper; it does not establish earlier 1.8.x coverage.

All ten 1.8 stable versions were individually inspected. Whole correction-handler and Entity-file payload hashes group 1.8.0 and 1.8.1 separately, while 1.8.2–1.8.9 match 1.8.9 exactly: handler SHA-256 prefixes respectively `1cca0ded9f11`, `65fe3f741549`, `7fa3d5873250`; Entity prefixes `2be081abe172`, `aa359621cf93`, `d4c10932cb5b`. The exact 1.8.9 sidecar identifies Feather gen2 build.2; the other 1.8 source trees have no local provenance sidecars, so their directory mapping family and source content are observable but generation/artifact provenance is unverified. The 1.7.10 tree likewise has no provenance sidecar. No source generation or network lookup was performed.

## Operation and finite witness

In each 1.8 `handlePlayerMove`, packet pitch is read into a `float`; if PITCH is relative, the handler adds the current `player.pitch` directly. It then calls `updatePositionAndAngles` and sends a `PlayerMoveC2SPacket.PositionAndAngles` by reading `player.pitch` directly. The setter writes `lastPitch = pitch = inputPitch`, shapes the player, then calls `setRotation`; `setRotation` assigns `pitch % 360.0F`. That modulo is not a ±90 clamp and preserves 120.0F. For example, starting at pitch 80.0F with a finite relative pitch delta of 40.0F yields 120.0F in 1.8.0–1.8.9; the immediate local position/angle response reads that raw 120.0F field. The accepted 1.9 comparison clamps the resulting absolute finite pitch to 90.0F. This witness needs no NaN/Infinity and asserts client correction application only, not server production/admission.

In 1.7.10, `handlePlayerMove` directly reads packet yaw/pitch as floats, clears velocity, and calls the same style `updatePositionAndAngles`; the setter writes pitch, places the shape, then invokes `setRotation` with `% 360.0F`. An absolute finite pitch of 120.0F therefore also remains 120.0F. The 1.7.10 handler does not resolve relative flags, so it is only a lower-bound behavior trace, not evidence for 1.8-relative semantics or a 1.7.x interval.

## Applicability closure

The no-clamp side is stable across 1.8.0–1.8.9. Sampled 1.7.10 proves the same finite out-of-range result existed by that concrete earlier version; exact earliest release and intermediate 1.7.x status remain unknown. The known transition to the clamped direct player correction is bounded by the accepted 1.8.9-to-1.9 comparison. This closes the specifically requested 1.8 sample gate and flags the lower introduction boundary as unproven.