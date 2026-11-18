# Audit renewal: corrected AutoJump clauses

- Corrected report: `domain-b-autojump/detector-entry-source/autojump-gates-1.10.2-to-26.2.md`
- SHA-256: `0429A82F0EF661AF821CCD496B51E825BD29EF4E26F887BA15B38998494DCDBF` (matches assigned identity).
- Scope check: original endpoint pair and bounded entry/counter topic retained. Report explicitly limits geometry, pose/entity-fit, block-factor effects, and broader creative-flight behavior; input trace is confined to vanilla producer/install paths. No new release-boundary claim.

## Renewed verdicts

1. **Reachable movement-input domain — ACCEPT.** In the exact 1.10.2 sources, `Minecraft` installs `KeyboardInput` for the local player (1913, 1946); `KeyboardInput.tick()` constructs each component from key increments/decrements (14–42), with sneak scaling by 0.3 (45–48), while the local item-use path scales by 0.2 (LocalClientPlayerEntity 666–670). `Input.getMovement()` returns those two stored components (Input 18–20). In 26.2, `ClientPacketListener` installs `KeyboardInput` (535, 1328); the producer derives each impulse from opposing booleans and normalizes the vector (KeyboardInput 14–35), while `Vec2.normalized()` maps below-`1e-4` magnitude to zero and otherwise divides by length (Vec2 57–60). A nonzero keyboard impulse pair has magnitude at least one before normalization, so the reachable current vector is zero or finite/nonzero. The old component predicate and current squared-length predicate agree for this vanilla input domain. Arbitrary underflow-sized vectors and NaNs remain outside that domain as the corrected report says.

2. **Counter origin marker — ACCEPT.** Old `LocalClientPlayerEntity.tickMovement` sets local `bl4` when consuming a positive `ticksToNextAutojump`, decrements the counter, and sets `input.jumping` (661–677); its creative-flight jump edge excludes that synthetic press with `!bl4` (719). Current `LocalPlayer.aiStep` similarly decrements positive `autoJumpTime`, sets `wasAutoJump`, and calls `input.makeJump()` (788–793); the jump-edge/double-tap branch excludes it with `!wasAutoJump` (838). The revised report correctly describes the corresponding immediate suppression role and the distinct input-state updates, without asserting a new current-only origin behavior.

The relevant source SHA-256 values match the report's listed values and source-manifest entries. The previously retained `audit.md` was left untouched.
