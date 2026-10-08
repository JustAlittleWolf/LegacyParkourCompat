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

### Derived-artifact provenance for this source finding

This source finding uses unchanged published Java source trees, with canonical consumer revision `feather-r1-2026-10-07` independently ops-verified. A revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`; `revision.json` at the same directory, SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `5fdba25a0ac067808c51a7ecad58d2034717b83402e8020bb11a07628942c8d9`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256`, SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/artifacts.sha256`, SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`. The unavailable original mapped-JAR hash in that manifest is `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`; identity with the revised snapshot is not claimed.

B revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; `revision.json` at the same directory, SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `40131c4f05a8229d384ee7cb3680ec2c45eb6e6b7a10a25544dd7c9b71c7cc3eb`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. The unavailable original mapped-JAR hash in that manifest is `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`; identity with the revised snapshot is not claimed.

## Source-level difference

B adds a PISTON-specific cumulative displacement limiter keyed by world time and entity. A has no mover-type branch or corresponding accumulator in `Entity.move`. With repeated same-axis piston pushes in one world time, B limits total accepted displacement to the clamp interval; A passes each caller displacement to ordinary collision resolution.

## Reachability and dependencies

The moving piston block entity queries entities intersecting its movement area and calls the movement method with the piston direction delta. A player intersecting that area is an entity candidate; B routes it through the PISTON branch before ordinary collision resolution. Exact progress, box-selection, collision shape, and per-tick scheduling dependencies remain open as D-PISTON and D-COLLISION.

## Consequence and uncertainty

Source proves a new per-world-time cumulative cap on piston-driven entity displacement. Position and collision consequences depend on piston progress, geometry, and repeated pushes. No runtime trajectory is claimed. `0.51` cap and `1.0E-5F` early return are source constants, not inferred outcomes.

## Handoff

Keep distinct from F-03, which concerns whether piston movement is subject to sneak edge restraint. Close D-PISTON and D-COLLISION before reconciliation. Introduction is unknown within the endpoint pair.
