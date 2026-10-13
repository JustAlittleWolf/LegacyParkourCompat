# F-01: Sneak edge restraint probes at step height

- Older version A: 1.10.2
- Newer version B: 1.11.2
- Mechanic / coverage slice IDs: grounded-player edge restraint; S4-sneak-probe
- Classification: changed behavior
- Confidence: source-confirmed
- Applicability: historical player behavior
- First changed release: unknown within (1.10.2, 1.11.2]
- Runtime validation: not performed

## Paired evidence

- A: artifact `A` in `../run.md`; `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#move(double,double,double)`; lines 468-512; SHA-256 `05da145effa19a6ef7934cc276e89226373b67c12f4ce89a8ce2183f29039f77`. For `onGround && isSneaking() && this instanceof PlayerEntity`, the x, z, and combined probes query the moved box at y offset `-1.0` and trim the requested horizontal movement by 0.05 until support/collision is found or movement becomes zero. `LivingEntity#LivingEntity(World)`, lines 155-166, SHA-256 `d40dd476b6b68c6ce45b4202823475deb546ecda2284da330ff6724b33815e82`, sets `stepHeight=0.6F`.
- B: artifact `B` in `../run.md`; `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather/net/minecraft/entity/Entity.java`; `net.minecraft.entity.Entity#move(MoverType,double,double,double)`; lines 519-562; SHA-256 `ce8104a17ce783df639cf9726e7b1cd0936563eaa7ba308603335bbe05d49440`. Under the same grounded/sneaking/player state and eligible mover types, the probes use y offset `-this.stepHeight`. `LivingEntity#LivingEntity(World)`, lines 162-173, SHA-256 `bb7dc6c9e423a9568d6433d51bba12e7aee4555fbf3fb3e2b87f618382279f2f`, also sets `stepHeight=0.6F`.

### Derived-artifact provenance for this source finding

This source finding uses unchanged published Java source trees, with canonical consumer revision `feather-r1-2026-10-07` independently ops-verified. A revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.10.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `0c1d71990c9c0d7cc88debf7e67663bd64c8dc7de5872176088a3119527fb28b`; `revision.json` at the same directory, SHA-256 `79b5688af6f5cf6eb5c006a867de841de0033b42408c8461283cd9c4b9389856`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `5fdba25a0ac067808c51a7ecad58d2034717b83402e8020bb11a07628942c8d9`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/ornithe-feather.sources.sha256`, SHA-256 `91b0f478acb7b6f13463c35b268a30d2806f583402631a56f54ce2eb70d1ec71`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.10.2/artifacts.sha256`, SHA-256 `6b402f3e6d6cf2f7b3647806364ff44214c47348fefd6949fad03e2011379116`. The unavailable original mapped-JAR hash in that manifest is `ab7aa536f52e94c4c099999bc731020c11a1b29e2979a566cedcc2ffb00e701d`; identity with the revised snapshot is not claimed.

B revised snapshot: `build/movement-campaign-2026-10-07/revisions/derived-artifact-snapshots/feather-r1-2026-10-07/1.11.2/ornithe-feather/client-ornithe-feather.jar`, SHA-256 `ba1872d5fd341770a45aeeb8d372776a1b89f0b88a11a72f15aa0fe879b6a29f`; `revision.json` at the same directory, SHA-256 `49fca091d3ef83551745119f740d7a66a2773e81262db137bfc747369e8f61ac`, records `originalDerivedArtifactAvailable: false`, `sourceTreeIdentical: true`, `rawInputsIdentical: true`, and `sourceFileDifferences: 0`. Its `artifact.sha256` has SHA-256 `40131c4f05a8229d384ee7cb3680ec2c45eb6e6b7a10a25544dd7c9b71c7cc3eb`. Original source manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/ornithe-feather.sources.sha256`, SHA-256 `d908c2af76598ad26999563bfef0089f1d3c8c32483eee6b108f0defa2773df0`; original raw-artifact manifest: `build/movement-campaign-2026-10-07/ready/1.11.2/artifacts.sha256`, SHA-256 `69327982116b0ce1efe32e13031cb83a93dc5f30dcd1b936bf6af003a584ac5f`. The unavailable original mapped-JAR hash in that manifest is `356d8efb64df6c89ab74e69b34718d5b79cbfbbc28b744913ce943d9679566b3`; identity with the revised snapshot is not claimed.

## Source-level difference

The vertical offset used by each support query changes from a fixed one block to the moving entity's `stepHeight`. The local-player inheritance chain reaches `LivingEntity` and has no later step-height writer, so the default local player changes this probe from `-1.0` to `-0.6`. Probe order and 0.05 adjustment loop remain the same in the inspected bodies. The consumer is `World.getCollisions`; its provider, shape, and neighboring-block dependencies remain open, so this finding does not claim which scenes produce a different result or the resulting trajectory.

## Reachability and dependencies

The local client player's `mobTick` samples sneak input; the living travel path calls `Entity.move`; the method guards on grounded, sneaking, and player identity before probing. B also gates this probe by mover type; the separately scoped PISTON bypass is F-03. The default and all writers of `stepHeight`, the complete `World.getCollisions` source path, and shape providers need further audit under D-STEPHEIGHT and D-COLLISION.

## Consequence and uncertainty

Source proves that B samples a different vertical volume for the support query when `stepHeight != 1.0`. It may change whether the loop considers the player supported and how much requested horizontal displacement is retained. No observed position or runtime behavior is claimed. The first changed release inside the endpoint interval is unknown.

## Handoff

Keep this delta separate from mover-type eligibility (F-03) and piston displacement clamping (F-02). Close dependencies D-STEPHEIGHT and D-COLLISION before implementation reconciliation.
