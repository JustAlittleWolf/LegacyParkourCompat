# Correction supplement: constrained fallback requires external setup

This supplement narrows the reachability wording in the constrained-pose candidate. It does not edit the source review, candidate drafts, correction audit, or closure report.

## Immutable revision bindings

| Record | SHA-256 |
| --- | --- |
| `ordinary-tick/source-review.md` | `f9daef67079078add9ad621c6ef279e8275ae687b00a11184873fc6e00c1d128` |
| `ordinary-tick/constrained-player-pose-fallback.draft.md` | `9e857b97768b5dba8b03851f878d8e0f4c99ad05761e390eeba88345ed90eaf0` |
| `ordinary-tick/correction-supplement.md` | `679a5605972826dc3203717200f9c125e5834b83db8e9357177da0b8e1825481` |
| `ordinary-correction-audit/independent-audit.md` | `e07e0d91b8a27eecdc399cc54202fd0d507cfcb4551975ffb04fecff7a5f3d40` |
| `pose-query-closure/closure-report.md` | `42d1ed3e7b94f0212a99b69b03edbde4c73aac98609a3aaadc3c0f7b5ad69711` |

## Superseding reachability conclusion

Supersede the constrained-fallback draft's implied **static one-block-corridor entry** wording. In a static corridor with a full-block floor top at y=1 and full-block ceiling starting y=2, standing (1.8 high) and sneaking (1.5 high) bodies collide with the ceiling during horizontal movement. The 1.14.4 `Entity.move` path resolves movement against collision shapes (`Entity.java:444–468, 573–620`). No source-supported ordinary movement path was found that first places a standing player inside that one-block clearance while the geometry remains static. Do not present “a standing player halted by a one-block-high overhead obstruction … can … pass through on a subsequent travel tick” as an ordinary movement witness.

The only established setup here is **external vanilla command positioning**: permission-gated `TeleportCommand` (`server/command/TeleportCommand.java:35–45`) dispatches player targets to `ServerPlayNetworkHandler.teleport` (`TeleportCommand.java:247–259`), which directly invokes `updatePositionAndAngles` and sends `PlayerMoveS2CPacket` without a collision-fit query (`ServerPlayNetworkHandler.java:825–839`). The client applies that correction (`client/network/handler/ClientPlayNetworkHandler.java:652–705`). On a subsequent ordinary client tick, `PlayerEntity.tick()` calls `updatePlayerPose()` at line 281; the swimming outer fit gate, failed standing/sneaking fits, and swimming fallback follow in `PlayerEntity.java:343–370`. The 1-block band geometry is: feet y=1; standing `[1,2.8]`; sneaking `[1,2.5]`; swimming `[1,1.6]`; roof `[2,3]`; full floor `[0,1]`. The fallback query can pass while the current standing body overlaps because it tests the requested swimming box at the current position (`Entity.java:1628–1629, 2300–2305`; `WorldView.java:121–175`).

**Candidate-level disposition:** constrained fallback is **only external setup** on the evidence closed here. Retain it only if a legal vanilla teleport/position-correction trigger is in scope for the intended source finding. Otherwise withdraw it from ordinary-movement findings. This supplement makes no new acceptance, implementation, or pair-freeze claim.
