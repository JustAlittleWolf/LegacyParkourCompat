# Bottom prismarine slab edge probe in 1.21.4 and 1.21.5

This is one partial-shape witness for the [canonical edge-support query finding](../../../../findings/1.21.4--1.21.5/player-edge-support-query.md). Both versions use the same bottom prismarine slab geometry and IndirectMerger tolerance. The 1.21.5 probe adds a horizontal inset of 1e-7. For the audited prismarine slab face, a raw horizontal overlap in (1e-7, 2e-7] distinguishes the paired partial-shape queries. The lower end already collapses under 1.21.4's merge tolerance. The upper end still collapses after the 1.21.5 inset.

In the loaded three-slab setup, the grounded standing-size player has center X/Z (0.25, 1.25) and feet y=0.5. The slab cells (0,0,0), (-1,0,1), and (1,0,1) are dry bottom prismarine slabs. The first two support the separate axis probes and the third distinguishes the corner probe for a request near (0.45000015, 0, 0.1). The old corner probe retains that horizontal request. The new joint loop subtracts two signed 0.05 steps and returns about (0.35000015, 0, 0). A map-configured high movement speed and the ordinary sneak gate condition this example. It describes the pre-collision backoff result for the source state. Default-speed recurrence, the broader slab roster, final trajectory and runtime parity are outside the finding.

- [Source geometry and caller trace](probe.md), SHA-256 5a11242cdf05ed82974e8192e1d5b54da4dfeb4fba89e1e8f9a457b4bb4f8467.
- [Independent verdict](verdict.md), SHA-256 b34f2fbea7f1147935a322572e10c6a74cfd43eb5bb6ef09cbc7ce4a6b521471.
