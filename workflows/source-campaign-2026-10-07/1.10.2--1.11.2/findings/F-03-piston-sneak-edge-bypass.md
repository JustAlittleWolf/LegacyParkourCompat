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

### Derived-artifact provenance for this source finding

This source finding uses unchanged published Java source trees, with canonical consumer revision `feather-r1-2026-10-07` independently ops-verified. A revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`; `revision.json` at the same directory, SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `5fdba25a0ac067808c51a7ecad58d2034717b83402e8020bb11a07628942c8d9`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256`, SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/artifacts.sha256`, SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`. The unavailable original mapped-JAR hash in that manifest is `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`; identity with the revised snapshot is not claimed.

B revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; `revision.json` at the same directory, SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `40131c4f05a8229d384ee7cb3680ec2c45eb6e6b7a10a25544dd7c9b71c7cc3eb`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. The unavailable original mapped-JAR hash in that manifest is `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`; identity with the revised snapshot is not claimed.

## Source-level difference

For an otherwise eligible grounded sneaking player, A always runs the horizontal support probes before movement. B runs them only for SELF and PLAYER mover types. The reachable piston displacement caller in B uses PISTON, so it does not enter the restraint block. F-01 separately records the changed probe depth when the block does run; F-02 records the independent piston cumulative cap.

## Reachability and dependencies

The player can intersect a moving piston entity area; B calls `Entity.move(PISTON,...)`. The player's grounded/sneaking state controls A's guard and remains checked in B, subject to the additional mover-type gate. Exact piston progress and collision data remain open under D-PISTON/D-COLLISION.

## Consequence and uncertainty

Source proves that B skips the edge-restraint probe for piston-supplied player movement. This may retain horizontal piston displacement that A would reduce near unsupported edges. Geometry and the final position are not established without completing collision dependencies or runtime validation.

## Handoff

Keep this delta separate from probe depth (F-01) and piston accumulated displacement (F-02). Introduction is unknown within the endpoint pair.
