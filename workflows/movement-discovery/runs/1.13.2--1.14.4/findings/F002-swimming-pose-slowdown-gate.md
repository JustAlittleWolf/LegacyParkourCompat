# F002: Swimming state suppresses the pose-based sneak slowdown gate

- Older version A: 1.14
- Newer version B: 1.14.4
- Mechanic / coverage slice IDs: stage 1, `INPUT-SLOWDOWN`
- Classification: changed behavior
- Confidence: source-confirmed at endpoints and exact 1.14.1; first-patch boundary unresolved
- Applicability: historical player behavior
- First changed release: after 1.14.1 and no later than 1.14.4
- Runtime validation: not performed

## Paired evidence

- A manifest: `../../../../build/stable-shared-minecraft/ready/1.14--feather.json`; `net/minecraft/client/entity/living/player/LocalClientPlayerEntity.java`, `m_63723874()` line 597, SHA-256 `2B1AD3B3416949A9DA2607A3EC2251AA69B2D9EEBD76638C92E20749D625338F`.
- 1.14.1 manifest: `../../../../build/stable-shared-minecraft/ready/1.14.1--feather.json`; the exact resolved release uses the same Feather Gen2 family. `LocalClientPlayerEntity.java`, `m_63723874()` lines 596–598, SHA-256 `2B1AD3B3416949A9DA2607A3EC2251AA69B2D9EEBD76638C92E20749D625338F`, identical to the 1.14 group-base source file.
- B manifest: `../../../../build/stable-shared-minecraft/ready/1.14.4--feather.json`; the corresponding method at lines 596–597, SHA-256 `708AF6A3880FB58B67BF4604A5509B351719A9C2A0C06A8EC261586435BF00CE`.
- On both sides, the local player computes `m_63723874() || m_99544176()` before `input.tick(...)`; the 1.14.4 `KeyboardInput.tick` applies the outer non-spectator guard. The 1.14.4 outside-water swimming-pose alternative is in `Entity.java`, lines 1818–1823, SHA-256 `7315A496C195DA767DE9D4936D3ADB6EFC3C419DC0F0E95D6F32781B0DA1BA55`.

## Source-level difference

At the exact 1.14 base, `m_63723874()` checks `!abilities.flying && canPose(SNEAKING)` before testing the sneak key or whether standing fits. In 1.14.4 it checks `!abilities.flying && !isSwimming() && canPose(SNEAKING)` before the same expression. The surrounding `m_99544176()` alternative remains true for the swimming pose outside water, so this added guard specifically suppresses the first gate while the player is swimming; it does not remove the outside-water swimming-pose path.

## Reachability and dependencies

The local player's `mobTick()` evaluates this predicate and passes it to `KeyboardInput.tick(...)`. The gate is reachable for the local player when the player is swimming and the pose/collision conditions permit the sneaking-pose check. The later flight-sneak compensation remains in the same player tick.

## Consequence and uncertainty

The source proves the endpoint expression change and confirms the guard is still absent in 1.14.1. It predicts a changed input slowdown result for swimming players who otherwise satisfy the 1.14 base pose gate. The exact patch where `!isSwimming()` first appeared is not established; inspect 1.14.2 and later patches only as needed. Do not register an override until the boundary is verified. No trajectory has been runtime-validated.

## Handoff

Keep F002 provisional for implementation boundary purposes. The targeted 1.14.1 gate body matches the 1.14 base. Inspect 1.14.2 next, then later 1.14 patches only if necessary to locate the first changed patch. If it differs from F001's 1.14 gate at a new `ParkourVersion` boundary, add the closest historical override; otherwise preserve the existing 1.14 behavior through that release.
