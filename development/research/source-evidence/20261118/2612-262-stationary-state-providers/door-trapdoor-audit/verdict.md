# Independent exact-handoff verdict: doors and trapdoors

## Immutable inputs

- Author comparison: door-trapdoor-author/comparison.md — SHA-256 392abc8af91147f2af7753f7c769309c6eb5036d26c93b78132ff243a9c2c820 (matches assigned hash).
- Author source roster: door-trapdoor-author/source-roster.txt — SHA-256 322bdb823d73ef8bd6d31847e88939cf9f2d40941d0f706849dccdb647150ef7 (matches assigned hash).
- Pre-claim blind source roster: door-trapdoor-audit/source-roster.json — SHA-256 c5a39b8a201aed35b1540a03d284a89208b9b3cd484d59459dc1ad7253502439.
- Pre-claim blind audit: door-trapdoor-audit/audit.json — SHA-256 df068ab9f2b9a04e66ba99a9e51763cc51138a6acdf1ae5c4896a553b58d2775.
- Exact pair: 26.1.2 → 26.2, unobfuscated. Source-manifest SHA-256 values: 26.1.2 54ae320660ca911a6d20514c965c33eb414fbedd1ad6727bbaa95fe906aa23c0; 26.2 a7ad74fc712567eb87136afc1eafcfd602b4088e9869f3596331fd34faefe894.

## DoorBlock verdict

**Accepted for the bounded stationary collision-shape claim.** The author's state legality, shape selector, direction-to-box mapping, and statement that HALF and POWERED are ignored by getShape match the exact source and the independently frozen audit. The seed box is full X/Y and Z=13..16 in 0..16 coordinates; its horizontal rotations map NORTH to z=13..16, EAST to x=0..3, SOUTH to z=0..3, and WEST to x=13..16. When closed, FACING selects the box; when open, LEFT selects FACING clockwise and RIGHT selects FACING counterclockwise. Hinge has no effect while closed. Both halves produce the same box for other equal properties.

## TrapDoorBlock verdict

**Accepted for the bounded stationary collision-shape claim.** The author's state legality, selector, and box outputs match the exact source and independently frozen audit. OPEN selects the vertical facing box; closed TOP selects the DOWN rotation (y=13..16), and closed BOTTOM selects the UP rotation (y=0..3). FACING is ignored while closed; HALF is ignored while open. POWERED and WATERLOGGED do not enter the shape selector.

## Registration, current provider, and caveats

The collision/outline/cache trace is supported: base getShape is overridden by each provider; default collision uses state.getShape when hasCollision, default visual shape delegates to collision, noOcclusion does not disable collision, and default properties have collision enabled. Door/trapdoor registrations reviewed pass ordinary properties without noCollision; copper weathering subclasses inherit the providers, and waxed variants use the base provider with copied properties. The cache is per non-dynamic state; these providers are static state-only maps and ignore level, position, and context. The author's “dry” qualifier is consistent: WATERLOGGED does not affect the trapdoor box. Powered/material/block-set type are excluded from direct geometry selection; those fields and input events may affect state production, which remains outside this stationary-provider verdict.

No correction is required. These two accepted verdicts do not freeze full-pair source coverage or cover placement, activation, fluid response, support/step response, collision traversal, or other block families. The out-of-scope DoorBlock.setPlacedBy pair difference does not alter the accepted stationary shape claim.
