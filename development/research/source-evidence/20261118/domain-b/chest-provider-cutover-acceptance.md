# Chest collision provider cutover in 1.9

The independent source review launched on 2026-11-18 (`chest_exact_provider_gate`) accepted the collision provider change and its first stable release. The reviewed [canonical finding](../../../findings/1.8.9--1.9.4/chest-mutable-collision-bounds.md) is Git blob `c095593612a764838b6169f1f2aead24481b4c76`, present at `583f8a9b01205b09922ed76d5a0eadfbe819cc85` and at the review launch. The checked draft and the normalized LF Git payload both have SHA-256 `734061d92b9cd353372e2d41cf97959708a0aeeee66bc20132aae8bb7543f514`.

- **1.8.9, Ornithe Feather Gen2 build.2.** `net/minecraft/block/ChestBlock.java:56–68` (`updateShape`) checks north, south, west and east in that order and writes bounds on the shared chest block instance through `Block.java:292–299` (`setShape`). `Block.java:339–350` (`addCollisions`, `getCollisionShape`) reads those bounds and translates them to the queried position.
- **1.9 through 1.9.4, Ornithe Feather Gen2 build.2.** Starting in exact 1.9, the generic collision provider calls the queried state's shape method. `ChestBlock.getShape` resolves adjacency at the queried position and returns its corresponding box. The body is at `ChestBlock.java:62–72` in 1.9, 1.9.1 and 1.9.2, and `:63–73` in 1.9.3 and 1.9.4. `World.getCollisions` still collects block collision candidates for `Entity.move` on both sides of the cutover.

The mapping-local `.decompile-provenance.json` is absent for 1.8.9 and 1.9.4. Their legacy `ornithe-feather.ready.json` files link the following source and artifact manifests. The consulted `ChestBlock`, `Block`, `World` and `Entity` sources match those manifests. The same four files match mapping-local provenance in 1.9 through 1.9.3.

| Release | Source manifest SHA-256 | Feather merged JAR SHA-256 | Feather Tiny SHA-256 |
| --- | --- | --- | --- |
| 1.8.9 | `9e75f46dc0ed43b6a355bd65db8a92c93a4dfeaecfa92284187c6fe9410d8004` | `303c4530c79a593b828bd778a97d3577e67f99d6a2c50760e5f9bec6fb32a9da` | `de2023ea2cca9921402fbfcfe6e475f41da4932ea6dbc609e35c505b76a32c63` |
| 1.9.4 | `c7b508fe01634887b65919dcd3a900c311a21d9510a1f1ab248d5c17c528ab19` | `49a38d0adfbda1749e519c29844116e9f22e895cb505633261b7f587268f4125` | `9e21708d4bc32a43ac404735ea3238465889797110204a0375bcb069a3798027` |

The earlier independently accepted clip remains conditional on a particular prior chest-bounds write. This verdict establishes the provider difference and cutover. The existing static-provider disposition remains in force. Deterministically reproducing the historical writer lifecycle in the modern client remains an open prerequisite. Original binary behavior and runtime parity remain unverified.
