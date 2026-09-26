# 1.17.1 → 1.18.2 source navigation index

Both sides use official Mojang names and version-specific mappings. This is a bounded research index, not a complete correspondence. Paired class/member conclusions must follow inspected callers, inheritance and exact bodies.

## Stage 1 — Local input and tick ordering

- A/B: `net.minecraft.client.player.LocalPlayer`, `KeyboardInput`, `Input`.
- Compare client tick ordering, superclass tick, input sampling, travel dispatch, movement input normalization, item-use/sneak scaling, sprint transitions, jump timing and previous/current flags.
- Read member bodies with line numbers; follow options/keybind callers only if they feed movement state. Record per-member hashes in `run.md`.
- Verified bounded delta: local sprint stop ignores B `minorHorizontalCollision`; see [F-001](findings/F-001-minor-horizontal-collision-sprint.md). Its flag producer/classifier is queued for stage 4.
- Verified bounded delta: fall-flying lift coefficient changes precision/trigonometric source; see [F-002](findings/F-002-elytra-lift-trig-precision.md).
- The inspected `LocalPlayer.tick()` call order and movement-relevant `aiStep()` order match; the key input producers assign only -1/0/1, and the 0.3 slowdown yields the same float results under the two expression types. `Input` move-vector and forward threshold expressions match. These checked slices found no further delta.

## Stage 2 — Player-specific state and gates

- A/B seeds: `net.minecraft.world.entity.player.Player`, `LivingEntity`, player abilities, `FoodData`, pose/dimension helpers.
- Trace pose, sprint gates, swimming/crawling, flight, active item, edge sneaking, dimensions/eye height, air-speed storage, initialization and reset.

## Stage 3 — Living movement integration

- A/B: `LivingEntity`, `Attributes`, vector/math helpers reached directly.
- Trace travel dispatch, acceleration, friction, gravity, jump and sprint impulse, climb/water/lava/glide branches, velocity cutoffs, post-travel timing, attributes and helpers.

## Stage 4 — Entity movement and collision

- A/B: `Entity`, `AABB`, voxel shape classes, collision query owner(s), support and fluid helpers.
- Trace movement axis order, step candidates/ties, edge probes, grounding, support, velocity cancellation/restitution, collision callbacks and fluid push.

## Stage 5 — Blocks and fluids

- A/B registrations and overrides: block/state bases; slime/bed bounce; soul sand/ice friction; web/honey/powder snow slowdown; climbables; fluids/bubble columns; pistons; historical partial shapes.
- Trace registrations, shapes, block properties, relevant neighboring-state rules and original client-jar tags/resources. Record modern-only blocks separately.

## Stage 6 — Effects, enchantments, attributes and equipment

- Trace consumer → attribute/helper → aggregation → registration/application/removal → equipment/tags/resources.
- Explicit dispositions: Speed, Slowness, Jump Boost, Levitation, Slow Falling, Dolphin's Grace, Blindness; Depth Strider, Soul Speed, Frost Walker, Riptide; Elytra, item-use slowdown and relevant equipment. Server-supplied data stays distinguished from client behavior.

## Stage 7 — External influences and dependency closure

- A/B seeds: `ClientPacketListener`, relevant `Entity` and player callers.
- Trace incoming velocity/position corrections, knockback/push, explosions, pistons, mount transitions and launch items insofar as they mutate local player state. Close all dependencies and revisit callers.

## Stage 1 provisional class/member correspondence

Shared official names identify candidate classes only. The checked A/B source hashes are in `run.md`. `LocalPlayer.aiStep()` exists on both sides and the sprint-stop predicate is directly paired. Input producer bodies have not yet been fully dispositioned. No rename/split claim is needed for the same-named entry classes; inheritance and remaining member bodies still require line-level comparison.
