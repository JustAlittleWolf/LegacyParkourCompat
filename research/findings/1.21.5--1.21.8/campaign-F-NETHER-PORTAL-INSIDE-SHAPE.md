# Candidate: Nether portal callback uses a broader entity-inside shape

1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/Entity.java`, class declaration / field initialization 107-110; setAsInsidePortal 2210-2221; handlePortal 2223-2244; handlePortal 2223-2245.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/Entity.java`, class declaration / field initialization 107-110; handlePortal 2391-2413.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, getShape 61-64; getPortalTransitionTime 114-125.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 49-51.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/phys/shapes/Shapes.java`, block 49-51.
1.21.5, mojmap: `1.21.5/mojmap/net/minecraft/world/entity/PortalProcessor.java`, processPortalTeleportation 21-43.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/entity/PortalProcessor.java`, processPortalTeleportation 21-43.
1.21.8, mojmap: `1.21.8/mojmap/net/minecraft/world/level/block/NetherPortalBlock.java`, getPortalTransitionTime 114-125.

Difference: - Exact precondition: on a tick when the player is eligible for portal use and not on portal cooldown, its movement callback scan reaches a NetherPortal block and the movement path intersects the block volume but misses the axis-specific thin portal shape. 1.21.5 then does not call the portal callback for that block; 1.21.8's inherited `Shapes.block()` sentinel calls it. To produce a transition, the 1.21.8-only callback condition must recur enough ticks for `PortalProcessor` to reach the configured Nether portal delay; one isolated callback only starts or refreshes its state. - Analysis: the default full-block sentinel intentionally bypasses shape intersection in `Entity#checkInsideBlocks`, while the removed override constrained the 1.21.5 callback to the portal plane. Sustained direct Player contacts can therefore start and advance portal processing in 1.21.8 when 1.21.5 would not call the callback.

Movement meaning: - Reachable Player callback: `NetherPortalBlock#entityInside` is identical 1.21.5/1.21.8 and calls `Entity#setAsInsidePortal` when `canUsePortal(false)` succeeds (1.21.5 lines 107-110; 1.21.8 lines 102-105). The unchanged `Entity#setAsInsidePortal` stores or updates `PortalProcessor` unless the entity is on portal cooldown (1.21.5 lines 2210-2221; 1.21.8 lines 2378-2389). `Entity#handlePortal` later processes it and applies the returned `TeleportTransition` (1.21.5 lines 2223-2244; 1.21.8 lines 2391-2413). These methods are on the direct player path; portals and player dimension transitions predate both releases. - Analysis: the default full-block sentinel intentionally bypasses shape intersection in `Entity#checkInsideBlocks`, while the removed override constrained the 1.21.5 callback to the portal plane. Sustained direct Player contacts can therefore start and advance portal processing in 1.21.8 when 1.21.5 would not call the callback.

Original evidence: [archived report](../../archive/legacy-workflows/source-campaign-2026-10-07/1.21.5--1.21.8/findings/F-NETHER-PORTAL-INSIDE-SHAPE.md).
