# Block C: player input evidence

This publication preserves each source comparison and its independent verdict byte-for-byte. Each verdict binds the exact comparison SHA-256 shown below. The source draft remains the immutable reviewed input; corrections and scope limits below follow the verdict and do not alter that binding. These are bounded source outcomes, not pair-wide coverage, implementation, or runtime-parity claims.

| Topic | Disposition and required correction/scope | Comparison SHA-256 | Verdict SHA-256 |
| --- | --- | --- | --- |
| [Position reconstruction](position-reconstruction/) | Accept the sampled post-collision coordinate/AABB double witness across the examined snapshots; patch boundary remains unknown in 1.16.5–1.17. Remove/qualify the draft's claimed changed auto-jump float response: both witness displacements cast to the same `0.3F`. Other double position/box readers can observe the difference. | `26d9d3ba0644778d27dcc66652e59bfc267c018125fff933c41a57751f32e82a` | `3d8d2802e6e3b879942ee75096ed1ee438950784195e4719b76085c576797500` |
| [Keyboard rounding](keyboard-rounding/) | Accept the three bounded operations: diagonal normalization moved into keyboard input (1.21.4→1.21.5); local-player damping/square correction (same pair); sneak/item multiplier ordering (1.21.3→1.21.4, retained in .5). No exact intermediate patch boundaries or runtime parity claimed. | `93b46651275edd1e26aca7d51933542ea42543a9342eb9811f8626f6d3c78e47` | `3408cf97bc4b00b569ee032ab791fdf18591ac577e8366b8bdd32cefc0b8ef68` |
| [Spectator dimension](spectator-dimension/) | Direct client movement/pose response is unchanged in the examined 1.21.5–1.21.8 snapshots; server camera transfer is scope context. Keep the respawn input-baseline change as a distinct candidate with unresolved movement consequence; do not fold it into camera behavior. | `ec2a056b3f4b618542a188f08be87ec1fc76d9b417a38fb72ad26b587e946304` | `06c564ecdd5bdbb90bab548049fcc7296a0b38405835b7fc42798f6e3ed6c997` |

The audit verdict is authoritative for each correction. See the paired files for full exact-source identities, method slices, numeric witnesses, reachability gates, and limitations.
