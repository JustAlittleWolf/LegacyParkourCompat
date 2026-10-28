# Addendum: no-physics gate in the updated research draft

Current draft SHA-256: `3085133baa5986a03f8f5a5526a94bde75bb3b99f7e6f39c3627e9f66db71f38`.
Immutable correction supplement SHA-256: `9a37211c63c75894b285376c1ec294eff1f31c4325d9e0df55a973ea9677c050`.

The draft now correctly states that the movement consequence requires `noPhysics == false` and that `Entity.move()` bypasses `maybeBackOffFromEdge` in its `noPhysics` branch. This matches the independently reviewed sources: 1.19.4 `Entity.java:550–552` directly updates position in that branch, then calls edge back-off in the other branch at `:568`; 1.20 does the same at `Entity.java:594–596` and invokes edge back-off at `:612`.

Citation refinement: the draft's source list currently cites `Entity.move` as 1.19.4 `561–570` and 1.20 `605–614`, which omit the branch itself. The factual correction is accurate; widen those method ranges to 1.19.4 `550–570` and 1.20 `593–614` if the source list should directly support the new gate. No other correction is needed. The prior independent review, comparison note and source hashes remain unchanged.
