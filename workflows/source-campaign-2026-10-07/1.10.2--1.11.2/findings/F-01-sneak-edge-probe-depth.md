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

## Source-level difference

The vertical offset used by each support query changes from a fixed one block to the moving entity's `stepHeight`. The local-player inheritance chain reaches `LivingEntity` and has no later step-height writer, so the default local player changes this probe from `-1.0` to `-0.6`. Probe order and 0.05 adjustment loop remain the same in the inspected bodies. The consumer is `World.getCollisions`; its provider, shape, and neighboring-block dependencies remain open, so this finding does not claim which scenes produce a different result or the resulting trajectory.

## Reachability and dependencies

The local client player's `mobTick` samples sneak input; the living travel path calls `Entity.move`; the method guards on grounded, sneaking, and player identity before probing. B also gates this probe by mover type; the separately scoped PISTON bypass is F-03. The default and all writers of `stepHeight`, the complete `World.getCollisions` source path, and shape providers need further audit under D-STEPHEIGHT and D-COLLISION.

## Consequence and uncertainty

Source proves that B samples a different vertical volume for the support query when `stepHeight != 1.0`. It may change whether the loop considers the player supported and how much requested horizontal displacement is retained. No observed position or runtime behavior is claimed. The first changed release inside the endpoint interval is unknown.

## Handoff

Keep this delta separate from mover-type eligibility (F-03) and piston displacement clamping (F-02). Close dependencies D-STEPHEIGHT and D-COLLISION before implementation reconciliation.
