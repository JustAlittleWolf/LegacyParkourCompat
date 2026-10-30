# MovingBlock collision-box alias follow-up

This closes the alias caveat in `provider-formulas/b.md` for the exact paired vanilla provider inventory and equal old-era states.

Exact source identities are the assigned read-only Feather manifests: 1.7.10 `11bada441ebe07661402d1c40e1c490b78b279ecaa084f9a4dc3ae2dd54caf6f`; 1.8 `52c8312c34a1a491d4a200c84e90f5d8d95c7e5caa9571f864d8e70f9737db05`. The compared source hashes are:

| File | 1.7.10 SHA-256 | 1.8 SHA-256 |
|---|---|---|
| `net/minecraft/block/MovingBlock.java` | `6528C9C12CDAE609A023326F2AE764309F7E9CEE2D70E9A5C4D04E1638F553D7` | `614CC52B357152F6F515B33EA59440DFE338227EA0CE4E7DAC7E43A13A95BA7D` |
| `net/minecraft/block/Block.java` | `462EA8943470DE83F2D923D1D267BEBC6B9AF05EECF3C60C5E90EAE870394D0B` | `4A4E6DFDE24A135ECF87C359FD98F0380664E4BACAF679815C9016CB1BA0D814` |

In 1.7.10, `MovingBlock.getCollisionShape` obtains the moved block's collision box then mutates its six bounds before returning it. In 1.8 it copies the six coordinates, mutates local doubles and returns `new Box(...)`. For the paired vanilla block set, the returned moved-block boxes are fresh objects: base `Block.getCollisionShape` constructs a new `Box.of(...)` in 1.7.10 and `new Box(...)` in 1.8; provider methods which update the shared block bounds delegate to that base method, and custom geometries construct a fresh `Box` or return null. The full direct provider-body comparisons and hashes are in `provider-formulas/a.md`, `b.md`, and `c.md`; the old Carpet provider also constructs a fresh box as recorded in its accepted finding.

Thus no paired vanilla moved-block collision provider returns a cached shared `Box` for `MovingBlock` to mutate. The object-identity difference has no additional effect on the ordinary player collision list under the exact vanilla provider inventory. A non-vanilla provider that deliberately returns a shared object is outside this source pair and the assignment's keep-vanilla-states boundary. Piston state/block-entity production and piston movement remain excluded.
