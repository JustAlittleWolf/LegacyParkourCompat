# Independent audit: local stuck-probe call/control

**Terminal result: COMPLETED**

**Frozen input SHA-256:** `c38f62e59babade51760952d6ad83bedf83eb4f4c9fe38d3cee1cab104495285`

**Canonical source identity check:** The raw source SHA-256 values in the frozen comparison match the three canonical files: 1.13.2 Feather `2583495f…9bf42bf`, 1.14.4 Feather `708af6a3…5bf00ce`, and 26.2 unobfuscated `8d089aa0…1da6d6`. Reviewed the stated method bodies and line locations in those files. The unavailable original derived 1.13.2 client remains unverified; this verdict does not claim its equivalence to the revised Feather source.

## Verdict

The frozen comparison is accurate for the assigned caller/control and direct-response claims, with the following precision about admission: the four corner probes are in the same W/S, W/N, E/N, E/S order in all three sources, before the subsequent food/sprint decision block in 1.13.2 and 1.14.4, and before the following sprint reset/start checks in 26.2. The arguments use the same ±0.35 current-width offsets and `box.minY + 0.5` Y in both Feather sources; 26.2 passes only X/Z offsets to its helper. The corner order is caller order, separate from the helper's W/E/N/S candidate order.

The no-clip/no-physics checks are not at the same code layer: 1.13.2 has no enclosing `mobTick` guard and `pushAwayFrom` returns immediately on `this.noClip`; 1.14.4 guards all four calls in `mobTick` with `if (!this.noClip)`, while its helper has no such guard; 26.2 guards the four calls with `if (!this.noPhysics)`. Thus ordinary admitted calls preserve the schedule, but the sources do not establish identical admission predicates across versions (`noClip` versus `noPhysics`) or identical gate placement. Do not conflate this control statement with the response arithmetic.

For each chosen direction, the Feather helpers change only the corresponding horizontal velocity component and leave the other two components intact: 1.13.2 directly assigns `velocityX` or `velocityZ`; 1.14.4 passes the new horizontal value and old other components to `m_32166403`. 1.13.2's response constants are `-0.1F`/`0.1F`; 1.14.4 passes `-0.1`/`0.1` (double literals). 26.2 likewise updates only the chosen horizontal component via `setDeltaMovement` with `0.1 * step` and the old other components. This supports a separate precision/arithmetic difference: `0.1F` versus the later double-valued `0.1D` response. It does not support a call-order change.

The candidate search remains W, E, N, S with strict `<` comparisons in both Feather methods and in 26.2's direction array/loop. This audit is limited to the assigned call/control and direct-response claims; it makes no occupancy/geometry or broader tick-coverage finding.
