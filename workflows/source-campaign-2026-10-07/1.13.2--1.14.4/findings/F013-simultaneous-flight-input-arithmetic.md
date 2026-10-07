# F013: Simultaneous sneak and jump flight arithmetic

- Older version A: 1.13.2
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: local flight vertical input; S010
- Classification: changed behavior
- Confidence: candidate (A artifact-integrity repair pending before acceptance)
- Applicability: historical player behavior
- First changed release: unknown within (1.13.2, 1.14.4]
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../build/movement-campaign-2026-10-07/ready/1.13.2/ornithe-feather/`; `LocalClientPlayerEntity.java`::`mobTick`, lines 797-806, SHA-256 `2583495f3a02b4791aa036e6a8d354d7596c4984761969a0f29b29d8d9bf42bf`.
- B manifest: `../../../build/movement-campaign-2026-10-07/ready/1.14.4/ornithe-feather/`; same method, lines 728-743, SHA-256 `708af6a3880fb58b67bf4604a5509b351719a9c2a0c06a8ec261586435bf00ce`.

## Source-level difference

Under `abilities.flying && isCamera()`, A handles sneak and jump in separate conditionals: sneaking subtracts `flySpeed * 3.0F` from double `velocityY`, then jumping adds the same float product. B increments/decrements integer `j` for those inputs and applies one vector update only when `j != 0`. With both inputs held, B leaves vertical velocity untouched; A executes two double assignments. Lateral input unscaling on sneak occurs in both.

## Reachability and dependencies

LocalClientPlayerEntity.mobTick snapshots current/previous jump and sneak state, updates flying ability, then runs this branch before superclass travel. Preconditions are flying ability, local camera player, simultaneous sneak+jump input. Fly speed comes from synchronized abilities.

## Consequence and uncertainty

Source proves different operation sequence and B's no-op at the combined-input guard. Floating-point roundoff could make A's subtract/add pair differ from the original value; no numeric witness or runtime trajectory is claimed. Exact fly-speed provenance and ability synchronization remain open in INV-EXTERNAL/INV-STATE.

## Handoff

Independent arithmetic candidate. Implementation handoff blocked by `D-ARTIFACT-INTEGRITY`, open state/ability provenance and absent independent reviewer acceptance. First changed release unknown.
