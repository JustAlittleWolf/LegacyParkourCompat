# Brewing Stand first hit on a bottom-slab AutoJump approach

This is a second fixture for the [existing first collision-order finding](../../../../findings/1.12.2--1.13.2/autojump-first-collision-order.md). It isolates the ordered boxes of a single Brewing Stand. The previous finding's two-block ordering witness remains independent.

For the reviewed ordinary sprint approach with feet at Y=0.5 and the stand at (0,1,0), the Y=1.01 side ray reaches the stem's footprint. Exact 1.12.2 appends the overlapping stem before the full plate, selects maxY=1.875 and rejects the 1.375 rise. In exact 1.13 and 1.13.2 the optimized union yields the low plate and a raised stem box. Only the plate intersects this ray, giving a .625 rise and setting timer 1.

The movement witness uses exact-touch center X = `-(double)(0.6F / 2.0F)`. The slab-floor step retry cannot clear the plate, so AutoJump starts at Y=0.5. On the independently traced full-floor control in 1.11.2 and 1.12.2, the player steps onto the plate at Y=1.125. AutoJump then selects the stem and schedules timer 1 at the 0.75 rise. Feet Y=1 and rise 0.875 describe the isolated query-entry control.

The exact Feather sources and their ready-manifest identities were checked. The original witness verifies exact 1.12.2. The [exact alias review](112-alias-verdict.md) confirms the same result in 1.12 and 1.12.1, the other members of the V1_12 profile. The review's raw SHA-256 is `c6bfe420b3b8adb3fe2385f742bd555c8db2a209d6b84808fba50a89886ca95f`. Earlier profiles, runtime parity and full-pair coverage remain separate.

- [Author witness](witness.md), raw SHA-256 `502a93ed7fcdd065d20900d8d70103c37d87f245bea7ec463018e123d672fd53`.
- [Independent source verdict](verdict.md), raw SHA-256 `03a1ac52a30a116071036775fc18e5cd48a9b38d47ebda4fefef68e45e5da59a`.

The alias extension covers source-level ordering in 1.11.2, 1.12 and 1.12.1. The exact-touch correction applies to the alias witness. Product eligibility remains limited to V1_12.

- [Alias-extension witness](../../../20261118/block-b-brewing-autojump/aliases-witness.md), raw SHA-256 `12757c4b0023ee57ba95fa10db2fc7c2034654bb991c6bc060113019a648fd43`.
- [Alias-extension audit](../../../20261118/block-b-brewing-autojump/aliases-audit.md), raw SHA-256 `bd2a938ad258bd9a70cf80895b8121819f3a2bfdf6c3250f0ac75c4528c0f696`.
- [Step-control report](../../../20261118/block-b-brewing-autojump/step-control.md), raw SHA-256 `89ffadc032d3bf41871d8dd8803cabc9280a29ddab56dbe21b3f5a06c862cf75`.
- [Step-control audit](../../../20261118/block-b-brewing-autojump/step-control-audit.md), raw SHA-256 `5be1000f8bb37e32db747d6a7905b307c2d4a08433678b8497f2f34be06d4fe5`. The preserved audit receipt transcribes one extra `b` in the report digest. The corrected digest above matches the original report bytes.
