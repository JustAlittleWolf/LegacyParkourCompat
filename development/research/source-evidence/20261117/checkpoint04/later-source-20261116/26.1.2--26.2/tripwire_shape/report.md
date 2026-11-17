# Tripwire attached-state collision shape: 26.1.2 → 26.2

## Endpoint identities

Both endpoints are exact `unobfuscated` decompilations. `unobfuscated.ready.json` reports `status=ready`; both ready marker and provenance agree on version and namespace.

| Endpoint | Ready marker SHA-256 | Provenance SHA-256 | Source manifest SHA-256 (verified against marker) | Artifact manifest SHA-256 (verified against marker) |
|---|---|---|---|---|
| 26.1.2 | `f24af0f4d38543c2bd9c19b50f347d4e89d6027050e4b21ae070734843946bb7` | `8d727f880cc91f4a297e148c5b75976303d051457334bc689268fbdf631a012a` | `54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0` | `89f648229de83b0109460695c9f1f3bed7ef36efb84ede9224e80cce87118f92` |
| 26.2 | `f9406adb6bf7cb4c1ab0792a798ab2cb90e082ad4c2eee032c9f674d0ea8070f` | `a328f5333a1b27aadde4422b23159a323ae219ccd26f11207c1a5461366f27d1` | `a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894` | `ba9dc53a41bca9744ec1c8ab2ce9363764a22333c39ef64d5105f4e3bf5c98ba` |

The ready-marker source/artifact digests match the corresponding manifest bytes. Each cited Java source's SHA-256 matches its entry in the endpoint source manifest:

| Endpoint | Source | Manifest and file SHA-256 |
|---|---|---|
| 26.1.2 | `net/minecraft/world/level/block/TripWireBlock.java` | `84a31077e77005805cb8f3e697f02c3d3f90c5e671f4ae4304730105d2a6a80d` |
| 26.1.2 | `net/minecraft/world/level/block/TripWireHookBlock.java` | `8e9897c46acd70209e82cbc609b92de636c2655b400d823b35390dd63b756f94` |
| 26.2 | `net/minecraft/world/level/block/TripWireBlock.java` | `3b379d732599e55b156ffde5ebd0f7043ab4d59e6556f128e830038c77c9249a` |
| 26.2 | `net/minecraft/world/level/block/TripWireHookBlock.java` | `c08a22c1da82367373a3aab1fcddae4e4821e523216c090a44c60a1b2786a52f` |

## Compared slices and coverage

All paths below are relative to each endpoint's `unobfuscated/` source root; line references are inclusive method/declaration ranges.

- `net/minecraft/world/level/block/TripWireBlock.java`: state properties and shape constants, 35–46; constructor/default state, 53–67; shape provider, 69–72; placement and neighbor state, 74–99; placement/removal source propagation, 101–142; entity-inside collision shape, 144–147; trigger callback and pressed-state update, 149–200; connection predicate, 202–204.
- `net/minecraft/world/level/block/TripWireHookBlock.java`: properties and shape map, 36–42; shape provider, 54–57; support survival and neighbor update gates, 59–81; placement and paired-state calculation, 83–194. In `calculateState`, wire scan and attachment gates are 117–158; endpoint writes and `ATTACHED` propagation to wire states are 159–192.
- `net/minecraft/world/level/block/state/BlockBehaviour.java`: normal collision provider dispatch and fallback, 327–340 in 26.2 (333–346 in 26.1.2); `BlockState` forwarding for collision and entity-inside collision shape, 686–695 in 26.2 (680–689 in 26.1.2). These establish dispatch to `getShape` and TripWireBlock's `getEntityInsideCollisionShape` override. Generic collision algorithms were not examined.

The paired wire state carries `POWERED`, `ATTACHED`, `DISARMED`, and four directional connection flags; hook state carries `FACING`, `POWERED`, and `ATTACHED`. TripWireBlock `getShape` selects `Block.column(16, 1, 2.5)` when attached and `Block.column(16, 0, 8)` otherwise; its entity-inside shape delegates to the same state shape. TripWireHookBlock `getShape` selects its facing-rotated hook shape. In `calculateState`, `ATTACHED` is determined by a contiguous wire run, armed wires, and a correctly facing receiver beyond the first wire; `POWERED` is computed separately from armed powered wires and then gated by `attached`. A changed `ATTACHED` value is copied along the connected wire run.

## Bounded finding / no-difference

No source difference was found in the paired-state gates, properties, connection/update propagation, `calculateState` attachment calculation or propagation, or either shape provider. Both endpoints route attached state to the same shape selection, which is reachable through the block collision-shape provider and TripWireBlock's entity-inside shape override.

There are 26.2 trigger changes in `TripWireBlock.entityInside` (an additional scheduled-tick guard) and `checkPressed` (schedule a zero-delay tick after a pressed-to-unpressed transition). These affect server-side powered-trigger reevaluation. The paired calculation uses `POWERED` for the powered output and `DISARMED`/wire-run/receiver conditions for `ATTACHED`; it does not derive `ATTACHED` from the changed trigger schedule. Thus this observed delta does not establish a tripwire collision-shape difference in the assigned slice. Hook redstone method rename is also outside this shape claim.

Coverage remains partial. Effective world-state inputs, exact block registrations and any data/resource/tag conditions supplying tripwire or support states were not closed from resource sources. No conclusion is made about whole-pair coverage, onset, implementation, or runtime parity.

