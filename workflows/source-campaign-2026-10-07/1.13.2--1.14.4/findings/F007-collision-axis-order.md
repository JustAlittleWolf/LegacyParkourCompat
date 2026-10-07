# F007: Collision axis order

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: entity movement collision solver; S006
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `Entity.java`::`move(MoverType,double,double,double)`, lines 578-599, SHA-256 `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; `Entity.java`::`move(MoverType,Vec3d)` and horizontal-axis helper `m_79801352`/`m_73670363`, lines 444-475 and 708-771, SHA-256 `7315a496c195da767de9d4936d3adb6efc3c419dc0f0e95d6f32781b0da1ba55`.

## Source-level difference

A obtains one collision list and resolves Y, then X, then Z. B delegates to a solver that resolves Y and orders horizontal axes by requested component magnitude, so diagonal motion can resolve Z before X or X before Z.

## Reachability and dependencies

Living travel and other displacement callers invoke Entity.move. Collision shape query generation and step solver candidate selection are separate open slices; this finding is limited to ordinary axis resolution in the cited movement call.

## Consequence and uncertainty

With competing diagonal obstacles, a different horizontal resolution order can change the final resolved vector. This is a source-level prediction; no world was simulated. Step-up and collision-shape behavior remain unresolved.

## Handoff

Independent solver delta; first changed release unknown. Implementation/testing deferred.
