# Independent source audit verdict: base collision clipping follow-up

## Verdict: ACCEPT

This verdict is bound to `resetpos_base_collision_clip_followup/clip-followup.md`, SHA-256 `1897876925198177f069265438880195703989e05ee99c6ff9d20d73509830c5`. Its bounded claims are supported; no correction is required.

## Identity and paired consumer trace

The 1.12.2 and 1.13.2 ready markers identify the assigned exact versions and Ornithe Feather Gen2 build 2 mapping. Their manifest digests match the report. Recomputed source hashes match the report and local manifest entries: 1.12.2 `Entity.java` `80f091bf32166c88cf8bbd31caf72d84fa16224410733c7d2a0f00563f294a0a`; 1.13.2 `Entity.java` `1d6ec8b80f74635401745c2c027bf36555c85348ca5693764f2668363b17d269`, `VoxelShapes.java` `313968d4e5855b5ec380272b6ababcc5849d478ab771926355912a8cb6aa9c95`, and `WorldView.java` `9577360b1a1bdde9280f0b72593e5e6dde45d9a54719cd7ff933f3ecd31b971f`.

In 1.12.2 `Entity.move` queries `World.getCollisions` using the expanded current box and clips each returned `Box` in Y, then X, then Z order (`Entity.java:567–600`), translating the current box after each axis. In 1.13.2 the ordinary branch obtains the collision stream from the current box plus requested deltas and clips in the same Y/X/Z order with `VoxelShapes.calculateMaxOffset` (`Entity.java:578–598`; method implementation `VoxelShapes.java:194–205`), applying each clipped displacement before the next axis. With no collision candidates, the old loops have no effect; the target's max-offset loop has no shape to modify the requested displacement.

The candidate-query distinction is stated accurately and narrowly. `WorldView.getCollisions` subtracts the slightly shifted current box from the swept query (`WorldView.java:154–163`); `getBlockCollisions` also rejects a candidate overlapping its excluded current-box shape, then filters empty/non-overlapping results (`:112–150`). This supports the report's description of the paired candidate-filter change. It does not imply equivalent clipping for nonempty candidate sets.

## Bounded false-gate claims and reused evidence

The referenced dispatch projection, exact-key join report/CSV, provider ledger, and prior key-join ACCEPT verdict match their cited SHA-256 identities. The accepted key-join audit independently compared the exact old-base-owner/new-base-route selection and found 115 unique keys, all with `hasCollision=true`; no claim is made that this census covers aliases, unmatched keys, specialized providers, or all provider/state/context combinations.

For the named `minecraft:end_portal` exception, the accepted key-join audit verifies that the old effective `EndPortalBlock.addCollisions` override is empty, while the target registration's false gate produces an empty collision shape. This is enough for the stated no-old-obstacle consequence and does not turn the exception into a provider-roster closure.

The Structure Void packet hash and ACCEPT verdict match the provider ledger entry. That packet verifies only the exact registered key's empty ordinary collision output on both sides, despite its separate changed `getShape` bounds. Reuse is within that key/provider claim. The follow-up correctly says provider geometry was not re-audited and retains broader provider/state/context and alias gaps.

Therefore the report's conclusion is supported only for its enumerated false-gate routes: neither supplies an old collision candidate that could alter this clipping branch. Its explicit caveat that other unmatched keys, aliases, states, providers, and pair coverage remain open is necessary and accurate. No full-pair coverage, complete provider closure, or runtime parity is established.
