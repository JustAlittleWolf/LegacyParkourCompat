# S3-01: Shallow-lava travel uses a different velocity adjustment

- Older version A: 1.15.2
- Newer version B: 1.16.5
- Mechanic / coverage slice IDs: Stage 3, living travel; `S3-LAVA-TRAVEL`.
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.15.2, 1.16.5]
- Runtime validation: not performed

## Paired evidence

- A: `LivingEntity.travel(Vec3)` lava branch lines 1932–1943 always scales velocity by 0.5 and applies gravity at one quarter when gravity is enabled. Source SHA-256 `46D243BB7E51F7B54404AA1D7D6E6B827682D0F5925D02847C4193306C4D5E54`.
- B: corresponding `travel(Vec3)` lines 1959–1971 first checks `getFluidHeight(LAVA) <= getFluidJumpThreshold()`. At or below the threshold it multiplies by `(0.5, 0.8F, 0.5)` and uses `getFluidFallingAdjustedMovement`; above it retains the half-scale/gravity branch. Source SHA-256 `B5D8A1A3C80F85D5D545B5A777E9E2A915DC002A7E31B5F5AD12BF7E285D7A88`.

## Source-level difference

B adds a shallow-lava branch. `getFluidJumpThreshold()` is 0.4 when eye height is at least 0.4 and 0 otherwise. A has no corresponding branch in its lava travel code.

## Reachability and dependencies

Player travel dispatches through `LivingEntity.travel`. A player affected by fluids and standing in lava whose measured height is at or below the threshold reaches B's special adjustment. Player flight bypasses fluid travel in both versions through their respective guards.

## Consequence and uncertainty

The vertical velocity multiplier and gravity adjustment differ in this shallow-lava state, while the horizontal multiplier remains 0.5. The exact resulting motion also depends on fluid height and the fluid falling helper; no trajectory was run.

## Handoff

Independent delta: shallow-lava travel branch. Related to fluid jump behavior in `S3-02`; first changed release is unknown.
