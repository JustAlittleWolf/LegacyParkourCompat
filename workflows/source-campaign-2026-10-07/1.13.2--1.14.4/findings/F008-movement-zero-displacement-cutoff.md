# F008: Resolved displacement cutoff

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: entity movement update; S006
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `Entity.java`::`move(MoverType,double,double,double)`, lines 578-599 and subsequent position update, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `Entity.java`::`move(MoverType,Vec3d)`, lines 463-468, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.

## Source-level difference

After sneak-edge preprocessing, B calls the collision solver but sets the shape and derives position only when the resolved vector squared length is greater than `1.0E-7`. A enters axis resolution for any nonzero requested component and later updates position from shape without this squared-length cutoff.

## Reachability and dependencies

All non-noClip Entity.move callers can reach this behavior; piston preprocessing and noClip have separate guards. The exact vector is produced by LivingEntity travel or external displacement. Full caller inventory remains open.

## Consequence and uncertainty

Very small resolved movement can update position/shape in A but be skipped in B. The source threshold is proven; practical reachable magnitudes and downstream callback consequences have not been tested.

## Handoff

Independent cutoff delta; first changed release unknown. Implementation/testing deferred.
