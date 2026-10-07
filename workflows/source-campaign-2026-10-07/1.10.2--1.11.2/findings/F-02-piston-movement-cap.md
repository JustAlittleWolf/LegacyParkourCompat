# F-02: Piston displacement is capped per world-time axis

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: external piston displacement; S4-piston-cap
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed

## Paired evidence

- A: artifact `A` in `../run.md`; `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#move(double,double,double)`, lines 446-514, SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`; `MovingBlockEntity#moveEntities()`, lines 91-159, SHA-256 `720e1305c494a3a316b21beef823158c435826f50383778a5bd00ad0e4ff6ac1`. Piston displacement is sent directly to `entity.move(dx,dy,dz)` without a movement category or cumulative cap.
- B: artifact `B` in `../run.md`; `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#move(MoverType,double,double,double)`, lines 459-500, SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`; `MovingBlockEntity#moveEntities(float)`, lines 116-160 and 210-242, SHA-256 `a55ee14227fe6ac0932283cba0414e946ba8e2637f543b3a3fe13d4e45b7b16e`. The moving-block caller uses `MoverType.PISTON`; the movement method resets per-axis accumulation when world time changes, clamps cumulative displacement to `[-0.51,0.51]`, derives this call's delta, and returns when its absolute value is at most `1.0E-5F`.

## Source-level difference

B adds a PISTON-specific cumulative displacement limiter keyed by world time and entity. A has no mover-type branch or corresponding accumulator in `Entity.move`. With repeated same-axis piston pushes in one world time, B limits total accepted displacement to the clamp interval; A passes each caller displacement to ordinary collision resolution.

## Reachability and dependencies

The moving piston block entity queries entities intersecting its movement area and calls the movement method with the piston direction delta. A player intersecting that area is an entity candidate; B routes it through the PISTON branch before ordinary collision resolution. Exact progress, box-selection, collision shape, and per-tick scheduling dependencies remain open as D-PISTON and D-COLLISION.

## Consequence and uncertainty

Source proves a new per-world-time cumulative cap on piston-driven entity displacement. Position and collision consequences depend on piston progress, geometry, and repeated pushes. No runtime trajectory is claimed. `0.51` cap and `1.0E-5F` early return are source constants, not inferred outcomes.

## Handoff

Keep distinct from F-03, which concerns whether piston movement is subject to sneak edge restraint. Close D-PISTON and D-COLLISION before reconciliation. Introduction is unknown within the endpoint pair.
