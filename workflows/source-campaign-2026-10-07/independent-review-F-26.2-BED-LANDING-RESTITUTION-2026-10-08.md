# Independent blind review: F-26.2-BED-LANDING-RESTITUTION

- Review date: 2026-10-08
- Snapshot commit: `2c1ed97d01bcda8ad3f347d82d3fc4599e94a3cf`
- Snapshot path: `workflows/source-campaign-2026-10-07/26.1.2--26.2/findings/F-26.2-BED-LANDING-RESTITUTION.md`
- Snapshot blob: `febf82a0b8457f8287d3277f2ddfe18582051c5b`
- Snapshot raw SHA-256: `9fec80371220666c2f96cd654d23cc1e60618532669efbd94e2c3e439e457b35`
- Decision: **REQUEST CHANGES**
- Pair status: **PARTIAL** (this finding decision does not establish pair coverage or completion)

## Scope and integrity

Reviewed only the immutable finding snapshot identified above and exact source/artifact material it cites for the A/B bed-contact witness. Did not consult implementation, wiki material, MCPK material, run ledgers, unrelated findings, or runtime output. The snapshot bytes and binding metadata match the supplied commit/path/blob/SHA-256. The previous review decision attached to snapshot `118cc0d89130c9b0709e71a4455cf8585031fb14` remains unchanged; this review is for the corrected snapshot and does not rewrite that history.

## What is source-supported

The paired source evidence supports the core conditional difference. At a downward collision, A dispatches the effect block's fall-on callback; the cited A `BedBlock` callback sets a living entity's upward velocity to `-movement.y * 0.66F`. B instead reaches the generic collision restitution response, checks the downward-current-velocity threshold and suppression tag, and takes the maximum of entity and block restitution. B registers beds with restitution `0.75F`, while the living-entity bounciness attribute defaults to zero and beds are not in the cited bounce-suppression tag resource.

For the stated *conditional* exact-contact inputs, the collision/math trace is internally consistent. Bed collision height is 9/16 in both endpoints. At exact contact with no horizontal displacement, downward requested movement `-1.0` clips to `+0.0`; the response retains the requested delta. Thus the B movement portion is `+0.0 / -1.0 == -0.0`, gravity compensation is signed zero, `Mth.lerp(-0.0, 1.0, 0.98F)` evaluates to `1.0`, and B writes `0.75`; A writes `0.6600000262260437` from the widened `0.66F`. The subsequent ordinary air-travel gravity and friction writes yield the snapshot's stated approximately `0.5684000367641454` (A) and `0.6566000127792359` (B), given its stated default gravity, drag, effects and friction assumptions. This exact-contact calculation is appropriately narrower than the prior snapshot's generalized claim.

The client-side packet decode path is also supported: the cited shared codec preserves `-1.0`, the client packet handler resolves the entity ID and assigns the decoded movement through `Entity.lerpMotion`, and the local player's class path inherits that entity behavior. The paired dead-zone does not erase a value of `-1.0`, and the cited local-player movement/travel code can reach collision response.

## Required correction

The finding does not close the key upstream dependency for its witness: it stipulates a `ClientboundSetEntityMotionPacket` containing Y `-1.0`, but does not cite an in-scope vanilla producer and server-to-local-player send path that can deliver that value to the local player in the described non-damage movement scenario. Exact round-trip through a packet codec establishes representability, and the client handler establishes consumption; neither establishes that vanilla reaches this packet/input state for the local player. The finding itself excludes damage resolution and does not bind an eligible producer for this exact vector. Consequently, the cited sources prove the mechanics *if the witness state occurs*, but the snapshot has not established the required reachable vanilla witness.

Revise the snapshot with one bounded, source-backed reachability witness. For example, bind an ordinary falling-player state to an exact tick-start velocity and exact bed-top contact using the paired gravity/travel recurrence, then recompute the collision response from that velocity; or cite a specific non-excluded vanilla player-only state writer plus the server-to-self packet path, showing how it produces the exact packet value. Do not rely on the codec alone, an unbound supplied vector, damage/combat production, or non-player motion. Preserve the current conditional exact-contact math only if its input is made reachable or clearly separate it from the accepted witness.

This is a bounded witness-completeness request, not a finding that the restitution mechanics or arithmetic are wrong. No runtime trajectory is claimed or requested by this review. The source pair supports only that the change occurred somewhere in `(26.1.2, 26.2]`; the first changed release and broader `S-MOVE-RESTITUTE` coverage remain open.

## Pair and campaign disposition

Keep the 26.1.2–26.2 pair **PARTIAL**. This finding review does not audit the full per-tick call graph, producer/consumer inventories, or remaining movement slices and therefore cannot establish pair discovery completion. No implementation disposition or runtime validation follows from this report.
