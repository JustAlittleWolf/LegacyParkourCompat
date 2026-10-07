# F-03: Piston movement bypasses sneak edge restraint

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: mover-type eligibility for sneak edge restraint; S4-piston-edge-bypass
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed

## Paired evidence

- A: artifact `A` in `../run.md`; `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#move(double,double,double)`, lines 468-472, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`. The edge probe guard is `onGround && isSneaking() && this instanceof PlayerEntity`; the untyped method applies it to all movement callers.
- B: artifact `B` in `../run.md`; `net.minecraft.entity.Entity#move(MoverType,double,double,double)`, lines 519-523, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`. The guard additionally requires mover type SELF or PLAYER. B `MovingBlockEntity#moveEntities(float)` at lines 159 and 241 passes `MoverType.PISTON`; file SHA-256 `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`.

## Source-level difference

For an otherwise eligible grounded sneaking player, A always runs the horizontal support probes before movement. B runs them only for SELF and PLAYER mover types. The reachable piston displacement caller in B uses PISTON, so it does not enter the restraint block. F-01 separately records the changed probe depth when the block does run; F-02 records the independent piston cumulative cap.

## Reachability and dependencies

The player can intersect a moving piston entity area; B calls `Entity.move(PISTON,...)`. The player's grounded/sneaking state controls A's guard and remains checked in B, subject to the additional mover-type gate. Exact piston progress and collision data remain open under D-PISTON/D-COLLISION.

## Consequence and uncertainty

Source proves that B skips the edge-restraint probe for piston-supplied player movement. This may retain horizontal piston displacement that A would reduce near unsupported edges. Geometry and the final position are not established without completing collision dependencies or runtime validation.

## Handoff

Keep this delta separate from probe depth (F-01) and piston accumulated displacement (F-02). Introduction is unknown within the endpoint pair.
