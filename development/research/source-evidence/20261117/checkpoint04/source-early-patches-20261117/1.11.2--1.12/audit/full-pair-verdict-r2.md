# Independent full-pair source coverage audit r2

Verdict: **PARTIAL** — accept the inventory as an accurate partial account of the exact `1.11.2 → 1.12` source comparison. Pair coverage is not complete and must not be frozen.

## Bound inputs

- Coverage inventory: `build/source-early-patches-20261117/1.11.2--1.12/coverage.md`, SHA-256 `da8f52d9b0e6e6445612cdf1f80096712e10b824f72c3d499f829b6024313514`.
- Exact source trees: `decompiled_minecraft/1.11.2/ornithe-feather/` and `decompiled_minecraft/1.12/ornithe-feather/`, with the publication/provenance identities recorded in the coverage report.
- All assigned candidate, candidate-verdict, provider/callback, tick/response, and portal-correction report hashes match the supplied immutable identities. The bounded provider and tick/response audit verdicts are consistent with their stated limits. The portal r2 verdict corrects only the client/server gate wording; its original REQUEST CHANGES history remains valid and it does not assess packet application.

## Audit disposition

The report correctly disclaims pair freeze and names material partial areas rather than treating bounded accepts as endpoint coverage. Its five source candidates have bounded, concrete player movement paths: fence/wall/pane connection bits feed collision geometry; bed vertical collision invokes the changed player response; and liquid face classification changes a flow vector applied to eligible player velocity. Their previously recorded ACCEPT verdicts remain bounded to those claims and do not close the neighboring-state crosswalk or the tick/provider inventory.

The exact-source screens and dependency reports support the described checked slices: the local-client wrapper and selected inherited tick/response methods; selected packet receiver bodies; the axis-signature change; selected collision and callback body scans; and the named face-shape providers/consumers. The provider inventory explicitly distinguishes shape declarations from collision-provider closure and callback bodies from player reachability. The tick/response inventory likewise distinguishes unchanged receivers from upstream producers and leaves client lifecycle unresolved. I found no basis in these bounded checks to upgrade their conclusions to complete coverage.

## Required open slices before freeze

- Trace the entire reachable local-client tick in order, including input production and pre-travel gates, water/lava/fall-flying and other travel branches, post-travel state, and all position, velocity, box, pose, dimension, attribute, equipment, and effect writers/consumers. Pair every changed body and close inherited caller dependencies; the current tick inventory explicitly does not do this.
- Finish the direct player response/event graph. Include packet application and lifecycle/respawn setup, block collision/step and activation callbacks, fluid and portal event routes, and any other client-side event writers. The portal correction only establishes the `inPortal` versus server-only portal-location gate; packet application and unrelated routes remain open.
- Close changed old-era collision providers by exact returned boxes, dynamic state and block-entity inputs, neighboring-state queries, and caller/entity filters. In particular, resolve moving/piston block-entity state/timing, stair virtual-shape and facing combinations, and dynamic shulker/provider callers. The current paired box/body scan is not exhaustive.
- Enumerate all remaining indirect `FaceShape` consumers and provider/callback caller edges. For each support/placement/state-production route, prove it only changes vanilla block state and is excluded; for each movement-time route, pair its provider and response behavior. Complete candidate neighbor face/state combinations as part of that work.
- Disposition the exact `World.tickEntity` player-specific loaded-area gate change in `World.java` (1.11.2 around 1408–1467; 1.12 around 1412–1476): trace its callers and client reachability, then close it as applicable or explicitly exclude it with source evidence. This changed scheduling path is not resolved by the selected packet or tick-wrapper checks.

These are specific closure requirements, not new accepted movement findings. No implementation, runtime parity, or intermediate patch-boundary claim follows from this verdict.
